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
 * @date 2025-02-20
 */
public class SecurityRequireExpectParty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 相关方类型 */
    @Excel(name = "相关方类型")
    private String partyInvolved;

    /** 需求和期望 */
    @Excel(name = "需求和期望")
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

    /** 备用字段1 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "备用字段1", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beiyongziduan1;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private String beiyongziduan2;

    /** 备用字段3 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "备用字段3", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beiyongziduan3;

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
    public void setBeiyongziduan1(Date beiyongziduan1) 
    {
        this.beiyongziduan1 = beiyongziduan1;
    }

    public Date getBeiyongziduan1() 
    {
        return beiyongziduan1;
    }
    public void setBeiyongziduan2(String beiyongziduan2) 
    {
        this.beiyongziduan2 = beiyongziduan2;
    }

    public String getBeiyongziduan2() 
    {
        return beiyongziduan2;
    }
    public void setBeiyongziduan3(Date beiyongziduan3) 
    {
        this.beiyongziduan3 = beiyongziduan3;
    }

    public Date getBeiyongziduan3() 
    {
        return beiyongziduan3;
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
            .append("beiyongziduan1", getBeiyongziduan1())
            .append("beiyongziduan2", getBeiyongziduan2())
            .append("beiyongziduan3", getBeiyongziduan3())
            .toString();
    }
}
