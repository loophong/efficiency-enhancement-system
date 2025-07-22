package com.heli.supplier.domain;

import java.io.Serial;
import java.io.Serializable;
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
 * 经营风险对象 supplier_risk
 * 
 * @author wll
 * @date 2025-02-27
 */
@Data
@TableName(value = "supplier_risk")
public class SupplierRisk implements Serializable
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

    /** 得分 */
    @ExcelIgnore
//    @Excel(name = "得分")
//    @ExcelProperty(value = "得分")
    private Double score;

    /** 风险数量 */
    @Excel(name = "期间风险数量")
    @ExcelProperty(value = "期间风险数量")
    private Long riskNumber;

    /** 上传时间 */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM")
//    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
//    @ExcelProperty(value = "上传时间")
    private Date uploadTime;

}
