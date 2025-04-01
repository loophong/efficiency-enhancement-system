package com.heli.supplier.domain;

import java.io.Serial;
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
 * 降本支持对象 supplier_reduce_support
 *
 * @author wll
 * @date 2025-03-25
 */
@Data
@TableName(value = "supplier_reduce_support")
public class SupplierReduceSupport
{
    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private String id;

    /** 原供应商代码 */
    @Excel(name = "原供应商代码")
    @ExcelProperty(value = "原供应商代码")
    private String supplierCodePast;

    /** 原供应商名称 */
    @Excel(name = "原供应商名称")
    @ExcelProperty(value = "原供应商名称")
    private String supplierNamePast;

    /** 新引进供应商代码 */
    @Excel(name = "（新引进）供应商代码")
    @ExcelProperty(value = "（新引进）供应商代码")
    private String supplierCodeNow;

    /** 新引进供应商名称 */
    @Excel(name = "（新引进）供应商名称")
    @ExcelProperty(value = "（新引进）供应商名称")
    private String supplierNameNow;

    /** 降幅 */
    @Excel(name = "降幅")
    @ExcelProperty(value = "降幅")
    private Double decrease;

    /** 总采购量 */
    @Excel(name = "总采购量")
    @ExcelProperty(value = "总采购量")
    private Long totalPurchases;

    /** 降本金额 */
    @Excel(name = "降本金额")
//    @ExcelProperty(value = "降本金额")
    @ExcelProperty(index = 14)
    private Double reduceMoney;

    /** 单价 */
    @Excel(name = "2023年调整单价不含税")
//    @ExcelProperty(value = "2023年调整单价不含税")
    @ExcelProperty(index = 9)
    private Double price;


    /** 供货金额=单价*总采购量 */
    @Excel(name = "供货金额")
    @ExcelProperty(value = "供货金额")
    private Double supplyAmount;

    /** 降本占比 */
    @Excel(name = "降本占比")
    @ExcelProperty(value = "降本占比")
    private Double percentage;

    /** 价格统计日期 */
    @Excel(name = "价格统计日期")
    @ExcelProperty(value = "价格统计日期")
    private String priceStatisticDate;

    /** 得分 */
    @Excel(name = "得分")
    @ExcelProperty(value = "得分")
    private Long score;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "上传时间")
    private Date uploadTime;

    /** 填报人 */
    @ExcelIgnore
    @Excel(name = "填报人")
    @ExcelProperty(value = "填报人")
    private String uploadName;

    /** 备选 */
    @ExcelIgnore
    @Excel(name = "备选")
    @ExcelProperty(value = "备选")
    private String one;



}
