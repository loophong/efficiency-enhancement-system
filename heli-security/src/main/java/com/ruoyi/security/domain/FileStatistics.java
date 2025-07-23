package com.ruoyi.security.domain;

/**
 * 文件统计信息
 */
public class FileStatistics {
    
    /** 文件总数 */
    private Integer totalFiles;
    
    /** 上传文件数量 */
    private Integer uploadFiles;
    
    /** 导入文件数量 */
    private Integer importFiles;
    
    /** 模块数量 */
    private Integer moduleCount;

    public Integer getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(Integer totalFiles) {
        this.totalFiles = totalFiles;
    }

    public Integer getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(Integer uploadFiles) {
        this.uploadFiles = uploadFiles;
    }

    public Integer getImportFiles() {
        return importFiles;
    }

    public void setImportFiles(Integer importFiles) {
        this.importFiles = importFiles;
    }

    public Integer getModuleCount() {
        return moduleCount;
    }

    public void setModuleCount(Integer moduleCount) {
        this.moduleCount = moduleCount;
    }
} 