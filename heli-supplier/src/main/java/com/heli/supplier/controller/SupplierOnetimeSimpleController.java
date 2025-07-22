package com.heli.supplier.controller;

import java.util.Date;
import java.util.List;

import com.heli.supplier.domain.dto.uploadFileDTO;
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
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.service.ISupplierOnetimeSimpleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 一次交检合格率-简化版Controller
 * 
 * @author weill
 * @date 2025-02-21
 */
@Slf4j
@RestController
@RequestMapping("/supplier/onetimesimple")
public class SupplierOnetimeSimpleController extends BaseController
{
    @Autowired
    private ISupplierOnetimeSimpleService supplierOnetimeSimpleService;


    @Log(title = "[一次交检合格率表]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable( MultipartFile excelFile,Date uploadMonth) {
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
//        log.info("传入的uploadMonth为 " + uploadMonth);
//        MultipartFile excelFile = uploadFileDTO.getExcelFile();
//        Date uploadMonth = uploadFileDTO.getUploadMonth();
        try {
            supplierOnetimeSimpleService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }




    /**
     * 查询一次交检合格率-简化版列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:onetimesimple:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierOnetimeSimple supplierOnetimeSimple)
    {
        startPage();
        List<SupplierOnetimeSimple> list = supplierOnetimeSimpleService.selectSupplierOnetimeSimpleList(supplierOnetimeSimple);
        return getDataTable(list);
    }

    /**
     * 导出一次交检合格率-简化版列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:onetimesimple:export')")
    @Log(title = "一次交检合格率-简化版", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierOnetimeSimple supplierOnetimeSimple)
    {
        List<SupplierOnetimeSimple> list = supplierOnetimeSimpleService.selectSupplierOnetimeSimpleList(supplierOnetimeSimple);
        ExcelUtil<SupplierOnetimeSimple> util = new ExcelUtil<SupplierOnetimeSimple>(SupplierOnetimeSimple.class);
        util.exportExcel(response, list, "一次交检合格率");
    }

    /**
     * 获取一次交检合格率-简化版详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:onetimesimple:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(supplierOnetimeSimpleService.selectSupplierOnetimeSimpleById(id));
    }

    /**
     * 新增一次交检合格率-简化版
     */
    @PreAuthorize("@ss.hasPermi('supplier:onetimesimple:add')")
    @Log(title = "一次交检合格率-简化版", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierOnetimeSimple supplierOnetimeSimple)
    {
        return toAjax(supplierOnetimeSimpleService.insertSupplierOnetimeSimple(supplierOnetimeSimple));
    }

    /**
     * 修改一次交检合格率-简化版
     */
    @PreAuthorize("@ss.hasPermi('supplier:onetimesimple:edit')")
    @Log(title = "一次交检合格率-简化版", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierOnetimeSimple supplierOnetimeSimple)
    {
        return toAjax(supplierOnetimeSimpleService.updateSupplierOnetimeSimple(supplierOnetimeSimple));
    }

    /**
     * 删除一次交检合格率-简化版
     */
    @PreAuthorize("@ss.hasPermi('supplier:onetimesimple:remove')")
    @Log(title = "一次交检合格率-简化版", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(supplierOnetimeSimpleService.deleteSupplierOnetimeSimpleByIds(ids));
    }
}
