package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityNonComplianceReportMapper;
import com.ruoyi.security.domain.SecurityNonComplianceReport;
import com.ruoyi.security.service.ISecurityNonComplianceReportService;

/**
 * 不符合报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityNonComplianceReportServiceImpl implements ISecurityNonComplianceReportService 
{
    @Autowired
    private SecurityNonComplianceReportMapper securityNonComplianceReportMapper;

    /**
     * 查询不符合报告
     * 
     * @param id 不符合报告主键
     * @return 不符合报告
     */
    @Override
    public SecurityNonComplianceReport selectSecurityNonComplianceReportById(String id)
    {
        return securityNonComplianceReportMapper.selectSecurityNonComplianceReportById(id);
    }

    /**
     * 查询不符合报告列表
     * 
     * @param securityNonComplianceReport 不符合报告
     * @return 不符合报告
     */
    @Override
    public List<SecurityNonComplianceReport> selectSecurityNonComplianceReportList(SecurityNonComplianceReport securityNonComplianceReport)
    {
        return securityNonComplianceReportMapper.selectSecurityNonComplianceReportList(securityNonComplianceReport);
    }

    /**
     * 新增不符合报告
     * 
     * @param securityNonComplianceReport 不符合报告
     * @return 结果
     */
    @Override
    public int insertSecurityNonComplianceReport(SecurityNonComplianceReport securityNonComplianceReport)
    {
        return securityNonComplianceReportMapper.insertSecurityNonComplianceReport(securityNonComplianceReport);
    }

    /**
     * 修改不符合报告
     * 
     * @param securityNonComplianceReport 不符合报告
     * @return 结果
     */
    @Override
    public int updateSecurityNonComplianceReport(SecurityNonComplianceReport securityNonComplianceReport)
    {
        return securityNonComplianceReportMapper.updateSecurityNonComplianceReport(securityNonComplianceReport);
    }

    /**
     * 批量删除不符合报告
     * 
     * @param ids 需要删除的不符合报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityNonComplianceReportByIds(String[] ids)
    {
        return securityNonComplianceReportMapper.deleteSecurityNonComplianceReportByIds(ids);
    }

    /**
     * 删除不符合报告信息
     * 
     * @param id 不符合报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityNonComplianceReportById(String id)
    {
        return securityNonComplianceReportMapper.deleteSecurityNonComplianceReportById(id);
    }
}
