package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierGuarantee;
import com.heli.supplier.mapper.SupplierGuaranteeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierChangeMapper;
import com.heli.supplier.domain.SupplierChange;
import com.heli.supplier.service.ISupplierChangeService;

/**
 * 擅自变更产品材质参数尺寸Service业务层处理
 * 
 * @author wll
 * @date 2025-03-07
 */
@Service
public class SupplierChangeServiceImpl extends ServiceImpl<SupplierChangeMapper, SupplierChange> implements ISupplierChangeService
{
    @Autowired
    private SupplierChangeMapper supplierChangeMapper;

    /**
     * 查询擅自变更产品材质参数尺寸
     * 
     * @param id 擅自变更产品材质参数尺寸主键
     * @return 擅自变更产品材质参数尺寸
     */
    @Override
    public SupplierChange selectSupplierChangeById(String id)
    {
        return supplierChangeMapper.selectSupplierChangeById(id);
    }

    /**
     * 查询擅自变更产品材质参数尺寸列表
     * 
     * @param supplierChange 擅自变更产品材质参数尺寸
     * @return 擅自变更产品材质参数尺寸
     */
    @Override
    public List<SupplierChange> selectSupplierChangeList(SupplierChange supplierChange)
    {
        return supplierChangeMapper.selectSupplierChangeList(supplierChange);
    }

    /**
     * 新增擅自变更产品材质参数尺寸
     * 
     * @param supplierChange 擅自变更产品材质参数尺寸
     * @return 结果
     */
    @Override
    public int insertSupplierChange(SupplierChange supplierChange)
    {
        return supplierChangeMapper.insertSupplierChange(supplierChange);
    }

    /**
     * 修改擅自变更产品材质参数尺寸
     * 
     * @param supplierChange 擅自变更产品材质参数尺寸
     * @return 结果
     */
    @Override
    public int updateSupplierChange(SupplierChange supplierChange)
    {
        return supplierChangeMapper.updateSupplierChange(supplierChange);
    }

    /**
     * 批量删除擅自变更产品材质参数尺寸
     * 
     * @param ids 需要删除的擅自变更产品材质参数尺寸主键
     * @return 结果
     */
    @Override
    public int deleteSupplierChangeByIds(String[] ids)
    {
        return supplierChangeMapper.deleteSupplierChangeByIds(ids);
    }

    /**
     * 删除擅自变更产品材质参数尺寸信息
     * 
     * @param id 擅自变更产品材质参数尺寸主键
     * @return 结果
     */
    @Override
    public int deleteSupplierChangeById(String id)
    {
        return supplierChangeMapper.deleteSupplierChangeById(id);
    }
}
