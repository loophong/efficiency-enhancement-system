package com.heli.device.maintenanceTable.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.maintenanceTable.mapper.DeviceIndicatorCountMapper;
import com.heli.device.maintenanceTable.domain.DeviceIndicatorCount;
import com.heli.device.maintenanceTable.service.IDeviceIndicatorCountService;

/**
 * 设备指标分析Service业务层处理
 * 
 * @author YYY
 * @date 2025-01-18
 */
@Service
public class DeviceIndicatorCountServiceImpl implements IDeviceIndicatorCountService 
{
    @Autowired
    private DeviceIndicatorCountMapper deviceIndicatorCountMapper;

    /**
     * 查询设备指标分析
     * 
     * @param indicatorId 设备指标分析主键
     * @return 设备指标分析
     */
    @Override
    public DeviceIndicatorCount selectDeviceIndicatorCountByIndicatorId(Long indicatorId)
    {
        return deviceIndicatorCountMapper.selectDeviceIndicatorCountByIndicatorId(indicatorId);
    }

    /**
     * 查询设备指标分析列表
     * 
     * @param deviceIndicatorCount 设备指标分析
     * @return 设备指标分析
     */
    @Override
    public List<DeviceIndicatorCount> selectDeviceIndicatorCountList(DeviceIndicatorCount deviceIndicatorCount)
    {
        return deviceIndicatorCountMapper.selectDeviceIndicatorCountList(deviceIndicatorCount);
    }

    /**
     * 新增设备指标分析
     * 
     * @param deviceIndicatorCount 设备指标分析
     * @return 结果
     */
    @Override
    public int insertDeviceIndicatorCount(DeviceIndicatorCount deviceIndicatorCount)
    {
        return deviceIndicatorCountMapper.insertDeviceIndicatorCount(deviceIndicatorCount);
    }

    /**
     * 修改设备指标分析
     * 
     * @param deviceIndicatorCount 设备指标分析
     * @return 结果
     */
    @Override
    public int updateDeviceIndicatorCount(DeviceIndicatorCount deviceIndicatorCount)
    {
        return deviceIndicatorCountMapper.updateDeviceIndicatorCount(deviceIndicatorCount);
    }

    /**
     * 批量删除设备指标分析
     * 
     * @param indicatorIds 需要删除的设备指标分析主键
     * @return 结果
     */
    @Override
    public int deleteDeviceIndicatorCountByIndicatorIds(Long[] indicatorIds)
    {
        return deviceIndicatorCountMapper.deleteDeviceIndicatorCountByIndicatorIds(indicatorIds);
    }

    /**
     * 删除设备指标分析信息
     * 
     * @param indicatorId 设备指标分析主键
     * @return 结果
     */
    @Override
    public int deleteDeviceIndicatorCountByIndicatorId(Long indicatorId)
    {
        return deviceIndicatorCountMapper.deleteDeviceIndicatorCountByIndicatorId(indicatorId);
    }
}
