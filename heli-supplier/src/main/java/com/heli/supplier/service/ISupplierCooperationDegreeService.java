package com.heli.supplier.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierCooperationDegree;
import com.heli.supplier.domain.SupplierRectificationTimeliness;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 新产品研发配合程度Service接口
 * 
 * @author wll
 * @date 2025-03-05
 */
public interface ISupplierCooperationDegreeService
{
    /**
     * 查询新产品研发配合程度
     * 
     * @param id 新产品研发配合程度主键
     * @return 新产品研发配合程度
     */
    public SupplierCooperationDegree selectSupplierCooperationDegreeById(String id);

    /**
     * 查询新产品研发配合程度列表
     * 
     * @param supplierCooperationDegree 新产品研发配合程度
     * @return 新产品研发配合程度集合
     */
    public List<SupplierCooperationDegree> selectSupplierCooperationDegreeList(SupplierCooperationDegree supplierCooperationDegree);

    /**
     * 新增新产品研发配合程度
     * 
     * @param supplierCooperationDegree 新产品研发配合程度
     * @return 结果
     */
    public int insertSupplierCooperationDegree(SupplierCooperationDegree supplierCooperationDegree);

    /**
     * 修改新产品研发配合程度
     * 
     * @param supplierCooperationDegree 新产品研发配合程度
     * @return 结果
     */
    public int updateSupplierCooperationDegree(SupplierCooperationDegree supplierCooperationDegree);

    /**
     * 批量删除新产品研发配合程度
     * 
     * @param ids 需要删除的新产品研发配合程度主键集合
     * @return 结果
     */
    public int deleteSupplierCooperationDegreeByIds(String[] ids);

    /**
     * 删除新产品研发配合程度信息
     * 
     * @param id 新产品研发配合程度主键
     * @return 结果
     */
    public int deleteSupplierCooperationDegreeById(String id);
}
