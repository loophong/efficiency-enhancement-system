package com.heli.device.maintenanceTable.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.device.maintenanceTable.domain.DeviceIndicatorCount;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;


/**
 * 设备指标分析Mapper接口
 * 
 * @author YYY
 * @date 2025-01-18
 */
public interface DeviceIndicatorCountMapper extends BaseMapper<DeviceIndicatorCount>
{

    @Select("SELECT * FROM device_indicator_count " +
            "WHERE indicator_time = #{year} " +
            "AND indicator_name = #{indicatorName} ")
    DeviceIndicatorCount selectExist(@Param("year") String year,
                                       @Param("indicatorName") String indicatorName);

    /**
     * 查询设备指标分析
     * 
     * @param indicatorId 设备指标分析主键
     * @return 设备指标分析
     */
    public DeviceIndicatorCount selectDeviceIndicatorCountByIndicatorId(Long indicatorId);


    /**
     * 查询 indicator_name 的去重值
     */
    @Select("SELECT DISTINCT indicator_name FROM device_indicator_count")
    List<String> selectDistinctIndicatorName();

    /**
     * 查询 indicator_time 的去重值
     */
    @Select("SELECT DISTINCT indicator_time FROM device_indicator_count")
    List<String> selectDistinctIndicatorTime();

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
     * 删除设备指标分析
     * 
     * @param indicatorId 设备指标分析主键
     * @return 结果
     */
    public int deleteDeviceIndicatorCountByIndicatorId(Long indicatorId);

    /**
     * 批量删除设备指标分析
     * 
     * @param indicatorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceIndicatorCountByIndicatorIds(Long[] indicatorIds);
}
