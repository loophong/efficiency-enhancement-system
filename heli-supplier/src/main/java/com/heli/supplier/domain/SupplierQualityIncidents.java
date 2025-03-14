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
 * 质量事故对象 supplier_quality_incidents
 *
 * @author ruoyi
 * @date 2025-02-20
 */
@Data
@TableName(value = "supplier_quality_incidents")
public class SupplierQualityIncidents
{
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 供应商代码
     */
    @Excel(name = "供应商编码")
    @ExcelProperty(value = "供应商编码")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    @ExcelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 质量事故发生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "质量事故发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "质量事故发生时间")
    private Date uploadTime;

    /**
     * 填报人
     */
//    @Excel(name = "填报人")
//    @ExcelProperty(value = "填报人")
//    private Long uploadId;

    /**
     * 事故描述
     */
    @Excel(name = "事故描述")
    @ExcelProperty(value = "事故描述")
    private String incidentDescription;

    /** 分数 */
    @Excel(name = "分数")
    private Double score;

    /** 填报人 */
    @Excel(name = "填报人")
    private String uploadName;

    /** 备选1 */
    @ExcelIgnore
    @Excel(name = "备选1")
    private String one;

    /** 备选2 */
    @ExcelIgnore
    @Excel(name = "备选2")
    private String two;
}
