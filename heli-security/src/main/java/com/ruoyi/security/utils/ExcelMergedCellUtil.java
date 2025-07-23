package com.ruoyi.security.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Excel合并单元格处理工具类
 * 
 * @author wang
 * @date 2025-07-18
 */
public class ExcelMergedCellUtil {
    
    private static final Logger log = LoggerFactory.getLogger(ExcelMergedCellUtil.class);
    
    /**
     * 处理合并单元格，将合并单元格的值填充到所有相关行
     * 
     * @param sheet Excel工作表
     */
    public static void processMergedCells(Sheet sheet) {
        if (sheet == null) {
            return;
        }
        
        // 获取所有合并单元格区域
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        
        log.info("工作表 {} 中发现 {} 个合并单元格区域", sheet.getSheetName(), mergedRegions.size());
        
        for (CellRangeAddress mergedRegion : mergedRegions) {
            processSingleMergedRegion(sheet, mergedRegion);
        }
    }
    
    /**
     * 处理单个合并单元格区域
     * 
     * @param sheet Excel工作表
     * @param mergedRegion 合并单元格区域
     */
    private static void processSingleMergedRegion(Sheet sheet, CellRangeAddress mergedRegion) {
        int firstRow = mergedRegion.getFirstRow();
        int lastRow = mergedRegion.getLastRow();
        int firstCol = mergedRegion.getFirstColumn();
        int lastCol = mergedRegion.getLastColumn();
        
        log.debug("处理合并单元格区域: 行({}-{}), 列({}-{})", firstRow, lastRow, firstCol, lastCol);
        
        // 获取合并单元格的值（通常在第一个单元格中）
        Row firstRowObj = sheet.getRow(firstRow);
        if (firstRowObj == null) {
            return;
        }
        
        Cell firstCell = firstRowObj.getCell(firstCol);
        if (firstCell == null) {
            return;
        }
        
        // 获取合并单元格的值
        String mergedValue = getCellValueAsString(firstCell);
        
        if (mergedValue == null || mergedValue.trim().isEmpty()) {
            return;
        }
        
        log.debug("合并单元格值: {}", mergedValue);
        
        // 将值填充到合并区域内的所有单元格
        for (int rowIndex = firstRow; rowIndex <= lastRow; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            
            for (int colIndex = firstCol; colIndex <= lastCol; colIndex++) {
                Cell cell = row.getCell(colIndex);
                if (cell == null) {
                    cell = row.createCell(colIndex);
                }
                
                // 如果单元格为空或者是合并单元格的一部分，则填充值
                String currentValue = getCellValueAsString(cell);
                if (currentValue == null || currentValue.trim().isEmpty()) {
                    cell.setCellValue(mergedValue);
                    log.debug("填充单元格 ({},{}) 值: {}", rowIndex, colIndex, mergedValue);
                }
            }
        }
    }
    
    /**
     * 获取单元格的字符串值
     * 
     * @param cell 单元格
     * @return 字符串值
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // 处理数字，避免科学计数法
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
                    try {
                        return String.valueOf(cell.getNumericCellValue());
                    } catch (Exception ex) {
                        return cell.getCellFormula();
                    }
                }
            case BLANK:
            case _NONE:
            default:
                return null;
        }
    }
    
    /**
     * 检查指定单元格是否在合并区域内
     * 
     * @param sheet Excel工作表
     * @param rowIndex 行索引
     * @param colIndex 列索引
     * @return 是否在合并区域内
     */
    public static boolean isCellInMergedRegion(Sheet sheet, int rowIndex, int colIndex) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        
        for (CellRangeAddress mergedRegion : mergedRegions) {
            if (rowIndex >= mergedRegion.getFirstRow() && rowIndex <= mergedRegion.getLastRow() &&
                colIndex >= mergedRegion.getFirstColumn() && colIndex <= mergedRegion.getLastColumn()) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 获取合并单元格的主单元格值
     * 
     * @param sheet Excel工作表
     * @param rowIndex 行索引
     * @param colIndex 列索引
     * @return 合并单元格的值
     */
    public static String getMergedCellValue(Sheet sheet, int rowIndex, int colIndex) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        
        for (CellRangeAddress mergedRegion : mergedRegions) {
            if (rowIndex >= mergedRegion.getFirstRow() && rowIndex <= mergedRegion.getLastRow() &&
                colIndex >= mergedRegion.getFirstColumn() && colIndex <= mergedRegion.getLastColumn()) {
                
                // 返回合并区域第一个单元格的值
                Row firstRow = sheet.getRow(mergedRegion.getFirstRow());
                if (firstRow != null) {
                    Cell firstCell = firstRow.getCell(mergedRegion.getFirstColumn());
                    return getCellValueAsString(firstCell);
                }
            }
        }
        
        // 如果不在合并区域内，返回当前单元格的值
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            Cell cell = row.getCell(colIndex);
            return getCellValueAsString(cell);
        }
        
        return null;
    }
}
