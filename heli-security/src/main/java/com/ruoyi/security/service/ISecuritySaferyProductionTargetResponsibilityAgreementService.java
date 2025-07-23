package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecuritySaferyProductionTargetResponsibilityAgreement;

/**
 * 安全生产目标责任书Service接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface ISecuritySaferyProductionTargetResponsibilityAgreementService 
{
    /**
     * 查询安全生产目标责任书
     * 
     * @param id 安全生产目标责任书主键
     * @return 安全生产目标责任书
     */
    public SecuritySaferyProductionTargetResponsibilityAgreement selectSecuritySaferyProductionTargetResponsibilityAgreementById(String id);

    /**
     * 查询安全生产目标责任书列表
     * 
     * @param securitySaferyProductionTargetResponsibilityAgreement 安全生产目标责任书
     * @return 安全生产目标责任书集合
     */
    public List<SecuritySaferyProductionTargetResponsibilityAgreement> selectSecuritySaferyProductionTargetResponsibilityAgreementList(SecuritySaferyProductionTargetResponsibilityAgreement securitySaferyProductionTargetResponsibilityAgreement);

    /**
     * 新增安全生产目标责任书
     * 
     * @param securitySaferyProductionTargetResponsibilityAgreement 安全生产目标责任书
     * @return 结果
     */
    public int insertSecuritySaferyProductionTargetResponsibilityAgreement(SecuritySaferyProductionTargetResponsibilityAgreement securitySaferyProductionTargetResponsibilityAgreement);

    /**
     * 修改安全生产目标责任书
     * 
     * @param securitySaferyProductionTargetResponsibilityAgreement 安全生产目标责任书
     * @return 结果
     */
    public int updateSecuritySaferyProductionTargetResponsibilityAgreement(SecuritySaferyProductionTargetResponsibilityAgreement securitySaferyProductionTargetResponsibilityAgreement);

    /**
     * 批量删除安全生产目标责任书
     * 
     * @param ids 需要删除的安全生产目标责任书主键集合
     * @return 结果
     */
    public int deleteSecuritySaferyProductionTargetResponsibilityAgreementByIds(String[] ids);

    /**
     * 删除安全生产目标责任书信息
     * 
     * @param id 安全生产目标责任书主键
     * @return 结果
     */
    public int deleteSecuritySaferyProductionTargetResponsibilityAgreementById(String id);
}
