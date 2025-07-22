package com.ruoyi.security.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.security.domain.SecurityEmergencyDrillPlan;
import com.ruoyi.security.service.ISecurityEmergencyDrillPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 应急演练计划Excel导入监听器
 * 支持处理合并单元格
 * 
 * @author wang
 */
public class SecurityEmergencyDrillPlanListener extends AnalysisEventListener<SecurityEmergencyDrillPlan> {
    
    private static final Logger log = LoggerFactory.getLogger(SecurityEmergencyDrillPlanListener.class);
    
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    
    private List<SecurityEmergencyDrillPlan> cachedDataList = new ArrayList<>();
    
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private ISecurityEmergencyDrillPlanService emergencyDrillPlanService;
    
    /**
     * 存储合并单元格信息
     */
    private Map<String, String> mergedRegionMap = new HashMap<>();
    
    /**
     * 存储每行的原始数据，用于处理合并单元格
     */
    private Map<Integer, Map<Integer, String>> rowDataMap = new HashMap<>();
    
    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param emergencyDrillPlanService
     */
    public SecurityEmergencyDrillPlanListener(ISecurityEmergencyDrillPlanService emergencyDrillPlanService) {
        this.emergencyDrillPlanService = emergencyDrillPlanService;
    }
    
    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(SecurityEmergencyDrillPlan data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);
        
        // 获取当前行号
        Integer currentRowIndex = context.readRowHolder().getRowIndex();
        
        // 处理合并单元格数据
        processMergedCells(data, currentRowIndex);
        
        cachedDataList.add(data);
        
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList.clear();
        }
    }
    
    /**
     * 处理合并单元格数据
     */
    private void processMergedCells(SecurityEmergencyDrillPlan data, Integer currentRowIndex) {
        // 存储当前行的数据
        Map<Integer, String> currentRowData = new HashMap<>();
        currentRowData.put(0, data.getLevel());
        currentRowData.put(1, data.getDepartmentTeam());
        currentRowData.put(2, data.getDrillType());
        currentRowData.put(3, data.getDrillTheme());
        // 可以根据需要添加更多字段
        
        rowDataMap.put(currentRowIndex, currentRowData);
        
        // 处理合并单元格：如果当前单元格为空，尝试从上一行获取数据
        if (StringUtils.isEmpty(data.getLevel()) && currentRowIndex > 1) {
            String previousLevel = getPreviousNonEmptyValue(currentRowIndex - 1, 0);
            if (StringUtils.isNotEmpty(previousLevel)) {
                data.setLevel(previousLevel);
            }
        }
        
        if (StringUtils.isEmpty(data.getDepartmentTeam()) && currentRowIndex > 1) {
            String previousDepartment = getPreviousNonEmptyValue(currentRowIndex - 1, 1);
            if (StringUtils.isNotEmpty(previousDepartment)) {
                data.setDepartmentTeam(previousDepartment);
            }
        }
        
        if (StringUtils.isEmpty(data.getDrillType()) && currentRowIndex > 1) {
            String previousType = getPreviousNonEmptyValue(currentRowIndex - 1, 2);
            if (StringUtils.isNotEmpty(previousType)) {
                data.setDrillType(previousType);
            }
        }
    }
    
    /**
     * 获取前面行中非空的值
     */
    private String getPreviousNonEmptyValue(Integer rowIndex, Integer columnIndex) {
        for (int i = rowIndex; i >= 1; i--) {
            Map<Integer, String> rowData = rowDataMap.get(i);
            if (rowData != null) {
                String value = rowData.get(columnIndex);
                if (StringUtils.isNotEmpty(value)) {
                    return value;
                }
            }
        }
        return null;
    }
    
    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }
    
    /**
     * 额外信息（批注、超链接、合并单元格信息读取）
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("读取到了一条额外信息:{}", extra);
        switch (extra.getType()) {
            case COMMENT:
                log.info("额外信息是批注,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(), extra.getText());
                break;
            case HYPERLINK:
                log.info("额外信息是超链接,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(), extra.getText());
                break;
            case MERGE:
                log.info("额外信息是合并单元格,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(), extra.getText());
                // 存储合并单元格信息
                String key = extra.getFirstRowIndex() + "," + extra.getFirstColumnIndex() + "," + 
                           extra.getLastRowIndex() + "," + extra.getLastColumnIndex();
                mergedRegionMap.put(key, extra.getText());
                break;
            default:
        }
    }
    
    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof NumberFormatException) {
            log.error("第{}行解析异常，数据为:{}", context.readRowHolder().getRowIndex(), exception.getMessage());
        }
    }
    
    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", headMap);
    }
    
    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        try {
            if (!cachedDataList.isEmpty()) {
                emergencyDrillPlanService.importSecurityEmergencyDrillPlan(cachedDataList, true, "system");
            }
        } catch (Exception e) {
            log.error("存储数据库失败", e);
            throw new ServiceException("导入数据失败：" + e.getMessage());
        }
        log.info("存储数据库成功！");
    }
}
