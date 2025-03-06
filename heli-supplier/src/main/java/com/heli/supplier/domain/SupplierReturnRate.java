package com.heli.supplier.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 售后返修率对象 supplier_return_rate
 * 
 * @author wll
 * @date 2025-02-27
 */
@Data
@TableName(value = "supplier_return_rate")
public class SupplierReturnRate implements Serializable
{
    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private String id;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    @ExcelProperty(value = "供应商编码")
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    @ExcelProperty(value = "供应商名称")
    private String supplierName;

    /** 售后返修率 */
    @Excel(name = "售后返修率")
    @ExcelProperty(value = "售后返修率")
    private String returnRate;

    /** 月份 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "月份", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "月份")
    private Date month;

}
