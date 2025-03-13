package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRiskOpportunityAssessmentMapper;
import com.ruoyi.security.domain.SecurityRiskOpportunityAssessment;
import com.ruoyi.security.service.ISecurityRiskOpportunityAssessmentService;

/**
 * 风险和机遇评估及控制措施Service业务层处理
 * 
 * @author wang
 * @date 2025-03-11
 */
@Service
public class SecurityRiskOpportunityAssessmentServiceImpl implements ISecurityRiskOpportunityAssessmentService 
{
    @Autowired
    private SecurityRiskOpportunityAssessmentMapper securityRiskOpportunityAssessmentMapper;

    /**
     * 查询风险和机遇评估及控制措施
     * 
     * @param id 风险和机遇评估及控制措施主键
     * @return 风险和机遇评估及控制措施
     */
    @Override
    public SecurityRiskOpportunityAssessment selectSecurityRiskOpportunityAssessmentById(Long id)
    {
        return securityRiskOpportunityAssessmentMapper.selectSecurityRiskOpportunityAssessmentById(id);
    }

    /**
     * 查询风险和机遇评估及控制措施列表
     * 
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 风险和机遇评估及控制措施
     */
    @Override
    public List<SecurityRiskOpportunityAssessment> selectSecurityRiskOpportunityAssessmentList(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return securityRiskOpportunityAssessmentMapper.selectSecurityRiskOpportunityAssessmentList(securityRiskOpportunityAssessment);
    }

    /**
     * 新增风险和机遇评估及控制措施
     * 
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 结果
     */
    @Override
    public int insertSecurityRiskOpportunityAssessment(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return securityRiskOpportunityAssessmentMapper.insertSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment);
    }

    /**
     * 修改风险和机遇评估及控制措施
     * 
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 结果
     */
    @Override
    public int updateSecurityRiskOpportunityAssessment(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return securityRiskOpportunityAssessmentMapper.updateSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment);
    }

    /**
     * 批量删除风险和机遇评估及控制措施
     * 
     * @param ids 需要删除的风险和机遇评估及控制措施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRiskOpportunityAssessmentByIds(Long[] ids)
    {
        return securityRiskOpportunityAssessmentMapper.deleteSecurityRiskOpportunityAssessmentByIds(ids);
    }

    /**
     * 删除风险和机遇评估及控制措施信息
     * 
     * @param id 风险和机遇评估及控制措施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRiskOpportunityAssessmentById(Long id)
    {
        return securityRiskOpportunityAssessmentMapper.deleteSecurityRiskOpportunityAssessmentById(id);
    }
}
