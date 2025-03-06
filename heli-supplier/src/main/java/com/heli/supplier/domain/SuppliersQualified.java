package com.heli.supplier.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.io.Serial;
import java.io.Serializable;

/**
 * 供应商对象 suppliers_qualified
 *
 * @author ruoyi
 * @date 2025-01-17
 */
@Data
@TableName(value = "suppliers_qualified")
public class SuppliersQualified implements Serializable {

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
     * 系统
     */
    @Excel(name = "系统")
    @ExcelProperty(value = "系统")
    private String controlSystem;

    /**
     * 供应商编码
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
     * 主要供货范围
     */
    @Excel(name = "主要供货范围")
    @ExcelProperty(value = "主要供货范围")
    private String mainSupplyrange;

    /**
     * 备注
     */
    @Excel(name = "备注")
    @ExcelProperty(value = "备注")
    private String remarks;

}
