package com.heli.production.controller;

import java.util.List;

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
import com.heli.production.domain.entity.DailyPlanEntity;
import com.heli.production.service.IDailyPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 日生产计划Controller
 *
 * @author hong
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/production/dailyPlan")
public class DailyPlanController extends BaseController {
    @Autowired
    private IDailyPlanService productionDailyPlanService;

    /**
     * 查询日生产计划列表
     */
    @PreAuthorize("@ss.hasPermi('production:dailyPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(DailyPlanEntity productionDailyPlan) {
        startPage();
        List<DailyPlanEntity> list = productionDailyPlanService.selectProductionDailyPlanList(productionDailyPlan);
        return getDataTable(list);
    }

    /**
     * 导出日生产计划列表
     */
    @PreAuthorize("@ss.hasPermi('production:dailyPlan:export')")
    @Log(title = "日生产计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DailyPlanEntity productionDailyPlan) {
        List<DailyPlanEntity> list = productionDailyPlanService.selectProductionDailyPlanList(productionDailyPlan);
        ExcelUtil<DailyPlanEntity> util = new ExcelUtil<DailyPlanEntity>(DailyPlanEntity.class);
        util.exportExcel(response, list, "日生产计划数据");
    }

    /**
     * 获取日生产计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:dailyPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(productionDailyPlanService.selectProductionDailyPlanById(id));
    }

    /**
     * 新增日生产计划
     */
    @PreAuthorize("@ss.hasPermi('production:dailyPlan:add')")
    @Log(title = "日生产计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DailyPlanEntity productionDailyPlan) {
        return toAjax(productionDailyPlanService.insertProductionDailyPlan(productionDailyPlan));
    }

    /**
     * 修改日生产计划
     */
    @PreAuthorize("@ss.hasPermi('production:dailyPlan:edit')")
    @Log(title = "日生产计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DailyPlanEntity productionDailyPlan) {
        return toAjax(productionDailyPlanService.updateProductionDailyPlan(productionDailyPlan));
    }

    /**
     * 删除日生产计划
     */
    @PreAuthorize("@ss.hasPermi('production:dailyPlan:remove')")
    @Log(title = "日生产计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(productionDailyPlanService.deleteProductionDailyPlanByIds(ids));
    }
}
