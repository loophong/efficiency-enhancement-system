package com.heli.production.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.production.domain.entity.MainPlanTableEntity;
import com.heli.production.mapper.MainPlanTableMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Slf4j
public class MainPlanTableListener implements ReadListener<MainPlanTableEntity> {

    private static final int BATCH_COUNT = 200;


    @Autowired
    private MainPlanTableMapper mainPlanTableMapper;

    private Date uploadDate;

    private List<MainPlanTableEntity> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public MainPlanTableListener(MainPlanTableMapper mainPlanTableMapper, Date uploadDate) {
        this.mainPlanTableMapper = mainPlanTableMapper;
        this.uploadDate = uploadDate;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(MainPlanTableEntity registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中

        log.info("当前读取的数据为:" + registerInfoExcel);

        if (registerInfoExcel.getOrderNumber() != null){
            registerInfoExcel.setUploadDate(uploadDate);
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
        mainPlanTableMapper.insert(cacheDataList);
    }
}
