package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierRectificationTimeliness;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SupplierRectificationTimelinessMapper;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import com.heli.supplier.service.ISupplierRectificationTimelinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierCooperationDegreeMapper;
import com.heli.supplier.domain.SupplierCooperationDegree;
import com.heli.supplier.service.ISupplierCooperationDegreeService;

/**
 * 新产品研发配合程度Service业务层处理
 * 
 * @author wll
 * @date 2025-03-05
 */
@Service
public class SupplierCooperationDegreeServiceImpl implements ISupplierCooperationDegreeService
{
    @Autowired
    private SupplierCooperationDegreeMapper supplierCooperationDegreeMapper;

    /**
     * 查询新产品研发配合程度
     * 
     * @param id 新产品研发配合程度主键
     * @return 新产品研发配合程度
     */
    @Override
    public SupplierCooperationDegree selectSupplierCooperationDegreeById(String id)
    {
        return supplierCooperationDegreeMapper.selectSupplierCooperationDegreeById(id);
    }

    /**
     * 查询新产品研发配合程度列表
     * 
     * @param supplierCooperationDegree 新产品研发配合程度
     * @return 新产品研发配合程度
     */
    @Override
    public List<SupplierCooperationDegree> selectSupplierCooperationDegreeList(SupplierCooperationDegree supplierCooperationDegree)
    {
        return supplierCooperationDegreeMapper.selectSupplierCooperationDegreeList(supplierCooperationDegree);
    }

    /**
     * 新增新产品研发配合程度
     * 
     * @param supplierCooperationDegree 新产品研发配合程度
     * @return 结果
     */
    @Override
    public int insertSupplierCooperationDegree(SupplierCooperationDegree supplierCooperationDegree)
    {
        return supplierCooperationDegreeMapper.insertSupplierCooperationDegree(supplierCooperationDegree);
    }

    /**
     * 修改新产品研发配合程度
     * 
     * @param supplierCooperationDegree 新产品研发配合程度
     * @return 结果
     */
    @Override
    public int updateSupplierCooperationDegree(SupplierCooperationDegree supplierCooperationDegree)
    {
        return supplierCooperationDegreeMapper.updateSupplierCooperationDegree(supplierCooperationDegree);
    }

    /**
     * 批量删除新产品研发配合程度
     * 
     * @param ids 需要删除的新产品研发配合程度主键
     * @return 结果
     */
    @Override
    public int deleteSupplierCooperationDegreeByIds(String[] ids)
    {
        return supplierCooperationDegreeMapper.deleteSupplierCooperationDegreeByIds(ids);
    }

    /**
     * 删除新产品研发配合程度信息
     * 
     * @param id 新产品研发配合程度主键
     * @return 结果
     */
    @Override
    public int deleteSupplierCooperationDegreeById(String id)
    {
        return supplierCooperationDegreeMapper.deleteSupplierCooperationDegreeById(id);
    }
}
