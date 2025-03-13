package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOccupationalHealthManagementReportMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthManagementReport;
import com.ruoyi.security.service.ISecurityEnvironmentalOccupationalHealthManagementReportService;

/**
 * 环境职业健康安全管理评审报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityEnvironmentalOccupationalHealthManagementReportServiceImpl implements ISecurityEnvironmentalOccupationalHealthManagementReportService 
{
    @Autowired
    private SecurityEnvironmentalOccupationalHealthManagementReportMapper securityEnvironmentalOccupationalHealthManagementReportMapper;

    /**
     * 查询环境职业健康安全管理评审报告
     * 
     * @param id 环境职业健康安全管理评审报告主键
     * @return 环境职业健康安全管理评审报告
     */
    @Override
    public SecurityEnvironmentalOccupationalHealthManagementReport selectSecurityEnvironmentalOccupationalHealthManagementReportById(String id)
    {
        return securityEnvironmentalOccupationalHealthManagementReportMapper.selectSecurityEnvironmentalOccupationalHealthManagementReportById(id);
    }

    /**
     * 查询环境职业健康安全管理评审报告列表
     * 
     * @param securityEnvironmentalOccupationalHealthManagementReport 环境职业健康安全管理评审报告
     * @return 环境职业健康安全管理评审报告
     */
    @Override
    public List<SecurityEnvironmentalOccupationalHealthManagementReport> selectSecurityEnvironmentalOccupationalHealthManagementReportList(SecurityEnvironmentalOccupationalHealthManagementReport securityEnvironmentalOccupationalHealthManagementReport)
    {
        return securityEnvironmentalOccupationalHealthManagementReportMapper.selectSecurityEnvironmentalOccupationalHealthManagementReportList(securityEnvironmentalOccupationalHealthManagementReport);
    }

    /**
     * 新增环境职业健康安全管理评审报告
     * 
     * @param securityEnvironmentalOccupationalHealthManagementReport 环境职业健康安全管理评审报告
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOccupationalHealthManagementReport(SecurityEnvironmentalOccupationalHealthManagementReport securityEnvironmentalOccupationalHealthManagementReport)
    {
        return securityEnvironmentalOccupationalHealthManagementReportMapper.insertSecurityEnvironmentalOccupationalHealthManagementReport(securityEnvironmentalOccupationalHealthManagementReport);
    }

    /**
     * 修改环境职业健康安全管理评审报告
     * 
     * @param securityEnvironmentalOccupationalHealthManagementReport 环境职业健康安全管理评审报告
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOccupationalHealthManagementReport(SecurityEnvironmentalOccupationalHealthManagementReport securityEnvironmentalOccupationalHealthManagementReport)
    {
        return securityEnvironmentalOccupationalHealthManagementReportMapper.updateSecurityEnvironmentalOccupationalHealthManagementReport(securityEnvironmentalOccupationalHealthManagementReport);
    }

    /**
     * 批量删除环境职业健康安全管理评审报告
     * 
     * @param ids 需要删除的环境职业健康安全管理评审报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOccupationalHealthManagementReportByIds(String[] ids)
    {
        return securityEnvironmentalOccupationalHealthManagementReportMapper.deleteSecurityEnvironmentalOccupationalHealthManagementReportByIds(ids);
    }

    /**
     * 删除环境职业健康安全管理评审报告信息
     * 
     * @param id 环境职业健康安全管理评审报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOccupationalHealthManagementReportById(String id)
    {
        return securityEnvironmentalOccupationalHealthManagementReportMapper.deleteSecurityEnvironmentalOccupationalHealthManagementReportById(id);
    }
}
