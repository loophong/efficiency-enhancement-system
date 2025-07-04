package com.heli.device.maintenanceTable.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.device.maintenanceTable.domain.DeviceGroupPlan;

import java.util.Date;
import java.util.List;

import com.heli.device.maintenanceTable.mapper.DeviceGroupPlanMapper;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class DeviceGroupPlanListener implements ReadListener<DeviceGroupPlan> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;
    private DeviceGroupPlanMapper deviceGroupPlanMapper;


    private List<DeviceGroupPlan> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public DeviceGroupPlanListener(DeviceGroupPlanMapper deviceGroupPlanMapper) {
        this.deviceGroupPlanMapper = deviceGroupPlanMapper;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(DeviceGroupPlan registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
       if (registerInfoExcel.getMaintenanceContent() != null) {
            // 处理合并单元格数据
            if (registerInfoExcel.getOrderNum() == null) {
                registerInfoExcel.setOrderNum(cacheDataList.get(currentRow - 1).getOrderNum());
            }
            if (registerInfoExcel.getDeviceType() == null) {
                registerInfoExcel.setDeviceType(cacheDataList.get(currentRow - 1).getDeviceType());
            }
           if (registerInfoExcel.getExecuteGroup() == null) {
               registerInfoExcel.setExecuteGroup(cacheDataList.get(currentRow - 1).getExecuteGroup());
           }
            log.info("当前读取的数据为" + registerInfoExcel);
            cacheDataList.add(registerInfoExcel);
            currentRow++;
        }

//        log.info("当前读取的数据为:" + registerInfoExcel);

        // 批量处理缓存的数据
        if (cacheDataList.size() >= BATCH_COUNT) {
            saveToDB();
            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 处理结尾, 不足100条
     *
     * @param analysisContext 全局监听内容
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveToDB();
        log.info("所有数据解析完成");
    }


    /**
     * 将读取到的内容写入DB
     */
    private void saveToDB() {
        log.info("开始写入数据库");
        SecurityUtils securityUtils = new SecurityUtils();
//        deviceGroupPlanMapper.insert(cacheDataList);
        Long userId =securityUtils.getUserId();
        for (DeviceGroupPlan data : cacheDataList) {
//            DeviceGroupPlan existing = deviceGroupPlanMapper.selectExist(data.getDeviceNum(),data.getFaultPhenomenon(),data.getReportedTime());
//            if (existing != null) {
//                // 如果存在重复记录，执行更新操作
//                data.setMaintenanceTableId(existing.getMaintenanceTableId()); // 确保设置了ID以便更新正确的记录
//                deviceMaintenanceTableMapper.updateDeviceMaintenanceTable(data);
//            } else {
                // 否则，执行插入操作
            data.setCreateBy(userId.toString());
            data.setCreateTime(new Date());
            data.setRollTime(new Date().toString());
            deviceGroupPlanMapper.insertDeviceGroupPlan(data);
//            }
        }
    }
}
