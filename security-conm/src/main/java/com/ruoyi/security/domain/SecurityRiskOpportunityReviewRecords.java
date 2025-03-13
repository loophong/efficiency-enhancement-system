package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 记录表对象 security_risk_opportunity_review_records
 * 
 * @author wang
 * @date 2025-03-11
 */
public class SecurityRiskOpportunityReviewRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 评审文件名称 */
    @Excel(name = "评审文件名称")
    private String reviewFileName;

    /** 评审日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评审日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewDate;

    /** 评审主要内容 */
    @Excel(name = "评审主要内容")
    private String reviewContent;

    /** 评审结论 */
    @Excel(name = "评审结论")
    private String reviewConclusion;

    /** 评审人 */
    @Excel(name = "评审人")
    private String reviewer;

    /** 存在问题及解决措施 */
    @Excel(name = "存在问题及解决措施")
    private String issuesAndSolutions;

    /** 科室1 */
    @Excel(name = "科室1")
    private String reviewDepartment1;

    /** 日期1 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期1", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewDepartmentTime1;

    /** 存在问题处理结果 */
    @Excel(name = "存在问题处理结果")
    private String issueResolutionResults;

    /** 科室2 */
    @Excel(name = "科室2")
    private String reviewDepartment2;

    /** 日期2 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期2", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewDepartmentTime2;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private Long status;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date statusTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setReviewFileName(String reviewFileName) 
    {
        this.reviewFileName = reviewFileName;
    }

    public String getReviewFileName() 
    {
        return reviewFileName;
    }
    public void setReviewDate(Date reviewDate) 
    {
        this.reviewDate = reviewDate;
    }

    public Date getReviewDate() 
    {
        return reviewDate;
    }
    public void setReviewContent(String reviewContent) 
    {
        this.reviewContent = reviewContent;
    }

    public String getReviewContent() 
    {
        return reviewContent;
    }
    public void setReviewConclusion(String reviewConclusion) 
    {
        this.reviewConclusion = reviewConclusion;
    }

    public String getReviewConclusion() 
    {
        return reviewConclusion;
    }
    public void setReviewer(String reviewer) 
    {
        this.reviewer = reviewer;
    }

    public String getReviewer() 
    {
        return reviewer;
    }
    public void setIssuesAndSolutions(String issuesAndSolutions) 
    {
        this.issuesAndSolutions = issuesAndSolutions;
    }

    public String getIssuesAndSolutions() 
    {
        return issuesAndSolutions;
    }
    public void setReviewDepartment1(String reviewDepartment1) 
    {
        this.reviewDepartment1 = reviewDepartment1;
    }

    public String getReviewDepartment1() 
    {
        return reviewDepartment1;
    }
    public void setReviewDepartmentTime1(Date reviewDepartmentTime1) 
    {
        this.reviewDepartmentTime1 = reviewDepartmentTime1;
    }

    public Date getReviewDepartmentTime1() 
    {
        return reviewDepartmentTime1;
    }
    public void setIssueResolutionResults(String issueResolutionResults) 
    {
        this.issueResolutionResults = issueResolutionResults;
    }

    public String getIssueResolutionResults() 
    {
        return issueResolutionResults;
    }
    public void setReviewDepartment2(String reviewDepartment2) 
    {
        this.reviewDepartment2 = reviewDepartment2;
    }

    public String getReviewDepartment2() 
    {
        return reviewDepartment2;
    }
    public void setReviewDepartmentTime2(Date reviewDepartmentTime2) 
    {
        this.reviewDepartmentTime2 = reviewDepartmentTime2;
    }

    public Date getReviewDepartmentTime2() 
    {
        return reviewDepartmentTime2;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setStatusTime(Date statusTime) 
    {
        this.statusTime = statusTime;
    }

    public Date getStatusTime() 
    {
        return statusTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("reviewFileName", getReviewFileName())
            .append("reviewDate", getReviewDate())
            .append("reviewContent", getReviewContent())
            .append("reviewConclusion", getReviewConclusion())
            .append("reviewer", getReviewer())
            .append("issuesAndSolutions", getIssuesAndSolutions())
            .append("reviewDepartment1", getReviewDepartment1())
            .append("reviewDepartmentTime1", getReviewDepartmentTime1())
            .append("issueResolutionResults", getIssueResolutionResults())
            .append("reviewDepartment2", getReviewDepartment2())
            .append("reviewDepartmentTime2", getReviewDepartmentTime2())
            .append("status", getStatus())
            .append("statusTime", getStatusTime())
            .toString();
    }
}
