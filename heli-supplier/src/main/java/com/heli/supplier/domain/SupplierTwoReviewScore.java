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
 * 二方审核得分对象 supplier_two_review_score
 *
 * @author wll
 * @date 2025-03-06
 */
@Data
@TableName(value = "supplier_two_review_score")
public class SupplierTwoReviewScore {

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
     * 不符合项
     */
    @Excel(name = "不符合项")
    @ExcelProperty(value = "不符合项")
    private String notTrue;

    /**
     * 得分
     */
    @ExcelIgnore
//    @Excel(name = "得分")
//    @ExcelProperty(value = "得分")
    private Long score;

    /**
     * 上传时间
     */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM")
//    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
//    @ExcelProperty(value = "上传时间")
    private Date time;






    /**
     * 不符合项2
     */
    @ExcelIgnore
//    @Excel(name = "不符合项2")
//    @ExcelProperty(value = "不符合项2")
    private String notTrue2;

    /**
     * 不符合项3
     */
    @ExcelIgnore
//    @Excel(name = "不符合项3")
//    @ExcelProperty(value = "不符合项3")
    private String notTrue3;

    /**
     * 不符合项4
     */
    @ExcelIgnore
//    @Excel(name = "不符合项4")
//    @ExcelProperty(value = "不符合项4")
    private String notTrue4;

    /**
     * 不符合项5
     */
    @ExcelIgnore
//    @Excel(name = "不符合项5")
//    @ExcelProperty(value = "不符合项5")
    private String notTrue5;


}
