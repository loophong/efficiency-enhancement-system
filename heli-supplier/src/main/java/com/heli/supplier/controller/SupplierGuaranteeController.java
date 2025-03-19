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
import com.heli.supplier.domain.SupplierGuarantee;
import com.heli.supplier.service.ISupplierGuaranteeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 供货保障Controller
 *
 * @author wll
 * @date 2025-03-19
 */
@Slf4j
@RestController
@RequestMapping("/supplier/guarantee")
public class SupplierGuaranteeController extends BaseController
{
    @Autowired
    private ISupplierGuaranteeService supplierGuaranteeService;

    @Log(title = "[供货保障表]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile, Date uploadMonth) {
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
        try {
            supplierGuaranteeService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);

        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }


    /**
     * 查询供货保障列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:guarantee:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierGuarantee supplierGuarantee)
    {
        startPage();
        List<SupplierGuarantee> list = supplierGuaranteeService.selectSupplierGuaranteeList(supplierGuarantee);
        return getDataTable(list);
    }

    /**
     * 导出供货保障列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:guarantee:export')")
    @Log(title = "供货保障", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierGuarantee supplierGuarantee)
    {
        List<SupplierGuarantee> list = supplierGuaranteeService.selectSupplierGuaranteeList(supplierGuarantee);
        ExcelUtil<SupplierGuarantee> util = new ExcelUtil<SupplierGuarantee>(SupplierGuarantee.class);
        util.exportExcel(response, list, "供货保障数据");
    }

    /**
     * 获取供货保障详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:guarantee:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(supplierGuaranteeService.selectSupplierGuaranteeById(id));
    }

    /**
     * 新增供货保障
     */
    @PreAuthorize("@ss.hasPermi('supplier:guarantee:add')")
    @Log(title = "供货保障", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierGuarantee supplierGuarantee)
    {
        return toAjax(supplierGuaranteeService.insertSupplierGuarantee(supplierGuarantee));
    }

    /**
     * 修改供货保障
     */
    @PreAuthorize("@ss.hasPermi('supplier:guarantee:edit')")
    @Log(title = "供货保障", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierGuarantee supplierGuarantee)
    {
        return toAjax(supplierGuaranteeService.updateSupplierGuarantee(supplierGuarantee));
    }

    /**
     * 删除供货保障
     */
    @PreAuthorize("@ss.hasPermi('supplier:guarantee:remove')")
    @Log(title = "供货保障", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(supplierGuaranteeService.deleteSupplierGuaranteeByIds(ids));
    }
}
