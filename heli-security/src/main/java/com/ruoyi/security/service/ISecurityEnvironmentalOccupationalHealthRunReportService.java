package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthRunReport;

/**
 * 环境职业健康安全管理体系运行报告Service接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface ISecurityEnvironmentalOccupationalHealthRunReportService 
{
    /**
     * 查询环境职业健康安全管理体系运行报告
     * 
     * @param id 环境职业健康安全管理体系运行报告主键
     * @return 环境职业健康安全管理体系运行报告
     */
    public SecurityEnvironmentalOccupationalHealthRunReport selectSecurityEnvironmentalOccupationalHealthRunReportById(String id);

    /**
     * 查询环境职业健康安全管理体系运行报告列表
     * 
     * @param securityEnvironmentalOccupationalHealthRunReport 环境职业健康安全管理体系运行报告
     * @return 环境职业健康安全管理体系运行报告集合
     */
    public List<SecurityEnvironmentalOccupationalHealthRunReport> selectSecurityEnvironmentalOccupationalHealthRunReportList(SecurityEnvironmentalOccupationalHealthRunReport securityEnvironmentalOccupationalHealthRunReport);

    /**
     * 新增环境职业健康安全管理体系运行报告
     * 
     * @param securityEnvironmentalOccupationalHealthRunReport 环境职业健康安全管理体系运行报告
     * @return 结果
     */
    public int insertSecurityEnvironmentalOccupationalHealthRunReport(SecurityEnvironmentalOccupationalHealthRunReport securityEnvironmentalOccupationalHealthRunReport);

    /**
     * 修改环境职业健康安全管理体系运行报告
     * 
     * @param securityEnvironmentalOccupationalHealthRunReport 环境职业健康安全管理体系运行报告
     * @return 结果
     */
    public int updateSecurityEnvironmentalOccupationalHealthRunReport(SecurityEnvironmentalOccupationalHealthRunReport securityEnvironmentalOccupationalHealthRunReport);

    /**
     * 批量删除环境职业健康安全管理体系运行报告
     * 
     * @param ids 需要删除的环境职业健康安全管理体系运行报告主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthRunReportByIds(String[] ids);

    /**
     * 删除环境职业健康安全管理体系运行报告信息
     * 
     * @param id 环境职业健康安全管理体系运行报告主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthRunReportById(String id);
}
