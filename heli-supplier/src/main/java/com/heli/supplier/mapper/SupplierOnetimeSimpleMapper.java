package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.domain.SupplierReturnRate;


/**
 * 一次交检合格率-简化版Mapper接口
 * 
 * @author weill
 * @date 2025-02-21
 */
public interface SupplierOnetimeSimpleMapper extends BaseMapper<SupplierOnetimeSimple>
{
    /**
     * 查询一次交检合格率-简化版
     * 
     * @param id 一次交检合格率-简化版主键
     * @return 一次交检合格率-简化版
     */
    public SupplierOnetimeSimple selectSupplierOnetimeSimpleById(Long id);

    /**
     * 查询一次交检合格率-简化版列表
     * 
     * @param supplierOnetimeSimple 一次交检合格率-简化版
     * @return 一次交检合格率-简化版集合
     */
    public List<SupplierOnetimeSimple> selectSupplierOnetimeSimpleList(SupplierOnetimeSimple supplierOnetimeSimple);

    /**
     * 新增一次交检合格率-简化版
     * 
     * @param supplierOnetimeSimple 一次交检合格率-简化版
     * @return 结果
     */
    public int insertSupplierOnetimeSimple(SupplierOnetimeSimple supplierOnetimeSimple);

    /**
     * 修改一次交检合格率-简化版
     * 
     * @param supplierOnetimeSimple 一次交检合格率-简化版
     * @return 结果
     */
    public int updateSupplierOnetimeSimple(SupplierOnetimeSimple supplierOnetimeSimple);

    /**
     * 删除一次交检合格率-简化版
     * 
     * @param id 一次交检合格率-简化版主键
     * @return 结果
     */
    public int deleteSupplierOnetimeSimpleById(Long id);

    /**
     * 批量删除一次交检合格率-简化版
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierOnetimeSimpleByIds(Long[] ids);

}
