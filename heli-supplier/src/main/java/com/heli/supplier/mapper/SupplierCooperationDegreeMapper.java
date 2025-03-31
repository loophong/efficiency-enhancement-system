package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierCooperationDegree;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 新产品研发配合程度Mapper接口
 * 
 * @author wll
 * @date 2025-03-05
 */
public interface SupplierCooperationDegreeMapper extends BaseMapper<SupplierCooperationDegree>
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
     * 删除新产品研发配合程度
     * 
     * @param id 新产品研发配合程度主键
     * @return 结果
     */
    public int deleteSupplierCooperationDegreeById(String id);

    /**
     * 批量删除新产品研发配合程度
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierCooperationDegreeByIds(String[] ids);
}
