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
import com.heli.supplier.domain.SupplierZeroKilometerFailureRate;
import com.heli.supplier.service.ISupplierZeroKilometerFailureRateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 零公里故障指标完成率Controller
 * 
 * @author wll
 * @date 2025-03-24
 */
@RestController
@Slf4j
@RequestMapping("/supplier/zeroFailureRate")
public class SupplierZeroKilometerFailureRateController extends BaseController
{
    @Autowired
    private ISupplierZeroKilometerFailureRateService supplierZeroKilometerFailureRateService;


    @PreAuthorize("@ss.hasPermi('supplier:zeroFailureRate:import')")
    @Log(title = "[产品过程故障率]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/importProductProcessFailures")
    @Transactional
    public void importProductProcessFailuresTable(MultipartFile excelFile, Date uploadMonth) {
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
        try {
            supplierZeroKilometerFailureRateService.readProcessFailuresTableToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }

    @PreAuthorize("@ss.hasPermi('supplier:zeroFailureRate:import')")
    @Log(title = "[零公里故障指标完成率]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile, Date uploadMonth) {
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
        try {
            supplierZeroKilometerFailureRateService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }
    /**
     * 查询零公里故障指标完成率列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:zeroFailureRate:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate)
    {
        startPage();
        List<SupplierZeroKilometerFailureRate> list = supplierZeroKilometerFailureRateService.selectSupplierZeroKilometerFailureRateList(supplierZeroKilometerFailureRate);
        return getDataTable(list);
    }

    /**
     * 导出零公里故障指标完成率列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:zeroFailureRate:export')")
    @Log(title = "零公里故障指标完成率", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate)
    {
        List<SupplierZeroKilometerFailureRate> list = supplierZeroKilometerFailureRateService.selectSupplierZeroKilometerFailureRateList(supplierZeroKilometerFailureRate);
        ExcelUtil<SupplierZeroKilometerFailureRate> util = new ExcelUtil<SupplierZeroKilometerFailureRate>(SupplierZeroKilometerFailureRate.class);
        util.exportExcel(response, list, "零公里故障指标完成率");
    }

    /**
     * 获取零公里故障指标完成率详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:zeroFailureRate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierZeroKilometerFailureRateService.selectSupplierZeroKilometerFailureRateById(id));
    }

    /**
     * 新增零公里故障指标完成率
     */
    @PreAuthorize("@ss.hasPermi('supplier:zeroFailureRate:add')")
    @Log(title = "零公里故障指标完成率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate)
    {
        return toAjax(supplierZeroKilometerFailureRateService.insertSupplierZeroKilometerFailureRate(supplierZeroKilometerFailureRate));
    }

    /**
     * 修改零公里故障指标完成率
     */
    @PreAuthorize("@ss.hasPermi('supplier:zeroFailureRate:edit')")
    @Log(title = "零公里故障指标完成率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate)
    {
        return toAjax(supplierZeroKilometerFailureRateService.updateSupplierZeroKilometerFailureRate(supplierZeroKilometerFailureRate));
    }

    /**
     * 删除零公里故障指标完成率
     */
    @PreAuthorize("@ss.hasPermi('supplier:zeroFailureRate:remove')")
    @Log(title = "零公里故障指标完成率", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierZeroKilometerFailureRateService.deleteSupplierZeroKilometerFailureRateByIds(ids));
    }
}
