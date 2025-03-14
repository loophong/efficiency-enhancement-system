package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierPriceTrust;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 价格诚信Mapper接口
 * 
 * @author wll
 * @date 2025-02-28
 */
public interface SupplierPriceTrustMapper extends BaseMapper<SupplierPriceTrust>
{
    /**
     * 查询价格诚信
     * 
     * @param id 价格诚信主键
     * @return 价格诚信
     */
    public SupplierPriceTrust selectSupplierPriceTrustById(String id);

    /**
     * 查询价格诚信列表
     * 
     * @param supplierPriceTrust 价格诚信
     * @return 价格诚信集合
     */
    public List<SupplierPriceTrust> selectSupplierPriceTrustList(SupplierPriceTrust supplierPriceTrust);

    /**
     * 新增价格诚信
     * 
     * @param supplierPriceTrust 价格诚信
     * @return 结果
     */
    public int insertSupplierPriceTrust(SupplierPriceTrust supplierPriceTrust);

    /**
     * 修改价格诚信
     * 
     * @param supplierPriceTrust 价格诚信
     * @return 结果
     */
    public int updateSupplierPriceTrust(SupplierPriceTrust supplierPriceTrust);

    /**
     * 删除价格诚信
     * 
     * @param id 价格诚信主键
     * @return 结果
     */
    public int deleteSupplierPriceTrustById(String id);

    /**
     * 批量删除价格诚信
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierPriceTrustByIds(String[] ids);
    // 在 SupplierPriceTrustMapper 接口中增加此方法
    SupplierPriceTrust selectBySupplierCode(String supplierCode);

}
