package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecuritySafetyInvestmentPlan;

/**
 * 安全投入计划Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecuritySafetyInvestmentPlanMapper 
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
     * 删除安全投入计划
     * 
     * @param serialNumber 安全投入计划主键
     * @return 结果
     */
    public int deleteSecuritySafetyInvestmentPlanBySerialNumber(Long serialNumber);

    /**
     * 批量删除安全投入计划
     * 
     * @param serialNumbers 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecuritySafetyInvestmentPlanBySerialNumbers(Long[] serialNumbers);
}
