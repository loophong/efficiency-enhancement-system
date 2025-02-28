package com.heli.production.domain.vo;

import com.heli.production.domain.entity.DailyUsedCapacityEntity;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import lombok.Data;

import java.util.List;

@Data
public class OrdersAndCapacityVO {
    private List<DailyUsedCapacityEntity> dailyUsedCapacityEntities;

    private List<OrderSchedulingEntity> orderSchedulingEntities;
}
