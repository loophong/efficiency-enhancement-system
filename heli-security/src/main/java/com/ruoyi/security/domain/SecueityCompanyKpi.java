package com.ruoyi.security.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 公司KPI对象 secueity_company_kpi
 * 
 * @author wang
 * @date 2025-02-27
 */
public class SecueityCompanyKpi extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 关联ID */
    @Excel(name = "关联ID", width = 15, type = Type.ALL)
    private Long relatedId;

    /** 单位名称 */
    @Excel(name = "单位名称", width = 15, type = Type.ALL)
    private String unitName;

    /** 分类 */
    @Excel(name = "分类", width = 15, type = Type.ALL)
    private String category;

    /** 指标名称 */
    @Excel(name = "指标名称", width = 20, type = Type.ALL)
    private String indicatorName;

    /** 单位 */
    @Excel(name = "单位", width = 10, type = Type.ALL)
    private String unitOfMeasurement;

    /** 年份 */
    @Excel(name = "年份", width = 10, type = Type.ALL)
    private String year;

    /** 目标值 */
    @Excel(name = "年度目标值", width = 15, type = Type.ALL)
    private BigDecimal annualTargetValue;

    /** 考核频次 */
    @Excel(name = "考核频次", width = 10, readConverterExp = "年度=年度,季度=季度,半年度=半年度", type = Type.ALL)
    private String evaluationFrequency;

    /** 权重 */
    @Excel(name = "权重", width = 10, suffix = "%", type = Type.ALL)
    private BigDecimal weight;

    /** 一季度目标值 */
    @Excel(name = "一季度目标值", width = 15)
    private BigDecimal q1TargetValue;

    /** 一季度结果（1-3月） */
    @Excel(name = "一季度结果值", width = 15)
    private BigDecimal q1Result;

    /** 一季度核定值 */
    @Excel(name = "一季度核定值", width = 10)
    private BigDecimal q1VerifiedValue;

    /** 二季度目标值 */
    @Excel(name = "二季度目标值", width = 15)
    private BigDecimal q2TargetValue;

    /** 二季度结果（1-6月） */
    @Excel(name = "二季度结果值", width = 15)
    private BigDecimal q2Result;

    /** 二季度核定值 */
    @Excel(name = "二季度核定值", width = 10)
    private BigDecimal q2VerifiedValue;

    /** 三季度目标值 */
    @Excel(name = "三季度目标值", width = 15)
    private BigDecimal q3TargetValue;

    /** 三季度结果（1-9月） */
    @Excel(name = "三季度结果值", width = 15)
    private BigDecimal q3Result;

    /** 三季度核定值 */
    @Excel(name = "三季度核定值", width = 10)
    private BigDecimal q3VerifiedValue;

    /** 四季度目标值 */
    @Excel(name = "四季度目标值", width = 15)
    private BigDecimal q4TargetValue;

    /** 四季度结果（1-12月） */
    @Excel(name = "四季度结果值", width = 15)
    private BigDecimal q4Result;

    /** 四季度核定值 */
    @Excel(name = "四季度核定值", width = 10)
    private BigDecimal q4VerifiedValue;

    /** 备注 */
    @Excel(name = "备注", width = 30, type = Type.ALL)
    private String beizhu;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setRelatedId(Long relatedId) 
    {
        this.relatedId = relatedId;
    }

    public Long getRelatedId() 
    {
        return relatedId;
    }

    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }
    public void setIndicatorName(String indicatorName) 
    {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorName() 
    {
        return indicatorName;
    }
    public void setUnitOfMeasurement(String unitOfMeasurement) 
    {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getUnitOfMeasurement() 
    {
        return unitOfMeasurement;
    }
    public void setYear(String year) 
    {
        this.year = year;
    }

    public String getYear() 
    {
        return year;
    }
    public void setAnnualTargetValue(BigDecimal annualTargetValue) 
    {
        this.annualTargetValue = annualTargetValue;
    }

    public BigDecimal getAnnualTargetValue() 
    {
        return annualTargetValue;
    }
    public void setEvaluationFrequency(String evaluationFrequency) 
    {
        this.evaluationFrequency = evaluationFrequency;
    }

    public String getEvaluationFrequency() 
    {
        return evaluationFrequency;
    }
    public void setWeight(BigDecimal weight) 
    {
        this.weight = weight;
    }

    public BigDecimal getWeight() 
    {
        return weight;
    }
    public void setQ1TargetValue(BigDecimal q1TargetValue) 
    {
        this.q1TargetValue = q1TargetValue;
    }

    public BigDecimal getQ1TargetValue() 
    {
        return q1TargetValue;
    }
    public void setQ1Result(BigDecimal q1Result) 
    {
        this.q1Result = q1Result;
    }

    public BigDecimal getQ1Result() 
    {
        return q1Result;
    }
    public void setQ1VerifiedValue(BigDecimal q1VerifiedValue) 
    {
        this.q1VerifiedValue = q1VerifiedValue;
    }

    public BigDecimal getQ1VerifiedValue() 
    {
        return q1VerifiedValue;
    }
    public void setQ2TargetValue(BigDecimal q2TargetValue) 
    {
        this.q2TargetValue = q2TargetValue;
    }

    public BigDecimal getQ2TargetValue() 
    {
        return q2TargetValue;
    }
    public void setQ2Result(BigDecimal q2Result) 
    {
        this.q2Result = q2Result;
    }

    public BigDecimal getQ2Result() 
    {
        return q2Result;
    }
    public void setQ2VerifiedValue(BigDecimal q2VerifiedValue) 
    {
        this.q2VerifiedValue = q2VerifiedValue;
    }

    public BigDecimal getQ2VerifiedValue() 
    {
        return q2VerifiedValue;
    }
    public void setQ3TargetValue(BigDecimal q3TargetValue) 
    {
        this.q3TargetValue = q3TargetValue;
    }

    public BigDecimal getQ3TargetValue() 
    {
        return q3TargetValue;
    }
    public void setQ3Result(BigDecimal q3Result) 
    {
        this.q3Result = q3Result;
    }

    public BigDecimal getQ3Result() 
    {
        return q3Result;
    }
    public void setQ3VerifiedValue(BigDecimal q3VerifiedValue) 
    {
        this.q3VerifiedValue = q3VerifiedValue;
    }

    public BigDecimal getQ3VerifiedValue() 
    {
        return q3VerifiedValue;
    }
    public void setQ4TargetValue(BigDecimal q4TargetValue) 
    {
        this.q4TargetValue = q4TargetValue;
    }

    public BigDecimal getQ4TargetValue() 
    {
        return q4TargetValue;
    }
    public void setQ4Result(BigDecimal q4Result) 
    {
        this.q4Result = q4Result;
    }

    public BigDecimal getQ4Result() 
    {
        return q4Result;
    }
    public void setQ4VerifiedValue(BigDecimal q4VerifiedValue) 
    {
        this.q4VerifiedValue = q4VerifiedValue;
    }

    public BigDecimal getQ4VerifiedValue() 
    {
        return q4VerifiedValue;
    }
    public void setBeizhu(String beizhu) 
    {
        this.beizhu = beizhu;
    }

    public String getBeizhu() 
    {
        return beizhu;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("relatedId", getRelatedId())
            .append("unitName", getUnitName())
            .append("category", getCategory())
            .append("indicatorName", getIndicatorName())
            .append("unitOfMeasurement", getUnitOfMeasurement())
            .append("year", getYear())
            .append("annualTargetValue", getAnnualTargetValue())
            .append("evaluationFrequency", getEvaluationFrequency())
            .append("weight", getWeight())
            .append("q1TargetValue", getQ1TargetValue())
            .append("q1Result", getQ1Result())
            .append("q1VerifiedValue", getQ1VerifiedValue())
            .append("q2TargetValue", getQ2TargetValue())
            .append("q2Result", getQ2Result())
            .append("q2VerifiedValue", getQ2VerifiedValue())
            .append("q3TargetValue", getQ3TargetValue())
            .append("q3Result", getQ3Result())
            .append("q3VerifiedValue", getQ3VerifiedValue())
            .append("q4TargetValue", getQ4TargetValue())
            .append("q4Result", getQ4Result())
            .append("q4VerifiedValue", getQ4VerifiedValue())
            .append("beizhu", getBeizhu())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
