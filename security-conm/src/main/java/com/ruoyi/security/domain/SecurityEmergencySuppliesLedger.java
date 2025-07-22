package com.ruoyi.security.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 应急物资管理台帐对象 security_emergency_supplies_ledger
 * 
 * @author wang
 * @date 2025-03-06
 */
public class SecurityEmergencySuppliesLedger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 物资名称 */
    @Excel(name = "物资名称")
    private String supplyName;

    /** 应急物资类型 */
    @Excel(name = "应急物资类型")
    private String emergencySupplyType;

    /** 物资数量 */
    @Excel(name = "物资数量")
    private BigDecimal quantity;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unitOfMeasurement;

    /** 存放位置 */
    @Excel(name = "存放位置")
    private String storageLocation;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contactPerson;

    /** 电话 */
    @Excel(name = "电话")
    private String phoneNumber;

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
    public void setSupplyName(String supplyName) 
    {
        this.supplyName = supplyName;
    }

    public String getSupplyName() 
    {
        return supplyName;
    }
    public void setEmergencySupplyType(String emergencySupplyType) 
    {
        this.emergencySupplyType = emergencySupplyType;
    }

    public String getEmergencySupplyType() 
    {
        return emergencySupplyType;
    }
    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }
    public void setUnitOfMeasurement(String unitOfMeasurement) 
    {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getUnitOfMeasurement() 
    {
        return unitOfMeasurement;
    }
    public void setStorageLocation(String storageLocation) 
    {
        this.storageLocation = storageLocation;
    }

    public String getStorageLocation() 
    {
        return storageLocation;
    }
    public void setContactPerson(String contactPerson) 
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() 
    {
        return contactPerson;
    }
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
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
            .append("supplyName", getSupplyName())
            .append("emergencySupplyType", getEmergencySupplyType())
            .append("quantity", getQuantity())
            .append("unitOfMeasurement", getUnitOfMeasurement())
            .append("storageLocation", getStorageLocation())
            .append("contactPerson", getContactPerson())
            .append("phoneNumber", getPhoneNumber())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
