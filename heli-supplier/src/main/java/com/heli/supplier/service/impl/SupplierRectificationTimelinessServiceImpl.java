package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierRectificationTimelinessMapper;
import com.heli.supplier.domain.SupplierRectificationTimeliness;
import com.heli.supplier.service.ISupplierRectificationTimelinessService;

/**
 * 产品技术问题整改及时性Service业务层处理
 * 
 * @author wll
 * @date 2025-03-05
 */
@Service
@Slf4j
public class SupplierRectificationTimelinessServiceImpl extends ServiceImpl<SupplierRectificationTimelinessMapper, SupplierRectificationTimeliness> implements ISupplierRectificationTimelinessService
{
    @Autowired
    private SupplierRectificationTimelinessMapper supplierRectificationTimelinessMapper;

    /**
     * 查询产品技术问题整改及时性
     * 
     * @param id 产品技术问题整改及时性主键
     * @return 产品技术问题整改及时性
     */
    @Override
    public SupplierRectificationTimeliness selectSupplierRectificationTimelinessById(String id)
    {
        return supplierRectificationTimelinessMapper.selectSupplierRectificationTimelinessById(id);
    }

    /**
     * 查询产品技术问题整改及时性列表
     * 
     * @param supplierRectificationTimeliness 产品技术问题整改及时性
     * @return 产品技术问题整改及时性
     */
    @Override
    public List<SupplierRectificationTimeliness> selectSupplierRectificationTimelinessList(SupplierRectificationTimeliness supplierRectificationTimeliness)
    {
        return supplierRectificationTimelinessMapper.selectSupplierRectificationTimelinessList(supplierRectificationTimeliness);
    }

    /**
     * 新增产品技术问题整改及时性
     * 
     * @param supplierRectificationTimeliness 产品技术问题整改及时性
     * @return 结果
     */
    @Override
    public int insertSupplierRectificationTimeliness(SupplierRectificationTimeliness supplierRectificationTimeliness)
    {
        return supplierRectificationTimelinessMapper.insertSupplierRectificationTimeliness(supplierRectificationTimeliness);
    }

    /**
     * 修改产品技术问题整改及时性
     * 
     * @param supplierRectificationTimeliness 产品技术问题整改及时性
     * @return 结果
     */
    @Override
    public int updateSupplierRectificationTimeliness(SupplierRectificationTimeliness supplierRectificationTimeliness)
    {
        return supplierRectificationTimelinessMapper.updateSupplierRectificationTimeliness(supplierRectificationTimeliness);
    }

    /**
     * 批量删除产品技术问题整改及时性
     * 
     * @param ids 需要删除的产品技术问题整改及时性主键
     * @return 结果
     */
    @Override
    public int deleteSupplierRectificationTimelinessByIds(String[] ids)
    {
        return supplierRectificationTimelinessMapper.deleteSupplierRectificationTimelinessByIds(ids);
    }

    /**
     * 删除产品技术问题整改及时性信息
     * 
     * @param id 产品技术问题整改及时性主键
     * @return 结果
     */
    @Override
    public int deleteSupplierRectificationTimelinessById(String id)
    {
        return supplierRectificationTimelinessMapper.deleteSupplierRectificationTimelinessById(id);
    }
}
