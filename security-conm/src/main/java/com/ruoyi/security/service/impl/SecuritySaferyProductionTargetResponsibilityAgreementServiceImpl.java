package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySaferyProductionTargetResponsibilityAgreementMapper;
import com.ruoyi.security.domain.SecuritySaferyProductionTargetResponsibilityAgreement;
import com.ruoyi.security.service.ISecuritySaferyProductionTargetResponsibilityAgreementService;

/**
 * 安全生产目标责任书Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecuritySaferyProductionTargetResponsibilityAgreementServiceImpl implements ISecuritySaferyProductionTargetResponsibilityAgreementService 
{
    @Autowired
    private SecuritySaferyProductionTargetResponsibilityAgreementMapper securitySaferyProductionTargetResponsibilityAgreementMapper;

    /**
     * 查询安全生产目标责任书
     * 
     * @param id 安全生产目标责任书主键
     * @return 安全生产目标责任书
     */
    @Override
    public SecuritySaferyProductionTargetResponsibilityAgreement selectSecuritySaferyProductionTargetResponsibilityAgreementById(String id)
    {
        return securitySaferyProductionTargetResponsibilityAgreementMapper.selectSecuritySaferyProductionTargetResponsibilityAgreementById(id);
    }

    /**
     * 查询安全生产目标责任书列表
     * 
     * @param securitySaferyProductionTargetResponsibilityAgreement 安全生产目标责任书
     * @return 安全生产目标责任书
     */
    @Override
    public List<SecuritySaferyProductionTargetResponsibilityAgreement> selectSecuritySaferyProductionTargetResponsibilityAgreementList(SecuritySaferyProductionTargetResponsibilityAgreement securitySaferyProductionTargetResponsibilityAgreement)
    {
        return securitySaferyProductionTargetResponsibilityAgreementMapper.selectSecuritySaferyProductionTargetResponsibilityAgreementList(securitySaferyProductionTargetResponsibilityAgreement);
    }

    /**
     * 新增安全生产目标责任书
     * 
     * @param securitySaferyProductionTargetResponsibilityAgreement 安全生产目标责任书
     * @return 结果
     */
    @Override
    public int insertSecuritySaferyProductionTargetResponsibilityAgreement(SecuritySaferyProductionTargetResponsibilityAgreement securitySaferyProductionTargetResponsibilityAgreement)
    {
        return securitySaferyProductionTargetResponsibilityAgreementMapper.insertSecuritySaferyProductionTargetResponsibilityAgreement(securitySaferyProductionTargetResponsibilityAgreement);
    }

    /**
     * 修改安全生产目标责任书
     * 
     * @param securitySaferyProductionTargetResponsibilityAgreement 安全生产目标责任书
     * @return 结果
     */
    @Override
    public int updateSecuritySaferyProductionTargetResponsibilityAgreement(SecuritySaferyProductionTargetResponsibilityAgreement securitySaferyProductionTargetResponsibilityAgreement)
    {
        return securitySaferyProductionTargetResponsibilityAgreementMapper.updateSecuritySaferyProductionTargetResponsibilityAgreement(securitySaferyProductionTargetResponsibilityAgreement);
    }

    /**
     * 批量删除安全生产目标责任书
     * 
     * @param ids 需要删除的安全生产目标责任书主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySaferyProductionTargetResponsibilityAgreementByIds(String[] ids)
    {
        return securitySaferyProductionTargetResponsibilityAgreementMapper.deleteSecuritySaferyProductionTargetResponsibilityAgreementByIds(ids);
    }

    /**
     * 删除安全生产目标责任书信息
     * 
     * @param id 安全生产目标责任书主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySaferyProductionTargetResponsibilityAgreementById(String id)
    {
        return securitySaferyProductionTargetResponsibilityAgreementMapper.deleteSecuritySaferyProductionTargetResponsibilityAgreementById(id);
    }
}
