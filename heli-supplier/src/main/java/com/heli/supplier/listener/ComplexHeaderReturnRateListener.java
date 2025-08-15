package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heli.supplier.domain.SupplierReturnRate;
import com.heli.supplier.mapper.SupplierReturnRateMapper;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ComplexHeaderReturnRateListener extends AnalysisEventListener<Map<Integer, String>> {
    private SupplierReturnRateMapper supplierReturnRateMapper;
    private Date targetMonth; // 目标月份
    private int monthColumnStart = -1; // 目标月份的起始列
    private int supplierNameColumn = 1; // 供应商名称在第2列（索引1）

    // 表头映射
    private Map<Integer, String> headerMap = new HashMap<>();
    private boolean isHeaderRow = true;


    private int rowIndex = 0; // 添加行号计数器
    private Long batchId = System.currentTimeMillis(); // 添加批次号
    public ComplexHeaderReturnRateListener(SupplierReturnRateMapper mapper, Date targetMonth) {
        this.supplierReturnRateMapper = mapper;
        this.targetMonth = targetMonth;
        this.rowIndex = 0;// 重置行号
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
        // 在这里添加：删除该月份的旧数据
        deleteExistingData();
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

        // 处理 #DIV/0! 错误值
        if (returnRate == null || returnRate.contains("#DIV/0!")) {
            returnRate = "0%";
        }

        log.info("读取到供应商[{}]的{}月售后反馈率: {}", supplierName,
                new SimpleDateFormat("M").format(targetMonth), returnRate);

//        if (returnRate != null && !returnRate.isEmpty() && !returnRate.equals("#DIV/0!")) {
//        if (returnRate != null && !returnRate.isEmpty()) {
            try {
                // 创建实体并保存
                SupplierReturnRate entity = new SupplierReturnRate();
                entity.setSupplierName(supplierName);
                entity.setReturnRate(returnRate);
                entity.setMonth(targetMonth);

                entity.setRowIndex(rowIndex++); // 设置行号并递增
                entity.setBatchId(batchId); // 设置批次号


                // 计算分数
                try {
                    if(returnRate.equals("#DIV/0!"))
                        entity.setScore(0.0);
                    else if(returnRate == null || returnRate.isEmpty())
                        entity.setScore(0.0);
//                    if (returnRate.endsWith("%"))
                    else {
                        double rate = Double.parseDouble(returnRate.replace("%", ""));
                        rate = 100 - rate;
                        double baseScore;
                        if (rate < 80) baseScore = 0;
                        else if (rate < 90) baseScore = 30;
                        else if (rate < 100) baseScore = 60;
                        else baseScore = 100;
                        entity.setScore(baseScore*0.08);
                    }
                } catch (Exception e) {
                    entity.setScore(0.0);
                    log.error("计算分数出错: {}", e.getMessage());
                }

                log.info("准备插入数据: {}", entity);
                supplierReturnRateMapper.insert(entity);
            } catch (Exception e) {
                log.error("插入数据出错: {}", e.getMessage());
            }
        }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成!");
    }

    /**
     * 删除指定月份的数据
     */
    private void deleteExistingData() {
        try {
            // 使用MyBatis-Plus的删除方法
            QueryWrapper<SupplierReturnRate> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("month", targetMonth);
            int deletedCount = supplierReturnRateMapper.delete(deleteWrapper);
            log.info("删除了 {} 条该月份的旧数据", deletedCount);


            // 重置行号计数器
            this.rowIndex = 0;
        } catch (Exception e) {
            log.error("删除旧数据失败: {}", e.getMessage());
        }
    }
}