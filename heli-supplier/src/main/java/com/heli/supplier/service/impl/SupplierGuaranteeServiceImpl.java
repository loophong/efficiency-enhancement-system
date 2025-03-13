package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierGuaranteeMapper;
import com.heli.supplier.domain.SupplierGuarantee;
import com.heli.supplier.service.ISupplierGuaranteeService;

/**
 * 供货保障Service业务层处理
 * 
 * @author wll
 * @date 2025-02-27
 */
@Service
@Slf4j
public class SupplierGuaranteeServiceImpl  extends ServiceImpl<SupplierGuaranteeMapper, SupplierGuarantee> implements ISupplierGuaranteeService
{
    @Autowired
    private SupplierGuaranteeMapper supplierGuaranteeMapper;

    /**
     * 查询供货保障
     * 
     * @param id 供货保障主键
     * @return 供货保障
     */
    @Override
    public SupplierGuarantee selectSupplierGuaranteeById(String id)
    {
        return supplierGuaranteeMapper.selectSupplierGuaranteeById(id);
    }

    /**
     * 查询供货保障列表
     * 
     * @param supplierGuarantee 供货保障
     * @return 供货保障
     */
    @Override
    public List<SupplierGuarantee> selectSupplierGuaranteeList(SupplierGuarantee supplierGuarantee)
    {
        return supplierGuaranteeMapper.selectSupplierGuaranteeList(supplierGuarantee);
    }

    /**
     * 新增供货保障
     * 
     * @param supplierGuarantee 供货保障
     * @return 结果
     */
    @Override
    public int insertSupplierGuarantee(SupplierGuarantee supplierGuarantee)
    {
        return supplierGuaranteeMapper.insertSupplierGuarantee(supplierGuarantee);
    }

    /**
     * 修改供货保障
     * 
     * @param supplierGuarantee 供货保障
     * @return 结果
     */
    @Override
    public int updateSupplierGuarantee(SupplierGuarantee supplierGuarantee)
    {
        return supplierGuaranteeMapper.updateSupplierGuarantee(supplierGuarantee);
    }

    /**
     * 批量删除供货保障
     * 
     * @param ids 需要删除的供货保障主键
     * @return 结果
     */
    @Override
    public int deleteSupplierGuaranteeByIds(String[] ids)
    {
        return supplierGuaranteeMapper.deleteSupplierGuaranteeByIds(ids);
    }

    /**
     * 删除供货保障信息
     * 
     * @param id 供货保障主键
     * @return 结果
     */
    @Override
    public int deleteSupplierGuaranteeById(String id)
    {
        return supplierGuaranteeMapper.deleteSupplierGuaranteeById(id);
    }
}
