package com.heli.device.maintenanceTable.service;

import java.io.InputStream;
import java.util.List;
import com.heli.device.maintenanceTable.domain.DeviceGroupPlan;
import com.ruoyi.common.core.domain.R;

/**
 * 班组计划保养Service接口
 * 
 * @author YYY
 * @date 2025-01-19
 */
public interface IDeviceGroupPlanService 
{
    /**
     * 查询班组计划保养
     * 
     * @param groupId 班组计划保养主键
     * @return 班组计划保养
     */
    public DeviceGroupPlan selectDeviceGroupPlanByGroupId(String groupId);

    //替换表中每周数据
    public String updateDeviceGroupPlan();

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
     * 批量删除班组计划保养
     * 
     * @param groupIds 需要删除的班组计划保养主键集合
     * @return 结果
     */
    public int deleteDeviceGroupPlanByGroupIds(String[] groupIds);

    /**
     * 删除班组计划保养信息
     * 
     * @param groupId 班组计划保养主键
     * @return 结果
     */
    public int deleteDeviceGroupPlanByGroupId(String groupId);




    /**
     * 读取班组计划表
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return {@link R }<{@link String }>
     */
    public R<String> readPlanGroupToDB(String fileName, InputStream inputStream);
}
