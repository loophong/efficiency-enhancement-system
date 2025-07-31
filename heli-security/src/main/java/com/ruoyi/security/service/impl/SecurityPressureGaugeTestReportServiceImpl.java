package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityPressureGaugeTestReportMapper;
import com.ruoyi.security.domain.SecurityPressureGaugeTestReport;
import com.ruoyi.security.service.ISecurityPressureGaugeTestReportService;

/**
 * 压力检测报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityPressureGaugeTestReportServiceImpl implements ISecurityPressureGaugeTestReportService 
{
    @Autowired
    private SecurityPressureGaugeTestReportMapper securityPressureGaugeTestReportMapper;

    /**
     * 查询压力检测报告
     * 
     * @param id 压力检测报告主键
     * @return 压力检测报告
     */
    @Override
    public SecurityPressureGaugeTestReport selectSecurityPressureGaugeTestReportById(String id)
    {
        return securityPressureGaugeTestReportMapper.selectSecurityPressureGaugeTestReportById(id);
    }

    /**
     * 查询压力检测报告列表
     * 
     * @param securityPressureGaugeTestReport 压力检测报告
     * @return 压力检测报告
     */
    @Override
    public List<SecurityPressureGaugeTestReport> selectSecurityPressureGaugeTestReportList(SecurityPressureGaugeTestReport securityPressureGaugeTestReport)
    {
        return securityPressureGaugeTestReportMapper.selectSecurityPressureGaugeTestReportList(securityPressureGaugeTestReport);
    }

    /**
     * 新增压力检测报告
     * 
     * @param securityPressureGaugeTestReport 压力检测报告
     * @return 结果
     */
    @Override
    public int insertSecurityPressureGaugeTestReport(SecurityPressureGaugeTestReport securityPressureGaugeTestReport)
    {
        return securityPressureGaugeTestReportMapper.insertSecurityPressureGaugeTestReport(securityPressureGaugeTestReport);
    }

    /**
     * 修改压力检测报告
     * 
     * @param securityPressureGaugeTestReport 压力检测报告
     * @return 结果
     */
    @Override
    public int updateSecurityPressureGaugeTestReport(SecurityPressureGaugeTestReport securityPressureGaugeTestReport)
    {
        return securityPressureGaugeTestReportMapper.updateSecurityPressureGaugeTestReport(securityPressureGaugeTestReport);
    }

    /**
     * 批量删除压力检测报告
     * 
     * @param ids 需要删除的压力检测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityPressureGaugeTestReportByIds(String[] ids)
    {
        return securityPressureGaugeTestReportMapper.deleteSecurityPressureGaugeTestReportByIds(ids);
    }

    /**
     * 删除压力检测报告信息
     * 
     * @param id 压力检测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityPressureGaugeTestReportById(String id)
    {
        return securityPressureGaugeTestReportMapper.deleteSecurityPressureGaugeTestReportById(id);
    }
}
