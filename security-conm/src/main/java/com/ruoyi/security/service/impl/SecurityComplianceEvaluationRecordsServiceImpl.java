package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityComplianceEvaluationRecordsMapper;
import com.ruoyi.security.domain.SecurityComplianceEvaluationRecords;
import com.ruoyi.security.service.ISecurityComplianceEvaluationRecordsService;

/**
 * 合规性评价记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Service
public class SecurityComplianceEvaluationRecordsServiceImpl implements ISecurityComplianceEvaluationRecordsService 
{
    @Autowired
    private SecurityComplianceEvaluationRecordsMapper securityComplianceEvaluationRecordsMapper;

    /**
     * 查询合规性评价记录
     * 
     * @param id 合规性评价记录主键
     * @return 合规性评价记录
     */
    @Override
    public SecurityComplianceEvaluationRecords selectSecurityComplianceEvaluationRecordsById(Long id)
    {
        return securityComplianceEvaluationRecordsMapper.selectSecurityComplianceEvaluationRecordsById(id);
    }

    /**
     * 查询合规性评价记录列表
     * 
     * @param securityComplianceEvaluationRecords 合规性评价记录
     * @return 合规性评价记录
     */
    @Override
    public List<SecurityComplianceEvaluationRecords> selectSecurityComplianceEvaluationRecordsList(SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        return securityComplianceEvaluationRecordsMapper.selectSecurityComplianceEvaluationRecordsList(securityComplianceEvaluationRecords);
    }

    /**
     * 新增合规性评价记录
     * 
     * @param securityComplianceEvaluationRecords 合规性评价记录
     * @return 结果
     */
    @Override
    public int insertSecurityComplianceEvaluationRecords(SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        return securityComplianceEvaluationRecordsMapper.insertSecurityComplianceEvaluationRecords(securityComplianceEvaluationRecords);
    }

    /**
     * 修改合规性评价记录
     * 
     * @param securityComplianceEvaluationRecords 合规性评价记录
     * @return 结果
     */
    @Override
    public int updateSecurityComplianceEvaluationRecords(SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        return securityComplianceEvaluationRecordsMapper.updateSecurityComplianceEvaluationRecords(securityComplianceEvaluationRecords);
    }

    /**
     * 批量删除合规性评价记录
     * 
     * @param ids 需要删除的合规性评价记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityComplianceEvaluationRecordsByIds(Long[] ids)
    {
        return securityComplianceEvaluationRecordsMapper.deleteSecurityComplianceEvaluationRecordsByIds(ids);
    }

    /**
     * 删除合规性评价记录信息
     * 
     * @param id 合规性评价记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityComplianceEvaluationRecordsById(Long id)
    {
        return securityComplianceEvaluationRecordsMapper.deleteSecurityComplianceEvaluationRecordsById(id);
    }
}
