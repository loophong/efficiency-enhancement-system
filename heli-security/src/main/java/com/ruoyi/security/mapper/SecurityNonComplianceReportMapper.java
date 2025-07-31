package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityNonComplianceReport;

/**
 * 不符合报告Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityNonComplianceReportMapper 
{
    /**
     * 查询不符合报告
     * 
     * @param id 不符合报告主键
     * @return 不符合报告
     */
    public SecurityNonComplianceReport selectSecurityNonComplianceReportById(String id);

    /**
     * 查询不符合报告列表
     * 
     * @param securityNonComplianceReport 不符合报告
     * @return 不符合报告集合
     */
    public List<SecurityNonComplianceReport> selectSecurityNonComplianceReportList(SecurityNonComplianceReport securityNonComplianceReport);

    /**
     * 新增不符合报告
     * 
     * @param securityNonComplianceReport 不符合报告
     * @return 结果
     */
    public int insertSecurityNonComplianceReport(SecurityNonComplianceReport securityNonComplianceReport);

    /**
     * 修改不符合报告
     * 
     * @param securityNonComplianceReport 不符合报告
     * @return 结果
     */
    public int updateSecurityNonComplianceReport(SecurityNonComplianceReport securityNonComplianceReport);

    /**
     * 删除不符合报告
     * 
     * @param id 不符合报告主键
     * @return 结果
     */
    public int deleteSecurityNonComplianceReportById(String id);

    /**
     * 批量删除不符合报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityNonComplianceReportByIds(String[] ids);
}
