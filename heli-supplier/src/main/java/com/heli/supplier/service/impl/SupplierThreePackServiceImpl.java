package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierThreePackMapper;
import com.heli.supplier.domain.SupplierThreePack;
import com.heli.supplier.service.ISupplierThreePackService;

/**
 * 三包发货及时率Service业务层处理
 * 
 * @author wll
 * @date 2025-02-26
 */
@Service
public class SupplierThreePackServiceImpl  extends ServiceImpl<SupplierThreePackMapper, SupplierThreePack> implements ISupplierThreePackService
{
    @Autowired
    private SupplierThreePackMapper supplierThreePackMapper;

    /**
     * 查询三包发货及时率
     * 
     * @param id 三包发货及时率主键
     * @return 三包发货及时率
     */
    @Override
    public SupplierThreePack selectSupplierThreePackById(String id)
    {
        return supplierThreePackMapper.selectSupplierThreePackById(id);
    }

    /**
     * 查询三包发货及时率列表
     * 
     * @param supplierThreePack 三包发货及时率
     * @return 三包发货及时率
     */
    @Override
    public List<SupplierThreePack> selectSupplierThreePackList(SupplierThreePack supplierThreePack)
    {
        return supplierThreePackMapper.selectSupplierThreePackList(supplierThreePack);
    }

    /**
     * 新增三包发货及时率
     * 
     * @param supplierThreePack 三包发货及时率
     * @return 结果
     */
    @Override
    public int insertSupplierThreePack(SupplierThreePack supplierThreePack)
    {
        return supplierThreePackMapper.insertSupplierThreePack(supplierThreePack);
    }

    /**
     * 修改三包发货及时率
     * 
     * @param supplierThreePack 三包发货及时率
     * @return 结果
     */
    @Override
    public int updateSupplierThreePack(SupplierThreePack supplierThreePack)
    {
        return supplierThreePackMapper.updateSupplierThreePack(supplierThreePack);
    }

    /**
     * 批量删除三包发货及时率
     * 
     * @param ids 需要删除的三包发货及时率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierThreePackByIds(String[] ids)
    {
        return supplierThreePackMapper.deleteSupplierThreePackByIds(ids);
    }

    /**
     * 删除三包发货及时率信息
     * 
     * @param id 三包发货及时率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierThreePackById(String id)
    {
        return supplierThreePackMapper.deleteSupplierThreePackById(id);
    }
}
