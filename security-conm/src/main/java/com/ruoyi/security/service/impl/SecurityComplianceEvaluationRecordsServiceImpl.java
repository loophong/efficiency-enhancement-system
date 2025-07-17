package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityComplianceEvaluationRecordsMapper;
import com.ruoyi.security.domain.SecurityComplianceEvaluationRecords;
import com.ruoyi.security.service.ISecurityComplianceEvaluationRecordsService;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 合规性评价记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Slf4j
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
    
    /**
     * 更新最近导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        log.info("更新最近导入的合规性评价记录关联ID: {}", relatedId);
        
        // 方法一：直接使用批量更新SQL
        int updatedCount = securityComplianceEvaluationRecordsMapper.updateLatestImportedRelatedId(relatedId);
        
        if (updatedCount > 0) {
            log.info("成功更新合规性评价记录关联ID，共更新{}条记录", updatedCount);
        } else {
            log.info("没有找到需要更新关联ID的合规性评价记录");
            
            // 方法二：如果方法一未更新任何记录，则尝试获取最近导入的记录并逐个更新
            List<SecurityComplianceEvaluationRecords> recentRecords = 
                securityComplianceEvaluationRecordsMapper.selectLatestImportedRecords();
            
            if (recentRecords != null && !recentRecords.isEmpty()) {
                for (SecurityComplianceEvaluationRecords record : recentRecords) {
                    record.setRelatedId(relatedId);
                    updatedCount += securityComplianceEvaluationRecordsMapper.updateSecurityComplianceEvaluationRecords(record);
                }
                log.info("使用方法二成功更新合规性评价记录关联ID，共更新{}条记录", updatedCount);
            }
        }
        
        return updatedCount;
    }
}
