package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityComplianceEvaluationRecords;

/**
 * 合规性评价记录Mapper接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface SecurityComplianceEvaluationRecordsMapper 
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
     * 删除合规性评价记录
     * 
     * @param id 合规性评价记录主键
     * @return 结果
     */
    public int deleteSecurityComplianceEvaluationRecordsById(Long id);

    /**
     * 批量删除合规性评价记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityComplianceEvaluationRecordsByIds(Long[] ids);
    
    /**
     * 查询最近导入的记录（未关联ID的记录）
     * 
     * @return 合规性评价记录集合
     */
    public List<SecurityComplianceEvaluationRecords> selectLatestImportedRecords();
    
    /**
     * 更新最近导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
