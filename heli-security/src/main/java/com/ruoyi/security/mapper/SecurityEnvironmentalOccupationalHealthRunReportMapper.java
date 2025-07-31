package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthRunReport;

/**
 * 环境职业健康安全管理体系运行报告Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityEnvironmentalOccupationalHealthRunReportMapper 
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
     * 删除环境职业健康安全管理体系运行报告
     * 
     * @param id 环境职业健康安全管理体系运行报告主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthRunReportById(String id);

    /**
     * 批量删除环境职业健康安全管理体系运行报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthRunReportByIds(String[] ids);
}
