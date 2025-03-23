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
 * 擅自变更产品材质参数尺寸对象 supplier_change
 * 
 * @author wll
 * @date 2025-03-07
 */
@Data
@TableName(value = "supplier_change")
public class SupplierChange
{
    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private String id;

    /** 供应商代码 */
    @Excel(name = "供应商编码")
    @ExcelProperty(value = "供应商编码")
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    @ExcelProperty(value = "供应商名称")
    private String supplierName;

    /** 供应商类别 */
    @Excel(name = "供应商类别")
    @ExcelProperty(value = "供应商类别")
    private Long formLeibie;

    /** 判定责任时间点 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "判定责任时间点", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "判定责任时间点")
    private Date responsibilityJudgmentTime;

    /** 具体内容 */
    @Excel(name = "具体内容")
    @ExcelProperty(value = "具体内容")
    private String specificContent;

    /** 得分 */
    @ExcelIgnore
    @Excel(name = "得分")
    @ExcelProperty(value = "得分")
    private Double score;



    /** 发生次数 */
    @ExcelIgnore
    @Excel(name = "发生次数")
    @ExcelProperty(value = "发生次数")
    private Long happenNumber;


    /** 发生时间 */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "发生时间")
    private Date happenTime;


    /** 填报人 */
    @ExcelIgnore
    @Excel(name = "填报人")
    @ExcelProperty(value = "填报人")
    private String reporter;



    /** 备选2 */
    @ExcelIgnore
    @Excel(name = "备选2")
    private String two;

    /** 备选3 */
    @ExcelIgnore
    @Excel(name = "备选3")
    private String three;


}
