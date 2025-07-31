package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文件管理对象 security_file_management
 * 
 * @author wang
 * @date 2025-07-09
 */
public class SecurityFileManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件路径 */
    private String filePath;

    /** 文件大小(字节) */
    @Excel(name = "文件大小(字节)")
    private Long fileSize;

    /** 文件类型(MIME类型) */
    @Excel(name = "文件类型(MIME类型)")
    private String fileType;

    /** 文件分类(UPLOAD:上传文件/IMPORT:导入文件) */
    @Excel(name = "文件分类(UPLOAD:上传文件/IMPORT:导入文件)")
    private String fileCategory;

    /** 所属模块名称 */
    @Excel(name = "所属模块名称")
    private String moduleName;

    /** 所属模块代码 */
    private String moduleCode;

    /** 关联ID(用于导入文件跳转) */
    private Long relatedId;

    /** 关联URL(用于跳转) */
    private String relatedUrl;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadTime;

    /** 上传用户 */
    @Excel(name = "上传用户")
    private String uploadUser;

    /** 状态(0正常 1禁用) */
    @Excel(name = "状态(0正常 1禁用)")
    private String status;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 其他信息 */
    @Excel(name = "其他信息")
    private String text;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }
    public void setFileSize(Long fileSize) 
    {
        this.fileSize = fileSize;
    }

    public Long getFileSize() 
    {
        return fileSize;
    }
    public void setFileType(String fileType) 
    {
        this.fileType = fileType;
    }

    public String getFileType() 
    {
        return fileType;
    }
    public void setFileCategory(String fileCategory) 
    {
        this.fileCategory = fileCategory;
    }

    public String getFileCategory() 
    {
        return fileCategory;
    }
    public void setModuleName(String moduleName) 
    {
        this.moduleName = moduleName;
    }

    public String getModuleName() 
    {
        return moduleName;
    }
    public void setModuleCode(String moduleCode) 
    {
        this.moduleCode = moduleCode;
    }

    public String getModuleCode() 
    {
        return moduleCode;
    }
    public void setRelatedId(Long relatedId) 
    {
        this.relatedId = relatedId;
    }

    public Long getRelatedId() 
    {
        return relatedId;
    }
    public void setRelatedUrl(String relatedUrl) 
    {
        this.relatedUrl = relatedUrl;
    }

    public String getRelatedUrl() 
    {
        return relatedUrl;
    }
    public void setUploadTime(Date uploadTime) 
    {
        this.uploadTime = uploadTime;
    }

    public Date getUploadTime() 
    {
        return uploadTime;
    }
    public void setUploadUser(String uploadUser) 
    {
        this.uploadUser = uploadUser;
    }

    public String getUploadUser() 
    {
        return uploadUser;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    
    public void setYear(String year) 
    {
        this.year = year;
    }

    public String getYear() 
    {
        return year;
    }
    
    public void setText(String text) 
    {
        this.text = text;
    }

    public String getText() 
    {
        return text;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fileName", getFileName())
            .append("filePath", getFilePath())
            .append("fileSize", getFileSize())
            .append("fileType", getFileType())
            .append("fileCategory", getFileCategory())
            .append("moduleName", getModuleName())
            .append("moduleCode", getModuleCode())
            .append("relatedId", getRelatedId())
            .append("relatedUrl", getRelatedUrl())
            .append("uploadTime", getUploadTime())
            .append("uploadUser", getUploadUser())
            .append("status", getStatus())
            .append("year", getYear())
            .append("text", getText())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
