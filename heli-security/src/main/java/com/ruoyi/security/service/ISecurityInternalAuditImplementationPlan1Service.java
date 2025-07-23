package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityInternalAuditImplementationPlan1;

/**
 * 内审实施计划Service接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface ISecurityInternalAuditImplementationPlan1Service 
{
    /**
     * 查询内审实施计划
     * 
     * @param id 内审实施计划主键
     * @return 内审实施计划
     */
    public SecurityInternalAuditImplementationPlan1 selectSecurityInternalAuditImplementationPlan1ById(String id);

    /**
     * 查询内审实施计划列表
     * 
     * @param securityInternalAuditImplementationPlan1 内审实施计划
     * @return 内审实施计划集合
     */
    public List<SecurityInternalAuditImplementationPlan1> selectSecurityInternalAuditImplementationPlan1List(SecurityInternalAuditImplementationPlan1 securityInternalAuditImplementationPlan1);

    /**
     * 新增内审实施计划
     * 
     * @param securityInternalAuditImplementationPlan1 内审实施计划
     * @return 结果
     */
    public int insertSecurityInternalAuditImplementationPlan1(SecurityInternalAuditImplementationPlan1 securityInternalAuditImplementationPlan1);

    /**
     * 修改内审实施计划
     * 
     * @param securityInternalAuditImplementationPlan1 内审实施计划
     * @return 结果
     */
    public int updateSecurityInternalAuditImplementationPlan1(SecurityInternalAuditImplementationPlan1 securityInternalAuditImplementationPlan1);

    /**
     * 批量删除内审实施计划
     * 
     * @param ids 需要删除的内审实施计划主键集合
     * @return 结果
     */
    public int deleteSecurityInternalAuditImplementationPlan1ByIds(String[] ids);

    /**
     * 删除内审实施计划信息
     * 
     * @param id 内审实施计划主键
     * @return 结果
     */
    public int deleteSecurityInternalAuditImplementationPlan1ById(String id);
}
