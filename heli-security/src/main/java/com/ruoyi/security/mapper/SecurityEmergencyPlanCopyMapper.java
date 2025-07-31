package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEmergencyPlanCopy;

/**
 * 风险和机遇评应对措施评审记录2Mapper接口
 * 
 * @author wang
 * @date 2025-07-30
 */
public interface SecurityEmergencyPlanCopyMapper 
{
    /**
     * 查询风险和机遇评应对措施评审记录2
     * 
     * @param id 风险和机遇评应对措施评审记录2主键
     * @return 风险和机遇评应对措施评审记录2
     */
    public SecurityEmergencyPlanCopy selectSecurityEmergencyPlanCopyById(String id);

    /**
     * 查询风险和机遇评应对措施评审记录2列表
     * 
     * @param securityEmergencyPlanCopy 风险和机遇评应对措施评审记录2
     * @return 风险和机遇评应对措施评审记录2集合
     */
    public List<SecurityEmergencyPlanCopy> selectSecurityEmergencyPlanCopyList(SecurityEmergencyPlanCopy securityEmergencyPlanCopy);

    /**
     * 新增风险和机遇评应对措施评审记录2
     * 
     * @param securityEmergencyPlanCopy 风险和机遇评应对措施评审记录2
     * @return 结果
     */
    public int insertSecurityEmergencyPlanCopy(SecurityEmergencyPlanCopy securityEmergencyPlanCopy);

    /**
     * 修改风险和机遇评应对措施评审记录2
     * 
     * @param securityEmergencyPlanCopy 风险和机遇评应对措施评审记录2
     * @return 结果
     */
    public int updateSecurityEmergencyPlanCopy(SecurityEmergencyPlanCopy securityEmergencyPlanCopy);

    /**
     * 删除风险和机遇评应对措施评审记录2
     * 
     * @param id 风险和机遇评应对措施评审记录2主键
     * @return 结果
     */
    public int deleteSecurityEmergencyPlanCopyById(String id);

    /**
     * 批量删除风险和机遇评应对措施评审记录2
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEmergencyPlanCopyByIds(String[] ids);
}
