package com.heli.device.maintenanceTable.service;

import java.io.InputStream;
import java.util.List;
import com.heli.device.maintenanceTable.domain.DeviceMajorPlan;
import com.ruoyi.common.core.domain.R;

/**
 * 专业计划保养Service接口
 * 
 * @author YYY
 * @date 2025-02-18
 */
public interface IDeviceMajorPlanService 
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
     * 批量删除专业计划保养
     * 
     * @param majorIds 需要删除的专业计划保养主键集合
     * @return 结果
     */
    public int deleteDeviceMajorPlanByMajorIds(String[] majorIds);

    /**
     * 删除专业计划保养信息
     * 
     * @param majorId 专业计划保养主键
     * @return 结果
     */
    public int deleteDeviceMajorPlanByMajorId(String majorId);

    /**
     * 读取专业计划表
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return {@link R }<{@link String }>
     */
    public R<String> readPlanMajorToDB(String fileName, InputStream inputStream);
}
