package com.heli.production.mapper;

import com.heli.production.domain.entity.OrderSchedulingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_order_scheduling(主计划表)】的数据库操作Mapper
 * @createDate 2025-01-17 14:34:39
 * @Entity com.heli.production.domain.ProductionOrderScheduling
 */
public interface OrderSchedulingMapper extends BaseMapper<OrderSchedulingEntity> {

    /**
     * 查询订单信息列表
     *
     * @param orderSchedulingEntity 订单信息
     * @return 订单信息集合
     */
    public List<OrderSchedulingEntity> selectOrderSchedulingList(OrderSchedulingEntity orderSchedulingEntity);

    /**
     * 删除订单信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    public int deleteOrderSchedulingById(Long id);

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderSchedulingByIds(Long[] ids);

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




