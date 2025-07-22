package com.ruoyi.security.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.security.domain.SecurityEmergencyDrillPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 应急演练计划Excel导入工具类
 * 专门处理合并单元格的情况
 * 
 * @author wang
 */
public class EmergencyDrillPlanExcelUtil {
    
    private static final Logger log = LoggerFactory.getLogger(EmergencyDrillPlanExcelUtil.class);
    
    /**
     * 读取Excel文件并处理合并单元格
     * 
     * @param inputStream Excel文件输入流
     * @return 解析后的数据列表
     */
    public static List<SecurityEmergencyDrillPlan> readExcelWithMergedCells(InputStream inputStream) {
        List<SecurityEmergencyDrillPlan> dataList = new ArrayList<>();
        
        EasyExcel.read(inputStream, SecurityEmergencyDrillPlan.class, new MergedCellAnalysisEventListener(dataList))
                .extraRead(CellExtraTypeEnum.MERGE) // 读取合并单元格信息
                .sheet()
                .doRead();
        
        return dataList;
    }
    
    /**
     * 合并单元格分析事件监听器
     */
    private static class MergedCellAnalysisEventListener extends AnalysisEventListener<SecurityEmergencyDrillPlan> {
        
        private List<SecurityEmergencyDrillPlan> dataList;
        
        /**
         * 存储合并单元格信息
         * key: "firstRowIndex,firstColumnIndex,lastRowIndex,lastColumnIndex"
         * value: 合并单元格的值
         */
        private Map<String, Object> mergedRegionMap = new HashMap<>();
        
        /**
         * 存储每行每列的数据，用于处理合并单元格
         */
        private Map<Integer, Map<Integer, Object>> cellDataMap = new HashMap<>();
        
        public MergedCellAnalysisEventListener(List<SecurityEmergencyDrillPlan> dataList) {
            this.dataList = dataList;
        }
        
        @Override
        public void invoke(SecurityEmergencyDrillPlan data, AnalysisContext context) {
            Integer currentRowIndex = context.readRowHolder().getRowIndex();
            log.info("解析到第{}行数据: {}", currentRowIndex, data);
            
            // 处理合并单元格数据
            processMergedCellData(data, currentRowIndex);
            
            // 验证必要字段
            if (isValidData(data)) {
                dataList.add(data);
            } else {
                log.warn("第{}行数据不完整，跳过: {}", currentRowIndex, data);
            }
        }
        
        @Override
        public void extra(CellExtra extra, AnalysisContext context) {
            if (extra.getType() == CellExtraTypeEnum.MERGE) {
                String key = extra.getFirstRowIndex() + "," + extra.getFirstColumnIndex() + "," + 
                           extra.getLastRowIndex() + "," + extra.getLastColumnIndex();
                mergedRegionMap.put(key, extra.getText());
                log.info("发现合并单元格: 行{}-{}, 列{}-{}, 值: {}", 
                        extra.getFirstRowIndex(), extra.getLastRowIndex(),
                        extra.getFirstColumnIndex(), extra.getLastColumnIndex(), 
                        extra.getText());
            }
        }
        
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            log.info("Excel解析完成，共解析{}条有效数据", dataList.size());
        }
        
        /**
         * 处理合并单元格数据
         */
        private void processMergedCellData(SecurityEmergencyDrillPlan data, Integer currentRowIndex) {
            // 存储当前行的原始数据
            Map<Integer, Object> currentRowData = new HashMap<>();
            currentRowData.put(0, data.getLevel());
            currentRowData.put(1, data.getDepartmentTeam());
            currentRowData.put(2, data.getDrillType());
            currentRowData.put(3, data.getDrillTheme());
            currentRowData.put(4, data.getReferenceScenario());
            currentRowData.put(5, data.getParticipantsObservers());
            currentRowData.put(6, data.getDrillCategory());
            currentRowData.put(7, data.getStatu());
            
            cellDataMap.put(currentRowIndex, currentRowData);
            
            // 处理合并单元格：如果当前单元格为空，尝试从合并单元格或上一行获取数据
            fillMergedCellValue(data, currentRowIndex, 0, data.getLevel(), 
                    (value) -> data.setLevel(String.valueOf(value)));
            fillMergedCellValue(data, currentRowIndex, 1, data.getDepartmentTeam(), 
                    (value) -> data.setDepartmentTeam(String.valueOf(value)));
            fillMergedCellValue(data, currentRowIndex, 2, data.getDrillType(), 
                    (value) -> data.setDrillType(String.valueOf(value)));
            fillMergedCellValue(data, currentRowIndex, 3, data.getDrillTheme(), 
                    (value) -> data.setDrillTheme(String.valueOf(value)));
            fillMergedCellValue(data, currentRowIndex, 4, data.getReferenceScenario(), 
                    (value) -> data.setReferenceScenario(String.valueOf(value)));
            fillMergedCellValue(data, currentRowIndex, 5, data.getParticipantsObservers(), 
                    (value) -> data.setParticipantsObservers(String.valueOf(value)));
            fillMergedCellValue(data, currentRowIndex, 6, data.getDrillCategory(), 
                    (value) -> data.setDrillCategory(String.valueOf(value)));
            fillMergedCellValue(data, currentRowIndex, 7, data.getStatu(), 
                    (value) -> data.setStatu(String.valueOf(value)));
        }
        
        /**
         * 填充合并单元格的值
         */
        private void fillMergedCellValue(SecurityEmergencyDrillPlan data, Integer currentRowIndex, 
                Integer columnIndex, Object currentValue, ValueSetter setter) {
            if (isEmpty(currentValue)) {
                // 首先尝试从合并单元格获取值
                Object mergedValue = getMergedCellValue(currentRowIndex, columnIndex);
                if (!isEmpty(mergedValue)) {
                    setter.setValue(mergedValue);
                    return;
                }
                
                // 如果合并单元格没有值，尝试从上一行获取
                Object previousValue = getPreviousNonEmptyValue(currentRowIndex - 1, columnIndex);
                if (!isEmpty(previousValue)) {
                    setter.setValue(previousValue);
                }
            }
        }
        
        /**
         * 获取合并单元格的值
         */
        private Object getMergedCellValue(Integer rowIndex, Integer columnIndex) {
            for (Map.Entry<String, Object> entry : mergedRegionMap.entrySet()) {
                String[] parts = entry.getKey().split(",");
                int firstRow = Integer.parseInt(parts[0]);
                int firstCol = Integer.parseInt(parts[1]);
                int lastRow = Integer.parseInt(parts[2]);
                int lastCol = Integer.parseInt(parts[3]);
                
                if (rowIndex >= firstRow && rowIndex <= lastRow && 
                    columnIndex >= firstCol && columnIndex <= lastCol) {
                    return entry.getValue();
                }
            }
            return null;
        }
        
        /**
         * 获取前面行中非空的值
         */
        private Object getPreviousNonEmptyValue(Integer rowIndex, Integer columnIndex) {
            for (int i = rowIndex; i >= 1; i--) {
                Map<Integer, Object> rowData = cellDataMap.get(i);
                if (rowData != null) {
                    Object value = rowData.get(columnIndex);
                    if (!isEmpty(value)) {
                        return value;
                    }
                }
            }
            return null;
        }
        
        /**
         * 判断值是否为空
         */
        private boolean isEmpty(Object value) {
            return value == null || StringUtils.isEmpty(String.valueOf(value).trim());
        }
        
        /**
         * 验证数据是否有效
         */
        private boolean isValidData(SecurityEmergencyDrillPlan data) {
            return StringUtils.isNotEmpty(data.getLevel()) || 
                   StringUtils.isNotEmpty(data.getDepartmentTeam()) ||
                   StringUtils.isNotEmpty(data.getDrillTheme());
        }
        
        /**
         * 值设置器接口
         */
        @FunctionalInterface
        private interface ValueSetter {
            void setValue(Object value);
        }
    }
}
