package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityAnnualPlan;

/**
 * 环境和职业健康安全方针Service接口
 * 
 * @author wang
 * @date 2025-07-22
 */
public interface ISecurityAnnualPlanService 
{
    /**
     * 查询环境和职业健康安全方针
     * 
     * @param id 环境和职业健康安全方针主键
     * @return 环境和职业健康安全方针
     */
    public SecurityAnnualPlan selectSecurityAnnualPlanById(String id);

    /**
     * 查询环境和职业健康安全方针列表
     * 
     * @param securityAnnualPlan 环境和职业健康安全方针
     * @return 环境和职业健康安全方针集合
     */
    public List<SecurityAnnualPlan> selectSecurityAnnualPlanList(SecurityAnnualPlan securityAnnualPlan);

    /**
     * 新增环境和职业健康安全方针
     * 
     * @param securityAnnualPlan 环境和职业健康安全方针
     * @return 结果
     */
    public int insertSecurityAnnualPlan(SecurityAnnualPlan securityAnnualPlan);

    /**
     * 修改环境和职业健康安全方针
     * 
     * @param securityAnnualPlan 环境和职业健康安全方针
     * @return 结果
     */
    public int updateSecurityAnnualPlan(SecurityAnnualPlan securityAnnualPlan);

    /**
     * 批量删除环境和职业健康安全方针
     * 
     * @param ids 需要删除的环境和职业健康安全方针主键集合
     * @return 结果
     */
    public int deleteSecurityAnnualPlanByIds(String[] ids);

    /**
     * 删除环境和职业健康安全方针信息
     * 
     * @param id 环境和职业健康安全方针主键
     * @return 结果
     */
    public int deleteSecurityAnnualPlanById(String id);
}
