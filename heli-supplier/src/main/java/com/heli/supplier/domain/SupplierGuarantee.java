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
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供货保障对象 supplier_guarantee
 * 
 * @author wll
 * @date 2025-02-27
 */
@Data
@TableName(value = "supplier_guarantee")
/**
 * 供货保障对象 supplier_guarantee
 *
 * @author wll
 * @date 2025-03-19
 */
public class SupplierGuarantee
{
    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 供应商代码 */
    @Excel(name = "供应商代码")
    @ExcelProperty(value = "供应商代码")
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    @ExcelProperty(value = "供应商名称")
    private String supplierName;

    /** 总条目数 */
    @Excel(name = "总条目数")
    @ExcelProperty(value = "总条目数")
    private Long totalNumber;

    /** 及时条目数 */
    @Excel(name = "及时条目数")
    @ExcelProperty(value = "及时条目数")
    private Long timelyNumber;

    /** 不及时条目数 */
    @Excel(name = "不及时条目数")
    @ExcelProperty(value = "不及时条目数")
    private Long notTimely;

    /** 供货及时率 */
    @Excel(name = "供货及时率")
    @ExcelProperty(value = "供货及时率")
    private String timelyDeliveryRate;

    /** 及时率得分 */
    @ExcelIgnore
//    @Excel(name = "及时率得分")
//    @ExcelProperty(value = "及时率得分")
    private Double timelyRateScore;

    /** 模型得分 */
    @ExcelIgnore
//    @Excel(name = "模型得分")
//    @ExcelProperty(value = "模型得分")
    private Double modelScore;

    /** 上传月份 */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "上传月份", width = 30, dateFormat = "yyyy-MM-dd")
//    @ExcelProperty(value = "上传月份")
    private Date uploadMonth;


}
