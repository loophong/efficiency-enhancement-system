package com.heli.device.maintenanceTable.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备故障分析对象 device_fault_analysis
 * 
 * @author YYY
 * @date 2025-01-17
 */
public class DeviceFaultAnalysis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分析主键id */
    @TableId(type = IdType.AUTO)
    private String analysisId;

    /** 故障类型名称 */
    @Excel(name = "故障类型名称")
    private String analysisName;

    /** 维修时间 */
    @Excel(name = "维修时间")
    private String analysisTime;

    /** 原因分析 */
    @Excel(name = "原因分析")
    private String analysisReason;

    /** 记录日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date analysisRecordTime;

    public void setAnalysisId(String analysisId) 
    {
        this.analysisId = analysisId;
    }

    public String getAnalysisId() 
    {
        return analysisId;
    }
    public void setAnalysisName(String analysisName) 
    {
        this.analysisName = analysisName;
    }

    public String getAnalysisName() 
    {
        return analysisName;
    }
    public void setAnalysisTime(String analysisTime) 
    {
        this.analysisTime = analysisTime;
    }

    public String getAnalysisTime() 
    {
        return analysisTime;
    }
    public void setAnalysisReason(String analysisReason) 
    {
        this.analysisReason = analysisReason;
    }

    public String getAnalysisReason() 
    {
        return analysisReason;
    }
    public void setAnalysisRecordTime(Date analysisRecordTime) 
    {
        this.analysisRecordTime = analysisRecordTime;
    }

    public Date getAnalysisRecordTime() 
    {
        return analysisRecordTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("analysisId", getAnalysisId())
            .append("analysisName", getAnalysisName())
            .append("analysisTime", getAnalysisTime())
            .append("analysisReason", getAnalysisReason())
            .append("analysisRecordTime", getAnalysisRecordTime())
            .toString();
    }
}
