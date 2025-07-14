package com.ruoyi.common.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * EasyExcel日期格式转换器
 * 
 * @author ruoyi
 */
public class DateStringConverter implements Converter<Date> {
    
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    
    @Override
    public Class<Date> supportJavaTypeKey() {
        return Date.class;
    }
    
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }
    
    @Override
    public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                  GlobalConfiguration globalConfiguration) {
        String dateString = cellData.getStringValue();
        try {
            if (dateString != null && !dateString.trim().isEmpty()) {
                String pattern = DEFAULT_PATTERN;
                if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
                    pattern = contentProperty.getDateTimeFormatProperty().getFormat();
                }
                
                // 对常见日期格式进行适配
                if (dateString.contains(".")) {
                    // 处理 2023.7.1 格式
                    String[] parts = dateString.split("\\.");
                    if (parts.length == 3) {
                        String year = parts[0];
                        String month = parts[1].length() == 1 ? "0" + parts[1] : parts[1];
                        String day = parts[2].length() == 1 ? "0" + parts[2] : parts[2];
                        dateString = year + "-" + month + "-" + day;
                    }
                }
                
                return new SimpleDateFormat(pattern).parse(dateString);
            }
        } catch (Exception e) {
            // 尝试其他格式
            try {
                // 尝试使用Excel数字日期格式
                double numericValue = Double.parseDouble(dateString);
                return org.apache.poi.ss.usermodel.DateUtil.getJavaDate(numericValue);
            } catch (Exception ex) {
                // 忽略
            }
        }
        return null;
    }
    
    @Override
    public WriteCellData<?> convertToExcelData(Date value, ExcelContentProperty contentProperty,
                                              GlobalConfiguration globalConfiguration) {
        if (value != null) {
            String pattern = DEFAULT_PATTERN;
            if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
                pattern = contentProperty.getDateTimeFormatProperty().getFormat();
            }
            String formattedDate = new SimpleDateFormat(pattern).format(value);
            return new WriteCellData<>(formattedDate);
        }
        return new WriteCellData<>("");
    }
} 