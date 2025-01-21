package com.heli.device.maintenanceTable.mapper;

import java.util.List;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;

/**
 * 2.设备故障记录(导入)Mapper接口
 *
 * @author ruoyi
 * @date 2025-01-09
 */
public interface DeviceMaintenanceTableMapper
{
    /**
     * 查询2.设备故障记录(导入)
     *
     * @param maintenanceTableId 2.设备故障记录(导入)主键
     * @return 2.设备故障记录(导入)
     */
    public DeviceMaintenanceTable selectDeviceMaintenanceTableByMaintenanceTableId(Long maintenanceTableId);

    /**
     * 查询2.设备故障记录(导入)列表
     *
     * @param deviceMaintenanceTable 2.设备故障记录(导入)
     * @return 2.设备故障记录(导入)集合
     */
    public List<DeviceMaintenanceTable> selectDeviceMaintenanceTableList(DeviceMaintenanceTable deviceMaintenanceTable);

    /**
     * 新增2.设备故障记录(导入)
     *
     * @param deviceMaintenanceTable 2.设备故障记录(导入)
     * @return 结果
     */
    public int insertDeviceMaintenanceTable(DeviceMaintenanceTable deviceMaintenanceTable);

    /**
     * 修改2.设备故障记录(导入)
     *
     * @param deviceMaintenanceTable 2.设备故障记录(导入)
     * @return 结果
     */
    public int updateDeviceMaintenanceTable(DeviceMaintenanceTable deviceMaintenanceTable);

    /**
     * 删除2.设备故障记录(导入)
     *
     * @param maintenanceTableId 2.设备故障记录(导入)主键
     * @return 结果
     */
    public int deleteDeviceMaintenanceTableByMaintenanceTableId(Long maintenanceTableId);

    /**
     * 批量删除2.设备故障记录(导入)
     *
     * @param maintenanceTableIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceMaintenanceTableByMaintenanceTableIds(Long[] maintenanceTableIds);
}
