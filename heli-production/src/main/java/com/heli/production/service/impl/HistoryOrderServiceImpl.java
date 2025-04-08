package com.heli.production.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.Listener.HistoryOrderListener;
import com.heli.production.domain.entity.HistoryOrderEntity;
import com.heli.production.domain.entity.HistoryOrderStatisticsEntity;
import com.heli.production.service.IHistoryOrderService;
import com.heli.production.mapper.HistoryOrderMapper;
import com.heli.production.service.IHistoryOrderStatisticsService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author loophong
 * @description 针对表【production_history_order】的数据库操作Service实现
 * @createDate 2025-01-19 10:47:10
 */
@Slf4j
@Service
public class HistoryOrderServiceImpl extends ServiceImpl<HistoryOrderMapper, HistoryOrderEntity> implements IHistoryOrderService {
    @Autowired
    private HistoryOrderMapper historyOrderMapper;
    @Autowired
    private IHistoryOrderStatisticsService orderStatisticsService;

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

    @Override
    public AjaxResult readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date date) {

        // 清空当年的数据
        this.remove(new LambdaQueryWrapper<HistoryOrderEntity>().eq(HistoryOrderEntity::getOrderYear, date));
        // 读取文件内容
        log.info("开始读取文件: {}", fileName);

        try {

            EasyExcel.read(excelFile.getInputStream(), HistoryOrderEntity.class, new HistoryOrderListener(historyOrderMapper, date)).sheet().doRead();
            log.info("开始统计处理数据");

            // 统计数据
            Date tmp = date;
            for (int i = 0; i < 12; i++) {
                List<HistoryOrderStatisticsEntity> result = historyOrderMapper.statistics(tmp);
                log.info("统计结果" + result);
                orderStatisticsService.remove(
                        new LambdaQueryWrapper<HistoryOrderStatisticsEntity>()
                                .eq(HistoryOrderStatisticsEntity::getYearAndMonth, tmp));
                for (HistoryOrderStatisticsEntity entity : result) {
                    entity.setYearAndMonth(tmp);
                }
                orderStatisticsService.saveBatch(result);
                tmp = DateUtils.getNextMonth(tmp);
            }
            log.info("读取文件成功: {}", fileName);

            // 统计历史订单中，产能型号为空的车型
            Map<String, Object> emptyCapacityType = orderStatisticsService.getEmptyCapacityType(date);
            log.info("统计历史订单中，产能型号为空的车型Map" + emptyCapacityType);
            // 将其key收集为list
            ArrayList<String> list = new ArrayList<>(emptyCapacityType.keySet());
            log.info("统计历史订单中，产能型号为空的车型List" + list);

            return AjaxResult.success("读取文件成功: " + fileName).put("emptyCapacityType", list);

        } catch (Exception e) {
            log.info("读取文件失败: {}", e.getMessage());
            return AjaxResult.error("读取文件失败,当前上传的文件为：" + fileName);
        }
    }


}




