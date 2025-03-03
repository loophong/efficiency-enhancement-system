package com.heli.production.domain.dto;

import com.heli.production.domain.entity.DailyUsedCapacityEntity;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import lombok.Data;

import java.util.List;

@Data
public class SchedulingDTO {

    private List<OrderSchedulingEntity> orderSchedulingList;

    private List<DailyUsedCapacityEntity> dailyUsedCapacityList;
}
