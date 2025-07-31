package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityMonitoringAlarmDeviceTestReport;

/**
 * 监测报警装置检测报告Service接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface ISecurityMonitoringAlarmDeviceTestReportService 
{
    /**
     * 查询监测报警装置检测报告
     * 
     * @param id 监测报警装置检测报告主键
     * @return 监测报警装置检测报告
     */
    public SecurityMonitoringAlarmDeviceTestReport selectSecurityMonitoringAlarmDeviceTestReportById(String id);

    /**
     * 查询监测报警装置检测报告列表
     * 
     * @param securityMonitoringAlarmDeviceTestReport 监测报警装置检测报告
     * @return 监测报警装置检测报告集合
     */
    public List<SecurityMonitoringAlarmDeviceTestReport> selectSecurityMonitoringAlarmDeviceTestReportList(SecurityMonitoringAlarmDeviceTestReport securityMonitoringAlarmDeviceTestReport);

    /**
     * 新增监测报警装置检测报告
     * 
     * @param securityMonitoringAlarmDeviceTestReport 监测报警装置检测报告
     * @return 结果
     */
    public int insertSecurityMonitoringAlarmDeviceTestReport(SecurityMonitoringAlarmDeviceTestReport securityMonitoringAlarmDeviceTestReport);

    /**
     * 修改监测报警装置检测报告
     * 
     * @param securityMonitoringAlarmDeviceTestReport 监测报警装置检测报告
     * @return 结果
     */
    public int updateSecurityMonitoringAlarmDeviceTestReport(SecurityMonitoringAlarmDeviceTestReport securityMonitoringAlarmDeviceTestReport);

    /**
     * 批量删除监测报警装置检测报告
     * 
     * @param ids 需要删除的监测报警装置检测报告主键集合
     * @return 结果
     */
    public int deleteSecurityMonitoringAlarmDeviceTestReportByIds(String[] ids);

    /**
     * 删除监测报警装置检测报告信息
     * 
     * @param id 监测报警装置检测报告主键
     * @return 结果
     */
    public int deleteSecurityMonitoringAlarmDeviceTestReportById(String id);
}
