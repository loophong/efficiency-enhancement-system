package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityPressureGaugeTestReport;

/**
 * 压力检测报告Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityPressureGaugeTestReportMapper 
{
    /**
     * 查询压力检测报告
     * 
     * @param id 压力检测报告主键
     * @return 压力检测报告
     */
    public SecurityPressureGaugeTestReport selectSecurityPressureGaugeTestReportById(String id);

    /**
     * 查询压力检测报告列表
     * 
     * @param securityPressureGaugeTestReport 压力检测报告
     * @return 压力检测报告集合
     */
    public List<SecurityPressureGaugeTestReport> selectSecurityPressureGaugeTestReportList(SecurityPressureGaugeTestReport securityPressureGaugeTestReport);

    /**
     * 新增压力检测报告
     * 
     * @param securityPressureGaugeTestReport 压力检测报告
     * @return 结果
     */
    public int insertSecurityPressureGaugeTestReport(SecurityPressureGaugeTestReport securityPressureGaugeTestReport);

    /**
     * 修改压力检测报告
     * 
     * @param securityPressureGaugeTestReport 压力检测报告
     * @return 结果
     */
    public int updateSecurityPressureGaugeTestReport(SecurityPressureGaugeTestReport securityPressureGaugeTestReport);

    /**
     * 删除压力检测报告
     * 
     * @param id 压力检测报告主键
     * @return 结果
     */
    public int deleteSecurityPressureGaugeTestReportById(String id);

    /**
     * 批量删除压力检测报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityPressureGaugeTestReportByIds(String[] ids);
}
