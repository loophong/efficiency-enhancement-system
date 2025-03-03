package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityKnowledgeAssessmentMapper;
import com.ruoyi.security.domain.SecurityKnowledgeAssessment;
import com.ruoyi.security.service.ISecurityKnowledgeAssessmentService;

/**
 * 安全知识考核Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Service
public class SecurityKnowledgeAssessmentServiceImpl implements ISecurityKnowledgeAssessmentService 
{
    @Autowired
    private SecurityKnowledgeAssessmentMapper securityKnowledgeAssessmentMapper;

    /**
     * 查询安全知识考核
     * 
     * @param id 安全知识考核主键
     * @return 安全知识考核
     */
    @Override
    public SecurityKnowledgeAssessment selectSecurityKnowledgeAssessmentById(Long id)
    {
        return securityKnowledgeAssessmentMapper.selectSecurityKnowledgeAssessmentById(id);
    }

    /**
     * 查询安全知识考核列表
     * 
     * @param securityKnowledgeAssessment 安全知识考核
     * @return 安全知识考核
     */
    @Override
    public List<SecurityKnowledgeAssessment> selectSecurityKnowledgeAssessmentList(SecurityKnowledgeAssessment securityKnowledgeAssessment)
    {
        return securityKnowledgeAssessmentMapper.selectSecurityKnowledgeAssessmentList(securityKnowledgeAssessment);
    }

    /**
     * 新增安全知识考核
     * 
     * @param securityKnowledgeAssessment 安全知识考核
     * @return 结果
     */
    @Override
    public int insertSecurityKnowledgeAssessment(SecurityKnowledgeAssessment securityKnowledgeAssessment)
    {
        return securityKnowledgeAssessmentMapper.insertSecurityKnowledgeAssessment(securityKnowledgeAssessment);
    }

    /**
     * 修改安全知识考核
     * 
     * @param securityKnowledgeAssessment 安全知识考核
     * @return 结果
     */
    @Override
    public int updateSecurityKnowledgeAssessment(SecurityKnowledgeAssessment securityKnowledgeAssessment)
    {
        return securityKnowledgeAssessmentMapper.updateSecurityKnowledgeAssessment(securityKnowledgeAssessment);
    }

    /**
     * 批量删除安全知识考核
     * 
     * @param ids 需要删除的安全知识考核主键
     * @return 结果
     */
    @Override
    public int deleteSecurityKnowledgeAssessmentByIds(Long[] ids)
    {
        return securityKnowledgeAssessmentMapper.deleteSecurityKnowledgeAssessmentByIds(ids);
    }

    /**
     * 删除安全知识考核信息
     * 
     * @param id 安全知识考核主键
     * @return 结果
     */
    @Override
    public int deleteSecurityKnowledgeAssessmentById(Long id)
    {
        return securityKnowledgeAssessmentMapper.deleteSecurityKnowledgeAssessmentById(id);
    }
}
