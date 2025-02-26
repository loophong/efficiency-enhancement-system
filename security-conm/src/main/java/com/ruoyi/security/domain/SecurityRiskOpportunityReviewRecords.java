package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 记录表对象 security_risk_opportunity_review_records
 * 
 * @author wang
 * @date 2025-02-23
 */
@Data
public class SecurityRiskOpportunityReviewRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 记录文件 */
    @Excel(name = "记录文件")
    private String reviewFileName;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewDate;

    /** 记录内容 */
    @Excel(name = "记录内容")
    private String reviewContent;

    /** 记录结论 */
    @Excel(name = "记录结论")
    private String reviewConclusion;

    /** 评审人 */
    @Excel(name = "评审人")
    private String reviewer;

    /** 评审结论 */
    @Excel(name = "评审结论")
    private String issuesAndSolutions;

    /** 评审部门1 */
    @Excel(name = "评审部门1")
    private String reviewDepartment1;

    /** 评审时间1 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评审时间1", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewDepartmentTime1;

    /** 评审结果 */
    @Excel(name = "评审结果")
    private String issueResolutionResults;

    /** 评审部门2 */
    @Excel(name = "评审部门2")
    private String reviewDepartment2;

    /** 评审时间2 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评审时间2", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewDepartmentTime2;

}
