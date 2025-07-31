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
import com.ruoyi.security.domain.SecurityEnvironmentalMonitoringReport;
import com.ruoyi.security.service.ISecurityEnvironmentalMonitoringReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境监测报告Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/EnvironmentalMonitoringReport")
public class SecurityEnvironmentalMonitoringReportController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentalMonitoringReportService securityEnvironmentalMonitoringReportService;

    /**
     * 查询环境监测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalMonitoringReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentalMonitoringReport securityEnvironmentalMonitoringReport)
    {
        startPage();
        List<SecurityEnvironmentalMonitoringReport> list = securityEnvironmentalMonitoringReportService.selectSecurityEnvironmentalMonitoringReportList(securityEnvironmentalMonitoringReport);
        return getDataTable(list);
    }

    /**
     * 导出环境监测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalMonitoringReport:export')")
    @Log(title = "环境监测报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentalMonitoringReport securityEnvironmentalMonitoringReport)
    {
        List<SecurityEnvironmentalMonitoringReport> list = securityEnvironmentalMonitoringReportService.selectSecurityEnvironmentalMonitoringReportList(securityEnvironmentalMonitoringReport);
        ExcelUtil<SecurityEnvironmentalMonitoringReport> util = new ExcelUtil<SecurityEnvironmentalMonitoringReport>(SecurityEnvironmentalMonitoringReport.class);
        util.exportExcel(response, list, "环境监测报告数据");
    }

    /**
     * 获取环境监测报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalMonitoringReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityEnvironmentalMonitoringReportService.selectSecurityEnvironmentalMonitoringReportById(id));
    }

    /**
     * 新增环境监测报告
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalMonitoringReport:add')")
    @Log(title = "环境监测报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentalMonitoringReport securityEnvironmentalMonitoringReport)
    {
        return toAjax(securityEnvironmentalMonitoringReportService.insertSecurityEnvironmentalMonitoringReport(securityEnvironmentalMonitoringReport));
    }

    /**
     * 修改环境监测报告
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalMonitoringReport:edit')")
    @Log(title = "环境监测报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentalMonitoringReport securityEnvironmentalMonitoringReport)
    {
        return toAjax(securityEnvironmentalMonitoringReportService.updateSecurityEnvironmentalMonitoringReport(securityEnvironmentalMonitoringReport));
    }

    /**
     * 删除环境监测报告
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalMonitoringReport:remove')")
    @Log(title = "环境监测报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityEnvironmentalMonitoringReportService.deleteSecurityEnvironmentalMonitoringReportByIds(ids));
    }
}
