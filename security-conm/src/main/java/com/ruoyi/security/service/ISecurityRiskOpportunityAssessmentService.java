package com.ruoyi.security.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.security.domain.SecurityRiskOpportunityAssessment;

/**
 * 风险和机遇评估及控制措施Service接口
 * 
 * @author wang
 * @date 2025-03-11
 */
public interface ISecurityRiskOpportunityAssessmentService 
{
    /**
     * 查询风险和机遇评估及控制措施
     * 
     * @param id 风险和机遇评估及控制措施主键
     * @return 风险和机遇评估及控制措施
     */
    public SecurityRiskOpportunityAssessment selectSecurityRiskOpportunityAssessmentById(Long id);

    /**
     * 查询风险和机遇评估及控制措施列表
     * 
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 风险和机遇评估及控制措施集合
     */
    public List<SecurityRiskOpportunityAssessment> selectSecurityRiskOpportunityAssessmentList(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment);

    /**
     * 新增风险和机遇评估及控制措施
     * 
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 结果
     */
    public int insertSecurityRiskOpportunityAssessment(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment);

    /**
     * 修改风险和机遇评估及控制措施
     * 
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 结果
     */
    public int updateSecurityRiskOpportunityAssessment(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment);

    /**
     * 批量删除风险和机遇评估及控制措施
     * 
     * @param ids 需要删除的风险和机遇评估及控制措施主键集合
     * @return 结果
     */
    public int deleteSecurityRiskOpportunityAssessmentByIds(Long[] ids);

    /**
     * 删除风险和机遇评估及控制措施信息
     * 
     * @param id 风险和机遇评估及控制措施主键
     * @return 结果
     */
    public int deleteSecurityRiskOpportunityAssessmentById(Long id);
    
    /**
     * 导入风险和机遇评估及控制措施数据
     * 
     * @param file 导入文件
     * @return 结果
     */
    public String importAssessment(MultipartFile file);

    int updateLatestImportedRelatedId(Long fileManagementId);

}
