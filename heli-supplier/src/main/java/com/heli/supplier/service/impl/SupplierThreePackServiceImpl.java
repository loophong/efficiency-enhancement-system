package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierThreePackMapper;
import com.heli.supplier.domain.SupplierThreePack;
import com.heli.supplier.service.ISupplierThreePackService;

/**
 * 三包发货及时率Service业务层处理
 * 
 * @author wll
 * @date 2025-02-26
 */
@Service
public class SupplierThreePackServiceImpl  extends ServiceImpl<SupplierThreePackMapper, SupplierThreePack> implements ISupplierThreePackService
{
    @Autowired
    private SupplierThreePackMapper supplierThreePackMapper;

    /**
     * 查询三包发货及时率
     * 
     * @param id 三包发货及时率主键
     * @return 三包发货及时率
     */
    @Override
    public SupplierThreePack selectSupplierThreePackById(String id)
    {
        return supplierThreePackMapper.selectSupplierThreePackById(id);
    }

    /**
     * 查询三包发货及时率列表
     * 
     * @param supplierThreePack 三包发货及时率
     * @return 三包发货及时率
     */
    @Override
    public List<SupplierThreePack> selectSupplierThreePackList(SupplierThreePack supplierThreePack)
    {
        return supplierThreePackMapper.selectSupplierThreePackList(supplierThreePack);
    }

    /**
     * 新增三包发货及时率
     * 
     * @param supplierThreePack 三包发货及时率
     * @return 结果
     */
    @Override
    public int insertSupplierThreePack(SupplierThreePack supplierThreePack)
    {
        supplierThreePack.setScore(calculateThreePackScore(supplierThreePack.getPlannedDeliveryTime(), supplierThreePack.getActualDeliveryTime()));
        return supplierThreePackMapper.insertSupplierThreePack(supplierThreePack);
    }

    /**
     * 修改三包发货及时率
     * 
     * @param supplierThreePack 三包发货及时率
     * @return 结果
     */
    @Override
    public int updateSupplierThreePack(SupplierThreePack supplierThreePack)
    {
        supplierThreePack.setScore(calculateThreePackScore(supplierThreePack.getPlannedDeliveryTime(), supplierThreePack.getActualDeliveryTime()));
        return supplierThreePackMapper.updateSupplierThreePack(supplierThreePack);
    }

    /**
     * 批量删除三包发货及时率
     * 
     * @param ids 需要删除的三包发货及时率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierThreePackByIds(String[] ids)
    {
        return supplierThreePackMapper.deleteSupplierThreePackByIds(ids);
    }

    /**
     * 删除三包发货及时率信息
     * 
     * @param id 三包发货及时率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierThreePackById(String id)
    {
        return supplierThreePackMapper.deleteSupplierThreePackById(id);
    }


    /**
     * 计算三包发货及时率得分
     * @param plannedDeliveryTime 计划发货时间
     * @param actualDeliveryTime 实际发货时间
     * @return 最终得分（3分或0分）
     */
    private double calculateThreePackScore(Date plannedDeliveryTime, Date actualDeliveryTime) {
        if (plannedDeliveryTime == null || actualDeliveryTime == null) {
            throw new IllegalArgumentException("计划发货时间点和实际发货时间点不能为空");
        }

        // 如果实际发货时间晚于计划发货时间，则得分为0，否则为3
        return actualDeliveryTime.after(plannedDeliveryTime) ? 0.0 : 3.0;
    }

}
