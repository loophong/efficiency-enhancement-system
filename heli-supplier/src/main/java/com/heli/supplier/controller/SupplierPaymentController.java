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
import com.heli.supplier.domain.SupplierPayment;
import com.heli.supplier.service.ISupplierPaymentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 付款限制条件Controller
 * 
 * @author wll
 * @date 2025-03-25
 */
@Slf4j
@RestController
@RequestMapping("/supplier/payment")
public class SupplierPaymentController extends BaseController
{
    @Autowired
    private ISupplierPaymentService supplierPaymentService;


    @Log(title = "[付款限制条件表]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile, Date uploadMonth) {
//        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
        try {
            supplierPaymentService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);
//            String result = supplierPaymentService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile, uploadMonth);
//            return AjaxResult.success(result); // 统一返回成功，详情在消息中
//            if (result.contains("存在问题")) {
//                return AjaxResult.warn(result);
//            } else {
//                return AjaxResult.success(result);
//            }
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
//            return AjaxResult.error("读取 " + excelFile.getName() + " 文件失败: " + e.getMessage());
        }
    }
    /**
     * 查询付款限制条件列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:payment:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierPayment supplierPayment)
    {
        startPage();
        List<SupplierPayment> list = supplierPaymentService.selectSupplierPaymentList(supplierPayment);
        return getDataTable(list);
    }

    /**
     * 导出付款限制条件列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:payment:export')")
    @Log(title = "付款限制条件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierPayment supplierPayment)
    {
        List<SupplierPayment> list = supplierPaymentService.selectSupplierPaymentList(supplierPayment);
        ExcelUtil<SupplierPayment> util = new ExcelUtil<SupplierPayment>(SupplierPayment.class);
        util.exportExcel(response, list, "付款限制条件");
    }

    /**
     * 获取付款限制条件详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:payment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierPaymentService.selectSupplierPaymentById(id));
    }

    /**
     * 新增付款限制条件
     */
    @PreAuthorize("@ss.hasPermi('supplier:payment:add')")
    @Log(title = "付款限制条件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierPayment supplierPayment)
    {
        return toAjax(supplierPaymentService.insertSupplierPayment(supplierPayment));
    }

    /**
     * 修改付款限制条件
     */
    @PreAuthorize("@ss.hasPermi('supplier:payment:edit')")
    @Log(title = "付款限制条件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierPayment supplierPayment)
    {
        return toAjax(supplierPaymentService.updateSupplierPayment(supplierPayment));
    }

    /**
     * 删除付款限制条件
     */
    @PreAuthorize("@ss.hasPermi('supplier:payment:remove')")
    @Log(title = "付款限制条件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierPaymentService.deleteSupplierPaymentByIds(ids));
    }
}
