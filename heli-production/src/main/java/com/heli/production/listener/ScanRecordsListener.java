package com.heli.production.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.production.domain.entity.ScanRecordsEntity;
import com.heli.production.mapper.ScanRecordsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ScanRecordsListener implements ReadListener<ScanRecordsEntity> {

    private static final int BATCH_COUNT = 500;

    @Autowired
    private ScanRecordsMapper scanRecordsMapper;

    private List<ScanRecordsEntity> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public ScanRecordsListener(ScanRecordsMapper scanRecordsMapper) {
        this.scanRecordsMapper = scanRecordsMapper;
    }


    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(ScanRecordsEntity registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中

        log.info("当前读取的数据为:" + registerInfoExcel);

        if (registerInfoExcel.getMachineId() != null) {
            cacheDataList.add(registerInfoExcel);
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
        scanRecordsMapper.batchInsert(cacheDataList);
    }
}
