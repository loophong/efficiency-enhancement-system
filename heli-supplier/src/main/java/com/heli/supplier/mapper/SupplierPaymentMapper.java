package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierPayment;
import com.heli.supplier.domain.SupplierRisk;

/**
 * 付款限制条件Mapper接口
 * 
 * @author wll
 * @date 2025-03-25
 */
public interface SupplierPaymentMapper extends BaseMapper<SupplierPayment>
{
    /**
     * 查询付款限制条件
     * 
     * @param id 付款限制条件主键
     * @return 付款限制条件
     */
    public SupplierPayment selectSupplierPaymentById(String id);

    /**
     * 查询付款限制条件列表
     * 
     * @param supplierPayment 付款限制条件
     * @return 付款限制条件集合
     */
    public List<SupplierPayment> selectSupplierPaymentList(SupplierPayment supplierPayment);

    /**
     * 新增付款限制条件
     * 
     * @param supplierPayment 付款限制条件
     * @return 结果
     */
    public int insertSupplierPayment(SupplierPayment supplierPayment);

    /**
     * 修改付款限制条件
     * 
     * @param supplierPayment 付款限制条件
     * @return 结果
     */
    public int updateSupplierPayment(SupplierPayment supplierPayment);

    /**
     * 删除付款限制条件
     * 
     * @param id 付款限制条件主键
     * @return 结果
     */
    public int deleteSupplierPaymentById(String id);

    /**
     * 批量删除付款限制条件
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierPaymentByIds(String[] ids);
}
