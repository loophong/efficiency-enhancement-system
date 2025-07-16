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
     * 根据关联ID查询公司KPI列表
     * 
     * @param relatedId 关联ID
     * @return 公司KPI集合
     */
    public List<SecueityCompanyKpi> selectSecueityCompanyKpiByRelatedId(Long relatedId);

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
    
    /**
     * 根据关联ID删除公司KPI
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    public int deleteSecueityCompanyKpiByRelatedId(Long relatedId);
    
    /**
     * 导入KPI数据
     * 
     * @param kpiList KPI数据列表
     * @param operName 操作人
     * @return 结果
     */
    public String importKpi(List<SecueityCompanyKpi> kpiList, String operName);

    /**
     * 更新最近导入的KPI数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
