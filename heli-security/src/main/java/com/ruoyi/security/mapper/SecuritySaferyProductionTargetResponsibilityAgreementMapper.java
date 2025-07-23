package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecuritySaferyProductionTargetResponsibilityAgreement;

/**
 * 安全生产目标责任书Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecuritySaferyProductionTargetResponsibilityAgreementMapper 
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
     * 删除安全生产目标责任书
     * 
     * @param id 安全生产目标责任书主键
     * @return 结果
     */
    public int deleteSecuritySaferyProductionTargetResponsibilityAgreementById(String id);

    /**
     * 批量删除安全生产目标责任书
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecuritySaferyProductionTargetResponsibilityAgreementByIds(String[] ids);
}
