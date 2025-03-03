package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRiskOpportunityReviewRecordsMapper;
import com.ruoyi.security.domain.SecurityRiskOpportunityReviewRecords;
import com.ruoyi.security.service.ISecurityRiskOpportunityReviewRecordsService;

/**
 * 记录表Service业务层处理
 * 
 * @author wang
 * @date 2025-02-23
 */
@Service
public class SecurityRiskOpportunityReviewRecordsServiceImpl implements ISecurityRiskOpportunityReviewRecordsService 
{
    @Autowired
    private SecurityRiskOpportunityReviewRecordsMapper securityRiskOpportunityReviewRecordsMapper;

    /**
     * 查询记录表
     * 
     * @param id 记录表主键
     * @return 记录表
     */
    @Override
    public SecurityRiskOpportunityReviewRecords selectSecurityRiskOpportunityReviewRecordsById(Long id)
    {
        return securityRiskOpportunityReviewRecordsMapper.selectSecurityRiskOpportunityReviewRecordsById(id);
    }

    /**
     * 查询记录表列表
     * 
     * @param securityRiskOpportunityReviewRecords 记录表
     * @return 记录表
     */
    @Override
    public List<SecurityRiskOpportunityReviewRecords> selectSecurityRiskOpportunityReviewRecordsList(SecurityRiskOpportunityReviewRecords securityRiskOpportunityReviewRecords)
    {
        return securityRiskOpportunityReviewRecordsMapper.selectSecurityRiskOpportunityReviewRecordsList(securityRiskOpportunityReviewRecords);
    }

    /**
     * 新增记录表
     * 
     * @param securityRiskOpportunityReviewRecords 记录表
     * @return 结果
     */
    @Override
    public int insertSecurityRiskOpportunityReviewRecords(SecurityRiskOpportunityReviewRecords securityRiskOpportunityReviewRecords)
    {
        return securityRiskOpportunityReviewRecordsMapper.insertSecurityRiskOpportunityReviewRecords(securityRiskOpportunityReviewRecords);
    }

    /**
     * 修改记录表
     * 
     * @param securityRiskOpportunityReviewRecords 记录表
     * @return 结果
     */
    @Override
    public int updateSecurityRiskOpportunityReviewRecords(SecurityRiskOpportunityReviewRecords securityRiskOpportunityReviewRecords)
    {
        return securityRiskOpportunityReviewRecordsMapper.updateSecurityRiskOpportunityReviewRecords(securityRiskOpportunityReviewRecords);
    }

    /**
     * 批量删除记录表
     * 
     * @param ids 需要删除的记录表主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRiskOpportunityReviewRecordsByIds(Long[] ids)
    {
        return securityRiskOpportunityReviewRecordsMapper.deleteSecurityRiskOpportunityReviewRecordsByIds(ids);
    }

    /**
     * 删除记录表信息
     * 
     * @param id 记录表主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRiskOpportunityReviewRecordsById(Long id)
    {
        return securityRiskOpportunityReviewRecordsMapper.deleteSecurityRiskOpportunityReviewRecordsById(id);
    }
}
