package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityInternalAuditImplementationPlan1Mapper;
import com.ruoyi.security.domain.SecurityInternalAuditImplementationPlan1;
import com.ruoyi.security.service.ISecurityInternalAuditImplementationPlan1Service;

/**
 * 内审实施计划Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityInternalAuditImplementationPlan1ServiceImpl implements ISecurityInternalAuditImplementationPlan1Service 
{
    @Autowired
    private SecurityInternalAuditImplementationPlan1Mapper securityInternalAuditImplementationPlan1Mapper;

    /**
     * 查询内审实施计划
     * 
     * @param id 内审实施计划主键
     * @return 内审实施计划
     */
    @Override
    public SecurityInternalAuditImplementationPlan1 selectSecurityInternalAuditImplementationPlan1ById(String id)
    {
        return securityInternalAuditImplementationPlan1Mapper.selectSecurityInternalAuditImplementationPlan1ById(id);
    }

    /**
     * 查询内审实施计划列表
     * 
     * @param securityInternalAuditImplementationPlan1 内审实施计划
     * @return 内审实施计划
     */
    @Override
    public List<SecurityInternalAuditImplementationPlan1> selectSecurityInternalAuditImplementationPlan1List(SecurityInternalAuditImplementationPlan1 securityInternalAuditImplementationPlan1)
    {
        return securityInternalAuditImplementationPlan1Mapper.selectSecurityInternalAuditImplementationPlan1List(securityInternalAuditImplementationPlan1);
    }

    /**
     * 新增内审实施计划
     * 
     * @param securityInternalAuditImplementationPlan1 内审实施计划
     * @return 结果
     */
    @Override
    public int insertSecurityInternalAuditImplementationPlan1(SecurityInternalAuditImplementationPlan1 securityInternalAuditImplementationPlan1)
    {
        return securityInternalAuditImplementationPlan1Mapper.insertSecurityInternalAuditImplementationPlan1(securityInternalAuditImplementationPlan1);
    }

    /**
     * 修改内审实施计划
     * 
     * @param securityInternalAuditImplementationPlan1 内审实施计划
     * @return 结果
     */
    @Override
    public int updateSecurityInternalAuditImplementationPlan1(SecurityInternalAuditImplementationPlan1 securityInternalAuditImplementationPlan1)
    {
        return securityInternalAuditImplementationPlan1Mapper.updateSecurityInternalAuditImplementationPlan1(securityInternalAuditImplementationPlan1);
    }

    /**
     * 批量删除内审实施计划
     * 
     * @param ids 需要删除的内审实施计划主键
     * @return 结果
     */
    @Override
    public int deleteSecurityInternalAuditImplementationPlan1ByIds(String[] ids)
    {
        return securityInternalAuditImplementationPlan1Mapper.deleteSecurityInternalAuditImplementationPlan1ByIds(ids);
    }

    /**
     * 删除内审实施计划信息
     * 
     * @param id 内审实施计划主键
     * @return 结果
     */
    @Override
    public int deleteSecurityInternalAuditImplementationPlan1ById(String id)
    {
        return securityInternalAuditImplementationPlan1Mapper.deleteSecurityInternalAuditImplementationPlan1ById(id);
    }
}
