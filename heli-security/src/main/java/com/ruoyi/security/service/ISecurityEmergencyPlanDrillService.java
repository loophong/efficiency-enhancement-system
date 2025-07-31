package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityEmergencyPlanDrill;

/**
 * 应急预案演练Service接口
 * 
 * @author wang
 * @date 2025-03-11
 */
public interface ISecurityEmergencyPlanDrillService 
{
    /**
     * 查询应急预案演练
     * 
     * @param id 应急预案演练主键
     * @return 应急预案演练
     */
    public SecurityEmergencyPlanDrill selectSecurityEmergencyPlanDrillById(String id);

    /**
     * 查询应急预案演练列表
     * 
     * @param securityEmergencyPlanDrill 应急预案演练
     * @return 应急预案演练集合
     */
    public List<SecurityEmergencyPlanDrill> selectSecurityEmergencyPlanDrillList(SecurityEmergencyPlanDrill securityEmergencyPlanDrill);

    /**
     * 新增应急预案演练
     * 
     * @param securityEmergencyPlanDrill 应急预案演练
     * @return 结果
     */
    public int insertSecurityEmergencyPlanDrill(SecurityEmergencyPlanDrill securityEmergencyPlanDrill);

    /**
     * 修改应急预案演练
     * 
     * @param securityEmergencyPlanDrill 应急预案演练
     * @return 结果
     */
    public int updateSecurityEmergencyPlanDrill(SecurityEmergencyPlanDrill securityEmergencyPlanDrill);

    /**
     * 批量删除应急预案演练
     * 
     * @param ids 需要删除的应急预案演练主键集合
     * @return 结果
     */
    public int deleteSecurityEmergencyPlanDrillByIds(String[] ids);

    /**
     * 删除应急预案演练信息
     * 
     * @param id 应急预案演练主键
     * @return 结果
     */
    public int deleteSecurityEmergencyPlanDrillById(String id);
}
