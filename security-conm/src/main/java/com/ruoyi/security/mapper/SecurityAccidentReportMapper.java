package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityAccidentReport;

/**
 * 事故处理报告Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecurityAccidentReportMapper 
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
     * 删除事故处理报告
     * 
     * @param id 事故处理报告主键
     * @return 结果
     */
    public int deleteSecurityAccidentReportById(Long id);

    /**
     * 批量删除事故处理报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityAccidentReportByIds(Long[] ids);
}
