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
 * 价格竞争力对象 supplier_price_compete
 * 
 * @author wll
 * @date 2025-03-05
 */
@Data
@TableName(value = "supplier_price_compete")
public class SupplierPriceCompete
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

    /** 记录时间 */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "记录时间")
    private Date time;

    /** 价格类型 */
    @Excel(name = "价格类型")
    @ExcelProperty(value = "价格类型")
    private String priceType;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "上传时间")
    private Date uploadTime;

    /** 得分 */
    @Excel(name = "得分")
    @ExcelProperty(value = "得分")
    private Long score;

    /** 模型得分 */
    @Excel(name = "模型得分")
    @ExcelProperty(value = "模型得分")
    private Long modelScore;

    /** 填报人 */
    @ExcelIgnore
    @Excel(name = "填报人")
    @ExcelProperty(value = "填报人")
    private String uploadName;


}
