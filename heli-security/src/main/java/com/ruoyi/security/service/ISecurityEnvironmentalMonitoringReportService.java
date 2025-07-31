package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalMonitoringReport;

/**
 * 环境监测报告Service接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface ISecurityEnvironmentalMonitoringReportService 
{
    /**
     * 查询环境监测报告
     * 
     * @param id 环境监测报告主键
     * @return 环境监测报告
     */
    public SecurityEnvironmentalMonitoringReport selectSecurityEnvironmentalMonitoringReportById(String id);

    /**
     * 查询环境监测报告列表
     * 
     * @param securityEnvironmentalMonitoringReport 环境监测报告
     * @return 环境监测报告集合
     */
    public List<SecurityEnvironmentalMonitoringReport> selectSecurityEnvironmentalMonitoringReportList(SecurityEnvironmentalMonitoringReport securityEnvironmentalMonitoringReport);

    /**
     * 新增环境监测报告
     * 
     * @param securityEnvironmentalMonitoringReport 环境监测报告
     * @return 结果
     */
    public int insertSecurityEnvironmentalMonitoringReport(SecurityEnvironmentalMonitoringReport securityEnvironmentalMonitoringReport);

    /**
     * 修改环境监测报告
     * 
     * @param securityEnvironmentalMonitoringReport 环境监测报告
     * @return 结果
     */
    public int updateSecurityEnvironmentalMonitoringReport(SecurityEnvironmentalMonitoringReport securityEnvironmentalMonitoringReport);

    /**
     * 批量删除环境监测报告
     * 
     * @param ids 需要删除的环境监测报告主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalMonitoringReportByIds(String[] ids);

    /**
     * 删除环境监测报告信息
     * 
     * @param id 环境监测报告主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalMonitoringReportById(String id);
}
