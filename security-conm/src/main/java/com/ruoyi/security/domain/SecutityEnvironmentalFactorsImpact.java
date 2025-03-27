package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 环境因素清单对象 secutity_environmental_factors_impact
 * 
 * @author wang
 * @date 2025-03-26
 */
public class SecutityEnvironmentalFactorsImpact extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 作业活动 */
    @Excel(name = "作业活动")
    private String activity;

    /** 环境因数 */
    @Excel(name = "环境因数")
    private String environmentalFactor;

    /** 环境影响 */
    @Excel(name = "环境影响")
    private String impactOfEnvironmentalFactor;

    /** 环境因素评价 */
    @Excel(name = "环境因素评价")
    private String evaluationOfEnvironmentalFactor;

    /** 环境影响程度 */
    @Excel(name = "环境影响程度")
    private String environmentalImpact;

    /** 时态 */
    @Excel(name = "时态")
    private String tense;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 控制措施 */
    @Excel(name = "控制措施")
    private String controlMeasures;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String statu;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setActivity(String activity) 
    {
        this.activity = activity;
    }

    public String getActivity() 
    {
        return activity;
    }
    public void setEnvironmentalFactor(String environmentalFactor) 
    {
        this.environmentalFactor = environmentalFactor;
    }

    public String getEnvironmentalFactor() 
    {
        return environmentalFactor;
    }
    public void setImpactOfEnvironmentalFactor(String impactOfEnvironmentalFactor) 
    {
        this.impactOfEnvironmentalFactor = impactOfEnvironmentalFactor;
    }

    public String getImpactOfEnvironmentalFactor() 
    {
        return impactOfEnvironmentalFactor;
    }
    public void setEvaluationOfEnvironmentalFactor(String evaluationOfEnvironmentalFactor) 
    {
        this.evaluationOfEnvironmentalFactor = evaluationOfEnvironmentalFactor;
    }

    public String getEvaluationOfEnvironmentalFactor() 
    {
        return evaluationOfEnvironmentalFactor;
    }
    public void setEnvironmentalImpact(String environmentalImpact) 
    {
        this.environmentalImpact = environmentalImpact;
    }

    public String getEnvironmentalImpact() 
    {
        return environmentalImpact;
    }
    public void setTense(String tense) 
    {
        this.tense = tense;
    }

    public String getTense() 
    {
        return tense;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setControlMeasures(String controlMeasures) 
    {
        this.controlMeasures = controlMeasures;
    }

    public String getControlMeasures() 
    {
        return controlMeasures;
    }
    public void setStatu(String statu) 
    {
        this.statu = statu;
    }

    public String getStatu() 
    {
        return statu;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activity", getActivity())
            .append("environmentalFactor", getEnvironmentalFactor())
            .append("impactOfEnvironmentalFactor", getImpactOfEnvironmentalFactor())
            .append("evaluationOfEnvironmentalFactor", getEvaluationOfEnvironmentalFactor())
            .append("environmentalImpact", getEnvironmentalImpact())
            .append("tense", getTense())
            .append("status", getStatus())
            .append("controlMeasures", getControlMeasures())
            .append("statu", getStatu())
            .toString();
    }
}
