package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityEducationAnnualTrainingPlan;

/**
 * 年度培训计划Service接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface ISecurityEducationAnnualTrainingPlanService 
{
    /**
     * 查询年度培训计划
     * 
     * @param id 年度培训计划主键
     * @return 年度培训计划
     */
    public SecurityEducationAnnualTrainingPlan selectSecurityEducationAnnualTrainingPlanById(Long id);

    /**
     * 查询年度培训计划列表
     * 
     * @param securityEducationAnnualTrainingPlan 年度培训计划
     * @return 年度培训计划集合
     */
    public List<SecurityEducationAnnualTrainingPlan> selectSecurityEducationAnnualTrainingPlanList(SecurityEducationAnnualTrainingPlan securityEducationAnnualTrainingPlan);

    /**
     * 新增年度培训计划
     * 
     * @param securityEducationAnnualTrainingPlan 年度培训计划
     * @return 结果
     */
    public int insertSecurityEducationAnnualTrainingPlan(SecurityEducationAnnualTrainingPlan securityEducationAnnualTrainingPlan);

    /**
     * 修改年度培训计划
     * 
     * @param securityEducationAnnualTrainingPlan 年度培训计划
     * @return 结果
     */
    public int updateSecurityEducationAnnualTrainingPlan(SecurityEducationAnnualTrainingPlan securityEducationAnnualTrainingPlan);

    /**
     * 批量删除年度培训计划
     * 
     * @param ids 需要删除的年度培训计划主键集合
     * @return 结果
     */
    public int deleteSecurityEducationAnnualTrainingPlanByIds(Long[] ids);

    /**
     * 删除年度培训计划信息
     * 
     * @param id 年度培训计划主键
     * @return 结果
     */
    public int deleteSecurityEducationAnnualTrainingPlanById(Long id);
}
