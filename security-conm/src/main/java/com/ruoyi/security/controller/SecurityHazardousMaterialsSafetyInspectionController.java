package com.ruoyi.security.controller;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.security.service.ISecurityHazardousChemicalInventoryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ruoyi.security.domain.SecurityHazardousMaterialsSafetyInspection;
import com.ruoyi.security.service.ISecurityHazardousMaterialsSafetyInspectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 危化品检查记录Controller
 * 
 * @author wang
 * @date 2025-03-03
 */
@Slf4j
@RestController
@RequestMapping("/security/hazardousinspection")
public class SecurityHazardousMaterialsSafetyInspectionController extends BaseController
{
    @Autowired
    private ISecurityHazardousMaterialsSafetyInspectionService securityHazardousMaterialsSafetyInspectionService;

    /**
     * 查询危化品检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        startPage();
        List<SecurityHazardousMaterialsSafetyInspection> list = securityHazardousMaterialsSafetyInspectionService.selectSecurityHazardousMaterialsSafetyInspectionList(securityHazardousMaterialsSafetyInspection);
        return getDataTable(list);
    }

    /**
     * 导出危化品检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:export')")
    @Log(title = "危化品检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        List<SecurityHazardousMaterialsSafetyInspection> list = securityHazardousMaterialsSafetyInspectionService.selectSecurityHazardousMaterialsSafetyInspectionList(securityHazardousMaterialsSafetyInspection);
        ExcelUtil<SecurityHazardousMaterialsSafetyInspection> util = new ExcelUtil<SecurityHazardousMaterialsSafetyInspection>(SecurityHazardousMaterialsSafetyInspection.class);
        util.exportExcel(response, list, "危化品检查记录数据");
    }

    /**
     * 获取危化品检查记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityHazardousMaterialsSafetyInspectionService.selectSecurityHazardousMaterialsSafetyInspectionById(id));
    }

    /**
     * 新增危化品检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:add')")
    @Log(title = "危化品检查记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        return toAjax(securityHazardousMaterialsSafetyInspectionService.insertSecurityHazardousMaterialsSafetyInspection(securityHazardousMaterialsSafetyInspection));
    }

    /**
     * 修改危化品检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:edit')")
    @Log(title = "危化品检查记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        return toAjax(securityHazardousMaterialsSafetyInspectionService.updateSecurityHazardousMaterialsSafetyInspection(securityHazardousMaterialsSafetyInspection));
    }

    /**
     * 删除危化品检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:remove')")
    @Log(title = "危化品检查记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityHazardousMaterialsSafetyInspectionService.deleteSecurityHazardousMaterialsSafetyInspectionByIds(ids));
    }

    /**
     * 导入危化品检查记录
     */
    @Log(title = "[危险检查记录i]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public void importTable( MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            securityHazardousMaterialsSafetyInspectionService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }

}


