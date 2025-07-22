package com.heli.supplier.controller;

import java.util.Date;
import java.util.List;

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
import com.heli.supplier.domain.SupplierReturnRate;
import com.heli.supplier.service.ISupplierReturnRateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 售后返修率Controller
 * 
 * @author wll
 * @date 2025-02-27
 */
@Slf4j
@RestController
@RequestMapping("/supplier/returnrate")
public class SupplierReturnRateController extends BaseController
{
    @Autowired
    private ISupplierReturnRateService supplierReturnRateService;


    @Log(title = "[返修率表]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile, Date uploadMonth) {
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
        try {
            supplierReturnRateService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }
    /**
     * 查询售后返修率列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:returnrate:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierReturnRate supplierReturnRate)
    {
        startPage();
        List<SupplierReturnRate> list = supplierReturnRateService.selectSupplierReturnRateList(supplierReturnRate);
        return getDataTable(list);
    }

    /**
     * 导出售后返修率列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:returnrate:export')")
    @Log(title = "售后返修率", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierReturnRate supplierReturnRate)
    {
        List<SupplierReturnRate> list = supplierReturnRateService.selectSupplierReturnRateList(supplierReturnRate);
        ExcelUtil<SupplierReturnRate> util = new ExcelUtil<SupplierReturnRate>(SupplierReturnRate.class);
        util.exportExcel(response, list, "售后返修率");
    }

    /**
     * 获取售后返修率详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:returnrate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierReturnRateService.selectSupplierReturnRateById(id));
    }

    /**
     * 新增售后返修率
     */
    @PreAuthorize("@ss.hasPermi('supplier:returnrate:add')")
    @Log(title = "售后返修率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierReturnRate supplierReturnRate)
    {
        return toAjax(supplierReturnRateService.insertSupplierReturnRate(supplierReturnRate));
    }

    /**
     * 修改售后返修率
     */
    @PreAuthorize("@ss.hasPermi('supplier:returnrate:edit')")
    @Log(title = "售后返修率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierReturnRate supplierReturnRate)
    {
        return toAjax(supplierReturnRateService.updateSupplierReturnRate(supplierReturnRate));
    }

    /**
     * 删除售后返修率
     */
    @PreAuthorize("@ss.hasPermi('supplier:returnrate:remove')")
    @Log(title = "售后返修率", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierReturnRateService.deleteSupplierReturnRateByIds(ids));
    }
}
