package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 风险网格化清单对象 security_danger_wang_list
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
public class SecurityDangerWangList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadDate;

    /** 上传人 */
    @Excel(name = "上传人")
    private String uploadBy;

    /** 上传部门 */
    @Excel(name = "上传部门")
    private String uploadDept;

    /** 文件 */
    @Excel(name = "文件")
    private String files;

    /** 描述 */
    @Excel(name = "描述")
    private String comment;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setUploadDate(Date uploadDate) 
    {
        this.uploadDate = uploadDate;
    }

    public Date getUploadDate() 
    {
        return uploadDate;
    }
    public void setUploadBy(String uploadBy) 
    {
        this.uploadBy = uploadBy;
    }

    public String getUploadBy() 
    {
        return uploadBy;
    }
    public void setUploadDept(String uploadDept) 
    {
        this.uploadDept = uploadDept;
    }

    public String getUploadDept() 
    {
        return uploadDept;
    }
    public void setFiles(String files) 
    {
        this.files = files;
    }

    public String getFiles() 
    {
        return files;
    }
    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public String getComment() 
    {
        return comment;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("uploadDate", getUploadDate())
            .append("uploadBy", getUploadBy())
            .append("uploadDept", getUploadDept())
            .append("files", getFiles())
            .append("comment", getComment())
            .toString();
    }
}
