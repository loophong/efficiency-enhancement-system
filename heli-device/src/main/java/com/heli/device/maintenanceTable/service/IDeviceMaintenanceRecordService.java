package com.heli.device.maintenanceTable.service;

import java.util.List;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceRecord;

/**
 * 记录Service接口
 * 
 * @author YYY
 * @date 2025-03-04
 */
public interface IDeviceMaintenanceRecordService 
{
    /**
     * 查询记录
     * 
     * @param maintenanceId 记录主键
     * @return 记录
     */
    public DeviceMaintenanceRecord selectDeviceMaintenanceRecordByMaintenanceId(Long maintenanceId);

    /**
     * 查询记录列表
     * 
     * @param deviceMaintenanceRecord 记录
     * @return 记录集合
     */
    public List<DeviceMaintenanceRecord> selectDeviceMaintenanceRecordList(DeviceMaintenanceRecord deviceMaintenanceRecord);

    /**
     * 新增记录
     * 
     * @param deviceMaintenanceRecord 记录
     * @return 结果
     */
    public int insertDeviceMaintenanceRecord(DeviceMaintenanceRecord deviceMaintenanceRecord);

    /**
     * 修改记录
     * 
     * @param deviceMaintenanceRecord 记录
     * @return 结果
     */
    public int updateDeviceMaintenanceRecord(DeviceMaintenanceRecord deviceMaintenanceRecord);

    /**
     * 批量删除记录
     * 
     * @param maintenanceIds 需要删除的记录主键集合
     * @return 结果
     */
    public int deleteDeviceMaintenanceRecordByMaintenanceIds(Long[] maintenanceIds);

    /**
     * 删除记录信息
     * 
     * @param maintenanceId 记录主键
     * @return 结果
     */
    public int deleteDeviceMaintenanceRecordByMaintenanceId(Long maintenanceId);
}
