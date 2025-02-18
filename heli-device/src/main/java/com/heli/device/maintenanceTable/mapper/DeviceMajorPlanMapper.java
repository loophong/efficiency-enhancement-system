package com.heli.device.maintenanceTable.mapper;

import java.util.List;
import com.heli.device.maintenanceTable.domain.DeviceMajorPlan;

/**
 * 专业计划保养Mapper接口
 * 
 * @author YYY
 * @date 2025-02-18
 */
public interface DeviceMajorPlanMapper 
{
    /**
     * 查询专业计划保养
     * 
     * @param majorId 专业计划保养主键
     * @return 专业计划保养
     */
    public DeviceMajorPlan selectDeviceMajorPlanByMajorId(String majorId);

    /**
     * 查询专业计划保养列表
     * 
     * @param deviceMajorPlan 专业计划保养
     * @return 专业计划保养集合
     */
    public List<DeviceMajorPlan> selectDeviceMajorPlanList(DeviceMajorPlan deviceMajorPlan);

    /**
     * 新增专业计划保养
     * 
     * @param deviceMajorPlan 专业计划保养
     * @return 结果
     */
    public int insertDeviceMajorPlan(DeviceMajorPlan deviceMajorPlan);

    /**
     * 修改专业计划保养
     * 
     * @param deviceMajorPlan 专业计划保养
     * @return 结果
     */
    public int updateDeviceMajorPlan(DeviceMajorPlan deviceMajorPlan);

    /**
     * 删除专业计划保养
     * 
     * @param majorId 专业计划保养主键
     * @return 结果
     */
    public int deleteDeviceMajorPlanByMajorId(String majorId);

    /**
     * 批量删除专业计划保养
     * 
     * @param majorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceMajorPlanByMajorIds(String[] majorIds);
}
