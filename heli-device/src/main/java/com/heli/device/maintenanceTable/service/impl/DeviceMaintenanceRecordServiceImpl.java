package com.heli.device.maintenanceTable.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.maintenanceTable.mapper.DeviceMaintenanceRecordMapper;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceRecord;
import com.heli.device.maintenanceTable.service.IDeviceMaintenanceRecordService;

/**
 * 记录Service业务层处理
 * 
 * @author YYY
 * @date 2025-03-04
 */
@Service
public class DeviceMaintenanceRecordServiceImpl implements IDeviceMaintenanceRecordService 
{
    @Autowired
    private DeviceMaintenanceRecordMapper deviceMaintenanceRecordMapper;

    /**
     * 查询记录
     * 
     * @param maintenanceId 记录主键
     * @return 记录
     */
    @Override
    public DeviceMaintenanceRecord selectDeviceMaintenanceRecordByMaintenanceId(Long maintenanceId)
    {
        return deviceMaintenanceRecordMapper.selectDeviceMaintenanceRecordByMaintenanceId(maintenanceId);
    }

    /**
     * 查询记录列表
     * 
     * @param deviceMaintenanceRecord 记录
     * @return 记录
     */
    @Override
    public List<DeviceMaintenanceRecord> selectDeviceMaintenanceRecordList(DeviceMaintenanceRecord deviceMaintenanceRecord)
    {
        return deviceMaintenanceRecordMapper.selectDeviceMaintenanceRecordList(deviceMaintenanceRecord);
    }

    /**
     * 新增记录
     * 
     * @param deviceMaintenanceRecord 记录
     * @return 结果
     */
    @Override
    public int insertDeviceMaintenanceRecord(DeviceMaintenanceRecord deviceMaintenanceRecord)
    {
        deviceMaintenanceRecord.setCreateTime(DateUtils.getNowDate());
        return deviceMaintenanceRecordMapper.insertDeviceMaintenanceRecord(deviceMaintenanceRecord);
    }

    /**
     * 修改记录
     * 
     * @param deviceMaintenanceRecord 记录
     * @return 结果
     */
    @Override
    public int updateDeviceMaintenanceRecord(DeviceMaintenanceRecord deviceMaintenanceRecord)
    {
        return deviceMaintenanceRecordMapper.updateDeviceMaintenanceRecord(deviceMaintenanceRecord);
    }

    /**
     * 批量删除记录
     * 
     * @param maintenanceIds 需要删除的记录主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMaintenanceRecordByMaintenanceIds(Long[] maintenanceIds)
    {
        return deviceMaintenanceRecordMapper.deleteDeviceMaintenanceRecordByMaintenanceIds(maintenanceIds);
    }

    /**
     * 删除记录信息
     * 
     * @param maintenanceId 记录主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMaintenanceRecordByMaintenanceId(Long maintenanceId)
    {
        return deviceMaintenanceRecordMapper.deleteDeviceMaintenanceRecordByMaintenanceId(maintenanceId);
    }
}
