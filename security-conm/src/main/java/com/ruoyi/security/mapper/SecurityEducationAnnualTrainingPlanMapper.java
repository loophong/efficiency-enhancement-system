package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEducationAnnualTrainingPlan;

/**
 * 年度培训计划Mapper接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface SecurityEducationAnnualTrainingPlanMapper 
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
     * 删除年度培训计划
     * 
     * @param id 年度培训计划主键
     * @return 结果
     */
    public int deleteSecurityEducationAnnualTrainingPlanById(Long id);

    /**
     * 批量删除年度培训计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEducationAnnualTrainingPlanByIds(Long[] ids);
}
