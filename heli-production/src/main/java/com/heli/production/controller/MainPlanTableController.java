package com.heli.production.controller;

import java.util.List;

import com.heli.production.domain.entity.MainPlanTable;
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
import com.heli.production.service.IMainPlanTableService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主计划表Controller
 *
 * @author hong
 * @date 2025-01-09
 */
@RestController
@RequestMapping("/production/mainPlanTable")
public class MainPlanTableController extends BaseController {
    @Autowired
    private IMainPlanTableService mainPlanTableService;

    /**
     * 查询主计划表列表
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:list')")
    @GetMapping("/list")
    public TableDataInfo list(MainPlanTable mainPlanTable) {
        startPage();
        List<MainPlanTable> list = mainPlanTableService.selectMainPlanTableList(mainPlanTable);
        return getDataTable(list);
    }

    /**
     * 导出主计划表列表
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:export')")
    @Log(title = "主计划表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MainPlanTable mainPlanTable) {
        List<MainPlanTable> list = mainPlanTableService.selectMainPlanTableList(mainPlanTable);
        ExcelUtil<MainPlanTable> util = new ExcelUtil<MainPlanTable>(MainPlanTable.class);
        util.exportExcel(response, list, "主计划表数据");
    }

    /**
     * 获取主计划表详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(mainPlanTableService.selectMainPlanTableById(id));
    }

    /**
     * 新增主计划表
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:add')")
    @Log(title = "主计划表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MainPlanTable mainPlanTable) {
        return toAjax(mainPlanTableService.insertMainPlanTable(mainPlanTable));
    }

    /**
     * 修改主计划表
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:edit')")
    @Log(title = "主计划表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MainPlanTable mainPlanTable) {
        return toAjax(mainPlanTableService.updateMainPlanTable(mainPlanTable));
    }

    /**
     * 删除主计划表
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:remove')")
    @Log(title = "主计划表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(mainPlanTableService.deleteMainPlanTableByIds(ids));
    }
}
