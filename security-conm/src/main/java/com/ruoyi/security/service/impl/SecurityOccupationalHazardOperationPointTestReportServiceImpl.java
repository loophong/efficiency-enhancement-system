package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityOccupationalHazardOperationPointTestReportMapper;
import com.ruoyi.security.domain.SecurityOccupationalHazardOperationPointTestReport;
import com.ruoyi.security.service.ISecurityOccupationalHazardOperationPointTestReportService;

/**
 * 职业危害作业点检测报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-11
 */
@Service
public class SecurityOccupationalHazardOperationPointTestReportServiceImpl implements ISecurityOccupationalHazardOperationPointTestReportService 
{
    @Autowired
    private SecurityOccupationalHazardOperationPointTestReportMapper securityOccupationalHazardOperationPointTestReportMapper;

    /**
     * 查询职业危害作业点检测报告
     * 
     * @param id 职业危害作业点检测报告主键
     * @return 职业危害作业点检测报告
     */
    @Override
    public SecurityOccupationalHazardOperationPointTestReport selectSecurityOccupationalHazardOperationPointTestReportById(String id)
    {
        return securityOccupationalHazardOperationPointTestReportMapper.selectSecurityOccupationalHazardOperationPointTestReportById(id);
    }

    /**
     * 查询职业危害作业点检测报告列表
     * 
     * @param securityOccupationalHazardOperationPointTestReport 职业危害作业点检测报告
     * @return 职业危害作业点检测报告
     */
    @Override
    public List<SecurityOccupationalHazardOperationPointTestReport> selectSecurityOccupationalHazardOperationPointTestReportList(SecurityOccupationalHazardOperationPointTestReport securityOccupationalHazardOperationPointTestReport)
    {
        return securityOccupationalHazardOperationPointTestReportMapper.selectSecurityOccupationalHazardOperationPointTestReportList(securityOccupationalHazardOperationPointTestReport);
    }

    /**
     * 新增职业危害作业点检测报告
     * 
     * @param securityOccupationalHazardOperationPointTestReport 职业危害作业点检测报告
     * @return 结果
     */
    @Override
    public int insertSecurityOccupationalHazardOperationPointTestReport(SecurityOccupationalHazardOperationPointTestReport securityOccupationalHazardOperationPointTestReport)
    {
        return securityOccupationalHazardOperationPointTestReportMapper.insertSecurityOccupationalHazardOperationPointTestReport(securityOccupationalHazardOperationPointTestReport);
    }

    /**
     * 修改职业危害作业点检测报告
     * 
     * @param securityOccupationalHazardOperationPointTestReport 职业危害作业点检测报告
     * @return 结果
     */
    @Override
    public int updateSecurityOccupationalHazardOperationPointTestReport(SecurityOccupationalHazardOperationPointTestReport securityOccupationalHazardOperationPointTestReport)
    {
        return securityOccupationalHazardOperationPointTestReportMapper.updateSecurityOccupationalHazardOperationPointTestReport(securityOccupationalHazardOperationPointTestReport);
    }

    /**
     * 批量删除职业危害作业点检测报告
     * 
     * @param ids 需要删除的职业危害作业点检测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHazardOperationPointTestReportByIds(String[] ids)
    {
        return securityOccupationalHazardOperationPointTestReportMapper.deleteSecurityOccupationalHazardOperationPointTestReportByIds(ids);
    }

    /**
     * 删除职业危害作业点检测报告信息
     * 
     * @param id 职业危害作业点检测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHazardOperationPointTestReportById(String id)
    {
        return securityOccupationalHazardOperationPointTestReportMapper.deleteSecurityOccupationalHazardOperationPointTestReportById(id);
    }
}
