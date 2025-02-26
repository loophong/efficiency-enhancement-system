package com.heli.production.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.heli.production.domain.dto.SchedulingDTO;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import com.heli.production.domain.entity.WorkdayEntity;
import com.heli.production.service.IDailyUsedCapacityService;
import com.heli.production.service.IOrderSchedulingService;
import com.heli.production.service.IWorkdayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Arrays;
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
