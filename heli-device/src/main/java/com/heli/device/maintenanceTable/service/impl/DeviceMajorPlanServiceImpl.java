package com.heli.device.maintenanceTable.service.impl;

import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.heli.device.maintenanceTable.domain.MajorPlanExcel;
import com.heli.device.maintenanceTable.listener.MajorPlanExcelListener;
import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.maintenanceTable.mapper.DeviceMajorPlanMapper;
import com.heli.device.maintenanceTable.domain.DeviceMajorPlan;
import com.heli.device.maintenanceTable.service.IDeviceMajorPlanService;

/**
 * 专业计划保养Service业务层处理
 * 
 * @author YYY
 * @date 2025-02-18
 */
@Service
@Slf4j
public class DeviceMajorPlanServiceImpl implements IDeviceMajorPlanService 
{
    @Autowired
    private DeviceMajorPlanMapper deviceMajorPlanMapper;

    /**
     * 专业计划表读取
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return {@link R }<{@link String }>
     */
    @Override
    public R<String> readPlanMajorToDB(String fileName, InputStream inputStream) {
        try {
//            log.info("开始读取文件: {}", fileName);
            // 读取文件前清空数据库
//            log.info("开始清空数据库");
//            enterpriseManagementSalaryTableMapper.clearSalaryTableAllInfo();
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
            EasyExcel.read(inputStream, DeviceMajorPlan.class, new MajorPlanExcelListener(deviceMajorPlanMapper)).sheet().headRowNumber(3).doRead();
            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }

    /**
     * 查询专业计划保养
     * 
     * @param majorId 专业计划保养主键
     * @return 专业计划保养
     */
    @Override
    public DeviceMajorPlan selectDeviceMajorPlanByMajorId(String majorId)
    {
        return deviceMajorPlanMapper.selectDeviceMajorPlanByMajorId(majorId);
    }

    /**
     * 查询专业计划保养列表
     * 
     * @param deviceMajorPlan 专业计划保养
     * @return 专业计划保养
     */
    @Override
    public List<DeviceMajorPlan> selectDeviceMajorPlanList(DeviceMajorPlan deviceMajorPlan)
    {
        return deviceMajorPlanMapper.selectDeviceMajorPlanList(deviceMajorPlan);
    }

    /**
     * 新增专业计划保养
     * 
     * @param deviceMajorPlan 专业计划保养
     * @return 结果
     */
    @Override
    public int insertDeviceMajorPlan(DeviceMajorPlan deviceMajorPlan)
    {
        return deviceMajorPlanMapper.insertDeviceMajorPlan(deviceMajorPlan);
    }

    /**
     * 修改专业计划保养
     * 
     * @param deviceMajorPlan 专业计划保养
     * @return 结果
     */
    @Override
    public int updateDeviceMajorPlan(DeviceMajorPlan deviceMajorPlan)
    {
        return deviceMajorPlanMapper.updateDeviceMajorPlan(deviceMajorPlan);
    }

    /**
     * 批量删除专业计划保养
     * 
     * @param majorIds 需要删除的专业计划保养主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMajorPlanByMajorIds(String[] majorIds)
    {
        return deviceMajorPlanMapper.deleteDeviceMajorPlanByMajorIds(majorIds);
    }

    /**
     * 删除专业计划保养信息
     * 
     * @param majorId 专业计划保养主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMajorPlanByMajorId(String majorId)
    {
        return deviceMajorPlanMapper.deleteDeviceMajorPlanByMajorId(majorId);
    }
}
