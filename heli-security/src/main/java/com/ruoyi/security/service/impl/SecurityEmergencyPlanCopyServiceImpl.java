package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEmergencyPlanCopyMapper;
import com.ruoyi.security.domain.SecurityEmergencyPlanCopy;
import com.ruoyi.security.service.ISecurityEmergencyPlanCopyService;

/**
 * 风险和机遇评应对措施评审记录2Service业务层处理
 * 
 * @author wang
 * @date 2025-07-30
 */
@Service
public class SecurityEmergencyPlanCopyServiceImpl implements ISecurityEmergencyPlanCopyService 
{
    @Autowired
    private SecurityEmergencyPlanCopyMapper securityEmergencyPlanCopyMapper;

    /**
     * 查询风险和机遇评应对措施评审记录2
     * 
     * @param id 风险和机遇评应对措施评审记录2主键
     * @return 风险和机遇评应对措施评审记录2
     */
    @Override
    public SecurityEmergencyPlanCopy selectSecurityEmergencyPlanCopyById(String id)
    {
        return securityEmergencyPlanCopyMapper.selectSecurityEmergencyPlanCopyById(id);
    }

    /**
     * 查询风险和机遇评应对措施评审记录2列表
     * 
     * @param securityEmergencyPlanCopy 风险和机遇评应对措施评审记录2
     * @return 风险和机遇评应对措施评审记录2
     */
    @Override
    public List<SecurityEmergencyPlanCopy> selectSecurityEmergencyPlanCopyList(SecurityEmergencyPlanCopy securityEmergencyPlanCopy)
    {
        return securityEmergencyPlanCopyMapper.selectSecurityEmergencyPlanCopyList(securityEmergencyPlanCopy);
    }

    /**
     * 新增风险和机遇评应对措施评审记录2
     * 
     * @param securityEmergencyPlanCopy 风险和机遇评应对措施评审记录2
     * @return 结果
     */
    @Override
    public int insertSecurityEmergencyPlanCopy(SecurityEmergencyPlanCopy securityEmergencyPlanCopy)
    {
        return securityEmergencyPlanCopyMapper.insertSecurityEmergencyPlanCopy(securityEmergencyPlanCopy);
    }

    /**
     * 修改风险和机遇评应对措施评审记录2
     * 
     * @param securityEmergencyPlanCopy 风险和机遇评应对措施评审记录2
     * @return 结果
     */
    @Override
    public int updateSecurityEmergencyPlanCopy(SecurityEmergencyPlanCopy securityEmergencyPlanCopy)
    {
        return securityEmergencyPlanCopyMapper.updateSecurityEmergencyPlanCopy(securityEmergencyPlanCopy);
    }

    /**
     * 批量删除风险和机遇评应对措施评审记录2
     * 
     * @param ids 需要删除的风险和机遇评应对措施评审记录2主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencyPlanCopyByIds(String[] ids)
    {
        return securityEmergencyPlanCopyMapper.deleteSecurityEmergencyPlanCopyByIds(ids);
    }

    /**
     * 删除风险和机遇评应对措施评审记录2信息
     * 
     * @param id 风险和机遇评应对措施评审记录2主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencyPlanCopyById(String id)
    {
        return securityEmergencyPlanCopyMapper.deleteSecurityEmergencyPlanCopyById(id);
    }
}
