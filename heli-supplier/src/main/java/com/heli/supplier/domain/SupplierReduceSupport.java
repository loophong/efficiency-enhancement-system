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


    /** 填报人  改为 供应商代码*/
    @Excel(name = "供应商代码")
    @ExcelProperty(value = "供应商代码")
//    private String uploadName;
    private String supplierCode;

    /** 备选    改为 供应商名称*/
    @Excel(name = "供应商名称")
    @ExcelProperty(value = "供应商名称")
//    private String one;
    private String supplierName;

    /** 降本金额 */
    @Excel(name = "降本金额")
    @ExcelProperty(value = "降本金额")
//    @ExcelProperty(index = 14)
    private Double reduceMoney;

    /** 降本占比 */
    @ExcelProperty(value = "降本占比")
    private String percentage;

    /** 得分 */
    @ExcelIgnore
//    @Excel(name = "得分")
//    @ExcelProperty(value = "得分")
    private Double score;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelIgnore
//    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
//    @ExcelProperty(value = "上传时间")
    private Date uploadTime;

}
