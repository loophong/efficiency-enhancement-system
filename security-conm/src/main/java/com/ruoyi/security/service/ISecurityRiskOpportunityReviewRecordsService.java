package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityRiskOpportunityReviewRecords;

/**
 * 记录表Service接口
 * 
 * @author wang
 * @date 2025-02-23
 */
public interface ISecurityRiskOpportunityReviewRecordsService 
{
    /**
     * 查询记录表
     * 
     * @param id 记录表主键
     * @return 记录表
     */
    public SecurityRiskOpportunityReviewRecords selectSecurityRiskOpportunityReviewRecordsById(Long id);

    /**
     * 查询记录表列表
     * 
     * @param securityRiskOpportunityReviewRecords 记录表
     * @return 记录表集合
     */
    public List<SecurityRiskOpportunityReviewRecords> selectSecurityRiskOpportunityReviewRecordsList(SecurityRiskOpportunityReviewRecords securityRiskOpportunityReviewRecords);

    /**
     * 新增记录表
     * 
     * @param securityRiskOpportunityReviewRecords 记录表
     * @return 结果
     */
    public int insertSecurityRiskOpportunityReviewRecords(SecurityRiskOpportunityReviewRecords securityRiskOpportunityReviewRecords);

    /**
     * 修改记录表
     * 
     * @param securityRiskOpportunityReviewRecords 记录表
     * @return 结果
     */
    public int updateSecurityRiskOpportunityReviewRecords(SecurityRiskOpportunityReviewRecords securityRiskOpportunityReviewRecords);

    /**
     * 批量删除记录表
     * 
     * @param ids 需要删除的记录表主键集合
     * @return 结果
     */
    public int deleteSecurityRiskOpportunityReviewRecordsByIds(Long[] ids);

    /**
     * 删除记录表信息
     * 
     * @param id 记录表主键
     * @return 结果
     */
    public int deleteSecurityRiskOpportunityReviewRecordsById(Long id);
}
