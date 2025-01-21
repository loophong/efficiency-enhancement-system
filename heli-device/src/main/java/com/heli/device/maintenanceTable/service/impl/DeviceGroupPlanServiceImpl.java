package com.heli.device.maintenanceTable.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.maintenanceTable.mapper.DeviceGroupPlanMapper;
import com.heli.device.maintenanceTable.domain.DeviceGroupPlan;
import com.heli.device.maintenanceTable.service.IDeviceGroupPlanService;

/**
 * 班组计划保养Service业务层处理
 * 
 * @author YYY
 * @date 2025-01-19
 */
@Service
public class DeviceGroupPlanServiceImpl implements IDeviceGroupPlanService 
{
    @Autowired
    private DeviceGroupPlanMapper deviceGroupPlanMapper;

    /**
     * 查询班组计划保养
     * 
     * @param groupId 班组计划保养主键
     * @return 班组计划保养
     */
    @Override
    public DeviceGroupPlan selectDeviceGroupPlanByGroupId(String groupId)
    {
        return deviceGroupPlanMapper.selectDeviceGroupPlanByGroupId(groupId);
    }

    /**
     * 查询班组计划保养列表
     * 
     * @param deviceGroupPlan 班组计划保养
     * @return 班组计划保养
     */
    @Override
    public List<DeviceGroupPlan> selectDeviceGroupPlanList(DeviceGroupPlan deviceGroupPlan)
    {
        return deviceGroupPlanMapper.selectDeviceGroupPlanList(deviceGroupPlan);
    }

    /**
     * 新增班组计划保养
     * 
     * @param deviceGroupPlan 班组计划保养
     * @return 结果
     */
    @Override
    public int insertDeviceGroupPlan(DeviceGroupPlan deviceGroupPlan)
    {
        return deviceGroupPlanMapper.insertDeviceGroupPlan(deviceGroupPlan);
    }

    /**
     * 修改班组计划保养
     * 
     * @param deviceGroupPlan 班组计划保养
     * @return 结果
     */
    @Override
    public int updateDeviceGroupPlan(DeviceGroupPlan deviceGroupPlan)
    {
        return deviceGroupPlanMapper.updateDeviceGroupPlan(deviceGroupPlan);
    }

    /**
     * 批量删除班组计划保养
     * 
     * @param groupIds 需要删除的班组计划保养主键
     * @return 结果
     */
    @Override
    public int deleteDeviceGroupPlanByGroupIds(String[] groupIds)
    {
        return deviceGroupPlanMapper.deleteDeviceGroupPlanByGroupIds(groupIds);
    }

    /**
     * 删除班组计划保养信息
     * 
     * @param groupId 班组计划保养主键
     * @return 结果
     */
    @Override
    public int deleteDeviceGroupPlanByGroupId(String groupId)
    {
        return deviceGroupPlanMapper.deleteDeviceGroupPlanByGroupId(groupId);
    }
}
