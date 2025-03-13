package com.heli.device.maintenanceTable.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceRecord;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 记录Mapper接口
 * 
 * @author YYY
 * @date 2025-03-04
 */
public interface DeviceMaintenanceRecordMapper extends BaseMapper<DeviceMaintenanceRecord>
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
     * 删除记录
     * 
     * @param maintenanceId 记录主键
     * @return 结果
     */
    public int deleteDeviceMaintenanceRecordByMaintenanceId(Long maintenanceId);

    /**
     * 批量删除记录
     * 
     * @param maintenanceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceMaintenanceRecordByMaintenanceIds(Long[] maintenanceIds);
    @Select("SELECT * FROM device_maintenance_record " +
            "WHERE maintenance_plan_id = #{planId} " +
            "AND plan_month_time = #{timeName} " )
    public DeviceMaintenanceRecord selectByIdAndTime(@Param("planId") Integer planId, @Param("timeName") String timeName);
}
