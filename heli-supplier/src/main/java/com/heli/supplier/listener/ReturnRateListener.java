package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.supplier.domain.SupplierReturnRate;
import com.heli.supplier.mapper.SupplierReturnRateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ClassName: OnetimeSimpleListener
 * Package: com.heli.supplier.listener
 * Description:
 *
 * @Author
 * @Create 2025/2/21 15:29
 * @Version 1.0
 */
@Slf4j

public class ReturnRateListener implements ReadListener<SupplierReturnRate> {

    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierReturnRateMapper supplierReturnRateMapper;

    private Date date;
    private Date month;

    private List<SupplierReturnRate> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public ReturnRateListener(SupplierReturnRateMapper supplierReturnRateMapper, Date month) {
        this.supplierReturnRateMapper = supplierReturnRateMapper;
        this.month = month;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SupplierReturnRate registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);

        // 数据处理
        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setMonth(month);

//            registerInfoExcel.setXXX(date);

//            if (registerInfoExcel.getControlSystem() == null) {
//                registerInfoExcel.setControlSystem(cacheDataList.get(currentRow - 1).getControlSystem());
//
//            }
            currentRow++;
            // 加入缓存
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
        if (!cacheDataList.isEmpty()) {
            saveToDB();
        }
        log.info("所有数据解析完成");
    }

    /**
     * 将读取到的内容写入DB
     */
    private void saveToDB() {
        log.info("开始写入数据库");
//        supplierOnetimeSimpleMapper.insert(cacheDataList);
        supplierReturnRateMapper.insert(cacheDataList);
    }

}
