package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 纠正与预防措施审批实施对象 security_corrective_preventive_measures_approval
 * 
 * @author wang
 * @date 2025-03-07
 */
public class SecurityCorrectivePreventiveMeasuresApproval extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 责任科室 */
    @Excel(name = "责任科室")
    private String responsibleUnit;

    /** 参加单位 */
    @Excel(name = "参加单位")
    private String participatingUnits;

    /** 问题描述 */
    @Excel(name = "问题描述")
    private String nonConformanceDescription;

    /** 原因分析 */
    @Excel(name = "原因分析")
    private String causeAnalysis;

    /** 负责人 */
    @Excel(name = "负责人")
    private String responsiblePerson;

    /** 签字时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签字时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 恢复计划 */
    @Excel(name = "恢复计划")
    private String correctivePreventivePlan;

    /** 责任单位主管 */
    @Excel(name = "责任单位主管")
    private String responsibleUnitSupervisor;

    /** 管理者代表审批 */
    @Excel(name = "管理者代表审批")
    private Integer managerRepresentativeApproval;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planTime;

    /** 完成情况 */
    @Excel(name = "完成情况")
    private String implementationStatus;

    /** 负责人签字 */
    @Excel(name = "负责人签字")
    private String responsibleDepartmentHead;

    /** 签字时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签字时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completionTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setResponsibleUnit(String responsibleUnit) 
    {
        this.responsibleUnit = responsibleUnit;
    }

    public String getResponsibleUnit() 
    {
        return responsibleUnit;
    }
    public void setParticipatingUnits(String participatingUnits) 
    {
        this.participatingUnits = participatingUnits;
    }

    public String getParticipatingUnits() 
    {
        return participatingUnits;
    }
    public void setNonConformanceDescription(String nonConformanceDescription) 
    {
        this.nonConformanceDescription = nonConformanceDescription;
    }

    public String getNonConformanceDescription() 
    {
        return nonConformanceDescription;
    }
    public void setCauseAnalysis(String causeAnalysis) 
    {
        this.causeAnalysis = causeAnalysis;
    }

    public String getCauseAnalysis() 
    {
        return causeAnalysis;
    }
    public void setResponsiblePerson(String responsiblePerson) 
    {
        this.responsiblePerson = responsiblePerson;
    }

    public String getResponsiblePerson() 
    {
        return responsiblePerson;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setCorrectivePreventivePlan(String correctivePreventivePlan) 
    {
        this.correctivePreventivePlan = correctivePreventivePlan;
    }

    public String getCorrectivePreventivePlan() 
    {
        return correctivePreventivePlan;
    }
    public void setResponsibleUnitSupervisor(String responsibleUnitSupervisor) 
    {
        this.responsibleUnitSupervisor = responsibleUnitSupervisor;
    }

    public String getResponsibleUnitSupervisor() 
    {
        return responsibleUnitSupervisor;
    }
    public void setManagerRepresentativeApproval(Integer managerRepresentativeApproval) 
    {
        this.managerRepresentativeApproval = managerRepresentativeApproval;
    }

    public Integer getManagerRepresentativeApproval() 
    {
        return managerRepresentativeApproval;
    }
    public void setPlanTime(Date planTime) 
    {
        this.planTime = planTime;
    }

    public Date getPlanTime() 
    {
        return planTime;
    }
    public void setImplementationStatus(String implementationStatus) 
    {
        this.implementationStatus = implementationStatus;
    }

    public String getImplementationStatus() 
    {
        return implementationStatus;
    }
    public void setResponsibleDepartmentHead(String responsibleDepartmentHead) 
    {
        this.responsibleDepartmentHead = responsibleDepartmentHead;
    }

    public String getResponsibleDepartmentHead() 
    {
        return responsibleDepartmentHead;
    }
    public void setCompletionTime(Date completionTime) 
    {
        this.completionTime = completionTime;
    }

    public Date getCompletionTime() 
    {
        return completionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("responsibleUnit", getResponsibleUnit())
            .append("participatingUnits", getParticipatingUnits())
            .append("nonConformanceDescription", getNonConformanceDescription())
            .append("causeAnalysis", getCauseAnalysis())
            .append("responsiblePerson", getResponsiblePerson())
            .append("time", getTime())
            .append("correctivePreventivePlan", getCorrectivePreventivePlan())
            .append("responsibleUnitSupervisor", getResponsibleUnitSupervisor())
            .append("managerRepresentativeApproval", getManagerRepresentativeApproval())
            .append("planTime", getPlanTime())
            .append("implementationStatus", getImplementationStatus())
            .append("responsibleDepartmentHead", getResponsibleDepartmentHead())
            .append("completionTime", getCompletionTime())
            .toString();
    }
}
