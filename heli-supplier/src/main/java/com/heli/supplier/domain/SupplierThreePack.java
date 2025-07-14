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
 * 三包发货及时率对象 supplier_three_pack
 * 
 * @author wll
 * @date 2025-02-26
 */
@Data
@TableName(value = "supplier_three_pack")
public class SupplierThreePack implements Serializable {

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

    /** 判定责任时间点 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "判定责任时间点", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "判定责任时间点")
    private Date responsibilityJudgmentTime;

    /** 计划发货时间点 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划发货时间点", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "计划发货时间点")
    private Date plannedDeliveryTime;

    /** 实际发货时间点 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际发货时间点", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "实际发货时间点")
    private Date actualDeliveryTime;

    /** 具体三包事项 */
    @Excel(name = "具体三包事项")
    @ExcelProperty(value = "具体三包事项")
    private String specificWarrantyItems;

//    /** 填报时间 */
//    @ExcelIgnore
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "填报时间", width = 30, dateFormat = "yyyy-MM-dd")
//    @ExcelProperty(value = "填报时间")
//    private Date happenTime;
//
//    /** 填报人 */
//    @ExcelIgnore
//    @Excel(name = "填报人")
//    @ExcelProperty(value = "填报人")
//    private String reporter;
//
    /** 分数 */
    @Excel(name = "分数")
    private Double score;

//    /** 备选1 */
//    @ExcelIgnore
//    @Excel(name = "备选1")
//    private String one;
//
//    /** 备选2 */
//    @ExcelIgnore
//    @Excel(name = "备选2")
//    private String two;
}
