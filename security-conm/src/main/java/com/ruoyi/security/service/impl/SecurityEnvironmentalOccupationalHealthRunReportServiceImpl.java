package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOccupationalHealthRunReportMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthRunReport;
import com.ruoyi.security.service.ISecurityEnvironmentalOccupationalHealthRunReportService;

/**
 * 环境职业健康安全管理体系运行报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityEnvironmentalOccupationalHealthRunReportServiceImpl implements ISecurityEnvironmentalOccupationalHealthRunReportService 
{
    @Autowired
    private SecurityEnvironmentalOccupationalHealthRunReportMapper securityEnvironmentalOccupationalHealthRunReportMapper;

    /**
     * 查询环境职业健康安全管理体系运行报告
     * 
     * @param id 环境职业健康安全管理体系运行报告主键
     * @return 环境职业健康安全管理体系运行报告
     */
    @Override
    public SecurityEnvironmentalOccupationalHealthRunReport selectSecurityEnvironmentalOccupationalHealthRunReportById(String id)
    {
        return securityEnvironmentalOccupationalHealthRunReportMapper.selectSecurityEnvironmentalOccupationalHealthRunReportById(id);
    }

    /**
     * 查询环境职业健康安全管理体系运行报告列表
     * 
     * @param securityEnvironmentalOccupationalHealthRunReport 环境职业健康安全管理体系运行报告
     * @return 环境职业健康安全管理体系运行报告
     */
    @Override
    public List<SecurityEnvironmentalOccupationalHealthRunReport> selectSecurityEnvironmentalOccupationalHealthRunReportList(SecurityEnvironmentalOccupationalHealthRunReport securityEnvironmentalOccupationalHealthRunReport)
    {
        return securityEnvironmentalOccupationalHealthRunReportMapper.selectSecurityEnvironmentalOccupationalHealthRunReportList(securityEnvironmentalOccupationalHealthRunReport);
    }

    /**
     * 新增环境职业健康安全管理体系运行报告
     * 
     * @param securityEnvironmentalOccupationalHealthRunReport 环境职业健康安全管理体系运行报告
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOccupationalHealthRunReport(SecurityEnvironmentalOccupationalHealthRunReport securityEnvironmentalOccupationalHealthRunReport)
    {
        return securityEnvironmentalOccupationalHealthRunReportMapper.insertSecurityEnvironmentalOccupationalHealthRunReport(securityEnvironmentalOccupationalHealthRunReport);
    }

    /**
     * 修改环境职业健康安全管理体系运行报告
     * 
     * @param securityEnvironmentalOccupationalHealthRunReport 环境职业健康安全管理体系运行报告
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOccupationalHealthRunReport(SecurityEnvironmentalOccupationalHealthRunReport securityEnvironmentalOccupationalHealthRunReport)
    {
        return securityEnvironmentalOccupationalHealthRunReportMapper.updateSecurityEnvironmentalOccupationalHealthRunReport(securityEnvironmentalOccupationalHealthRunReport);
    }

    /**
     * 批量删除环境职业健康安全管理体系运行报告
     * 
     * @param ids 需要删除的环境职业健康安全管理体系运行报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOccupationalHealthRunReportByIds(String[] ids)
    {
        return securityEnvironmentalOccupationalHealthRunReportMapper.deleteSecurityEnvironmentalOccupationalHealthRunReportByIds(ids);
    }

    /**
     * 删除环境职业健康安全管理体系运行报告信息
     * 
     * @param id 环境职业健康安全管理体系运行报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOccupationalHealthRunReportById(String id)
    {
        return securityEnvironmentalOccupationalHealthRunReportMapper.deleteSecurityEnvironmentalOccupationalHealthRunReportById(id);
    }
}
