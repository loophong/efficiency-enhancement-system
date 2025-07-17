package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.mapper.SupplierRiskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class RiskListener implements ReadListener<SupplierRisk> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierRiskMapper supplierRiskMapper;

    private Date date;
    private Date uploadMonth;

    private List<SupplierRisk> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public RiskListener(SupplierRiskMapper supplierRiskMapper,Date uploadMonth) {
        this.supplierRiskMapper = supplierRiskMapper;
        this.uploadMonth = uploadMonth;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SupplierRisk registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);
        // 数据处理
        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setUploadTime(uploadMonth);
            currentRow++;

            calculateRiskScore(registerInfoExcel);

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
        supplierRiskMapper.insert(cacheDataList);
    }
    private void calculateRiskScore(SupplierRisk supplierRisk) {
        // 基础分
        long baseScore = 100;
        // 获取风险数量，如果为 null 则默认为 0
        long riskNumber = (supplierRisk.getRiskNumber() != null) ? supplierRisk.getRiskNumber() : 0;
        // 计算总扣分
        double deduction = riskNumber * 10;
        // 最终得分，不能小于 0
        double finalScore = Math.max(baseScore - deduction, 0);
        supplierRisk.setScore(Math.max(finalScore, 0));

    }
}
