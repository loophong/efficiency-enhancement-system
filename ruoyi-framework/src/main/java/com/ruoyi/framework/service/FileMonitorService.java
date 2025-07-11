package com.ruoyi.framework.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.interceptor.FileMonitorInterceptor;

/**
 * 文件监控服务
 */
@Service
public class FileMonitorService {
    private static final Logger log = LoggerFactory.getLogger(FileMonitorService.class);
    
    /**
     * 记录文件上传信息到文件管理系统
     * 
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @param fileType 文件类型
     * @param moduleName 模块名称
     * @param isImport 是否为导入文件
     * @param relatedUrl 关联URL
     * @return 结果
     */
    public AjaxResult recordFileUpload(String filePath, String fileName, Long fileSize, String fileType, 
            String moduleName, boolean isImport, String relatedUrl) {
        try {
            // 构建文件信息
            Map<String, Object> fileInfo = new HashMap<>();
            fileInfo.put("fileName", fileName);
            fileInfo.put("filePath", filePath);
            fileInfo.put("fileSize", fileSize);
            fileInfo.put("fileType", fileType);
            fileInfo.put("fileCategory", isImport ? "IMPORT" : "UPLOAD");
            fileInfo.put("moduleName", moduleName);
            fileInfo.put("moduleCode", moduleName.replaceAll("\\s+", "_").toLowerCase());
            fileInfo.put("uploadTime", DateUtils.getTime());
            fileInfo.put("uploadUser", SecurityUtils.getUsername());
            fileInfo.put("relatedUrl", relatedUrl);
            fileInfo.put("status", "0");
            fileInfo.put("remark", "系统自动记录");
            
            // 记录到缓存
            String cacheKey = fileName + "_" + System.currentTimeMillis();
            FileMonitorInterceptor.getFileUploadCache().put(cacheKey, fileInfo);
            
            log.info("记录文件上传信息成功: {}", fileName);
            return AjaxResult.success("记录文件上传信息成功");
        } catch (Exception e) {
            log.error("记录文件上传信息失败", e);
            return AjaxResult.error("记录文件上传信息失败");
        }
    }
    
    /**
     * 获取文件上传缓存
     * 
     * @return 文件上传缓存
     */
    public Map<String, Object> getFileUploadCache() {
        return FileMonitorInterceptor.getFileUploadCache();
    }
    
    /**
     * 清除文件上传缓存
     */
    public void clearFileUploadCache() {
        FileMonitorInterceptor.clearFileUploadCache();
    }
}
