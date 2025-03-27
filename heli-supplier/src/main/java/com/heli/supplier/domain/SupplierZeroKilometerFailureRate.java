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
 * 零公里故障指标完成率对象 supplier_zero_kilometer_failure_rate
 *
 * @author wll
 * @date 2025-03-24
 */
@Data
@TableName(value = "supplier_zero_kilometer_failure_rate")
public class SupplierZeroKilometerFailureRate{

    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    @ExcelProperty(value = {"2024年考核名单","供应商名称"},index = 1)
    private String supplierName;

    /**
     * 部件类别
     */
    @Excel(name = "部件类别")
    @ExcelProperty(value = "部件类别",index = 2)
    private String componentType;

    /**
     * 指标值
     */
    @Excel(name = "指标值")
    @ExcelProperty(value = {"2024年指标","指标值"},index = 3)
    private String targetPpm;

    /**
     * 故障描述
     */
    @Excel(name = "故障描述")
    @ExcelProperty(value = "故障描述",index = 4)
    private String failureDescription;

    /**
     * 故障数
     */
    @Excel(name = "故障数")
    @ExcelProperty(value = "故障数",index = 5)
    private String failureCount;

    /**
     * 装车数
     */
    @Excel(name = "装车数")
    @ExcelProperty(value = "装车数",index = 6)
    private String installedVehicles;

    /**
     * PPM值
     */
    @Excel(name = "PPM值")
    @ExcelProperty(value = "PPM值",index = 7)
    private String ppmValue;

    /**
     * 季度累计PPM值
     */
    @Excel(name = "季度累计PPM值")
    @ExcelProperty(value = "季度累计PPM值",index = 8)
    private String cumulativePpm;

    /**
     * 零公里故障率
     */
    @Excel(name = "零公里故障率")
    @ExcelProperty(value = "零公里故障率")
    private String zeroFailureRate;

    /**
     * 得分
     */
    @Excel(name = "得分")
    @ExcelProperty(value = "得分")
    private Double score;

    /**
     * 上传月份
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传月份", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "上传月份")
    private Date uploadMonth;

    /**
     * 时间
     */
    @Excel(name = "时间")
    @ExcelProperty(value = "时间")
    private String two;

}
