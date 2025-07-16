package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 风险和机遇评估及控制措施对象 security_risk_opportunity_assessment
 * 
 * @author wang
 * @date 2025-03-11
 */
public class SecurityRiskOpportunityAssessment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 活动/过程 */
    @Excel(name = "活动/过程", sort = 1)
    private String activity;

    /** 风险和机遇 */
    @Excel(name = "风险和机遇", sort = 2)
    private String riskOpportunity;

    /** 造成后果 */
    @Excel(name = "造成后果", sort = 3)
    private String consequences;

    /** 风险等级 */
    @Excel(name = "风险等级", sort = 4, readConverterExp = "高=高,中=一般,低=低")
    private String riskLevel;

    /** 应对措施 */
    @Excel(name = "应对措施", sort = 5)
    private String responseMeasures;

    /** 实施时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实施时间", width = 30, dateFormat = "yyyy-MM-dd", sort = 6)
    private Date implementationTime;

    /** 实施部门 */
    @Excel(name = "实施部门", sort = 7)
    private String department;

    /** 严重程度 */
    @Excel(name = "严重程度", sort = 8)
    private String severity;

    /** 发生频次 */
    @Excel(name = "发生频次", sort = 9)
    private String frequency;

    /** 风险系数 */
    @Excel(name = "风险系数", isExport = false)
    private String risk;

    /** 审批状态 */
    @Excel(name = "审批状态", isExport = false)
    private Long status;

    /** 审批部门 */
    @Excel(name = "审批部门", isExport = false)
    private String statusDepartment;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd", isExport = false)
    private Date statusTime;
    /**关联ID**/
    @Excel(name = "关联ID", isExport = false)
    private Long relatedId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setActivity(String activity) 
    {
        this.activity = activity;
    }

    public String getActivity() 
    {
        return activity;
    }
    public void setRiskOpportunity(String riskOpportunity) 
    {
        this.riskOpportunity = riskOpportunity;
    }

    public String getRiskOpportunity() 
    {
        return riskOpportunity;
    }
    public void setConsequences(String consequences) 
    {
        this.consequences = consequences;
    }

    public String getConsequences() 
    {
        return consequences;
    }
    public void setRiskLevel(String riskLevel) 
    {
        this.riskLevel = riskLevel;
    }

    public String getRiskLevel() 
    {
        return riskLevel;
    }
    public void setResponseMeasures(String responseMeasures) 
    {
        this.responseMeasures = responseMeasures;
    }

    public String getResponseMeasures() 
    {
        return responseMeasures;
    }
    public void setImplementationTime(Date implementationTime) 
    {
        this.implementationTime = implementationTime;
    }

    public Date getImplementationTime() 
    {
        return implementationTime;
    }
    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }
    public void setSeverity(String severity) 
    {
        this.severity = severity;
    }

    public String getSeverity() 
    {
        return severity;
    }
    public void setFrequency(String frequency) 
    {
        this.frequency = frequency;
    }

    public String getFrequency() 
    {
        return frequency;
    }
    public void setRisk(String risk) 
    {
        this.risk = risk;
    }

    public String getRisk() 
    {
        return risk;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setStatusDepartment(String statusDepartment) 
    {
        this.statusDepartment = statusDepartment;
    }

    public String getStatusDepartment() 
    {
        return statusDepartment;
    }
    public void setStatusTime(Date statusTime) 
    {
        this.statusTime = statusTime;
    }

    public Date getStatusTime() 
    {
        return statusTime;
    }
    public Long getRelatedId()
    {
        return relatedId;
    }
    public void setRelatedId(Long relatedId)
    {
        this.relatedId = relatedId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activity", getActivity())
            .append("riskOpportunity", getRiskOpportunity())
            .append("consequences", getConsequences())
            .append("riskLevel", getRiskLevel())
            .append("responseMeasures", getResponseMeasures())
            .append("implementationTime", getImplementationTime())
            .append("department", getDepartment())
            .append("severity", getSeverity())
            .append("frequency", getFrequency())
            .append("risk", getRisk())
            .append("status", getStatus())
            .append("statusDepartment", getStatusDepartment())
            .append("statusTime", getStatusTime())
            .toString();
    }
}
