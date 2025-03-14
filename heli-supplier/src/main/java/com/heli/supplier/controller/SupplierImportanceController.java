package com.heli.supplier.controller;

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
import com.heli.supplier.domain.SupplierImportance;
import com.heli.supplier.service.ISupplierImportanceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 重要度Controller
 * 
 * @author wll
 * @date 2025-03-07
 */
@RestController
@RequestMapping("/supplier/importance")
public class SupplierImportanceController extends BaseController
{
    @Autowired
    private ISupplierImportanceService supplierImportanceService;

    /**
     * 查询重要度列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:importance:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierImportance supplierImportance)
    {
        startPage();
        List<SupplierImportance> list = supplierImportanceService.selectSupplierImportanceList(supplierImportance);
        return getDataTable(list);
    }

    /**
     * 导出重要度列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:importance:export')")
    @Log(title = "重要度", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierImportance supplierImportance)
    {
        List<SupplierImportance> list = supplierImportanceService.selectSupplierImportanceList(supplierImportance);
        ExcelUtil<SupplierImportance> util = new ExcelUtil<SupplierImportance>(SupplierImportance.class);
        util.exportExcel(response, list, "重要度数据");
    }

    /**
     * 获取重要度详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:importance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierImportanceService.selectSupplierImportanceById(id));
    }

    /**
     * 新增重要度
     */
    @PreAuthorize("@ss.hasPermi('supplier:importance:add')")
    @Log(title = "重要度", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierImportance supplierImportance)
    {
        return toAjax(supplierImportanceService.insertSupplierImportance(supplierImportance));
    }

    /**
     * 修改重要度
     */
    @PreAuthorize("@ss.hasPermi('supplier:importance:edit')")
    @Log(title = "重要度", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierImportance supplierImportance)
    {
        return toAjax(supplierImportanceService.updateSupplierImportance(supplierImportance));
    }

    /**
     * 删除重要度
     */
    @PreAuthorize("@ss.hasPermi('supplier:importance:remove')")
    @Log(title = "重要度", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierImportanceService.deleteSupplierImportanceByIds(ids));
    }
}
