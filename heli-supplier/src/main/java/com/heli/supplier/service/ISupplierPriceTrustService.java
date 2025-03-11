package com.heli.supplier.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierPriceTrust;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 价格诚信Service接口
 * 
 * @author wll
 * @date 2025-02-28
 */
public interface ISupplierPriceTrustService extends IService<SupplierPriceTrust>
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
     * 批量删除价格诚信
     * 
     * @param ids 需要删除的价格诚信主键集合
     * @return 结果
     */
    public int deleteSupplierPriceTrustByIds(String[] ids);

    /**
     * 删除价格诚信信息
     * 
     * @param id 价格诚信主键
     * @return 结果
     */
    public int deleteSupplierPriceTrustById(String id);
}
