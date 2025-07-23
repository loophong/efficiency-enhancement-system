package com.ruoyi.security.service;

import java.io.InputStream;
import java.util.List;
import com.ruoyi.security.domain.SecurityKnowledgeAssessment;

/**
 * 安全知识考核Service接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface ISecurityKnowledgeAssessmentService 
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
     * 批量删除安全知识考核
     * 
     * @param ids 需要删除的安全知识考核主键集合
     * @return 结果
     */
    public int deleteSecurityKnowledgeAssessmentByIds(Long[] ids);

    /**
     * 删除安全知识考核信息
     *
     * @param id 安全知识考核主键
     * @return 结果
     */
    public int deleteSecurityKnowledgeAssessmentById(Long id);

    /**
     * 导入安全知识考核数据
     *
     * @param inputStream 文件输入流
     * @param updateSupport 是否更新已存在数据
     * @return 导入结果信息
     */
    public String importData(InputStream inputStream, boolean updateSupport) throws Exception;

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);

    /**
     * 根据关联ID查询安全知识考核列表
     *
     * @param relatedId 关联ID
     * @return 安全知识考核集合
     */
    public List<SecurityKnowledgeAssessment> selectSecurityKnowledgeAssessmentByRelatedId(Long relatedId);
}
