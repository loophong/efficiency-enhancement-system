package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityAnnualPlanMapper;
import com.ruoyi.security.domain.SecurityAnnualPlan;
import com.ruoyi.security.service.ISecurityAnnualPlanService;

/**
 * 环境和职业健康安全方针Service业务层处理
 * 
 * @author wang
 * @date 2025-07-22
 */
@Service
public class SecurityAnnualPlanServiceImpl implements ISecurityAnnualPlanService 
{
    @Autowired
    private SecurityAnnualPlanMapper securityAnnualPlanMapper;

    /**
     * 查询环境和职业健康安全方针
     * 
     * @param id 环境和职业健康安全方针主键
     * @return 环境和职业健康安全方针
     */
    @Override
    public SecurityAnnualPlan selectSecurityAnnualPlanById(String id)
    {
        return securityAnnualPlanMapper.selectSecurityAnnualPlanById(id);
    }

    /**
     * 查询环境和职业健康安全方针列表
     * 
     * @param securityAnnualPlan 环境和职业健康安全方针
     * @return 环境和职业健康安全方针
     */
    @Override
    public List<SecurityAnnualPlan> selectSecurityAnnualPlanList(SecurityAnnualPlan securityAnnualPlan)
    {
        return securityAnnualPlanMapper.selectSecurityAnnualPlanList(securityAnnualPlan);
    }

    /**
     * 新增环境和职业健康安全方针
     * 
     * @param securityAnnualPlan 环境和职业健康安全方针
     * @return 结果
     */
    @Override
    public int insertSecurityAnnualPlan(SecurityAnnualPlan securityAnnualPlan)
    {
        return securityAnnualPlanMapper.insertSecurityAnnualPlan(securityAnnualPlan);
    }

    /**
     * 修改环境和职业健康安全方针
     * 
     * @param securityAnnualPlan 环境和职业健康安全方针
     * @return 结果
     */
    @Override
    public int updateSecurityAnnualPlan(SecurityAnnualPlan securityAnnualPlan)
    {
        return securityAnnualPlanMapper.updateSecurityAnnualPlan(securityAnnualPlan);
    }

    /**
     * 批量删除环境和职业健康安全方针
     * 
     * @param ids 需要删除的环境和职业健康安全方针主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAnnualPlanByIds(String[] ids)
    {
        return securityAnnualPlanMapper.deleteSecurityAnnualPlanByIds(ids);
    }

    /**
     * 删除环境和职业健康安全方针信息
     * 
     * @param id 环境和职业健康安全方针主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAnnualPlanById(String id)
    {
        return securityAnnualPlanMapper.deleteSecurityAnnualPlanById(id);
    }
}
