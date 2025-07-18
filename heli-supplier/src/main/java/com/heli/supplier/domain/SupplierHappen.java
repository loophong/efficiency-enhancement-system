package com.heli.supplier.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 质量通知单对象 supplier_happen
 * 
 * @author wll
 * @date 2025-03-07
 */
@Data
@TableName(value = "supplier_happen")
public class SupplierHappen
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 供应商代码 */
    @Excel(name = "供应商代码")
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date happenTime;


    /** 回函截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回函截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deadline;

    /** 反馈单文件名 */
    @Excel(name = "反馈单文件名")
    private String feedbackFileName;

    /** 反馈单附件路径 */
    @Excel(name = "反馈单附件路径")
    private String feedbackFileUrl;

    /** 回函文件名 */
    @Excel(name = "回函文件名")
    private String replyFileName;

    /** 回函附件路径 */
    @Excel(name = "回函附件路径")
    private String replyFileUrl;

    /** 回函上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回函上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date replyTime;

    /** 回函状态 */
    @Excel(name = "回函状态")
    private String replyStatus;

//    /** 填报人 */
//    @Excel(name = "填报人")
//    private String uploadName;
//
//    /** 备选1 */
//    @Excel(name = "备选1")
//    private String one;
//
//    /** 备选2 */
//    @Excel(name = "备选2")
//    private String two;
//
//    /** 备选3 */
//    @Excel(name = "备选3")
//    private String three;

}
