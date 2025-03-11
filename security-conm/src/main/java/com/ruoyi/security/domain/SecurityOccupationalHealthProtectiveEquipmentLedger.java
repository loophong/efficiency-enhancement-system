package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 职业健康防护用品台帐对象 security_occupational_health_protective_equipment_ledger
 * 
 * @author wang
 * @date 2025-03-06
 */
public class SecurityOccupationalHealthProtectiveEquipmentLedger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 盘锦合力SAP物料号 */
    @Excel(name = "盘锦合力SAP物料号")
    private String panjinHeliSapMaterialNumber;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 规格 */
    @Excel(name = "规格")
    private String specifications;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPanjinHeliSapMaterialNumber(String panjinHeliSapMaterialNumber) 
    {
        this.panjinHeliSapMaterialNumber = panjinHeliSapMaterialNumber;
    }

    public String getPanjinHeliSapMaterialNumber() 
    {
        return panjinHeliSapMaterialNumber;
    }
    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getMaterialName() 
    {
        return materialName;
    }
    public void setSpecifications(String specifications) 
    {
        this.specifications = specifications;
    }

    public String getSpecifications() 
    {
        return specifications;
    }
    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("panjinHeliSapMaterialNumber", getPanjinHeliSapMaterialNumber())
            .append("materialName", getMaterialName())
            .append("specifications", getSpecifications())
            .append("brand", getBrand())
            .append("unit", getUnit())
            .append("remarks", getRemarks())
            .toString();
    }
}
