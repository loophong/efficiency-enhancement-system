package com.heli.production.service;

import com.heli.production.domain.entity.HistoryOrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_history_order】的数据库操作Service
 * @createDate 2025-01-19 10:47:10
 */
public interface IHistoryOrderService extends IService<HistoryOrderEntity> {
    /**
     * 查询历史订单
     *
     * @param id 历史订单主键
     * @return 历史订单
     */
    public HistoryOrderEntity selectHistoryOrderById(String id);

    /**
     * 查询历史订单列表
     *
     * @param historyOrder 历史订单
     * @return 历史订单集合
     */
    public List<HistoryOrderEntity> selectHistoryOrderList(HistoryOrderEntity historyOrder);

    /**
     * 新增历史订单
     *
     * @param historyOrder 历史订单
     * @return 结果
     */
    public int insertHistoryOrder(HistoryOrderEntity historyOrder);

    /**
     * 修改历史订单
     *
     * @param historyOrder 历史订单
     * @return 结果
     */
    public int updateHistoryOrder(HistoryOrderEntity historyOrder);

    /**
     * 批量删除历史订单
     *
     * @param ids 需要删除的历史订单主键集合
     * @return 结果
     */
    public int deleteHistoryOrderByIds(String[] ids);

    /**
     * 删除历史订单信息
     *
     * @param id 历史订单主键
     * @return 结果
     */
    public int deleteHistoryOrderById(String id);
}
