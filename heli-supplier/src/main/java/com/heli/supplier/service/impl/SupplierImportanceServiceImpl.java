package com.heli.supplier.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierImportanceMapper;
import com.heli.supplier.domain.SupplierImportance;
import com.heli.supplier.service.ISupplierImportanceService;

/**
 * 重要度Service业务层处理
 * 
 * @author wll
 * @date 2025-03-07
 */
@Service
public class SupplierImportanceServiceImpl implements ISupplierImportanceService 
{
    @Autowired
    private SupplierImportanceMapper supplierImportanceMapper;

    /**
     * 查询重要度
     * 
     * @param id 重要度主键
     * @return 重要度
     */
    @Override
    public SupplierImportance selectSupplierImportanceById(String id)
    {
        return supplierImportanceMapper.selectSupplierImportanceById(id);
    }

    /**
     * 查询重要度列表
     * 
     * @param supplierImportance 重要度
     * @return 重要度
     */
    @Override
    public List<SupplierImportance> selectSupplierImportanceList(SupplierImportance supplierImportance)
    {
        return supplierImportanceMapper.selectSupplierImportanceList(supplierImportance);
    }

    /**
     * 新增重要度
     * 
     * @param supplierImportance 重要度
     * @return 结果
     */
    @Override
    public int insertSupplierImportance(SupplierImportance supplierImportance)
    {
        return supplierImportanceMapper.insertSupplierImportance(supplierImportance);
    }

    /**
     * 修改重要度
     * 
     * @param supplierImportance 重要度
     * @return 结果
     */
    @Override
    public int updateSupplierImportance(SupplierImportance supplierImportance)
    {
        return supplierImportanceMapper.updateSupplierImportance(supplierImportance);
    }

    /**
     * 批量删除重要度
     * 
     * @param ids 需要删除的重要度主键
     * @return 结果
     */
    @Override
    public int deleteSupplierImportanceByIds(String[] ids)
    {
        return supplierImportanceMapper.deleteSupplierImportanceByIds(ids);
    }

    /**
     * 删除重要度信息
     * 
     * @param id 重要度主键
     * @return 结果
     */
    @Override
    public int deleteSupplierImportanceById(String id)
    {
        return supplierImportanceMapper.deleteSupplierImportanceById(id);
    }
}
