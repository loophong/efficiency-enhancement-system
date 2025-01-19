package com.heli.production.mapper;

import com.heli.production.domain.entity.HistoryOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_history_order】的数据库操作Mapper
 * @createDate 2025-01-19 10:47:10
 * @Entity com.heli.production.domain.ProductionHistoryOrder
 */
public interface HistoryOrderMapper extends BaseMapper<HistoryOrderEntity> {

    public void batchInsert(List<HistoryOrderEntity> list);

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
     * 删除历史订单
     *
     * @param id 历史订单主键
     * @return 结果
     */
    public int deleteHistoryOrderById(String id);

    /**
     * 批量删除历史订单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHistoryOrderByIds(String[] ids);
}




