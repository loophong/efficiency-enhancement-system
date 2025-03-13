package com.heli.device.fileTable.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备基础档案对象 device_basic_file
 *
 * @author YYY
 * @date 2025-01-20
 */
public class DeviceBasicFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String basicFileId;

    /** 关联保养id */
    @Excel(name = "关联保养id")
    private Long basicCombineMaintenance;

    /** 关联维修id（多个） */
    @Excel(name = "关联维修id", readConverterExp = "多=个")
    private String basicCombineRepair;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceNum;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 档案类型 */
    @Excel(name = "档案类型")
    private String fileType;

    /** 上传日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date upTime;

    /** 版本号 */
    @Excel(name = "版本号")
    private String versionId;

    /** 保养文件信息 */
    @Excel(name = "保养文件信息")
    private String fileInfo;

    /** 维修文件信息 */
    @Excel(name = "维修文件信息")
    private String fileInfoRepair;

    /** 是否是历史版本 */
    @Excel(name = "是否是历史版本")
    private String ifHistory;

    public void setBasicFileId(String basicFileId)
    {
        this.basicFileId = basicFileId;
    }

    public String getBasicFileId()
    {
        return basicFileId;
    }
    public void setBasicCombineMaintenance(Long basicCombineMaintenance)
    {
        this.basicCombineMaintenance = basicCombineMaintenance;
    }

    public Long getBasicCombineMaintenance()
    {
        return basicCombineMaintenance;
    }
    public void setBasicCombineRepair(String basicCombineRepair)
    {
        this.basicCombineRepair = basicCombineRepair;
    }

    public String getBasicCombineRepair()
    {
        return basicCombineRepair;
    }
    public void setDeviceNum(String deviceNum)
    {
        this.deviceNum = deviceNum;
    }

    public String getDeviceNum()
    {
        return deviceNum;
    }
    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName()
    {
        return deviceName;
    }
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getFileType()
    {
        return fileType;
    }
    public void setUpTime(Date upTime)
    {
        this.upTime = upTime;
    }

    public Date getUpTime()
    {
        return upTime;
    }
    public void setVersionId(String versionId)
    {
        this.versionId = versionId;
    }

    public String getVersionId()
    {
        return versionId;
    }
    public void setFileInfo(String fileInfo)
    {
        this.fileInfo = fileInfo;
    }

    public String getFileInfo()
    {
        return fileInfo;
    }
    public void setFileInfoRepair(String fileInfoRepair)
    {
        this.fileInfoRepair = fileInfoRepair;
    }

    public String getFileInfoRepair()
    {
        return fileInfoRepair;
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
                .append("basicFileId", getBasicFileId())
                .append("basicCombineMaintenance", getBasicCombineMaintenance())
                .append("basicCombineRepair", getBasicCombineRepair())
                .append("deviceNum", getDeviceNum())
                .append("deviceName", getDeviceName())
                .append("fileType", getFileType())
                .append("upTime", getUpTime())
                .append("versionId", getVersionId())
                .append("fileInfo", getFileInfo())
                .append("fileInfoRepair", getFileInfoRepair())
                .append("ifHistory", getIfHistory())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
