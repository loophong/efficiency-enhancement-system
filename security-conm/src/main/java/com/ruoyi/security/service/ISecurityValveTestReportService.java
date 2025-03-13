package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityValveTestReport;

/**
 * 安全阀检测报告Service接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface ISecurityValveTestReportService 
{
    /**
     * 查询安全阀检测报告
     * 
     * @param id 安全阀检测报告主键
     * @return 安全阀检测报告
     */
    public SecurityValveTestReport selectSecurityValveTestReportById(String id);

    /**
     * 查询安全阀检测报告列表
     * 
     * @param securityValveTestReport 安全阀检测报告
     * @return 安全阀检测报告集合
     */
    public List<SecurityValveTestReport> selectSecurityValveTestReportList(SecurityValveTestReport securityValveTestReport);

    /**
     * 新增安全阀检测报告
     * 
     * @param securityValveTestReport 安全阀检测报告
     * @return 结果
     */
    public int insertSecurityValveTestReport(SecurityValveTestReport securityValveTestReport);

    /**
     * 修改安全阀检测报告
     * 
     * @param securityValveTestReport 安全阀检测报告
     * @return 结果
     */
    public int updateSecurityValveTestReport(SecurityValveTestReport securityValveTestReport);

    /**
     * 批量删除安全阀检测报告
     * 
     * @param ids 需要删除的安全阀检测报告主键集合
     * @return 结果
     */
    public int deleteSecurityValveTestReportByIds(String[] ids);

    /**
     * 删除安全阀检测报告信息
     * 
     * @param id 安全阀检测报告主键
     * @return 结果
     */
    public int deleteSecurityValveTestReportById(String id);
}
