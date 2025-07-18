package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecueityCompanyKpi;

/**
 * 公司KPIMapper接口
 * 
 * @author wang
 * @date 2025-02-27
 */
public interface SecueityCompanyKpiMapper 
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
     * 删除公司KPI
     * 
     * @param id 公司KPI主键
     * @return 结果
     */
    public int deleteSecueityCompanyKpiById(Long id);

    /**
     * 批量删除公司KPI
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecueityCompanyKpiByIds(Long[] ids);

    /**
     * 根据关联ID删除公司KPI
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    public int deleteSecueityCompanyKpiByRelatedId(Long relatedId);

    /**
     * 查询最近导入的没有关联ID的记录
     * 
     * @return 公司KPI集合
     */
    public List<SecueityCompanyKpi> selectLatestImportedRecords();
}
