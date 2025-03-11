package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierRiskMapper;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.service.ISupplierRiskService;

/**
 * 经营风险Service业务层处理
 * 
 * @author wll
 * @date 2025-02-27
 */
@Service
@Slf4j
public class SupplierRiskServiceImpl  extends ServiceImpl<SupplierRiskMapper, SupplierRisk> implements ISupplierRiskService
{
    @Autowired
    private SupplierRiskMapper supplierRiskMapper;

    /**
     * 查询经营风险
     * 
     * @param id 经营风险主键
     * @return 经营风险
     */
    @Override
    public SupplierRisk selectSupplierRiskById(String id)
    {
        return supplierRiskMapper.selectSupplierRiskById(id);
    }

    /**
     * 查询经营风险列表
     * 
     * @param supplierRisk 经营风险
     * @return 经营风险
     */
    @Override
    public List<SupplierRisk> selectSupplierRiskList(SupplierRisk supplierRisk)
    {
        return supplierRiskMapper.selectSupplierRiskList(supplierRisk);
    }

    /**
     * 新增经营风险
     * 
     * @param supplierRisk 经营风险
     * @return 结果
     */
    @Override
    public int insertSupplierRisk(SupplierRisk supplierRisk)
    {
        return supplierRiskMapper.insertSupplierRisk(supplierRisk);
    }

    /**
     * 修改经营风险
     * 
     * @param supplierRisk 经营风险
     * @return 结果
     */
    @Override
    public int updateSupplierRisk(SupplierRisk supplierRisk)
    {
        return supplierRiskMapper.updateSupplierRisk(supplierRisk);
    }

    /**
     * 批量删除经营风险
     * 
     * @param ids 需要删除的经营风险主键
     * @return 结果
     */
    @Override
    public int deleteSupplierRiskByIds(String[] ids)
    {
        return supplierRiskMapper.deleteSupplierRiskByIds(ids);
    }

    /**
     * 删除经营风险信息
     * 
     * @param id 经营风险主键
     * @return 结果
     */
    @Override
    public int deleteSupplierRiskById(String id)
    {
        return supplierRiskMapper.deleteSupplierRiskById(id);
    }
}
