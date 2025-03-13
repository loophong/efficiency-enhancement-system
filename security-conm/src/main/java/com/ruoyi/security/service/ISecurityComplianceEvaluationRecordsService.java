package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityComplianceEvaluationRecords;

/**
 * 合规性评价记录Service接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface ISecurityComplianceEvaluationRecordsService 
{
    /**
     * 查询合规性评价记录
     * 
     * @param id 合规性评价记录主键
     * @return 合规性评价记录
     */
    public SecurityComplianceEvaluationRecords selectSecurityComplianceEvaluationRecordsById(Long id);

    /**
     * 查询合规性评价记录列表
     * 
     * @param securityComplianceEvaluationRecords 合规性评价记录
     * @return 合规性评价记录集合
     */
    public List<SecurityComplianceEvaluationRecords> selectSecurityComplianceEvaluationRecordsList(SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords);

    /**
     * 新增合规性评价记录
     * 
     * @param securityComplianceEvaluationRecords 合规性评价记录
     * @return 结果
     */
    public int insertSecurityComplianceEvaluationRecords(SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords);

    /**
     * 修改合规性评价记录
     * 
     * @param securityComplianceEvaluationRecords 合规性评价记录
     * @return 结果
     */
    public int updateSecurityComplianceEvaluationRecords(SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords);

    /**
     * 批量删除合规性评价记录
     * 
     * @param ids 需要删除的合规性评价记录主键集合
     * @return 结果
     */
    public int deleteSecurityComplianceEvaluationRecordsByIds(Long[] ids);

    /**
     * 删除合规性评价记录信息
     * 
     * @param id 合规性评价记录主键
     * @return 结果
     */
    public int deleteSecurityComplianceEvaluationRecordsById(Long id);
}
