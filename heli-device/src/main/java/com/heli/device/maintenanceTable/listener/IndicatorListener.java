package com.heli.device.maintenanceTable.listener;

import com.alibaba.excel.context.AnalysisContext;

import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heli.device.maintenanceTable.domain.DeviceIndicatorCount;

import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;
import com.heli.device.maintenanceTable.mapper.DeviceIndicatorCountMapper;
import lombok.extern.log4j.Log4j2;
@Log4j2
public class IndicatorListener implements ReadListener<DeviceIndicatorCount> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    private String year ="";
    private DeviceIndicatorCountMapper deviceIndicatorCountMapper;

    /**
     * 核心合并单元格的缓存
     * 可以把map当成一个二位excel表格，里面记录每个存储的被合并单元格的内容。
     * key 为 [行号-列号]
     */
    private Map<String, Object> mergedCellsIndexMap = new HashMap<>();


    private List<DeviceIndicatorCount> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public IndicatorListener(DeviceIndicatorCountMapper deviceIndicatorCountMapper,String year) {
        this.deviceIndicatorCountMapper = deviceIndicatorCountMapper;
        this.year = year;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(DeviceIndicatorCount registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        if (registerInfoExcel.getIndicatorName() != null) {
            // 处理数据
//            if (registerInfoExcel.getSeries() == null) {
//                registerInfoExcel.setSeries(cacheDataList.get(currentRow - 1).getSeries());
//            }
//            if (registerInfoExcel.getTonnage() == null) {
//                registerInfoExcel.setTonnage(cacheDataList.get(currentRow - 1).getTonnage());
//            }
            log.info("当前读取的数据为" + registerInfoExcel);
            cacheDataList.add(registerInfoExcel);
//            currentRow++;
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
//        deviceIndicatorCountMapper.insert(cacheDataList);
        for (DeviceIndicatorCount data : cacheDataList) {
//            DeviceIndicatorCount existing = deviceIndicatorCountMapper.selectExist(year,data.getIndicatorName());
//            if (existing != null) {
//                // 如果存在重复记录，执行更新操作
//                data.setIndicatorId(existing.getIndicatorId()); // 确保设置了ID以便更新正确的记录
//                deviceIndicatorCountMapper.updateDeviceIndicatorCount(data);
//            } else {
                // 否则，执行插入操作
                data.setIndicatorTime(year);
                deviceIndicatorCountMapper.insertDeviceIndicatorCount(data);
//            }
        }
    }
}
