package com.heli.supplier.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierChange;
import com.heli.supplier.domain.SupplierGuarantee;

/**
 * 擅自变更产品材质参数尺寸Service接口
 * 
 * @author wll
 * @date 2025-03-07
 */
public interface ISupplierChangeService extends IService<SupplierChange>
{
    /**
     * 查询擅自变更产品材质参数尺寸
     * 
     * @param id 擅自变更产品材质参数尺寸主键
     * @return 擅自变更产品材质参数尺寸
     */
    public SupplierChange selectSupplierChangeById(String id);

    /**
     * 查询擅自变更产品材质参数尺寸列表
     * 
     * @param supplierChange 擅自变更产品材质参数尺寸
     * @return 擅自变更产品材质参数尺寸集合
     */
    public List<SupplierChange> selectSupplierChangeList(SupplierChange supplierChange);

    /**
     * 新增擅自变更产品材质参数尺寸
     * 
     * @param supplierChange 擅自变更产品材质参数尺寸
     * @return 结果
     */
    public int insertSupplierChange(SupplierChange supplierChange);

    /**
     * 修改擅自变更产品材质参数尺寸
     * 
     * @param supplierChange 擅自变更产品材质参数尺寸
     * @return 结果
     */
    public int updateSupplierChange(SupplierChange supplierChange);

    /**
     * 批量删除擅自变更产品材质参数尺寸
     * 
     * @param ids 需要删除的擅自变更产品材质参数尺寸主键集合
     * @return 结果
     */
    public int deleteSupplierChangeByIds(String[] ids);

    /**
     * 删除擅自变更产品材质参数尺寸信息
     * 
     * @param id 擅自变更产品材质参数尺寸主键
     * @return 结果
     */
    public int deleteSupplierChangeById(String id);
}
