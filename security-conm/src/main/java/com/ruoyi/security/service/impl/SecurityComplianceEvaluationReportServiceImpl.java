package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityComplianceEvaluationReportMapper;
import com.ruoyi.security.domain.SecurityComplianceEvaluationReport;
import com.ruoyi.security.service.ISecurityComplianceEvaluationReportService;

/**
 * 合规性评价报告Service业务层处理
 * 
 * @author wang
 * @date 2025-07-22
 */
@Service
public class SecurityComplianceEvaluationReportServiceImpl implements ISecurityComplianceEvaluationReportService 
{
    @Autowired
    private SecurityComplianceEvaluationReportMapper securityComplianceEvaluationReportMapper;

    /**
     * 查询合规性评价报告
     * 
     * @param id 合规性评价报告主键
     * @return 合规性评价报告
     */
    @Override
    public SecurityComplianceEvaluationReport selectSecurityComplianceEvaluationReportById(String id)
    {
        return securityComplianceEvaluationReportMapper.selectSecurityComplianceEvaluationReportById(id);
    }

    /**
     * 查询合规性评价报告列表
     * 
     * @param securityComplianceEvaluationReport 合规性评价报告
     * @return 合规性评价报告
     */
    @Override
    public List<SecurityComplianceEvaluationReport> selectSecurityComplianceEvaluationReportList(SecurityComplianceEvaluationReport securityComplianceEvaluationReport)
    {
        return securityComplianceEvaluationReportMapper.selectSecurityComplianceEvaluationReportList(securityComplianceEvaluationReport);
    }

    /**
     * 新增合规性评价报告
     * 
     * @param securityComplianceEvaluationReport 合规性评价报告
     * @return 结果
     */
    @Override
    public int insertSecurityComplianceEvaluationReport(SecurityComplianceEvaluationReport securityComplianceEvaluationReport)
    {
        return securityComplianceEvaluationReportMapper.insertSecurityComplianceEvaluationReport(securityComplianceEvaluationReport);
    }

    /**
     * 修改合规性评价报告
     * 
     * @param securityComplianceEvaluationReport 合规性评价报告
     * @return 结果
     */
    @Override
    public int updateSecurityComplianceEvaluationReport(SecurityComplianceEvaluationReport securityComplianceEvaluationReport)
    {
        return securityComplianceEvaluationReportMapper.updateSecurityComplianceEvaluationReport(securityComplianceEvaluationReport);
    }

    /**
     * 批量删除合规性评价报告
     * 
     * @param ids 需要删除的合规性评价报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityComplianceEvaluationReportByIds(String[] ids)
    {
        return securityComplianceEvaluationReportMapper.deleteSecurityComplianceEvaluationReportByIds(ids);
    }

    /**
     * 删除合规性评价报告信息
     * 
     * @param id 合规性评价报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityComplianceEvaluationReportById(String id)
    {
        return securityComplianceEvaluationReportMapper.deleteSecurityComplianceEvaluationReportById(id);
    }
}
