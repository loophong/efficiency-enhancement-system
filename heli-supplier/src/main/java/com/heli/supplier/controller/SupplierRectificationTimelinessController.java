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
import com.heli.supplier.domain.SupplierRectificationTimeliness;
import com.heli.supplier.service.ISupplierRectificationTimelinessService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产品技术问题整改及时性Controller
 * 
 * @author wll
 * @date 2025-03-05
 */
@Slf4j
@RestController
@RequestMapping("/supplier/timeliness")
public class SupplierRectificationTimelinessController extends BaseController
{
    @Autowired
    private ISupplierRectificationTimelinessService supplierRectificationTimelinessService;

    /**
     * 查询产品技术问题整改及时性列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:timeliness:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierRectificationTimeliness supplierRectificationTimeliness)
    {
        startPage();
        List<SupplierRectificationTimeliness> list = supplierRectificationTimelinessService.selectSupplierRectificationTimelinessList(supplierRectificationTimeliness);
        return getDataTable(list);
    }

    /**
     * 导出产品技术问题整改及时性列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:timeliness:export')")
    @Log(title = "产品技术问题整改及时性", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierRectificationTimeliness supplierRectificationTimeliness)
    {
        List<SupplierRectificationTimeliness> list = supplierRectificationTimelinessService.selectSupplierRectificationTimelinessList(supplierRectificationTimeliness);
        ExcelUtil<SupplierRectificationTimeliness> util = new ExcelUtil<SupplierRectificationTimeliness>(SupplierRectificationTimeliness.class);
        util.exportExcel(response, list, "产品技术问题整改及时性数据");
    }

    /**
     * 获取产品技术问题整改及时性详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:timeliness:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierRectificationTimelinessService.selectSupplierRectificationTimelinessById(id));
    }

    /**
     * 新增产品技术问题整改及时性
     */
    @PreAuthorize("@ss.hasPermi('supplier:timeliness:add')")
    @Log(title = "产品技术问题整改及时性", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierRectificationTimeliness supplierRectificationTimeliness)
    {
        return toAjax(supplierRectificationTimelinessService.insertSupplierRectificationTimeliness(supplierRectificationTimeliness));
    }

    /**
     * 修改产品技术问题整改及时性
     */
    @PreAuthorize("@ss.hasPermi('supplier:timeliness:edit')")
    @Log(title = "产品技术问题整改及时性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierRectificationTimeliness supplierRectificationTimeliness)
    {
        return toAjax(supplierRectificationTimelinessService.updateSupplierRectificationTimeliness(supplierRectificationTimeliness));
    }

    /**
     * 删除产品技术问题整改及时性
     */
    @PreAuthorize("@ss.hasPermi('supplier:timeliness:remove')")
    @Log(title = "产品技术问题整改及时性", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierRectificationTimelinessService.deleteSupplierRectificationTimelinessByIds(ids));
    }
}
