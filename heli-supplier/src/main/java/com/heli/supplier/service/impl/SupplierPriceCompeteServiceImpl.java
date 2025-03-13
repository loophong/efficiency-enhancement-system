package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierPriceCompeteMapper;
import com.heli.supplier.domain.SupplierPriceCompete;
import com.heli.supplier.service.ISupplierPriceCompeteService;

/**
 * 价格竞争力Service业务层处理
 * 
 * @author wll
 * @date 2025-03-05
 */
@Service
public class SupplierPriceCompeteServiceImpl extends ServiceImpl<SupplierPriceCompeteMapper, SupplierPriceCompete> implements ISupplierPriceCompeteService
{
    @Autowired
    private SupplierPriceCompeteMapper supplierPriceCompeteMapper;

    /**
     * 查询价格竞争力
     * 
     * @param id 价格竞争力主键
     * @return 价格竞争力
     */
    @Override
    public SupplierPriceCompete selectSupplierPriceCompeteById(String id)
    {
        return supplierPriceCompeteMapper.selectSupplierPriceCompeteById(id);
    }

    /**
     * 查询价格竞争力列表
     * 
     * @param supplierPriceCompete 价格竞争力
     * @return 价格竞争力
     */
    @Override
    public List<SupplierPriceCompete> selectSupplierPriceCompeteList(SupplierPriceCompete supplierPriceCompete)
    {
        return supplierPriceCompeteMapper.selectSupplierPriceCompeteList(supplierPriceCompete);
    }

    /**
     * 新增价格竞争力
     * 
     * @param supplierPriceCompete 价格竞争力
     * @return 结果
     */
    @Override
    public int insertSupplierPriceCompete(SupplierPriceCompete supplierPriceCompete)
    {
        return supplierPriceCompeteMapper.insertSupplierPriceCompete(supplierPriceCompete);
    }

    /**
     * 修改价格竞争力
     * 
     * @param supplierPriceCompete 价格竞争力
     * @return 结果
     */
    @Override
    public int updateSupplierPriceCompete(SupplierPriceCompete supplierPriceCompete)
    {
        return supplierPriceCompeteMapper.updateSupplierPriceCompete(supplierPriceCompete);
    }

    /**
     * 批量删除价格竞争力
     * 
     * @param ids 需要删除的价格竞争力主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPriceCompeteByIds(String[] ids)
    {
        return supplierPriceCompeteMapper.deleteSupplierPriceCompeteByIds(ids);
    }

    /**
     * 删除价格竞争力信息
     * 
     * @param id 价格竞争力主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPriceCompeteById(String id)
    {
        return supplierPriceCompeteMapper.deleteSupplierPriceCompeteById(id);
    }
}
