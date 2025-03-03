package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecueityCompanyKpi;

/**
 * 公司KPIService接口
 * 
 * @author wang
 * @date 2025-02-27
 */
public interface ISecueityCompanyKpiService 
{
    /**
     * 查询公司KPI
     * 
     * @param id 公司KPI主键
     * @return 公司KPI
     */
    public SecueityCompanyKpi selectSecueityCompanyKpiById(Long id);

    /**
     * 查询公司KPI列表
     * 
     * @param secueityCompanyKpi 公司KPI
     * @return 公司KPI集合
     */
    public List<SecueityCompanyKpi> selectSecueityCompanyKpiList(SecueityCompanyKpi secueityCompanyKpi);

    /**
     * 新增公司KPI
     * 
     * @param secueityCompanyKpi 公司KPI
     * @return 结果
     */
    public int insertSecueityCompanyKpi(SecueityCompanyKpi secueityCompanyKpi);

    /**
     * 修改公司KPI
     * 
     * @param secueityCompanyKpi 公司KPI
     * @return 结果
     */
    public int updateSecueityCompanyKpi(SecueityCompanyKpi secueityCompanyKpi);

    /**
     * 批量删除公司KPI
     * 
     * @param ids 需要删除的公司KPI主键集合
     * @return 结果
     */
    public int deleteSecueityCompanyKpiByIds(Long[] ids);

    /**
     * 删除公司KPI信息
     * 
     * @param id 公司KPI主键
     * @return 结果
     */
    public int deleteSecueityCompanyKpiById(Long id);
}
