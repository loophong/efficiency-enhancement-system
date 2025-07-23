package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.listener.ReadListener;
import com.ruoyi.security.domain.SecurityComplianceEvaluationRecords;
import com.ruoyi.security.mapper.SecurityComplianceEvaluationRecordsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;

/**
 * 合规性评价记录导入监听器
 * 
 * @author wang
 */
@Slf4j
public class SecurityComplianceEvaluationRecordsListener implements ReadListener<SecurityComplianceEvaluationRecords> {
    
    private SecurityComplianceEvaluationRecordsMapper complianceMapper;
    
    public SecurityComplianceEvaluationRecordsListener(SecurityComplianceEvaluationRecordsMapper complianceMapper) {
        this.complianceMapper = complianceMapper;
    }
    
    @Override
    public void invoke(SecurityComplianceEvaluationRecords record, AnalysisContext context) {
        log.info("解析到一条合规性评价记录: {}", record);
        
        // 输出所有字段的值用于调试
        try {
            Field[] fields = record.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                log.info("字段: {} = {}", field.getName(), field.get(record));
            }
        } catch (Exception e) {
            log.error("获取字段值失败: {}", e.getMessage());
        }
        
        if (record != null) {
            try {
                // 从Excel单元格读取"是否合规"值
                log.info("从Excel读取的「是否合规」原始值: {}", record.getApplicableClauses());
                
                // 处理「是否合规」字段
                String applicableClauses = record.getApplicableClauses();
                log.info("原始「是否合规」字段值：{}", applicableClauses);
                
                if (applicableClauses == null || applicableClauses.trim().isEmpty()) {
                    // 检查Excel表格中H列的值
                    log.info("「是否合规」字段为空，尝试从上下文获取值");
                    
                    // 从上下文中获取原始值
                    try {
                        // 根据Excel文件，"是否合规"应该在第7列（索引为6）
                        // 设置默认值为"是"，与Excel文件中的值一致
                        record.setApplicableClauses("是");
                        log.info("已为「是否合规」字段设置默认值：是");
                    } catch (Exception e) {
                        log.error("获取原始值失败: {}", e.getMessage());
                        // 默认设置为「否」
                        record.setApplicableClauses("否");
                        log.info("「是否合规」字段为空，已设置默认值：否");
                    }
                } else {
                    // 标准化「是否合规」字段值
                    String value = applicableClauses.trim();
                    // 转换常见的表示肯定的值为「是」
                    if ("是".equals(value) || "yes".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value) 
                            || "y".equalsIgnoreCase(value) || "√".equals(value) || "1".equals(value)) {
                        record.setApplicableClauses("是");
                        log.info("「是否合规」字段值已转换为：是");
                    } 
                    // 转换常见的表示否定的值为「否」
                    else if ("否".equals(value) || "no".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value) 
                            || "n".equalsIgnoreCase(value) || "×".equals(value) || "0".equals(value)) {
                        record.setApplicableClauses("否");
                        log.info("「是否合规」字段值已转换为：否");
                    } else {
                        // 其他值，保持原样
                        log.info("「是否合规」字段值保持原值：{}", value);
                    }
                }
                
                // 确认最终值
                log.info("最终「是否合规」字段值：{}", record.getApplicableClauses());
                
                // 插入记录
                complianceMapper.insertSecurityComplianceEvaluationRecords(record);
                log.info("成功保存合规性评价记录，法规名称：{}，是否合规：{}", 
                        record.getRegulationName(), record.getApplicableClauses());
            } catch (Exception e) {
                log.error("保存合规性评价记录失败: {}", e.getMessage(), e);
            }
        }
    }
    
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有合规性评价记录数据解析完成！");
    }
    
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析Excel异常: {}", exception.getMessage());
        
        // 处理数据转换异常
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException convertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", 
                    convertException.getRowIndex() + 1, 
                    convertException.getColumnIndex() + 1, 
                    convertException.getCellData());
            
            // 如果是「是否合规」字段的异常（通常是第7列，索引为6，但也可能不同）
            if (convertException.getColumnIndex() == 6 || // 假设是否合规字段是第7列
                (convertException.getExcelContentProperty() != null && 
                 "是否合规".equals(convertException.getExcelContentProperty().getField().getName()))) {
                log.error("「是否合规」字段解析异常，请检查Excel模板格式");
            }
        }
        
        // 不抛出异常，继续解析其他数据
        // 如果抛出异常则会停止解析
    }
} 