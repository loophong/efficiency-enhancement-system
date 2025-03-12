package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierRectificationTimeliness;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 产品技术问题整改及时性Mapper接口
 * 
 * @author wll
 * @date 2025-03-05
 */
public interface SupplierRectificationTimelinessMapper extends BaseMapper<SupplierRectificationTimeliness>
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
     * 删除产品技术问题整改及时性
     * 
     * @param id 产品技术问题整改及时性主键
     * @return 结果
     */
    public int deleteSupplierRectificationTimelinessById(String id);

    /**
     * 批量删除产品技术问题整改及时性
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierRectificationTimelinessByIds(String[] ids);
}
