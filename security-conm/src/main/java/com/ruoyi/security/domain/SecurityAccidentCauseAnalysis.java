package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 事故原因分析记录对象 security_accident_cause_analysis
 * 
 * @author wang
 * @date 2025-03-13
 */
public class SecurityAccidentCauseAnalysis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 类别 */
    @Excel(name = "类别")
    private String category;

    /** 项目 */
    @Excel(name = "项目")
    private String project;

    /** 具体内容 */
    @Excel(name = "具体内容")
    private String specificContent;

    /** 分析结果 */
    @Excel(name = "分析结果")
    private String analysisResult;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }
    public void setProject(String project) 
    {
        this.project = project;
    }

    public String getProject() 
    {
        return project;
    }
    public void setSpecificContent(String specificContent) 
    {
        this.specificContent = specificContent;
    }

    public String getSpecificContent() 
    {
        return specificContent;
    }
    public void setAnalysisResult(String analysisResult) 
    {
        this.analysisResult = analysisResult;
    }

    public String getAnalysisResult() 
    {
        return analysisResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("category", getCategory())
            .append("project", getProject())
            .append("specificContent", getSpecificContent())
            .append("analysisResult", getAnalysisResult())
            .toString();
    }
}
