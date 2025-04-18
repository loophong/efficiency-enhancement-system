package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthManagementPlan;

/**
 * 环境职业健康安全管理评审计划Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityEnvironmentalOccupationalHealthManagementPlanMapper 
{
    /**
     * 查询环境职业健康安全管理评审计划
     * 
     * @param id 环境职业健康安全管理评审计划主键
     * @return 环境职业健康安全管理评审计划
     */
    public SecurityEnvironmentalOccupationalHealthManagementPlan selectSecurityEnvironmentalOccupationalHealthManagementPlanById(String id);

    /**
     * 查询环境职业健康安全管理评审计划列表
     * 
     * @param securityEnvironmentalOccupationalHealthManagementPlan 环境职业健康安全管理评审计划
     * @return 环境职业健康安全管理评审计划集合
     */
    public List<SecurityEnvironmentalOccupationalHealthManagementPlan> selectSecurityEnvironmentalOccupationalHealthManagementPlanList(SecurityEnvironmentalOccupationalHealthManagementPlan securityEnvironmentalOccupationalHealthManagementPlan);

    /**
     * 新增环境职业健康安全管理评审计划
     * 
     * @param securityEnvironmentalOccupationalHealthManagementPlan 环境职业健康安全管理评审计划
     * @return 结果
     */
    public int insertSecurityEnvironmentalOccupationalHealthManagementPlan(SecurityEnvironmentalOccupationalHealthManagementPlan securityEnvironmentalOccupationalHealthManagementPlan);

    /**
     * 修改环境职业健康安全管理评审计划
     * 
     * @param securityEnvironmentalOccupationalHealthManagementPlan 环境职业健康安全管理评审计划
     * @return 结果
     */
    public int updateSecurityEnvironmentalOccupationalHealthManagementPlan(SecurityEnvironmentalOccupationalHealthManagementPlan securityEnvironmentalOccupationalHealthManagementPlan);

    /**
     * 删除环境职业健康安全管理评审计划
     * 
     * @param id 环境职业健康安全管理评审计划主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthManagementPlanById(String id);

    /**
     * 批量删除环境职业健康安全管理评审计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthManagementPlanByIds(String[] ids);
}
