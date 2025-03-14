package com.heli.supplier.service;

import java.util.List;
import com.heli.supplier.domain.SupplierImportance;

/**
 * 重要度Service接口
 * 
 * @author wll
 * @date 2025-03-07
 */
public interface ISupplierImportanceService 
{
    /**
     * 查询重要度
     * 
     * @param id 重要度主键
     * @return 重要度
     */
    public SupplierImportance selectSupplierImportanceById(String id);

    /**
     * 查询重要度列表
     * 
     * @param supplierImportance 重要度
     * @return 重要度集合
     */
    public List<SupplierImportance> selectSupplierImportanceList(SupplierImportance supplierImportance);

    /**
     * 新增重要度
     * 
     * @param supplierImportance 重要度
     * @return 结果
     */
    public int insertSupplierImportance(SupplierImportance supplierImportance);

    /**
     * 修改重要度
     * 
     * @param supplierImportance 重要度
     * @return 结果
     */
    public int updateSupplierImportance(SupplierImportance supplierImportance);

    /**
     * 批量删除重要度
     * 
     * @param ids 需要删除的重要度主键集合
     * @return 结果
     */
    public int deleteSupplierImportanceByIds(String[] ids);

    /**
     * 删除重要度信息
     * 
     * @param id 重要度主键
     * @return 结果
     */
    public int deleteSupplierImportanceById(String id);
}
