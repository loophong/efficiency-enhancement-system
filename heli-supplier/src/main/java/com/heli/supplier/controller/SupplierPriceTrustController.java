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
import com.heli.supplier.domain.SupplierPriceTrust;
import com.heli.supplier.service.ISupplierPriceTrustService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 价格诚信Controller
 * 
 * @author wll
 * @date 2025-02-28
 */
@Slf4j
@RestController
@RequestMapping("/supplier/pricetrust")
public class SupplierPriceTrustController extends BaseController
{
    @Autowired
    private ISupplierPriceTrustService supplierPriceTrustService;
    @Log(title = "[价格诚信]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile, Date uploadMonth) {
//        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
        try {
            supplierPriceTrustService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);

        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }
    /**
     * 查询价格诚信列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricetrust:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierPriceTrust supplierPriceTrust)
    {
        startPage();
        List<SupplierPriceTrust> list = supplierPriceTrustService.selectSupplierPriceTrustList(supplierPriceTrust);
        return getDataTable(list);
    }

    /**
     * 导出价格诚信列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricetrust:export')")
    @Log(title = "价格诚信", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierPriceTrust supplierPriceTrust)
    {
        List<SupplierPriceTrust> list = supplierPriceTrustService.selectSupplierPriceTrustList(supplierPriceTrust);
        ExcelUtil<SupplierPriceTrust> util = new ExcelUtil<SupplierPriceTrust>(SupplierPriceTrust.class);
        util.exportExcel(response, list, "价格诚信数据");
    }

    /**
     * 获取价格诚信详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricetrust:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierPriceTrustService.selectSupplierPriceTrustById(id));
    }

    /**
     * 新增价格诚信
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricetrust:add')")
    @Log(title = "价格诚信", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierPriceTrust supplierPriceTrust)
    {
        return toAjax(supplierPriceTrustService.insertSupplierPriceTrust(supplierPriceTrust));
    }

    /**
     * 修改价格诚信
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricetrust:edit')")
    @Log(title = "价格诚信", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierPriceTrust supplierPriceTrust)
    {
        return toAjax(supplierPriceTrustService.updateSupplierPriceTrust(supplierPriceTrust));
    }

    /**
     * 删除价格诚信
     */
    @PreAuthorize("@ss.hasPermi('supplier:pricetrust:remove')")
    @Log(title = "价格诚信", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierPriceTrustService.deleteSupplierPriceTrustByIds(ids));
    }
}
