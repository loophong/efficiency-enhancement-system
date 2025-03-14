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
import com.heli.supplier.domain.SupplierChange;
import com.heli.supplier.service.ISupplierChangeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 擅自变更产品材质参数尺寸Controller
 * 
 * @author wll
 * @date 2025-03-07
 */
@RestController
@Slf4j
@RequestMapping("/supplier/change")
public class SupplierChangeController extends BaseController
{
    @Autowired
    private ISupplierChangeService supplierChangeService;

    /**
     * 查询擅自变更产品材质参数尺寸列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:change:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierChange supplierChange)
    {
        startPage();
        List<SupplierChange> list = supplierChangeService.selectSupplierChangeList(supplierChange);
        return getDataTable(list);
    }

    /**
     * 导出擅自变更产品材质参数尺寸列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:change:export')")
    @Log(title = "擅自变更产品材质参数尺寸", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierChange supplierChange)
    {
        List<SupplierChange> list = supplierChangeService.selectSupplierChangeList(supplierChange);
        ExcelUtil<SupplierChange> util = new ExcelUtil<SupplierChange>(SupplierChange.class);
        util.exportExcel(response, list, "擅自变更产品材质参数尺寸数据");
    }

    /**
     * 获取擅自变更产品材质参数尺寸详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:change:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierChangeService.selectSupplierChangeById(id));
    }

    /**
     * 新增擅自变更产品材质参数尺寸
     */
    @PreAuthorize("@ss.hasPermi('supplier:change:add')")
    @Log(title = "擅自变更产品材质参数尺寸", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierChange supplierChange)
    {
        return toAjax(supplierChangeService.insertSupplierChange(supplierChange));
    }

    /**
     * 修改擅自变更产品材质参数尺寸
     */
    @PreAuthorize("@ss.hasPermi('supplier:change:edit')")
    @Log(title = "擅自变更产品材质参数尺寸", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierChange supplierChange)
    {
        return toAjax(supplierChangeService.updateSupplierChange(supplierChange));
    }

    /**
     * 删除擅自变更产品材质参数尺寸
     */
    @PreAuthorize("@ss.hasPermi('supplier:change:remove')")
    @Log(title = "擅自变更产品材质参数尺寸", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierChangeService.deleteSupplierChangeByIds(ids));
    }
}
