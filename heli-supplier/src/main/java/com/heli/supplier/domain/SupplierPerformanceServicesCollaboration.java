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
 * 服务与协作对象 supplier_performance_services_collaboration
 * 
 * @author wll
 * @date 2025-03-05
 */
@Data
@TableName(value = "supplier_performance_services_collaboration")
public class SupplierPerformanceServicesCollaboration
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
    @ExcelProperty(value = {"供应商代码","供应商代码"},index = 0)
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    @ExcelProperty(value = {"供应商名称","供应商名称"},index = 1)
    private String supplierName;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "记录时间")
    private Date time;

    /** 供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作 */
    @Excel(name = "供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作")
    @ExcelProperty(value = {"服务于协作","供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作"},index = 2)
    private Long letter;

    /** 供应商如受到相关方处罚 */
    @Excel(name = "供应商如受到相关方处罚")
    @ExcelProperty(value = {"服务于协作","供应商如受到相关方处罚"}, index = 3)
    private Long punish;

    /** 供应商如对采购员需求反馈不及时 */
    @Excel(name = "供应商如对采购员需求反馈不及时")
    @ExcelProperty(value = { "服务于协作","供应商如对采购员需求反馈不及时"},index = 4)
    private Long feedbackNotTimely;

    /** 得分 */
    @Excel(name = "得分")
    @ExcelProperty(value = "得分",index = 5)
    private Long score;

    /** 具体内容 */
    @Excel(name = "具体内容")
    @ExcelProperty(value = "具体内容")
    private String specificContent;

    /** 填报人 */
    @ExcelIgnore
    @Excel(name = "填报人")
    @ExcelProperty(value = "填报人")
    private String uploadName;


}
