package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityValveTestReport;

/**
 * 安全阀检测报告Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityValveTestReportMapper 
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
     * 删除安全阀检测报告
     * 
     * @param id 安全阀检测报告主键
     * @return 结果
     */
    public int deleteSecurityValveTestReportById(String id);

    /**
     * 批量删除安全阀检测报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityValveTestReportByIds(String[] ids);
}
