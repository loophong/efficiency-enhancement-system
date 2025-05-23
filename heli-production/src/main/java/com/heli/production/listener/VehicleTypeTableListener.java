package com.heli.production.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.production.domain.entity.VehicleTypeEntity;
import com.heli.production.mapper.VehicleTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.excel.metadata.CellExtra;

import java.util.*;

@Slf4j
public class VehicleTypeTableListener implements ReadListener<VehicleTypeEntity> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    /**
     * 合并单元格
     */
    private List<CellExtra> extraMergeInfoList = new ArrayList<>();

    @Autowired
    private VehicleTypeMapper vehicleTypeMapper;

    private List<VehicleTypeEntity> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public VehicleTypeTableListener(VehicleTypeMapper vehicleTypeMapper) {
        this.vehicleTypeMapper = vehicleTypeMapper;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(VehicleTypeEntity registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中

        log.info("当前读取的数据为:" + registerInfoExcel);

        if (registerInfoExcel.getVehicleType() != null) {
            registerInfoExcel.setId(null);
            if (registerInfoExcel.getSeries() == null) {
                registerInfoExcel.setSeries(cacheDataList.get(currentRow - 1).getSeries());
            }
            if (registerInfoExcel.getTonnage() == null) {
                registerInfoExcel.setTonnage(cacheDataList.get(currentRow - 1).getTonnage());
            }
            if (registerInfoExcel.getMainConfiguration() == null) {
                registerInfoExcel.setMainConfiguration(cacheDataList.get(currentRow - 1).getMainConfiguration());
            }
            if (registerInfoExcel.getStandardEquipment() == null) {
                registerInfoExcel.setStandardEquipment(cacheDataList.get(currentRow - 1).getStandardEquipment());
            }
            log.info("当前读取的数据为" + registerInfoExcel);
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
        vehicleTypeMapper.insert(cacheDataList);
    }

}
