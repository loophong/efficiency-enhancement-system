package com.heli.supplier.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierRectificationTimeliness;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 产品技术问题整改及时性Service接口
 * 
 * @author wll
 * @date 2025-03-05
 */
public interface ISupplierRectificationTimelinessService extends IService<SupplierRectificationTimeliness>
{
    /**
     * 查询产品技术问题整改及时性
     * 
     * @param id 产品技术问题整改及时性主键
     * @return 产品技术问题整改及时性
     */
    public SupplierRectificationTimeliness selectSupplierRectificationTimelinessById(String id);

    /**
     * 查询产品技术问题整改及时性列表
     * 
     * @param supplierRectificationTimeliness 产品技术问题整改及时性
     * @return 产品技术问题整改及时性集合
     */
    public List<SupplierRectificationTimeliness> selectSupplierRectificationTimelinessList(SupplierRectificationTimeliness supplierRectificationTimeliness);

    /**
     * 新增产品技术问题整改及时性
     * 
     * @param supplierRectificationTimeliness 产品技术问题整改及时性
     * @return 结果
     */
    public int insertSupplierRectificationTimeliness(SupplierRectificationTimeliness supplierRectificationTimeliness);

    /**
     * 修改产品技术问题整改及时性
     * 
     * @param supplierRectificationTimeliness 产品技术问题整改及时性
     * @return 结果
     */
    public int updateSupplierRectificationTimeliness(SupplierRectificationTimeliness supplierRectificationTimeliness);

    /**
     * 批量删除产品技术问题整改及时性
     * 
     * @param ids 需要删除的产品技术问题整改及时性主键集合
     * @return 结果
     */
    public int deleteSupplierRectificationTimelinessByIds(String[] ids);

    /**
     * 删除产品技术问题整改及时性信息
     * 
     * @param id 产品技术问题整改及时性主键
     * @return 结果
     */
    public int deleteSupplierRectificationTimelinessById(String id);
}
