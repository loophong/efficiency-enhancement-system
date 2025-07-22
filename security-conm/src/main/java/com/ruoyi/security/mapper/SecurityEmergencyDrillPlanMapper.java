package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEmergencyDrillPlan;

/**
 * 应急演练计划Mapper接口
 * 
 * @author wang
 * @date 2025-03-06
 */
public interface SecurityEmergencyDrillPlanMapper 
{
    /**
     * 查询应急演练计划
     * 
     * @param id 应急演练计划主键
     * @return 应急演练计划
     */
    public SecurityEmergencyDrillPlan selectSecurityEmergencyDrillPlanById(Long id);

    /**
     * 查询应急演练计划列表
     * 
     * @param securityEmergencyDrillPlan 应急演练计划
     * @return 应急演练计划集合
     */
    public List<SecurityEmergencyDrillPlan> selectSecurityEmergencyDrillPlanList(SecurityEmergencyDrillPlan securityEmergencyDrillPlan);

    /**
     * 新增应急演练计划
     * 
     * @param securityEmergencyDrillPlan 应急演练计划
     * @return 结果
     */
    public int insertSecurityEmergencyDrillPlan(SecurityEmergencyDrillPlan securityEmergencyDrillPlan);

    /**
     * 修改应急演练计划
     * 
     * @param securityEmergencyDrillPlan 应急演练计划
     * @return 结果
     */
    public int updateSecurityEmergencyDrillPlan(SecurityEmergencyDrillPlan securityEmergencyDrillPlan);

    /**
     * 删除应急演练计划
     * 
     * @param id 应急演练计划主键
     * @return 结果
     */
    public int deleteSecurityEmergencyDrillPlanById(Long id);

    /**
     * 批量删除应急演练计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEmergencyDrillPlanByIds(Long[] ids);

    /**
     * 根据关联ID查询应急演练计划列表
     *
     * @param relatedId 关联ID
     * @return 应急演练计划集合
     */
    public List<SecurityEmergencyDrillPlan> selectSecurityEmergencyDrillPlanByRelatedId(Long relatedId);

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
