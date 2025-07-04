package com.heli.production.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heli.production.domain.entity.DailyUsedCapacityEntity;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import com.heli.production.domain.vo.OrdersAndCapacityVO;
import com.heli.production.service.IOrderSchedulingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
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
import com.heli.production.service.IDailyUsedCapacityService;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Date;
import java.util.List;

/**
 * 每日使用的产能Controller
 *
 * @author hong
 * @date 2025-02-26
 */
@RestController
@RequestMapping("/production/dailyCapacity")
public class DailyUsedCapacityController extends BaseController {
    @Autowired
    private IDailyUsedCapacityService dailyUsedCapacityService;

    /**
     * 查询每日使用的产能列表
     */
    @PreAuthorize("@ss.hasPermi('production:dailyCapacity:list')")
    @GetMapping("/list")
    public TableDataInfo list(DailyUsedCapacityEntity dailyUsedCapacity) {
        startPage();
        List<DailyUsedCapacityEntity> list = dailyUsedCapacityService.selectDailyUsedCapacityList(dailyUsedCapacity);
        return getDataTable(list);
    }

    /**
     * 导出每日使用的产能列表
     */
    @PreAuthorize("@ss.hasPermi('production:dailyCapacity:export')")
    @Log(title = "每日使用的产能", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DailyUsedCapacityEntity dailyUsedCapacity) {
        List<DailyUsedCapacityEntity> list = dailyUsedCapacityService.selectDailyUsedCapacityList(dailyUsedCapacity);
        ExcelUtil<DailyUsedCapacityEntity> util = new ExcelUtil<DailyUsedCapacityEntity>(DailyUsedCapacityEntity.class);
        util.exportExcel(response, list, "每日使用的产能数据");
    }

    /**
     * 获取每日使用的产能详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:dailyCapacity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(dailyUsedCapacityService.selectDailyUsedCapacityById(id));
    }

    /**
     * 新增每日使用的产能
     */
    @PreAuthorize("@ss.hasPermi('production:dailyCapacity:add')")
    @Log(title = "每日使用的产能", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DailyUsedCapacityEntity dailyUsedCapacity) {
        return toAjax(dailyUsedCapacityService.insertDailyUsedCapacity(dailyUsedCapacity));
    }

    /**
     * 修改每日使用的产能
     */
    @PreAuthorize("@ss.hasPermi('production:dailyCapacity:edit')")
    @Log(title = "每日使用的产能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DailyUsedCapacityEntity dailyUsedCapacity) {
        return toAjax(dailyUsedCapacityService.updateDailyUsedCapacity(dailyUsedCapacity));
    }

    /**
     * 删除每日使用的产能
     */
    @PreAuthorize("@ss.hasPermi('production:dailyCapacity:remove')")
    @Log(title = "每日使用的产能", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(dailyUsedCapacityService.deleteDailyUsedCapacityByIds(ids));
    }
}
