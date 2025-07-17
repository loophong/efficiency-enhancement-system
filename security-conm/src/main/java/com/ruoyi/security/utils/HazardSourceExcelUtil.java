package com.ruoyi.security.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.security.domain.SecurityHazardSourceList;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 危险源清单Excel导出工具类
 * 扩展ExcelUtil，支持合并单元格
 * 
 * @author wang
 */
public class HazardSourceExcelUtil extends ExcelUtil<SecurityHazardSourceList> {
    private static final Logger log = LoggerFactory.getLogger(HazardSourceExcelUtil.class);

    public HazardSourceExcelUtil() {
        super(SecurityHazardSourceList.class);
    }

    /**
     * 导出危险源清单数据（支持合并单元格）
     * 
     * @param response 响应对象
     * @param list 数据列表
     * @param sheetName 工作表名称
     * @param title 标题
     */
    @Override
    public void exportExcel(HttpServletResponse response, List<SecurityHazardSourceList> list, String sheetName, String title) {
        super.exportExcel(response, list, sheetName, title);
        
        try {
            // 通过反射获取父类的workbook对象
            Field wbField = ExcelUtil.class.getDeclaredField("wb");
            wbField.setAccessible(true);
            Workbook workbook = (Workbook) wbField.get(this);
            
            if (workbook == null) {
                log.error("无法获取工作簿对象");
                return;
            }
            
            // 获取当前工作表
            Sheet sheet = workbook.getSheetAt(0);
            
            // 处理合并单元格
            processMergeCells(sheet, list);
        } catch (Exception e) {
            log.error("处理合并单元格失败", e);
        }
    }
    
    /**
     * 处理合并单元格
     * 
     * @param sheet 工作表
     * @param list 数据列表
     */
    private void processMergeCells(Sheet sheet, List<SecurityHazardSourceList> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        
        log.info("开始处理合并单元格，数据量：{}", list.size());
        
        // 获取标题行索引（通常是第0行）
        int titleRowIndex = 0;
        
        // 获取表头行索引（通常是第1行）
        int headerRowIndex = 1;
        
        // 数据起始行索引（通常是第2行）
        int dataStartRowIndex = 2;
        
        // 获取表头行
        Row headerRow = sheet.getRow(headerRowIndex);
        if (headerRow == null) {
            log.warn("未找到表头行，无法处理合并单元格");
            return;
        }
        
        // 列映射：列名 -> 列索引
        Map<String, Integer> columnMap = new HashMap<>();
        
        // 遍历表头行，建立列名与列索引的映射
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null) {
                String columnName = cell.getStringCellValue();
                columnMap.put(columnName, i);
                log.debug("列映射：{} -> {}", columnName, i);
            }
        }
        
        // 处理"作业活动"列的合并
        processMergeColumn(sheet, list, columnMap, "作业活动", dataStartRowIndex);
        
        // 移除控制措施列的合并处理
    }
    
    /**
     * 处理指定列的合并单元格
     * 
     * @param sheet 工作表
     * @param list 数据列表
     * @param columnMap 列映射
     * @param columnName 列名
     * @param dataStartRowIndex 数据起始行索引
     */
    private void processMergeColumn(Sheet sheet, List<SecurityHazardSourceList> list, Map<String, Integer> columnMap, 
            String columnName, int dataStartRowIndex) {
        
        Integer columnIndex = columnMap.get(columnName);
        if (columnIndex == null) {
            log.warn("未找到列：{}，无法处理合并单元格", columnName);
            return;
        }
        
        log.info("处理列 {} 的合并单元格，列索引：{}", columnName, columnIndex);
        
        // 记录需要合并的单元格范围
        List<CellRangeAddress> mergeRanges = new ArrayList<>();
        
        // 当前值
        String currentValue = null;
        // 合并开始行
        int mergeStartRow = dataStartRowIndex;
        
        // 遍历数据行
        for (int i = 0; i < list.size(); i++) {
            int rowIndex = dataStartRowIndex + i;
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                log.warn("未找到行：{}，跳过", rowIndex);
                continue;
            }
            
            Cell cell = row.getCell(columnIndex);
            String cellValue = cell != null ? cell.getStringCellValue() : "";
            
            // 第一行或值发生变化时
            if (currentValue == null) {
                currentValue = cellValue;
                mergeStartRow = rowIndex;
            } else if (!currentValue.equals(cellValue)) {
                // 如果当前值与上一个值不同，且有多行相同值，则合并单元格
                if (rowIndex - 1 > mergeStartRow) {
                    mergeRanges.add(new CellRangeAddress(mergeStartRow, rowIndex - 1, columnIndex, columnIndex));
                    log.debug("添加合并范围：({}, {}, {}, {})", mergeStartRow, rowIndex - 1, columnIndex, columnIndex);
                }
                
                // 更新当前值和合并开始行
                currentValue = cellValue;
                mergeStartRow = rowIndex;
            }
        }
        
        // 处理最后一组相同值
        int lastRowIndex = dataStartRowIndex + list.size() - 1;
        if (lastRowIndex > mergeStartRow) {
            mergeRanges.add(new CellRangeAddress(mergeStartRow, lastRowIndex, columnIndex, columnIndex));
            log.debug("添加最后一个合并范围：({}, {}, {}, {})", mergeStartRow, lastRowIndex, columnIndex, columnIndex);
        }
        
        // 应用合并
        for (CellRangeAddress range : mergeRanges) {
            sheet.addMergedRegion(range);
        }
        
        log.info("列 {} 合并完成，共合并 {} 个区域", columnName, mergeRanges.size());
    }
} 