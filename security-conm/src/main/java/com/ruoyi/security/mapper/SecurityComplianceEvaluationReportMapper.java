package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityComplianceEvaluationReport;

/**
 * 合规性评价报告Mapper接口
 * 
 * @author wang
 * @date 2025-07-22
 */
public interface SecurityComplianceEvaluationReportMapper 
{
    /**
     * 查询合规性评价报告
     * 
     * @param id 合规性评价报告主键
     * @return 合规性评价报告
     */
    public SecurityComplianceEvaluationReport selectSecurityComplianceEvaluationReportById(String id);

    /**
     * 查询合规性评价报告列表
     * 
     * @param securityComplianceEvaluationReport 合规性评价报告
     * @return 合规性评价报告集合
     */
    public List<SecurityComplianceEvaluationReport> selectSecurityComplianceEvaluationReportList(SecurityComplianceEvaluationReport securityComplianceEvaluationReport);

    /**
     * 新增合规性评价报告
     * 
     * @param securityComplianceEvaluationReport 合规性评价报告
     * @return 结果
     */
    public int insertSecurityComplianceEvaluationReport(SecurityComplianceEvaluationReport securityComplianceEvaluationReport);

    /**
     * 修改合规性评价报告
     * 
     * @param securityComplianceEvaluationReport 合规性评价报告
     * @return 结果
     */
    public int updateSecurityComplianceEvaluationReport(SecurityComplianceEvaluationReport securityComplianceEvaluationReport);

    /**
     * 删除合规性评价报告
     * 
     * @param id 合规性评价报告主键
     * @return 结果
     */
    public int deleteSecurityComplianceEvaluationReportById(String id);

    /**
     * 批量删除合规性评价报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityComplianceEvaluationReportByIds(String[] ids);
}
