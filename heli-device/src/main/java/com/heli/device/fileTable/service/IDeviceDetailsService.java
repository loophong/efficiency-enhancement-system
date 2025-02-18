package com.heli.device.fileTable.service;

import java.util.List;
import com.heli.device.fileTable.domain.DeviceDetails;

/**
 * 设备台账Service接口
 * 
 * @author YYY
 * @date 2025-01-19
 */
public interface IDeviceDetailsService 
{
    /**
     * 查询设备台账
     * 
     * @param detailsId 设备台账主键
     * @return 设备台账
     */
    public DeviceDetails selectDeviceDetailsByDetailsId(String detailsId);

    /**
     * 查询设备台账列表
     * 
     * @param deviceDetails 设备台账
     * @return 设备台账集合
     */
    public List<DeviceDetails> selectDeviceDetailsList(DeviceDetails deviceDetails);

    /**
     * 新增设备台账
     * 
     * @param deviceDetails 设备台账
     * @return 结果
     */
    public int insertDeviceDetails(DeviceDetails deviceDetails);

    /**
     * 修改设备台账
     * 
     * @param deviceDetails 设备台账
     * @return 结果
     */
    public int updateDeviceDetails(DeviceDetails deviceDetails);

    /**
     * 批量删除设备台账
     * 
     * @param detailsIds 需要删除的设备台账主键集合
     * @return 结果
     */
    public int deleteDeviceDetailsByDetailsIds(String[] detailsIds);

    /**
     * 删除设备台账信息
     * 
     * @param detailsId 设备台账主键
     * @return 结果
     */
    public int deleteDeviceDetailsByDetailsId(String detailsId);
}
