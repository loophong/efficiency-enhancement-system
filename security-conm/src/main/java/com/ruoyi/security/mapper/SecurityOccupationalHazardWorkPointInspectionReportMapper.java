package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityOccupationalHazardWorkPointInspectionReport;

/**
 * 职业危害作业点检测报告Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityOccupationalHazardWorkPointInspectionReportMapper 
{
    /**
     * 查询职业危害作业点检测报告
     * 
     * @param id 职业危害作业点检测报告主键
     * @return 职业危害作业点检测报告
     */
    public SecurityOccupationalHazardWorkPointInspectionReport selectSecurityOccupationalHazardWorkPointInspectionReportById(String id);

    /**
     * 查询职业危害作业点检测报告列表
     * 
     * @param securityOccupationalHazardWorkPointInspectionReport 职业危害作业点检测报告
     * @return 职业危害作业点检测报告集合
     */
    public List<SecurityOccupationalHazardWorkPointInspectionReport> selectSecurityOccupationalHazardWorkPointInspectionReportList(SecurityOccupationalHazardWorkPointInspectionReport securityOccupationalHazardWorkPointInspectionReport);

    /**
     * 新增职业危害作业点检测报告
     * 
     * @param securityOccupationalHazardWorkPointInspectionReport 职业危害作业点检测报告
     * @return 结果
     */
    public int insertSecurityOccupationalHazardWorkPointInspectionReport(SecurityOccupationalHazardWorkPointInspectionReport securityOccupationalHazardWorkPointInspectionReport);

    /**
     * 修改职业危害作业点检测报告
     * 
     * @param securityOccupationalHazardWorkPointInspectionReport 职业危害作业点检测报告
     * @return 结果
     */
    public int updateSecurityOccupationalHazardWorkPointInspectionReport(SecurityOccupationalHazardWorkPointInspectionReport securityOccupationalHazardWorkPointInspectionReport);

    /**
     * 删除职业危害作业点检测报告
     * 
     * @param id 职业危害作业点检测报告主键
     * @return 结果
     */
    public int deleteSecurityOccupationalHazardWorkPointInspectionReportById(String id);

    /**
     * 批量删除职业危害作业点检测报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityOccupationalHazardWorkPointInspectionReportByIds(String[] ids);
}
