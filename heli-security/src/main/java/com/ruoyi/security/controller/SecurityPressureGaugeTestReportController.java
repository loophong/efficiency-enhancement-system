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
import com.ruoyi.security.domain.SecurityPressureGaugeTestReport;
import com.ruoyi.security.service.ISecurityPressureGaugeTestReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 压力检测报告Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/GaugeTestReport")
public class SecurityPressureGaugeTestReportController extends BaseController
{
    @Autowired
    private ISecurityPressureGaugeTestReportService securityPressureGaugeTestReportService;

    /**
     * 查询压力检测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:GaugeTestReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityPressureGaugeTestReport securityPressureGaugeTestReport)
    {
        startPage();
        List<SecurityPressureGaugeTestReport> list = securityPressureGaugeTestReportService.selectSecurityPressureGaugeTestReportList(securityPressureGaugeTestReport);
        return getDataTable(list);
    }

    /**
     * 导出压力检测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:GaugeTestReport:export')")
    @Log(title = "压力检测报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityPressureGaugeTestReport securityPressureGaugeTestReport)
    {
        List<SecurityPressureGaugeTestReport> list = securityPressureGaugeTestReportService.selectSecurityPressureGaugeTestReportList(securityPressureGaugeTestReport);
        ExcelUtil<SecurityPressureGaugeTestReport> util = new ExcelUtil<SecurityPressureGaugeTestReport>(SecurityPressureGaugeTestReport.class);
        util.exportExcel(response, list, "压力检测报告数据");
    }

    /**
     * 获取压力检测报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:GaugeTestReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityPressureGaugeTestReportService.selectSecurityPressureGaugeTestReportById(id));
    }

    /**
     * 新增压力检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:GaugeTestReport:add')")
    @Log(title = "压力检测报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityPressureGaugeTestReport securityPressureGaugeTestReport)
    {
        return toAjax(securityPressureGaugeTestReportService.insertSecurityPressureGaugeTestReport(securityPressureGaugeTestReport));
    }

    /**
     * 修改压力检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:GaugeTestReport:edit')")
    @Log(title = "压力检测报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityPressureGaugeTestReport securityPressureGaugeTestReport)
    {
        return toAjax(securityPressureGaugeTestReportService.updateSecurityPressureGaugeTestReport(securityPressureGaugeTestReport));
    }

    /**
     * 删除压力检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:GaugeTestReport:remove')")
    @Log(title = "压力检测报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityPressureGaugeTestReportService.deleteSecurityPressureGaugeTestReportByIds(ids));
    }
}
