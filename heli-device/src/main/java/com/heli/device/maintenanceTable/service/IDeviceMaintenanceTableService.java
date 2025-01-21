package com.heli.device.maintenanceTable.service;

import java.io.InputStream;
import java.util.List;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;
import com.ruoyi.common.core.domain.R;

/**
 * 2.设备故障记录(导入)Service接口
 *
 * @author ruoyi
 * @date 2025-01-09
 */
public interface IDeviceMaintenanceTableService
{
    /**
     * 查询2.设备故障记录(导入)
     *
     * @param maintenanceTableId 2.设备故障记录(导入)主键
     * @return 2.设备故障记录(导入)
     */
    public DeviceMaintenanceTable selectDeviceMaintenanceTableByMaintenanceTableId(Long maintenanceTableId);
    public R<String> readFaultRecordsToDB(String fileName, InputStream inputStream);

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
     * 批量删除2.设备故障记录(导入)
     *
     * @param maintenanceTableIds 需要删除的2.设备故障记录(导入)主键集合
     * @return 结果
     */
    public int deleteDeviceMaintenanceTableByMaintenanceTableIds(Long[] maintenanceTableIds);

    /**
     * 删除2.设备故障记录(导入)信息
     *
     * @param maintenanceTableId 2.设备故障记录(导入)主键
     * @return 结果
     */
    public int deleteDeviceMaintenanceTableByMaintenanceTableId(Long maintenanceTableId);
}
