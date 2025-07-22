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
 * 付款限制条件对象 supplier_payment
 * 
 * @author wll
 * @date 2025-03-25
 */
@Data
@TableName(value = "supplier_payment")
public class SupplierPayment{

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
     * 供应商代码
     */
    @Excel(name = "供应商代码")
    @ExcelProperty(value = "供应商代码")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    @ExcelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 付款条件
     */
    @Excel(name = "付款条件")
    @ExcelProperty(value = "付款条件")
    private String paymentTerms;

    /**
     * 付款说明
     */
    @Excel(name = "付款说明")
    @ExcelProperty(value = "付款说明")
    private String one;
    /**
     *  备注
     */
    @Excel(name = "备注")
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 得分
     */
    @ExcelIgnore
//    @Excel(name = "得分")
//    @ExcelProperty(value = "得分")
    private Double score;

    /**
     * 模型得分
     */
    @ExcelIgnore
//    @Excel(name = "模型得分")
//    @ExcelProperty(value = "模型得分")
    private Double modelScore;

    /**
     * 上传时间
     */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date uploadTime;

    /**
     * 备注2
     */
    @ExcelIgnore
//    @Excel(name = "备注2")
//    @ExcelProperty(value = "备注2")
    private String two;

}
