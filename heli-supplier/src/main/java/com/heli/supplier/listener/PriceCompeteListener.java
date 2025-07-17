package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.supplier.domain.SupplierPriceCompete;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.mapper.SupplierPriceCompeteMapper;
import com.heli.supplier.mapper.SupplierRiskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class PriceCompeteListener implements ReadListener<SupplierPriceCompete> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierPriceCompeteMapper supplierPriceCompeteMapper;

    private Date date;
    private Date uploadMonth;

    private List<SupplierPriceCompete> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PriceCompeteListener(SupplierPriceCompeteMapper supplierPriceCompeteMapper,Date uploadMonth) {
        this.supplierPriceCompeteMapper = supplierPriceCompeteMapper;
        this.uploadMonth = uploadMonth;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SupplierPriceCompete registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);
        // 数据处理
        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setUploadTime(uploadMonth);
            setSupplierScore(registerInfoExcel);
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
        supplierPriceCompeteMapper.insert(cacheDataList);
    }
    /**
     * 计算价格竞争力分数
     * 各供应商默认基础分60分，如存在专项返利政策+20分；需自主提货-20分；
     * 常规物料存在价格优势+20分；单一物料无价格优势-5分；常规物料无价格优势-20分。
     *
     * @param priceType 价格类型
     * @return 计算后的分数
     */
    private Long calculateScore(String priceType) {
        // 默认基础分60分
        long baseScore = 60;

        if (priceType == null || priceType.trim().isEmpty()) {
            return baseScore;
        }

        // 根据价格类型调整分数
        switch (priceType) {
            case "专项返利政策":
            case "1":
                return baseScore + 20; // +20分

            case "需自主提货":
            case "2":
                return baseScore - 20; // -20分

            case "常规物料有价格优势":
            case "3":
                return baseScore + 20; // +20分

            case "单一物料无价格优势":
            case "4":
                return baseScore - 5;  // -5分

            case "常规物料无价格优势":
            case "5":
                return baseScore - 20; // -20分

            default:
                log.warn("未知的价格类型: {}, 使用默认分数", priceType);
                return baseScore;
        }
    }

    /**
     * 设置供应商分数
     * 包括计算分数和模型得分
     *
     * @param supplierPriceCompete 供应商价格竞争力对象
     */
    private void setSupplierScore(SupplierPriceCompete supplierPriceCompete) {
        if (supplierPriceCompete != null) {
            // 计算分数
            Long score = calculateScore(supplierPriceCompete.getPriceType());
            supplierPriceCompete.setScore(score);

            log.info("供应商 [{}] 的价格类型为 [{}], 计算得分: {}, 模型得分: {}",
                    supplierPriceCompete.getSupplierName(),
                    supplierPriceCompete.getPriceType(),
                    supplierPriceCompete.getScore(),
                    supplierPriceCompete.getModelScore());
        }
    }
}
