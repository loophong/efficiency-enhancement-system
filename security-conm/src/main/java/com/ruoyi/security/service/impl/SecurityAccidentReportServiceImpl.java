package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityAccidentReportMapper;
import com.ruoyi.security.domain.SecurityAccidentReport;
import com.ruoyi.security.service.ISecurityAccidentReportService;

/**
 * 事故处理报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityAccidentReportServiceImpl implements ISecurityAccidentReportService 
{
    @Autowired
    private SecurityAccidentReportMapper securityAccidentReportMapper;

    /**
     * 查询事故处理报告
     * 
     * @param id 事故处理报告主键
     * @return 事故处理报告
     */
    @Override
    public SecurityAccidentReport selectSecurityAccidentReportById(Long id)
    {
        return securityAccidentReportMapper.selectSecurityAccidentReportById(id);
    }

    /**
     * 查询事故处理报告列表
     * 
     * @param securityAccidentReport 事故处理报告
     * @return 事故处理报告
     */
    @Override
    public List<SecurityAccidentReport> selectSecurityAccidentReportList(SecurityAccidentReport securityAccidentReport)
    {
        return securityAccidentReportMapper.selectSecurityAccidentReportList(securityAccidentReport);
    }

    /**
     * 新增事故处理报告
     * 
     * @param securityAccidentReport 事故处理报告
     * @return 结果
     */
    @Override
    public int insertSecurityAccidentReport(SecurityAccidentReport securityAccidentReport)
    {
        return securityAccidentReportMapper.insertSecurityAccidentReport(securityAccidentReport);
    }

    /**
     * 修改事故处理报告
     * 
     * @param securityAccidentReport 事故处理报告
     * @return 结果
     */
    @Override
    public int updateSecurityAccidentReport(SecurityAccidentReport securityAccidentReport)
    {
        return securityAccidentReportMapper.updateSecurityAccidentReport(securityAccidentReport);
    }

    /**
     * 批量删除事故处理报告
     * 
     * @param ids 需要删除的事故处理报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAccidentReportByIds(Long[] ids)
    {
        return securityAccidentReportMapper.deleteSecurityAccidentReportByIds(ids);
    }

    /**
     * 删除事故处理报告信息
     * 
     * @param id 事故处理报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAccidentReportById(Long id)
    {
        return securityAccidentReportMapper.deleteSecurityAccidentReportById(id);
    }
}
