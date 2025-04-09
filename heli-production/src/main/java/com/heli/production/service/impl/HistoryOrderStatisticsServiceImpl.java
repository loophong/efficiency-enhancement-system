package com.heli.production.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.HistoryOrderEntity;
import com.heli.production.domain.entity.HistoryOrderStatisticsEntity;
import com.heli.production.mapper.HistoryOrderMapper;
import com.heli.production.mapper.HistoryOrderStatisticsMapper;
import com.heli.production.service.IHistoryOrderStatisticsService;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 历史订单统计Service业务层处理
 *
 * @author hong
 * @date 2025-02-20
 */
@Slf4j
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

    /**
     * @description: 统计历史订单中，产能型号为空的车型
     * @author: hong
     * @date: 2025/4/8 14:07
     * @version: 1.0
     */
    public Map<String, Object> getEmptyCapacityType(Date date) {
        HashMap<String, Object> map = new HashMap<>();
        int year = date.getYear() + 1900;
        log.info("date:" + DateUtils.formatOutput(date));
        log.info("year:" + year);
        List<HistoryOrderStatisticsEntity> orderEntities = baseMapper.selectList(
                new LambdaQueryWrapper<HistoryOrderStatisticsEntity>()
                        .like(HistoryOrderStatisticsEntity::getYearAndMonth, year));

        orderEntities.forEach(entity -> {
            if (entity.getCapacityType() == null || entity.getCapacityType().equals("")) {
                map.put(entity.getVehicleModel(), null);
            }
        });
        return map;
    }

}
