package com.heli.device.maintenanceTable.service;

import java.io.InputStream;
import java.util.List;
import com.heli.device.maintenanceTable.domain.DeviceIndicatorCount;
import com.ruoyi.common.core.domain.R;

/**
 * 设备指标分析Service接口
 * 
 * @author YYY
 * @date 2025-01-18
 */
public interface IDeviceIndicatorCountService 
{
    /**
     * 查询设备指标分析
     * 
     * @param indicatorId 设备指标分析主键
     * @return 设备指标分析
     */
    public DeviceIndicatorCount selectDeviceIndicatorCountByIndicatorId(Long indicatorId);

    /**
     * 查询设备指标分析列表
     * 
     * @param deviceIndicatorCount 设备指标分析
     * @return 设备指标分析集合
     */
    public List<DeviceIndicatorCount> selectDeviceIndicatorCountList(DeviceIndicatorCount deviceIndicatorCount);

    /**
     * 新增设备指标分析
     * 
     * @param deviceIndicatorCount 设备指标分析
     * @return 结果
     */
    public int insertDeviceIndicatorCount(DeviceIndicatorCount deviceIndicatorCount);

    /**
     * 修改设备指标分析
     * 
     * @param deviceIndicatorCount 设备指标分析
     * @return 结果
     */
    public int updateDeviceIndicatorCount(DeviceIndicatorCount deviceIndicatorCount);

    /**
     * 批量删除设备指标分析
     * 
     * @param indicatorIds 需要删除的设备指标分析主键集合
     * @return 结果
     */
    public int deleteDeviceIndicatorCountByIndicatorIds(Long[] indicatorIds);

    /**
     * 删除设备指标分析信息
     * 
     * @param indicatorId 设备指标分析主键
     * @return 结果
     */
    public int deleteDeviceIndicatorCountByIndicatorId(Long indicatorId);

    /**
     * 读取指标数据
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return {@link R }<{@link String }>
     */
    public R<String> readIndicatorToDB(String fileName, InputStream inputStream,String year);
}
