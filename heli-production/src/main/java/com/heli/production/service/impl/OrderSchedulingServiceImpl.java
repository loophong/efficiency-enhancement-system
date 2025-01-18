package com.heli.production.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.CycleEntity;
import com.heli.production.domain.entity.MainPlanTableEntity;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import com.heli.production.service.ICycleService;
import com.heli.production.service.IMainPlanTableService;
import com.heli.production.service.IOrderSchedulingService;
import com.heli.production.mapper.OrderSchedulingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_order_scheduling(主计划表)】的数据库操作Service实现
 * @createDate 2025-01-17 14:34:39
 */
@Slf4j
@Service
public class OrderSchedulingServiceImpl extends ServiceImpl<OrderSchedulingMapper, OrderSchedulingEntity> implements IOrderSchedulingService {

    @Autowired
    private OrderSchedulingMapper orderSchedulingMapper;

    @Autowired
    private IMainPlanTableService mainPlanTableService;

    @Autowired
    private ICycleService cycleService;

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

    @Override
    public void updateOrderData(Date date) {
        List<MainPlanTableEntity> list = mainPlanTableService.list(new LambdaQueryWrapper<MainPlanTableEntity>().eq(MainPlanTableEntity::getUploadDate, date));

        for (MainPlanTableEntity mainPlanTableEntity : list) {
            CycleEntity cycle = cycleService.getOne(new LambdaQueryWrapper<CycleEntity>().eq(CycleEntity::getVehicleModel, mainPlanTableEntity.getVehicleModel()));

            OrderSchedulingEntity orderSchedulingEntity = new OrderSchedulingEntity();
            BeanUtils.copyProperties(mainPlanTableEntity, orderSchedulingEntity);

//            log.info("orderSchedulingEntity: {}", orderSchedulingEntity);
//            log.info("mainPlanTableEntity: {}", mainPlanTableEntity);
//            log.info("cycle: {}", cycle);
            // 逻辑处理 统计信息
            if (cycle != null) {
                // 设置生产周期
                orderSchedulingEntity.setProductionCycle(cycle.getProductionCycle());

                // 计算最晚上线时间
                Date systemDeliveryDate = orderSchedulingEntity.getSystemDeliveryDate();
                Integer productionCycle = cycle.getProductionCycle();
                Date lastOnlineDate = new Date(systemDeliveryDate.getTime() - productionCycle * 24 * 60 * 60 * 1000);
                orderSchedulingEntity.setLatestOnlineDate(lastOnlineDate);
                log.info("系统交付日期: {}", systemDeliveryDate);
                log.info("生产周期: {}", productionCycle);
                log.info("最晚上线时间: {}", lastOnlineDate);

                // 判断是否超期

                // 判断是否加急
                String remarks = orderSchedulingEntity.getRemarks();
                if (remarks != null && (remarks.contains("加急") || remarks.contains("特急"))) {
                    orderSchedulingEntity.setIsUrgent(1);
                }else {
                    orderSchedulingEntity.setIsUrgent(0);
                }
                // 设置产能类型
                orderSchedulingEntity.setCapacityType(cycle.getCapacityVehicleModel());
            }

            OrderSchedulingEntity exist = this.getOne(new LambdaQueryWrapper<OrderSchedulingEntity>().eq(OrderSchedulingEntity::getOrderNumber, mainPlanTableEntity.getOrderNumber()));

            if (exist == null) {
                log.info("不存在该订单号，新增订单信息");
                orderSchedulingEntity.setId(null);
                orderSchedulingEntity.setIsScheduling(0);
            } else {
                log.info("存在该订单号");
                orderSchedulingEntity.setId(exist.getId());
            }
            this.saveOrUpdate(orderSchedulingEntity);
        }
    }
}




