package com.heli.production.domain.vo;

import com.heli.production.domain.entity.CapacityEntity;
import com.heli.production.domain.entity.DailyUsedCapacityEntity;
import lombok.Data;

import java.util.List;

@Data
public class CapacityVO {
    private List<CapacityEntity> capacityEntities;

    private List<DailyUsedCapacityEntity> dailyUsedCapacityEntities;
}
