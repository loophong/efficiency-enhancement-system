package com.ruoyi.security.service.impl;

import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.security.mapper.SecurityEducationAnnualTrainingPlanMapper;
import com.ruoyi.security.domain.SecurityEducationAnnualTrainingPlan;
import com.ruoyi.security.service.ISecurityEducationAnnualTrainingPlanService;

/**
 * 年度培训计划Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Service
public class SecurityEducationAnnualTrainingPlanServiceImpl implements ISecurityEducationAnnualTrainingPlanService 
{
    private static final Logger log = LoggerFactory.getLogger(SecurityEducationAnnualTrainingPlanServiceImpl.class);
    
    @Autowired
    private SecurityEducationAnnualTrainingPlanMapper securityEducationAnnualTrainingPlanMapper;

    /**
     * 查询年度培训计划
     * 
     * @param id 年度培训计划主键
     * @return 年度培训计划
     */
    @Override
    public SecurityEducationAnnualTrainingPlan selectSecurityEducationAnnualTrainingPlanById(Long id)
    {
        return securityEducationAnnualTrainingPlanMapper.selectSecurityEducationAnnualTrainingPlanById(id);
    }

    /**
     * 查询年度培训计划列表
     * 
     * @param securityEducationAnnualTrainingPlan 年度培训计划
     * @return 年度培训计划
     */
    @Override
    public List<SecurityEducationAnnualTrainingPlan> selectSecurityEducationAnnualTrainingPlanList(SecurityEducationAnnualTrainingPlan securityEducationAnnualTrainingPlan)
    {
        return securityEducationAnnualTrainingPlanMapper.selectSecurityEducationAnnualTrainingPlanList(securityEducationAnnualTrainingPlan);
    }

    /**
     * 新增年度培训计划
     * 
     * @param securityEducationAnnualTrainingPlan 年度培训计划
     * @return 结果
     */
    @Override
    public int insertSecurityEducationAnnualTrainingPlan(SecurityEducationAnnualTrainingPlan securityEducationAnnualTrainingPlan)
    {
        return securityEducationAnnualTrainingPlanMapper.insertSecurityEducationAnnualTrainingPlan(securityEducationAnnualTrainingPlan);
    }

    /**
     * 修改年度培训计划
     * 
     * @param securityEducationAnnualTrainingPlan 年度培训计划
     * @return 结果
     */
    @Override
    public int updateSecurityEducationAnnualTrainingPlan(SecurityEducationAnnualTrainingPlan securityEducationAnnualTrainingPlan)
    {
        return securityEducationAnnualTrainingPlanMapper.updateSecurityEducationAnnualTrainingPlan(securityEducationAnnualTrainingPlan);
    }

    /**
     * 批量删除年度培训计划
     * 
     * @param ids 需要删除的年度培训计划主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEducationAnnualTrainingPlanByIds(Long[] ids)
    {
        return securityEducationAnnualTrainingPlanMapper.deleteSecurityEducationAnnualTrainingPlanByIds(ids);
    }

    /**
     * 删除年度培训计划信息
     * 
     * @param id 年度培训计划主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEducationAnnualTrainingPlanById(Long id)
    {
        return securityEducationAnnualTrainingPlanMapper.deleteSecurityEducationAnnualTrainingPlanById(id);
    }
    
    /**
     * 导入年度培训计划数据
     * 
     * @param is 输入流
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    @Override
    public String importData(InputStream is, Boolean updateSupport)
    {
        try {
            ExcelUtil<SecurityEducationAnnualTrainingPlan> util = new ExcelUtil<>(SecurityEducationAnnualTrainingPlan.class);
            List<SecurityEducationAnnualTrainingPlan> planList = util.importExcel(is);
            
            if (StringUtils.isNull(planList) || planList.isEmpty()) {
                throw new ServiceException("导入年度培训计划数据不能为空！");
            }
            
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            
            // 处理合并单元格情况
            handleMergedCells(planList);
            
            for (SecurityEducationAnnualTrainingPlan plan : planList) {
                try {
                    // 验证必填字段
                    if (StringUtils.isEmpty(plan.getTrainees())) {
                        failureNum++;
                        failureMsg.append("<br/>第 ").append(failureNum).append(" 条数据导入失败：培训对象不能为空");
                        continue;
                    }
                    
                    // 检查是否存在相同记录
                    SecurityEducationAnnualTrainingPlan existPlan = new SecurityEducationAnnualTrainingPlan();
                    existPlan.setTrainingProject1(plan.getTrainingProject1());
                    existPlan.setTrainees(plan.getTrainees());
                    existPlan.setTrainingMonth(plan.getTrainingMonth());
                    List<SecurityEducationAnnualTrainingPlan> existingPlans = securityEducationAnnualTrainingPlanMapper.selectSecurityEducationAnnualTrainingPlanList(existPlan);
                    
                    if (!existingPlans.isEmpty() && updateSupport) {
                        // 更新已有数据
                        plan.setId(existingPlans.get(0).getId());
                        securityEducationAnnualTrainingPlanMapper.updateSecurityEducationAnnualTrainingPlan(plan);
                        successNum++;
                        successMsg.append("<br/>第 ").append(successNum).append(" 条数据更新成功");
                    } else if (existingPlans.isEmpty()) {
                        // 插入新数据
                        securityEducationAnnualTrainingPlanMapper.insertSecurityEducationAnnualTrainingPlan(plan);
                        successNum++;
                        successMsg.append("<br/>第 ").append(successNum).append(" 条数据导入成功");
                    } else {
                        // 数据已存在但不更新
                        failureNum++;
                        failureMsg.append("<br/>第 ").append(failureNum).append(" 条数据已存在，未更新");
                    }
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>第 " + failureNum + " 条数据导入失败：";
                    failureMsg.append(msg).append(e.getMessage());
                    log.error(msg, e);
                }
            }
            
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                throw new ServiceException(failureMsg.toString());
            } else {
                successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            }
            
            return successMsg.toString();
        } catch (Exception e) {
            log.error("导入Excel数据失败：", e);
            throw new ServiceException("导入Excel数据失败：" + e.getMessage());
        }
    }
    
    /**
     * 处理合并单元格的情况
     * 
     * @param planList 计划列表
     */
    private void handleMergedCells(List<SecurityEducationAnnualTrainingPlan> planList) {
        if (planList == null || planList.isEmpty()) {
            return;
        }
        
        log.info("开始处理合并单元格情况，共 {} 条记录", planList.size());
        String lastTrainingProject1 = null;
        
        for (int i = 0; i < planList.size(); i++) {
            SecurityEducationAnnualTrainingPlan currentPlan = planList.get(i);
            
            // 1. 处理培训项目1为空的情况，向上获取值
            if (StringUtils.isEmpty(currentPlan.getTrainingProject1()) && lastTrainingProject1 != null) {
                log.info("第 {} 行的培训项目1为空，使用上一行的值: {}", i + 1, lastTrainingProject1);
                currentPlan.setTrainingProject1(lastTrainingProject1);
            }
            
            // 记录当前的培训项目1值，用于下一行
            if (StringUtils.isNotEmpty(currentPlan.getTrainingProject1())) {
                lastTrainingProject1 = currentPlan.getTrainingProject1();
            }
            
            // 2. 处理其他字段为空的情况，向左获取值
            fillEmptyFieldsFromLeft(currentPlan);
        }
        
        log.info("合并单元格处理完成");
    }
    
    /**
     * 填充空字段，从左侧获取值
     * 
     * @param plan 培训计划对象
     */
    private void fillEmptyFieldsFromLeft(SecurityEducationAnnualTrainingPlan plan) {
        // 按照从左到右的顺序填充空字段，确保每个字段使用最近的左侧非空值
        
        // 确保培训项目1有值（这应该在前面的步骤已经处理过了）
        String lastNonEmptyValue = plan.getTrainingProject1();
        
        // 培训项目2为空时，使用最近的左侧非空值（培训项目1）
        if (StringUtils.isEmpty(plan.getTrainingProject2())) {
            if (StringUtils.isNotEmpty(lastNonEmptyValue)) {
                log.info("培训项目2为空，使用最近左侧值: {}", lastNonEmptyValue);
                plan.setTrainingProject2(lastNonEmptyValue);
            }
        }
        
        // 更新最近的非空值
        if (StringUtils.isNotEmpty(plan.getTrainingProject2())) {
            lastNonEmptyValue = plan.getTrainingProject2();
        }
        
        // 培训对象为空时，使用最近的左侧非空值
        if (StringUtils.isEmpty(plan.getTrainees())) {
            if (StringUtils.isNotEmpty(lastNonEmptyValue)) {
                log.info("培训对象为空，使用最近左侧值: {}", lastNonEmptyValue);
                plan.setTrainees(lastNonEmptyValue);
            }
        }
        
        // 更新最近的非空值
        if (StringUtils.isNotEmpty(plan.getTrainees())) {
            lastNonEmptyValue = plan.getTrainees();
        }
        
        // 培训类别/内容可能比较特殊，通常不会使用其他字段的值，但仍然记录最近的非空值
        if (StringUtils.isNotEmpty(plan.getTrainingCategoryContent())) {
            lastNonEmptyValue = plan.getTrainingCategoryContent();
        }
        
        // 培训方式为空时，使用最近的左侧非空值
        if (StringUtils.isEmpty(plan.getTrainingMethod())) {
            if (StringUtils.isNotEmpty(lastNonEmptyValue)) {
                log.info("培训方式为空，使用最近左侧值: {}", lastNonEmptyValue);
                plan.setTrainingMethod(lastNonEmptyValue);
            } else {
                // 如果前面所有字段都为空，设置默认值
                plan.setTrainingMethod("授课、观摩");
                log.info("培训方式为空且左侧无值，设置默认值: 授课、观摩");
            }
        }
        
        // 更新最近的非空值
        if (StringUtils.isNotEmpty(plan.getTrainingMethod())) {
            lastNonEmptyValue = plan.getTrainingMethod();
        }
        
        // 课时为空时，尝试使用左侧值或设置默认值
        if (plan.getHours() == null) {
            try {
                // 尝试将左侧值转换为数字
                if (StringUtils.isNotEmpty(lastNonEmptyValue) && lastNonEmptyValue.matches("\\d+")) {
                    Long hours = Long.parseLong(lastNonEmptyValue);
                    log.info("课时为空，使用左侧数值: {}", hours);
                    plan.setHours(hours);
                } else {
                    // 无法转换为数字，使用默认值
                    log.info("课时为空，设置默认值: 8");
                    plan.setHours(8L);
                }
            } catch (Exception e) {
                log.info("课时转换异常，设置默认值: 8");
                plan.setHours(8L);
            }
        }
        
        // 更新最近的非空值（将hours转为字符串）
        if (plan.getHours() != null) {
            lastNonEmptyValue = plan.getHours().toString();
        }
        
        // 培训月份为空时，使用最近的左侧非空值
        if (StringUtils.isEmpty(plan.getTrainingMonth())) {
            if (StringUtils.isNotEmpty(lastNonEmptyValue)) {
                log.info("培训月份为空，使用最近左侧值: {}", lastNonEmptyValue);
                plan.setTrainingMonth(lastNonEmptyValue);
            } else {
                // 如果前面所有字段都为空，设置默认值
                plan.setTrainingMonth("8");
                log.info("培训月份为空且左侧无值，设置默认值: 8");
            }
        }
        
        // 更新最近的非空值
        if (StringUtils.isNotEmpty(plan.getTrainingMonth())) {
            lastNonEmptyValue = plan.getTrainingMonth();
        }
        
        // 考核方式为空时，使用最近的左侧非空值或设置默认值
        if (StringUtils.isEmpty(plan.getAssessmentMethod())) {
            if (StringUtils.isNotEmpty(lastNonEmptyValue)) {
                log.info("考核方式为空，使用最近左侧值: {}", lastNonEmptyValue);
                plan.setAssessmentMethod(lastNonEmptyValue);
            } else {
                // 如果前面所有字段都为空，设置默认值
                log.info("考核方式为空且左侧无值，设置默认值: 考试");
                plan.setAssessmentMethod("考试");
            }
        }
        
        // 备注字段通常不需要自动填充
    }
    
    /**
     * 获取第一个非空值
     * 
     * @param values 值列表
     * @return 第一个非空值，如果都为空则返回null
     */
    private String getFirstNonEmptyValue(String... values) {
        if (values == null) {
            return null;
        }
        
        for (String value : values) {
            if (StringUtils.isNotEmpty(value)) {
                return value;
            }
        }
        
        return null;
    }
    
    /**
     * 更新最近导入记录的关联ID
     * 
     * @param fileManagementId 文件管理ID
     * @return 更新的记录数
     */
    @Override
    public int updateLatestImportedRelatedId(Long fileManagementId) {
        if (fileManagementId == null) {
            log.warn("更新最近导入年度培训计划关联ID失败：fileManagementId为空");
            return 0;
        }
        log.info("更新最近导入年度培训计划关联ID: {}", fileManagementId);
        return securityEducationAnnualTrainingPlanMapper.updateLatestImportedRelatedId(fileManagementId);
    }
}
