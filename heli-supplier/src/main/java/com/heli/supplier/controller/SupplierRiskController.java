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
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.service.ISupplierRiskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 经营风险Controller
 * 
 * @author wll
 * @date 2025-02-27
 */
@Slf4j
@RestController
@RequestMapping("/supplier/risk")
public class SupplierRiskController extends BaseController
{
    @Autowired
    private ISupplierRiskService supplierRiskService;


    @Log(title = "[经营风险表]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile, Date uploadMonth) {
//        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
        try {
            supplierRiskService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);

        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }
    /**
     * 查询经营风险列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:risk:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierRisk supplierRisk)
    {
        startPage();
        List<SupplierRisk> list = supplierRiskService.selectSupplierRiskList(supplierRisk);
        return getDataTable(list);
    }

    /**
     * 导出经营风险列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:risk:export')")
    @Log(title = "经营风险", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierRisk supplierRisk)
    {
        List<SupplierRisk> list = supplierRiskService.selectSupplierRiskList(supplierRisk);
        ExcelUtil<SupplierRisk> util = new ExcelUtil<SupplierRisk>(SupplierRisk.class);
        util.exportExcel(response, list, "经营风险数据");
    }

    /**
     * 获取经营风险详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:risk:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierRiskService.selectSupplierRiskById(id));
    }

    /**
     * 新增经营风险
     */
    @PreAuthorize("@ss.hasPermi('supplier:risk:add')")
    @Log(title = "经营风险", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierRisk supplierRisk)
    {
        return toAjax(supplierRiskService.insertSupplierRisk(supplierRisk));
    }

    /**
     * 修改经营风险
     */
    @PreAuthorize("@ss.hasPermi('supplier:risk:edit')")
    @Log(title = "经营风险", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierRisk supplierRisk)
    {
        return toAjax(supplierRiskService.updateSupplierRisk(supplierRisk));
    }

    /**
     * 删除经营风险
     */
    @PreAuthorize("@ss.hasPermi('supplier:risk:remove')")
    @Log(title = "经营风险", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierRiskService.deleteSupplierRiskByIds(ids));
    }
}
