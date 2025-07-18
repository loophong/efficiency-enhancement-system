package com.ruoyi.security.aspect;

import java.util.Date;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.ruoyi.security.service.*;
import com.ruoyi.security.service.impl.SecurityRequireExpectPartyServiceImpl;
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
import org.springframework.core.annotation.Order;
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
import com.ruoyi.security.utils.ThreadLocalContext;
import com.ruoyi.security.service.ISecurityRiskOpportunityAssessmentService;


/**
 * 文件上传切面，用于拦截所有上传方法
 */
@Aspect
@Component
public class FileUploadAspect {
    private static final Logger log = LoggerFactory.getLogger(FileUploadAspect.class);

    // 用于标记请求是否已处理文件的属性名
    private static final String FILE_PROCESSED_FLAG = "FILE_PROCESSED_FLAG";
    private static final String FILE_MANAGEMENT_ID = "FILE_MANAGEMENT_ID";

    @Autowired
    private ISecurityFileManagementService fileManagementService;
    
    @Autowired
    private ISecuritySysMenuService securitySysMenuService;
    @Autowired
    private ISecurityEnvironmentalOrganizationDescriptionService environmentalService;
    @Autowired
    private ISecurityRequireExpectPartyService requireExpectPartyService;
    @Autowired
    private ISecurityRiskOpportunityAssessmentService riskAssessmentService;
    @Autowired
    private ISecurityCompanyKeyWorksService companyKeyWorksService;
    @Autowired
    private ISecueityCompanyKpiService secueityCompanyKpiService;
    @Autowired
    private IAnnualEnvironmentalOccupationalHealthAndSafetyGoalsService annualEnvironmentalOccupationalHealthAndSafetyGoalsService;
    @Autowired
    private ISecurityCompanyImportantEnvironmentalFactorsService importantFactorsService;
    @Autowired
    private ISecutityEnvironmentalFactorsImpactService impactService;
    @Autowired
    private ISecurityHazardSourceListService hazardSourceListService;
    @Autowired
    private ISecurityUnacceptableRiskListService unacceptableRiskListService;
    @Autowired
    private ISecutityLegalRegulationsIdentificationList1Service regulationsIdentificationList1Service;
    @Autowired
    private ISecutityLegalRegulationsIdentificationListService regulationsIdentificationListService;
    @Autowired
    private ISecurityComplianceEvaluationRecordsService securityComplianceEvaluationRecordsService;
    @Autowired
    private ISecurityEducationAnnualTrainingPlanService securityEducationAnnualTrainingPlanService;
    
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
     * 定义切点 - 拦截所有表单管理相关的Controller方法
     */
    @Pointcut("execution(* com.ruoyi..controller..*.*(..))")
    public void formManagementPointcut() {}

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
            * 环绕通知，处理所有导入方法
 */
    @Around("execution(* com.ruoyi..controller..*.*import*(..))")
    public Object aroundImportMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = LoggerFactory.getLogger(FileUploadAspect.class);
        log.info("环绕通知：处理所有导入方法 {}.{}",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());

                    // 获取当前请求
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }

                        HttpServletRequest request = attributes.getRequest();
        
        // 获取业务ID（从ThreadLocal中）
        Long threadLocalRelatedId = ThreadLocalContext.getRelatedId();
        
        // 获取Referer和当前URI
        String referer = request.getHeader("Referer");
                        String uri = request.getRequestURI();
        
        // 构造relatedUrl，优先使用Referer
        String relatedUrl = StringUtils.isNotEmpty(referer) ? referer : uri;
        relatedUrl = ensureSecurityConmPrefix(relatedUrl);

        // 获取当前用户
                        String username = SecurityUtils.getUsername();

                        // 获取模块名称
        String moduleName = getModuleNameFromUri(referer);
                        if ("未知模块".equals(moduleName)) {
                            moduleName = getModuleNameFromUri(uri);
                        }

        Long fileManagementId = null;

        // 处理文件上传
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) arg;
                if (!file.isEmpty()) {
                    // 处理文件上传，获取文件管理记录ID
                    fileManagementId = processMultipartFile(
                        file,
                        moduleName,
                                getShortClassName(joinPoint.getTarget().getClass().getSimpleName()),
                                joinPoint.getSignature().getName(),
                        username,
                        "IMPORT",
                        relatedUrl,
                        threadLocalRelatedId
                    );

                        if (fileManagementId != null) {
                        // 将处理标志和文件管理记录ID存储到请求属性中
                        request.setAttribute(FILE_PROCESSED_FLAG, true);
                        request.setAttribute(FILE_MANAGEMENT_ID, fileManagementId);
                        
                        // 存储到ThreadLocal中
                            ThreadLocalContext.setRelatedId(fileManagementId);
                        log.info("已将文件管理记录ID: {} 存储到ThreadLocal和请求属性中", fileManagementId);
                    }
                    break;
                }
            }
        }

        // 执行原始方法
        Object result = joinPoint.proceed();
        
        // 如果导入成功且有文件管理记录ID，更新关联ID
        if (result instanceof AjaxResult && fileManagementId != null) {
            AjaxResult ajaxResult = (AjaxResult) result;
            if (ajaxResult.isSuccess()) {
                String className = joinPoint.getTarget().getClass().getSimpleName();
                log.info("导入成功，准备更新关联ID: {}，控制器类名: {}", fileManagementId, className);
                updateRelatedIdForSecurityController(className, fileManagementId);
            }
        }
        
        return result;
    }
    
    /**
     * 根据控制器类型更新关联 ID
     */
    private void updateRelatedIdForSecurityController(String controllerName, Long fileManagementId) {
        int updatedRows = 0;

        try {
            // 识别所有 security 控制器并更新关联 ID
            if (controllerName.contains("EnvironmentalOrganization")) {
                // 环境识别导入
                updatedRows = environmentalService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新环境识别数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("RequireExpectParty")) {
                // 相关方期望
                updatedRows = requireExpectPartyService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新相关方期望数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            }
                else if (controllerName.contains("RiskOpportunityAssessment")){
                updatedRows = riskAssessmentService.updateLatestImportedRelatedId(fileManagementId);
               //公司更新重点工作
            } else if (controllerName.contains("CompanyKeyWorksController")) {
                updatedRows = companyKeyWorksService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新重点工作ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecueityCompanyKpiController")) {
                // 公司KPI
                updatedRows = secueityCompanyKpiService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新公司KPI数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityAccidentCauseAnalysis")) {
                // 事故原因分析secueityCompanyKpiService
                updatedRows = secueityCompanyKpiService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新事故原因分析数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("AnnualEnvironmentalOccupationalHealthAndSafetyGoals") || controllerName.contains("HealthAndSafetyGoals")) {
                // 年度环境职业健康安全目标
                updatedRows = annualEnvironmentalOccupationalHealthAndSafetyGoalsService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新年度环境职业健康安全目标数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityCompanyImportantEnvironmentalFactors")) {
                // 重要环境因素
                updatedRows = importantFactorsService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新重要环境因素数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecutityEnvironmentalFactorsImpact") || controllerName.contains("SecurityImpact")) {
                // 环境因素清单
                updatedRows = impactService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新环境因素清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityHazardSourceList") || controllerName.contains("SecurityRisklist")) {
                // 危险源清单
                updatedRows = hazardSourceListService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新危险源清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityUnacceptableRiskList") || controllerName.contains("Unacceptablerisklist")) {
                // 不可接受风险清单
                updatedRows = unacceptableRiskListService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新不可接受风险清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecutityLegalRegulationsIdentificationList1") || controllerName.contains("RegulationsIdentificationList1")) {
                // 安全法律法规识别清单
                updatedRows = regulationsIdentificationList1Service.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新安全法律法规识别清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if(controllerName.contains("SecutityLegalRegulationsIdentificationList")|| controllerName.contains("RegulationsIdentificationList")) {
                // 安全法律法规识别清单
                updatedRows = regulationsIdentificationListService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新安全法律法规识别清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityComplianceEvaluationRecords") || controllerName.contains("Compliance")) {
                // 合规性评价记录
                updatedRows = securityComplianceEvaluationRecordsService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新合规性评价记录数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEducationAnnualTrainingPlan") || controllerName.contains("SecurityTrainingplan")) {
                // 年度培训计划
                updatedRows = securityEducationAnnualTrainingPlanService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新年度培训计划数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            }
                // 事故措施跟踪
                else if (controllerName.contains("SecurityAccidentMeasuresTracking")) {
                updatedRows = getServiceAndUpdateRelatedId("accidentMeasuresTrackingService", fileManagementId);
                log.info("已更新事故措施跟踪数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityAnnualPlan")) {
                // 年度计划
                updatedRows = getServiceAndUpdateRelatedId("annualPlanService", fileManagementId);
                log.info("已更新年度计划数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityChangeManagement")) {
                // 变更管理
                updatedRows = getServiceAndUpdateRelatedId("changeManagementService", fileManagementId);
                log.info("已更新变更管理数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityCheckReport")) {
                // 检查报告
                updatedRows = getServiceAndUpdateRelatedId("checkReportService", fileManagementId);
                log.info("已更新检查报告数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityCollaboration")) {
                // 协作管理
                updatedRows = getServiceAndUpdateRelatedId("collaborationService", fileManagementId);
                log.info("已更新协作管理数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEmergencyDrill")) {
                // 应急演练
                updatedRows = getServiceAndUpdateRelatedId("emergencyDrillService", fileManagementId);
                log.info("已更新应急演练数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEmergencyPlan")) {
                // 应急预案
                updatedRows = getServiceAndUpdateRelatedId("emergencyPlanService", fileManagementId);
                log.info("已更新应急预案数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityHazardousInspection")) {
                // 危险源检查
                updatedRows = getServiceAndUpdateRelatedId("hazardousInspectionService", fileManagementId);
                log.info("已更新危险源检查数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityHazardousManagement")) {
                // 危险源管理
                updatedRows = getServiceAndUpdateRelatedId("hazardousManagementService", fileManagementId);
                log.info("已更新危险源管理数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityInternalAudit")) {
                // 内部审核
                updatedRows = getServiceAndUpdateRelatedId("internalAuditService", fileManagementId);
                log.info("已更新内部审核数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityKpiEvaluation")) {
                // KPI评价
                updatedRows = getServiceAndUpdateRelatedId("kpiEvaluationService", fileManagementId);
                log.info("已更新KPI评价数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityLegalRequirement")) {
                // 法律法规
                updatedRows = getServiceAndUpdateRelatedId("legalRequirementService", fileManagementId);
                log.info("已更新法律法规数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityManagementReview")) {
                // 管理评审
                updatedRows = getServiceAndUpdateRelatedId("managementReviewService", fileManagementId);
                log.info("已更新管理评审数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityObjective")) {
                // 目标指标
                updatedRows = getServiceAndUpdateRelatedId("objectiveService", fileManagementId);
                log.info("已更新目标指标数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityRiskAssessment")) {
                // 风险评估
                updatedRows = getServiceAndUpdateRelatedId("riskAssessmentService", fileManagementId);
                log.info("已更新风险评估数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityTraining")) {
                // 培训管理
                updatedRows = getServiceAndUpdateRelatedId("trainingService", fileManagementId);
                log.info("已更新培训管理数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityFileManagement")) {
                // 文件管理
                updatedRows = getServiceAndUpdateRelatedId("fileManagementService", fileManagementId);
                log.info("已更新文件管理数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityLegalRegulationsIdentificationList1")) {
                // 安全法律法规识别清单
                updatedRows = regulationsIdentificationList1Service.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新安全法律法规识别清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else {
                // 其他 security 控制器，通用处理
                log.info("未找到匹配的服务类，尝试通用处理: {}", controllerName);

                // 尝试从控制器名称推断服务名称
                String serviceName = inferServiceNameFromController(controllerName);
                if (serviceName != null) {
                    updatedRows = getServiceAndUpdateRelatedId(serviceName, fileManagementId);
                    log.info("已更新 {} 数据的关联 ID: {}, 更新行数: {}", serviceName, fileManagementId, updatedRows);
                } else {
                    log.warn("无法处理控制器 {}, 未能更新关联 ID", controllerName);
                }
            }
        } catch (Exception e) {
            log.error("更新关联 ID 失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 从控制器名称推断服务名称
     */
    private String inferServiceNameFromController(String controllerName) {
        // 移除 "Controller" 后缀
        String name = controllerName.replace("Controller", "");

        // 首字母小写
        if (name.length() > 0) {
            name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        }

        // 添加 "Service" 后缀
        name += "Service";

        return name;
    }

    /**
     * 获取服务并更新关联 ID
     */
    private int getServiceAndUpdateRelatedId(String serviceName, Long fileManagementId) {
        try {
            // 尝试从 Spring 容器中获取服务
            Object service = SpringUtils.getBean(serviceName);
            if (service != null) {
                // 尝试调用 updateLatestImportedRelatedId 方法
                Method method = service.getClass().getMethod("updateLatestImportedRelatedId", Long.class);
                Object result = method.invoke(service, fileManagementId);
                if (result instanceof Integer) {
                    return (Integer) result;
                }
            }
        } catch (Exception e) {
            log.error("获取服务或调用方法失败: {}", e.getMessage());
        }
        return 0;
    }

@Order(Integer.MAX_VALUE)
@AfterReturning("formManagementPointcut()")
public void afterFormManagement() {
    // 清除ThreadLocal中的数据，防止内存泄漏
    ThreadLocalContext.clearRelatedId();
    log.debug("已清除ThreadLocal中的relatedId");
}
    
    /**
     * 在上传方法返回后执行
     */
    @AfterReturning(pointcut = "uploadPointcut()", returning = "result")
    public void afterUpload(JoinPoint joinPoint, Object result) {
        log.info("拦截到上传/导入方法: {}.{}", 
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());
        
        try {
            // 获取当前请求
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                log.warn("无法获取请求上下文，可能不在Web请求中");
                return;
            }
            
            HttpServletRequest request = attributes.getRequest();
            
            // 检查是否已经处理过文件
            Boolean fileProcessed = (Boolean) request.getAttribute(FILE_PROCESSED_FLAG);
            if (Boolean.TRUE.equals(fileProcessed)) {
                log.info("文件已在环绕通知中处理，跳过afterUpload处理");
                return;
            }
            
            // 获取当前ThreadLocal中的relatedId
            Long threadLocalRelatedId = ThreadLocalContext.getRelatedId();
            
            // 获取当前登录用户
            String username = SecurityUtils.getLoginUser().getUser().getUserName();
            
            // 获取请求的URI和Referer
            String uri = request.getRequestURI();
            String referer = request.getHeader("Referer");
            
            // 构造完整的relatedUrl
            String relatedUrl = StringUtils.isNotEmpty(referer) ? referer : uri;
            relatedUrl = ensureSecurityConmPrefix(relatedUrl);
            
            // 获取模块名称
            String moduleName = getModuleNameFromUri(referer);
            if ("未知模块".equals(moduleName)) {
                moduleName = getModuleNameFromUri(uri);
            }
            
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            
            // 处理文件上传
            boolean fileHandled = false;
            
            // 处理方法参数中的MultipartFile
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) arg;
                    if (!file.isEmpty()) {
                        processMultipartFile(file, moduleName, getShortClassName(className),
                                methodName, username, "UPLOAD", relatedUrl, threadLocalRelatedId);
                        fileHandled = true;
                    }
                } else if (arg instanceof MultipartFile[]) {
                    MultipartFile[] files = (MultipartFile[]) arg;
                    for (MultipartFile file : files) {
                        if (!file.isEmpty()) {
                            processMultipartFile(file, moduleName, getShortClassName(className),
                                    methodName, username, "UPLOAD", relatedUrl, threadLocalRelatedId);
                            fileHandled = true;
                                    }
                    }
                }
            }
            
            // 如果没有处理过文件，检查MultipartHttpServletRequest
            if (!fileHandled && request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
                
                for (MultipartFile file : fileMap.values()) {
                    if (!file.isEmpty()) {
                        processMultipartFile(file, moduleName, getShortClassName(className),
                                methodName, username, "UPLOAD", relatedUrl, threadLocalRelatedId);
                        fileHandled = true;
                    }
                }
            }
                        } catch (Exception e) {
            log.error("处理文件上传失败", e);
        }
    }
    
    /**
     * 处理上传的文件，返回文件管理记录ID
     */
    private Long processMultipartFile(MultipartFile file, String moduleName, String moduleCode,
            String methodName, String username, String fileCategory, String relatedUrl, Long threadLocalRelatedId) {
        if (file == null || file.isEmpty()) {
            return null;
        }

            String originalFilename = file.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            return null;
        }

                try {
                    SecurityFileManagement fileManagement = new SecurityFileManagement();
                    fileManagement.setFileName(originalFilename);
            fileManagement.setFilePath("securityConm/" + originalFilename);
                    fileManagement.setFileSize(file.getSize());
                    fileManagement.setFileType(file.getContentType());
                    fileManagement.setFileCategory(fileCategory);
                    fileManagement.setModuleName(moduleName);
                    fileManagement.setModuleCode(moduleCode);
                    fileManagement.setUploadTime(new Date());
                    fileManagement.setUploadUser(username);
                    fileManagement.setStatus("0");
                    fileManagement.setRemark("系统自动记录-" + methodName);
            fileManagement.setRelatedUrl(relatedUrl);

            // 设置关联ID
                    if (threadLocalRelatedId != null) {
                        fileManagement.setRelatedId(threadLocalRelatedId);
                        log.info("使用ThreadLocal中的关联ID: {}", threadLocalRelatedId);
            }

                    int rows = fileManagementService.insertSecurityFileManagement(fileManagement);
            if (rows > 0) {
                log.info("成功记录文件: {}, ID: {}, 关联ID: {}", originalFilename, fileManagement.getId(), fileManagement.getRelatedId());
                return fileManagement.getId();
                    }
                } catch (Exception e) {
            log.error("记录文件信息失败: {}", e.getMessage(), e);
        }
        return null;
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