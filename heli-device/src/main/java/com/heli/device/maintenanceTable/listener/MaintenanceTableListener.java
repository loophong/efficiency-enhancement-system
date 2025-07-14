package com.heli.device.maintenanceTable.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;
import com.heli.device.maintenanceTable.mapper.DeviceMaintenanceTableMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;
import java.util.List;

@Log4j2
public class MaintenanceTableListener implements ReadListener<DeviceMaintenanceTable> {


    private static final int BATCH_COUNT = 200;

    @Autowired
    private DeviceMaintenanceTableMapper deviceMaintenanceTableMapper;


    private List<DeviceMaintenanceTable> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public MaintenanceTableListener(DeviceMaintenanceTableMapper deviceMaintenanceTableMapper) {
        this.deviceMaintenanceTableMapper = deviceMaintenanceTableMapper;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(DeviceMaintenanceTable registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        if (registerInfoExcel.getDeviceNum() != null) {
            // 处理数据
//            if (registerInfoExcel.getColumnNum() != null) {
//                registerInfoExcel.setColumnNum(registerInfoExcel.getColumnNum().replaceAll(",", ""));
//            }else {
//                registerInfoExcel.setColumnNum("0");
//            }
//            if (registerInfoExcel.getRowName() != null) {
//                registerInfoExcel.setRowName(registerInfoExcel.getRowName().replaceAll(" ", ""));
//            }
            cacheDataList.add(registerInfoExcel);
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
        log.info("cacheDataList:" + cacheDataList);
    }



    /**
     * 将读取到的内容写入DB
     */
    private void saveToDB() {
        log.info("开始写入数据库");
//        deviceMaintenanceTableMapper.insert(cacheDataList);
        for (DeviceMaintenanceTable data : cacheDataList) {
            DeviceMaintenanceTable existing = deviceMaintenanceTableMapper.selectExist(data.getDeviceNum(),data.getFaultPhenomenon(),data.getReportedTime());
            if (existing != null) {
                // 如果存在重复记录，执行更新操作
                data.setMaintenanceTableId(existing.getMaintenanceTableId()); // 确保设置了ID以便更新正确的记录
                data.setUpdateTime(new Date());
                deviceMaintenanceTableMapper.updateDeviceMaintenanceTable(data);
            } else {
                // 否则，执行插入操作
                data.setCreateTime(new Date());
                deviceMaintenanceTableMapper.insertDeviceMaintenanceTable(data);
            }
        }

    }

    /**
     * 捕获解析异常，记录失败行号和列号
     */
    @Override
    public void onException(Exception exception, AnalysisContext analysisContext) {
        log.error("读取 Excel 时发生异常");

        // 区分异常类型
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException convertException = (ExcelDataConvertException) exception;
            int rowIndex = convertException.getRowIndex(); // 获取异常行号（从0开始）
            int columnIndex = convertException.getColumnIndex(); // 获取异常列号（从0开始）

            log.error("【行号 {}，列号 {}】解析失败: {}", rowIndex + 1, columnIndex + 1, exception.getMessage());
        } else {
            log.error("未知异常: {}", exception.getMessage());
        }

        // 根据需求决定是否中断解析（默认继续解析）
        // 如果希望中断解析，抛出异常即可：
        // throw new RuntimeException(exception);
    }

}
