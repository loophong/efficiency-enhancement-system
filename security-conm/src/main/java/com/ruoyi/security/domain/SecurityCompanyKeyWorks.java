package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 公司重点工作对象 security_company_key_works
 * 
 * @author wang
 * @date 2025-02-27
 */
public class SecurityCompanyKeyWorks extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 重点工作名称 */
    @Excel(name = "重点工作名称")
    private String keyWorkName;

    /** 现状及要因  */
    @Excel(name = "现状及要因 ")
    private String currentStatusAndCauses;

    /** 目标 */
    @Excel(name = "目标")
    private String target;

    /** 权重1 */
    @Excel(name = "权重1")
    private String weight1;

    /** 权重2 */
    @Excel(name = "权重2")
    private String weight2;

    /** 一季度重点实施事项 	 */
    @Excel(name = "一季度重点实施事项 	")
    private String q1FocusImplementation;

    /** 完成结果 */
    @Excel(name = "完成结果")
    private String q1CompletionResults;

    /** 得分 */
    @Excel(name = "得分")
    private String q1Score;

    /** 二季度重点实施事项 	 */
    @Excel(name = "二季度重点实施事项 	")
    private String q2FocusImplementation;

    /** 完成结果 */
    @Excel(name = "完成结果")
    private String q2CompletionResults;

    /** 得分 */
    @Excel(name = "得分")
    private String q2Score;

    /** 三季度重点实施事项 */
    @Excel(name = "三季度重点实施事项")
    private String q3FocusImplementation;

    /** 完成结果 */
    @Excel(name = "完成结果")
    private String q3CompletionResults;

    /** 得分 */
    @Excel(name = "得分")
    private String q3Score;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setKeyWorkName(String keyWorkName) 
    {
        this.keyWorkName = keyWorkName;
    }

    public String getKeyWorkName() 
    {
        return keyWorkName;
    }
    public void setCurrentStatusAndCauses(String currentStatusAndCauses) 
    {
        this.currentStatusAndCauses = currentStatusAndCauses;
    }

    public String getCurrentStatusAndCauses() 
    {
        return currentStatusAndCauses;
    }
    public void setTarget(String target) 
    {
        this.target = target;
    }

    public String getTarget() 
    {
        return target;
    }
    public void setWeight1(String weight1) 
    {
        this.weight1 = weight1;
    }

    public String getWeight1() 
    {
        return weight1;
    }
    public void setWeight2(String weight2) 
    {
        this.weight2 = weight2;
    }

    public String getWeight2() 
    {
        return weight2;
    }
    public void setQ1FocusImplementation(String q1FocusImplementation) 
    {
        this.q1FocusImplementation = q1FocusImplementation;
    }

    public String getQ1FocusImplementation() 
    {
        return q1FocusImplementation;
    }
    public void setQ1CompletionResults(String q1CompletionResults) 
    {
        this.q1CompletionResults = q1CompletionResults;
    }

    public String getQ1CompletionResults() 
    {
        return q1CompletionResults;
    }
    public void setQ1Score(String q1Score) 
    {
        this.q1Score = q1Score;
    }

    public String getQ1Score() 
    {
        return q1Score;
    }
    public void setQ2FocusImplementation(String q2FocusImplementation) 
    {
        this.q2FocusImplementation = q2FocusImplementation;
    }

    public String getQ2FocusImplementation() 
    {
        return q2FocusImplementation;
    }
    public void setQ2CompletionResults(String q2CompletionResults) 
    {
        this.q2CompletionResults = q2CompletionResults;
    }

    public String getQ2CompletionResults() 
    {
        return q2CompletionResults;
    }
    public void setQ2Score(String q2Score) 
    {
        this.q2Score = q2Score;
    }

    public String getQ2Score() 
    {
        return q2Score;
    }
    public void setQ3FocusImplementation(String q3FocusImplementation) 
    {
        this.q3FocusImplementation = q3FocusImplementation;
    }

    public String getQ3FocusImplementation() 
    {
        return q3FocusImplementation;
    }
    public void setQ3CompletionResults(String q3CompletionResults) 
    {
        this.q3CompletionResults = q3CompletionResults;
    }

    public String getQ3CompletionResults() 
    {
        return q3CompletionResults;
    }
    public void setQ3Score(String q3Score) 
    {
        this.q3Score = q3Score;
    }

    public String getQ3Score() 
    {
        return q3Score;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitName", getUnitName())
            .append("keyWorkName", getKeyWorkName())
            .append("currentStatusAndCauses", getCurrentStatusAndCauses())
            .append("target", getTarget())
            .append("weight1", getWeight1())
            .append("weight2", getWeight2())
            .append("q1FocusImplementation", getQ1FocusImplementation())
            .append("q1CompletionResults", getQ1CompletionResults())
            .append("q1Score", getQ1Score())
            .append("q2FocusImplementation", getQ2FocusImplementation())
            .append("q2CompletionResults", getQ2CompletionResults())
            .append("q2Score", getQ2Score())
            .append("q3FocusImplementation", getQ3FocusImplementation())
            .append("q3CompletionResults", getQ3CompletionResults())
            .append("q3Score", getQ3Score())
            .toString();
    }
}
