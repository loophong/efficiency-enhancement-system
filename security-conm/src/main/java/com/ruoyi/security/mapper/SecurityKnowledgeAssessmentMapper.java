package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityKnowledgeAssessment;

/**
 * 安全知识考核Mapper接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface SecurityKnowledgeAssessmentMapper 
{
    /**
     * 查询安全知识考核
     * 
     * @param id 安全知识考核主键
     * @return 安全知识考核
     */
    public SecurityKnowledgeAssessment selectSecurityKnowledgeAssessmentById(Long id);

    /**
     * 查询安全知识考核列表
     * 
     * @param securityKnowledgeAssessment 安全知识考核
     * @return 安全知识考核集合
     */
    public List<SecurityKnowledgeAssessment> selectSecurityKnowledgeAssessmentList(SecurityKnowledgeAssessment securityKnowledgeAssessment);

    /**
     * 新增安全知识考核
     * 
     * @param securityKnowledgeAssessment 安全知识考核
     * @return 结果
     */
    public int insertSecurityKnowledgeAssessment(SecurityKnowledgeAssessment securityKnowledgeAssessment);

    /**
     * 修改安全知识考核
     * 
     * @param securityKnowledgeAssessment 安全知识考核
     * @return 结果
     */
    public int updateSecurityKnowledgeAssessment(SecurityKnowledgeAssessment securityKnowledgeAssessment);

    /**
     * 删除安全知识考核
     * 
     * @param id 安全知识考核主键
     * @return 结果
     */
    public int deleteSecurityKnowledgeAssessmentById(Long id);

    /**
     * 批量删除安全知识考核
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityKnowledgeAssessmentByIds(Long[] ids);
}
