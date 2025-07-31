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
import com.ruoyi.security.domain.SecurityMonitoringAlarmDeviceTestReport;
import com.ruoyi.security.service.ISecurityMonitoringAlarmDeviceTestReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 监测报警装置检测报告Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/AlarmDeviceTestReport")
public class SecurityMonitoringAlarmDeviceTestReportController extends BaseController
{
    @Autowired
    private ISecurityMonitoringAlarmDeviceTestReportService securityMonitoringAlarmDeviceTestReportService;

    /**
     * 查询监测报警装置检测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:AlarmDeviceTestReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityMonitoringAlarmDeviceTestReport securityMonitoringAlarmDeviceTestReport)
    {
        startPage();
        List<SecurityMonitoringAlarmDeviceTestReport> list = securityMonitoringAlarmDeviceTestReportService.selectSecurityMonitoringAlarmDeviceTestReportList(securityMonitoringAlarmDeviceTestReport);
        return getDataTable(list);
    }

    /**
     * 导出监测报警装置检测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:AlarmDeviceTestReport:export')")
    @Log(title = "监测报警装置检测报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityMonitoringAlarmDeviceTestReport securityMonitoringAlarmDeviceTestReport)
    {
        List<SecurityMonitoringAlarmDeviceTestReport> list = securityMonitoringAlarmDeviceTestReportService.selectSecurityMonitoringAlarmDeviceTestReportList(securityMonitoringAlarmDeviceTestReport);
        ExcelUtil<SecurityMonitoringAlarmDeviceTestReport> util = new ExcelUtil<SecurityMonitoringAlarmDeviceTestReport>(SecurityMonitoringAlarmDeviceTestReport.class);
        util.exportExcel(response, list, "监测报警装置检测报告数据");
    }

    /**
     * 获取监测报警装置检测报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:AlarmDeviceTestReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityMonitoringAlarmDeviceTestReportService.selectSecurityMonitoringAlarmDeviceTestReportById(id));
    }

    /**
     * 新增监测报警装置检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:AlarmDeviceTestReport:add')")
    @Log(title = "监测报警装置检测报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityMonitoringAlarmDeviceTestReport securityMonitoringAlarmDeviceTestReport)
    {
        return toAjax(securityMonitoringAlarmDeviceTestReportService.insertSecurityMonitoringAlarmDeviceTestReport(securityMonitoringAlarmDeviceTestReport));
    }

    /**
     * 修改监测报警装置检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:AlarmDeviceTestReport:edit')")
    @Log(title = "监测报警装置检测报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityMonitoringAlarmDeviceTestReport securityMonitoringAlarmDeviceTestReport)
    {
        return toAjax(securityMonitoringAlarmDeviceTestReportService.updateSecurityMonitoringAlarmDeviceTestReport(securityMonitoringAlarmDeviceTestReport));
    }

    /**
     * 删除监测报警装置检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:AlarmDeviceTestReport:remove')")
    @Log(title = "监测报警装置检测报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityMonitoringAlarmDeviceTestReportService.deleteSecurityMonitoringAlarmDeviceTestReportByIds(ids));
    }
}
