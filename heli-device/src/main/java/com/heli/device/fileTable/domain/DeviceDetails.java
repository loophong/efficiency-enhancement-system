package com.heli.device.fileTable.domain;

import java.io.Serializable;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.alibaba.excel.annotation.ExcelProperty;


/**
 * 设备台账对象 device_details
 *
 * @author YYY
 * @date 2025-01-19
 */
@TableName(value ="device_details")
@Data
public class DeviceDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ExcelIgnore
    private String detailsId;

    /** 资本化日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "资本化日期")
    private String financialDate;

    /** 存货号 */
    @ExcelProperty(value = "存货号")
    private String inventoryNum;

    /** 固定资产名称 */
    @ExcelProperty(value = "固定资产名称")
    private String fixedAssetName;

    /** 班组 */
    @ExcelProperty(value = "班组")
    private String detailsGroup;

    /** 设备状态 */
    @ExcelProperty(value = "设备状态")
    private String deviceStatus;

    /** 设备类别 */
    @ExcelProperty(value = "设备类别")
    private String deviceType;

    /** 重点设备标注 */
    @ExcelProperty(value = "重点设备标注")
    private String ifKey;

    /** 保管使用单位 */
    @ExcelProperty(value = "保管使用单位")
    private String storageUnit;

    /** 责任成本中心 */
    @ExcelProperty(value = "责任成本中心")
    private String costCenter;

    /** 使用年限 */
    @ExcelProperty(value = "使用年限(月)")
    private String usedYear;

    /** 资产原值 */
    @ExcelProperty(value = "资产原值")
    private String assetOrigin;

    /** 吨位 */
    @ExcelProperty(value = "吨位")
    private String tonnage;

    /** 品牌 */
    @ExcelProperty(value = "品牌")
    private String brand;

    /** 故障次数 */
    @ExcelIgnore
    private String faultCount;



    public void setDetailsId(String detailsId)
    {
        this.detailsId = detailsId;
    }

    public String getDetailsId()
    {
        return detailsId;
    }
    public void setFinancialDate(String financialDate)
    {
        this.financialDate = financialDate;
    }

    public String getFinancialDate()
    {
        return financialDate;
    }
    public void setInventoryNum(String inventoryNum)
    {
        this.inventoryNum = inventoryNum;
    }

    public String getInventoryNum()
    {
        return inventoryNum;
    }
    public void setFixedAssetName(String fixedAssetName)
    {
        this.fixedAssetName = fixedAssetName;
    }

    public String getFixedAssetName()
    {
        return fixedAssetName;
    }
    public void setDetailsGroup(String detailsGroup) {this.detailsGroup = detailsGroup;}

    public String getDetailsGroup()
    {
        return detailsGroup;
    }
    public void setDeviceStatus(String deviceStatus)
    {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceStatus()
    {
        return deviceStatus;
    }
    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType()
    {
        return deviceType;
    }
    public void setIfKey(String ifKey)
    {
        this.ifKey = ifKey;
    }

    public String getIfKey()
    {
        return ifKey;
    }
    public void setStorageUnit(String storageUnit)
    {
        this.storageUnit = storageUnit;
    }

    public String getStorageUnit()
    {
        return storageUnit;
    }
    public void setCostCenter(String costCenter)
    {
        this.costCenter = costCenter;
    }

    public String getCostCenter()
    {
        return costCenter;
    }
    public void setUsedYear(String usedYear)
    {
        this.usedYear = usedYear;
    }

    public String getUsedYear()
    {
        return usedYear;
    }
    public void setAssetOrigin(String assetOrigin)
    {
        this.assetOrigin = assetOrigin;
    }

    public String getAssetOrigin()
    {
        return assetOrigin;
    }
    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getBrand()
    {
        return brand;
    }
    public void setTonnage(String tonnage)
    {
        this.tonnage = tonnage;
    }

    public String getTonnage()
    {
        return tonnage;
    }

    public void setFaultCount(String faultCount)
    {
        this.faultCount = faultCount;
    }

    public String getFaultCount()
    {
        return faultCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailsId", getDetailsId())
            .append("financialDate", getFinancialDate())
            .append("inventoryNum", getInventoryNum())
            .append("fixedAssetName", getFixedAssetName())
            .append("detailsGroup", getDetailsGroup())
            .append("deviceStatus", getDeviceStatus())
            .append("deviceType", getDeviceType())
            .append("ifKey", getIfKey())
            .append("storageUnit", getStorageUnit())
            .append("costCenter", getCostCenter())
            .append("usedYear", getUsedYear())
            .append("assetOrigin", getAssetOrigin())
            .append("brand", getBrand())
            .append("tonnage", getTonnage())
            .append("faultCount", getFaultCount())
            .toString();
    }
}
