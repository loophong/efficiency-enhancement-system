package com.heli.production.controller;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heli.production.domain.dto.CharParamsDTO;
import com.heli.production.domain.entity.HistoryOrderStatisticsEntity;
import com.heli.production.service.IHistoryOrderStatisticsService;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 查询历史订单统计列表
     */
//    @PreAuthorize("@ss.hasPermi('production:orderStatistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(HistoryOrderStatisticsEntity historyOrderStatisticsEntity) {
        startPage();
        List<HistoryOrderStatisticsEntity> list = historyOrderStatisticsEntityService.selectHistoryOrderStatisticsEntityList(historyOrderStatisticsEntity);
        return getDataTable(list);
    }

    /**
     * 获取历史订单统计详细信息
     */
//    @PreAuthorize("@ss.hasPermi('production:orderStatistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(historyOrderStatisticsEntityService.getById(id));
    }

    /**
     * 新增历史订单统计
     */
//    @PreAuthorize("@ss.hasPermi('production:orderStatistics:add')")
    @Log(title = "历史订单统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HistoryOrderStatisticsEntity historyOrderStatisticsEntity) {
        return toAjax(historyOrderStatisticsEntityService.save(historyOrderStatisticsEntity));
    }

    /**
     * 修改历史订单统计
     */
//    @PreAuthorize("@ss.hasPermi('production:orderStatistics:edit')")
    @Log(title = "历史订单统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HistoryOrderStatisticsEntity historyOrderStatisticsEntity) {
        return toAjax(historyOrderStatisticsEntityService.saveOrUpdate(historyOrderStatisticsEntity));
    }

    /**
     * 删除历史订单统计
     */
//    @PreAuthorize("@ss.hasPermi('production:orderStatistics:remove')")
    @Log(title = "历史订单统计", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(historyOrderStatisticsEntityService.removeByIds(Arrays.asList(ids)));
    }


    @PostMapping("/charData")
    public AjaxResult getCharData(@RequestBody CharParamsDTO charParamsDTO) {
        log.info("charParamsDTO:{}", charParamsDTO);
        LambdaQueryWrapper<HistoryOrderStatisticsEntity> queryWrapper = new LambdaQueryWrapper<HistoryOrderStatisticsEntity>()
                .eq(charParamsDTO.getVehicleModel() != null, HistoryOrderStatisticsEntity::getVehicleModel, charParamsDTO.getVehicleModel())
                .eq(charParamsDTO.getCapacityType() != null, HistoryOrderStatisticsEntity::getCapacityType, charParamsDTO.getCapacityType())
                .between(charParamsDTO.getStartTime() != null, HistoryOrderStatisticsEntity::getYearAndMonth, charParamsDTO.getStartTime(), charParamsDTO.getEndTime());
        List<HistoryOrderStatisticsEntity> list = historyOrderStatisticsEntityService.list(queryWrapper);
        return success(list);
    }


}
