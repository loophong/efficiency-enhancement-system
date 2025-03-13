package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEmergencyDrillPlanMapper;
import com.ruoyi.security.domain.SecurityEmergencyDrillPlan;
import com.ruoyi.security.service.ISecurityEmergencyDrillPlanService;

/**
 * 应急演练计划Service业务层处理
 * 
 * @author wang
 * @date 2025-03-06
 */
@Service
public class SecurityEmergencyDrillPlanServiceImpl implements ISecurityEmergencyDrillPlanService 
{
    @Autowired
    private SecurityEmergencyDrillPlanMapper securityEmergencyDrillPlanMapper;

    /**
     * 查询应急演练计划
     * 
     * @param id 应急演练计划主键
     * @return 应急演练计划
     */
    @Override
    public SecurityEmergencyDrillPlan selectSecurityEmergencyDrillPlanById(Long id)
    {
        return securityEmergencyDrillPlanMapper.selectSecurityEmergencyDrillPlanById(id);
    }

    /**
     * 查询应急演练计划列表
     * 
     * @param securityEmergencyDrillPlan 应急演练计划
     * @return 应急演练计划
     */
    @Override
    public List<SecurityEmergencyDrillPlan> selectSecurityEmergencyDrillPlanList(SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        return securityEmergencyDrillPlanMapper.selectSecurityEmergencyDrillPlanList(securityEmergencyDrillPlan);
    }

    /**
     * 新增应急演练计划
     * 
     * @param securityEmergencyDrillPlan 应急演练计划
     * @return 结果
     */
    @Override
    public int insertSecurityEmergencyDrillPlan(SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        return securityEmergencyDrillPlanMapper.insertSecurityEmergencyDrillPlan(securityEmergencyDrillPlan);
    }

    /**
     * 修改应急演练计划
     * 
     * @param securityEmergencyDrillPlan 应急演练计划
     * @return 结果
     */
    @Override
    public int updateSecurityEmergencyDrillPlan(SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        return securityEmergencyDrillPlanMapper.updateSecurityEmergencyDrillPlan(securityEmergencyDrillPlan);
    }

    /**
     * 批量删除应急演练计划
     * 
     * @param ids 需要删除的应急演练计划主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencyDrillPlanByIds(Long[] ids)
    {
        return securityEmergencyDrillPlanMapper.deleteSecurityEmergencyDrillPlanByIds(ids);
    }

    /**
     * 删除应急演练计划信息
     * 
     * @param id 应急演练计划主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencyDrillPlanById(Long id)
    {
        return securityEmergencyDrillPlanMapper.deleteSecurityEmergencyDrillPlanById(id);
    }
}
