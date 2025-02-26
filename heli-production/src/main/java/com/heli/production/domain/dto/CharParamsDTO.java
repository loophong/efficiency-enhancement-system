package com.heli.production.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CharParamsDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date endTime;

    String vehicleModel;

    String capacityType;

}
