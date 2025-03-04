package com.heli.production.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heli.production.domain.entity.CapacityEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ForecastDTO {

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date date;

    private List<CapacityEntity> capacityList;
}
