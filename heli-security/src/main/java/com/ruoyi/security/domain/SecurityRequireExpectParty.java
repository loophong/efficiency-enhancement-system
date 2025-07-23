package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 相关方期望对象 security_require_expect_party
 *
 * @author wang
 * @date 2025-03-11
 */
public class SecurityRequireExpectParty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 相关方类型 */
    @Excel(name = "相关方类型", needMerge = true)
    private String partyInvolved;

    /** 需求和期望 */
    @Excel(name = "需求和期望", needMerge = true)
    private String demand;

    /** 监测指标 */
    @Excel(name = "监测指标")
    private String monitoringIndicators;

    /** 监测频率 */
    @Excel(name = "监测频率")
    private String monitoringFrequency;

    /** 监测部门 */
    @Excel(name = "监测部门")
    private String monitoringDepartment;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd", isExport = false)
    private Date approvalTime;

    /** 填报时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "填报时间", width = 30, dateFormat = "yyyy-MM-dd", isExport = false)
    private Date fillTime;

    /** 审批状态 */
    @Excel(name = "审批状态", isExport = false)
    private String status;

    /** 审批部门 */
    @Excel(name = "审批部门", isExport = false)
    private String statusDepartment;
    @Excel(name = "文件管理ID", isExport = false)
    private Long relatedId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setPartyInvolved(String partyInvolved)
    {
        this.partyInvolved = partyInvolved;
    }

    public String getPartyInvolved()
    {
        return partyInvolved;
    }
    public void setDemand(String demand)
    {
        this.demand = demand;
    }

    public String getDemand()
    {
        return demand;
    }
    public void setMonitoringIndicators(String monitoringIndicators)
    {
        this.monitoringIndicators = monitoringIndicators;
    }

    public String getMonitoringIndicators()
    {
        return monitoringIndicators;
    }
    public void setMonitoringFrequency(String monitoringFrequency)
    {
        this.monitoringFrequency = monitoringFrequency;
    }

    public String getMonitoringFrequency()
    {
        return monitoringFrequency;
    }
    public void setMonitoringDepartment(String monitoringDepartment)
    {
        this.monitoringDepartment = monitoringDepartment;
    }

    public String getMonitoringDepartment()
    {
        return monitoringDepartment;
    }
    public void setApprovalTime(Date approvalTime)
    {
        this.approvalTime = approvalTime;
    }

    public Date getApprovalTime()
    {
        return approvalTime;
    }
    public void setFillTime(Date fillTime)
    {
        this.fillTime = fillTime;
    }

    public Date getFillTime()
    {
        return fillTime;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
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

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("partyInvolved", getPartyInvolved())
                .append("demand", getDemand())
                .append("monitoringIndicators", getMonitoringIndicators())
                .append("monitoringFrequency", getMonitoringFrequency())
                .append("monitoringDepartment", getMonitoringDepartment())
                .append("approvalTime", getApprovalTime())
                .append("fillTime", getFillTime())
                .append("status", getStatus())
                .append("statusDepartment", getStatusDepartment())
                .toString();
    }
}
