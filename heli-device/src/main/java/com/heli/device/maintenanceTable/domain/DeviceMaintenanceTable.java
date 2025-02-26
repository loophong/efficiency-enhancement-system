package com.heli.device.maintenanceTable.domain;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 2.设备故障记录(导入)对象 device_maintenance_table
 *
 * @author YYY
 * @date 2025-01-20
 */
public class DeviceMaintenanceTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Long maintenanceTableId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 工单状态 */
    @Excel(name = "工单状态")
    private String workStatus;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceNum;

    /** 问题类型 */
    @Excel(name = "问题类型")
    private String issueType;

    /** 故障类型 */
    @Excel(name = "故障类型")
    private String faultType;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applyBy;

    /** 申请部门 */
    @Excel(name = "申请部门")
    private String applyDepartment;

    /** 维修人员 */
    @Excel(name = "维修人员")
    private String maintenancePeople;

    /** 故障现象 */
    @Excel(name = "故障现象")
    private String faultPhenomenon;

    /** 维修分析 */
    @Excel(name = "维修分析")
    private String maintenanceAnalysis;

    /** 维修内容 */
    @Excel(name = "维修内容")
    private String maintenanceContent;

    /** 报修时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报修时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportedTime;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date resolutionTime;

    /** 故障时长 */
    @Excel(name = "故障时长")
    private String faultDuration;

    /** 维修费用 */
    @Excel(name = "维修费用")
    private String maintenanceCast;

    /** 是否停机 */
    @Excel(name = "是否停机")
    private String ifDown;

    public void setMaintenanceTableId(Long maintenanceTableId)
    {
        this.maintenanceTableId = maintenanceTableId;
    }

    public Long getMaintenanceTableId()
    {
        return maintenanceTableId;
    }
    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName()
    {
        return deviceName;
    }
    public void setWorkStatus(String workStatus)
    {
        this.workStatus = workStatus;
    }

    public String getWorkStatus()
    {
        return workStatus;
    }
    public void setDeviceNum(String deviceNum)
    {
        this.deviceNum = deviceNum;
    }

    public String getDeviceNum()
    {
        return deviceNum;
    }
    public void setIssueType(String issueType)
    {
        this.issueType = issueType;
    }

    public String getIssueType()
    {
        return issueType;
    }
    public void setFaultType(String faultType)
    {
        this.faultType = faultType;
    }

    public String getFaultType()
    {
        return faultType;
    }
    public void setApplyBy(String applyBy)
    {
        this.applyBy = applyBy;
    }

    public String getApplyBy()
    {
        return applyBy;
    }
    public void setApplyDepartment(String applyDepartment)
    {
        this.applyDepartment = applyDepartment;
    }

    public String getApplyDepartment()
    {
        return applyDepartment;
    }
    public void setMaintenancePeople(String maintenancePeople)
    {
        this.maintenancePeople = maintenancePeople;
    }

    public String getMaintenancePeople()
    {
        return maintenancePeople;
    }
    public void setFaultPhenomenon(String faultPhenomenon)
    {
        this.faultPhenomenon = faultPhenomenon;
    }

    public String getFaultPhenomenon()
    {
        return faultPhenomenon;
    }
    public void setMaintenanceAnalysis(String maintenanceAnalysis)
    {
        this.maintenanceAnalysis = maintenanceAnalysis;
    }

    public String getMaintenanceAnalysis()
    {
        return maintenanceAnalysis;
    }
    public void setMaintenanceContent(String maintenanceContent)
    {
        this.maintenanceContent = maintenanceContent;
    }

    public String getMaintenanceContent()
    {
        return maintenanceContent;
    }
    public void setReportedTime(Date reportedTime)
    {
        this.reportedTime = reportedTime;
    }

    public Date getReportedTime()
    {
        return reportedTime;
    }
    public void setResolutionTime(Date resolutionTime)
    {
        this.resolutionTime = resolutionTime;
    }

    public Date getResolutionTime()
    {
        return resolutionTime;
    }
    public void setFaultDuration(String faultDuration)
    {
        this.faultDuration = faultDuration;
    }

    public String getFaultDuration()
    {
        return faultDuration;
    }
    public void setMaintenanceCast(String maintenanceCast)
    {
        this.maintenanceCast = maintenanceCast;
    }

    public String getMaintenanceCast()
    {
        return maintenanceCast;
    }
    public void setIfDown(String ifDown)
    {
        this.ifDown = ifDown;
    }

    public String getIfDown()
    {
        return ifDown;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("maintenanceTableId", getMaintenanceTableId())
                .append("deviceName", getDeviceName())
                .append("workStatus", getWorkStatus())
                .append("deviceNum", getDeviceNum())
                .append("issueType", getIssueType())
                .append("faultType", getFaultType())
                .append("applyBy", getApplyBy())
                .append("applyDepartment", getApplyDepartment())
                .append("maintenancePeople", getMaintenancePeople())
                .append("faultPhenomenon", getFaultPhenomenon())
                .append("maintenanceAnalysis", getMaintenanceAnalysis())
                .append("maintenanceContent", getMaintenanceContent())
                .append("reportedTime", getReportedTime())
                .append("resolutionTime", getResolutionTime())
                .append("faultDuration", getFaultDuration())
                .append("maintenanceCast", getMaintenanceCast())
                .append("ifDown", getIfDown())
                .toString();
    }
}
