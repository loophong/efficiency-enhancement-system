package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierReturnRate;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 售后返修率Mapper接口
 * 
 * @author wll
 * @date 2025-02-27
 */
public interface SupplierReturnRateMapper  extends BaseMapper<SupplierReturnRate>
{
    /**
     * 查询售后返修率
     *
     * @param id 售后返修率主键
     * @return 售后返修率
     */
    public SupplierReturnRate selectSupplierReturnRateById(String id);

    /**
     * 查询售后返修率列表
     *
     * @param supplierReturnRate 售后返修率
     * @return 售后返修率集合
     */
    public List<SupplierReturnRate> selectSupplierReturnRateList(SupplierReturnRate supplierReturnRate);

    /**
     * 新增售后返修率
     *
     * @param supplierReturnRate 售后返修率
     * @return 结果
     */
    public int insertSupplierReturnRate(SupplierReturnRate supplierReturnRate);

    /**
     * 修改售后返修率
     *
     * @param supplierReturnRate 售后返修率
     * @return 结果
     */
    public int updateSupplierReturnRate(SupplierReturnRate supplierReturnRate);

    /**
     * 删除售后返修率
     *
     * @param id 售后返修率主键
     * @return 结果
     */
    public int deleteSupplierReturnRateById(String id);

    /**
     * 批量删除售后返修率
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierReturnRateByIds(String[] ids);
}
