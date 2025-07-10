package com.ruoyi.security.aspect;

import java.util.Date;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.security.domain.SecurityFileManagement;
import com.ruoyi.security.service.ISecurityFileManagementService;
import com.ruoyi.security.service.ISecuritySysMenuService;

/**
 * 文件上传切面，用于拦截所有上传方法
 */
@Aspect
@Component
public class FileUploadAspect {
    private static final Logger log = LoggerFactory.getLogger(FileUploadAspect.class);

    @Autowired
    private ISecurityFileManagementService fileManagementService;
    
    @Autowired
    private ISecuritySysMenuService securitySysMenuService;

    /**
     * 定义切点 - 拦截所有包含upload、import、file等关键词的Controller方法
     */
    @Pointcut("execution(* com.ruoyi..controller..*.*(..)) && (" +
              "execution(* *..*upload*(..)) || " +
              "execution(* *..*import*(..)) || " +
              "execution(* *..*Upload*(..)) || " +
              "execution(* *..*Import*(..)) || " +
              "execution(* *..*file*(..)) || " +
              "execution(* *..*File*(..)))")
    public void uploadPointcut() {}


    /**
     * 在上传方法执行前记录信息
     */
    @Before("uploadPointcut()")
    public void beforeUpload(JoinPoint joinPoint) {
        log.info("准备处理上传/导入方法: {}.{}", 
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());
    }


    /**
     * 在上传方法返回后执行
     */
    @AfterReturning(pointcut = "uploadPointcut()", returning = "result")
    public void afterUpload(JoinPoint joinPoint, Object result) {
        log.info("拦截到上传/导入方法: {}.{}", joinPoint.getTarget().getClass().getName(), 
                joinPoint.getSignature().getName());
        
        try {
            // 如果是环境识别导入方法，跳过处理（由环绕通知处理）
            if (joinPoint.getSignature().getName().equals("importData") && 
                joinPoint.getTarget().getClass().getSimpleName().contains("SecurityEnvironmentalOrganizationDescription")) {
                log.info("检测到环境识别导入方法，正在处理...");
            }
            
            // 获取当前请求
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                log.warn("无法获取请求上下文，可能不在Web请求中");
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            
            // 获取当前登录用户
            String username = "";
            try {
                SysUser user = SecurityUtils.getLoginUser().getUser();
                if (user != null) {
                    username = user.getUserName();
                }
            } catch (Exception e) {
                log.error("获取当前登录用户失败", e);
            }
            
            // 获取请求的URI和方法
            String uri = request.getRequestURI();
            String method = request.getMethod();
            log.info("请求URI: {}, 方法: {}, 用户: {}", uri, method, username);
            
            // 构建完整URL（包含查询参数）
            String fullUrl = uri;
            String queryString = request.getQueryString();
            if (StringUtils.isNotEmpty(queryString)) {
                fullUrl = uri + "?" + queryString;
            }
            
            // 获取来源URL（Referer）
            String referer = request.getHeader("Referer");
            // 优先使用Referer作为关联URL，如果没有则使用当前请求URL
            String relatedUrl = StringUtils.isNotEmpty(referer) ? referer : fullUrl;
            
            // 处理relatedUrl，确保只有一个securityConm前缀
            relatedUrl = ensureSecurityConmPrefix(relatedUrl);
            log.info("关联URL(处理后): {}", relatedUrl);
            
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            
            // 获取模块名称 - 优先从Referer中获取，因为上传文件时通常是从其他模块页面发起的
            String moduleName = "";
            if (StringUtils.isNotEmpty(referer)) {
                moduleName = getModuleNameFromUri(referer);
                log.info("从Referer获取的模块名称: {}", moduleName);
            }
            
            // 如果从Referer中无法获取有效的模块名称，则尝试从当前URI获取
            if (StringUtils.isEmpty(moduleName) || "未知模块".equals(moduleName)) {
                moduleName = getModuleNameFromUri(uri);
                log.info("从URI获取的模块名称: {}", moduleName);
            }
            
            // 添加请求内容检查，记录更多信息
            Object[] args = joinPoint.getArgs();
            log.debug("请求URI: {}, 方法: {}, 参数数量: {}", uri, method, args.length);
            for (int i = 0; i < args.length; i++) {
                log.debug("参数[{}]类型: {}", i, args[i] != null ? args[i].getClass().getName() : "null");
            }
            
            // 处理文件信息
            boolean fileProcessed = false;
            
            // 1. 处理方法参数中的MultipartFile
            for (Object arg : args) {
                if (arg instanceof MultipartFile) {
                    // 单文件上传
                    log.info("检测到单文件上传参数: {}", ((MultipartFile) arg).getOriginalFilename());
                    processMultipartFile((MultipartFile) arg, moduleName, getShortClassName(className), methodName, username, "UPLOAD", relatedUrl);
                    fileProcessed = true;
                } else if (arg instanceof MultipartFile[]) {
                    // 多文件上传
                    MultipartFile[] files = (MultipartFile[]) arg;
                    log.info("检测到多文件上传参数，文件数量: {}", files.length);
                    for (MultipartFile file : files) {
                        processMultipartFile(file, moduleName, getShortClassName(className), methodName, username, "UPLOAD", relatedUrl);
                    }
                    fileProcessed = true;
                } else {
                    // 尝试检查复杂对象中的MultipartFile字段
                    try {
                        if (arg != null) {
                            Class<?> argClass = arg.getClass();
                            for (Field field : argClass.getDeclaredFields()) {
                                field.setAccessible(true);
                                Object fieldValue = field.get(arg);
                                if (fieldValue instanceof MultipartFile) {
                                    log.info("从复杂对象中提取到文件: {}", field.getName());
                                    processMultipartFile((MultipartFile) fieldValue, moduleName, 
                                                        getShortClassName(className), methodName, 
                                                        username, "UPLOAD", relatedUrl);
                                    fileProcessed = true;
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.debug("检查复杂对象中的文件字段失败", e);
                    }
                }
            }
            
            // 2. 处理MultipartHttpServletRequest中的文件
            if (!fileProcessed && request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
                log.info("从MultipartHttpServletRequest中检测到文件，数量: {}", fileMap.size());
                
                for (MultipartFile file : fileMap.values()) {
                    processMultipartFile(file, moduleName, getShortClassName(className), methodName, username, "UPLOAD", relatedUrl);
                    fileProcessed = true;
                }
            }
            
            // 处理常见的文件上传场景，如Spring的CommonsMultipartFile
            if (!fileProcessed) {
                for (Object arg : args) {
                    if (arg != null && arg.getClass().getName().contains("MultipartFile")) {
                        try {
                            log.info("尝试通过反射处理文件类型: {}", arg.getClass().getName());
                            // 尝试通过反射调用getOriginalFilename方法
                            Method getOriginalFilename = 
                                arg.getClass().getMethod("getOriginalFilename");
                            String filename = (String) getOriginalFilename.invoke(arg);
                            
                            // 尝试通过反射调用getSize方法
                            Method getSize = 
                                arg.getClass().getMethod("getSize");
                            Long size = (Long) getSize.invoke(arg);
                            
                            // 尝试通过反射调用getContentType方法
                            Method getContentType = 
                                arg.getClass().getMethod("getContentType");
                            String contentType = (String) getContentType.invoke(arg);
                            
                            log.info("通过反射获取文件信息: 名称={}, 大小={}, 类型={}", filename, size, contentType);
                            
                            // 创建文件记录
                            SecurityFileManagement fileManagement = new SecurityFileManagement();
                            fileManagement.setFileName(filename);
                            fileManagement.setFilePath("securityConm/" + filename);
                            fileManagement.setFileSize(size);
                            fileManagement.setFileType(contentType);
                            fileManagement.setFileCategory("UPLOAD");
                            fileManagement.setModuleName(moduleName);
                            fileManagement.setModuleCode(getShortClassName(className));
                            fileManagement.setUploadTime(new Date());
                            fileManagement.setUploadUser(username);
                            fileManagement.setStatus("0");
                            fileManagement.setRemark("系统自动记录(反射)-" + methodName);
                            fileManagement.setRelatedUrl(ensureSecurityConmPrefix(relatedUrl));
                            
                            log.info("准备插入文件记录到数据库: {}", fileManagement.getFileName());
                            int rows = fileManagementService.insertSecurityFileManagement(fileManagement);
                            log.info("文件记录插入结果: {} 行受影响, ID: {}", rows, fileManagement.getId());
                            
                            log.info("通过反射处理文件: {}", filename);
                            fileProcessed = true;
                        } catch (Exception e) {
                            log.error("尝试通过反射处理文件失败", e);
                        }
                    }
                }
            }
            
            // 3. 处理导入类型的请求（Excel导入等）
            if ((methodName.toLowerCase().contains("import") || uri.toLowerCase().contains("import")) && !fileProcessed) {
                // 尝试从返回结果中获取文件信息
                if (result instanceof AjaxResult) {
                    AjaxResult ajaxResult = (AjaxResult) result;
                    // 放宽条件，不再严格要求msg包含"导入"
                    if (ajaxResult.isSuccess()) {
                        log.info("检测到成功的导入操作，准备记录导入文件");
                        // 创建导入文件记录
                        SecurityFileManagement fileManagement = new SecurityFileManagement();
                        fileManagement.setFileName(methodName + "_导入_" + System.currentTimeMillis());
                        fileManagement.setFilePath("securityConm/" + methodName + "_导入_" + System.currentTimeMillis());
                        fileManagement.setFileCategory("IMPORT");
                        fileManagement.setModuleName(moduleName);
                        fileManagement.setModuleCode(getShortClassName(className));
                        fileManagement.setUploadTime(new Date());
                        fileManagement.setUploadUser(username);
                        fileManagement.setStatus("0");
                        fileManagement.setRemark("系统自动记录-导入操作");
                        fileManagement.setRelatedUrl(ensureSecurityConmPrefix(relatedUrl)); // 设置关联URL
                        
                        log.info("准备插入导入文件记录到数据库: {}", fileManagement.getFileName());
                        int rows = fileManagementService.insertSecurityFileManagement(fileManagement);
                        log.info("导入文件记录插入结果: {} 行受影响", rows);
                        
                        log.info("记录导入操作: {}, 模块: {}, 用户: {}, 关联URL: {}", 
                                methodName, moduleName, username, relatedUrl);
                        fileProcessed = true;
                    } else {
                        log.warn("导入操作未成功，不记录文件信息");
                    }
                }
            }
            
            // 检查请求中是否包含文件参数
            if (!fileProcessed) {
                try {
                    if (request.getContentType() != null && 
                        request.getContentType().toLowerCase().contains("multipart")) {
                        log.info("检测到multipart请求，但未能从参数中提取文件，尝试从request中获取");
                        // 记录一个通用的上传记录
                        SecurityFileManagement fileManagement = new SecurityFileManagement();
                        fileManagement.setFileName("未识别文件_" + System.currentTimeMillis());
                        fileManagement.setFilePath("securityConm/" + uri);
                        fileManagement.setFileCategory("UPLOAD");
                        fileManagement.setModuleName(moduleName);
                        fileManagement.setModuleCode(getShortClassName(className));
                        fileManagement.setUploadTime(new Date());
                        fileManagement.setUploadUser(username);
                        fileManagement.setStatus("0");
                        fileManagement.setRemark("系统自动记录-" + methodName + "(未能识别具体文件)");
                        fileManagement.setRelatedUrl(ensureSecurityConmPrefix(relatedUrl));
                        
                        log.info("准备插入未识别文件记录到数据库");
                        int rows = fileManagementService.insertSecurityFileManagement(fileManagement);
                        log.info("未识别文件记录插入结果: {} 行受影响", rows);
                        
                        fileProcessed = true;
                    }
                } catch (Exception e) {
                    log.error("检查请求内容类型失败", e);
                }
            }
            
            if (!fileProcessed) {
                log.warn("方法 {}.{} 可能包含文件上传，但未能识别文件参数", className, methodName);
            }
        } catch (Exception e) {
            log.error("记录文件上传信息失败", e);
        }
    }
    
    /**
     * 处理上传的文件
     */
    private void processMultipartFile(MultipartFile file, String moduleName, String moduleCode, String methodName, String username, String fileCategory, String relatedUrl) {
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            long size = file.getSize();
            
            log.info("处理文件: {}, 类型: {}, 大小: {} 字节, 模块: {}", originalFilename, contentType, size, moduleName);
            
            if (StringUtils.isNotEmpty(originalFilename)) {
                try {
                    // 创建文件记录
                    SecurityFileManagement fileManagement = new SecurityFileManagement();
                    fileManagement.setFileName(originalFilename);
                    fileManagement.setFilePath("securityConm/" + originalFilename); // 添加securityConm前缀到路径
                    fileManagement.setFileSize(file.getSize());
                    fileManagement.setFileType(file.getContentType());
                    fileManagement.setFileCategory(fileCategory);
                    fileManagement.setModuleName(moduleName);
                    fileManagement.setModuleCode(moduleCode);
                    fileManagement.setUploadTime(new Date());
                    fileManagement.setUploadUser(username);
                    fileManagement.setStatus("0");
                    fileManagement.setRemark("系统自动记录-" + methodName);
                    
                    // 确保relatedUrl只有一个securityConm前缀
                    relatedUrl = ensureSecurityConmPrefix(relatedUrl);
                    fileManagement.setRelatedUrl(relatedUrl); // 设置关联URL
                    
                    // 提取关联ID（如果URL中包含ID参数）
                    if (StringUtils.isNotEmpty(relatedUrl)) {
                        try {
                            // 尝试从URL中提取id参数
                            if (relatedUrl.contains("id=")) {
                                String idParam = relatedUrl.substring(relatedUrl.indexOf("id=") + 3);
                                if (idParam.contains("&")) {
                                    idParam = idParam.substring(0, idParam.indexOf("&"));
                                }
                                try {
                                    Long relatedId = Long.parseLong(idParam);
                                    fileManagement.setRelatedId(relatedId);
                                    log.info("从URL中提取到关联ID: {}", relatedId);
                                } catch (NumberFormatException nfe) {
                                    log.debug("关联ID不是有效的数字: {}", idParam);
                                }
                            }
                        } catch (Exception e) {
                            log.debug("从URL提取关联ID失败", e);
                        }
                    }
                    
                    log.info("准备插入文件记录到数据库: {}, 模块: {}, 用户: {}", originalFilename, moduleName, username);
                    int rows = fileManagementService.insertSecurityFileManagement(fileManagement);
                    log.info("文件记录插入结果: {} 行受影响, ID: {}", rows, fileManagement.getId());
                    
                    if (rows <= 0) {
                        log.error("文件记录插入失败，受影响行数为0");
                    } else {
                        log.info("成功记录文件上传: {}, 文件: {}, 模块: {}, 用户: {}, 关联URL: {}", 
                                methodName, originalFilename, moduleName, username, relatedUrl);
                    }
                } catch (Exception e) {
                    log.error("插入文件记录到数据库失败: {}", e.getMessage(), e);
                }
            } else {
                log.warn("文件名为空，无法处理文件");
            }
        } else {
            log.warn("文件为空或无效，无法处理");
        }
    }
    
    /**
     * 标准化URL，确保是相对路径
     * 
     * @param url 原始URL
     * @return 标准化后的相对URL
     */
    private String normalizeUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        
        String normalizedUrl = url;
        
        // 处理重复的URL前缀问题
        String httpPattern = "http://";
        String httpsPattern = "https://";
        int lastHttpIndex = normalizedUrl.lastIndexOf(httpPattern);
        int lastHttpsIndex = normalizedUrl.lastIndexOf(httpsPattern);
        int lastIndex = Math.max(lastHttpIndex, lastHttpsIndex);
        
        if (lastIndex > 0) {
            normalizedUrl = normalizedUrl.substring(lastIndex);
        }
        
        // 移除域名和端口号部分
        if (normalizedUrl.startsWith(httpPattern) || normalizedUrl.startsWith(httpsPattern)) {
            int pathStartIndex = normalizedUrl.indexOf('/', 8); // 跳过http(s)://
            if (pathStartIndex > 0) {
                normalizedUrl = normalizedUrl.substring(pathStartIndex);
            }
        }
        
        // 先移除所有securityConm前缀，但只处理以securityConm/开头的情况
        while (normalizedUrl.startsWith("/securityConm/") || normalizedUrl.startsWith("securityConm/")) {
            if (normalizedUrl.startsWith("/securityConm/")) {
                normalizedUrl = "/" + normalizedUrl.substring("/securityConm/".length());
            } else if (normalizedUrl.startsWith("securityConm/")) {
                normalizedUrl = normalizedUrl.substring("securityConm/".length());
            }
        }
        
        // 提取security1或security2后面的部分
        int securityIndex1 = normalizedUrl.indexOf("security1");
        int securityIndex2 = normalizedUrl.indexOf("security2");
        
        if (securityIndex1 != -1) {
            normalizedUrl = normalizedUrl.substring(securityIndex1);
        } else if (securityIndex2 != -1) {
            normalizedUrl = normalizedUrl.substring(securityIndex2);
        } else {
            // 如果没有找到security1或security2，尝试查找security
            int securityIndex = normalizedUrl.indexOf("security");
            if (securityIndex != -1) {
                normalizedUrl = normalizedUrl.substring(securityIndex);
            }
        }
        
        // 确保URL以/开头
        if (!normalizedUrl.startsWith("/") && !normalizedUrl.startsWith("#")) {
            normalizedUrl = "/" + normalizedUrl;
        }
        
        return normalizedUrl;
    }
    
    /**
     * 从URI中获取模块名称
     */
    private String getModuleNameFromUri(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return "未知模块";
        }
        
        try {
            log.debug("开始从URI解析模块名称: {}", uri);
            
            // 解析URI获取模块标识
            String moduleKey = "";
            StringBuilder modulePath = new StringBuilder();
            
            // 处理前端URL格式（例如：http://localhost:8080/#/security/fileManagement）
            if (uri.contains("#/")) {
                String path = uri.substring(uri.indexOf("#/") + 2);
                log.debug("从前端URL提取路径: {}", path);
                
                String[] pathParts = path.split("/");
                
                // 查找 security 或 security1 或 security2
                int securityIndex = -1;
                for (int i = 0; i < pathParts.length; i++) {
                    if (pathParts[i] != null && !pathParts[i].isEmpty() && 
                        (pathParts[i].equals("security") || 
                         pathParts[i].equals("security1") || 
                         pathParts[i].equals("security2"))) {
                        securityIndex = i;
                        break;
                    }
                }
                
                // 如果找到了 security，提取它和它后面的所有部分
                if (securityIndex >= 0) {
                    modulePath.append(pathParts[securityIndex]);
                    // 添加 security 后面的所有部分
                    for (int i = securityIndex + 1; i < pathParts.length; i++) {
                        if (pathParts[i] != null && !pathParts[i].isEmpty()) {
                            modulePath.append("/").append(pathParts[i]);
                        }
                    }
                    moduleKey = modulePath.toString();
                    log.debug("从前端URL提取到完整模块路径: {}", moduleKey);
                } else {
                    // 如果没有找到 security，使用最后一个非空部分
                    for (int i = pathParts.length - 1; i >= 0; i--) {
                        if (pathParts[i] != null && !pathParts[i].isEmpty()) {
                            moduleKey = pathParts[i];
                            log.debug("从前端URL提取到模块标识: {}", moduleKey);
                            break;
                        }
                    }
                }
            } else {
                // 处理后端API URL格式（例如：/security/environmentidicaation/import）
                String[] pathParts = uri.split("/");
                
                // 查找 security 或 security1 或 security2
                int securityIndex = -1;
                for (int i = 0; i < pathParts.length; i++) {
                    if (pathParts[i] != null && !pathParts[i].isEmpty() && 
                        (pathParts[i].equals("security") || 
                         pathParts[i].equals("security1") || 
                         pathParts[i].equals("security2"))) {
                        securityIndex = i;
                        break;
                    }
                }
                
                // 如果找到了 security，提取它和它后面的所有部分
                if (securityIndex >= 0) {
                    modulePath.append(pathParts[securityIndex]);
                    // 添加 security 后面的所有部分
                    for (int i = securityIndex + 1; i < pathParts.length; i++) {
                        if (pathParts[i] != null && !pathParts[i].isEmpty()) {
                            modulePath.append("/").append(pathParts[i]);
                        }
                    }
                    moduleKey = modulePath.toString();
                    log.debug("从后端URL提取到完整模块路径: {}", moduleKey);
                } else {
                    // 如果没有找到 security，使用最后一个非空部分
                    for (int i = pathParts.length - 1; i >= 0; i--) {
                        if (pathParts[i] != null && !pathParts[i].isEmpty()) {
                            moduleKey = pathParts[i];
                            log.debug("从后端URL提取到模块标识: {}", moduleKey);
                            break;
                        }
                    }
                }
            }
            
            // 处理特殊情况：如果模块标识是一些常见的操作名称，则继续向前查找
            if (moduleKey.equals("import") || moduleKey.equals("upload") || moduleKey.equals("download") || 
                moduleKey.equals("list") || moduleKey.equals("add") || moduleKey.equals("edit") || 
                moduleKey.equals("delete") || moduleKey.equals("export")) {
                
                log.debug("检测到操作名称，继续向前查找模块标识: {}", moduleKey);
                
                // 重新从URL中提取模块标识
                String[] parts;
                if (uri.contains("#/")) {
                    String path = uri.substring(uri.indexOf("#/") + 2);
                    parts = path.split("/");
                } else {
                    parts = uri.split("/");
                }
                
                // 查找倒数第二个非空部分作为模块标识
                String lastPart = null;
                String secondLastPart = null;
                
                for (int i = parts.length - 1; i >= 0; i--) {
                    if (parts[i] != null && !parts[i].isEmpty()) {
                        if (lastPart == null) {
                            lastPart = parts[i];
                        } else {
                            secondLastPart = parts[i];
                            break;
                        }
                    }
                }
                
                if (secondLastPart != null) {
                    moduleKey = secondLastPart;
                    log.debug("提取到倒数第二个部分作为模块标识: {}", moduleKey);
                }
            }
            
            // 如果找到模块标识，尝试从菜单表中查询对应的 menu_name
            if (StringUtils.isNotEmpty(moduleKey)) {
                try {
                    // 使用注入的 securitySysMenuService
                    String menuName = securitySysMenuService.getMenuNameByPath(moduleKey);
                    if (StringUtils.isNotEmpty(menuName)) {
                        log.info("根据路径 {} 找到菜单名称: {}", moduleKey, menuName);
                        return menuName;
                    }
                } catch (Exception e) {
                    log.debug("查询菜单名称失败: {}", e.getMessage());
                }
            }
            
            // 如果无法从菜单表中获取，则使用 URL 中的模块名
            if (StringUtils.isNotEmpty(moduleKey)) {
                return moduleKey;
            }
            
            // 如果所有方法都失败，尝试从URI中提取一个合理的部分
            String[] parts = uri.split("/");
            if (parts.length >= 3) {
                for (int i = 2; i < parts.length; i++) {
                    if (StringUtils.isNotEmpty(parts[i]) && 
                        !parts[i].equals("import") && 
                        !parts[i].equals("upload") && 
                        !parts[i].equals("download")) {
                        return parts[i];
                    }
                }
                return parts[2];
            } else if (parts.length >= 2) {
                return parts[1];
            }
        } catch (Exception e) {
            log.error("解析模块名称失败", e);
        }
        
        return "未知模块";
    }
    
    /**
     * 获取类名的简短表示（确保不超过50个字符）
     */
    private String getShortClassName(String className) {
        if (StringUtils.isEmpty(className)) {
            return "Unknown";
        }
        
        // 如果类名已经很短，直接返回
        if (className.length() <= 50) {
            return className;
        }
        
        // 如果类名太长，截取前50个字符
        return className.substring(0, 50);
    }
    
    /**
     * 确保URL只有一个securityConm前缀
     * 
     * @param url 原始URL
     * @return 处理后的URL，确保只有一个securityConm前缀
     */
    private String ensureSecurityConmPrefix(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        
        // 先进行标准化处理
        String normalizedUrl = normalizeUrl(url);
        
        // 移除所有securityConm前缀，但只处理以securityConm/开头的情况
        while (normalizedUrl.startsWith("/securityConm/") || normalizedUrl.startsWith("securityConm/")) {
            if (normalizedUrl.startsWith("/securityConm/")) {
                normalizedUrl = "/" + normalizedUrl.substring("/securityConm/".length());
            } else if (normalizedUrl.startsWith("securityConm/")) {
                normalizedUrl = normalizedUrl.substring("securityConm/".length());
            }
        }
        
        // 确保URL以/开头
        if (!normalizedUrl.startsWith("/")) {
            normalizedUrl = "/" + normalizedUrl;
        }
        
        // 添加一个securityConm前缀
        return "securityConm" + normalizedUrl;
    }
}