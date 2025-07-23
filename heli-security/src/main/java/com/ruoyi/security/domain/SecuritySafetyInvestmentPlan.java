package com.ruoyi.security.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全投入计划对象 security_safety_investment_plan
 * 
 * @author wang
 * @date 2025-03-13
 */
public class SecuritySafetyInvestmentPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long serialNumber;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 2024年预算合计 */
    @Excel(name = "2024年预算合计")
    private BigDecimal budgetTotal2024;

    /** 2024年第一季度 */
    @Excel(name = "2024年第一季度")
    private BigDecimal budgetQ12024;

    /** 2024年第二季度 */
    @Excel(name = "2024年第二季度")
    private BigDecimal budgetQ22024;

    /** 2024年第三季度 */
    @Excel(name = "2024年第三季度")
    private BigDecimal budgetQ32024;

    /** 2024年第四季度 */
    @Excel(name = "2024年第四季度")
    private BigDecimal budgetQ42024;

    /** 预算编制依据或简要说明 */
    @Excel(name = "预算编制依据或简要说明")
    private String budgetBasis;

    public void setSerialNumber(Long serialNumber) 
    {
        this.serialNumber = serialNumber;
    }

    public Long getSerialNumber() 
    {
        return serialNumber;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setBudgetTotal2024(BigDecimal budgetTotal2024) 
    {
        this.budgetTotal2024 = budgetTotal2024;
    }

    public BigDecimal getBudgetTotal2024() 
    {
        return budgetTotal2024;
    }
    public void setBudgetQ12024(BigDecimal budgetQ12024) 
    {
        this.budgetQ12024 = budgetQ12024;
    }

    public BigDecimal getBudgetQ12024() 
    {
        return budgetQ12024;
    }
    public void setBudgetQ22024(BigDecimal budgetQ22024) 
    {
        this.budgetQ22024 = budgetQ22024;
    }

    public BigDecimal getBudgetQ22024() 
    {
        return budgetQ22024;
    }
    public void setBudgetQ32024(BigDecimal budgetQ32024) 
    {
        this.budgetQ32024 = budgetQ32024;
    }

    public BigDecimal getBudgetQ32024() 
    {
        return budgetQ32024;
    }
    public void setBudgetQ42024(BigDecimal budgetQ42024) 
    {
        this.budgetQ42024 = budgetQ42024;
    }

    public BigDecimal getBudgetQ42024() 
    {
        return budgetQ42024;
    }
    public void setBudgetBasis(String budgetBasis) 
    {
        this.budgetBasis = budgetBasis;
    }

    public String getBudgetBasis() 
    {
        return budgetBasis;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("serialNumber", getSerialNumber())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("budgetTotal2024", getBudgetTotal2024())
            .append("budgetQ12024", getBudgetQ12024())
            .append("budgetQ22024", getBudgetQ22024())
            .append("budgetQ32024", getBudgetQ32024())
            .append("budgetQ42024", getBudgetQ42024())
            .append("budgetBasis", getBudgetBasis())
            .toString();
    }
}
