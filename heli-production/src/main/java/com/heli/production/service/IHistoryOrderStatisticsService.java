package com.heli.production.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.production.domain.entity.HistoryOrderEntity;
import com.heli.production.domain.entity.HistoryOrderStatisticsEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IHistoryOrderStatisticsService extends IService<HistoryOrderStatisticsEntity> {

    /**
     * 查询历史订单统计列表
     *
     * @param historyOrderStatisticsEntity 历史订单统计
     * @return 历史订单统计集合
     */
    public List<HistoryOrderStatisticsEntity> selectHistoryOrderStatisticsEntityList(HistoryOrderStatisticsEntity historyOrderStatisticsEntity);

    Map<String, Object> getEmptyCapacityType(Date date);
}