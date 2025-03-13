package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityValveTestReportMapper;
import com.ruoyi.security.domain.SecurityValveTestReport;
import com.ruoyi.security.service.ISecurityValveTestReportService;

/**
 * 安全阀检测报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityValveTestReportServiceImpl implements ISecurityValveTestReportService 
{
    @Autowired
    private SecurityValveTestReportMapper securityValveTestReportMapper;

    /**
     * 查询安全阀检测报告
     * 
     * @param id 安全阀检测报告主键
     * @return 安全阀检测报告
     */
    @Override
    public SecurityValveTestReport selectSecurityValveTestReportById(String id)
    {
        return securityValveTestReportMapper.selectSecurityValveTestReportById(id);
    }

    /**
     * 查询安全阀检测报告列表
     * 
     * @param securityValveTestReport 安全阀检测报告
     * @return 安全阀检测报告
     */
    @Override
    public List<SecurityValveTestReport> selectSecurityValveTestReportList(SecurityValveTestReport securityValveTestReport)
    {
        return securityValveTestReportMapper.selectSecurityValveTestReportList(securityValveTestReport);
    }

    /**
     * 新增安全阀检测报告
     * 
     * @param securityValveTestReport 安全阀检测报告
     * @return 结果
     */
    @Override
    public int insertSecurityValveTestReport(SecurityValveTestReport securityValveTestReport)
    {
        return securityValveTestReportMapper.insertSecurityValveTestReport(securityValveTestReport);
    }

    /**
     * 修改安全阀检测报告
     * 
     * @param securityValveTestReport 安全阀检测报告
     * @return 结果
     */
    @Override
    public int updateSecurityValveTestReport(SecurityValveTestReport securityValveTestReport)
    {
        return securityValveTestReportMapper.updateSecurityValveTestReport(securityValveTestReport);
    }

    /**
     * 批量删除安全阀检测报告
     * 
     * @param ids 需要删除的安全阀检测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityValveTestReportByIds(String[] ids)
    {
        return securityValveTestReportMapper.deleteSecurityValveTestReportByIds(ids);
    }

    /**
     * 删除安全阀检测报告信息
     * 
     * @param id 安全阀检测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityValveTestReportById(String id)
    {
        return securityValveTestReportMapper.deleteSecurityValveTestReportById(id);
    }
}
