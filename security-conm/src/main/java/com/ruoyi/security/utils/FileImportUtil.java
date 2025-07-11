package com.ruoyi.security.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件导入工具类，用于处理上传的文件内容，并为每行数据添加关联ID
 */
public class FileImportUtil {
    private static final Logger log = LoggerFactory.getLogger(FileImportUtil.class);

    /**
     * 解析CSV文件，并为每行数据添加关联ID
     * 
     * @param file 上传的CSV文件
     * @param fileManagementId 文件管理记录ID
     * @param hasHeader 是否包含标题行
     * @return 解析后的数据列表
     */
    public static List<Map<String, Object>> parseCSVWithRelatedId(MultipartFile file, Long fileManagementId, boolean hasHeader) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        
        try (InputStream inputStream = file.getInputStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            
            // 获取CSV标题
            Map<String, Integer> headerMap = csvParser.getHeaderMap();
            String[] headers = headerMap.keySet().toArray(new String[0]);
            
            // 逐行读取CSV数据
            for (CSVRecord record : csvParser) {
                Map<String, Object> rowData = new HashMap<>();
                
                // 将每列数据添加到Map中
                for (String header : headers) {
                    rowData.put(header, record.get(header));
                }
                
                // 添加关联ID
                rowData.put("relatedId", fileManagementId);
                
                resultList.add(rowData);
            }
            
            log.info("成功解析CSV文件，共 {} 行数据，已添加关联ID: {}", resultList.size(), fileManagementId);
            
        } catch (IOException e) {
            log.error("解析CSV文件失败: {}", e.getMessage(), e);
        }
        
        return resultList;
    }
    
    /**
     * 解析Excel文件，并为每行数据添加关联ID
     * 
     * @param file 上传的Excel文件
     * @param fileManagementId 文件管理记录ID
     * @param sheetIndex 工作表索引（从0开始）
     * @param hasHeader 是否包含标题行
     * @return 解析后的数据列表
     */
    public static List<Map<String, Object>> parseExcelWithRelatedId(MultipartFile file, Long fileManagementId, int sheetIndex, boolean hasHeader) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            
            // 获取指定的工作表
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            if (sheet == null) {
                log.error("未找到索引为 {} 的工作表", sheetIndex);
                return resultList;
            }
            
            // 获取标题行
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                log.error("Excel文件中未找到标题行");
                return resultList;
            }
            
            // 获取标题
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }
            
            // 逐行读取数据
            int startRowIndex = hasHeader ? 1 : 0;
            for (int i = startRowIndex; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                
                Map<String, Object> rowData = new HashMap<>();
                
                // 将每列数据添加到Map中
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    rowData.put(headers.get(j), cell != null ? getCellValueAsString(cell) : "");
                }
                
                // 添加关联ID
                rowData.put("relatedId", fileManagementId);
                
                resultList.add(rowData);
            }
            
            log.info("成功解析Excel文件，共 {} 行数据，已添加关联ID: {}", resultList.size(), fileManagementId);
            
        } catch (IOException e) {
            log.error("解析Excel文件失败: {}", e.getMessage(), e);
        }
        
        return resultList;
    }
    
    /**
     * 获取单元格的值并转换为字符串
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // 处理数字和日期
                return String.valueOf(cell.getNumericCellValue());
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
                return "";
            default:
                return "";
        }
    }
    
    /**
     * 通用方法：根据文件类型解析上传的文件，并为每行数据添加关联ID
     * 
     * @param file 上传的文件
     * @param fileManagementId 文件管理记录ID
     * @param hasHeader 是否包含标题行
     * @return 解析后的数据列表
     */
    public static List<Map<String, Object>> parseFileWithRelatedId(MultipartFile file, Long fileManagementId, boolean hasHeader) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            log.error("文件名为空，无法确定文件类型");
            return new ArrayList<>();
        }
        
        // 根据文件扩展名判断文件类型
        if (fileName.toLowerCase().endsWith(".csv")) {
            return parseCSVWithRelatedId(file, fileManagementId, hasHeader);
        } else if (fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".xlsx")) {
            return parseExcelWithRelatedId(file, fileManagementId, 0, hasHeader);
        } else {
            log.error("不支持的文件类型: {}", fileName);
            return new ArrayList<>();
        }
    }
    
    /**
     * 批量保存带有关联ID的数据
     * 
     * @param <T> 实体类型
     * @param dataList 数据列表
     * @param entityClass 实体类
     * @param service 服务类
     * @return 保存成功的记录数
     */
    public static <T> int batchSaveWithRelatedId(List<Map<String, Object>> dataList, Class<T> entityClass, Object service) {
        if (dataList == null || dataList.isEmpty()) {
            log.warn("没有数据需要保存");
            return 0;
        }
        
        int successCount = 0;
        
        try {
            // 获取服务类中的批量插入方法
            java.lang.reflect.Method batchInsertMethod = findBatchInsertMethod(service.getClass());
            if (batchInsertMethod != null) {
                // 将Map列表转换为实体列表
                List<T> entityList = convertToEntityList(dataList, entityClass);
                
                // 调用批量插入方法
                Object result = batchInsertMethod.invoke(service, entityList);
                if (result instanceof Integer) {
                    successCount = (Integer) result;
                }
                
                log.info("批量保存数据成功，共 {} 条记录", successCount);
            } else {
                log.error("未找到批量插入方法");
            }
        } catch (Exception e) {
            log.error("批量保存数据失败: {}", e.getMessage(), e);
        }
        
        return successCount;
    }
    
    /**
     * 查找服务类中的批量插入方法
     */
    private static java.lang.reflect.Method findBatchInsertMethod(Class<?> serviceClass) {
        for (java.lang.reflect.Method method : serviceClass.getMethods()) {
            String methodName = method.getName().toLowerCase();
            if ((methodName.contains("batch") && methodName.contains("insert")) || 
                methodName.contains("insertbatch") || 
                methodName.contains("batchinsert")) {
                return method;
            }
        }
        return null;
    }
    
    /**
     * 将Map列表转换为实体列表
     */
    private static <T> List<T> convertToEntityList(List<Map<String, Object>> dataList, Class<T> entityClass) {
        List<T> entityList = new ArrayList<>();
        
        try {
            for (Map<String, Object> data : dataList) {
                T entity = entityClass.getDeclaredConstructor().newInstance();
                
                // 遍历Map中的每个字段
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    String fieldName = entry.getKey();
                    Object fieldValue = entry.getValue();
                    
                    try {
                        // 查找对应的字段
                        java.lang.reflect.Field field = findField(entityClass, fieldName);
                        if (field != null) {
                            field.setAccessible(true);
                            
                            // 设置字段值（需要类型转换）
                            field.set(entity, convertValueToFieldType(fieldValue, field.getType()));
                        }
                    } catch (Exception e) {
                        log.debug("设置字段 {} 值失败: {}", fieldName, e.getMessage());
                    }
                }
                
                entityList.add(entity);
            }
        } catch (Exception e) {
            log.error("转换实体列表失败: {}", e.getMessage(), e);
        }
        
        return entityList;
    }
    
    /**
     * 查找实体类中的字段（包括父类字段）
     */
    private static java.lang.reflect.Field findField(Class<?> clazz, String fieldName) {
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            try {
                return currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // 在当前类中未找到字段，继续查找父类
                currentClass = currentClass.getSuperclass();
            }
        }
        return null;
    }
    
    /**
     * 将值转换为字段类型
     */
    private static Object convertValueToFieldType(Object value, Class<?> fieldType) {
        if (value == null) {
            return null;
        }
        
        // 如果值已经是目标类型，直接返回
        if (fieldType.isAssignableFrom(value.getClass())) {
            return value;
        }
        
        String strValue = value.toString();
        
        // 根据字段类型进行转换
        if (fieldType == String.class) {
            return strValue;
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return Integer.parseInt(strValue);
        } else if (fieldType == Long.class || fieldType == long.class) {
            return Long.parseLong(strValue);
        } else if (fieldType == Double.class || fieldType == double.class) {
            return Double.parseDouble(strValue);
        } else if (fieldType == Float.class || fieldType == float.class) {
            return Float.parseFloat(strValue);
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return Boolean.parseBoolean(strValue);
        } else if (fieldType == java.util.Date.class) {
            try {
                return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strValue);
            } catch (Exception e) {
                try {
                    return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(strValue);
                } catch (Exception ex) {
                    return null;
                }
            }
        }
        
        // 其他类型暂不支持
        return null;
    }
} 