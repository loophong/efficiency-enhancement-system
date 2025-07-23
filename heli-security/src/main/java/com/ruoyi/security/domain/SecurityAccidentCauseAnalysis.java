package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 事故原因分析记录对象 security_accident_cause_analysis
 * 
 * @author wang
 * @date 2025-07-21
 */
public class SecurityAccidentCauseAnalysis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 上传人 */
    @Excel(name = "上传人")
    private String people;

    /** 上传部门 */
    @Excel(name = "上传部门")
    private String uploadBumen;

    /** 文件 */
    @Excel(name = "文件")
    private String file;

    /** 描述 */
    @Excel(name = "描述")
    private String result;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setPeople(String people) 
    {
        this.people = people;
    }

    public String getPeople() 
    {
        return people;
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
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("time", getTime())
            .append("people", getPeople())
            .append("uploadBumen", getUploadBumen())
            .append("file", getFile())
            .append("result", getResult())
            .toString();
    }
}
