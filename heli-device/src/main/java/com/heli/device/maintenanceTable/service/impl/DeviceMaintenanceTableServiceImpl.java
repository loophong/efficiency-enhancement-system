package com.heli.device.maintenanceTable.service.impl;

import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.heli.device.maintenanceTable.listener.MaintenanceTableListener;
import com.heli.device.maintenanceTable.mapper.DeviceMaintenanceTableMapper;
import com.heli.device.maintenanceTable.service.IDeviceMaintenanceTableService;
import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;


/**
 * 2.设备故障记录(导入)Service业务层处理
 *
 * @author ruoyi
 * @date 2025-01-09
 */
@Slf4j
@Service
public class DeviceMaintenanceTableServiceImpl implements IDeviceMaintenanceTableService
{
    @Autowired
    private DeviceMaintenanceTableMapper deviceMaintenanceTableMapper;

    /**
     * 查询设备故障记录(导入)
     *
     * @param
     * @return 2.设备故障记录(导入)
     */
    @Override
    public R<String> readFaultRecordsToDB(String fileName, InputStream inputStream) {
        try {
//            log.info("开始读取文件: {}", fileName);
            // 读取文件前清空数据库
//            log.info("开始清空数据库");
//            enterpriseManagementSalaryTableMapper.clearSalaryTableAllInfo();
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
            EasyExcel.read(inputStream, DeviceMaintenanceTable.class, new MaintenanceTableListener(deviceMaintenanceTableMapper)).sheet().doRead();
            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }
    @Override
    public DeviceMaintenanceTable selectDeviceMaintenanceTableByMaintenanceTableId(Long maintenanceTableId)
    {
        return deviceMaintenanceTableMapper.selectDeviceMaintenanceTableByMaintenanceTableId(maintenanceTableId);
    }

    /**
     * 查询2.设备故障记录(导入)列表
     *
     * @param deviceMaintenanceTable 2.设备故障记录(导入)
     * @return 2.设备故障记录(导入)
     */
    @Override
    public List<DeviceMaintenanceTable> selectDeviceMaintenanceTableList(DeviceMaintenanceTable deviceMaintenanceTable)
    {
        return deviceMaintenanceTableMapper.selectDeviceMaintenanceTableList(deviceMaintenanceTable);
    }

    /**
     * 新增2.设备故障记录(导入)
     *
     * @param deviceMaintenanceTable 2.设备故障记录(导入)
     * @return 结果
     */
    @Override
    public int insertDeviceMaintenanceTable(DeviceMaintenanceTable deviceMaintenanceTable)
    {
        return deviceMaintenanceTableMapper.insertDeviceMaintenanceTable(deviceMaintenanceTable);
    }

    /**
     * 修改2.设备故障记录(导入)
     *
     * @param deviceMaintenanceTable 2.设备故障记录(导入)
     * @return 结果
     */
    @Override
    public int updateDeviceMaintenanceTable(DeviceMaintenanceTable deviceMaintenanceTable)
    {
        return deviceMaintenanceTableMapper.updateDeviceMaintenanceTable(deviceMaintenanceTable);
    }

    /**
     * 批量删除2.设备故障记录(导入)
     *
     * @param maintenanceTableIds 需要删除的2.设备故障记录(导入)主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMaintenanceTableByMaintenanceTableIds(Long[] maintenanceTableIds)
    {
        return deviceMaintenanceTableMapper.deleteDeviceMaintenanceTableByMaintenanceTableIds(maintenanceTableIds);
    }

    /**
     * 删除2.设备故障记录(导入)信息
     *
     * @param maintenanceTableId 2.设备故障记录(导入)主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMaintenanceTableByMaintenanceTableId(Long maintenanceTableId)
    {
        return deviceMaintenanceTableMapper.deleteDeviceMaintenanceTableByMaintenanceTableId(maintenanceTableId);
    }
}
