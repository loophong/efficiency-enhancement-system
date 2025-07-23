package com.ruoyi.security.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel日期处理工具类
 * 专门处理Excel导入时的各种日期格式问题
 * 
 * @author system
 * @date 2025-01-18
 */
public class ExcelDateUtils {
    
    private static final Logger log = LoggerFactory.getLogger(ExcelDateUtils.class);
    
    // 支持的日期格式模式
    private static final String[] DATE_PATTERNS = {
        "yyyy-MM-dd",
        "yyyy.MM.dd", 
        "yyyy/MM/dd",
        "yyyy-M-dd",
        "yyyy.M.dd",
        "yyyy/M/dd",
        "yyyy-MM-d",
        "yyyy.MM.d",
        "yyyy/MM/d",
        "yyyy-M-d",
        "yyyy.M.d",
        "yyyy/M/d"
    };
    
    // 年份范围验证
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;
    
    /**
     * 解析Excel中的日期字符串
     * 支持单个日期和日期范围格式
     * 
     * @param dateStr 日期字符串
     * @return 解析后的日期，如果是日期范围则返回起始日期，解析失败返回null
     */
    public static Date parseExcelDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        
        try {
            String cleanDateStr = dateStr.trim();
            
            // 处理日期范围格式（如：2024.4.19-2024.12.30）
            if (cleanDateStr.contains("-") && !cleanDateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                // 可能是日期范围，提取结束日期
                String[] parts = cleanDateStr.split("-");
                if (parts.length >= 2) {
                    // 取最后一个日期作为结束日期
                    cleanDateStr = parts[parts.length - 1].trim();
                    log.debug("检测到日期范围格式，提取结束日期: {}", cleanDateStr);
                }
            }
            
            // 验证年份是否在合理范围内
            if (!isValidYear(cleanDateStr)) {
                log.warn("日期年份超出合理范围: {}", cleanDateStr);
                return null;
            }
            
            // 尝试解析日期
            return parseWithPatterns(cleanDateStr);
            
        } catch (Exception e) {
            log.error("解析日期失败: {}, 错误: {}", dateStr, e.getMessage());
            return null;
        }
    }
    
    /**
     * 验证日期字符串中的年份是否在合理范围内
     * 
     * @param dateStr 日期字符串
     * @return true如果年份合理，false否则
     */
    private static boolean isValidYear(String dateStr) {
        try {
            // 提取年份部分
            String yearStr = null;
            if (dateStr.matches("^\\d{4}[./-].*")) {
                yearStr = dateStr.substring(0, 4);
            } else if (dateStr.matches("^\\d{1,3}[./-].*")) {
                // 年份位数不足4位，可能是错误数据
                return false;
            } else if (dateStr.matches("^\\d{5,}[./-].*")) {
                // 年份位数超过4位，可能是错误数据
                return false;
            }
            
            if (yearStr != null) {
                int year = Integer.parseInt(yearStr);
                return year >= MIN_YEAR && year <= MAX_YEAR;
            }
            
            return true; // 无法提取年份时，允许进一步处理
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 使用多种日期格式模式尝试解析日期
     * 
     * @param dateStr 日期字符串
     * @return 解析后的日期，失败返回null
     */
    private static Date parseWithPatterns(String dateStr) {
        for (String pattern : DATE_PATTERNS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                sdf.setLenient(false); // 严格模式，不允许无效日期
                Date date = sdf.parse(dateStr);
                
                // 验证解析后的日期年份
                if (isValidParsedDate(date)) {
                    log.debug("成功解析日期: {} -> {}", dateStr, date);
                    return date;
                }
            } catch (ParseException e) {
                // 继续尝试下一个格式
                continue;
            }
        }
        
        log.warn("所有日期格式都无法解析: {}", dateStr);
        return null;
    }
    
    /**
     * 验证解析后的日期是否合理
     * 
     * @param date 解析后的日期
     * @return true如果日期合理，false否则
     */
    private static boolean isValidParsedDate(Date date) {
        if (date == null) {
            return false;
        }
        
        // 检查日期是否在合理范围内
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        try {
            int year = Integer.parseInt(yearFormat.format(date));
            return year >= MIN_YEAR && year <= MAX_YEAR;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 安全地解析Excel数值型日期
     * 
     * @param excelDateValue Excel日期数值
     * @return 解析后的日期，失败返回null
     */
    public static Date parseExcelNumericDate(Double excelDateValue) {
        if (excelDateValue == null) {
            return null;
        }
        
        try {
            // Excel日期数值范围检查：1900-01-01(1) 到 9999-12-31(2958465)
            if (excelDateValue <= 0 || excelDateValue > 2958465) {
                log.warn("Excel日期数值超出合理范围: {}", excelDateValue);
                return null;
            }
            
            // 使用POI的DateUtil转换
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(excelDateValue);
            
            // 验证转换后的日期
            if (isValidParsedDate(date)) {
                return date;
            } else {
                log.warn("Excel数值转换的日期不合理: {} -> {}", excelDateValue, date);
                return null;
            }
            
        } catch (Exception e) {
            log.error("解析Excel数值型日期失败: {}, 错误: {}", excelDateValue, e.getMessage());
            return null;
        }
    }
    
    /**
     * 格式化日期为标准字符串
     * 
     * @param date 日期对象
     * @return 格式化后的日期字符串
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
