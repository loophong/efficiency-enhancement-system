package com.heli.supplier.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.service.ISuppliersQualifiedService;
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
import com.heli.supplier.domain.SupplierQualityIncidents;
import com.heli.supplier.service.ISupplierQualityIncidentsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 质量事故Controller
 *
 * @author ruoyi
 * @date 2025-02-20
 */
@Slf4j
@RestController
@RequestMapping("/supplier/incidents")
public class SupplierQualityIncidentsController extends BaseController {
    @Autowired
    private ISupplierQualityIncidentsService supplierQualityIncidentsService;
    @Autowired
    private ISuppliersQualifiedService suppliersQualifiedService;

    /**
     * 查询质量事故列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:incidents:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierQualityIncidents supplierQualityIncidents) {
        startPage();
        List<SupplierQualityIncidents> list = supplierQualityIncidentsService.selectSupplierQualityIncidentsList(supplierQualityIncidents);
        return getDataTable(list);
    }

    /**
     * 导出质量事故列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:incidents:export')")
    @Log(title = "质量事故", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierQualityIncidents supplierQualityIncidents) {
        List<SupplierQualityIncidents> list = supplierQualityIncidentsService.selectSupplierQualityIncidentsList(supplierQualityIncidents);
        ExcelUtil<SupplierQualityIncidents> util = new ExcelUtil<SupplierQualityIncidents>(SupplierQualityIncidents.class);
        util.exportExcel(response, list, "质量事故数据");
    }

    /**
     * 获取质量事故详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:incidents:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(supplierQualityIncidentsService.selectSupplierQualityIncidentsById(id));
    }

    /**
     * 新增质量事故
     */
    @PreAuthorize("@ss.hasPermi('supplier:incidents:add')")
    @Log(title = "质量事故", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierQualityIncidents supplierQualityIncidents) {
        String supplierName = supplierQualityIncidents.getSupplierName();

        String supplierCode = suppliersQualifiedService.getOne(
                        new LambdaQueryWrapper<SuppliersQualified>()
                                .eq(SuppliersQualified::getSupplierName, supplierName))
                .getSupplierCode();
        QueryWrapper<SuppliersQualified> queryWrapper =
                new QueryWrapper<SuppliersQualified>()
                        .eq("supplier_name", supplierName);

        supplierQualityIncidents.setSupplierCode(supplierCode);
//        supplierQualityIncidents.setUploadName(getUsername());
        log.info("supplierQualityIncidents:" + supplierQualityIncidents);

        return toAjax(supplierQualityIncidentsService.insertSupplierQualityIncidents(supplierQualityIncidents));
    }

    /**
     * 修改质量事故
     */
    @PreAuthorize("@ss.hasPermi('supplier:incidents:edit')")
    @Log(title = "质量事故", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierQualityIncidents supplierQualityIncidents) {
        return toAjax(supplierQualityIncidentsService.updateSupplierQualityIncidents(supplierQualityIncidents));
    }

    /**
     * 删除质量事故
     */
    @PreAuthorize("@ss.hasPermi('supplier:incidents:remove')")
    @Log(title = "质量事故", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(supplierQualityIncidentsService.deleteSupplierQualityIncidentsByIds(ids));
    }
}
