package com.ruoyi.security.utils;

import java.util.List;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.security.domain.SecurityEmergencyDrillPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 合并单元格数据处理器
 * 用于处理Excel导入后的数据，填充合并单元格的空值
 * 
 * @author wang
 */
public class MergedCellProcessor {
    
    private static final Logger log = LoggerFactory.getLogger(MergedCellProcessor.class);
    
    /**
     * 处理应急演练计划数据中的合并单元格
     * 
     * @param dataList 原始数据列表
     * @return 处理后的数据列表
     */
    public static List<SecurityEmergencyDrillPlan> processEmergencyDrillPlanMergedCells(List<SecurityEmergencyDrillPlan> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return dataList;
        }
        
        log.info("开始处理应急演练计划合并单元格数据，共{}条记录", dataList.size());
        
        // 用于存储上一行的数据，用于填充合并单元格
        String lastLevel = null;
        String lastDepartmentTeam = null;
        String lastDrillType = null;
        String lastPlannedDrillTime = null;
        String lastDrillTheme = null;
        String lastReferenceScenario = null;
        String lastParticipantsObservers = null;
        String lastDrillCategory = null;
        String lastStatu = null;
        
        for (int i = 0; i < dataList.size(); i++) {
            SecurityEmergencyDrillPlan current = dataList.get(i);
            
            // 处理级别字段
            if (StringUtils.isEmpty(current.getLevel())) {
                if (StringUtils.isNotEmpty(lastLevel)) {
                    current.setLevel(lastLevel);
                    log.debug("第{}行级别字段为空，使用上一行值: {}", i + 1, lastLevel);
                }
            } else {
                lastLevel = current.getLevel();
            }
            
            // 处理科室/班组字段
            if (StringUtils.isEmpty(current.getDepartmentTeam())) {
                if (StringUtils.isNotEmpty(lastDepartmentTeam)) {
                    current.setDepartmentTeam(lastDepartmentTeam);
                    log.debug("第{}行科室/班组字段为空，使用上一行值: {}", i + 1, lastDepartmentTeam);
                }
            } else {
                lastDepartmentTeam = current.getDepartmentTeam();
            }
            
            // 处理类型字段
            if (StringUtils.isEmpty(current.getDrillType())) {
                if (StringUtils.isNotEmpty(lastDrillType)) {
                    current.setDrillType(lastDrillType);
                    log.debug("第{}行类型字段为空，使用上一行值: {}", i + 1, lastDrillType);
                }
            } else {
                lastDrillType = current.getDrillType();
            }

            // 处理预计演练时间字段
            if (StringUtils.isEmpty(current.getPlannedDrillTime())) {
                if (StringUtils.isNotEmpty(lastPlannedDrillTime)) {
                    current.setPlannedDrillTime(lastPlannedDrillTime);
                    log.debug("第{}行预计演练时间字段为空，使用上一行值: {}", i + 1, lastPlannedDrillTime);
                }
            } else {
                lastPlannedDrillTime = current.getPlannedDrillTime();
            }
            
            // 处理演练主题字段
            if (StringUtils.isEmpty(current.getDrillTheme())) {
                if (StringUtils.isNotEmpty(lastDrillTheme)) {
                    current.setDrillTheme(lastDrillTheme);
                    log.debug("第{}行演练主题字段为空，使用上一行值: {}", i + 1, lastDrillTheme);
                }
            } else {
                lastDrillTheme = current.getDrillTheme();
            }
            
            // 处理参考情景字段
            if (StringUtils.isEmpty(current.getReferenceScenario())) {
                if (StringUtils.isNotEmpty(lastReferenceScenario)) {
                    current.setReferenceScenario(lastReferenceScenario);
                    log.debug("第{}行参考情景字段为空，使用上一行值: {}", i + 1, lastReferenceScenario);
                }
            } else {
                lastReferenceScenario = current.getReferenceScenario();
            }
            
            // 处理参演及观摩人员字段
            if (StringUtils.isEmpty(current.getParticipantsObservers())) {
                if (StringUtils.isNotEmpty(lastParticipantsObservers)) {
                    current.setParticipantsObservers(lastParticipantsObservers);
                    log.debug("第{}行参演及观摩人员字段为空，使用上一行值: {}", i + 1, lastParticipantsObservers);
                }
            } else {
                lastParticipantsObservers = current.getParticipantsObservers();
            }
            
            // 处理类型字段（drill_category）
            if (StringUtils.isEmpty(current.getDrillCategory())) {
                if (StringUtils.isNotEmpty(lastDrillCategory)) {
                    current.setDrillCategory(lastDrillCategory);
                    log.debug("第{}行类型字段为空，使用上一行值: {}", i + 1, lastDrillCategory);
                }
            } else {
                lastDrillCategory = current.getDrillCategory();
            }
            
            // 处理审批状态字段
            if (StringUtils.isEmpty(current.getStatu())) {
                if (StringUtils.isNotEmpty(lastStatu)) {
                    current.setStatu(lastStatu);
                    log.debug("第{}行审批状态字段为空，使用上一行值: {}", i + 1, lastStatu);
                }
            } else {
                lastStatu = current.getStatu();
            }
        }
        
        log.info("应急演练计划合并单元格数据处理完成");
        return dataList;
    }
    
    /**
     * 验证数据是否有效
     * 
     * @param data 数据对象
     * @return 是否有效
     */
    public static boolean isValidEmergencyDrillPlanData(SecurityEmergencyDrillPlan data) {
        // 至少需要有级别或科室/班组或演练主题中的一个
        return StringUtils.isNotEmpty(data.getLevel()) || 
               StringUtils.isNotEmpty(data.getDepartmentTeam()) ||
               StringUtils.isNotEmpty(data.getDrillTheme());
    }
    
    /**
     * 过滤无效数据
     * 
     * @param dataList 原始数据列表
     * @return 过滤后的数据列表
     */
    public static List<SecurityEmergencyDrillPlan> filterValidData(List<SecurityEmergencyDrillPlan> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return dataList;
        }
        
        return dataList.stream()
                .filter(MergedCellProcessor::isValidEmergencyDrillPlanData)
                .collect(java.util.stream.Collectors.toList());
    }
}
