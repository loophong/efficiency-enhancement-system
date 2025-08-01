package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 不可接受风险清单对象 security_unacceptable_risk_list
 * 
 * @author wang
 * @date 2025-03-26
 */
public class SecurityUnacceptableRiskList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 危险源点
 */
    @Excel(name = "危险源点", needMerge = true)
    private String hazardSource;

    /** 重要危险源 */
    @Excel(name = "重要危险源", needMerge = true)
    private String significantHazard;

    /** 导致事故健康危害 */
    @Excel(name = "导致事故健康危害", needMerge = true)
    private String accidentHealthHazards;

    /** 工序/ 设备/人员 */
    @Excel(name = "工序/ 设备/人员", needMerge = true)
    private String processEquipmentPersonnel;

    /** 控制方法 */
    @Excel(name = "控制方法", needMerge = true)
    private String controlMethods;

    /** 部门/工序 */
    @Excel(name = "部门/工序", needMerge = true)
    private String departmentProcess;

    /** 审批状态 */
//    @Excel(name = "审批状态")
    private String statu;
    
    /** 关联ID */
    private Long relatedId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHazardSource(String hazardSource) 
    {
        this.hazardSource = hazardSource;
    }

    public String getHazardSource() 
    {
        return hazardSource;
    }
    public void setSignificantHazard(String significantHazard) 
    {
        this.significantHazard = significantHazard;
    }

    public String getSignificantHazard() 
    {
        return significantHazard;
    }
    public void setAccidentHealthHazards(String accidentHealthHazards) 
    {
        this.accidentHealthHazards = accidentHealthHazards;
    }

    public String getAccidentHealthHazards() 
    {
        return accidentHealthHazards;
    }
    public void setProcessEquipmentPersonnel(String processEquipmentPersonnel) 
    {
        this.processEquipmentPersonnel = processEquipmentPersonnel;
    }

    public String getProcessEquipmentPersonnel() 
    {
        return processEquipmentPersonnel;
    }
    public void setControlMethods(String controlMethods) 
    {
        this.controlMethods = controlMethods;
    }

    public String getControlMethods() 
    {
        return controlMethods;
    }
    public void setDepartmentProcess(String departmentProcess) 
    {
        this.departmentProcess = departmentProcess;
    }

    public String getDepartmentProcess() 
    {
        return departmentProcess;
    }
    public void setStatu(String statu) 
    {
        this.statu = statu;
    }

    public String getStatu() 
    {
        return statu;
    }
    
    public void setRelatedId(Long relatedId) 
    {
        this.relatedId = relatedId;
    }

    public Long getRelatedId() 
    {
        return relatedId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("hazardSource", getHazardSource())
            .append("significantHazard", getSignificantHazard())
            .append("accidentHealthHazards", getAccidentHealthHazards())
            .append("processEquipmentPersonnel", getProcessEquipmentPersonnel())
            .append("controlMethods", getControlMethods())
            .append("departmentProcess", getDepartmentProcess())
            .append("statu", getStatu())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
