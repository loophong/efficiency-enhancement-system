package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityMonitoringAlarmDeviceTestReportMapper;
import com.ruoyi.security.domain.SecurityMonitoringAlarmDeviceTestReport;
import com.ruoyi.security.service.ISecurityMonitoringAlarmDeviceTestReportService;

/**
 * 监测报警装置检测报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityMonitoringAlarmDeviceTestReportServiceImpl implements ISecurityMonitoringAlarmDeviceTestReportService 
{
    @Autowired
    private SecurityMonitoringAlarmDeviceTestReportMapper securityMonitoringAlarmDeviceTestReportMapper;

    /**
     * 查询监测报警装置检测报告
     * 
     * @param id 监测报警装置检测报告主键
     * @return 监测报警装置检测报告
     */
    @Override
    public SecurityMonitoringAlarmDeviceTestReport selectSecurityMonitoringAlarmDeviceTestReportById(String id)
    {
        return securityMonitoringAlarmDeviceTestReportMapper.selectSecurityMonitoringAlarmDeviceTestReportById(id);
    }

    /**
     * 查询监测报警装置检测报告列表
     * 
     * @param securityMonitoringAlarmDeviceTestReport 监测报警装置检测报告
     * @return 监测报警装置检测报告
     */
    @Override
    public List<SecurityMonitoringAlarmDeviceTestReport> selectSecurityMonitoringAlarmDeviceTestReportList(SecurityMonitoringAlarmDeviceTestReport securityMonitoringAlarmDeviceTestReport)
    {
        return securityMonitoringAlarmDeviceTestReportMapper.selectSecurityMonitoringAlarmDeviceTestReportList(securityMonitoringAlarmDeviceTestReport);
    }

    /**
     * 新增监测报警装置检测报告
     * 
     * @param securityMonitoringAlarmDeviceTestReport 监测报警装置检测报告
     * @return 结果
     */
    @Override
    public int insertSecurityMonitoringAlarmDeviceTestReport(SecurityMonitoringAlarmDeviceTestReport securityMonitoringAlarmDeviceTestReport)
    {
        return securityMonitoringAlarmDeviceTestReportMapper.insertSecurityMonitoringAlarmDeviceTestReport(securityMonitoringAlarmDeviceTestReport);
    }

    /**
     * 修改监测报警装置检测报告
     * 
     * @param securityMonitoringAlarmDeviceTestReport 监测报警装置检测报告
     * @return 结果
     */
    @Override
    public int updateSecurityMonitoringAlarmDeviceTestReport(SecurityMonitoringAlarmDeviceTestReport securityMonitoringAlarmDeviceTestReport)
    {
        return securityMonitoringAlarmDeviceTestReportMapper.updateSecurityMonitoringAlarmDeviceTestReport(securityMonitoringAlarmDeviceTestReport);
    }

    /**
     * 批量删除监测报警装置检测报告
     * 
     * @param ids 需要删除的监测报警装置检测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityMonitoringAlarmDeviceTestReportByIds(String[] ids)
    {
        return securityMonitoringAlarmDeviceTestReportMapper.deleteSecurityMonitoringAlarmDeviceTestReportByIds(ids);
    }

    /**
     * 删除监测报警装置检测报告信息
     * 
     * @param id 监测报警装置检测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityMonitoringAlarmDeviceTestReportById(String id)
    {
        return securityMonitoringAlarmDeviceTestReportMapper.deleteSecurityMonitoringAlarmDeviceTestReportById(id);
    }
}
