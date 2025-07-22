package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.supplier.domain.SupplierGuarantee;
import com.heli.supplier.domain.SupplierPerformanceServicesCollaboration;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.mapper.SupplierGuaranteeMapper;
import com.heli.supplier.mapper.SupplierPerformanceServicesCollaborationMapper;
import com.heli.supplier.mapper.SupplierRiskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class PerformanceServicesCollaborationListener implements ReadListener<SupplierPerformanceServicesCollaboration> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierPerformanceServicesCollaborationMapper supplierPerformanceServicesCollaborationMapper;

    private Date date;
    private Date uploadMonth;

    private List<SupplierPerformanceServicesCollaboration> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PerformanceServicesCollaborationListener(SupplierPerformanceServicesCollaborationMapper supplierPerformanceServicesCollaborationMapper,Date uploadMonth) {
        this.supplierPerformanceServicesCollaborationMapper = supplierPerformanceServicesCollaborationMapper;
        this.uploadMonth = uploadMonth;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SupplierPerformanceServicesCollaboration registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);
        // 数据处理
        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setTime(uploadMonth);
            currentRow++;

            // 计算分数并设置到当前数据对象中
            Long score = calculateScore(registerInfoExcel);
            registerInfoExcel.setScore(score); // 假设 SupplierPerformanceServicesCollaboration 类中有 setScore 方法


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
        supplierPerformanceServicesCollaborationMapper.insert(cacheDataList);
    }
    private Long calculateScore(SupplierPerformanceServicesCollaboration data) {
        long baseScore = 100;
        // 如果字段为空，则赋默认值 0
        long letter = (data.getLetter() != null) ? data.getLetter() : 0;
        long punish = (data.getPunish() != null) ? data.getPunish() : 0;
        long feedbackNotTimely = (data.getFeedbackNotTimely() != null) ? data.getFeedbackNotTimely() : 0;

        long deduction = (letter * 20) + (punish* 40) + (feedbackNotTimely * 10);
        long finalScore = baseScore - deduction;

        // 确保得分不能小于 0
        finalScore = Math.max(finalScore, 0);

        // 计算得分，确保最终分数也不能小于 0
        return finalScore;
    }
}
