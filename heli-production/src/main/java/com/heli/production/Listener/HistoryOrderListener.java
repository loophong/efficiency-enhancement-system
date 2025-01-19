package com.heli.production.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.production.domain.entity.HistoryOrderEntity;
import com.heli.production.domain.entity.MainPlanTableEntity;
import com.heli.production.mapper.HistoryOrderMapper;
import com.heli.production.mapper.MainPlanTableMapper;
import com.heli.production.service.IHistoryOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Slf4j
public class HistoryOrderListener implements ReadListener<HistoryOrderEntity> {

    private static final int BATCH_COUNT = 500;

    @Autowired
//    private IHistoryOrderService historyOrderService;
    private HistoryOrderMapper historyOrderMapper;


    private Date orderYear;

    private List<HistoryOrderEntity> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public HistoryOrderListener(HistoryOrderMapper historyOrderMapper, Date orderYear) {
        this.historyOrderMapper = historyOrderMapper;
        this.orderYear = orderYear;
    }
//    public HistoryOrderListener(IHistoryOrderService historyOrderService, Date orderYear) {
//        this.historyOrderService = historyOrderService;
//        this.orderYear = orderYear;
//    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(HistoryOrderEntity registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中

        log.info("当前读取的数据为:" + registerInfoExcel);

        if (registerInfoExcel.getOrderNumber() != null) {
            registerInfoExcel.setOrderYear(orderYear);
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
//        historyOrderMapper.insert(cacheDataList);
        historyOrderMapper.batchInsert(cacheDataList);

//        historyOrderService.saveBatch(cacheDataList, BATCH_COUNT);

    }
}
