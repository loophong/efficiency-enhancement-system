package com.heli.production.controller;


import com.heli.production.domain.entity.CycleEntity;
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
import com.heli.production.service.ICycleService;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.List;

/**
 * 产能Controller
 *
 * @author hong
 * @date 2025-01-17
 */
@RestController
@RequestMapping("/production/cycle")
public class CycleController extends BaseController {
    @Autowired
    private ICycleService productionCycleService;

    /**
     * 查询产能列表
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:list')")
    @GetMapping("/list")
    public TableDataInfo list(CycleEntity productionCycle) {
        startPage();
        List<CycleEntity> list = productionCycleService.selectProductionCycleList(productionCycle);
        return getDataTable(list);
    }

    /**
     * 导出产能列表
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:export')")
    @Log(title = "产能", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CycleEntity productionCycle) {
        List<CycleEntity> list = productionCycleService.selectProductionCycleList(productionCycle);
        ExcelUtil<CycleEntity> util = new ExcelUtil<CycleEntity>(CycleEntity.class);
        util.exportExcel(response, list, "产能数据");
    }

    /**
     * 获取产能详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(productionCycleService.selectProductionCycleById(id));
    }

    /**
     * 新增产能
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:add')")
    @Log(title = "产能", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CycleEntity productionCycle) {
        return toAjax(productionCycleService.insertProductionCycle(productionCycle));
    }

    /**
     * 修改产能
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:edit')")
    @Log(title = "产能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CycleEntity productionCycle) {
        return toAjax(productionCycleService.updateProductionCycle(productionCycle));
    }

    /**
     * 删除产能
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:remove')")
    @Log(title = "产能", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(productionCycleService.deleteProductionCycleByIds(ids));
    }
}
