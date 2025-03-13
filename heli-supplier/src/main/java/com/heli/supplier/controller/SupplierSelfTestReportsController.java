package com.heli.supplier.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import com.heli.supplier.domain.SupplierSelfTestReports;
import com.heli.supplier.service.ISupplierSelfTestReportsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 自检报告Controller
 * 
 * @author wll
 * @date 2025-02-26
 */
@Slf4j
@RestController
@RequestMapping("/supplier/checkreports")
public class SupplierSelfTestReportsController extends BaseController
{
    @Autowired
    private ISupplierSelfTestReportsService supplierSelfTestReportsService;

    /**
     * 查询自检报告列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:checkreports:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierSelfTestReports supplierSelfTestReports)
    {
        startPage();
        List<SupplierSelfTestReports> list = supplierSelfTestReportsService.selectSupplierSelfTestReportsList(supplierSelfTestReports);
        return getDataTable(list);
    }

    /**
     * 导出自检报告列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:checkreports:export')")
    @Log(title = "自检报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierSelfTestReports supplierSelfTestReports)
    {
        List<SupplierSelfTestReports> list = supplierSelfTestReportsService.selectSupplierSelfTestReportsList(supplierSelfTestReports);
        ExcelUtil<SupplierSelfTestReports> util = new ExcelUtil<SupplierSelfTestReports>(SupplierSelfTestReports.class);
        util.exportExcel(response, list, "自检报告数据");
    }

    /**
     * 获取自检报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:checkreports:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(supplierSelfTestReportsService.selectSupplierSelfTestReportsById(id));
    }

    /**
     * 新增自检报告
     */
    @PreAuthorize("@ss.hasPermi('supplier:checkreports:add')")
    @Log(title = "自检报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierSelfTestReports supplierSelfTestReports)
    {
        return toAjax(supplierSelfTestReportsService.insertSupplierSelfTestReports(supplierSelfTestReports));
    }

    /**
     * 修改自检报告
     */
    @PreAuthorize("@ss.hasPermi('supplier:checkreports:edit')")
    @Log(title = "自检报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierSelfTestReports supplierSelfTestReports)
    {
        return toAjax(supplierSelfTestReportsService.updateSupplierSelfTestReports(supplierSelfTestReports));
    }

    /**
     * 删除自检报告
     */
    @PreAuthorize("@ss.hasPermi('supplier:checkreports:remove')")
    @Log(title = "自检报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(supplierSelfTestReportsService.deleteSupplierSelfTestReportsByIds(ids));
    }
}
