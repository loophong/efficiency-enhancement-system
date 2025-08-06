package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 环境因素清单对象 secutity_environmental_factors_impact
 * 
 * @author wang
 * @date 2025-07-14
 */
public class SecutityEnvironmentalFactorsImpact extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
//    @Excel(name = "序号")
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

    /** A */
    @Excel(name = "A")
    private String evaluationOfEnvironmentalFactorA;

    /** B */
    @Excel(name = "B")
    private String evaluationOfEnvironmentalFactorB;

    /** C */
    @Excel(name = "C")
    private String evaluationOfEnvironmentalFactorC;

    /** D */
    @Excel(name = "D")
    private String evaluationOfEnvironmentalFactorD;

    /** E */
    @Excel(name = "E")
    private String evaluationOfEnvironmentalFactorE;

    /** F */
    @Excel(name = "F")
    private String evaluationOfEnvironmentalFactorF;

    /** G */
    @Excel(name = "G")
    private String evaluationOfEnvironmentalFactorG;

    /** 环境影响程度 */
    @Excel(name = "环境影响程度")
    private String environmentalImpact;

    /** 时态/状态 */
    @Excel(name = "时态/状态")
    private String tense;

    /** 控制措施 */
    @Excel(name = "控制措施")
    private String controlMeasures;

    /** 审批状态 */
//    @Excel(name = "审批状态")
    private String statu;

    /** 关联ID */
//    @Excel(name = "关联ID")
    private String relatedId;

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
    public void setEvaluationOfEnvironmentalFactorA(String evaluationOfEnvironmentalFactorA) 
    {
        this.evaluationOfEnvironmentalFactorA = evaluationOfEnvironmentalFactorA;
    }

    public String getEvaluationOfEnvironmentalFactorA() 
    {
        return evaluationOfEnvironmentalFactorA;
    }
    public void setEvaluationOfEnvironmentalFactorB(String evaluationOfEnvironmentalFactorB) 
    {
        this.evaluationOfEnvironmentalFactorB = evaluationOfEnvironmentalFactorB;
    }

    public String getEvaluationOfEnvironmentalFactorB() 
    {
        return evaluationOfEnvironmentalFactorB;
    }
    public void setEvaluationOfEnvironmentalFactorC(String evaluationOfEnvironmentalFactorC) 
    {
        this.evaluationOfEnvironmentalFactorC = evaluationOfEnvironmentalFactorC;
    }

    public String getEvaluationOfEnvironmentalFactorC() 
    {
        return evaluationOfEnvironmentalFactorC;
    }
    public void setEvaluationOfEnvironmentalFactorD(String evaluationOfEnvironmentalFactorD) 
    {
        this.evaluationOfEnvironmentalFactorD = evaluationOfEnvironmentalFactorD;
    }

    public String getEvaluationOfEnvironmentalFactorD() 
    {
        return evaluationOfEnvironmentalFactorD;
    }
    public void setEvaluationOfEnvironmentalFactorE(String evaluationOfEnvironmentalFactorE) 
    {
        this.evaluationOfEnvironmentalFactorE = evaluationOfEnvironmentalFactorE;
    }

    public String getEvaluationOfEnvironmentalFactorE() 
    {
        return evaluationOfEnvironmentalFactorE;
    }
    public void setEvaluationOfEnvironmentalFactorF(String evaluationOfEnvironmentalFactorF) 
    {
        this.evaluationOfEnvironmentalFactorF = evaluationOfEnvironmentalFactorF;
    }

    public String getEvaluationOfEnvironmentalFactorF() 
    {
        return evaluationOfEnvironmentalFactorF;
    }
    public void setEvaluationOfEnvironmentalFactorG(String evaluationOfEnvironmentalFactorG) 
    {
        this.evaluationOfEnvironmentalFactorG = evaluationOfEnvironmentalFactorG;
    }

    public String getEvaluationOfEnvironmentalFactorG() 
    {
        return evaluationOfEnvironmentalFactorG;
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
    public void setRelatedId(String relatedId) 
    {
        this.relatedId = relatedId;
    }

    public String getRelatedId() 
    {
        return relatedId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activity", getActivity())
            .append("environmentalFactor", getEnvironmentalFactor())
            .append("impactOfEnvironmentalFactor", getImpactOfEnvironmentalFactor())
            .append("evaluationOfEnvironmentalFactorA", getEvaluationOfEnvironmentalFactorA())
            .append("evaluationOfEnvironmentalFactorB", getEvaluationOfEnvironmentalFactorB())
            .append("evaluationOfEnvironmentalFactorC", getEvaluationOfEnvironmentalFactorC())
            .append("evaluationOfEnvironmentalFactorD", getEvaluationOfEnvironmentalFactorD())
            .append("evaluationOfEnvironmentalFactorE", getEvaluationOfEnvironmentalFactorE())
            .append("evaluationOfEnvironmentalFactorF", getEvaluationOfEnvironmentalFactorF())
            .append("evaluationOfEnvironmentalFactorG", getEvaluationOfEnvironmentalFactorG())
            .append("environmentalImpact", getEnvironmentalImpact())
            .append("tense", getTense())
            .append("controlMeasures", getControlMeasures())
            .append("statu", getStatu())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
