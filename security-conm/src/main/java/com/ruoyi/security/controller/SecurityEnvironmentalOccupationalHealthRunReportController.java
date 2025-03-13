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
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthRunReport;
import com.ruoyi.security.service.ISecurityEnvironmentalOccupationalHealthRunReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境职业健康安全管理体系运行报告Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/HealthRunReport")
public class SecurityEnvironmentalOccupationalHealthRunReportController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentalOccupationalHealthRunReportService securityEnvironmentalOccupationalHealthRunReportService;

    /**
     * 查询环境职业健康安全管理体系运行报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthRunReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentalOccupationalHealthRunReport securityEnvironmentalOccupationalHealthRunReport)
    {
        startPage();
        List<SecurityEnvironmentalOccupationalHealthRunReport> list = securityEnvironmentalOccupationalHealthRunReportService.selectSecurityEnvironmentalOccupationalHealthRunReportList(securityEnvironmentalOccupationalHealthRunReport);
        return getDataTable(list);
    }

    /**
     * 导出环境职业健康安全管理体系运行报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthRunReport:export')")
    @Log(title = "环境职业健康安全管理体系运行报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentalOccupationalHealthRunReport securityEnvironmentalOccupationalHealthRunReport)
    {
        List<SecurityEnvironmentalOccupationalHealthRunReport> list = securityEnvironmentalOccupationalHealthRunReportService.selectSecurityEnvironmentalOccupationalHealthRunReportList(securityEnvironmentalOccupationalHealthRunReport);
        ExcelUtil<SecurityEnvironmentalOccupationalHealthRunReport> util = new ExcelUtil<SecurityEnvironmentalOccupationalHealthRunReport>(SecurityEnvironmentalOccupationalHealthRunReport.class);
        util.exportExcel(response, list, "环境职业健康安全管理体系运行报告数据");
    }

    /**
     * 获取环境职业健康安全管理体系运行报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:HealthRunReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityEnvironmentalOccupationalHealthRunReportService.selectSecurityEnvironmentalOccupationalHealthRunReportById(id));
    }

    /**
     * 新增环境职业健康安全管理体系运行报告
     */
    @PreAuthorize("@ss.hasPermi('security:HealthRunReport:add')")
    @Log(title = "环境职业健康安全管理体系运行报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentalOccupationalHealthRunReport securityEnvironmentalOccupationalHealthRunReport)
    {
        return toAjax(securityEnvironmentalOccupationalHealthRunReportService.insertSecurityEnvironmentalOccupationalHealthRunReport(securityEnvironmentalOccupationalHealthRunReport));
    }

    /**
     * 修改环境职业健康安全管理体系运行报告
     */
    @PreAuthorize("@ss.hasPermi('security:HealthRunReport:edit')")
    @Log(title = "环境职业健康安全管理体系运行报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentalOccupationalHealthRunReport securityEnvironmentalOccupationalHealthRunReport)
    {
        return toAjax(securityEnvironmentalOccupationalHealthRunReportService.updateSecurityEnvironmentalOccupationalHealthRunReport(securityEnvironmentalOccupationalHealthRunReport));
    }

    /**
     * 删除环境职业健康安全管理体系运行报告
     */
    @PreAuthorize("@ss.hasPermi('security:HealthRunReport:remove')")
    @Log(title = "环境职业健康安全管理体系运行报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityEnvironmentalOccupationalHealthRunReportService.deleteSecurityEnvironmentalOccupationalHealthRunReportByIds(ids));
    }
}
