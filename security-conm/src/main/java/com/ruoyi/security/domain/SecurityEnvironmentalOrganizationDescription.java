package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 环境识别对象 Security_environmental_organization_description
 * 
 */
public class SecurityEnvironmentalOrganizationDescription extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
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
    
    /** 父ID */
    private Long parentId;

    /** 关联ID */
    private Long relatedId;

    /** 子项列表 */
    private java.util.List<SecurityEnvironmentalOrganizationDescription> children;

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
    
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    
    public void setRelatedId(Long relatedId) 
    {
        this.relatedId = relatedId;
    }

    public Long getRelatedId() 
    {
        return relatedId;
    }
    
    public java.util.List<SecurityEnvironmentalOrganizationDescription> getChildren() 
    {
        return children;
    }

    public void setChildren(java.util.List<SecurityEnvironmentalOrganizationDescription> children) 
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("environment", getEnvironment())
            .append("features", getFeatures())
            .append("description", getDescription())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("parentId", getParentId())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
