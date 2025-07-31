package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityAcceptanceEvaluationMapper;
import com.ruoyi.security.domain.SecurityAcceptanceEvaluation;
import com.ruoyi.security.service.ISecurityAcceptanceEvaluationService;

/**
 * 安全验收评价Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityAcceptanceEvaluationServiceImpl implements ISecurityAcceptanceEvaluationService 
{
    @Autowired
    private SecurityAcceptanceEvaluationMapper securityAcceptanceEvaluationMapper;

    /**
     * 查询安全验收评价
     * 
     * @param id 安全验收评价主键
     * @return 安全验收评价
     */
    @Override
    public SecurityAcceptanceEvaluation selectSecurityAcceptanceEvaluationById(String id)
    {
        return securityAcceptanceEvaluationMapper.selectSecurityAcceptanceEvaluationById(id);
    }

    /**
     * 查询安全验收评价列表
     * 
     * @param securityAcceptanceEvaluation 安全验收评价
     * @return 安全验收评价
     */
    @Override
    public List<SecurityAcceptanceEvaluation> selectSecurityAcceptanceEvaluationList(SecurityAcceptanceEvaluation securityAcceptanceEvaluation)
    {
        return securityAcceptanceEvaluationMapper.selectSecurityAcceptanceEvaluationList(securityAcceptanceEvaluation);
    }

    /**
     * 新增安全验收评价
     * 
     * @param securityAcceptanceEvaluation 安全验收评价
     * @return 结果
     */
    @Override
    public int insertSecurityAcceptanceEvaluation(SecurityAcceptanceEvaluation securityAcceptanceEvaluation)
    {
        return securityAcceptanceEvaluationMapper.insertSecurityAcceptanceEvaluation(securityAcceptanceEvaluation);
    }

    /**
     * 修改安全验收评价
     * 
     * @param securityAcceptanceEvaluation 安全验收评价
     * @return 结果
     */
    @Override
    public int updateSecurityAcceptanceEvaluation(SecurityAcceptanceEvaluation securityAcceptanceEvaluation)
    {
        return securityAcceptanceEvaluationMapper.updateSecurityAcceptanceEvaluation(securityAcceptanceEvaluation);
    }

    /**
     * 批量删除安全验收评价
     * 
     * @param ids 需要删除的安全验收评价主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAcceptanceEvaluationByIds(String[] ids)
    {
        return securityAcceptanceEvaluationMapper.deleteSecurityAcceptanceEvaluationByIds(ids);
    }

    /**
     * 删除安全验收评价信息
     * 
     * @param id 安全验收评价主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAcceptanceEvaluationById(String id)
    {
        return securityAcceptanceEvaluationMapper.deleteSecurityAcceptanceEvaluationById(id);
    }
}
