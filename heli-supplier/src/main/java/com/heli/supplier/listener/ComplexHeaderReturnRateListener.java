package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.heli.supplier.domain.SupplierReturnRate;
import com.heli.supplier.mapper.SupplierReturnRateMapper;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.heli.supplier.domain.SupplierReturnRate;
//import com.heli.supplier.mapper.SupplierReturnRateMapper;
//import lombok.extern.slf4j.Slf4j;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.read.listener.ReadListener;
//import com.alibaba.excel.util.ListUtils;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.heli.supplier.domain.ProductionErrorTable;
//import com.heli.supplier.domain.SupplierReturnRate;
//import com.heli.supplier.domain.SupplierZeroKilometerFailureRate;
//import com.heli.supplier.mapper.SupplierReturnRateMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.Month;
//import java.util.Date;
//import java.util.List;
//
///**
// * ClassName: OnetimeSimpleListener
// * Package: com.heli.supplier.listener
// * Description:
// *
// * @Author
// * @Create 2025/2/21 15:29
// * @Version 1.0
// */
//@Slf4j
//
//public class ReturnRateListener implements ReadListener<SupplierReturnRate> {
//
//    private static final int BATCH_COUNT = 200;
//
//    private int currentRow = 0;
//
//    @Autowired
//    private SupplierReturnRateMapper supplierReturnRateMapper;
//
//    private Date date;
//    private Date month;
//
//    private List<SupplierReturnRate> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
//
//    public ReturnRateListener(SupplierReturnRateMapper supplierReturnRateMapper, Date month) {
//        this.supplierReturnRateMapper = supplierReturnRateMapper;
//        this.month = month;
//    }
//
//    /**
//     * 批量读取Excel写入DB
//     *
//     * @param registerInfoExcel 全局监听内容
//     * @param analysisContext   读取到的Excel内容
//     */
//    @Override
//    public void invoke(SupplierReturnRate registerInfoExcel, AnalysisContext analysisContext) {
//        // 将监听到的数据存入缓存集合中
//        log.info("当前读取的数据为:" + registerInfoExcel);
//
//        // 数据处理
//        if(registerInfoExcel.getSupplierName() != null){
//            registerInfoExcel.setMonth(month);
//            // 计算分数
//            double score = calculateScore(registerInfoExcel.getReturnRate());
//            registerInfoExcel.setScore(score);
//            currentRow++;
//            // 加入缓存
//            cacheDataList.add(registerInfoExcel);
//        }
//
//        // 批量处理缓存的数据
//        if (cacheDataList.size() >= BATCH_COUNT) {
//            saveToDB();
//            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
//        }
//    }
//
//    /**
//     * 处理结尾, 不足100条
//     *
//     * @param analysisContext 全局监听内容
//     */
//    @Override
//    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        saveToDB();
//        log.info("所有数据解析完成");
//    }
//
//    /**
//     * 将读取到的内容写入DB
//     */
//    private void saveToDB() {
////        log.info("开始写入数据库");
////        supplierReturnRateMapper.insert(cacheDataList);
//        log.info("开始写入数据库，共 {} 条数据", cacheDataList.size());
//        for (SupplierReturnRate item : cacheDataList) {
//            try {
//                supplierReturnRateMapper.insert(item);
//            } catch (Exception e) {
//                log.error("插入数据失败: {}, 错误: {}", item.getSupplierName(), e.getMessage());
//            }
//        }
//    }
//
//
//    /**
//     * 根据返修率计算最终得分
//     * @param returnRate 返修率（字符串格式，如 "85.5%"）
//     * @return 最终得分（BigDecimal 格式）
//     */
//    private double calculateScore(String returnRate) {
//        if (returnRate == null || !returnRate.endsWith("%")) {
//            throw new IllegalArgumentException("返修率格式错误，必须是百分比字符串，如 '85.5%'");
//        }
//
//        // 去掉百分号并转换为数值
//        double rate = Double.parseDouble(returnRate.replace("%", "").trim());
//
//        // 计算基础分
//        double baseScore;
//        if (rate < 80) {
//            baseScore = 0;
//        } else if (rate < 90) {
//            baseScore = 30;
//        } else if (rate < 100) {
//            baseScore = 60;
//        } else {
//            baseScore = 100;
//        }
//        // 计算最终得分
//        return baseScore * 0.08;
//    }
//
//}
@Slf4j
public class ComplexHeaderReturnRateListener extends AnalysisEventListener<Map<Integer, String>> {
    private SupplierReturnRateMapper supplierReturnRateMapper;
    private Date targetMonth; // 目标月份
    private int monthColumnStart = -1; // 目标月份的起始列
    private int supplierNameColumn = 1; // 供应商名称在第2列（索引1）

    // 表头映射
    private Map<Integer, String> headerMap = new HashMap<>();
    private boolean isHeaderRow = true;

    public ComplexHeaderReturnRateListener(SupplierReturnRateMapper mapper, Date targetMonth) {
        this.supplierReturnRateMapper = mapper;
        this.targetMonth = targetMonth;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到表头: {}", headMap);
        // 存储表头信息
        headerMap.putAll(headMap);

        // 查找目标月份列
        // 假设格式为 "1月" 或 "2月" 这样
        String monthStr = new SimpleDateFormat("M").format(targetMonth) + "月";
        log.info("目标月份: {}", monthStr);

        for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
            if (monthStr.equals(entry.getValue())) {
                monthColumnStart = entry.getKey();
                log.info("找到目标月份列: {}", monthColumnStart);
                break;
            }
        }
    }

    @Override
    public void invoke(Map<Integer, String> rowData, AnalysisContext context) {
        // 跳过表头行
        if (isHeaderRow) {
            isHeaderRow = false;
            return;
        }

        log.info("当前行数据: {}", rowData);

        // 如果找不到目标月份列，则退出
        if (monthColumnStart == -1) {
            log.error("未找到目标月份列!");
            return;
        }

        // 获取供应商名称
        String supplierName = rowData.get(supplierNameColumn);
        if (supplierName == null || supplierName.trim().isEmpty()) {
            return; // 跳过空行
        }

        // 获取月度售后反馈率 (假设在目标月份列+7的位置)
        // 根据截图分析，"月度售后反馈率"在目标月份起始列后的第7列
        String returnRate = rowData.get(monthColumnStart + 8);
        log.info("读取到供应商[{}]的{}月售后反馈率: {}", supplierName,
                new SimpleDateFormat("M").format(targetMonth), returnRate);

        if (returnRate != null && !returnRate.isEmpty() && !returnRate.equals("#DIV/0!")) {
            try {
                // 创建实体并保存
                SupplierReturnRate entity = new SupplierReturnRate();
                entity.setSupplierName(supplierName);
                entity.setReturnRate(returnRate);
                entity.setMonth(targetMonth);

                // 计算分数
                try {
                    if (returnRate.endsWith("%")) {
                        double rate = Double.parseDouble(returnRate.replace("%", ""));
                        double baseScore;
                        if (rate < 80) baseScore = 0;
                        else if (rate < 90) baseScore = 30;
                        else if (rate < 100) baseScore = 60;
                        else baseScore = 100;
                        entity.setScore(baseScore * 0.08);
                    }
                } catch (Exception e) {
                    log.error("计算分数出错: {}", e.getMessage());
                }

                log.info("准备插入数据: {}", entity);
                supplierReturnRateMapper.insert(entity);
            } catch (Exception e) {
                log.error("插入数据出错: {}", e.getMessage());
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成!");
    }
}