package com.heli.production.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.heli.production.domain.dto.SchedulingDTO;
import com.heli.production.domain.entity.DailyPlanEntity;
import com.heli.production.domain.entity.DailyUsedCapacityEntity;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import com.heli.production.domain.entity.WorkdayEntity;
import com.heli.production.domain.vo.OrdersAndCapacityVO;
import com.heli.production.service.IDailyPlanService;
import com.heli.production.service.IDailyUsedCapacityService;
import com.heli.production.service.IOrderSchedulingService;
import com.heli.production.service.IWorkdayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.stream.Collectors;

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
    @Autowired
    private IDailyPlanService dailyPlanService;


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
        List<OrderSchedulingEntity> list = orderSchedulingService.list(
                new LambdaQueryWrapper<OrderSchedulingEntity>()
                        .eq(OrderSchedulingEntity::getIsScheduling, 1)
                        .eq(OrderSchedulingEntity::getOnlineDate, date));
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
        // 删除日生产计划
        dailyPlanService.remove(
                new LambdaQueryWrapper<DailyPlanEntity>().eq(DailyPlanEntity::getOnlineDate, date)
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

        Date date = schedulingDTO.getDate();
        log.info("排产日期: {}", date);

        // 如果订单排产，则更新，不排产则设置排产状态为未排产，并修改上线时间为null
        schedulingDTO.getOrderSchedulingList().forEach(orderSchedulingEntity -> {
            if (orderSchedulingEntity.getIsScheduling().equals(1)) {
                orderSchedulingService.updateById(orderSchedulingEntity);
            } else {
                log.info("取消订单排产，id：" + orderSchedulingEntity.getId());
                orderSchedulingService.update(
                        new LambdaUpdateWrapper<OrderSchedulingEntity>()
                                .eq(OrderSchedulingEntity::getId, orderSchedulingEntity.getId())
                                .set(OrderSchedulingEntity::getIsScheduling, 0).setSql("online_date = NULL"));
            }
        });

        dailyUsedCapacityService.saveOrUpdateBatch(schedulingDTO.getDailyUsedCapacityList());

        int size = schedulingDTO.getOrderSchedulingList().stream().filter((orderSchedulingEntity) -> orderSchedulingEntity.getIsScheduling().equals(1)).toList().size();
        workdayService.update(
                new LambdaUpdateWrapper<WorkdayEntity>()
                        .eq(WorkdayEntity::getDate, schedulingDTO.getDailyUsedCapacityList().get(0).getProductionDate())
                        .set(WorkdayEntity::getProductStatus, size > 0 ? 1 : 0)
        );

        // 生成日计划
        // 1、如果当日已排产，则移除原有计划
        dailyPlanService.remove(new LambdaQueryWrapper<DailyPlanEntity>().eq(DailyPlanEntity::getOnlineDate, date));
        // 2、创建新的排产计划
        schedulingDTO.getOrderSchedulingList().forEach(item -> {
            if (item.getIsScheduling().equals(1)) {
                OrderSchedulingEntity orderSchedulingEntity = orderSchedulingService.getById(item.getId());
                for (int i = 0; i < orderSchedulingEntity.getQuantity(); i++) {
                    DailyPlanEntity dailyPlanEntity = new DailyPlanEntity();
                    dailyPlanEntity.setContractNumber(orderSchedulingEntity.getContractNumber());
                    dailyPlanEntity.setVehicleModel(orderSchedulingEntity.getVehicleModel());
                    dailyPlanEntity.setMast(orderSchedulingEntity.getMast());
                    dailyPlanEntity.setQuantity(1L);
                    dailyPlanEntity.setAttachments(orderSchedulingEntity.getAttachments());
                    // 阀片需要转换
                    // 取出第一个字符，并将其从中文转换为阿拉伯数字
                    char c = orderSchedulingEntity.getValvePlate().charAt(0);
                    dailyPlanEntity.setValvePlate(String.valueOf(convertor(c)));
                    // 配置信息需要转换
                    String tmp = "";
                    if (orderSchedulingEntity.getAirFilter() != null) {
                        tmp += orderSchedulingEntity.getAirFilter();
                    }
                    if (orderSchedulingEntity.getTires() != null) {
                        tmp += orderSchedulingEntity.getTires();
                    }
                    if (orderSchedulingEntity.getConfiguration() != null) {
                        tmp += orderSchedulingEntity.getConfiguration();
                    }
                    dailyPlanEntity.setDescriptiveConfigurationInfo(tmp);

                    dailyPlanEntity.setSystemDeliveryDate(orderSchedulingEntity.getSystemDeliveryDate());
                    dailyPlanEntity.setOrderDate(orderSchedulingEntity.getOrderDate());
                    dailyPlanEntity.setBranch(orderSchedulingEntity.getBranch());
                    dailyPlanEntity.setOnlineDate(orderSchedulingEntity.getOnlineDate());

                    dailyPlanService.save(dailyPlanEntity);
                }
            }
        });

        return success();
    }

    public int convertor(char c) {
        return switch (c) {
            case '一' -> 1;
            case '二', '两' -> 2;
            case '三' -> 3;
            case '四' -> 4;
            case '五' -> 5;
            case '六' -> 6;
            case '七' -> 7;
            case '八' -> 8;
            case '九' -> 9;
            case '十' -> 10;
            default -> 0;
        };
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public AjaxResult getOrderList(@RequestParam(required = false) Date date) {
        log.info("date: {}", date);
        // 查询未排产订单
        List<OrderSchedulingEntity> list = orderSchedulingService.list(
                new LambdaQueryWrapper<OrderSchedulingEntity>()
                        .eq(OrderSchedulingEntity::getIsScheduling, 0));
        // 查询已排产订单
        if (date != null) {
            List<OrderSchedulingEntity> schedulingList = orderSchedulingService.list(
                    new LambdaQueryWrapper<OrderSchedulingEntity>()
                            .eq(OrderSchedulingEntity::getIsScheduling, 1)
                            .eq(OrderSchedulingEntity::getOnlineDate, date));
            if (!schedulingList.isEmpty()) {
                log.info("当日已排产，订单列表如下: {}", schedulingList);
                list.addAll(schedulingList);
            }
        }
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
