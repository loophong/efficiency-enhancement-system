package com.heli.device.fileTable.mapper;

import java.util.List;
import com.heli.device.fileTable.domain.DeviceDetails;

/**
 * 设备台账Mapper接口
 * 
 * @author YYY
 * @date 2025-01-19
 */
public interface DeviceDetailsMapper 
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
     * 删除设备台账
     * 
     * @param detailsId 设备台账主键
     * @return 结果
     */
    public int deleteDeviceDetailsByDetailsId(String detailsId);

    /**
     * 批量删除设备台账
     * 
     * @param detailsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceDetailsByDetailsIds(String[] detailsIds);
}
