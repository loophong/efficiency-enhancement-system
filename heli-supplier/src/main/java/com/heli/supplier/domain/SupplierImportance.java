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
 * 重要度对象 supplier_importance
 * 
 * @author wll
 * @date 2025-03-07
 */
@Data
@TableName(value = "supplier_importance")
public class SupplierImportance extends BaseEntity
{
    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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

    /** 供货金额 */
    @Excel(name = "供货金额")
    @ExcelProperty(value = "供货金额")
    private Long amount;

    /** 采购金额占比 */
    @Excel(name = "采购金额占比")
    @ExcelProperty(value = "采购金额占比")
    private String percentage;

    /** 得分 */
    @Excel(name = "得分")
    @ExcelProperty(value = "得分")
    private Long score;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "上传时间")
    private Date uploadTime;

    /** 备选1 */
    @Excel(name = "备选1")
    @ExcelIgnore
    private String one;

    /** 备选2 */
    @Excel(name = "备选2")
    @ExcelIgnore
    private String two;

    /** 备选3 */
    @Excel(name = "备选3")
    @ExcelIgnore
    private String three;


}
