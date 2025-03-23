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
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评分表对象 supplier_evaluate
 * 
 * @author wll
 * @date 2025-03-13
 */
@Data
@NoArgsConstructor
@TableName(value = "supplier_evaluate")
public class SupplierEvaluate implements Serializable
{
    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Serial
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

    /** 质量事故 */
    @Excel(name = "质量事故")
    @ExcelProperty(value = "质量事故")
    private BigDecimal qualityIncident;

    /** 一次交检合格率 */
    @Excel(name = "一次交检合格率")
    @ExcelProperty(value = "一次交检合格率")
    private BigDecimal firstInspectionPassrate;

    /** 零公里故障指标完成率 */
    @Excel(name = "零公里故障指标完成率")
    @ExcelProperty(value = "零公里故障指标完成率")
    private BigDecimal zeroKilometerFailurerate;

    /** 质量通知单发生 */
    @Excel(name = "质量通知单发生")
    @ExcelProperty(value = "质量通知单发生")
    private BigDecimal qualityNotificationOrderrate;

    /** 反馈单回函及时率 */
    @Excel(name = "反馈单回函及时率")
    @ExcelProperty(value = "反馈单回函及时率")
    private BigDecimal feedbackOrderletterTimeliness;

    /** 保修期内市场售后返修率 */
    @Excel(name = "保修期内市场售后返修率")
    @ExcelProperty(value = "保修期内市场售后返修率")
    private BigDecimal warrantyperiodRepairrate;

    /** 三包配件发货及时率 */
    @Excel(name = "三包配件发货及时率")
    @ExcelProperty(value = "三包配件发货及时率")
    private BigDecimal threepackageComponentRepairrate;

    /** 二方审核得分 */
    @Excel(name = "二方审核得分")
    @ExcelProperty(value = "二方审核得分")
    private BigDecimal secondpartyAuditscore;

    /** 自检报告准确率 */
    @Excel(name = "自检报告准确率")
    @ExcelProperty(value = "自检报告准确率")
    private BigDecimal selfinspectionReportaccuracy;

    /** 擅自变更产品材质参数尺寸 */
    @Excel(name = "擅自变更产品材质参数尺寸")
    @ExcelProperty(value = "擅自变更产品材质参数尺寸")
    private BigDecimal productmaterialParametersizeChange;

    /** 重要度 */
    @Excel(name = "重要度")
    @ExcelProperty(value = "重要度")
    private BigDecimal weight;

    /** 经营风险 */
    @Excel(name = "经营风险")
    @ExcelProperty(value = "经营风险")
    private BigDecimal businessRisk;

    /** 供货保障 */
    @Excel(name = "供货保障")
    @ExcelProperty(value = "供货保障")
    private BigDecimal supplyGuarantee;

    /** 价格竞争力 */
    @Excel(name = "价格竞争力")
    @ExcelProperty(value = "价格竞争力")
    private BigDecimal priceCompetitiveness;

    /** 降本支持 */
    @Excel(name = "降本支持")
    @ExcelProperty(value = "降本支持")
    private BigDecimal costreductionSupport;

    /** 服务与协作 */
    @Excel(name = "服务与协作")
    @ExcelProperty(value = "服务与协作")
    private BigDecimal serviceAndCoordination;

    /** 价格诚信 */
    @Excel(name = "价格诚信")
    @ExcelProperty(value = "价格诚信")
    private BigDecimal priceIntegrity;

    /** 付款限制条件 */
    @Excel(name = "付款限制条件")
    @ExcelProperty(value = "付款限制条件")
    private BigDecimal paymentRestrictionconditions;

    /** 新产品研发配合程度 */
    @Excel(name = "新产品研发配合程度")
    @ExcelProperty(value = "新产品研发配合程度")
    private BigDecimal newproductDevelopmentCooperationdegree;

    /** 产品技术问题整改及时性 */
    @Excel(name = "产品技术问题整改及时性")
    @ExcelProperty(value = "产品技术问题整改及时性")
    private BigDecimal producttechnologyproblemImprovementtimeliness;

    /** 总分 */
    @Excel(name = "总分")
    @ExcelProperty(value = "总分")
    private BigDecimal totalScore;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "开始时间")
    private Date happenTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "结束时间")
    private Date endTime;

    /** 备1 */
    @ExcelIgnore
    @Excel(name = "备1")
    private String one;

    /** 备2 */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "备2", width = 30, dateFormat = "yyyy-MM-dd")
    private Date two;

    /** 备3 */
    @ExcelIgnore
    @Excel(name = "备3")
    private String three;

    /** 入库标识符 */
    @ExcelIgnore
    @Excel(name = "入库标识符")
    private Integer storageFlag;


}
