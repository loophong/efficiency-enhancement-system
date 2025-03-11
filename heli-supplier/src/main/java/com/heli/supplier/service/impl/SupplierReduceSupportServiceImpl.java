package com.heli.supplier.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierReduceSupportMapper;
import com.heli.supplier.domain.SupplierReduceSupport;
import com.heli.supplier.service.ISupplierReduceSupportService;

/**
 * 降本支持Service业务层处理
 * 
 * @author wll
 * @date 2025-03-06
 */
@Service
public class SupplierReduceSupportServiceImpl implements ISupplierReduceSupportService 
{
    @Autowired
    private SupplierReduceSupportMapper supplierReduceSupportMapper;

    /**
     * 查询降本支持
     * 
     * @param id 降本支持主键
     * @return 降本支持
     */
    @Override
    public SupplierReduceSupport selectSupplierReduceSupportById(String id)
    {
        return supplierReduceSupportMapper.selectSupplierReduceSupportById(id);
    }

    /**
     * 查询降本支持列表
     * 
     * @param supplierReduceSupport 降本支持
     * @return 降本支持
     */
    @Override
    public List<SupplierReduceSupport> selectSupplierReduceSupportList(SupplierReduceSupport supplierReduceSupport)
    {
        return supplierReduceSupportMapper.selectSupplierReduceSupportList(supplierReduceSupport);
    }

    /**
     * 新增降本支持
     * 
     * @param supplierReduceSupport 降本支持
     * @return 结果
     */
    @Override
    public int insertSupplierReduceSupport(SupplierReduceSupport supplierReduceSupport)
    {
        return supplierReduceSupportMapper.insertSupplierReduceSupport(supplierReduceSupport);
    }

    /**
     * 修改降本支持
     * 
     * @param supplierReduceSupport 降本支持
     * @return 结果
     */
    @Override
    public int updateSupplierReduceSupport(SupplierReduceSupport supplierReduceSupport)
    {
        return supplierReduceSupportMapper.updateSupplierReduceSupport(supplierReduceSupport);
    }

    /**
     * 批量删除降本支持
     * 
     * @param ids 需要删除的降本支持主键
     * @return 结果
     */
    @Override
    public int deleteSupplierReduceSupportByIds(String[] ids)
    {
        return supplierReduceSupportMapper.deleteSupplierReduceSupportByIds(ids);
    }

    /**
     * 删除降本支持信息
     * 
     * @param id 降本支持主键
     * @return 结果
     */
    @Override
    public int deleteSupplierReduceSupportById(String id)
    {
        return supplierReduceSupportMapper.deleteSupplierReduceSupportById(id);
    }
}
