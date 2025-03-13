package com.heli.production.domain.vo;

import com.heli.production.domain.entity.PlanCoSignEntity;
import com.heli.production.domain.entity.DailyPlanEntity;
import lombok.Data;

import java.util.List;

@Data
public class CoSignInfoVO {
    private PlanCoSignEntity planCoSign;
    private List<DailyPlanEntity> dailyPlanList;
}
