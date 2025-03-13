package com.ruoyi.security.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全知识考核对象 security_knowledge_assessment
 * 
 * @author wang
 * @date 2025-03-02
 */
public class SecurityKnowledgeAssessment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 考试名称 */
    @Excel(name = "考试名称")
    private String examineeName;

    /** 笔试成绩 */
    @Excel(name = "笔试成绩")
    private BigDecimal writtenScore;

    /** 实操成绩 */
    @Excel(name = "实操成绩")
    private BigDecimal practicalScore;

    /** 其他 */
    @Excel(name = "其他")
    private String other;

    /** 是否合格 */
    @Excel(name = "是否合格")
    private Integer isQualified;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 考核内容 */
    @Excel(name = "考核内容")
    private String assessmentContent;

    /** 考试时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考试时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date examinationDate;

    /** 考察形式 */
    @Excel(name = "考察形式")
    private String assessmentForm;

    /** 考核评价人 */
    @Excel(name = "考核评价人")
    private String assessor;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setExamineeName(String examineeName) 
    {
        this.examineeName = examineeName;
    }

    public String getExamineeName() 
    {
        return examineeName;
    }
    public void setWrittenScore(BigDecimal writtenScore) 
    {
        this.writtenScore = writtenScore;
    }

    public BigDecimal getWrittenScore() 
    {
        return writtenScore;
    }
    public void setPracticalScore(BigDecimal practicalScore) 
    {
        this.practicalScore = practicalScore;
    }

    public BigDecimal getPracticalScore() 
    {
        return practicalScore;
    }
    public void setOther(String other) 
    {
        this.other = other;
    }

    public String getOther() 
    {
        return other;
    }
    public void setIsQualified(Integer isQualified) 
    {
        this.isQualified = isQualified;
    }

    public Integer getIsQualified() 
    {
        return isQualified;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setAssessmentContent(String assessmentContent) 
    {
        this.assessmentContent = assessmentContent;
    }

    public String getAssessmentContent() 
    {
        return assessmentContent;
    }
    public void setExaminationDate(Date examinationDate) 
    {
        this.examinationDate = examinationDate;
    }

    public Date getExaminationDate() 
    {
        return examinationDate;
    }
    public void setAssessmentForm(String assessmentForm) 
    {
        this.assessmentForm = assessmentForm;
    }

    public String getAssessmentForm() 
    {
        return assessmentForm;
    }
    public void setAssessor(String assessor) 
    {
        this.assessor = assessor;
    }

    public String getAssessor() 
    {
        return assessor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("examineeName", getExamineeName())
            .append("writtenScore", getWrittenScore())
            .append("practicalScore", getPracticalScore())
            .append("other", getOther())
            .append("isQualified", getIsQualified())
            .append("remarks", getRemarks())
            .append("assessmentContent", getAssessmentContent())
            .append("examinationDate", getExaminationDate())
            .append("assessmentForm", getAssessmentForm())
            .append("assessor", getAssessor())
            .toString();
    }
}
