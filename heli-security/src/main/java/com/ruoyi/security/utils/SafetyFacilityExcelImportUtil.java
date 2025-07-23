package com.ruoyi.security.utils;

import com.ruoyi.security.domain.SecurityEquipmentSafetyFacilityLedger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 安全防护设备设施Excel导入工具类
 * 支持处理合并单元格
 * 
 * @author wang
 * @date 2025-07-18
 */
public class SafetyFacilityExcelImportUtil {
    
    private static final Logger log = LoggerFactory.getLogger(SafetyFacilityExcelImportUtil.class);
    
    /**
     * 从Excel文件导入安全防护设备设施数据
     *
     * @param inputStream Excel文件输入流
     * @return 安全防护设备设施列表
     * @throws Exception 导入异常
     */
    public static List<SecurityEquipmentSafetyFacilityLedger> importFromExcel(InputStream inputStream) throws Exception {
        List<SecurityEquipmentSafetyFacilityLedger> dataList = new ArrayList<>();

        Workbook workbook = null;
        try {
            // 尝试创建工作簿
            try {
                workbook = new XSSFWorkbook(inputStream);
            } catch (Exception e) {
                inputStream.reset();
                workbook = new HSSFWorkbook(inputStream);
            }

            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new Exception("Excel文件中没有找到工作表");
            }

            log.info("开始处理Excel工作表: {}", sheet.getSheetName());

            // 处理合并单元格
            ExcelMergedCellUtil.processMergedCells(sheet);

            // 获取数据行数
            int lastRowNum = sheet.getLastRowNum();
            log.info("Excel工作表总行数: {}", lastRowNum + 1);

            // 用于存储上一行的关键字段值，实现向上查找
            String lastFacilityCategory = null;
            String lastSafetyFacilityName = null;

            // 从第二行开始读取数据（第一行通常是标题）
            for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                // 检查是否为空行
                if (isEmptyRow(row)) {
                    continue;
                }

                try {
                    SecurityEquipmentSafetyFacilityLedger facility = parseRowToFacility(sheet, row, rowIndex, lastFacilityCategory, lastSafetyFacilityName);
                    if (facility != null) {
                        // 更新最后的有效值
                        if (facility.getFacilityCategory() != null && !facility.getFacilityCategory().trim().isEmpty()) {
                            lastFacilityCategory = facility.getFacilityCategory();
                        }
                        if (facility.getSafetyFacilityName() != null && !facility.getSafetyFacilityName().trim().isEmpty()) {
                            lastSafetyFacilityName = facility.getSafetyFacilityName();
                        }

                        dataList.add(facility);
                        log.debug("成功解析第{}行数据: {} - {}", rowIndex + 1, facility.getFacilityCategory(), facility.getSafetyFacilityName());
                    }
                } catch (Exception e) {
                    log.error("解析第{}行数据时发生错误: {}", rowIndex + 1, e.getMessage());
                    throw new Exception("第" + (rowIndex + 1) + "行数据格式错误: " + e.getMessage());
                }
            }

            log.info("成功导入{}条安全防护设备设施数据", dataList.size());

        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

        return dataList;
    }
    
    /**
     * 将Excel行数据解析为安全防护设备设施对象
     *
     * @param sheet 工作表
     * @param row 数据行
     * @param rowIndex 行索引
     * @param lastFacilityCategory 上一行的设施类别
     * @param lastSafetyFacilityName 上一行的安全设施名称
     * @return 安全防护设备设施对象
     */
    private static SecurityEquipmentSafetyFacilityLedger parseRowToFacility(Sheet sheet, Row row, int rowIndex, String lastFacilityCategory, String lastSafetyFacilityName) {
        SecurityEquipmentSafetyFacilityLedger facility = new SecurityEquipmentSafetyFacilityLedger();
        
        try {
            // 根据Excel模板的列顺序解析数据
            // 序号 (A列，索引0) - 可选
            String idStr = getCellValue(sheet, rowIndex, 0);
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    facility.setId(Long.parseLong(idStr.trim()));
                } catch (NumberFormatException e) {
                    log.debug("序号列不是数字，跳过: {}", idStr);
                }
            }
            
            // 设施类别 (B列，索引1) - 必填，支持合并单元格和向上查找
            String facilityCategory = getCellValue(sheet, rowIndex, 1);
            if (facilityCategory == null || facilityCategory.trim().isEmpty()) {
                // 如果当前行为空，先使用传入的上一行值，如果还是空则向上查找
                if (lastFacilityCategory != null && !lastFacilityCategory.trim().isEmpty()) {
                    facilityCategory = lastFacilityCategory;
                    log.debug("第{}行设施类别为空，使用上一行的值: {}", rowIndex + 1, facilityCategory);
                } else {
                    // 向上查找最近的非空值
                    facilityCategory = findValueFromAbove(sheet, rowIndex, 1);
                    if (facilityCategory == null || facilityCategory.trim().isEmpty()) {
                        throw new Exception("设施类别不能为空，且无法从上方行获取有效值");
                    }
                    log.debug("第{}行设施类别为空，通过向上查找获得值: {}", rowIndex + 1, facilityCategory);
                }
            }
            facility.setFacilityCategory(facilityCategory.trim());

            // 安全设施名称 (C列，索引2) - 必填，支持向上查找
            String safetyFacilityName = getCellValue(sheet, rowIndex, 2);
            if (safetyFacilityName == null || safetyFacilityName.trim().isEmpty()) {
                // 如果当前行为空，先使用传入的上一行值，如果还是空则向上查找
                if (lastSafetyFacilityName != null && !lastSafetyFacilityName.trim().isEmpty()) {
                    safetyFacilityName = lastSafetyFacilityName;
                    log.debug("第{}行安全设施名称为空，使用上一行的值: {}", rowIndex + 1, safetyFacilityName);
                } else {
                    // 向上查找最近的非空值
                    safetyFacilityName = findValueFromAbove(sheet, rowIndex, 2);
                    if (safetyFacilityName == null || safetyFacilityName.trim().isEmpty()) {
                        throw new Exception("安全设施名称不能为空，且无法从上方行获取有效值");
                    }
                    log.debug("第{}行安全设施名称为空，通过向上查找获得值: {}", rowIndex + 1, safetyFacilityName);
                }
            }
            facility.setSafetyFacilityName(safetyFacilityName.trim());
            
            // 数量 (D列，索引3)
            String quantityStr = getCellValue(sheet, rowIndex, 3);
            if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                try {
                    facility.setQuantity(Long.parseLong(quantityStr.trim()));
                } catch (NumberFormatException e) {
                    log.warn("数量格式错误，设置为1: {}", quantityStr);
                    facility.setQuantity(1L);
                }
            }
            
            // 校验周期 (E列，索引4)
            String inspectionCycle = getCellValue(sheet, rowIndex, 4);
            if (inspectionCycle != null && !inspectionCycle.trim().isEmpty()) {
                facility.setInspectionCycle(inspectionCycle.trim());
            }
            
            // 下次校验时间 (F列，索引5)
            String nextInspectionDateStr = getCellValue(sheet, rowIndex, 5);
            if (nextInspectionDateStr != null && !nextInspectionDateStr.trim().isEmpty()) {
                try {
                    // 尝试多种日期格式
                    Date nextInspectionDate = parseDate(nextInspectionDateStr.trim());
                    facility.setNextInspectionDate(nextInspectionDate);
                } catch (Exception e) {
                    log.warn("下次校验时间格式错误: {}", nextInspectionDateStr);
                }
            }
            
            // 责任单位 (G列，索引6)
            String responsibleUnit = getCellValue(sheet, rowIndex, 6);
            if (responsibleUnit != null && !responsibleUnit.trim().isEmpty()) {
                facility.setResponsibleUnit(responsibleUnit.trim());
            }
            
            // 位置 (H列，索引7)
            String location = getCellValue(sheet, rowIndex, 7);
            if (location != null && !location.trim().isEmpty()) {
                facility.setLocation(location.trim());
            }
            
            // 负责人 (I列，索引8)
            String personInCharge = getCellValue(sheet, rowIndex, 8);
            if (personInCharge != null && !personInCharge.trim().isEmpty()) {
                facility.setPersonInCharge(personInCharge.trim());
            }
            
            // 现状 (J列，索引9)
            String currentStatus = getCellValue(sheet, rowIndex, 9);
            if (currentStatus != null && !currentStatus.trim().isEmpty()) {
                facility.setCurrentStatus(currentStatus.trim());
            }
            
            return facility;
            
        } catch (Exception e) {
            log.error("解析第{}行数据时发生错误: {}", rowIndex + 1, e.getMessage());
            throw new RuntimeException("第" + (rowIndex + 1) + "行数据解析失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取单元格值，支持合并单元格
     * 
     * @param sheet 工作表
     * @param rowIndex 行索引
     * @param colIndex 列索引
     * @return 单元格值
     */
    private static String getCellValue(Sheet sheet, int rowIndex, int colIndex) {
        // 首先尝试获取合并单元格的值
        String mergedValue = ExcelMergedCellUtil.getMergedCellValue(sheet, rowIndex, colIndex);
        if (mergedValue != null && !mergedValue.trim().isEmpty()) {
            return mergedValue;
        }
        
        // 如果不是合并单元格或合并单元格为空，获取当前单元格的值
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return null;
        }
        
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            return null;
        }
        
        return getCellValueAsString(cell);
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
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(date);
                } else {
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
     * 解析日期字符串
     * 
     * @param dateStr 日期字符串
     * @return 日期对象
     * @throws Exception 解析异常
     */
    private static Date parseDate(String dateStr) throws Exception {
        String[] patterns = {
            "yyyy-MM-dd",
            "yyyy/MM/dd",
            "yyyy-M-d",
            "yyyy/M/d",
            "MM/dd/yyyy",
            "dd/MM/yyyy"
        };
        
        for (String pattern : patterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                return sdf.parse(dateStr);
            } catch (Exception e) {
                // 继续尝试下一个格式
            }
        }
        
        throw new Exception("无法解析日期格式: " + dateStr);
    }
    
    /**
     * 向上查找指定列的最近非空值
     *
     * @param sheet 工作表
     * @param currentRowIndex 当前行索引
     * @param colIndex 列索引
     * @return 找到的非空值，如果没有找到返回null
     */
    private static String findValueFromAbove(Sheet sheet, int currentRowIndex, int colIndex) {
        // 从当前行的上一行开始向上查找
        for (int rowIndex = currentRowIndex - 1; rowIndex >= 1; rowIndex--) {
            String value = getCellValue(sheet, rowIndex, colIndex);
            if (value != null && !value.trim().isEmpty()) {
                log.debug("在第{}行找到列{}的非空值: {}", rowIndex + 1, colIndex + 1, value);
                return value.trim();
            }
        }
        return null;
    }

    /**
     * 检查是否为空行
     *
     * @param row 行对象
     * @return 是否为空行
     */
    private static boolean isEmptyRow(Row row) {
        if (row == null) {
            return true;
        }

        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String cellValue = getCellValueAsString(cell);
                if (cellValue != null && !cellValue.trim().isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }
}
