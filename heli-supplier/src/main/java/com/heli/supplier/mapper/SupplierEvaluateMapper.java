package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierEvaluate;

/**
 * 评分表Mapper接口
 * 
 * @author wll
 * @date 2025-03-13
 */
public interface SupplierEvaluateMapper extends BaseMapper<SupplierEvaluate>
{
    /**
     * 查询评分表
     * 
     * @param id 评分表主键
     * @return 评分表
     */
    public SupplierEvaluate selectSupplierEvaluateById(String id);

    /**
     * 查询评分表列表
     * 
     * @param supplierEvaluate 评分表
     * @return 评分表集合
     */
    public List<SupplierEvaluate> selectSupplierEvaluateList(SupplierEvaluate supplierEvaluate);

    /**
     * 新增评分表
     * 
     * @param supplierEvaluate 评分表
     * @return 结果
     */
    public int insertSupplierEvaluate(SupplierEvaluate supplierEvaluate);

    /**
     * 修改评分表
     * 
     * @param supplierEvaluate 评分表
     * @return 结果
     */
    public int updateSupplierEvaluate(SupplierEvaluate supplierEvaluate);

    /**
     * 删除评分表
     * 
     * @param id 评分表主键
     * @return 结果
     */
    public int deleteSupplierEvaluateById(String id);

    /**
     * 批量删除评分表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierEvaluateByIds(String[] ids);
}
