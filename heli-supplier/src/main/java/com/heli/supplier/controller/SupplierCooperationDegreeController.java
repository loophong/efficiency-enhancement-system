package com.heli.supplier.controller;

import java.util.List;

import com.ruoyi.common.core.domain.R;
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
import com.heli.supplier.domain.SupplierCooperationDegree;
import com.heli.supplier.service.ISupplierCooperationDegreeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新产品研发配合程度Controller
 * 
 * @author wll
 * @date 2025-03-05
 */
@Slf4j
@RestController
@RequestMapping("/supplier/degree")
public class SupplierCooperationDegreeController extends BaseController
{
    @Autowired
    private ISupplierCooperationDegreeService supplierCooperationDegreeService;

    /**
     * 查询新产品研发配合程度列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:degree:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierCooperationDegree supplierCooperationDegree)
    {
        startPage();
        List<SupplierCooperationDegree> list = supplierCooperationDegreeService.selectSupplierCooperationDegreeList(supplierCooperationDegree);
        return getDataTable(list);
    }

    /**
     * 导出新产品研发配合程度列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:degree:export')")
    @Log(title = "新产品研发配合程度", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierCooperationDegree supplierCooperationDegree)
    {
        List<SupplierCooperationDegree> list = supplierCooperationDegreeService.selectSupplierCooperationDegreeList(supplierCooperationDegree);
        ExcelUtil<SupplierCooperationDegree> util = new ExcelUtil<SupplierCooperationDegree>(SupplierCooperationDegree.class);
        util.exportExcel(response, list, "新产品研发配合程度数据");
    }

    /**
     * 获取新产品研发配合程度详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:degree:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierCooperationDegreeService.selectSupplierCooperationDegreeById(id));
//        return R.ok().setMsg("xxxxxx");
//        return R.ok().setMsg("bbbbbb");
    }

    /**
     * 新增新产品研发配合程度
     */
    @PreAuthorize("@ss.hasPermi('supplier:degree:add')")
    @Log(title = "新产品研发配合程度", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierCooperationDegree supplierCooperationDegree)
    {
        return toAjax(supplierCooperationDegreeService.insertSupplierCooperationDegree(supplierCooperationDegree));
    }

    /**
     * 修改新产品研发配合程度
     */
    @PreAuthorize("@ss.hasPermi('supplier:degree:edit')")
    @Log(title = "新产品研发配合程度", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierCooperationDegree supplierCooperationDegree)
    {
        return toAjax(supplierCooperationDegreeService.updateSupplierCooperationDegree(supplierCooperationDegree));
    }

    /**
     * 删除新产品研发配合程度
     */
    @PreAuthorize("@ss.hasPermi('supplier:degree:remove')")
    @Log(title = "新产品研发配合程度", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierCooperationDegreeService.deleteSupplierCooperationDegreeByIds(ids));
    }
}
