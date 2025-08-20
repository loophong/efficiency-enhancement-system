package com.heli.production.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heli.production.domain.dto.CharParamsDTO;
import com.heli.production.domain.dto.ForecastDTO;
import com.heli.production.domain.entity.CapacityEntity;
import com.heli.production.domain.entity.HistoryOrderStatisticsEntity;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import com.heli.production.domain.entity.VehicleTypeEntity;
import com.heli.production.service.IHistoryOrderStatisticsService;
import com.heli.production.service.IVehicleTypeService;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 历史订单统计Controller
 *
 * @author hong
 * @date 2025-02-20
 */
@Slf4j
@RestController
@RequestMapping("/production/orderStatistics")
public class HistoryOrderStatisticsController extends BaseController {
    @Autowired
    private IHistoryOrderStatisticsService historyOrderStatisticsEntityService;
    @Autowired
    private IVehicleTypeService vehicleTypeService;

    /**
     * 查询历史订单统计列表
     */
    @PreAuthorize("@ss.hasPermi('production:orderStatistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(HistoryOrderStatisticsEntity historyOrderStatisticsEntity) {
        startPage();
        List<HistoryOrderStatisticsEntity> list = historyOrderStatisticsEntityService.selectHistoryOrderStatisticsEntityList(historyOrderStatisticsEntity);
        return getDataTable(list);
    }

    /**
     * 获取历史订单统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:orderStatistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(historyOrderStatisticsEntityService.getById(id));
    }

    /**
     * 新增历史订单统计
     */
    @PreAuthorize("@ss.hasPermi('production:orderStatistics:add')")
    @Log(title = "历史订单统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HistoryOrderStatisticsEntity historyOrderStatisticsEntity) {
        return toAjax(historyOrderStatisticsEntityService.save(historyOrderStatisticsEntity));
    }

    /**
     * 修改历史订单统计
     */
    @PreAuthorize("@ss.hasPermi('production:orderStatistics:edit')")
    @Log(title = "历史订单统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HistoryOrderStatisticsEntity historyOrderStatisticsEntity) {
        return toAjax(historyOrderStatisticsEntityService.saveOrUpdate(historyOrderStatisticsEntity));
    }

    /**
     * 删除历史订单统计
     */
    @PreAuthorize("@ss.hasPermi('production:orderStatistics:remove')")
    @Log(title = "历史订单统计", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(historyOrderStatisticsEntityService.removeByIds(Arrays.asList(ids)));
    }


    /**
     * @description: 订单图表
     * @author: hong
     * @date: 2025/3/4 16:10
     * @version: 1.0
     */
    @PostMapping("/charData")
    @PreAuthorize("@ss.hasPermi('production:orderStatistics:list')")
    public AjaxResult getCharData(@RequestBody CharParamsDTO charParamsDTO) {
        log.info("charParamsDTO:{}", charParamsDTO);
        LambdaQueryWrapper<HistoryOrderStatisticsEntity> queryWrapper = new LambdaQueryWrapper<HistoryOrderStatisticsEntity>()
                .eq(charParamsDTO.getVehicleModel() != null, HistoryOrderStatisticsEntity::getVehicleModel, charParamsDTO.getVehicleModel())
                .eq(charParamsDTO.getCapacityType() != null, HistoryOrderStatisticsEntity::getCapacityType, charParamsDTO.getCapacityType())
                .between(charParamsDTO.getStartTime() != null, HistoryOrderStatisticsEntity::getYearAndMonth, charParamsDTO.getStartTime(), charParamsDTO.getEndTime());
        List<HistoryOrderStatisticsEntity> list = historyOrderStatisticsEntityService.list(queryWrapper);
        // 将列表中小于等于3的全部去掉
//        list.removeIf(historyOrderStatisticsEntity -> historyOrderStatisticsEntity.getQuantity() <= 10);
        return success(list);
    }

    /**
     * @description: 订单预测
     * @author: hong
     * @date: 2025/3/4 16:10
     * @version: 1.0
     */
    @PostMapping("/forecast")
    @PreAuthorize("@ss.hasPermi('production:orderStatistics:forecast')")
    public AjaxResult forecast(@RequestBody ForecastDTO forecastDTO) {
        log.info("forecastDTO:{}", forecastDTO);
        Date date = forecastDTO.getDate();
        Date newdate = DateUtils.getPreviousYear(date);
        log.info("date:{}", newdate);
        List<CapacityEntity> capacityList = forecastDTO.getCapacityList();
        ArrayList<OrderSchedulingEntity> orderSchedulingEntities = new ArrayList<>();
        // 根据日期查询历史订单统计
        capacityList.forEach(capacityEntity -> {
            if (capacityEntity.getProductionQuantity() > 0) {
                List<HistoryOrderStatisticsEntity> list = historyOrderStatisticsEntityService.list(
                        new LambdaQueryWrapper<HistoryOrderStatisticsEntity>()
                                .eq(HistoryOrderStatisticsEntity::getYearAndMonth, newdate)
                                .eq(HistoryOrderStatisticsEntity::getCapacityType, capacityEntity.getCapacityType())
                                .orderByDesc(HistoryOrderStatisticsEntity::getQuantity)
                );
                log.info("查询出来的结果为list:{}", list);
                Integer quantity = capacityEntity.getProductionQuantity();
                // 用查询的结果计算比例，按比例进行排产
                int total = 0;
                for (HistoryOrderStatisticsEntity historyOrderStatisticsEntity : list) {
                    // 计算总数
                    total += historyOrderStatisticsEntity.getQuantity();
                }
                // 遍历，计算比例，四舍五入进行排产
                for (HistoryOrderStatisticsEntity historyOrderStatisticsEntity : list) {
                    Long num = historyOrderStatisticsEntity.getQuantity();
                    double percent = (double) num / total;
                    int orderNum = (int) Math.round(quantity * percent);
                    log.info("percent:{},orderNum:{},quantity:{}", percent, orderNum, quantity);

                    if (orderNum > 0) {
                        OrderSchedulingEntity orderSchedulingEntity = new OrderSchedulingEntity();
                        orderSchedulingEntity.setQuantity(orderNum);
                        orderSchedulingEntity.setVehicleModel(historyOrderStatisticsEntity.getVehicleModel());
                        orderSchedulingEntity.setMast(historyOrderStatisticsEntity.getMastHeight());
                        orderSchedulingEntity.setRemarks("预测订单");
                        orderSchedulingEntity.setCapacityType(historyOrderStatisticsEntity.getCapacityType());

                        orderSchedulingEntities.add(orderSchedulingEntity);
                    }

                }
            }
        });

        return success(orderSchedulingEntities);
    }


}
