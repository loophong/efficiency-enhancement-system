package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 特种设备台账对象 security_special_operations_certificate_ledger
 * 
 * @author wang
 * @date 2025-03-06
 */
public class SecuritySpecialOperationsCertificateLedger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备类别 */
    @Excel(name = "设备类别")
    private String equipmentCategory;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String equipmentModel;

    /** 内部编号 */
    @Excel(name = "内部编号")
    private String internalNumber;

    /** 使用证号 */
    @Excel(name = "使用证号")
    private String usageCertificateNumber;

    /** 发证日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发证日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issuanceDate;

    /** 制造单位 */
    @Excel(name = "制造单位")
    private String manufacturingUnit;

    /** 检验日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionDate;

    /** 下次检验日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下次检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextInspectionDate;

    /** 是否停用 */
    @Excel(name = "是否停用")
    private String isDisabled;

    /** 特征设备类别 */
    @Excel(name = "特征设备类别")
    private String specialEquipememtCategory;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }
    public void setEquipmentCategory(String equipmentCategory) 
    {
        this.equipmentCategory = equipmentCategory;
    }

    public String getEquipmentCategory() 
    {
        return equipmentCategory;
    }
    public void setEquipmentModel(String equipmentModel) 
    {
        this.equipmentModel = equipmentModel;
    }

    public String getEquipmentModel() 
    {
        return equipmentModel;
    }
    public void setInternalNumber(String internalNumber) 
    {
        this.internalNumber = internalNumber;
    }

    public String getInternalNumber() 
    {
        return internalNumber;
    }
    public void setUsageCertificateNumber(String usageCertificateNumber) 
    {
        this.usageCertificateNumber = usageCertificateNumber;
    }

    public String getUsageCertificateNumber() 
    {
        return usageCertificateNumber;
    }
    public void setIssuanceDate(Date issuanceDate) 
    {
        this.issuanceDate = issuanceDate;
    }

    public Date getIssuanceDate() 
    {
        return issuanceDate;
    }
    public void setManufacturingUnit(String manufacturingUnit) 
    {
        this.manufacturingUnit = manufacturingUnit;
    }

    public String getManufacturingUnit() 
    {
        return manufacturingUnit;
    }
    public void setInspectionDate(Date inspectionDate) 
    {
        this.inspectionDate = inspectionDate;
    }

    public Date getInspectionDate() 
    {
        return inspectionDate;
    }
    public void setNextInspectionDate(Date nextInspectionDate) 
    {
        this.nextInspectionDate = nextInspectionDate;
    }

    public Date getNextInspectionDate() 
    {
        return nextInspectionDate;
    }
    public void setIsDisabled(String isDisabled) 
    {
        this.isDisabled = isDisabled;
    }

    public String getIsDisabled() 
    {
        return isDisabled;
    }
    public void setSpecialEquipememtCategory(String specialEquipememtCategory) 
    {
        this.specialEquipememtCategory = specialEquipememtCategory;
    }

    public String getSpecialEquipememtCategory() 
    {
        return specialEquipememtCategory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitName", getUnitName())
            .append("equipmentName", getEquipmentName())
            .append("equipmentCategory", getEquipmentCategory())
            .append("equipmentModel", getEquipmentModel())
            .append("internalNumber", getInternalNumber())
            .append("usageCertificateNumber", getUsageCertificateNumber())
            .append("issuanceDate", getIssuanceDate())
            .append("manufacturingUnit", getManufacturingUnit())
            .append("inspectionDate", getInspectionDate())
            .append("nextInspectionDate", getNextInspectionDate())
            .append("isDisabled", getIsDisabled())
            .append("specialEquipememtCategory", getSpecialEquipememtCategory())
            .toString();
    }
}
