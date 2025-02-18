package com.heli.device.maintenanceTable.service.impl;

import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;
import com.heli.device.maintenanceTable.domain.MajorPlanExcel;
import com.heli.device.maintenanceTable.listener.DeviceGroupPlanListener;
import com.heli.device.maintenanceTable.listener.MaintenanceTableListener;
import com.heli.device.maintenanceTable.listener.MajorPlanExcelListener;
import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.maintenanceTable.mapper.DeviceGroupPlanMapper;
import com.heli.device.maintenanceTable.domain.DeviceGroupPlan;
import com.heli.device.maintenanceTable.service.IDeviceGroupPlanService;

/**
 * 班组计划保养Service业务层处理
 * 
 * @author YYY
 * @date 2025-01-19
 */
@Service
@Slf4j
public class DeviceGroupPlanServiceImpl implements IDeviceGroupPlanService 
{
    @Autowired
    private DeviceGroupPlanMapper deviceGroupPlanMapper;



    /**
     * 班组计划表读取
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return {@link R }<{@link String }>
     */
    @Override
    public R<String> readPlanGroupToDB(String fileName, InputStream inputStream) {
        try {
//            log.info("开始读取文件: {}", fileName);
            // 读取文件前清空数据库
//            log.info("开始清空数据库");
//            enterpriseManagementSalaryTableMapper.clearSalaryTableAllInfo();
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
            EasyExcel.read(inputStream, DeviceGroupPlan.class, new DeviceGroupPlanListener(deviceGroupPlanMapper)).sheet(1).doRead();
            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }

    /**
     * 查询班组计划保养
     * 
     * @param groupId 班组计划保养主键
     * @return 班组计划保养
     */
    @Override
    public DeviceGroupPlan selectDeviceGroupPlanByGroupId(String groupId)
    {
        return deviceGroupPlanMapper.selectDeviceGroupPlanByGroupId(groupId);
    }

    /**
     * 查询班组计划保养列表
     * 
     * @param deviceGroupPlan 班组计划保养
     * @return 班组计划保养
     */
    @Override
    public List<DeviceGroupPlan> selectDeviceGroupPlanList(DeviceGroupPlan deviceGroupPlan)
    {
        return deviceGroupPlanMapper.selectDeviceGroupPlanList(deviceGroupPlan);
    }

    /**
     * 新增班组计划保养
     * 
     * @param deviceGroupPlan 班组计划保养
     * @return 结果
     */
    @Override
    public int insertDeviceGroupPlan(DeviceGroupPlan deviceGroupPlan)
    {
        return deviceGroupPlanMapper.insertDeviceGroupPlan(deviceGroupPlan);
    }

    /**
     * 修改班组计划保养
     * 
     * @param deviceGroupPlan 班组计划保养
     * @return 结果
     */
    @Override
    public int updateDeviceGroupPlan(DeviceGroupPlan deviceGroupPlan)
    {
        return deviceGroupPlanMapper.updateDeviceGroupPlan(deviceGroupPlan);
    }

    /**
     * 批量删除班组计划保养
     * 
     * @param groupIds 需要删除的班组计划保养主键
     * @return 结果
     */
    @Override
    public int deleteDeviceGroupPlanByGroupIds(String[] groupIds)
    {
        return deviceGroupPlanMapper.deleteDeviceGroupPlanByGroupIds(groupIds);
    }

    /**
     * 删除班组计划保养信息
     * 
     * @param groupId 班组计划保养主键
     * @return 结果
     */
    @Override
    public int deleteDeviceGroupPlanByGroupId(String groupId)
    {
        return deviceGroupPlanMapper.deleteDeviceGroupPlanByGroupId(groupId);
    }
}
