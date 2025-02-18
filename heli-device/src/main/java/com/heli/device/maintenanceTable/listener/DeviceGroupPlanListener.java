package com.heli.device.maintenanceTable.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.device.maintenanceTable.domain.DeviceGroupPlan;

import java.util.List;

import com.heli.device.maintenanceTable.mapper.DeviceGroupPlanMapper;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class DeviceGroupPlanListener implements ReadListener<DeviceGroupPlan> {
    private static final int BATCH_COUNT = 200;

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
//        if (registerInfoExcel.getRowName() != null) {
//            // 处理数据
//            if (registerInfoExcel.getColumnNum() != null) {
//                registerInfoExcel.setColumnNum(registerInfoExcel.getColumnNum().replaceAll(",", ""));
//            }else {
//                registerInfoExcel.setColumnNum("0");
//            }
//            if (registerInfoExcel.getRowName() != null) {
//                registerInfoExcel.setRowName(registerInfoExcel.getRowName().replaceAll(" ", ""));
//            }
//            cacheDataList.add(registerInfoExcel);
//        }

        log.info("当前读取的数据为:" + registerInfoExcel);

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
//        financialTempTableMapper.batchInsertTempTable(cacheDataList);
//        financialTempTableMapper.batchInsertSalaryTable(cacheDataList);
    }
}
