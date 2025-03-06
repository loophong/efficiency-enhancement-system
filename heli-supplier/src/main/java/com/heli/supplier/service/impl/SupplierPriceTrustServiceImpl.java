package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierPriceTrustMapper;
import com.heli.supplier.domain.SupplierPriceTrust;
import com.heli.supplier.service.ISupplierPriceTrustService;

/**
 * 价格诚信Service业务层处理
 * 
 * @author wll
 * @date 2025-02-28
 */
@Service
@Slf4j
public class SupplierPriceTrustServiceImpl  extends ServiceImpl<SupplierPriceTrustMapper, SupplierPriceTrust> implements ISupplierPriceTrustService
{
    @Autowired
    private SupplierPriceTrustMapper supplierPriceTrustMapper;

    /**
     * 查询价格诚信
     * 
     * @param id 价格诚信主键
     * @return 价格诚信
     */
    @Override
    public SupplierPriceTrust selectSupplierPriceTrustById(String id)
    {
        return supplierPriceTrustMapper.selectSupplierPriceTrustById(id);
    }

    /**
     * 查询价格诚信列表
     * 
     * @param supplierPriceTrust 价格诚信
     * @return 价格诚信
     */
    @Override
    public List<SupplierPriceTrust> selectSupplierPriceTrustList(SupplierPriceTrust supplierPriceTrust)
    {
        return supplierPriceTrustMapper.selectSupplierPriceTrustList(supplierPriceTrust);
    }

    /**
     * 新增价格诚信
     * 
     * @param supplierPriceTrust 价格诚信
     * @return 结果
     */
    @Override
    public int insertSupplierPriceTrust(SupplierPriceTrust supplierPriceTrust)
    {
        return supplierPriceTrustMapper.insertSupplierPriceTrust(supplierPriceTrust);
    }

    /**
     * 修改价格诚信
     * 
     * @param supplierPriceTrust 价格诚信
     * @return 结果
     */
    @Override
    public int updateSupplierPriceTrust(SupplierPriceTrust supplierPriceTrust)
    {
        return supplierPriceTrustMapper.updateSupplierPriceTrust(supplierPriceTrust);
    }

    /**
     * 批量删除价格诚信
     * 
     * @param ids 需要删除的价格诚信主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPriceTrustByIds(String[] ids)
    {
        return supplierPriceTrustMapper.deleteSupplierPriceTrustByIds(ids);
    }

    /**
     * 删除价格诚信信息
     * 
     * @param id 价格诚信主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPriceTrustById(String id)
    {
        return supplierPriceTrustMapper.deleteSupplierPriceTrustById(id);
    }
}
