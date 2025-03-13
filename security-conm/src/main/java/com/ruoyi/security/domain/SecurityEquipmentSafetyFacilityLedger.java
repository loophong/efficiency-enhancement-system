package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全防护设备设施对象 security_equipment_safety_facility_ledger
 * 
 * @author wang
 * @date 2025-03-13
 */
public class SecurityEquipmentSafetyFacilityLedger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 设施类别 */
    @Excel(name = "设施类别")
    private String facilityCategory;

    /** 安全设施名称 */
    @Excel(name = "安全设施名称")
    private String safetyFacilityName;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 校验周期 */
    @Excel(name = "校验周期")
    private String inspectionCycle;

    /** 下次校验时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下次校验时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextInspectionDate;

    /** 责任单位 */
    @Excel(name = "责任单位")
    private String responsibleUnit;

    /** 位置 */
    @Excel(name = "位置")
    private String location;

    /** 负责人 */
    @Excel(name = "负责人")
    private String personInCharge;

    /** 现状 */
    @Excel(name = "现状")
    private String currentStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFacilityCategory(String facilityCategory) 
    {
        this.facilityCategory = facilityCategory;
    }

    public String getFacilityCategory() 
    {
        return facilityCategory;
    }
    public void setSafetyFacilityName(String safetyFacilityName) 
    {
        this.safetyFacilityName = safetyFacilityName;
    }

    public String getSafetyFacilityName() 
    {
        return safetyFacilityName;
    }
    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }
    public void setInspectionCycle(String inspectionCycle) 
    {
        this.inspectionCycle = inspectionCycle;
    }

    public String getInspectionCycle() 
    {
        return inspectionCycle;
    }
    public void setNextInspectionDate(Date nextInspectionDate) 
    {
        this.nextInspectionDate = nextInspectionDate;
    }

    public Date getNextInspectionDate() 
    {
        return nextInspectionDate;
    }
    public void setResponsibleUnit(String responsibleUnit) 
    {
        this.responsibleUnit = responsibleUnit;
    }

    public String getResponsibleUnit() 
    {
        return responsibleUnit;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setPersonInCharge(String personInCharge) 
    {
        this.personInCharge = personInCharge;
    }

    public String getPersonInCharge() 
    {
        return personInCharge;
    }
    public void setCurrentStatus(String currentStatus) 
    {
        this.currentStatus = currentStatus;
    }

    public String getCurrentStatus() 
    {
        return currentStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("facilityCategory", getFacilityCategory())
            .append("safetyFacilityName", getSafetyFacilityName())
            .append("quantity", getQuantity())
            .append("inspectionCycle", getInspectionCycle())
            .append("nextInspectionDate", getNextInspectionDate())
            .append("responsibleUnit", getResponsibleUnit())
            .append("location", getLocation())
            .append("personInCharge", getPersonInCharge())
            .append("currentStatus", getCurrentStatus())
            .toString();
    }
}
