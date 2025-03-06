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
import com.heli.supplier.domain.SupplierThreePack;
import com.heli.supplier.service.ISupplierThreePackService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 三包发货及时率Controller
 * 
 * @author wll
 * @date 2025-02-26
 */
@Slf4j
@RestController
@RequestMapping("/supplier/threepack")
public class SupplierThreePackController extends BaseController
{
    @Autowired
    private ISupplierThreePackService supplierThreePackService;

    /**
     * 查询三包发货及时率列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:threepack:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierThreePack supplierThreePack)
    {
        startPage();
        List<SupplierThreePack> list = supplierThreePackService.selectSupplierThreePackList(supplierThreePack);
        return getDataTable(list);
    }

    /**
     * 导出三包发货及时率列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:threepack:export')")
    @Log(title = "三包发货及时率", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierThreePack supplierThreePack)
    {
        List<SupplierThreePack> list = supplierThreePackService.selectSupplierThreePackList(supplierThreePack);
        ExcelUtil<SupplierThreePack> util = new ExcelUtil<SupplierThreePack>(SupplierThreePack.class);
        util.exportExcel(response, list, "三包发货及时率数据");
    }

    /**
     * 获取三包发货及时率详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:threepack:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierThreePackService.selectSupplierThreePackById(id));
    }

    /**
     * 新增三包发货及时率
     */
    @PreAuthorize("@ss.hasPermi('supplier:threepack:add')")
    @Log(title = "三包发货及时率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierThreePack supplierThreePack)
    {
        return toAjax(supplierThreePackService.insertSupplierThreePack(supplierThreePack));
    }

    /**
     * 修改三包发货及时率
     */
    @PreAuthorize("@ss.hasPermi('supplier:threepack:edit')")
    @Log(title = "三包发货及时率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierThreePack supplierThreePack)
    {
        return toAjax(supplierThreePackService.updateSupplierThreePack(supplierThreePack));
    }

    /**
     * 删除三包发货及时率
     */
    @PreAuthorize("@ss.hasPermi('supplier:threepack:remove')")
    @Log(title = "三包发货及时率", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierThreePackService.deleteSupplierThreePackByIds(ids));
    }
}
