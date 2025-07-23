package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityCorrectivePreventiveMeasuresApproval;

/**
 * 纠正与预防措施审批实施Mapper接口
 * 
 * @author wang
 * @date 2025-03-07
 */
public interface SecurityCorrectivePreventiveMeasuresApprovalMapper 
{
    /**
     * 查询纠正与预防措施审批实施
     * 
     * @param id 纠正与预防措施审批实施主键
     * @return 纠正与预防措施审批实施
     */
    public SecurityCorrectivePreventiveMeasuresApproval selectSecurityCorrectivePreventiveMeasuresApprovalById(Long id);

    /**
     * 查询纠正与预防措施审批实施列表
     * 
     * @param securityCorrectivePreventiveMeasuresApproval 纠正与预防措施审批实施
     * @return 纠正与预防措施审批实施集合
     */
    public List<SecurityCorrectivePreventiveMeasuresApproval> selectSecurityCorrectivePreventiveMeasuresApprovalList(SecurityCorrectivePreventiveMeasuresApproval securityCorrectivePreventiveMeasuresApproval);

    /**
     * 新增纠正与预防措施审批实施
     * 
     * @param securityCorrectivePreventiveMeasuresApproval 纠正与预防措施审批实施
     * @return 结果
     */
    public int insertSecurityCorrectivePreventiveMeasuresApproval(SecurityCorrectivePreventiveMeasuresApproval securityCorrectivePreventiveMeasuresApproval);

    /**
     * 修改纠正与预防措施审批实施
     * 
     * @param securityCorrectivePreventiveMeasuresApproval 纠正与预防措施审批实施
     * @return 结果
     */
    public int updateSecurityCorrectivePreventiveMeasuresApproval(SecurityCorrectivePreventiveMeasuresApproval securityCorrectivePreventiveMeasuresApproval);

    /**
     * 删除纠正与预防措施审批实施
     * 
     * @param id 纠正与预防措施审批实施主键
     * @return 结果
     */
    public int deleteSecurityCorrectivePreventiveMeasuresApprovalById(Long id);

    /**
     * 批量删除纠正与预防措施审批实施
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityCorrectivePreventiveMeasuresApprovalByIds(Long[] ids);
}
