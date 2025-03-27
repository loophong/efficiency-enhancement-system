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
import com.heli.supplier.domain.SupplierReduceSupport;
import com.heli.supplier.service.ISupplierReduceSupportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 降本支持Controller
 * 
 * @author wll
 * @date 2025-03-06
 */
@Slf4j
@RestController
@RequestMapping("/supplier/support")
public class SupplierReduceSupportController extends BaseController
{
    @Autowired
    private ISupplierReduceSupportService supplierReduceSupportService;


    @Log(title = "[降本支持表]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile, Date uploadMonth) {
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
//        log.info("传入的uploadMonth为 " + uploadMonth);
//        MultipartFile excelFile = uploadFileDTO.getExcelFile();
//        Date uploadMonth = uploadFileDTO.getUploadMonth();
        try {
            supplierReduceSupportService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }

    /**
     * 查询降本支持列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:support:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierReduceSupport supplierReduceSupport)
    {
        startPage();
        List<SupplierReduceSupport> list = supplierReduceSupportService.selectSupplierReduceSupportList(supplierReduceSupport);
        return getDataTable(list);
    }

    /**
     * 导出降本支持列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:support:export')")
    @Log(title = "降本支持", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierReduceSupport supplierReduceSupport)
    {
        List<SupplierReduceSupport> list = supplierReduceSupportService.selectSupplierReduceSupportList(supplierReduceSupport);
        ExcelUtil<SupplierReduceSupport> util = new ExcelUtil<SupplierReduceSupport>(SupplierReduceSupport.class);
        util.exportExcel(response, list, "降本支持数据");
    }

    /**
     * 获取降本支持详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:support:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierReduceSupportService.selectSupplierReduceSupportById(id));
    }

    /**
     * 新增降本支持
     */
    @PreAuthorize("@ss.hasPermi('supplier:support:add')")
    @Log(title = "降本支持", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierReduceSupport supplierReduceSupport)
    {
        return toAjax(supplierReduceSupportService.insertSupplierReduceSupport(supplierReduceSupport));
    }

    /**
     * 修改降本支持
     */
    @PreAuthorize("@ss.hasPermi('supplier:support:edit')")
    @Log(title = "降本支持", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierReduceSupport supplierReduceSupport)
    {
        return toAjax(supplierReduceSupportService.updateSupplierReduceSupport(supplierReduceSupport));
    }

    /**
     * 删除降本支持
     */
    @PreAuthorize("@ss.hasPermi('supplier:support:remove')")
    @Log(title = "降本支持", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierReduceSupportService.deleteSupplierReduceSupportByIds(ids));
    }
}
