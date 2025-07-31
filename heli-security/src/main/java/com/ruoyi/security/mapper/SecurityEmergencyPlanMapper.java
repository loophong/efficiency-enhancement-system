package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEmergencyPlan;

/**
 * 应急预案Mapper接口
 * 
 * @author wang
 * @date 2025-03-11
 */
public interface SecurityEmergencyPlanMapper 
{
    /**
     * 查询应急预案
     * 
     * @param id 应急预案主键
     * @return 应急预案
     */
    public SecurityEmergencyPlan selectSecurityEmergencyPlanById(String id);

    /**
     * 查询应急预案列表
     * 
     * @param securityEmergencyPlan 应急预案
     * @return 应急预案集合
     */
    public List<SecurityEmergencyPlan> selectSecurityEmergencyPlanList(SecurityEmergencyPlan securityEmergencyPlan);

    /**
     * 新增应急预案
     * 
     * @param securityEmergencyPlan 应急预案
     * @return 结果
     */
    public int insertSecurityEmergencyPlan(SecurityEmergencyPlan securityEmergencyPlan);

    /**
     * 修改应急预案
     * 
     * @param securityEmergencyPlan 应急预案
     * @return 结果
     */
    public int updateSecurityEmergencyPlan(SecurityEmergencyPlan securityEmergencyPlan);

    /**
     * 删除应急预案
     * 
     * @param id 应急预案主键
     * @return 结果
     */
    public int deleteSecurityEmergencyPlanById(String id);

    /**
     * 批量删除应急预案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEmergencyPlanByIds(String[] ids);
}
