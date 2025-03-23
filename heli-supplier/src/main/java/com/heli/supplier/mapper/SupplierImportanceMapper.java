package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierImportance;
import com.heli.supplier.domain.SupplierOnetimeSimple;

/**
 * 重要度Mapper接口
 * 
 * @author wll
 * @date 2025-03-07
 */
public interface SupplierImportanceMapper extends BaseMapper<SupplierImportance>
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
     * 删除重要度
     * 
     * @param id 重要度主键
     * @return 结果
     */
    public int deleteSupplierImportanceById(String id);

    /**
     * 批量删除重要度
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierImportanceByIds(String[] ids);
}
