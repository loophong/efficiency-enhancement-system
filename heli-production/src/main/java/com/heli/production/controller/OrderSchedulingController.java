package com.heli.production.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.heli.production.domain.dto.SchedulingDTO;
import com.heli.production.domain.entity.DailyUsedCapacityEntity;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import com.heli.production.domain.entity.WorkdayEntity;
import com.heli.production.domain.vo.OrdersAndCapacityVO;
import com.heli.production.service.IDailyUsedCapacityService;
import com.heli.production.service.IOrderSchedulingService;
import com.heli.production.service.IWorkdayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 订单信息Controller
 *
 * @author hong
 * @date 2025-01-17
 */
@Slf4j
@RestController
@RequestMapping("/production/scheduling")
public class OrderSchedulingController extends BaseController {
    @Autowired
    private IOrderSchedulingService orderSchedulingService;
    @Autowired
    private IDailyUsedCapacityService dailyUsedCapacityService;
    @Autowired
    private IWorkdayService workdayService;


    /**
     * @description: 通过日期 获取订单和排产信息
     * @author: hong
     * @date: 2025/2/28 14:59
     * @version: 1.0
     */
    @GetMapping("/getOrdersAndCapacityInfoByDate")
    public AjaxResult list(@RequestParam Date date) {
        OrdersAndCapacityVO ordersAndCapacityVO = new OrdersAndCapacityVO();
        List<DailyUsedCapacityEntity> dailyUsedCapacityEntities = dailyUsedCapacityService.list(new LambdaQueryWrapper<DailyUsedCapacityEntity>().eq(DailyUsedCapacityEntity::getProductionDate, date));
        List<OrderSchedulingEntity> list = orderSchedulingService.list(new LambdaQueryWrapper<OrderSchedulingEntity>().eq(OrderSchedulingEntity::getOnlineDate, date));
        ordersAndCapacityVO.setDailyUsedCapacityEntities(dailyUsedCapacityEntities);
        ordersAndCapacityVO.setOrderSchedulingEntities(list);
        return success(ordersAndCapacityVO);
    }


    /**
     * @description: 取消排产
     * @author: hong
     * @date: 2025/2/28 14:59
     * @version: 1.0
     */
    @GetMapping("/cancel")
    public AjaxResult cancelSchedulingOrders(@RequestParam Date date) {
        log.info("取消订单排产，时间：" + date);
        orderSchedulingService.update(
                new LambdaUpdateWrapper<OrderSchedulingEntity>()
                        .eq(OrderSchedulingEntity::getIsScheduling, 1)
                        .eq(OrderSchedulingEntity::getOnlineDate, date)
                        .set(OrderSchedulingEntity::getIsScheduling, 0)
                        .setSql("online_date = NULL")
        );
        dailyUsedCapacityService.remove(
                new LambdaQueryWrapper<DailyUsedCapacityEntity>()
                        .eq(DailyUsedCapacityEntity::getProductionDate, date));
        workdayService.update(
                new LambdaUpdateWrapper<WorkdayEntity>()
                        .eq(WorkdayEntity::getDate, date)
                        .set(WorkdayEntity::getProductStatus, 0)
        );
        return success();
    }

    /**
     * @description: 订单排产
     * @author: hong
     * @date: 2025/2/26 17:46
     * @version: 1.0
     */
    //    @PreAuthorize("@ss.hasPermi('production:scheduling:list')")
    @PostMapping("/orders")
    public AjaxResult schedulingOrders(@RequestBody SchedulingDTO schedulingDTO) {
        log.info("排产订单信息" + schedulingDTO.getOrderSchedulingList());
        log.info("使用产能表" + schedulingDTO.getDailyUsedCapacityList());

        log.info("排产订单信息: {}", schedulingDTO.getOrderSchedulingList());
        log.info("使用产能表: {}", schedulingDTO.getDailyUsedCapacityList());

        log.info("size:{}", schedulingDTO.getOrderSchedulingList().size());
        log.info("id:{}", schedulingDTO.getOrderSchedulingList().get(1).getId());

        orderSchedulingService.updateBatchById(schedulingDTO.getOrderSchedulingList());
        dailyUsedCapacityService.saveOrUpdateBatch(schedulingDTO.getDailyUsedCapacityList());
        workdayService.update(
                new LambdaUpdateWrapper<WorkdayEntity>()
                        .eq(WorkdayEntity::getDate, schedulingDTO.getDailyUsedCapacityList().get(0).getProductionDate())
                        .set(WorkdayEntity::getProductStatus, 1)
        );
        return success();
    }

    /**
     * @description: 获取未排产的加急订单列表
     * @author: hong
     * @date: 2025/1/18 16:28
     * @version: 1.0
     */
    @PreAuthorize("@ss.hasPermi('production:scheduling:list')")
    @GetMapping("/getUrgentOrder")
    public AjaxResult getUrgentOrderList() {
        List<OrderSchedulingEntity> list = orderSchedulingService.list(
                new LambdaQueryWrapper<OrderSchedulingEntity>()
                        .eq(OrderSchedulingEntity::getIsUrgent, 1)
                        .eq(OrderSchedulingEntity::getIsScheduling, 0));
        return success(list);
    }

    /**
     * @description: 获取未排产的订单列表
     * @author: hong
     * @date: 2025/2/17 15:47
     * @version: 1.0
     */
    @PreAuthorize("@ss.hasPermi('production:scheduling:list')")
    @GetMapping("/getOrderList")
    public AjaxResult getOrderList() {
        List<OrderSchedulingEntity> list = orderSchedulingService.list(
                new LambdaQueryWrapper<OrderSchedulingEntity>()
                        .eq(OrderSchedulingEntity::getIsScheduling, 0));
        return success(list);
    }

    /**
     * 查询订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('production:scheduling:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderSchedulingEntity orderSchedulingEntity) {
        startPage();
        List<OrderSchedulingEntity> list = orderSchedulingService.selectOrderSchedulingList(orderSchedulingEntity);
        return getDataTable(list);
    }

    /**
     * @description: 查询缺件订单列表
     * @author: hong
     * @date: 2025/2/27 15:43
     * @version: 1.0
     */
    @PreAuthorize("@ss.hasPermi('production:scheduling:list')")
    @GetMapping("/listMissingParts")
    public TableDataInfo listMissingParts(OrderSchedulingEntity orderSchedulingEntity) {
        startPage();
        List<OrderSchedulingEntity> list = orderSchedulingService.selectMissingPartsList(orderSchedulingEntity);
        return getDataTable(list);
    }

    /**
     * @description: 延期订单数据分析
     * @author: hong
     * @date: 2025/2/27 17:29
     * @version: 1.0
     */
    @PreAuthorize("@ss.hasPermi('production:scheduling:list')")
    @GetMapping("/dataAnalysis")
    public TableDataInfo dataAnalysis(OrderSchedulingEntity orderSchedulingEntity) {
        startPage();
        List<OrderSchedulingEntity> list = orderSchedulingService.selectDataAnalysis(orderSchedulingEntity);
        return getDataTable(list);
    }


    /**
     * 获取订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:scheduling:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(orderSchedulingService.getById(id));
    }

    /**
     * 新增订单信息
     */
    @PreAuthorize("@ss.hasPermi('production:scheduling:add')")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderSchedulingEntity orderSchedulingEntity) {
        return toAjax(orderSchedulingService.saveOrUpdate(orderSchedulingEntity));
    }

    /**
     * 修改订单信息
     */
    @PreAuthorize("@ss.hasPermi('production:scheduling:edit')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderSchedulingEntity orderSchedulingEntity) {
        return toAjax(orderSchedulingService.saveOrUpdate(orderSchedulingEntity));
    }

    /**
     * 删除订单信息
     */
    @PreAuthorize("@ss.hasPermi('production:scheduling:remove')")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(orderSchedulingService.deleteOrderSchedulingByIds(ids));
    }
}
