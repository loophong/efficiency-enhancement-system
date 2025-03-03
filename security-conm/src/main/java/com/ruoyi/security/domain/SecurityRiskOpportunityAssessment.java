package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.security.core.userdetails.User;

/**
 * 风险和机遇评估及控制措施对象 security_risk_opportunity_assessment
 *
 * @author wang
 * @date 2025-02-23
 */
@Data
public class SecurityRiskOpportunityAssessment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 活动 */
    @Excel(name = "活动")
    private String activity;

    /** 风险和机遇 */
    @Excel(name = "风险和机遇")
    private String riskOpportunity;

    /** 造成后果 */
    @Excel(name = "造成后果")
    private String consequences;

    /** 评价 */
    @Excel(name = "评价")
    private String evaluation;

    /** 风险等级 */
    @Excel(name = "风险等级")
    private String riskLevel;

    /** 应对措施 */
    @Excel(name = "应对措施")
    private String responseMeasures;

    /** 实施时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实施时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date implementationTime;

    /** 部门 */
    @Excel(name = "部门")
    private String department;

    private Integer status;

}
