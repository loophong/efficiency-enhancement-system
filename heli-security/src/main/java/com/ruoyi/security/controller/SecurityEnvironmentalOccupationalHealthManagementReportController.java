package com.ruoyi.security.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthManagementReport;
import com.ruoyi.security.service.ISecurityEnvironmentalOccupationalHealthManagementReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境职业健康安全管理评审报告Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/HealthManagementReport")
public class SecurityEnvironmentalOccupationalHealthManagementReportController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentalOccupationalHealthManagementReportService securityEnvironmentalOccupationalHealthManagementReportService;

    /**
     * 查询环境职业健康安全管理评审报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentalOccupationalHealthManagementReport securityEnvironmentalOccupationalHealthManagementReport)
    {
        startPage();
        List<SecurityEnvironmentalOccupationalHealthManagementReport> list = securityEnvironmentalOccupationalHealthManagementReportService.selectSecurityEnvironmentalOccupationalHealthManagementReportList(securityEnvironmentalOccupationalHealthManagementReport);
        return getDataTable(list);
    }

    /**
     * 导出环境职业健康安全管理评审报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementReport:export')")
    @Log(title = "环境职业健康安全管理评审报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentalOccupationalHealthManagementReport securityEnvironmentalOccupationalHealthManagementReport)
    {
        List<SecurityEnvironmentalOccupationalHealthManagementReport> list = securityEnvironmentalOccupationalHealthManagementReportService.selectSecurityEnvironmentalOccupationalHealthManagementReportList(securityEnvironmentalOccupationalHealthManagementReport);
        ExcelUtil<SecurityEnvironmentalOccupationalHealthManagementReport> util = new ExcelUtil<SecurityEnvironmentalOccupationalHealthManagementReport>(SecurityEnvironmentalOccupationalHealthManagementReport.class);
        util.exportExcel(response, list, "环境职业健康安全管理评审报告数据");
    }

    /**
     * 获取环境职业健康安全管理评审报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityEnvironmentalOccupationalHealthManagementReportService.selectSecurityEnvironmentalOccupationalHealthManagementReportById(id));
    }

    /**
     * 新增环境职业健康安全管理评审报告
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementReport:add')")
    @Log(title = "环境职业健康安全管理评审报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentalOccupationalHealthManagementReport securityEnvironmentalOccupationalHealthManagementReport)
    {
        return toAjax(securityEnvironmentalOccupationalHealthManagementReportService.insertSecurityEnvironmentalOccupationalHealthManagementReport(securityEnvironmentalOccupationalHealthManagementReport));
    }

    /**
     * 修改环境职业健康安全管理评审报告
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementReport:edit')")
    @Log(title = "环境职业健康安全管理评审报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentalOccupationalHealthManagementReport securityEnvironmentalOccupationalHealthManagementReport)
    {
        return toAjax(securityEnvironmentalOccupationalHealthManagementReportService.updateSecurityEnvironmentalOccupationalHealthManagementReport(securityEnvironmentalOccupationalHealthManagementReport));
    }

    /**
     * 删除环境职业健康安全管理评审报告
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementReport:remove')")
    @Log(title = "环境职业健康安全管理评审报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityEnvironmentalOccupationalHealthManagementReportService.deleteSecurityEnvironmentalOccupationalHealthManagementReportByIds(ids));
    }
}
