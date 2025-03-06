package com.heli.supplier.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 降本支持对象 supplier_reduce_support
 * 
 * @author wll
 * @date 2025-03-06
 */
public class SupplierReduceSupport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 供应商代码 */
    @Excel(name = "供应商代码")
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 单价 */
    @Excel(name = "单价")
    private Long singlePrice;

    /** 降本金额 */
    @Excel(name = "降本金额")
    private Long reduceMoney;

    /** 总采购量 */
    @Excel(name = "总采购量")
    private Long totalPurchases;

    /** 供货金额=单价*总采购量 */
    @Excel(name = "供货金额=单价*总采购量")
    private Long supplyAmount;

    /** 降本占比 */
    @Excel(name = "降本占比")
    private BigDecimal percentage;

    /** 得分 */
    @Excel(name = "得分")
    private Long score;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadTime;

    /** 填报人 */
    @Excel(name = "填报人")
    private String uploadName;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setSupplierCode(String supplierCode) 
    {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() 
    {
        return supplierCode;
    }
    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
    }
    public void setSinglePrice(Long singlePrice) 
    {
        this.singlePrice = singlePrice;
    }

    public Long getSinglePrice() 
    {
        return singlePrice;
    }
    public void setReduceMoney(Long reduceMoney) 
    {
        this.reduceMoney = reduceMoney;
    }

    public Long getReduceMoney() 
    {
        return reduceMoney;
    }
    public void setTotalPurchases(Long totalPurchases) 
    {
        this.totalPurchases = totalPurchases;
    }

    public Long getTotalPurchases() 
    {
        return totalPurchases;
    }
    public void setSupplyAmount(Long supplyAmount) 
    {
        this.supplyAmount = supplyAmount;
    }

    public Long getSupplyAmount() 
    {
        return supplyAmount;
    }
    public void setPercentage(BigDecimal percentage) 
    {
        this.percentage = percentage;
    }

    public BigDecimal getPercentage() 
    {
        return percentage;
    }
    public void setScore(Long score) 
    {
        this.score = score;
    }

    public Long getScore() 
    {
        return score;
    }
    public void setUploadTime(Date uploadTime) 
    {
        this.uploadTime = uploadTime;
    }

    public Date getUploadTime() 
    {
        return uploadTime;
    }
    public void setUploadName(String uploadName) 
    {
        this.uploadName = uploadName;
    }

    public String getUploadName() 
    {
        return uploadName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("supplierCode", getSupplierCode())
            .append("supplierName", getSupplierName())
            .append("singlePrice", getSinglePrice())
            .append("reduceMoney", getReduceMoney())
            .append("totalPurchases", getTotalPurchases())
            .append("supplyAmount", getSupplyAmount())
            .append("percentage", getPercentage())
            .append("score", getScore())
            .append("uploadTime", getUploadTime())
            .append("uploadName", getUploadName())
            .toString();
    }
}
