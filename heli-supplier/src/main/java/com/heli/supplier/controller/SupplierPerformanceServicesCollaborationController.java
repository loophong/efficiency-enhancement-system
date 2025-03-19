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
import com.heli.supplier.domain.SupplierPerformanceServicesCollaboration;
import com.heli.supplier.service.ISupplierPerformanceServicesCollaborationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 服务与协作Controller
 * 
 * @author wll
 * @date 2025-03-05
 */
@RestController
@Slf4j
@RequestMapping("/supplier/collaboration")
public class SupplierPerformanceServicesCollaborationController extends BaseController
{
    @Autowired
    private ISupplierPerformanceServicesCollaborationService supplierPerformanceServicesCollaborationService;
    @Log(title = "[服务于协作表]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile, Date uploadMonth) {
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
        try {
            supplierPerformanceServicesCollaborationService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);

        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }
    /**
     * 查询服务与协作列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:collaboration:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration)
    {
        startPage();
        List<SupplierPerformanceServicesCollaboration> list = supplierPerformanceServicesCollaborationService.selectSupplierPerformanceServicesCollaborationList(supplierPerformanceServicesCollaboration);
        return getDataTable(list);
    }

    /**
     * 导出服务与协作列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:collaboration:export')")
    @Log(title = "服务与协作", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration)
    {
        List<SupplierPerformanceServicesCollaboration> list = supplierPerformanceServicesCollaborationService.selectSupplierPerformanceServicesCollaborationList(supplierPerformanceServicesCollaboration);
        ExcelUtil<SupplierPerformanceServicesCollaboration> util = new ExcelUtil<SupplierPerformanceServicesCollaboration>(SupplierPerformanceServicesCollaboration.class);
        util.exportExcel(response, list, "服务与协作数据");
    }

    /**
     * 获取服务与协作详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:collaboration:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierPerformanceServicesCollaborationService.selectSupplierPerformanceServicesCollaborationById(id));
    }

    /**
     * 新增服务与协作
     */
    @PreAuthorize("@ss.hasPermi('supplier:collaboration:add')")
    @Log(title = "服务与协作", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration)
    {
        return toAjax(supplierPerformanceServicesCollaborationService.insertSupplierPerformanceServicesCollaboration(supplierPerformanceServicesCollaboration));
    }

    /**
     * 修改服务与协作
     */
    @PreAuthorize("@ss.hasPermi('supplier:collaboration:edit')")
    @Log(title = "服务与协作", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration)
    {
        return toAjax(supplierPerformanceServicesCollaborationService.updateSupplierPerformanceServicesCollaboration(supplierPerformanceServicesCollaboration));
    }

    /**
     * 删除服务与协作
     */
    @PreAuthorize("@ss.hasPermi('supplier:collaboration:remove')")
    @Log(title = "服务与协作", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierPerformanceServicesCollaborationService.deleteSupplierPerformanceServicesCollaborationByIds(ids));
    }
}
