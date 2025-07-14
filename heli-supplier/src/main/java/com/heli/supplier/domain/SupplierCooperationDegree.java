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
 * 新产品研发配合程度对象 supplier_cooperation_degree
 * 
 * @author wll
 * @date 2025-03-05
 */
@Data
@TableName(value = "supplier_cooperation_degree")
public class SupplierCooperationDegree
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

    /** 具体内容 */
    @Excel(name = "具体内容")
    @ExcelProperty(value = "具体内容")
    private String specificContent;

    /** 配合程度 */
    @Excel(name = "配合程度", dictType = "supplier_cooperation_degree")
//    @ExcelProperty(value = "配合程度")
    private Long cooperationDegree;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "上传时间")
    private Date month;

    /** 得分 */
    @Excel(name = "得分")
    @ExcelProperty(value = "得分")
    private Long score;

//    /** 填报人 */
//    @Excel(name = "填报人")
//    @ExcelProperty(value = "填报人")
//    private String uploadName;


}
