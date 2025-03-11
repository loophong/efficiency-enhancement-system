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
 * 自检报告对象 supplier_self_test_reports
 * 
 * @author wll
 * @date 2025-02-26
 */
@Data
@TableName(value = "supplier_self_test_reports")
public class SupplierSelfTestReports implements Serializable {

    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    @ExcelProperty(value = "供应商编码")
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

    /** 具体内容 */
    @Excel(name = "具体内容")
    @ExcelProperty(value = "具体内容")
    private String specificContent;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "发生时间")
    private Date happenTime;

    /** 填报人 */
    @Excel(name = "填报人")
    @ExcelProperty(value = "填报人")
    private String reporter;


}
