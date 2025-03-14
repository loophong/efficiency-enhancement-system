package com.heli.supplier.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 重要度对象 supplier_importance
 * 
 * @author wll
 * @date 2025-03-07
 */
public class SupplierImportance extends BaseEntity
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

    /** 供货金额 */
    @Excel(name = "供货金额")
    private Long amount;

    /** 采购金额占比 */
    @Excel(name = "采购金额占比")
    private String percentage;

    /** 得分 */
    @Excel(name = "得分")
    private Long score;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadTime;

    /** 备选1 */
    @Excel(name = "备选1")
    private String one;

    /** 备选2 */
    @Excel(name = "备选2")
    private String two;

    /** 备选3 */
    @Excel(name = "备选3")
    private String three;

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
    public void setAmount(Long amount) 
    {
        this.amount = amount;
    }

    public Long getAmount() 
    {
        return amount;
    }
    public void setPercentage(String percentage) 
    {
        this.percentage = percentage;
    }

    public String getPercentage() 
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
    public void setOne(String one) 
    {
        this.one = one;
    }

    public String getOne() 
    {
        return one;
    }
    public void setTwo(String two) 
    {
        this.two = two;
    }

    public String getTwo() 
    {
        return two;
    }
    public void setThree(String three) 
    {
        this.three = three;
    }

    public String getThree() 
    {
        return three;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("supplierCode", getSupplierCode())
            .append("supplierName", getSupplierName())
            .append("amount", getAmount())
            .append("percentage", getPercentage())
            .append("score", getScore())
            .append("uploadTime", getUploadTime())
            .append("one", getOne())
            .append("two", getTwo())
            .append("three", getThree())
            .toString();
    }
}
