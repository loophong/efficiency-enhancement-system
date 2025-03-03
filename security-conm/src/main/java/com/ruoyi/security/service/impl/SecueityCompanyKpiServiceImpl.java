package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecueityCompanyKpiMapper;
import com.ruoyi.security.domain.SecueityCompanyKpi;
import com.ruoyi.security.service.ISecueityCompanyKpiService;

/**
 * 公司KPIService业务层处理
 * 
 * @author wang
 * @date 2025-02-27
 */
@Service
public class SecueityCompanyKpiServiceImpl implements ISecueityCompanyKpiService 
{
    @Autowired
    private SecueityCompanyKpiMapper secueityCompanyKpiMapper;

    /**
     * 查询公司KPI
     * 
     * @param id 公司KPI主键
     * @return 公司KPI
     */
    @Override
    public SecueityCompanyKpi selectSecueityCompanyKpiById(Long id)
    {
        return secueityCompanyKpiMapper.selectSecueityCompanyKpiById(id);
    }

    /**
     * 查询公司KPI列表
     * 
     * @param secueityCompanyKpi 公司KPI
     * @return 公司KPI
     */
    @Override
    public List<SecueityCompanyKpi> selectSecueityCompanyKpiList(SecueityCompanyKpi secueityCompanyKpi)
    {
        return secueityCompanyKpiMapper.selectSecueityCompanyKpiList(secueityCompanyKpi);
    }

    /**
     * 新增公司KPI
     * 
     * @param secueityCompanyKpi 公司KPI
     * @return 结果
     */
    @Override
    public int insertSecueityCompanyKpi(SecueityCompanyKpi secueityCompanyKpi)
    {
        return secueityCompanyKpiMapper.insertSecueityCompanyKpi(secueityCompanyKpi);
    }

    /**
     * 修改公司KPI
     * 
     * @param secueityCompanyKpi 公司KPI
     * @return 结果
     */
    @Override
    public int updateSecueityCompanyKpi(SecueityCompanyKpi secueityCompanyKpi)
    {
        return secueityCompanyKpiMapper.updateSecueityCompanyKpi(secueityCompanyKpi);
    }

    /**
     * 批量删除公司KPI
     * 
     * @param ids 需要删除的公司KPI主键
     * @return 结果
     */
    @Override
    public int deleteSecueityCompanyKpiByIds(Long[] ids)
    {
        return secueityCompanyKpiMapper.deleteSecueityCompanyKpiByIds(ids);
    }

    /**
     * 删除公司KPI信息
     * 
     * @param id 公司KPI主键
     * @return 结果
     */
    @Override
    public int deleteSecueityCompanyKpiById(Long id)
    {
        return secueityCompanyKpiMapper.deleteSecueityCompanyKpiById(id);
    }
}
