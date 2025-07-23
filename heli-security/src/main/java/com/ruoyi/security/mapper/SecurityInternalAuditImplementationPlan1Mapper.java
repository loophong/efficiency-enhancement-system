package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityInternalAuditImplementationPlan1;

/**
 * 内审实施计划Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityInternalAuditImplementationPlan1Mapper 
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
     * 删除内审实施计划
     * 
     * @param id 内审实施计划主键
     * @return 结果
     */
    public int deleteSecurityInternalAuditImplementationPlan1ById(String id);

    /**
     * 批量删除内审实施计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityInternalAuditImplementationPlan1ByIds(String[] ids);
}
