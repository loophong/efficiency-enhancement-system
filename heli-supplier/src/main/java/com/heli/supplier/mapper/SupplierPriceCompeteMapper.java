package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierPriceCompete;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 价格竞争力Mapper接口
 * 
 * @author wll
 * @date 2025-03-05
 */
public interface SupplierPriceCompeteMapper extends BaseMapper<SupplierPriceCompete>
{
    /**
     * 查询价格竞争力
     * 
     * @param id 价格竞争力主键
     * @return 价格竞争力
     */
    public SupplierPriceCompete selectSupplierPriceCompeteById(String id);

    /**
     * 查询价格竞争力列表
     * 
     * @param supplierPriceCompete 价格竞争力
     * @return 价格竞争力集合
     */
    public List<SupplierPriceCompete> selectSupplierPriceCompeteList(SupplierPriceCompete supplierPriceCompete);

    /**
     * 新增价格竞争力
     * 
     * @param supplierPriceCompete 价格竞争力
     * @return 结果
     */
    public int insertSupplierPriceCompete(SupplierPriceCompete supplierPriceCompete);

    /**
     * 修改价格竞争力
     * 
     * @param supplierPriceCompete 价格竞争力
     * @return 结果
     */
    public int updateSupplierPriceCompete(SupplierPriceCompete supplierPriceCompete);

    /**
     * 删除价格竞争力
     * 
     * @param id 价格竞争力主键
     * @return 结果
     */
    public int deleteSupplierPriceCompeteById(String id);

    /**
     * 批量删除价格竞争力
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierPriceCompeteByIds(String[] ids);
}
