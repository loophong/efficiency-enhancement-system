package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySafetyWorkPlanMapper;
import com.ruoyi.security.domain.SecuritySafetyWorkPlan;
import com.ruoyi.security.service.ISecuritySafetyWorkPlanService;

/**
 * 中长短期安全工作计划Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecuritySafetyWorkPlanServiceImpl implements ISecuritySafetyWorkPlanService 
{
    @Autowired
    private SecuritySafetyWorkPlanMapper securitySafetyWorkPlanMapper;

    /**
     * 查询中长短期安全工作计划
     * 
     * @param id 中长短期安全工作计划主键
     * @return 中长短期安全工作计划
     */
    @Override
    public SecuritySafetyWorkPlan selectSecuritySafetyWorkPlanById(String id)
    {
        return securitySafetyWorkPlanMapper.selectSecuritySafetyWorkPlanById(id);
    }

    /**
     * 查询中长短期安全工作计划列表
     * 
     * @param securitySafetyWorkPlan 中长短期安全工作计划
     * @return 中长短期安全工作计划
     */
    @Override
    public List<SecuritySafetyWorkPlan> selectSecuritySafetyWorkPlanList(SecuritySafetyWorkPlan securitySafetyWorkPlan)
    {
        return securitySafetyWorkPlanMapper.selectSecuritySafetyWorkPlanList(securitySafetyWorkPlan);
    }

    /**
     * 新增中长短期安全工作计划
     * 
     * @param securitySafetyWorkPlan 中长短期安全工作计划
     * @return 结果
     */
    @Override
    public int insertSecuritySafetyWorkPlan(SecuritySafetyWorkPlan securitySafetyWorkPlan)
    {
        return securitySafetyWorkPlanMapper.insertSecuritySafetyWorkPlan(securitySafetyWorkPlan);
    }

    /**
     * 修改中长短期安全工作计划
     * 
     * @param securitySafetyWorkPlan 中长短期安全工作计划
     * @return 结果
     */
    @Override
    public int updateSecuritySafetyWorkPlan(SecuritySafetyWorkPlan securitySafetyWorkPlan)
    {
        return securitySafetyWorkPlanMapper.updateSecuritySafetyWorkPlan(securitySafetyWorkPlan);
    }

    /**
     * 批量删除中长短期安全工作计划
     * 
     * @param ids 需要删除的中长短期安全工作计划主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySafetyWorkPlanByIds(String[] ids)
    {
        return securitySafetyWorkPlanMapper.deleteSecuritySafetyWorkPlanByIds(ids);
    }

    /**
     * 删除中长短期安全工作计划信息
     * 
     * @param id 中长短期安全工作计划主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySafetyWorkPlanById(String id)
    {
        return securitySafetyWorkPlanMapper.deleteSecuritySafetyWorkPlanById(id);
    }
}
