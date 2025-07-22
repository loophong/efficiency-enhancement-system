package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 变更管理对象 security_change_list
 * 
 * @author wnag
 * @date 2025-07-21
 */
public class SecurityChangeList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadTime;

    /** 上传人 */
    @Excel(name = "上传人")
    private String uploadPeople;

    /** 上传部门 */
    @Excel(name = "上传部门")
    private String uploadBumen;

    /** 文件 */
    @Excel(name = "文件")
    private String file;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUploadTime(Date uploadTime) 
    {
        this.uploadTime = uploadTime;
    }

    public Date getUploadTime() 
    {
        return uploadTime;
    }
    public void setUploadPeople(String uploadPeople) 
    {
        this.uploadPeople = uploadPeople;
    }

    public String getUploadPeople() 
    {
        return uploadPeople;
    }
    public void setUploadBumen(String uploadBumen) 
    {
        this.uploadBumen = uploadBumen;
    }

    public String getUploadBumen() 
    {
        return uploadBumen;
    }
    public void setFile(String file) 
    {
        this.file = file;
    }

    public String getFile() 
    {
        return file;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("uploadTime", getUploadTime())
            .append("uploadPeople", getUploadPeople())
            .append("uploadBumen", getUploadBumen())
            .append("file", getFile())
            .append("remark", getRemark())
            .toString();
    }
}
