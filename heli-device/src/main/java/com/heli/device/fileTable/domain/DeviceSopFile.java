package com.heli.device.fileTable.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * SOP文件管理对象 device_sop_file
 * 
 * @author YYY
 * @date 2025-01-19
 */
public class DeviceSopFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String sopFileId;

    /** 关联id */
    @Excel(name = "关联id")
    private Long sopCombineId;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String sopNum;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String sopName;

    /** 保养文件 */
    @Excel(name = "保养文件")
    private String sopMaintenance;

    /** 维修文件 */
    @Excel(name = "维修文件")
    private String sopRepair;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date upTime;

    /** 是否是历史版本 */
    @Excel(name = "是否是历史版本")
    private String ifHistory;

    public void setSopFileId(String sopFileId)
    {
        this.sopFileId = sopFileId;
    }

    public String getSopFileId()
    {
        return sopFileId;
    }
    public void setSopCombineId(Long sopCombineId)
    {
        this.sopCombineId = sopCombineId;
    }

    public Long getSopCombineId()
    {
        return sopCombineId;
    }
    public void setSopNum(String sopNum)
    {
        this.sopNum = sopNum;
    }

    public String getSopNum()
    {
        return sopNum;
    }
    public void setSopName(String sopName)
    {
        this.sopName = sopName;
    }

    public String getSopName()
    {
        return sopName;
    }
    public void setSopMaintenance(String sopMaintenance)
    {
        this.sopMaintenance = sopMaintenance;
    }

    public String getSopMaintenance()
    {
        return sopMaintenance;
    }
    public void setSopRepair(String sopRepair)
    {
        this.sopRepair = sopRepair;
    }

    public String getSopRepair()
    {
        return sopRepair;
    }
    public void setUpTime(Date upTime)
    {
        this.upTime = upTime;
    }

    public Date getUpTime()
    {
        return upTime;
    }
    public void setIfHistory(String ifHistory)
    {
        this.ifHistory = ifHistory;
    }

    public String getIfHistory()
    {
        return ifHistory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("sopFileId", getSopFileId())
                .append("sopCombineId", getSopCombineId())
                .append("sopNum", getSopNum())
                .append("sopName", getSopName())
                .append("sopMaintenance", getSopMaintenance())
                .append("sopRepair", getSopRepair())
                .append("upTime", getUpTime())
                .append("ifHistory", getIfHistory())
                .toString();
    }
}
