package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecuritySafetyInvestmentPlan;

/**
 * 安全投入计划Service接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface ISecuritySafetyInvestmentPlanService 
{
    /**
     * 查询安全投入计划
     * 
     * @param serialNumber 安全投入计划主键
     * @return 安全投入计划
     */
    public SecuritySafetyInvestmentPlan selectSecuritySafetyInvestmentPlanBySerialNumber(Long serialNumber);

    /**
     * 查询安全投入计划列表
     * 
     * @param securitySafetyInvestmentPlan 安全投入计划
     * @return 安全投入计划集合
     */
    public List<SecuritySafetyInvestmentPlan> selectSecuritySafetyInvestmentPlanList(SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan);

    /**
     * 新增安全投入计划
     * 
     * @param securitySafetyInvestmentPlan 安全投入计划
     * @return 结果
     */
    public int insertSecuritySafetyInvestmentPlan(SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan);

    /**
     * 修改安全投入计划
     * 
     * @param securitySafetyInvestmentPlan 安全投入计划
     * @return 结果
     */
    public int updateSecuritySafetyInvestmentPlan(SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan);

    /**
     * 批量删除安全投入计划
     * 
     * @param serialNumbers 需要删除的安全投入计划主键集合
     * @return 结果
     */
    public int deleteSecuritySafetyInvestmentPlanBySerialNumbers(Long[] serialNumbers);

    /**
     * 删除安全投入计划信息
     * 
     * @param serialNumber 安全投入计划主键
     * @return 结果
     */
    public int deleteSecuritySafetyInvestmentPlanBySerialNumber(Long serialNumber);
    
    /**
     * 导入安全投入计划数据
     * 
     * @param investmentPlanList 安全投入计划数据列表
     * @param updateSupport 是否支持更新
     * @param operName 操作用户
     * @return 结果
     */
    public String importInvestmentPlan(List<SecuritySafetyInvestmentPlan> investmentPlanList, boolean updateSupport, String operName);
}
