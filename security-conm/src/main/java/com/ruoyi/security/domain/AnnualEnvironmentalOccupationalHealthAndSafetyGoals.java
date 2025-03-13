package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 年度环境职业健康安全目标、指标及工作分解对象 annual_environmental_occupational_health_and_safety_goals
 * 
 * @author wang
 * @date 2025-03-11
 */
public class AnnualEnvironmentalOccupationalHealthAndSafetyGoals extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long serialNumber;

    /** 部门 */
    @Excel(name = "部门")
    private String departmentName;

    /** 目标项 */
    @Excel(name = "目标项")
    private String goalItem;

    /** 目标值 */
    @Excel(name = "目标值")
    private String goalValue;

    /** 考核结果 */
    @Excel(name = "考核结果")
    private String evaluationResult;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 汇编人 */
    @Excel(name = "汇编人")
    private String compiler;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditor;

    /** 批准人 */
    @Excel(name = "批准人")
    private String approver;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private String status;

    public void setSerialNumber(Long serialNumber) 
    {
        this.serialNumber = serialNumber;
    }

    public Long getSerialNumber() 
    {
        return serialNumber;
    }
    public void setDepartmentName(String departmentName) 
    {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() 
    {
        return departmentName;
    }
    public void setGoalItem(String goalItem) 
    {
        this.goalItem = goalItem;
    }

    public String getGoalItem() 
    {
        return goalItem;
    }
    public void setGoalValue(String goalValue) 
    {
        this.goalValue = goalValue;
    }

    public String getGoalValue() 
    {
        return goalValue;
    }
    public void setEvaluationResult(String evaluationResult) 
    {
        this.evaluationResult = evaluationResult;
    }

    public String getEvaluationResult() 
    {
        return evaluationResult;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setCompiler(String compiler) 
    {
        this.compiler = compiler;
    }

    public String getCompiler() 
    {
        return compiler;
    }
    public void setAuditor(String auditor) 
    {
        this.auditor = auditor;
    }

    public String getAuditor() 
    {
        return auditor;
    }
    public void setApprover(String approver) 
    {
        this.approver = approver;
    }

    public String getApprover() 
    {
        return approver;
    }
    public void setDate(Date date) 
    {
        this.date = date;
    }

    public Date getDate() 
    {
        return date;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("serialNumber", getSerialNumber())
            .append("departmentName", getDepartmentName())
            .append("goalItem", getGoalItem())
            .append("goalValue", getGoalValue())
            .append("evaluationResult", getEvaluationResult())
            .append("remarks", getRemarks())
            .append("compiler", getCompiler())
            .append("auditor", getAuditor())
            .append("approver", getApprover())
            .append("date", getDate())
            .append("status", getStatus())
            .toString();
    }
}
