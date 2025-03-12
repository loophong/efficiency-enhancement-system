package com.heli.supplier.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 二方审核得分对象 supplier_two_review_score
 * 
 * @author wll
 * @date 2025-03-06
 */
public class SupplierTwoReviewScore extends BaseEntity
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

    /** 不符合项1 */
    @Excel(name = "不符合项1")
    private String notTrue1;

    /** 不符合项2 */
    @Excel(name = "不符合项2")
    private String notTrue2;

    /** 不符合项3 */
    @Excel(name = "不符合项3")
    private String notTrue3;

    /** 不符合项4 */
    @Excel(name = "不符合项4")
    private String notTrue4;

    /** 不符合项5 */
    @Excel(name = "不符合项5")
    private String notTrue5;

    /** 得分 */
    @Excel(name = "得分")
    private Long score;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

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
    public void setNotTrue1(String notTrue1) 
    {
        this.notTrue1 = notTrue1;
    }

    public String getNotTrue1() 
    {
        return notTrue1;
    }
    public void setNotTrue2(String notTrue2) 
    {
        this.notTrue2 = notTrue2;
    }

    public String getNotTrue2() 
    {
        return notTrue2;
    }
    public void setNotTrue3(String notTrue3) 
    {
        this.notTrue3 = notTrue3;
    }

    public String getNotTrue3() 
    {
        return notTrue3;
    }
    public void setNotTrue4(String notTrue4) 
    {
        this.notTrue4 = notTrue4;
    }

    public String getNotTrue4() 
    {
        return notTrue4;
    }
    public void setNotTrue5(String notTrue5) 
    {
        this.notTrue5 = notTrue5;
    }

    public String getNotTrue5() 
    {
        return notTrue5;
    }
    public void setScore(Long score) 
    {
        this.score = score;
    }

    public Long getScore() 
    {
        return score;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
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
            .append("notTrue1", getNotTrue1())
            .append("notTrue2", getNotTrue2())
            .append("notTrue3", getNotTrue3())
            .append("notTrue4", getNotTrue4())
            .append("notTrue5", getNotTrue5())
            .append("score", getScore())
            .append("time", getTime())
            .append("uploadName", getUploadName())
            .toString();
    }
}
