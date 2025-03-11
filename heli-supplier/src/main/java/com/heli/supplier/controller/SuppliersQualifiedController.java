package com.heli.supplier.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.ruoyi.common.exception.ServiceException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.service.ISuppliersQualifiedService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 供应商Controller
 *
 * @author ruoyi
 * @date 2025-01-17
 */
@Slf4j
@RestController
@RequestMapping("/supplier/qualified")
public class SuppliersQualifiedController extends BaseController {
    @Autowired
    private ISuppliersQualifiedService suppliersQualifiedService;

    @Log(title = "[合格供应商表]上传", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('production:qualified:import')")
    @PostMapping("/import")
    @Transactional
//    public void importTable(MultipartFile excelFile,Date date) {
    public void importTable(MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {

            suppliersQualifiedService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile);

        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }

    /**
     * 查询供应商列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:qualified:list')")
    @GetMapping("/list")
    public TableDataInfo list(SuppliersQualified suppliersQualified) {
        startPage();
        List<SuppliersQualified> list = suppliersQualifiedService.selectSuppliersQualifiedList(suppliersQualified);
        return getDataTable(list);
    }

    /**
     * 导出供应商列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:qualified:export')")
    @Log(title = "供应商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SuppliersQualified suppliersQualified) {
        List<SuppliersQualified> list = suppliersQualifiedService.selectSuppliersQualifiedList(suppliersQualified);
        ExcelUtil<SuppliersQualified> util = new ExcelUtil<SuppliersQualified>(SuppliersQualified.class);
        util.exportExcel(response, list, "供应商数据");
    }

    /**
     * 获取供应商详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:qualified:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(suppliersQualifiedService.selectSuppliersQualifiedById(id));
    }

    /**
     * 新增供应商
     */
    @PreAuthorize("@ss.hasPermi('supplier:qualified:add')")
    @Log(title = "供应商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SuppliersQualified suppliersQualified) {
        log.info("suppliersQualified"+suppliersQualified);
        return toAjax(suppliersQualifiedService.saveOrUpdate(suppliersQualified));
    }

    /**
     * 修改供应商
     */
    @PreAuthorize("@ss.hasPermi('supplier:qualified:edit')")
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SuppliersQualified suppliersQualified) {
        return toAjax(suppliersQualifiedService.saveOrUpdate(suppliersQualified));
    }

    /**
     * 删除供应商
     */
    @PreAuthorize("@ss.hasPermi('supplier:qualified:remove')")
    @Log(title = "供应商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(suppliersQualifiedService.deleteSuppliersQualifiedByIds(ids));
    }

    /**
     * 查询供应商列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:qualified:list')")
    @GetMapping("/all")
    public AjaxResult all() {
        LambdaQueryWrapper<SuppliersQualified> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SuppliersQualified> select = queryWrapper.select(SuppliersQualified::getSupplierCode, SuppliersQualified::getSupplierName);
        List<SuppliersQualified> list = suppliersQualifiedService.list(select);
        return success(list);
    }
}
