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
 * 价格诚信对象 supplier_price_trust
 * 
 * @author wll
 * @date 2025-02-28
 */
@Data
@TableName(value = "supplier_price_trust")
public class SupplierPriceTrust implements Serializable
{
    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private String id;

    /** 供应商代码 */
    @Excel(name = "供应商代码")
    @ExcelProperty(value = "供应商代码")
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    @ExcelProperty(value = "供应商名称")
    private String supplierName;

    /** 发票号 */
    @Excel(name = "发票号")
    @ExcelProperty(value = "发票号")
    private String invoiceNumber;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "日期")
    private Date time;

    /** 具体内容 */
    @Excel(name = "具体内容")
    @ExcelProperty(value = "具体内容")
    private String specificContent;

    /** 发生次数 */
    @Excel(name = "价格诚信")
    @ExcelProperty(value = "价格诚信")
    private Long happenNumber;

    /** 得分 */
    @Excel(name = "得分")
    @ExcelProperty(value = "得分")
    private Double score;

    /** 填报人 */
    @Excel(name = "填报人")
    @ExcelProperty(value = "填报人")
    private String uploadName;

    /** 备选 */
    @ExcelIgnore
    @Excel(name = "备选")
    @ExcelProperty(value = "备选")
    private String one;
}
