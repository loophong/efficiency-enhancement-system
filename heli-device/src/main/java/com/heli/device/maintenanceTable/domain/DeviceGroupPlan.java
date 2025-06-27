package com.heli.device.maintenanceTable.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.core.domain.BaseEntity;


/**
 * 班组计划保养对象 device_group_plan
 * 
 * @author YYY
 * @date 2025-01-19
 */
@TableName(value ="device_group_plan")
@Data
public class DeviceGroupPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private String groupId;

    /** 序号 */
    @ExcelProperty(index = 0)
    private String orderNum;

    /** 设备类别 */
    @ExcelProperty(index = 1)
    private String deviceType;

    /** 执行班组 */
    @ExcelProperty(index = 2)
    private String executeGroup;

    /** 保养内容 */
    @ExcelProperty(index = 3)
    private String maintenanceContent;

    /** 保养周期 */
    @ExcelProperty(index = 4)
    private String maintenanceCycle;

    /** 月度(1W) */
    @ExcelProperty(index = 5)
    private String monthOne;

    /** 月度(2W) */
    @ExcelProperty(index = 6)
    private String monthTwo;

    /** 月度(3W) */
    @ExcelProperty(index = 7)
    private String monthThree;

    /** 月度(4W) */
    @ExcelProperty(index = 8)
    private String monthFour;

    @ExcelIgnore
    private String lastCompleteTime;

    @ExcelIgnore
    private String rollTime;

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

    public void setLastCompleteTime(String lastCompleteTime)
    {
        this.lastCompleteTime = lastCompleteTime;
    }

    public String getLastCompleteTime()
    {
        return lastCompleteTime;
    }

    public void setRollTime(String rollTime)
    {
        this.rollTime = rollTime;
    }

    public String getRollTime()
    {
        return rollTime;
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
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("lastCompleteTime", getLastCompleteTime())
            .toString();
    }
}
