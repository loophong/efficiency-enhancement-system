package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRelatedPartyEvaluationRecordsMapper;
import com.ruoyi.security.domain.SecurityRelatedPartyEvaluationRecords;
import com.ruoyi.security.service.ISecurityRelatedPartyEvaluationRecordsService;

/**
 * 相关方评价记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-05
 */
@Service
public class SecurityRelatedPartyEvaluationRecordsServiceImpl implements ISecurityRelatedPartyEvaluationRecordsService 
{
    @Autowired
    private SecurityRelatedPartyEvaluationRecordsMapper securityRelatedPartyEvaluationRecordsMapper;

    /**
     * 查询相关方评价记录
     * 
     * @param id 相关方评价记录主键
     * @return 相关方评价记录
     */
    @Override
    public SecurityRelatedPartyEvaluationRecords selectSecurityRelatedPartyEvaluationRecordsById(Long id)
    {
        return securityRelatedPartyEvaluationRecordsMapper.selectSecurityRelatedPartyEvaluationRecordsById(id);
    }

    /**
     * 查询相关方评价记录列表
     * 
     * @param securityRelatedPartyEvaluationRecords 相关方评价记录
     * @return 相关方评价记录
     */
    @Override
    public List<SecurityRelatedPartyEvaluationRecords> selectSecurityRelatedPartyEvaluationRecordsList(SecurityRelatedPartyEvaluationRecords securityRelatedPartyEvaluationRecords)
    {
        return securityRelatedPartyEvaluationRecordsMapper.selectSecurityRelatedPartyEvaluationRecordsList(securityRelatedPartyEvaluationRecords);
    }

    /**
     * 新增相关方评价记录
     * 
     * @param securityRelatedPartyEvaluationRecords 相关方评价记录
     * @return 结果
     */
    @Override
    public int insertSecurityRelatedPartyEvaluationRecords(SecurityRelatedPartyEvaluationRecords securityRelatedPartyEvaluationRecords)
    {
        return securityRelatedPartyEvaluationRecordsMapper.insertSecurityRelatedPartyEvaluationRecords(securityRelatedPartyEvaluationRecords);
    }

    /**
     * 修改相关方评价记录
     * 
     * @param securityRelatedPartyEvaluationRecords 相关方评价记录
     * @return 结果
     */
    @Override
    public int updateSecurityRelatedPartyEvaluationRecords(SecurityRelatedPartyEvaluationRecords securityRelatedPartyEvaluationRecords)
    {
        return securityRelatedPartyEvaluationRecordsMapper.updateSecurityRelatedPartyEvaluationRecords(securityRelatedPartyEvaluationRecords);
    }

    /**
     * 批量删除相关方评价记录
     * 
     * @param ids 需要删除的相关方评价记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelatedPartyEvaluationRecordsByIds(Long[] ids)
    {
        return securityRelatedPartyEvaluationRecordsMapper.deleteSecurityRelatedPartyEvaluationRecordsByIds(ids);
    }

    /**
     * 删除相关方评价记录信息
     * 
     * @param id 相关方评价记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelatedPartyEvaluationRecordsById(Long id)
    {
        return securityRelatedPartyEvaluationRecordsMapper.deleteSecurityRelatedPartyEvaluationRecordsById(id);
    }
}
