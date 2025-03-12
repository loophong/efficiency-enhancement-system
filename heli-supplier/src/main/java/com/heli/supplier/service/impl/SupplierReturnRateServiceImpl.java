package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierReturnRateMapper;
import com.heli.supplier.domain.SupplierReturnRate;
import com.heli.supplier.service.ISupplierReturnRateService;

/**
 * 售后返修率Service业务层处理
 * 
 * @author wll
 * @date 2025-02-27
 */
@Service
public class SupplierReturnRateServiceImpl  extends ServiceImpl<SupplierReturnRateMapper, SupplierReturnRate> implements ISupplierReturnRateService
{
    @Autowired
    private SupplierReturnRateMapper supplierReturnRateMapper;

    /**
     * 查询售后返修率
     * 
     * @param id 售后返修率主键
     * @return 售后返修率
     */
    @Override
    public SupplierReturnRate selectSupplierReturnRateById(String id)
    {
        return supplierReturnRateMapper.selectSupplierReturnRateById(id);
    }

    /**
     * 查询售后返修率列表
     * 
     * @param supplierReturnRate 售后返修率
     * @return 售后返修率
     */
    @Override
    public List<SupplierReturnRate> selectSupplierReturnRateList(SupplierReturnRate supplierReturnRate)
    {
        return supplierReturnRateMapper.selectSupplierReturnRateList(supplierReturnRate);
    }

    /**
     * 新增售后返修率
     * 
     * @param supplierReturnRate 售后返修率
     * @return 结果
     */
    @Override
    public int insertSupplierReturnRate(SupplierReturnRate supplierReturnRate)
    {
        return supplierReturnRateMapper.insertSupplierReturnRate(supplierReturnRate);
    }

    /**
     * 修改售后返修率
     * 
     * @param supplierReturnRate 售后返修率
     * @return 结果
     */
    @Override
    public int updateSupplierReturnRate(SupplierReturnRate supplierReturnRate)
    {
        return supplierReturnRateMapper.updateSupplierReturnRate(supplierReturnRate);
    }

    /**
     * 批量删除售后返修率
     * 
     * @param ids 需要删除的售后返修率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierReturnRateByIds(String[] ids)
    {
        return supplierReturnRateMapper.deleteSupplierReturnRateByIds(ids);
    }

    /**
     * 删除售后返修率信息
     * 
     * @param id 售后返修率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierReturnRateById(String id)
    {
        return supplierReturnRateMapper.deleteSupplierReturnRateById(id);
    }
}
