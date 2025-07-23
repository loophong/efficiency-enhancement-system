package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityOccupationalHazardWorkPointInspectionReportMapper;
import com.ruoyi.security.domain.SecurityOccupationalHazardWorkPointInspectionReport;
import com.ruoyi.security.service.ISecurityOccupationalHazardWorkPointInspectionReportService;

/**
 * 职业危害作业点检测报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityOccupationalHazardWorkPointInspectionReportServiceImpl implements ISecurityOccupationalHazardWorkPointInspectionReportService 
{
    @Autowired
    private SecurityOccupationalHazardWorkPointInspectionReportMapper securityOccupationalHazardWorkPointInspectionReportMapper;

    /**
     * 查询职业危害作业点检测报告
     * 
     * @param id 职业危害作业点检测报告主键
     * @return 职业危害作业点检测报告
     */
    @Override
    public SecurityOccupationalHazardWorkPointInspectionReport selectSecurityOccupationalHazardWorkPointInspectionReportById(String id)
    {
        return securityOccupationalHazardWorkPointInspectionReportMapper.selectSecurityOccupationalHazardWorkPointInspectionReportById(id);
    }

    /**
     * 查询职业危害作业点检测报告列表
     * 
     * @param securityOccupationalHazardWorkPointInspectionReport 职业危害作业点检测报告
     * @return 职业危害作业点检测报告
     */
    @Override
    public List<SecurityOccupationalHazardWorkPointInspectionReport> selectSecurityOccupationalHazardWorkPointInspectionReportList(SecurityOccupationalHazardWorkPointInspectionReport securityOccupationalHazardWorkPointInspectionReport)
    {
        return securityOccupationalHazardWorkPointInspectionReportMapper.selectSecurityOccupationalHazardWorkPointInspectionReportList(securityOccupationalHazardWorkPointInspectionReport);
    }

    /**
     * 新增职业危害作业点检测报告
     * 
     * @param securityOccupationalHazardWorkPointInspectionReport 职业危害作业点检测报告
     * @return 结果
     */
    @Override
    public int insertSecurityOccupationalHazardWorkPointInspectionReport(SecurityOccupationalHazardWorkPointInspectionReport securityOccupationalHazardWorkPointInspectionReport)
    {
        return securityOccupationalHazardWorkPointInspectionReportMapper.insertSecurityOccupationalHazardWorkPointInspectionReport(securityOccupationalHazardWorkPointInspectionReport);
    }

    /**
     * 修改职业危害作业点检测报告
     * 
     * @param securityOccupationalHazardWorkPointInspectionReport 职业危害作业点检测报告
     * @return 结果
     */
    @Override
    public int updateSecurityOccupationalHazardWorkPointInspectionReport(SecurityOccupationalHazardWorkPointInspectionReport securityOccupationalHazardWorkPointInspectionReport)
    {
        return securityOccupationalHazardWorkPointInspectionReportMapper.updateSecurityOccupationalHazardWorkPointInspectionReport(securityOccupationalHazardWorkPointInspectionReport);
    }

    /**
     * 批量删除职业危害作业点检测报告
     * 
     * @param ids 需要删除的职业危害作业点检测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHazardWorkPointInspectionReportByIds(String[] ids)
    {
        return securityOccupationalHazardWorkPointInspectionReportMapper.deleteSecurityOccupationalHazardWorkPointInspectionReportByIds(ids);
    }

    /**
     * 删除职业危害作业点检测报告信息
     * 
     * @param id 职业危害作业点检测报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHazardWorkPointInspectionReportById(String id)
    {
        return securityOccupationalHazardWorkPointInspectionReportMapper.deleteSecurityOccupationalHazardWorkPointInspectionReportById(id);
    }
}
