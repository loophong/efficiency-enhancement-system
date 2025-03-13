package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOccupationalHealthEvaluationReportMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthEvaluationReport;
import com.ruoyi.security.service.ISecurityEnvironmentalOccupationalHealthEvaluationReportService;

/**
 * 环境职业健康安全绩效评价报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityEnvironmentalOccupationalHealthEvaluationReportServiceImpl implements ISecurityEnvironmentalOccupationalHealthEvaluationReportService 
{
    @Autowired
    private SecurityEnvironmentalOccupationalHealthEvaluationReportMapper securityEnvironmentalOccupationalHealthEvaluationReportMapper;

    /**
     * 查询环境职业健康安全绩效评价报告
     * 
     * @param id 环境职业健康安全绩效评价报告主键
     * @return 环境职业健康安全绩效评价报告
     */
    @Override
    public SecurityEnvironmentalOccupationalHealthEvaluationReport selectSecurityEnvironmentalOccupationalHealthEvaluationReportById(String id)
    {
        return securityEnvironmentalOccupationalHealthEvaluationReportMapper.selectSecurityEnvironmentalOccupationalHealthEvaluationReportById(id);
    }

    /**
     * 查询环境职业健康安全绩效评价报告列表
     * 
     * @param securityEnvironmentalOccupationalHealthEvaluationReport 环境职业健康安全绩效评价报告
     * @return 环境职业健康安全绩效评价报告
     */
    @Override
    public List<SecurityEnvironmentalOccupationalHealthEvaluationReport> selectSecurityEnvironmentalOccupationalHealthEvaluationReportList(SecurityEnvironmentalOccupationalHealthEvaluationReport securityEnvironmentalOccupationalHealthEvaluationReport)
    {
        return securityEnvironmentalOccupationalHealthEvaluationReportMapper.selectSecurityEnvironmentalOccupationalHealthEvaluationReportList(securityEnvironmentalOccupationalHealthEvaluationReport);
    }

    /**
     * 新增环境职业健康安全绩效评价报告
     * 
     * @param securityEnvironmentalOccupationalHealthEvaluationReport 环境职业健康安全绩效评价报告
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOccupationalHealthEvaluationReport(SecurityEnvironmentalOccupationalHealthEvaluationReport securityEnvironmentalOccupationalHealthEvaluationReport)
    {
        return securityEnvironmentalOccupationalHealthEvaluationReportMapper.insertSecurityEnvironmentalOccupationalHealthEvaluationReport(securityEnvironmentalOccupationalHealthEvaluationReport);
    }

    /**
     * 修改环境职业健康安全绩效评价报告
     * 
     * @param securityEnvironmentalOccupationalHealthEvaluationReport 环境职业健康安全绩效评价报告
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOccupationalHealthEvaluationReport(SecurityEnvironmentalOccupationalHealthEvaluationReport securityEnvironmentalOccupationalHealthEvaluationReport)
    {
        return securityEnvironmentalOccupationalHealthEvaluationReportMapper.updateSecurityEnvironmentalOccupationalHealthEvaluationReport(securityEnvironmentalOccupationalHealthEvaluationReport);
    }

    /**
     * 批量删除环境职业健康安全绩效评价报告
     * 
     * @param ids 需要删除的环境职业健康安全绩效评价报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOccupationalHealthEvaluationReportByIds(String[] ids)
    {
        return securityEnvironmentalOccupationalHealthEvaluationReportMapper.deleteSecurityEnvironmentalOccupationalHealthEvaluationReportByIds(ids);
    }

    /**
     * 删除环境职业健康安全绩效评价报告信息
     * 
     * @param id 环境职业健康安全绩效评价报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOccupationalHealthEvaluationReportById(String id)
    {
        return securityEnvironmentalOccupationalHealthEvaluationReportMapper.deleteSecurityEnvironmentalOccupationalHealthEvaluationReportById(id);
    }
}
