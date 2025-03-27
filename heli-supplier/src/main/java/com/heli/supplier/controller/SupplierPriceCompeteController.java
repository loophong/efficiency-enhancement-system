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
import com.heli.supplier.domain.SupplierPriceCompete;
import com.heli.supplier.service.ISupplierPriceCompeteService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 价格竞争力Controller
 * 
 * @author wll
 * @date 2025-03-05
 */
@Slf4j
@RestController
@RequestMapping("/supplier/pricecompete")
public class SupplierPriceCompeteController extends BaseController
{
    @Autowired
    private ISupplierPriceCompeteService supplierPriceCompeteService;

    @Log(title = "[价格竞争力]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile, Date uploadMonth) {
//        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
        try {
            supplierPriceCompeteService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);

        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }

    /**
     * 查询价格竞争力列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricecompete:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierPriceCompete supplierPriceCompete)
    {
        startPage();
        List<SupplierPriceCompete> list = supplierPriceCompeteService.selectSupplierPriceCompeteList(supplierPriceCompete);
        return getDataTable(list);
    }

    /**
     * 导出价格竞争力列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricecompete:export')")
    @Log(title = "价格竞争力", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierPriceCompete supplierPriceCompete)
    {
        List<SupplierPriceCompete> list = supplierPriceCompeteService.selectSupplierPriceCompeteList(supplierPriceCompete);
        ExcelUtil<SupplierPriceCompete> util = new ExcelUtil<SupplierPriceCompete>(SupplierPriceCompete.class);
        util.exportExcel(response, list, "价格竞争力数据");
    }

    /**
     * 获取价格竞争力详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricecompete:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierPriceCompeteService.selectSupplierPriceCompeteById(id));
    }

    /**
     * 新增价格竞争力
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricecompete:add')")
    @Log(title = "价格竞争力", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierPriceCompete supplierPriceCompete)
    {
        return toAjax(supplierPriceCompeteService.insertSupplierPriceCompete(supplierPriceCompete));
    }

    /**
     * 修改价格竞争力
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricecompete:edit')")
    @Log(title = "价格竞争力", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierPriceCompete supplierPriceCompete)
    {
        return toAjax(supplierPriceCompeteService.updateSupplierPriceCompete(supplierPriceCompete));
    }

    /**
     * 删除价格竞争力
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricecompete:remove')")
    @Log(title = "价格竞争力", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierPriceCompeteService.deleteSupplierPriceCompeteByIds(ids));
    }
}
