package com.ruoyi.security.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 相关方评价记录对象 security_related_party_evaluation_records
 * 
 * @author wang
 * @date 2025-03-05
 */
public class SecurityRelatedPartyEvaluationRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 评价内容 */
    @Excel(name = "评价内容")
    private String evaluationContent;

    /** 分值 */
    @Excel(name = "分值")
    private BigDecimal score;

    /** 评分 */
    @Excel(name = "评分")
    private String rating;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 参与评价人员 */
    @Excel(name = "参与评价人员")
    private String evaluators;

    /** 评价标准 */
    @Excel(name = "评价标准")
    private String evaluationCriteria;

    /** 评价建议 */
    @Excel(name = "评价建议")
    private String evaluationSuggestions;

    /** 审核标记 */
    @Excel(name = "审核标记")
    private String statu;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEvaluationContent(String evaluationContent) 
    {
        this.evaluationContent = evaluationContent;
    }

    public String getEvaluationContent() 
    {
        return evaluationContent;
    }
    public void setScore(BigDecimal score) 
    {
        this.score = score;
    }

    public BigDecimal getScore() 
    {
        return score;
    }
    public void setRating(String rating) 
    {
        this.rating = rating;
    }

    public String getRating() 
    {
        return rating;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setEvaluators(String evaluators) 
    {
        this.evaluators = evaluators;
    }

    public String getEvaluators() 
    {
        return evaluators;
    }
    public void setEvaluationCriteria(String evaluationCriteria) 
    {
        this.evaluationCriteria = evaluationCriteria;
    }

    public String getEvaluationCriteria() 
    {
        return evaluationCriteria;
    }
    public void setEvaluationSuggestions(String evaluationSuggestions) 
    {
        this.evaluationSuggestions = evaluationSuggestions;
    }

    public String getEvaluationSuggestions() 
    {
        return evaluationSuggestions;
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
            .append("evaluationContent", getEvaluationContent())
            .append("score", getScore())
            .append("rating", getRating())
            .append("remarks", getRemarks())
            .append("evaluators", getEvaluators())
            .append("evaluationCriteria", getEvaluationCriteria())
            .append("evaluationSuggestions", getEvaluationSuggestions())
            .append("statu", getStatu())
            .toString();
    }
}
