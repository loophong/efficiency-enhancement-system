package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 年度培训计划对象 security_education_annual_training_plan
 * 
 * @author wang
 * @date 2025-03-02
 */
public class SecurityEducationAnnualTrainingPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 培训项目1 */
    @Excel(name = "培训项目1")
    private String trainingProject1;

    /** 培训项目2 */
    @Excel(name = "培训项目2")
    private String trainingProject2;

    /** 培训对象 */
    @Excel(name = "培训对象")
    private String trainees;

    /** 培训类别/内容 */
    @Excel(name = "培训类别/内容")
    private String trainingCategoryContent;

    /** 培训方式 */
    @Excel(name = "培训方式")
    private String trainingMethod;

    /** 课时 */
    @Excel(name = "课时")
    private Long hours;

    /** 培训月份 */
    @Excel(name = "培训月份")
    private String trainingMonth;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String assessmentMethod;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTrainingProject1(String trainingProject1) 
    {
        this.trainingProject1 = trainingProject1;
    }

    public String getTrainingProject1() 
    {
        return trainingProject1;
    }
    public void setTrainingProject2(String trainingProject2) 
    {
        this.trainingProject2 = trainingProject2;
    }

    public String getTrainingProject2() 
    {
        return trainingProject2;
    }
    public void setTrainees(String trainees) 
    {
        this.trainees = trainees;
    }

    public String getTrainees() 
    {
        return trainees;
    }
    public void setTrainingCategoryContent(String trainingCategoryContent) 
    {
        this.trainingCategoryContent = trainingCategoryContent;
    }

    public String getTrainingCategoryContent() 
    {
        return trainingCategoryContent;
    }
    public void setTrainingMethod(String trainingMethod) 
    {
        this.trainingMethod = trainingMethod;
    }

    public String getTrainingMethod() 
    {
        return trainingMethod;
    }
    public void setHours(Long hours) 
    {
        this.hours = hours;
    }

    public Long getHours() 
    {
        return hours;
    }
    public void setTrainingMonth(String trainingMonth) 
    {
        this.trainingMonth = trainingMonth;
    }

    public String getTrainingMonth() 
    {
        return trainingMonth;
    }
    public void setAssessmentMethod(String assessmentMethod) 
    {
        this.assessmentMethod = assessmentMethod;
    }

    public String getAssessmentMethod() 
    {
        return assessmentMethod;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("trainingProject1", getTrainingProject1())
            .append("trainingProject2", getTrainingProject2())
            .append("trainees", getTrainees())
            .append("trainingCategoryContent", getTrainingCategoryContent())
            .append("trainingMethod", getTrainingMethod())
            .append("hours", getHours())
            .append("trainingMonth", getTrainingMonth())
            .append("assessmentMethod", getAssessmentMethod())
            .append("remarks", getRemarks())
            .toString();
    }
}
