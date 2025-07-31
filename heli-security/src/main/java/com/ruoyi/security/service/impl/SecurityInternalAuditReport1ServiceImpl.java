package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityInternalAuditReport1Mapper;
import com.ruoyi.security.domain.SecurityInternalAuditReport1;
import com.ruoyi.security.service.ISecurityInternalAuditReport1Service;

/**
 * 内审报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityInternalAuditReport1ServiceImpl implements ISecurityInternalAuditReport1Service 
{
    @Autowired
    private SecurityInternalAuditReport1Mapper securityInternalAuditReport1Mapper;

    /**
     * 查询内审报告
     * 
     * @param id 内审报告主键
     * @return 内审报告
     */
    @Override
    public SecurityInternalAuditReport1 selectSecurityInternalAuditReport1ById(String id)
    {
        return securityInternalAuditReport1Mapper.selectSecurityInternalAuditReport1ById(id);
    }

    /**
     * 查询内审报告列表
     * 
     * @param securityInternalAuditReport1 内审报告
     * @return 内审报告
     */
    @Override
    public List<SecurityInternalAuditReport1> selectSecurityInternalAuditReport1List(SecurityInternalAuditReport1 securityInternalAuditReport1)
    {
        return securityInternalAuditReport1Mapper.selectSecurityInternalAuditReport1List(securityInternalAuditReport1);
    }

    /**
     * 新增内审报告
     * 
     * @param securityInternalAuditReport1 内审报告
     * @return 结果
     */
    @Override
    public int insertSecurityInternalAuditReport1(SecurityInternalAuditReport1 securityInternalAuditReport1)
    {
        return securityInternalAuditReport1Mapper.insertSecurityInternalAuditReport1(securityInternalAuditReport1);
    }

    /**
     * 修改内审报告
     * 
     * @param securityInternalAuditReport1 内审报告
     * @return 结果
     */
    @Override
    public int updateSecurityInternalAuditReport1(SecurityInternalAuditReport1 securityInternalAuditReport1)
    {
        return securityInternalAuditReport1Mapper.updateSecurityInternalAuditReport1(securityInternalAuditReport1);
    }

    /**
     * 批量删除内审报告
     * 
     * @param ids 需要删除的内审报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityInternalAuditReport1ByIds(String[] ids)
    {
        return securityInternalAuditReport1Mapper.deleteSecurityInternalAuditReport1ByIds(ids);
    }

    /**
     * 删除内审报告信息
     * 
     * @param id 内审报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityInternalAuditReport1ById(String id)
    {
        return securityInternalAuditReport1Mapper.deleteSecurityInternalAuditReport1ById(id);
    }
}
