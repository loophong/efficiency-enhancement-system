package com.heli.production.controller;

import java.util.List;

import com.heli.production.domain.entity.ScanRecords;
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
import com.heli.production.service.IScanRecordsService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 整机扫码记录Controller
 *
 * @author ruoyi
 * @date 2025-02-27
 */
@RestController
@RequestMapping("/production/scanRecords")
public class ScanRecordsController extends BaseController {
    @Autowired
    private IScanRecordsService scanRecordsService;

    /**
     * 查询整机扫码记录列表
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScanRecords scanRecords) {
        startPage();
        List<ScanRecords> list = scanRecordsService.selectScanRecordsList(scanRecords);
        return getDataTable(list);
    }

    /**
     * 导出整机扫码记录列表
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:export')")
    @Log(title = "整机扫码记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScanRecords scanRecords) {
        List<ScanRecords> list = scanRecordsService.selectScanRecordsList(scanRecords);
        ExcelUtil<ScanRecords> util = new ExcelUtil<ScanRecords>(ScanRecords.class);
        util.exportExcel(response, list, "整机扫码记录数据");
    }

    /**
     * 获取整机扫码记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(scanRecordsService.selectScanRecordsById(id));
    }

    /**
     * 新增整机扫码记录
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:add')")
    @Log(title = "整机扫码记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScanRecords scanRecords) {
        return toAjax(scanRecordsService.insertScanRecords(scanRecords));
    }

    /**
     * 修改整机扫码记录
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:edit')")
    @Log(title = "整机扫码记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScanRecords scanRecords) {
        return toAjax(scanRecordsService.updateScanRecords(scanRecords));
    }

    /**
     * 删除整机扫码记录
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:remove')")
    @Log(title = "整机扫码记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(scanRecordsService.deleteScanRecordsByIds(ids));
    }
}
