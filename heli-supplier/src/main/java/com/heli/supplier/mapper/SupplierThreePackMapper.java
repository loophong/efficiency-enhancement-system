package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierThreePack;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 三包发货及时率Mapper接口
 * 
 * @author wll
 * @date 2025-02-26
 */
public interface SupplierThreePackMapper extends BaseMapper<SupplierThreePack>
{
    /**
     * 查询三包发货及时率
     * 
     * @param id 三包发货及时率主键
     * @return 三包发货及时率
     */
    public SupplierThreePack selectSupplierThreePackById(String id);

    /**
     * 查询三包发货及时率列表
     * 
     * @param supplierThreePack 三包发货及时率
     * @return 三包发货及时率集合
     */
    public List<SupplierThreePack> selectSupplierThreePackList(SupplierThreePack supplierThreePack);

    /**
     * 新增三包发货及时率
     * 
     * @param supplierThreePack 三包发货及时率
     * @return 结果
     */
    public int insertSupplierThreePack(SupplierThreePack supplierThreePack);

    /**
     * 修改三包发货及时率
     * 
     * @param supplierThreePack 三包发货及时率
     * @return 结果
     */
    public int updateSupplierThreePack(SupplierThreePack supplierThreePack);

    /**
     * 删除三包发货及时率
     * 
     * @param id 三包发货及时率主键
     * @return 结果
     */
    public int deleteSupplierThreePackById(String id);

    /**
     * 批量删除三包发货及时率
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierThreePackByIds(String[] ids);
}
