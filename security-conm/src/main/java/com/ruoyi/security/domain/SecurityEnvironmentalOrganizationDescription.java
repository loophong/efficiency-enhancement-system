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
 * @author wang
 * @date 2025-03-05
 */
public class SecurityEnvironmentalOrganizationDescription extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
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

    /** 审批状态 */
    private String approvalStatus;

    /** 审批科室 */
    private String approvalDept;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approvalTime;

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
    public void setApprovalStatus(String approvalStatus)
    {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus()
    {
        return approvalStatus;
    }
    public void setApprovalDept(String approvalDept)
    {
        this.approvalDept = approvalDept;
    }

    public String getApprovalDept()
    {
        return approvalDept;
    }
    public void setApprovalTime(Date approvalTime)
    {
        this.approvalTime = approvalTime;
    }

    public Date getApprovalTime()
    {
        return approvalTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("environment", getEnvironment())
                .append("features", getFeatures())
                .append("description", getDescription())
                .append("approvalStatus", getApprovalStatus())
                .append("approvalDept", getApprovalDept())
                .append("approvalTime", getApprovalTime())
                .toString();
    }
}
