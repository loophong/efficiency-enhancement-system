package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
