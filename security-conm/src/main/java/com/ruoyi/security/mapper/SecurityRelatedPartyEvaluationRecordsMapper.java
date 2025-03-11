package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityRelatedPartyEvaluationRecords;

/**
 * 相关方评价记录Mapper接口
 * 
 * @author wang
 * @date 2025-03-05
 */
public interface SecurityRelatedPartyEvaluationRecordsMapper 
{
    /**
     * 查询相关方评价记录
     * 
     * @param id 相关方评价记录主键
     * @return 相关方评价记录
     */
    public SecurityRelatedPartyEvaluationRecords selectSecurityRelatedPartyEvaluationRecordsById(Long id);

    /**
     * 查询相关方评价记录列表
     * 
     * @param securityRelatedPartyEvaluationRecords 相关方评价记录
     * @return 相关方评价记录集合
     */
    public List<SecurityRelatedPartyEvaluationRecords> selectSecurityRelatedPartyEvaluationRecordsList(SecurityRelatedPartyEvaluationRecords securityRelatedPartyEvaluationRecords);

    /**
     * 新增相关方评价记录
     * 
     * @param securityRelatedPartyEvaluationRecords 相关方评价记录
     * @return 结果
     */
    public int insertSecurityRelatedPartyEvaluationRecords(SecurityRelatedPartyEvaluationRecords securityRelatedPartyEvaluationRecords);

    /**
     * 修改相关方评价记录
     * 
     * @param securityRelatedPartyEvaluationRecords 相关方评价记录
     * @return 结果
     */
    public int updateSecurityRelatedPartyEvaluationRecords(SecurityRelatedPartyEvaluationRecords securityRelatedPartyEvaluationRecords);

    /**
     * 删除相关方评价记录
     * 
     * @param id 相关方评价记录主键
     * @return 结果
     */
    public int deleteSecurityRelatedPartyEvaluationRecordsById(Long id);

    /**
     * 批量删除相关方评价记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityRelatedPartyEvaluationRecordsByIds(Long[] ids);
}
