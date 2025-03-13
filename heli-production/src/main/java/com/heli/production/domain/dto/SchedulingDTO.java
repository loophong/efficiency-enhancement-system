package com.heli.production.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heli.production.domain.entity.DailyUsedCapacityEntity;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class SchedulingDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private List<OrderSchedulingEntity> orderSchedulingList;

    private List<DailyUsedCapacityEntity> dailyUsedCapacityList;
}
