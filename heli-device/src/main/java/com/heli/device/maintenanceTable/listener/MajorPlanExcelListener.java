package com.heli.device.maintenanceTable.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.device.maintenanceTable.domain.DeviceMajorPlan;
import com.heli.device.maintenanceTable.domain.MajorPlanExcel;
import com.heli.device.maintenanceTable.mapper.DeviceMajorPlanMapper;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class MajorPlanExcelListener implements ReadListener<DeviceMajorPlan> {

    private static final int BATCH_COUNT = 200;
    private int currentRow = 0;
    private DeviceMajorPlanMapper deviceMajorPlanMapper;


    private List<DeviceMajorPlan> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public MajorPlanExcelListener(DeviceMajorPlanMapper deviceMajorPlanMapper) {
        this.deviceMajorPlanMapper = deviceMajorPlanMapper;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(DeviceMajorPlan registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        if (registerInfoExcel.getMajorProject() != null) {
            // 处理合并单元格数据
            if (registerInfoExcel.getMajorName() == null) {
                registerInfoExcel.setMajorName(cacheDataList.get(currentRow - 1).getMajorName());
            }
            log.info("当前读取的数据为:" + registerInfoExcel);
            cacheDataList.add(registerInfoExcel);
            currentRow++;
        }

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
        deviceMajorPlanMapper.insert(cacheDataList);
//        financialTempTableMapper.batchInsertSalaryTable(cacheDataList);
    }
}
