package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.supplier.domain.SupplierPriceTrust;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.mapper.SupplierPriceTrustMapper;
import com.heli.supplier.mapper.SupplierRiskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class PriceTrustListener implements ReadListener<SupplierPriceTrust> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierPriceTrustMapper supplierPriceTrustMapper;

    private Date date;
    private Date uploadMonth;

    private List<SupplierPriceTrust> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PriceTrustListener(SupplierPriceTrustMapper supplierPriceTrustMapper,Date uploadMonth) {
        this.supplierPriceTrustMapper = supplierPriceTrustMapper;
        this.uploadMonth = uploadMonth;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SupplierPriceTrust registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);
        // 数据处理
        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setTime(uploadMonth);

            calculateAndSetScore(registerInfoExcel);

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
        saveToDB();
        log.info("所有数据解析完成");
    }

    /**
     * 将读取到的内容写入DB
     */
    private void saveToDB() {
        log.info("开始写入数据库");
        supplierPriceTrustMapper.insert(cacheDataList);
    }
    private void calculateAndSetScore(SupplierPriceTrust supplierPriceTrust) {
        // 确保 happenNumber 不能为空
//        Long happenNumber = supplierPriceTrust.getHappenNumber();
//        if (supplierPriceTrust.getHappenNumber() == null) {
//            happenNumber = 0L;
//        }

        long happenNumber = (supplierPriceTrust.getHappenNumber() != null) ? supplierPriceTrust.getHappenNumber() : 0;
        // 计算基础得分
        double baseScore = 100.0;
        double finalScore = (baseScore - happenNumber * 20);

        // 确保得分不会低于 0
        supplierPriceTrust.setScore(Math.max(finalScore, 0));
    }
}
