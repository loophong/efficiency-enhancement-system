package com.heli.device.maintenanceTable.mapper;


import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 2.设备故障记录(导入)Mapper接口
 *
 * @author ruoyi
 * @date 2025-01-09
 */
public interface DeviceMaintenanceTableMapper extends BaseMapper<DeviceMaintenanceTable>
{
    /**
     * 查询设备故障记录
     *
     * @param maintenanceTableId 设备故障记录主键
     * @return 设备故障记录
     */
    public DeviceMaintenanceTable selectDeviceMaintenanceTableByMaintenanceTableId(Long maintenanceTableId);

    /**
     * 查询设备故障记录列表
     *
     * @param deviceMaintenanceTable 设备故障记录
     * @return 设备故障记录集合
     */
    public List<DeviceMaintenanceTable> selectDeviceMaintenanceTableList(DeviceMaintenanceTable deviceMaintenanceTable);

    @Select("SELECT * FROM device_maintenance_table " +
            "WHERE DATE_FORMAT(STR_TO_DATE(reported_time, '%y-%m-%d %H:%i'), '%Y-%m') = #{yearAndMonth}")
    public List<DeviceMaintenanceTable> selectListByDate(String yearAndMonth);
    /**
     * 新增设备故障记录
     *
     * @param deviceMaintenanceTable 设备故障记录
     * @return 结果
     */
    public int insertDeviceMaintenanceTable(DeviceMaintenanceTable deviceMaintenanceTable);

    /**
     * 修改设备故障记录
     *
     * @param deviceMaintenanceTable 设备故障记录
     * @return 结果
     */
    public int updateDeviceMaintenanceTable(DeviceMaintenanceTable deviceMaintenanceTable);

    /**
     * 删除设备故障记录
     *
     * @param maintenanceTableId 设备故障记录主键
     * @return 结果
     */
    public int deleteDeviceMaintenanceTableByMaintenanceTableId(Long maintenanceTableId);

    /**
     * 批量删除设备故障记录
     *
     * @param maintenanceTableIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceMaintenanceTableByMaintenanceTableIds(Long[] maintenanceTableIds);

    @Select("SELECT * FROM device_maintenance_table " +
            "WHERE device_num = #{deviceNum} " +
            "AND fault_phenomenon = #{faultPhenomenon} " +
            "AND reported_time = #{reportedTime}")
    DeviceMaintenanceTable selectExist(@Param("deviceNum") String deviceNum,
                                       @Param("faultPhenomenon") String faultPhenomenon,
                                       @Param("reportedTime") String reportedTime);
    /**
     * 查询故障树 TODO 带查询条件待完成
     *
     * @param deviceNum           设备编号
     * @param reportedTimeStart   报修开始时间
     * @param reportedTimeEnd     报修结束时间
     * @param resolutionTimeStart 解决开始时间
     * @param resolutionTimeEnd   解决结束时间
     * @return {@link List }<{@link DeviceMaintenanceTable }>
     */
//    @Select("SELECT * FROM device_maintenance_table " +
//            "WHERE device_num LIKE CONCAT('%', #{deviceNum}, '%') " +
//            "AND STR_TO_DATE(reportedTime, '%y-%m-%d %H:%i') BETWEEN " +
//            "STR_TO_DATE(#{reportedTimeStart}, '%y-%m-%d %H:%i') AND STR_TO_DATE(#{reportedTimeEnd}, '%y-%m-%d %H:%i') " +
//            "AND STR_TO_DATE(resolutionTime, '%y-%m-%d %H:%i') BETWEEN " +
//            "STR_TO_DATE(#{resolutionTimeStart}, '%y-%m-%d %H:%i') AND STR_TO_DATE(#{resolutionTimeEnd}, '%y-%m-%d %H:%i')")
//    @Param("deviceNum") String deviceNum,
//    @Param("reportedTimeStart") String reportedTimeStart,
//    @Param("reportedTimeEnd") String reportedTimeEnd,
//    @Param("resolutionTimeStart") String resolutionTimeStart,
//    @Param("resolutionTimeEnd") String resolutionTimeEnd);
    @Select("SELECT * FROM device_maintenance_table ")
    List<DeviceMaintenanceTable> selectTree();

}
