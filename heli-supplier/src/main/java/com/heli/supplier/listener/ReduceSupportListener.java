package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.supplier.domain.SupplierReduceSupport;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.mapper.SupplierReduceSupportMapper;
import com.heli.supplier.mapper.SupplierRiskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class ReduceSupportListener implements ReadListener<SupplierReduceSupport> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierReduceSupportMapper supplierReduceSupportMapper;

    private Date date;
    private Date uploadMonth;


    private List<SupplierReduceSupport> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public ReduceSupportListener(SupplierReduceSupportMapper supplierReduceSupportMapper,Date uploadMonth) {
        this.supplierReduceSupportMapper = supplierReduceSupportMapper;
        this.uploadMonth = uploadMonth;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SupplierReduceSupport registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);
        // 数据处理

        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setUploadTime(uploadMonth);
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
        supplierReduceSupportMapper.insert(cacheDataList);

    }

    private void calculateAndSetScore(SupplierReduceSupport supplierReduceSupport) {
        String percentageStr = supplierReduceSupport.getPercentage();
//        if (percentageStr == null || !percentageStr.endsWith("%")) {
//            throw new IllegalArgumentException("无效的降本占比格式");
//        }
        double percentageValue;
//        try {
        if (percentageStr != null && percentageStr.endsWith("%")) {
            percentageValue = Double.parseDouble(percentageStr.replace("%", ""));
//        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException("无法解析降本占比数值: " + percentageStr);
//        }
            int score = 10;
            if (percentageValue > 0.1 && percentageValue <= 0.5) {
                score = 20;
            } else if (percentageValue > 0.5 && percentageValue <= 1.5) {
                score = 50;
            } else if (percentageValue > 1.5 && percentageValue <= 3.0) {
                score = 80;
            } else if (percentageValue > 3.0) {
                score = 100;
            }
            supplierReduceSupport.setScore((double) score);
        } else {
            supplierReduceSupport.setScore(0.0);
        }
    }
}
