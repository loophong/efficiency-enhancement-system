package com.heli.production.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.HistoryOrderEntity;
import com.heli.production.service.IHistoryOrderService;
import com.heli.production.mapper.HistoryOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_history_order】的数据库操作Service实现
 * @createDate 2025-01-19 10:47:10
 */
@Service
public class HistoryOrderServiceImpl extends ServiceImpl<HistoryOrderMapper, HistoryOrderEntity> implements IHistoryOrderService {
    @Autowired
    private HistoryOrderMapper historyOrderMapper;

    /**
     * 查询历史订单
     *
     * @param id 历史订单主键
     * @return 历史订单
     */
    @Override
    public HistoryOrderEntity selectHistoryOrderById(String id) {
        return historyOrderMapper.selectHistoryOrderById(id);
    }

    /**
     * 查询历史订单列表
     *
     * @param historyOrder 历史订单
     * @return 历史订单
     */
    @Override
    public List<HistoryOrderEntity> selectHistoryOrderList(HistoryOrderEntity historyOrder) {
        return historyOrderMapper.selectHistoryOrderList(historyOrder);
    }

    /**
     * 新增历史订单
     *
     * @param historyOrder 历史订单
     * @return 结果
     */
    @Override
    public int insertHistoryOrder(HistoryOrderEntity historyOrder) {
        return historyOrderMapper.insertHistoryOrder(historyOrder);
    }

    /**
     * 修改历史订单
     *
     * @param historyOrder 历史订单
     * @return 结果
     */
    @Override
    public int updateHistoryOrder(HistoryOrderEntity historyOrder) {
        return historyOrderMapper.updateHistoryOrder(historyOrder);
    }

    /**
     * 批量删除历史订单
     *
     * @param ids 需要删除的历史订单主键
     * @return 结果
     */
    @Override
    public int deleteHistoryOrderByIds(String[] ids) {
        return historyOrderMapper.deleteHistoryOrderByIds(ids);
    }

    /**
     * 删除历史订单信息
     *
     * @param id 历史订单主键
     * @return 结果
     */
    @Override
    public int deleteHistoryOrderById(String id) {
        return historyOrderMapper.deleteHistoryOrderById(id);
    }
}




