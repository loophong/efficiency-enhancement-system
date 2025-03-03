package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 环境识别对象 Security_environmental_organization_description
 * 
 * @author 王
 * @date 2025-01-14
 */
public class SecurityEnvironmentalOrganizationDescription extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 环境 */
    @Excel(name = "环境")
    private String environment;

    /** 环境要素 */
    @Excel(name = "环境要素")
    private String features;

    /** 环境要素描述 */
    @Excel(name = "环境要素描述")
    private String description;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String beiyongziduan1;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private Long beiyongziduan2;

    /** 备用字段3 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "备用字段3", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beiyongdiduan3;

    /** 备用字段4 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "备用字段4", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beiyongziduan4;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEnvironment(String environment) 
    {
        this.environment = environment;
    }

    public String getEnvironment() 
    {
        return environment;
    }
    public void setFeatures(String features) 
    {
        this.features = features;
    }

    public String getFeatures() 
    {
        return features;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setBeiyongziduan1(String beiyongziduan1) 
    {
        this.beiyongziduan1 = beiyongziduan1;
    }

    public String getBeiyongziduan1() 
    {
        return beiyongziduan1;
    }
    public void setBeiyongziduan2(Long beiyongziduan2) 
    {
        this.beiyongziduan2 = beiyongziduan2;
    }

    public Long getBeiyongziduan2() 
    {
        return beiyongziduan2;
    }
    public void setBeiyongdiduan3(Date beiyongdiduan3) 
    {
        this.beiyongdiduan3 = beiyongdiduan3;
    }

    public Date getBeiyongdiduan3() 
    {
        return beiyongdiduan3;
    }
    public void setBeiyongziduan4(Date beiyongziduan4) 
    {
        this.beiyongziduan4 = beiyongziduan4;
    }

    public Date getBeiyongziduan4() 
    {
        return beiyongziduan4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("environment", getEnvironment())
            .append("features", getFeatures())
            .append("description", getDescription())
            .append("beiyongziduan1", getBeiyongziduan1())
            .append("beiyongziduan2", getBeiyongziduan2())
            .append("beiyongdiduan3", getBeiyongdiduan3())
            .append("beiyongziduan4", getBeiyongziduan4())
            .toString();
    }
}
