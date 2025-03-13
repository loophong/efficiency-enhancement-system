package com.heli.device.maintenanceTable.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 记录对象 device_maintenance_record
 * 
 * @author YYY
 * @date 2025-03-04
 */
public class DeviceMaintenanceRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主ID */
    private Long maintenanceId;

    /** 关联主计划id */
    @Excel(name = "关联主计划id")
    private Long maintenancePlanId;

    /** 关联月份数据 */
    @Excel(name = "关联月份数据")
    private String planMonthTime;

    /** 完成情况描述 */
    @Excel(name = "完成情况描述")
    private String completionDescription;

    /** 照片附件 */
    @Excel(name = "照片附件")
    private String imageAttachment;

    /** 遗留问题描述 */
    @Excel(name = "遗留问题描述")
    private String remainingIssuesDescription;

    /** 遗留问题分析 */
    @Excel(name = "遗留问题分析")
    private String remainingIssuesAnalysis;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private String maintenanceExamineStatus;

    /** 审核意见 */
    @Excel(name = "审核意见")
    private String maintenanceExamineComment;

    /** 截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date maintenanceDeadline;

    /** 班组或自主 */
    @Excel(name = "班组或自主")
    private String majorGroup;

    public void setMaintenanceId(Long maintenanceId) 
    {
        this.maintenanceId = maintenanceId;
    }

    public Long getMaintenanceId() 
    {
        return maintenanceId;
    }
    public void setMaintenancePlanId(Long maintenancePlanId) 
    {
        this.maintenancePlanId = maintenancePlanId;
    }

    public Long getMaintenancePlanId() 
    {
        return maintenancePlanId;
    }
    public void setPlanMonthTime(String planMonthTime) 
    {
        this.planMonthTime = planMonthTime;
    }

    public String getPlanMonthTime() 
    {
        return planMonthTime;
    }
    public void setCompletionDescription(String completionDescription) 
    {
        this.completionDescription = completionDescription;
    }

    public String getCompletionDescription() 
    {
        return completionDescription;
    }
    public void setImageAttachment(String imageAttachment) 
    {
        this.imageAttachment = imageAttachment;
    }

    public String getImageAttachment() 
    {
        return imageAttachment;
    }
    public void setRemainingIssuesDescription(String remainingIssuesDescription) 
    {
        this.remainingIssuesDescription = remainingIssuesDescription;
    }

    public String getRemainingIssuesDescription() 
    {
        return remainingIssuesDescription;
    }
    public void setRemainingIssuesAnalysis(String remainingIssuesAnalysis) 
    {
        this.remainingIssuesAnalysis = remainingIssuesAnalysis;
    }

    public String getRemainingIssuesAnalysis() 
    {
        return remainingIssuesAnalysis;
    }
    public void setMaintenanceExamineStatus(String maintenanceExamineStatus) 
    {
        this.maintenanceExamineStatus = maintenanceExamineStatus;
    }

    public String getMaintenanceExamineStatus() 
    {
        return maintenanceExamineStatus;
    }
    public void setMaintenanceExamineComment(String maintenanceExamineComment) 
    {
        this.maintenanceExamineComment = maintenanceExamineComment;
    }

    public String getMaintenanceExamineComment() 
    {
        return maintenanceExamineComment;
    }
    public void setMaintenanceDeadline(Date maintenanceDeadline) 
    {
        this.maintenanceDeadline = maintenanceDeadline;
    }

    public Date getMaintenanceDeadline() 
    {
        return maintenanceDeadline;
    }

    public void setMajorGroup(String majorGroup)
    {
        this.majorGroup = majorGroup;
    }

    public String getMajorGroup()
    {
        return majorGroup;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("maintenanceId", getMaintenanceId())
            .append("maintenancePlanId", getMaintenancePlanId())
            .append("planMonthTime", getPlanMonthTime())
            .append("completionDescription", getCompletionDescription())
            .append("imageAttachment", getImageAttachment())
            .append("remainingIssuesDescription", getRemainingIssuesDescription())
            .append("remainingIssuesAnalysis", getRemainingIssuesAnalysis())
            .append("maintenanceExamineStatus", getMaintenanceExamineStatus())
            .append("maintenanceExamineComment", getMaintenanceExamineComment())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("maintenanceDeadline", getMaintenanceDeadline())
            .append("majorGroup", getMajorGroup())
            .toString();
    }
}
