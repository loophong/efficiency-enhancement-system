package com.heli.production.controller;

import com.heli.production.domain.entity.SpecialCasesEntity;
import com.heli.production.service.ISpecialCasesService;
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
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.List;

/**
 * 特殊情况Controller
 *
 * @author hong
 * @date 2025-02-25
 */
@RestController
@RequestMapping("/production/special")
public class SpecialCasesController extends BaseController {
    @Autowired
    private ISpecialCasesService specialCasesService;

    /**
     * 查询特殊情况列表
     */
    @PreAuthorize("@ss.hasPermi('production:special:list')")
    @GetMapping("/list")
    public TableDataInfo list(SpecialCasesEntity specialCases) {
        startPage();
        List<SpecialCasesEntity> list = specialCasesService.selectSpecialCasesList(specialCases);
        return getDataTable(list);
    }

    /**
     * 导出特殊情况列表
     */
    @PreAuthorize("@ss.hasPermi('production:special:export')")
    @Log(title = "特殊情况", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SpecialCasesEntity specialCases) {
        List<SpecialCasesEntity> list = specialCasesService.selectSpecialCasesList(specialCases);
        ExcelUtil<SpecialCasesEntity> util = new ExcelUtil<SpecialCasesEntity>(SpecialCasesEntity.class);
        util.exportExcel(response, list, "特殊情况数据");
    }

    /**
     * 获取特殊情况详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:special:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(specialCasesService.selectSpecialCasesById(id));
    }

    /**
     * 新增特殊情况
     */
    @PreAuthorize("@ss.hasPermi('production:special:add')")
    @Log(title = "特殊情况", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SpecialCasesEntity specialCases) {
        return toAjax(specialCasesService.insertSpecialCases(specialCases));
    }

    /**
     * 修改特殊情况
     */
    @PreAuthorize("@ss.hasPermi('production:special:edit')")
    @Log(title = "特殊情况", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SpecialCasesEntity specialCases) {
        return toAjax(specialCasesService.updateSpecialCases(specialCases));
    }

    /**
     * 删除特殊情况
     */
    @PreAuthorize("@ss.hasPermi('production:special:remove')")
    @Log(title = "特殊情况", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(specialCasesService.deleteSpecialCasesByIds(ids));
    }
}
