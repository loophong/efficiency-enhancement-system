package com.heli.device.maintenanceTable.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 专业计划保养对象 device_major_plan
 * 
 * @author YYY
 * @date 2025-02-18
 */
public class DeviceMajorPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String majorId;

    /** 序号 */
    @Excel(name = "序号")
    private String majorOrder;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String majorName;

    /** 部位 */
    @Excel(name = "部位")
    private String majorPosition;

    /** 保养维修项目 */
    @Excel(name = "保养维修项目")
    private String majorProject;

    /** 周期 */
    @Excel(name = "周期")
    private String majorCycleNum;

    /** 人员 */
    @Excel(name = "人员")
    private String majorPeople;

    /** 月份 */
    @Excel(name = "月份")
    private String majorMonth;

    /** 第几周 */
    @Excel(name = "第几周")
    private String majorMonthWeek;

    /** 内容 */
    @Excel(name = "内容")
    private String majorContent;

    public void setMajorId(String majorId) 
    {
        this.majorId = majorId;
    }

    public String getMajorId() 
    {
        return majorId;
    }
    public void setMajorOrder(String majorOrder) 
    {
        this.majorOrder = majorOrder;
    }

    public String getMajorOrder() 
    {
        return majorOrder;
    }
    public void setMajorName(String majorName) 
    {
        this.majorName = majorName;
    }

    public String getMajorName() 
    {
        return majorName;
    }
    public void setMajorPosition(String majorPosition) 
    {
        this.majorPosition = majorPosition;
    }

    public String getMajorPosition() 
    {
        return majorPosition;
    }
    public void setMajorProject(String majorProject) 
    {
        this.majorProject = majorProject;
    }

    public String getMajorProject() 
    {
        return majorProject;
    }
    public void setMajorCycleNum(String majorCycleNum) 
    {
        this.majorCycleNum = majorCycleNum;
    }

    public String getMajorCycleNum() 
    {
        return majorCycleNum;
    }
    public void setMajorPeople(String majorPeople) 
    {
        this.majorPeople = majorPeople;
    }

    public String getMajorPeople() 
    {
        return majorPeople;
    }
    public void setMajorMonth(String majorMonth) 
    {
        this.majorMonth = majorMonth;
    }

    public String getMajorMonth() 
    {
        return majorMonth;
    }
    public void setMajorMonthWeek(String majorMonthWeek) 
    {
        this.majorMonthWeek = majorMonthWeek;
    }

    public String getMajorMonthWeek() 
    {
        return majorMonthWeek;
    }
    public void setMajorContent(String majorContent) 
    {
        this.majorContent = majorContent;
    }

    public String getMajorContent() 
    {
        return majorContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("majorId", getMajorId())
            .append("majorOrder", getMajorOrder())
            .append("majorName", getMajorName())
            .append("majorPosition", getMajorPosition())
            .append("majorProject", getMajorProject())
            .append("majorCycleNum", getMajorCycleNum())
            .append("majorPeople", getMajorPeople())
            .append("majorMonth", getMajorMonth())
            .append("majorMonthWeek", getMajorMonthWeek())
            .append("majorContent", getMajorContent())
            .toString();
    }
}
