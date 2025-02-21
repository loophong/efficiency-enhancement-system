package com.heli.production.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.HistoryOrderEntity;
import com.heli.production.domain.entity.HistoryOrderStatisticsEntity;
import com.heli.production.mapper.HistoryOrderMapper;
import com.heli.production.mapper.HistoryOrderStatisticsMapper;
import com.heli.production.service.IHistoryOrderStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 历史订单统计Service业务层处理
 *
 * @author hong
 * @date 2025-02-20
 */
@Service
public class HistoryOrderStatisticsServiceImpl extends ServiceImpl<HistoryOrderStatisticsMapper, HistoryOrderStatisticsEntity> implements IHistoryOrderStatisticsService {
    @Autowired
    private HistoryOrderStatisticsMapper historyOrderStatisticsEntityMapper;

    /**
     * 查询历史订单统计列表
     *
     * @param historyOrderStatisticsEntity 历史订单统计
     * @return 历史订单统计
     */
    @Override
    public List<HistoryOrderStatisticsEntity> selectHistoryOrderStatisticsEntityList(HistoryOrderStatisticsEntity historyOrderStatisticsEntity) {
        return historyOrderStatisticsEntityMapper.selectHistoryOrderStatisticsEntityList(historyOrderStatisticsEntity);
    }

}
