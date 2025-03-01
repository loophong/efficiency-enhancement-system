package com.heli.device.maintenanceTable.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.device.maintenanceTable.domain.DeviceGroupPlan;


/**
 * 班组计划保养Mapper接口
 * 
 * @author YYY
 * @date 2025-01-19
 */
public interface DeviceGroupPlanMapper extends BaseMapper<DeviceGroupPlan>
{
    /**
     * 查询班组计划保养
     * 
     * @param groupId 班组计划保养主键
     * @return 班组计划保养
     */
    public DeviceGroupPlan selectDeviceGroupPlanByGroupId(String groupId);

    /**
     * 查询班组计划保养列表
     * 
     * @param deviceGroupPlan 班组计划保养
     * @return 班组计划保养集合
     */
    public List<DeviceGroupPlan> selectDeviceGroupPlanList(DeviceGroupPlan deviceGroupPlan);

    /**
     * 新增班组计划保养
     * 
     * @param deviceGroupPlan 班组计划保养
     * @return 结果
     */
    public int insertDeviceGroupPlan(DeviceGroupPlan deviceGroupPlan);

    /**
     * 修改班组计划保养
     * 
     * @param deviceGroupPlan 班组计划保养
     * @return 结果
     */
    public int updateDeviceGroupPlan(DeviceGroupPlan deviceGroupPlan);

    /**
     * 删除班组计划保养
     * 
     * @param groupId 班组计划保养主键
     * @return 结果
     */
    public int deleteDeviceGroupPlanByGroupId(String groupId);

    /**
     * 批量删除班组计划保养
     * 
     * @param groupIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceGroupPlanByGroupIds(String[] groupIds);
}
