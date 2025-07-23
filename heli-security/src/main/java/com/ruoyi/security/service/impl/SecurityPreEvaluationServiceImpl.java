package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityPreEvaluationMapper;
import com.ruoyi.security.domain.SecurityPreEvaluation;
import com.ruoyi.security.service.ISecurityPreEvaluationService;

/**
 * 安全预评价Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityPreEvaluationServiceImpl implements ISecurityPreEvaluationService 
{
    @Autowired
    private SecurityPreEvaluationMapper securityPreEvaluationMapper;

    /**
     * 查询安全预评价
     * 
     * @param id 安全预评价主键
     * @return 安全预评价
     */
    @Override
    public SecurityPreEvaluation selectSecurityPreEvaluationById(String id)
    {
        return securityPreEvaluationMapper.selectSecurityPreEvaluationById(id);
    }

    /**
     * 查询安全预评价列表
     * 
     * @param securityPreEvaluation 安全预评价
     * @return 安全预评价
     */
    @Override
    public List<SecurityPreEvaluation> selectSecurityPreEvaluationList(SecurityPreEvaluation securityPreEvaluation)
    {
        return securityPreEvaluationMapper.selectSecurityPreEvaluationList(securityPreEvaluation);
    }

    /**
     * 新增安全预评价
     * 
     * @param securityPreEvaluation 安全预评价
     * @return 结果
     */
    @Override
    public int insertSecurityPreEvaluation(SecurityPreEvaluation securityPreEvaluation)
    {
        return securityPreEvaluationMapper.insertSecurityPreEvaluation(securityPreEvaluation);
    }

    /**
     * 修改安全预评价
     * 
     * @param securityPreEvaluation 安全预评价
     * @return 结果
     */
    @Override
    public int updateSecurityPreEvaluation(SecurityPreEvaluation securityPreEvaluation)
    {
        return securityPreEvaluationMapper.updateSecurityPreEvaluation(securityPreEvaluation);
    }

    /**
     * 批量删除安全预评价
     * 
     * @param ids 需要删除的安全预评价主键
     * @return 结果
     */
    @Override
    public int deleteSecurityPreEvaluationByIds(String[] ids)
    {
        return securityPreEvaluationMapper.deleteSecurityPreEvaluationByIds(ids);
    }

    /**
     * 删除安全预评价信息
     * 
     * @param id 安全预评价主键
     * @return 结果
     */
    @Override
    public int deleteSecurityPreEvaluationById(String id)
    {
        return securityPreEvaluationMapper.deleteSecurityPreEvaluationById(id);
    }
}
