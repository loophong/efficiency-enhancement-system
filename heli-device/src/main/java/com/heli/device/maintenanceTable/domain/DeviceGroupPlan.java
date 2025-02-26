package com.heli.device.maintenanceTable.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班组计划保养对象 device_group_plan
 * 
 * @author YYY
 * @date 2025-01-19
 */
public class DeviceGroupPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String groupId;

    /** 序号 */
    @Excel(name = "序号")
    private String orderNum;

    /** 设备类别 */
    @Excel(name = "设备类别")
    private String deviceType;

    /** 执行班组 */
    @Excel(name = "执行班组")
    private String executeGroup;

    /** 保养内容 */
    @Excel(name = "保养内容")
    private String maintenanceContent;

    /** 保养周期 */
    @Excel(name = "保养周期")
    private String maintenanceCycle;

    /** 月度(1W) */
    @Excel(name = "月度(1W)")
    private String monthOne;

    /** 月度(2W) */
    @Excel(name = "月度(2W)")
    private String monthTwo;

    /** 月度(3W) */
    @Excel(name = "月度(3W)")
    private String monthThree;

    /** 月度(4W) */
    @Excel(name = "月度(4W)")
    private String monthFour;

    public void setGroupId(String groupId) 
    {
        this.groupId = groupId;
    }

    public String getGroupId() 
    {
        return groupId;
    }
    public void setOrderNum(String orderNum) 
    {
        this.orderNum = orderNum;
    }

    public String getOrderNum() 
    {
        return orderNum;
    }
    public void setDeviceType(String deviceType) 
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType() 
    {
        return deviceType;
    }
    public void setExecuteGroup(String executeGroup) 
    {
        this.executeGroup = executeGroup;
    }

    public String getExecuteGroup() 
    {
        return executeGroup;
    }
    public void setMaintenanceContent(String maintenanceContent) 
    {
        this.maintenanceContent = maintenanceContent;
    }

    public String getMaintenanceContent() 
    {
        return maintenanceContent;
    }
    public void setMaintenanceCycle(String maintenanceCycle) 
    {
        this.maintenanceCycle = maintenanceCycle;
    }

    public String getMaintenanceCycle() 
    {
        return maintenanceCycle;
    }
    public void setMonthOne(String monthOne) 
    {
        this.monthOne = monthOne;
    }

    public String getMonthOne() 
    {
        return monthOne;
    }
    public void setMonthTwo(String monthTwo) 
    {
        this.monthTwo = monthTwo;
    }

    public String getMonthTwo() 
    {
        return monthTwo;
    }
    public void setMonthThree(String monthThree) 
    {
        this.monthThree = monthThree;
    }

    public String getMonthThree() 
    {
        return monthThree;
    }
    public void setMonthFour(String monthFour) 
    {
        this.monthFour = monthFour;
    }

    public String getMonthFour() 
    {
        return monthFour;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("groupId", getGroupId())
            .append("orderNum", getOrderNum())
            .append("deviceType", getDeviceType())
            .append("executeGroup", getExecuteGroup())
            .append("maintenanceContent", getMaintenanceContent())
            .append("maintenanceCycle", getMaintenanceCycle())
            .append("monthOne", getMonthOne())
            .append("monthTwo", getMonthTwo())
            .append("monthThree", getMonthThree())
            .append("monthFour", getMonthFour())
            .toString();
    }
}
