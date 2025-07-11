package com.ruoyi.framework.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 文件监控拦截器
 */
@Component
public class FileMonitorInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(FileMonitorInterceptor.class);
    
    // 存储文件上传信息的缓存
    private static final Map<String, Object> FILE_UPLOAD_CACHE = new ConcurrentHashMap<>();
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查是否是文件上传请求
        if (isFileUploadRequest(request)) {
            try {
                // 记录文件上传信息
                recordFileUpload(request);
            } catch (Exception e) {
                log.error("文件监控拦截器异常", e);
            }
        }
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 不需要实现
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 不需要实现
    }
    
    /**
     * 判断是否是文件上传请求
     */
    private boolean isFileUploadRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().contains("multipart/form-data");
    }
    
    /**
     * 记录文件上传信息
     */
    private void recordFileUpload(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            
            if (fileMap != null && !fileMap.isEmpty()) {
                for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                    MultipartFile file = entry.getValue();
                    if (file != null && !file.isEmpty()) {
                        // 获取当前用户
                        String username = "未知用户";
                        try {
                            SysUser user = SecurityUtils.getLoginUser().getUser();
                            if (user != null) {
                                username = user.getUserName();
                            }
                        } catch (Exception e) {
                            log.error("获取当前用户失败", e);
                        }
                        
                        // 构建文件信息
                        Map<String, Object> fileInfo = new ConcurrentHashMap<>();
                        fileInfo.put("fileName", file.getOriginalFilename());
                        fileInfo.put("fileSize", file.getSize());
                        fileInfo.put("fileType", file.getContentType());
                        fileInfo.put("uploadUser", username);
                        fileInfo.put("uploadTime", System.currentTimeMillis());
                        fileInfo.put("requestURI", request.getRequestURI());
                        fileInfo.put("isImport", request.getRequestURI().contains("/import"));
                        
                        // 存储文件信息到缓存
                        String cacheKey = file.getOriginalFilename() + "_" + System.currentTimeMillis();
                        FILE_UPLOAD_CACHE.put(cacheKey, fileInfo);
                        
                        log.info("监控到文件上传: {}, 大小: {}, 类型: {}, 用户: {}", 
                                file.getOriginalFilename(), file.getSize(), file.getContentType(), username);
                    }
                }
            }
        }
    }
    
    /**
     * 获取文件上传缓存
     */
    public static Map<String, Object> getFileUploadCache() {
        return FILE_UPLOAD_CACHE;
    }
    
    /**
     * 清除文件上传缓存
     */
    public static void clearFileUploadCache() {
        FILE_UPLOAD_CACHE.clear();
    }
}