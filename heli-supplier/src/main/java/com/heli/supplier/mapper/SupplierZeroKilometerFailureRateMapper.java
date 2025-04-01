package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierTwoReviewScore;
import com.heli.supplier.domain.SupplierZeroKilometerFailureRate;

/**
 * 零公里故障指标完成率Mapper接口
 * 
 * @author wll
 * @date 2025-03-24
 */
public interface SupplierZeroKilometerFailureRateMapper extends BaseMapper<SupplierZeroKilometerFailureRate>
{
    /**
     * 查询零公里故障指标完成率
     * 
     * @param id 零公里故障指标完成率主键
     * @return 零公里故障指标完成率
     */
    public SupplierZeroKilometerFailureRate selectSupplierZeroKilometerFailureRateById(String id);

    /**
     * 查询零公里故障指标完成率列表
     * 
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 零公里故障指标完成率集合
     */
    public List<SupplierZeroKilometerFailureRate> selectSupplierZeroKilometerFailureRateList(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate);

    /**
     * 新增零公里故障指标完成率
     * 
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 结果
     */
    public int insertSupplierZeroKilometerFailureRate(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate);

    /**
     * 修改零公里故障指标完成率
     * 
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 结果
     */
    public int updateSupplierZeroKilometerFailureRate(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate);

    /**
     * 删除零公里故障指标完成率
     * 
     * @param id 零公里故障指标完成率主键
     * @return 结果
     */
    public int deleteSupplierZeroKilometerFailureRateById(String id);

    /**
     * 批量删除零公里故障指标完成率
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierZeroKilometerFailureRateByIds(String[] ids);
}
