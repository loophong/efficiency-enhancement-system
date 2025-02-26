package com.heli.production.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.production.domain.entity.HistoryOrderEntity;
import com.heli.production.domain.entity.HistoryOrderStatisticsEntity;

import java.util.List;

public interface HistoryOrderStatisticsMapper extends BaseMapper<HistoryOrderStatisticsEntity> {

    /**
     * 查询历史订单统计列表
     *
     * @param historyOrderStatisticsEntity 历史订单统计
     * @return 历史订单统计集合
     */
    public List<HistoryOrderStatisticsEntity> selectHistoryOrderStatisticsEntityList(HistoryOrderStatisticsEntity historyOrderStatisticsEntity);

}
