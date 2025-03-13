package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecuritySafetyWorkPlan;

/**
 * 中长短期安全工作计划Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecuritySafetyWorkPlanMapper 
{
    /**
     * 查询中长短期安全工作计划
     * 
     * @param id 中长短期安全工作计划主键
     * @return 中长短期安全工作计划
     */
    public SecuritySafetyWorkPlan selectSecuritySafetyWorkPlanById(String id);

    /**
     * 查询中长短期安全工作计划列表
     * 
     * @param securitySafetyWorkPlan 中长短期安全工作计划
     * @return 中长短期安全工作计划集合
     */
    public List<SecuritySafetyWorkPlan> selectSecuritySafetyWorkPlanList(SecuritySafetyWorkPlan securitySafetyWorkPlan);

    /**
     * 新增中长短期安全工作计划
     * 
     * @param securitySafetyWorkPlan 中长短期安全工作计划
     * @return 结果
     */
    public int insertSecuritySafetyWorkPlan(SecuritySafetyWorkPlan securitySafetyWorkPlan);

    /**
     * 修改中长短期安全工作计划
     * 
     * @param securitySafetyWorkPlan 中长短期安全工作计划
     * @return 结果
     */
    public int updateSecuritySafetyWorkPlan(SecuritySafetyWorkPlan securitySafetyWorkPlan);

    /**
     * 删除中长短期安全工作计划
     * 
     * @param id 中长短期安全工作计划主键
     * @return 结果
     */
    public int deleteSecuritySafetyWorkPlanById(String id);

    /**
     * 批量删除中长短期安全工作计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecuritySafetyWorkPlanByIds(String[] ids);
}
