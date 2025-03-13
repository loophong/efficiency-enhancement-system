package com.heli.production.service;

import com.heli.production.domain.entity.OrderSchedulingEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
* @author loophong
* @description 针对表【production_order_scheduling(主计划表)】的数据库操作Service
* @createDate 2025-01-17 14:34:39
*/
public interface IOrderSchedulingService extends IService<OrderSchedulingEntity> {


    /**
     * 查询订单信息列表
     *
     * @param orderSchedulingEntity 订单信息
     * @return 订单信息集合
     */
    public List<OrderSchedulingEntity> selectOrderSchedulingList(OrderSchedulingEntity orderSchedulingEntity);

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的订单信息主键集合
     * @return 结果
     */
    public int deleteOrderSchedulingByIds(Long[] ids);

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    public int deleteOrderSchedulingById(Long id);

    List<OrderSchedulingEntity> updateOrderData(Date date);

    /**
     * @description: 查询缺件订单列表
     * @author: hong
     * @date: 2025/2/27 15:43
     * @version: 1.0
     */
    List<OrderSchedulingEntity> selectMissingPartsList(OrderSchedulingEntity orderSchedulingEntity);

    /**
     * @description: 延期订单数据分析
     * @author: hong
     * @date: 2025/2/27 17:29
     * @version: 1.0
     */
    List<OrderSchedulingEntity> selectDataAnalysis(OrderSchedulingEntity orderSchedulingEntity);
}
