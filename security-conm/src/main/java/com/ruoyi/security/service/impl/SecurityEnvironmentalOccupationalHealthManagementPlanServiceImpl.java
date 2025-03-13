package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOccupationalHealthManagementPlanMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthManagementPlan;
import com.ruoyi.security.service.ISecurityEnvironmentalOccupationalHealthManagementPlanService;

/**
 * 环境职业健康安全管理评审计划Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityEnvironmentalOccupationalHealthManagementPlanServiceImpl implements ISecurityEnvironmentalOccupationalHealthManagementPlanService 
{
    @Autowired
    private SecurityEnvironmentalOccupationalHealthManagementPlanMapper securityEnvironmentalOccupationalHealthManagementPlanMapper;

    /**
     * 查询环境职业健康安全管理评审计划
     * 
     * @param id 环境职业健康安全管理评审计划主键
     * @return 环境职业健康安全管理评审计划
     */
    @Override
    public SecurityEnvironmentalOccupationalHealthManagementPlan selectSecurityEnvironmentalOccupationalHealthManagementPlanById(String id)
    {
        return securityEnvironmentalOccupationalHealthManagementPlanMapper.selectSecurityEnvironmentalOccupationalHealthManagementPlanById(id);
    }

    /**
     * 查询环境职业健康安全管理评审计划列表
     * 
     * @param securityEnvironmentalOccupationalHealthManagementPlan 环境职业健康安全管理评审计划
     * @return 环境职业健康安全管理评审计划
     */
    @Override
    public List<SecurityEnvironmentalOccupationalHealthManagementPlan> selectSecurityEnvironmentalOccupationalHealthManagementPlanList(SecurityEnvironmentalOccupationalHealthManagementPlan securityEnvironmentalOccupationalHealthManagementPlan)
    {
        return securityEnvironmentalOccupationalHealthManagementPlanMapper.selectSecurityEnvironmentalOccupationalHealthManagementPlanList(securityEnvironmentalOccupationalHealthManagementPlan);
    }

    /**
     * 新增环境职业健康安全管理评审计划
     * 
     * @param securityEnvironmentalOccupationalHealthManagementPlan 环境职业健康安全管理评审计划
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOccupationalHealthManagementPlan(SecurityEnvironmentalOccupationalHealthManagementPlan securityEnvironmentalOccupationalHealthManagementPlan)
    {
        return securityEnvironmentalOccupationalHealthManagementPlanMapper.insertSecurityEnvironmentalOccupationalHealthManagementPlan(securityEnvironmentalOccupationalHealthManagementPlan);
    }

    /**
     * 修改环境职业健康安全管理评审计划
     * 
     * @param securityEnvironmentalOccupationalHealthManagementPlan 环境职业健康安全管理评审计划
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOccupationalHealthManagementPlan(SecurityEnvironmentalOccupationalHealthManagementPlan securityEnvironmentalOccupationalHealthManagementPlan)
    {
        return securityEnvironmentalOccupationalHealthManagementPlanMapper.updateSecurityEnvironmentalOccupationalHealthManagementPlan(securityEnvironmentalOccupationalHealthManagementPlan);
    }

    /**
     * 批量删除环境职业健康安全管理评审计划
     * 
     * @param ids 需要删除的环境职业健康安全管理评审计划主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOccupationalHealthManagementPlanByIds(String[] ids)
    {
        return securityEnvironmentalOccupationalHealthManagementPlanMapper.deleteSecurityEnvironmentalOccupationalHealthManagementPlanByIds(ids);
    }

    /**
     * 删除环境职业健康安全管理评审计划信息
     * 
     * @param id 环境职业健康安全管理评审计划主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOccupationalHealthManagementPlanById(String id)
    {
        return securityEnvironmentalOccupationalHealthManagementPlanMapper.deleteSecurityEnvironmentalOccupationalHealthManagementPlanById(id);
    }
}
