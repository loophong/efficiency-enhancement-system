package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityRiskOpportunityAssessment;

/**
 * 风险和机遇评估及控制措施Mapper接口
 *
 * @author wang
 * @date 2025-02-23
 */
public interface SecurityRiskOpportunityAssessmentMapper
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
     * 删除风险和机遇评估及控制措施
     *
     * @param id 风险和机遇评估及控制措施主键
     * @return 结果
     */
    public int deleteSecurityRiskOpportunityAssessmentById(Long id);

    /**
     * 批量删除风险和机遇评估及控制措施
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityRiskOpportunityAssessmentByIds(Long[] ids);
}
