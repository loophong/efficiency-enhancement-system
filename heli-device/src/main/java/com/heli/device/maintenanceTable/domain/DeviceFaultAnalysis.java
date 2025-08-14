package com.heli.device.maintenanceTable.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 设备故障分析对象 device_fault_analysis
 * 
 * @author YYY
 * @date 2025-01-17
 */
@TableName(value ="device_fault_analysis")
@Data
public class DeviceFaultAnalysis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分析主键id */
    private String analysisId;

    /** 故障类型名称 */

    private String analysisName;

    /** 记录日期 */

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date analysisRecordTime;

    /** 原因分析 */
    private String maintenanceAnalysis;

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
    public void setAnalysisRecordTime(Date analysisRecordTime)
    {
        this.analysisRecordTime = analysisRecordTime;
    }

    public Date getAnalysisRecordTime()
    {
        return analysisRecordTime;
    }
    public void setMaintenanceAnalysis(String maintenanceAnalysis)
    {
        this.maintenanceAnalysis = maintenanceAnalysis;
    }

    public String getMaintenanceAnalysis()
    {
        return maintenanceAnalysis;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("analysisId", getAnalysisId())
                .append("analysisName", getAnalysisName())
                .append("analysisRecordTime", getAnalysisRecordTime())
                .append("maintenanceAnalysis", getMaintenanceAnalysis())
                .toString();
    }
}
