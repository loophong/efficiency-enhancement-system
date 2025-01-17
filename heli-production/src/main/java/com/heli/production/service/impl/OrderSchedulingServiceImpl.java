package com.heli.production.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import com.heli.production.service.IOrderSchedulingService;
import com.heli.production.mapper.OrderSchedulingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_order_scheduling(主计划表)】的数据库操作Service实现
 * @createDate 2025-01-17 14:34:39
 */
@Service
public class OrderSchedulingServiceImpl extends ServiceImpl<OrderSchedulingMapper, OrderSchedulingEntity> implements IOrderSchedulingService {

    @Autowired
    private OrderSchedulingMapper orderSchedulingMapper;

    /**
     * 查询订单信息列表
     *
     * @param orderSchedulingEntity 订单信息
     * @return 订单信息
     */
    @Override
    public List<OrderSchedulingEntity> selectOrderSchedulingList(OrderSchedulingEntity orderSchedulingEntity) {
        return orderSchedulingMapper.selectOrderSchedulingList(orderSchedulingEntity);
    }

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderSchedulingByIds(Long[] ids) {
        return orderSchedulingMapper.deleteOrderSchedulingByIds(ids);
    }

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderSchedulingById(Long id) {
        return orderSchedulingMapper.deleteOrderSchedulingById(id);
    }
}




