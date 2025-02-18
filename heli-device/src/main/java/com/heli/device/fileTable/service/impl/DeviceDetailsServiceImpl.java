package com.heli.device.fileTable.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.fileTable.mapper.DeviceDetailsMapper;
import com.heli.device.fileTable.domain.DeviceDetails;
import com.heli.device.fileTable.service.IDeviceDetailsService;

/**
 * 设备台账Service业务层处理
 * 
 * @author YYY
 * @date 2025-01-19
 */
@Service
public class DeviceDetailsServiceImpl implements IDeviceDetailsService 
{
    @Autowired
    private DeviceDetailsMapper deviceDetailsMapper;

    /**
     * 查询设备台账
     * 
     * @param detailsId 设备台账主键
     * @return 设备台账
     */
    @Override
    public DeviceDetails selectDeviceDetailsByDetailsId(String detailsId)
    {
        return deviceDetailsMapper.selectDeviceDetailsByDetailsId(detailsId);
    }

    /**
     * 查询设备台账列表
     * 
     * @param deviceDetails 设备台账
     * @return 设备台账
     */
    @Override
    public List<DeviceDetails> selectDeviceDetailsList(DeviceDetails deviceDetails)
    {
        return deviceDetailsMapper.selectDeviceDetailsList(deviceDetails);
    }

    /**
     * 新增设备台账
     * 
     * @param deviceDetails 设备台账
     * @return 结果
     */
    @Override
    public int insertDeviceDetails(DeviceDetails deviceDetails)
    {
        return deviceDetailsMapper.insertDeviceDetails(deviceDetails);
    }

    /**
     * 修改设备台账
     * 
     * @param deviceDetails 设备台账
     * @return 结果
     */
    @Override
    public int updateDeviceDetails(DeviceDetails deviceDetails)
    {
        return deviceDetailsMapper.updateDeviceDetails(deviceDetails);
    }

    /**
     * 批量删除设备台账
     * 
     * @param detailsIds 需要删除的设备台账主键
     * @return 结果
     */
    @Override
    public int deleteDeviceDetailsByDetailsIds(String[] detailsIds)
    {
        return deviceDetailsMapper.deleteDeviceDetailsByDetailsIds(detailsIds);
    }

    /**
     * 删除设备台账信息
     * 
     * @param detailsId 设备台账主键
     * @return 结果
     */
    @Override
    public int deleteDeviceDetailsByDetailsId(String detailsId)
    {
        return deviceDetailsMapper.deleteDeviceDetailsByDetailsId(detailsId);
    }
}
