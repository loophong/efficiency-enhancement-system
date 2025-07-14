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
    @Excel(name = "供应商代码")
    @ExcelProperty(value = "供应商代码")
    @ExcelIgnore
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    @ExcelProperty(value = "供应商名称")
    private String supplierName;

    /** 售后返修率 */
    @Excel(name = "月度售后反馈率")
    @ExcelProperty(value = "月度售后反馈率")
    private String returnRate;

    /** 月份 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传月份", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "上传月份")
    private Date month;

    /** 备选1 */
    @Excel(name = "分数")
    @ExcelProperty(value = "分数")
    @ExcelIgnore
    private Double score;

//    /** 2 */
//    @Excel(name = "2")
//    @ExcelProperty(value = "2")
//    @ExcelIgnore
//    private String two;
//
//    /** 3 */
//    @Excel(name = "3")
//    @ExcelProperty(value = "3")
//    @ExcelIgnore
//    private String three;
//@ExcelProperty(index = 12)
//private String one;
//
//    @ExcelProperty(index = 22)
//    private String two;
//
//    @ExcelProperty(index = 32)
//    private String three;
//
//    @ExcelProperty(index = 42)
//    private String four;
//
//    @ExcelProperty(index = 52)
//    private String five;
//
//    @ExcelProperty(index = 62)
//    private String six;
//
//    @ExcelProperty(index = 72)
//    private String seven;
//
//    @ExcelProperty(index = 82)
//    private String eight;
//
//    @ExcelProperty(index = 92)
//    private String nine;
//
//    @ExcelProperty(index = 102)
//    private String ten;
//
//    @ExcelProperty(index = 112)
//    private String eleven;
//
//    @ExcelProperty(index = 122)
//    private String twelve;


}
