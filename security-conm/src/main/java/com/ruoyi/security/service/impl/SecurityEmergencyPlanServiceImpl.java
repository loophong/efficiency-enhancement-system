package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEmergencyPlanMapper;
import com.ruoyi.security.domain.SecurityEmergencyPlan;
import com.ruoyi.security.service.ISecurityEmergencyPlanService;

/**
 * 应急预案Service业务层处理
 * 
 * @author wang
 * @date 2025-03-11
 */
@Service
public class SecurityEmergencyPlanServiceImpl implements ISecurityEmergencyPlanService 
{
    @Autowired
    private SecurityEmergencyPlanMapper securityEmergencyPlanMapper;

    /**
     * 查询应急预案
     * 
     * @param id 应急预案主键
     * @return 应急预案
     */
    @Override
    public SecurityEmergencyPlan selectSecurityEmergencyPlanById(String id)
    {
        return securityEmergencyPlanMapper.selectSecurityEmergencyPlanById(id);
    }

    /**
     * 查询应急预案列表
     * 
     * @param securityEmergencyPlan 应急预案
     * @return 应急预案
     */
    @Override
    public List<SecurityEmergencyPlan> selectSecurityEmergencyPlanList(SecurityEmergencyPlan securityEmergencyPlan)
    {
        return securityEmergencyPlanMapper.selectSecurityEmergencyPlanList(securityEmergencyPlan);
    }

    /**
     * 新增应急预案
     * 
     * @param securityEmergencyPlan 应急预案
     * @return 结果
     */
    @Override
    public int insertSecurityEmergencyPlan(SecurityEmergencyPlan securityEmergencyPlan)
    {
        return securityEmergencyPlanMapper.insertSecurityEmergencyPlan(securityEmergencyPlan);
    }

    /**
     * 修改应急预案
     * 
     * @param securityEmergencyPlan 应急预案
     * @return 结果
     */
    @Override
    public int updateSecurityEmergencyPlan(SecurityEmergencyPlan securityEmergencyPlan)
    {
        return securityEmergencyPlanMapper.updateSecurityEmergencyPlan(securityEmergencyPlan);
    }

    /**
     * 批量删除应急预案
     * 
     * @param ids 需要删除的应急预案主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencyPlanByIds(String[] ids)
    {
        return securityEmergencyPlanMapper.deleteSecurityEmergencyPlanByIds(ids);
    }

    /**
     * 删除应急预案信息
     * 
     * @param id 应急预案主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencyPlanById(String id)
    {
        return securityEmergencyPlanMapper.deleteSecurityEmergencyPlanById(id);
    }
}
