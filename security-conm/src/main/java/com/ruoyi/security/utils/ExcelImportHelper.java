package com.ruoyi.security.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.ruoyi.security.domain.SecurityComplianceEvaluationRecords;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Excel导入辅助类，专用于合规性评价记录
 * 
 * @author wang
 */
@Slf4j
public class ExcelImportHelper {
    
    // 修改DATE_PATTERNS数组，增加更多格式支持
    private static final String[] DATE_PATTERNS = {
        "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd", 
        "yyyy年MM月dd日", "yyyy-M-d", "yyyy/M/d", 
        "M/d/yyyy", "dd-MM-yyyy", "dd/MM/yyyy",
        "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss",
        "yyyy.MM.dd HH:mm:ss", "yyyy年MM月dd日 HH时mm分ss秒",
        "EEE, MMM dd, yyyy", "EEE, d MMM yyyy", "yyyy-MM-dd'T'HH:mm:ss",
        "MMMM d, yyyy", "d MMMM, yyyy", "dd MMMM yyyy"
    };
    
    // 在类的开头添加一个常量数组
    private static final String[] CHINESE_MONTH_NAMES = {
        "一月", "二月", "三月", "四月", "五月", "六月", 
        "七月", "八月", "九月", "十月", "十一月", "十二月"
    };
    
    /**
     * 导入Excel文件
     * 
     * @param inputStream Excel文件输入流
     * @return 导入的记录列表
     */
    public static List<SecurityComplianceEvaluationRecords> importExcel(InputStream inputStream) {
        List<SecurityComplianceEvaluationRecords> dataList = new ArrayList<>();
        
        try {
            // 使用通用的Map<Integer, Object>方式读取Excel，避免直接映射的问题
            EasyExcel.read(inputStream)
                .registerReadListener(new AnalysisEventListener<Map<Integer, Object>>() {
                    @Override
                    public void invoke(Map<Integer, Object> data, AnalysisContext context) {
                        try {
                            // 跳过表头行
                            if (context.readRowHolder().getRowIndex() == 0) {
                                log.info("表头行数据: {}", data);
                                return;
                            }
                            
                            SecurityComplianceEvaluationRecords record = new SecurityComplianceEvaluationRecords();
                            
                            // 从列中获取数据
                            log.info("读取行数据: {}", data);
                            
                            // 法律法规名称（B列，索引1）
                            record.setRegulationName(getStringValue(data.get(1)));
                            
                            // 文号（C列，索引2）
                            record.setDocumentNumber(getStringValue(data.get(2)));
                            
                            // 发布单位（D列，索引3）
                            record.setIssuingUnit(getStringValue(data.get(3)));
                            
                            // 发布/修订日期（E列，索引4）
                            Object issuanceRevisionDateObj = data.get(4);
                            log.info("发布/修订日期原始对象: {}, 类型: {}", 
                                    issuanceRevisionDateObj, 
                                    issuanceRevisionDateObj != null ? issuanceRevisionDateObj.getClass().getName() : "null");
                            
                            Date issuanceRevisionDate = getDateValue(issuanceRevisionDateObj);
                            if (issuanceRevisionDate != null) {
                                record.setIssuanceRevisionDate(issuanceRevisionDate);
                                log.info("发布/修订日期解析成功: {}", 
                                        new SimpleDateFormat("yyyy-MM-dd").format(issuanceRevisionDate));
                            }
                            
                            // 实施日期（F列，索引5）
                            Object implementationDateObj = data.get(5);
                            log.info("实施日期原始对象: {}, 类型: {}", 
                                    implementationDateObj, 
                                    implementationDateObj != null ? implementationDateObj.getClass().getName() : "null");
                            
                            Date implementationDate = getDateValue(implementationDateObj);
                            if (implementationDate != null) {
                                record.setImplementationDate(implementationDate);
                                log.info("实施日期解析成功: {}", 
                                        new SimpleDateFormat("yyyy-MM-dd").format(implementationDate));
                            }
                            
                            // 效力（G列，索引6）
                            record.setEffectiveness(getStringValue(data.get(6)));
                            
                            // 是否合规（H列，索引7）
                            String applicableClauses = getStringValue(data.get(7));
                            log.info("读取到「是否合规」值: {}", applicableClauses);
                            
                            if (StringUtils.isEmpty(applicableClauses)) {
                                record.setApplicableClauses("否");
                                log.info("「是否合规」字段为空，设置为默认值: 否");
                            } else if ("是".equals(applicableClauses) || 
                                       "yes".equalsIgnoreCase(applicableClauses) || 
                                       "true".equalsIgnoreCase(applicableClauses) || 
                                       "y".equalsIgnoreCase(applicableClauses) || 
                                       "√".equals(applicableClauses) || 
                                       "1".equals(applicableClauses) ||
                                       "是的".equals(applicableClauses) ||
                                       "合规".equals(applicableClauses) ||
                                       "符合".equals(applicableClauses)) {
                                record.setApplicableClauses("是");
                                log.info("「是否合规」字段值转换为: 是");
                            } else {
                                record.setApplicableClauses("否");
                                log.info("「是否合规」字段值转换为: 否");
                            }
                            
                            // 如果日期解析失败，尝试再次处理
                            if (record.getIssuanceRevisionDate() == null && issuanceRevisionDateObj != null) {
                                log.info("尝试再次解析发布/修订日期");
                                // 尝试直接创建日期对象
                                try {
                                    String dateStr = issuanceRevisionDateObj.toString().trim();
                                    // 清理可能的异常字符
                                    dateStr = dateStr.replaceAll("[\\s　]+", " ")  // 处理全角空格和多余空格
                                                   .replace("-", "/")
                                                   .replace(".", "/");
                                    
                                    // 记录处理后的字符串
                                    log.info("清理后的发布/修订日期字符串: {}", dateStr);
                                    
                                    if (dateStr.startsWith("星期")) {
                                        // 尝试专门的中文日期解析方法
                                        Date parsedDate = parseChineseWeekDateFormat(dateStr);
                                        if (parsedDate != null) {
                                            record.setIssuanceRevisionDate(parsedDate);
                                            log.info("使用专门方法成功解析发布/修订日期: {}", 
                                                   new SimpleDateFormat("yyyy-MM-dd").format(parsedDate));
                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("二次尝试解析发布/修订日期失败: {}", e.getMessage());
                                }
                            }
                            
                            // 对实施日期同样处理
                            if (record.getImplementationDate() == null && implementationDateObj != null) {
                                log.info("尝试再次解析实施日期");
                                try {
                                    String dateStr = implementationDateObj.toString().trim();
                                    // 清理可能的异常字符
                                    dateStr = dateStr.replaceAll("[\\s　]+", " ")  // 处理全角空格和多余空格
                                                   .replace("-", "/")
                                                   .replace(".", "/");
                                    
                                    // 记录处理后的字符串
                                    log.info("清理后的实施日期字符串: {}", dateStr);
                                    
                                    if (dateStr.startsWith("星期")) {
                                        // 尝试专门的中文日期解析方法
                                        Date parsedDate = parseChineseWeekDateFormat(dateStr);
                                        if (parsedDate != null) {
                                            record.setImplementationDate(parsedDate);
                                            log.info("使用专门方法成功解析实施日期: {}", 
                                                   new SimpleDateFormat("yyyy-MM-dd").format(parsedDate));
                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("二次尝试解析实施日期失败: {}", e.getMessage());
                                }
                            }
                            
                            // 备注（I列，索引8）
                            record.setComplianceStatus(getStringValue(data.get(8)));
                            
                            // 添加到列表
                            dataList.add(record);
                            log.info("处理完成一条记录: 法规名称={}, 是否合规={}, 发布日期={}, 实施日期={}", 
                                    record.getRegulationName(), 
                                    record.getApplicableClauses(),
                                    record.getIssuanceRevisionDate(),
                                    record.getImplementationDate());
                        } catch (Exception e) {
                            log.error("处理Excel行数据异常: {}", e.getMessage(), e);
                        }
                    }
                    
                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                        log.info("共读取到 {} 条记录", dataList.size());
                    }
                })
                .sheet()
                .doRead();
            
            return dataList;
        } catch (Exception e) {
            log.error("Excel导入异常: {}", e.getMessage(), e);
            throw new RuntimeException("Excel导入失败: " + e.getMessage());
        }
    }
    
    /**
     * 从任意类型的对象中获取字符串值
     * 
     * @param obj 对象
     * @return 字符串值
     */
    private static String getStringValue(Object obj) {
        if (obj == null) {
            return null;
        }
        
        if (obj instanceof ReadCellData) {
            return ((ReadCellData<?>) obj).getStringValue();
        }
        
        return obj.toString();
    }
    
    /**
     * 从任意类型的对象中获取日期值
     * 
     * @param obj 对象
     * @return 日期值
     */
    private static Date getDateValue(Object obj) {
        if (obj == null) {
            return null;
        }
        
        log.info("获取日期值，原始对象类型: {}, 值: {}", obj.getClass().getName(), obj);
        
        // 如果对象已经是日期类型
        if (obj instanceof Date) {
            log.info("对象是Date类型，直接返回: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)obj));
            return (Date) obj;
        }
        
        // 如果是ReadCellData类型（EasyExcel原始数据）
        if (obj instanceof ReadCellData) {
            ReadCellData<?> cellData = (ReadCellData<?>) obj;
            log.info("对象是ReadCellData类型，单元格类型: {}", cellData.getType());
            
            // 根据单元格类型处理
            switch (cellData.getType()) {
                case STRING:
                    String stringValue = cellData.getStringValue();
                    log.info("单元格字符串值: {}", stringValue);
                    Date parsedDate = parseDate(stringValue);
                    if (parsedDate != null) {
                        log.info("解析字符串日期成功: {}", new SimpleDateFormat("yyyy-MM-dd").format(parsedDate));
                    } else {
                        log.warn("解析字符串日期失败: {}", stringValue);
                    }
                    return parsedDate;
                case NUMBER:
                    // 尝试将数字解析为Excel日期
                    try {
                        double numberValue = cellData.getNumberValue().doubleValue();
                        log.info("单元格数字值: {}", numberValue);
                        Date dateValue = DateUtil.getJavaDate(numberValue);
                        log.info("将数字转换为日期成功: {}", new SimpleDateFormat("yyyy-MM-dd").format(dateValue));
                        return dateValue;
                    } catch (Exception e) {
                        log.warn("无法将数字转换为日期: {}, 错误: {}", cellData.getNumberValue(), e.getMessage());
                    }
                    break;
                case BOOLEAN:
                case EMPTY:
                case ERROR:
                default:
                    log.warn("单元格类型不支持转换为日期: {}", cellData.getType());
                    break;
            }
            return null;
        }
        
        // 如果是数字类型
        if (obj instanceof Number) {
            try {
                double numberValue = ((Number) obj).doubleValue();
                log.info("对象是Number类型，值: {}", numberValue);
                Date dateValue = DateUtil.getJavaDate(numberValue);
                log.info("将数字转换为日期成功: {}", new SimpleDateFormat("yyyy-MM-dd").format(dateValue));
                return dateValue;
            } catch (Exception e) {
                log.warn("无法将数字转换为日期: {}, 错误: {}", obj, e.getMessage());
            }
            return null;
        }
        
        // 尝试解析字符串
        String stringValue = obj.toString();
        log.info("尝试将对象转换为字符串解析: {}", stringValue);
        Date parsedDate = parseDate(stringValue);
        if (parsedDate != null) {
            log.info("解析字符串日期成功: {}", new SimpleDateFormat("yyyy-MM-dd").format(parsedDate));
        } else {
            log.warn("解析字符串日期失败: {}", stringValue);
        }
        return parsedDate;
    }
    
    /**
     * 专门解析星期日期格式
     * 例如: "星期一, 四月 10, 2000", "星期二, 十二月 05, 2023" 等
     */
    private static Date parseChineseWeekDateFormat(String dateStr) {
        if (dateStr == null || !dateStr.startsWith("星期")) {
            return null;
        }
        
        try {
            log.info("尝试解析中文星期日期格式: {}", dateStr);
            
            // 首先去除星期部分
            int firstCommaIndex = dateStr.indexOf(',');
            if (firstCommaIndex <= 0) {
                // 尝试查找其他可能的分隔符
                firstCommaIndex = dateStr.indexOf(' ');
                if (firstCommaIndex <= 0) {
                    log.warn("日期格式不包含有效分隔符: {}", dateStr);
                    return null;
                }
            }
            
            // 提取月日年部分，例如: "四月 10, 2000"
            String monthDayYearPart = dateStr.substring(firstCommaIndex + 1).trim();
            log.info("提取的月日年部分: {}", monthDayYearPart);
            
            // 查找月份
            int monthValue = -1;
            String matchedMonthName = null;
            
            // 首先尝试完整的中文月份名称
            for (int i = 0; i < CHINESE_MONTH_NAMES.length; i++) {
                if (monthDayYearPart.contains(CHINESE_MONTH_NAMES[i])) {
                    monthValue = i + 1; // 月份值从1开始
                    matchedMonthName = CHINESE_MONTH_NAMES[i];
                    break;
                }
            }
            
            // 如果没找到，尝试简化的月份表示（比如"四"而不是"四月"）
            if (monthValue == -1) {
                String[] simpleMonthNames = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
                for (int i = 0; i < simpleMonthNames.length; i++) {
                    if (monthDayYearPart.contains(simpleMonthNames[i])) {
                        monthValue = i + 1;
                        matchedMonthName = simpleMonthNames[i];
                        log.info("使用简化月份名称: {}", matchedMonthName);
                        break;
                    }
                }
            }
            
            if (monthValue == -1 || matchedMonthName == null) {
                log.warn("无法识别月份名称: {}", monthDayYearPart);
                return null;
            }
            
            log.info("识别到月份: {}, 对应数值: {}", matchedMonthName, monthValue);
            
            // 提取日和年
            int monthNameEndIndex = monthDayYearPart.indexOf(matchedMonthName) + matchedMonthName.length();
            String afterMonthPart = monthDayYearPart.substring(monthNameEndIndex).trim();
            log.info("月份后部分: {}", afterMonthPart);
            
            // 查找日和年 (格式应该是 "10, 2000")
            int secondCommaIndex = afterMonthPart.indexOf(',');
            if (secondCommaIndex <= 0) {
                // 尝试使用空格作为分隔符
                secondCommaIndex = afterMonthPart.indexOf(' ');
                if (secondCommaIndex <= 0) {
                    // 尝试直接解析出数字部分
                    String[] numbers = afterMonthPart.split("\\D+");
                    if (numbers.length >= 2) {
                        // 假设第一个是日，第二个是年
                        String dayPart = numbers[0];
                        String yearPart = numbers[1];
                        
                        if (dayPart.length() > 0 && yearPart.length() > 0) {
                            try {
                                int day = Integer.parseInt(dayPart);
                                int year = Integer.parseInt(yearPart);
                                
                                if (year < 100) {
                                    // 处理两位数年份
                                    year = year + (year >= 50 ? 1900 : 2000);
                                }
                                
                                // 构建日期
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String formattedDate = String.format("%04d-%02d-%02d", year, monthValue, day);
                                log.info("从数字提取的日期: {}", formattedDate);
                                
                                return sdf.parse(formattedDate);
                            } catch (Exception e) {
                                log.warn("从数字提取日期失败: {}", e.getMessage());
                            }
                        }
                    }
                    
                    log.warn("日期和年份之间缺少有效分隔符: {}", afterMonthPart);
                    return null;
                }
            }
            
            String dayPart = afterMonthPart.substring(0, secondCommaIndex).trim();
            String yearPart = afterMonthPart.substring(secondCommaIndex + 1).trim();
            
            // 清理可能的非数字字符
            dayPart = dayPart.replaceAll("\\D+", "");
            yearPart = yearPart.replaceAll("\\D+", "");
            
            // 验证日和年是否为数字
            int day, year;
            try {
                day = Integer.parseInt(dayPart);
                year = Integer.parseInt(yearPart);
                
                // 处理两位数年份
                if (year < 100) {
                    year = year + (year >= 50 ? 1900 : 2000);
                }
            } catch (NumberFormatException e) {
                log.warn("日或年不是有效数字: 日={}, 年={}", dayPart, yearPart);
                return null;
            }
            
            log.info("解析结果: 年={}, 月={}, 日={}", year, monthValue, day);
            
            // 验证日期是否有效
            if (year < 1900 || year > 2100 || monthValue < 1 || monthValue > 12 || day < 1 || day > 31) {
                log.warn("解析出的日期值超出合理范围: 年={}, 月={}, 日={}", year, monthValue, day);
                return null;
            }
            
            // 构建日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false); // 不宽松模式，会验证日期有效性
            String formattedDate = String.format("%04d-%02d-%02d", year, monthValue, day);
            log.info("格式化的日期字符串: {}", formattedDate);
            
            try {
                return sdf.parse(formattedDate);
            } catch (Exception e) {
                log.warn("日期字符串无效: {}, 错误: {}", formattedDate, e.getMessage());
                return null;
            }
        } catch (Exception e) {
            log.warn("解析中文星期日期格式失败: {}, 错误: {}", dateStr, e.getMessage());
            return null;
        }
    }
    
    /**
     * 解析日期字符串，支持多种格式
     * 
     * @param dateStr 日期字符串
     * @return 解析后的日期对象，如果无法解析则返回null
     */
    private static Date parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        
        log.info("开始解析日期字符串: {}", dateStr);
        
        // 首先尝试中文星期日期格式
        if (dateStr.startsWith("星期")) {
            Date weekDate = parseChineseWeekDateFormat(dateStr);
            if (weekDate != null) {
                log.info("成功解析中文星期日期: {}", new SimpleDateFormat("yyyy-MM-dd").format(weekDate));
                return weekDate;
            }
        }
        
        // 清理日期字符串中的非标准字符
        dateStr = dateStr.trim()
                .replace("年", "-")
                .replace("月", "-")
                .replace("日", "")
                .replace(".", "-");
        
        // 处理星期日期格式（如：星期一, 四月 10, 2000）
        if (dateStr.startsWith("星期")) {
            try {
                log.info("检测到星期日期格式: {}", dateStr);
                
                // 移除星期部分
                int commaIndex = dateStr.indexOf(',');
                if (commaIndex > 0) {
                    dateStr = dateStr.substring(commaIndex + 1).trim();
                    log.info("移除星期部分后: {}", dateStr);
                }
                
                // 检查是否包含月份名称
                String[] monthNames = {"一月", "二月", "三月", "四月", "五月", "六月", 
                                      "七月", "八月", "九月", "十月", "十一月", "十二月"};
                int[] monthValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
                
                int monthIndex = -1;
                for (int i = 0; i < monthNames.length; i++) {
                    if (dateStr.contains(monthNames[i])) {
                        monthIndex = i;
                        break;
                    }
                }
                
                if (monthIndex >= 0) {
                    // 找到了月份
                    log.info("找到月份: {}, 索引: {}", monthNames[monthIndex], monthIndex);
                    
                    // 提取日和年
                    String monthPart = monthNames[monthIndex];
                    int monthPartIndex = dateStr.indexOf(monthPart);
                    
                    // 查找月份后的数字（日）
                    String afterMonthPart = dateStr.substring(monthPartIndex + monthPart.length()).trim();
                    log.info("月份后部分: {}", afterMonthPart);
                    
                    // 查找逗号位置来分隔日和年
                    int dayYearSeparator = afterMonthPart.indexOf(',');
                    if (dayYearSeparator > 0) {
                        String dayPart = afterMonthPart.substring(0, dayYearSeparator).trim();
                        String yearPart = afterMonthPart.substring(dayYearSeparator + 1).trim();
                        
                        log.info("解析出日: {}, 年: {}", dayPart, yearPart);
                        
                        // 构建标准日期字符串：yyyy-MM-dd
                        String standardDateStr = yearPart + "-" + (monthIndex + 1) + "-" + dayPart;
                        log.info("构建标准日期字符串: {}", standardDateStr);
                        
                        try {
                            return new SimpleDateFormat("yyyy-MM-dd").parse(standardDateStr);
                        } catch (Exception e) {
                            log.warn("解析标准日期字符串失败: {}, 错误: {}", standardDateStr, e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("处理星期日期格式失败: {}, 错误: {}", dateStr, e.getMessage());
            }
        }
        
        try {
            // 尝试使用DateUtils解析多种格式
            Date date = DateUtils.parseDate(dateStr, DATE_PATTERNS);
            log.info("使用DateUtils成功解析日期: {}", new SimpleDateFormat("yyyy-MM-dd").format(date));
            return date;
        } catch (Exception e) {
            log.debug("使用DateUtils解析日期失败: {}, 尝试其他方法", dateStr);
            
            // 尝试处理数字格式的日期（Excel内部日期）
            try {
                if (dateStr.matches("\\d+(\\.\\d+)?")) {
                    double excelDate = Double.parseDouble(dateStr);
                    Date date = DateUtil.getJavaDate(excelDate);
                    log.info("将数字转换为Excel日期成功: {}", new SimpleDateFormat("yyyy-MM-dd").format(date));
                    return date;
                }
            } catch (Exception ex) {
                log.debug("无法将数值转换为日期: {}", dateStr);
            }
            
            // 尝试处理中文日期格式
            try {
                if (dateStr.contains("/")) {
                    String[] parts = dateStr.split("/");
                    if (parts.length == 3) {
                        // 尝试处理月/日/年格式
                        Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateStr);
                        log.info("解析MM/dd/yyyy格式成功: {}", new SimpleDateFormat("yyyy-MM-dd").format(date));
                        return date;
                    }
                }
            } catch (Exception ex) {
                log.debug("无法解析日期格式: {}", dateStr);
            }
            
            log.warn("所有解析方法均失败，无法解析日期: {}", dateStr);
            return null;
        }
    }
} 