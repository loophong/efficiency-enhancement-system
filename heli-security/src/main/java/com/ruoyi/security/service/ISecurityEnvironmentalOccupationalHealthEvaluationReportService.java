package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthEvaluationReport;

/**
 * 环境职业健康安全绩效评价报告Service接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface ISecurityEnvironmentalOccupationalHealthEvaluationReportService 
{
    /**
     * 查询环境职业健康安全绩效评价报告
     * 
     * @param id 环境职业健康安全绩效评价报告主键
     * @return 环境职业健康安全绩效评价报告
     */
    public SecurityEnvironmentalOccupationalHealthEvaluationReport selectSecurityEnvironmentalOccupationalHealthEvaluationReportById(String id);

    /**
     * 查询环境职业健康安全绩效评价报告列表
     * 
     * @param securityEnvironmentalOccupationalHealthEvaluationReport 环境职业健康安全绩效评价报告
     * @return 环境职业健康安全绩效评价报告集合
     */
    public List<SecurityEnvironmentalOccupationalHealthEvaluationReport> selectSecurityEnvironmentalOccupationalHealthEvaluationReportList(SecurityEnvironmentalOccupationalHealthEvaluationReport securityEnvironmentalOccupationalHealthEvaluationReport);

    /**
     * 新增环境职业健康安全绩效评价报告
     * 
     * @param securityEnvironmentalOccupationalHealthEvaluationReport 环境职业健康安全绩效评价报告
     * @return 结果
     */
    public int insertSecurityEnvironmentalOccupationalHealthEvaluationReport(SecurityEnvironmentalOccupationalHealthEvaluationReport securityEnvironmentalOccupationalHealthEvaluationReport);

    /**
     * 修改环境职业健康安全绩效评价报告
     * 
     * @param securityEnvironmentalOccupationalHealthEvaluationReport 环境职业健康安全绩效评价报告
     * @return 结果
     */
    public int updateSecurityEnvironmentalOccupationalHealthEvaluationReport(SecurityEnvironmentalOccupationalHealthEvaluationReport securityEnvironmentalOccupationalHealthEvaluationReport);

    /**
     * 批量删除环境职业健康安全绩效评价报告
     * 
     * @param ids 需要删除的环境职业健康安全绩效评价报告主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthEvaluationReportByIds(String[] ids);

    /**
     * 删除环境职业健康安全绩效评价报告信息
     * 
     * @param id 环境职业健康安全绩效评价报告主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthEvaluationReportById(String id);
}
