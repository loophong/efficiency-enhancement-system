package com.heli.supplier.service;

import java.util.List;
import com.heli.supplier.domain.SupplierReduceSupport;

/**
 * 降本支持Service接口
 * 
 * @author wll
 * @date 2025-03-06
 */
public interface ISupplierReduceSupportService 
{
    /**
     * 查询降本支持
     * 
     * @param id 降本支持主键
     * @return 降本支持
     */
    public SupplierReduceSupport selectSupplierReduceSupportById(String id);

    /**
     * 查询降本支持列表
     * 
     * @param supplierReduceSupport 降本支持
     * @return 降本支持集合
     */
    public List<SupplierReduceSupport> selectSupplierReduceSupportList(SupplierReduceSupport supplierReduceSupport);

    /**
     * 新增降本支持
     * 
     * @param supplierReduceSupport 降本支持
     * @return 结果
     */
    public int insertSupplierReduceSupport(SupplierReduceSupport supplierReduceSupport);

    /**
     * 修改降本支持
     * 
     * @param supplierReduceSupport 降本支持
     * @return 结果
     */
    public int updateSupplierReduceSupport(SupplierReduceSupport supplierReduceSupport);

    /**
     * 批量删除降本支持
     * 
     * @param ids 需要删除的降本支持主键集合
     * @return 结果
     */
    public int deleteSupplierReduceSupportByIds(String[] ids);

    /**
     * 删除降本支持信息
     * 
     * @param id 降本支持主键
     * @return 结果
     */
    public int deleteSupplierReduceSupportById(String id);
}
