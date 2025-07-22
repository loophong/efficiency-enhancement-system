package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityAccidentReport;

/**
 * 事故处理报告Service接口
 * 
 * @author wang
 * @date 2025-07-21
 */
public interface ISecurityAccidentReportService 
{
    /**
     * 查询事故处理报告
     * 
     * @param id 事故处理报告主键
     * @return 事故处理报告
     */
    public SecurityAccidentReport selectSecurityAccidentReportById(Long id);

    /**
     * 查询事故处理报告列表
     * 
     * @param securityAccidentReport 事故处理报告
     * @return 事故处理报告集合
     */
    public List<SecurityAccidentReport> selectSecurityAccidentReportList(SecurityAccidentReport securityAccidentReport);

    /**
     * 新增事故处理报告
     * 
     * @param securityAccidentReport 事故处理报告
     * @return 结果
     */
    public int insertSecurityAccidentReport(SecurityAccidentReport securityAccidentReport);

    /**
     * 修改事故处理报告
     * 
     * @param securityAccidentReport 事故处理报告
     * @return 结果
     */
    public int updateSecurityAccidentReport(SecurityAccidentReport securityAccidentReport);

    /**
     * 批量删除事故处理报告
     * 
     * @param ids 需要删除的事故处理报告主键集合
     * @return 结果
     */
    public int deleteSecurityAccidentReportByIds(Long[] ids);

    /**
     * 删除事故处理报告信息
     * 
     * @param id 事故处理报告主键
     * @return 结果
     */
    public int deleteSecurityAccidentReportById(Long id);
}
