package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySafetyInvestmentPlanMapper;
import com.ruoyi.security.domain.SecuritySafetyInvestmentPlan;
import com.ruoyi.security.service.ISecuritySafetyInvestmentPlanService;

/**
 * 安全投入计划Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecuritySafetyInvestmentPlanServiceImpl implements ISecuritySafetyInvestmentPlanService 
{
    @Autowired
    private SecuritySafetyInvestmentPlanMapper securitySafetyInvestmentPlanMapper;

    /**
     * 查询安全投入计划
     * 
     * @param serialNumber 安全投入计划主键
     * @return 安全投入计划
     */
    @Override
    public SecuritySafetyInvestmentPlan selectSecuritySafetyInvestmentPlanBySerialNumber(Long serialNumber)
    {
        return securitySafetyInvestmentPlanMapper.selectSecuritySafetyInvestmentPlanBySerialNumber(serialNumber);
    }

    /**
     * 查询安全投入计划列表
     * 
     * @param securitySafetyInvestmentPlan 安全投入计划
     * @return 安全投入计划
     */
    @Override
    public List<SecuritySafetyInvestmentPlan> selectSecuritySafetyInvestmentPlanList(SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan)
    {
        return securitySafetyInvestmentPlanMapper.selectSecuritySafetyInvestmentPlanList(securitySafetyInvestmentPlan);
    }

    /**
     * 新增安全投入计划
     * 
     * @param securitySafetyInvestmentPlan 安全投入计划
     * @return 结果
     */
    @Override
    public int insertSecuritySafetyInvestmentPlan(SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan)
    {
        return securitySafetyInvestmentPlanMapper.insertSecuritySafetyInvestmentPlan(securitySafetyInvestmentPlan);
    }

    /**
     * 修改安全投入计划
     * 
     * @param securitySafetyInvestmentPlan 安全投入计划
     * @return 结果
     */
    @Override
    public int updateSecuritySafetyInvestmentPlan(SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan)
    {
        return securitySafetyInvestmentPlanMapper.updateSecuritySafetyInvestmentPlan(securitySafetyInvestmentPlan);
    }

    /**
     * 批量删除安全投入计划
     * 
     * @param serialNumbers 需要删除的安全投入计划主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySafetyInvestmentPlanBySerialNumbers(Long[] serialNumbers)
    {
        return securitySafetyInvestmentPlanMapper.deleteSecuritySafetyInvestmentPlanBySerialNumbers(serialNumbers);
    }

    /**
     * 删除安全投入计划信息
     * 
     * @param serialNumber 安全投入计划主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySafetyInvestmentPlanBySerialNumber(Long serialNumber)
    {
        return securitySafetyInvestmentPlanMapper.deleteSecuritySafetyInvestmentPlanBySerialNumber(serialNumber);
    }
}
