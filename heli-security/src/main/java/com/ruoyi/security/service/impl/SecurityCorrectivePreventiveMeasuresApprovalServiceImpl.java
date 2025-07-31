package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityCorrectivePreventiveMeasuresApprovalMapper;
import com.ruoyi.security.domain.SecurityCorrectivePreventiveMeasuresApproval;
import com.ruoyi.security.service.ISecurityCorrectivePreventiveMeasuresApprovalService;

/**
 * 纠正与预防措施审批实施Service业务层处理
 * 
 * @author wang
 * @date 2025-03-07
 */
@Service
public class SecurityCorrectivePreventiveMeasuresApprovalServiceImpl implements ISecurityCorrectivePreventiveMeasuresApprovalService 
{
    @Autowired
    private SecurityCorrectivePreventiveMeasuresApprovalMapper securityCorrectivePreventiveMeasuresApprovalMapper;

    /**
     * 查询纠正与预防措施审批实施
     * 
     * @param id 纠正与预防措施审批实施主键
     * @return 纠正与预防措施审批实施
     */
    @Override
    public SecurityCorrectivePreventiveMeasuresApproval selectSecurityCorrectivePreventiveMeasuresApprovalById(Long id)
    {
        return securityCorrectivePreventiveMeasuresApprovalMapper.selectSecurityCorrectivePreventiveMeasuresApprovalById(id);
    }

    /**
     * 查询纠正与预防措施审批实施列表
     * 
     * @param securityCorrectivePreventiveMeasuresApproval 纠正与预防措施审批实施
     * @return 纠正与预防措施审批实施
     */
    @Override
    public List<SecurityCorrectivePreventiveMeasuresApproval> selectSecurityCorrectivePreventiveMeasuresApprovalList(SecurityCorrectivePreventiveMeasuresApproval securityCorrectivePreventiveMeasuresApproval)
    {
        return securityCorrectivePreventiveMeasuresApprovalMapper.selectSecurityCorrectivePreventiveMeasuresApprovalList(securityCorrectivePreventiveMeasuresApproval);
    }

    /**
     * 新增纠正与预防措施审批实施
     * 
     * @param securityCorrectivePreventiveMeasuresApproval 纠正与预防措施审批实施
     * @return 结果
     */
    @Override
    public int insertSecurityCorrectivePreventiveMeasuresApproval(SecurityCorrectivePreventiveMeasuresApproval securityCorrectivePreventiveMeasuresApproval)
    {
        return securityCorrectivePreventiveMeasuresApprovalMapper.insertSecurityCorrectivePreventiveMeasuresApproval(securityCorrectivePreventiveMeasuresApproval);
    }

    /**
     * 修改纠正与预防措施审批实施
     * 
     * @param securityCorrectivePreventiveMeasuresApproval 纠正与预防措施审批实施
     * @return 结果
     */
    @Override
    public int updateSecurityCorrectivePreventiveMeasuresApproval(SecurityCorrectivePreventiveMeasuresApproval securityCorrectivePreventiveMeasuresApproval)
    {
        return securityCorrectivePreventiveMeasuresApprovalMapper.updateSecurityCorrectivePreventiveMeasuresApproval(securityCorrectivePreventiveMeasuresApproval);
    }

    /**
     * 批量删除纠正与预防措施审批实施
     * 
     * @param ids 需要删除的纠正与预防措施审批实施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityCorrectivePreventiveMeasuresApprovalByIds(Long[] ids)
    {
        return securityCorrectivePreventiveMeasuresApprovalMapper.deleteSecurityCorrectivePreventiveMeasuresApprovalByIds(ids);
    }

    /**
     * 删除纠正与预防措施审批实施信息
     * 
     * @param id 纠正与预防措施审批实施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityCorrectivePreventiveMeasuresApprovalById(Long id)
    {
        return securityCorrectivePreventiveMeasuresApprovalMapper.deleteSecurityCorrectivePreventiveMeasuresApprovalById(id);
    }
}
