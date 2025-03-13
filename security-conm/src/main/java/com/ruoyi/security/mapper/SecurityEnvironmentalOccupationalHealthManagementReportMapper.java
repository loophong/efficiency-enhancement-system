package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthManagementReport;

/**
 * 环境职业健康安全管理评审报告Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityEnvironmentalOccupationalHealthManagementReportMapper 
{
    /**
     * 查询环境职业健康安全管理评审报告
     * 
     * @param id 环境职业健康安全管理评审报告主键
     * @return 环境职业健康安全管理评审报告
     */
    public SecurityEnvironmentalOccupationalHealthManagementReport selectSecurityEnvironmentalOccupationalHealthManagementReportById(String id);

    /**
     * 查询环境职业健康安全管理评审报告列表
     * 
     * @param securityEnvironmentalOccupationalHealthManagementReport 环境职业健康安全管理评审报告
     * @return 环境职业健康安全管理评审报告集合
     */
    public List<SecurityEnvironmentalOccupationalHealthManagementReport> selectSecurityEnvironmentalOccupationalHealthManagementReportList(SecurityEnvironmentalOccupationalHealthManagementReport securityEnvironmentalOccupationalHealthManagementReport);

    /**
     * 新增环境职业健康安全管理评审报告
     * 
     * @param securityEnvironmentalOccupationalHealthManagementReport 环境职业健康安全管理评审报告
     * @return 结果
     */
    public int insertSecurityEnvironmentalOccupationalHealthManagementReport(SecurityEnvironmentalOccupationalHealthManagementReport securityEnvironmentalOccupationalHealthManagementReport);

    /**
     * 修改环境职业健康安全管理评审报告
     * 
     * @param securityEnvironmentalOccupationalHealthManagementReport 环境职业健康安全管理评审报告
     * @return 结果
     */
    public int updateSecurityEnvironmentalOccupationalHealthManagementReport(SecurityEnvironmentalOccupationalHealthManagementReport securityEnvironmentalOccupationalHealthManagementReport);

    /**
     * 删除环境职业健康安全管理评审报告
     * 
     * @param id 环境职业健康安全管理评审报告主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthManagementReportById(String id);

    /**
     * 批量删除环境职业健康安全管理评审报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOccupationalHealthManagementReportByIds(String[] ids);
}
