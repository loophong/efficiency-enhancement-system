package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 应急演练计划对象 security_emergency_drill_plan
 * 
 * @author wang
 * @date 2025-03-06
 */
public class SecurityEmergencyDrillPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 级别 */
    @Excel(name = "级别")
    private String level;

    /** 科室/班组 */
    @Excel(name = "科室/班组")
    private String departmentTeam;

    /** 类型 */
    @Excel(name = "类型")
    private String drillType;

    /** 预计演练时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计演练时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date plannedDrillTime;

    /** 演练主题 */
    @Excel(name = "演练主题")
    private String drillTheme;

    /** 参考情景 */
    @Excel(name = "参考情景")
    private String referenceScenario;

    /** 参演及观摩人员 */
    @Excel(name = "参演及观摩人员")
    private String participantsObservers;

    /** 类型 */
    @Excel(name = "类型")
    private String drillCategory;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String statu;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setDepartmentTeam(String departmentTeam) 
    {
        this.departmentTeam = departmentTeam;
    }

    public String getDepartmentTeam() 
    {
        return departmentTeam;
    }
    public void setDrillType(String drillType) 
    {
        this.drillType = drillType;
    }

    public String getDrillType() 
    {
        return drillType;
    }
    public void setPlannedDrillTime(Date plannedDrillTime) 
    {
        this.plannedDrillTime = plannedDrillTime;
    }

    public Date getPlannedDrillTime() 
    {
        return plannedDrillTime;
    }
    public void setDrillTheme(String drillTheme) 
    {
        this.drillTheme = drillTheme;
    }

    public String getDrillTheme() 
    {
        return drillTheme;
    }
    public void setReferenceScenario(String referenceScenario) 
    {
        this.referenceScenario = referenceScenario;
    }

    public String getReferenceScenario() 
    {
        return referenceScenario;
    }
    public void setParticipantsObservers(String participantsObservers) 
    {
        this.participantsObservers = participantsObservers;
    }

    public String getParticipantsObservers() 
    {
        return participantsObservers;
    }
    public void setDrillCategory(String drillCategory) 
    {
        this.drillCategory = drillCategory;
    }

    public String getDrillCategory() 
    {
        return drillCategory;
    }
    public void setStatu(String statu) 
    {
        this.statu = statu;
    }

    public String getStatu() 
    {
        return statu;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("level", getLevel())
            .append("departmentTeam", getDepartmentTeam())
            .append("drillType", getDrillType())
            .append("plannedDrillTime", getPlannedDrillTime())
            .append("drillTheme", getDrillTheme())
            .append("referenceScenario", getReferenceScenario())
            .append("participantsObservers", getParticipantsObservers())
            .append("drillCategory", getDrillCategory())
            .append("statu", getStatu())
            .toString();
    }
}
