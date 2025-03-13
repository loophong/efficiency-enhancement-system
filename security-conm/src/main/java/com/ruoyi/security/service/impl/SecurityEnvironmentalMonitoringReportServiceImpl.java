package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalMonitoringReportMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalMonitoringReport;
import com.ruoyi.security.service.ISecurityEnvironmentalMonitoringReportService;

/**
 * 环境监测报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityEnvironmentalMonitoringReportServiceImpl implements ISecurityEnvironmentalMonitoringReportService 
{
    @Autowired
    private SecurityEnvironmentalMonitoringReportMapper securityEnvironmentalMonitoringReportMapper;

    /**
     * 查询环境监测报告
     * 
     * @param id 环境监测报告主键
     * @return 环境监测报告
     */
    @Override
    public SecurityEnvironmentalMonitoringReport selectSecurityEnvironmentalMonitoringReportById(String id)
    {
        return securityEnvironmentalMonitoringReportMapper.selectSecurityEnvironmentalMonitoringReportById(id);
    }

    /**
     * 查询环境监测报告列表
     * 
     * @param securityEnvironmentalMonitoringReport 环境监测报告
     * @return 环境监测报告
     */
    @Override
    public List<SecurityEnvironmentalMonitoringReport> selectSecurityEnvironmentalMonitoringReportList(SecurityEnvironmentalMonitoringReport securityEnvironmentalMonitoringReport)
    {
        return securityEnvironmentalMonitoringReportMapper.selectSecurityEnvironmentalMonitoringReportList(securityEnvironmentalMonitoringReport);
    }

    /**
     * 新增环境监测报告
     * 
     * @param securityEnvironmentalMonitoringReport 环境监测报告
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalMonitoringReport(SecurityEnvironmentalMonitoringReport securityEnvironmentalMonitoringReport)
    {
        return securityEnvironmentalMonitoringReportMapper.insertSecurityEnvironmentalMonitoringReport(securityEnvironmentalMonitoringReport);
    }

    /**
     * 修改环境监测报告
     * 
     * @param securityEnvironmentalMonitoringReport 环境监测报告
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalMonitoringReport(SecurityEnvironmentalMonitoringReport securityEnvironmentalMonitoringReport)
    {
        return securityEnvironmentalMonitoringReportMapper.updateSecurityEnvironmentalMonitoringReport(securityEnvironmentalMonitoringReport);
    }

    /**
     * 批量删除环境监测报告
     * 
     * @param ids 需要删除的环境监测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalMonitoringReportByIds(String[] ids)
    {
        return securityEnvironmentalMonitoringReportMapper.deleteSecurityEnvironmentalMonitoringReportByIds(ids);
    }

    /**
     * 删除环境监测报告信息
     * 
     * @param id 环境监测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalMonitoringReportById(String id)
    {
        return securityEnvironmentalMonitoringReportMapper.deleteSecurityEnvironmentalMonitoringReportById(id);
    }
}
