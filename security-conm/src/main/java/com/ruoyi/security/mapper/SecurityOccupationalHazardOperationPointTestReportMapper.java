package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityOccupationalHazardOperationPointTestReport;

/**
 * 职业危害作业点检测报告Mapper接口
 * 
 * @author wang
 * @date 2025-03-11
 */
public interface SecurityOccupationalHazardOperationPointTestReportMapper 
{
    /**
     * 查询职业危害作业点检测报告
     * 
     * @param id 职业危害作业点检测报告主键
     * @return 职业危害作业点检测报告
     */
    public SecurityOccupationalHazardOperationPointTestReport selectSecurityOccupationalHazardOperationPointTestReportById(String id);

    /**
     * 查询职业危害作业点检测报告列表
     * 
     * @param securityOccupationalHazardOperationPointTestReport 职业危害作业点检测报告
     * @return 职业危害作业点检测报告集合
     */
    public List<SecurityOccupationalHazardOperationPointTestReport> selectSecurityOccupationalHazardOperationPointTestReportList(SecurityOccupationalHazardOperationPointTestReport securityOccupationalHazardOperationPointTestReport);

    /**
     * 新增职业危害作业点检测报告
     * 
     * @param securityOccupationalHazardOperationPointTestReport 职业危害作业点检测报告
     * @return 结果
     */
    public int insertSecurityOccupationalHazardOperationPointTestReport(SecurityOccupationalHazardOperationPointTestReport securityOccupationalHazardOperationPointTestReport);

    /**
     * 修改职业危害作业点检测报告
     * 
     * @param securityOccupationalHazardOperationPointTestReport 职业危害作业点检测报告
     * @return 结果
     */
    public int updateSecurityOccupationalHazardOperationPointTestReport(SecurityOccupationalHazardOperationPointTestReport securityOccupationalHazardOperationPointTestReport);

    /**
     * 删除职业危害作业点检测报告
     * 
     * @param id 职业危害作业点检测报告主键
     * @return 结果
     */
    public int deleteSecurityOccupationalHazardOperationPointTestReportById(String id);

    /**
     * 批量删除职业危害作业点检测报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityOccupationalHazardOperationPointTestReportByIds(String[] ids);
}
