package com.heli.supplier.domain;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * 一次交检合格率-简化版对象 supplier_onetime_simple
 * 
 * @author weill
 * @date 2025-02-21
 */
@Data
@TableName(value = "supplier_onetime_simple")
public class SupplierOnetimeSimple implements Serializable {
    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 数据合格率
     */
    @Excel(name = "数量合格率")
    @ExcelProperty(value = "数量合格率")
    private String quantityPassRate;

    /**
     * 上次月份
     */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "上传月份", width = 30, dateFormat = "yyyy-MM-dd")
//    @ExcelProperty(value = "上传月份")
    private Date updateMonth;

//    /**
//     * 填报人
//     */
//    @Excel(name = "填报人")
//    @ExcelProperty(value = "填报人")
//    private String addName;

    /** 分数 */
    @ExcelIgnore
    @Excel(name = "分数")
    private Double score;


    @ExcelIgnore
    private Integer rowIndex;

    @ExcelIgnore
    private Long batchId;


}
