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
import com.ruoyi.security.domain.SecurityValveTestReport;
import com.ruoyi.security.service.ISecurityValveTestReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全阀检测报告Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/ValveTestReport")
public class SecurityValveTestReportController extends BaseController
{
    @Autowired
    private ISecurityValveTestReportService securityValveTestReportService;

    /**
     * 查询安全阀检测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:ValveTestReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityValveTestReport securityValveTestReport)
    {
        startPage();
        List<SecurityValveTestReport> list = securityValveTestReportService.selectSecurityValveTestReportList(securityValveTestReport);
        return getDataTable(list);
    }

    /**
     * 导出安全阀检测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:ValveTestReport:export')")
    @Log(title = "安全阀检测报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityValveTestReport securityValveTestReport)
    {
        List<SecurityValveTestReport> list = securityValveTestReportService.selectSecurityValveTestReportList(securityValveTestReport);
        ExcelUtil<SecurityValveTestReport> util = new ExcelUtil<SecurityValveTestReport>(SecurityValveTestReport.class);
        util.exportExcel(response, list, "安全阀检测报告数据");
    }

    /**
     * 获取安全阀检测报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:ValveTestReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityValveTestReportService.selectSecurityValveTestReportById(id));
    }

    /**
     * 新增安全阀检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:ValveTestReport:add')")
    @Log(title = "安全阀检测报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityValveTestReport securityValveTestReport)
    {
        return toAjax(securityValveTestReportService.insertSecurityValveTestReport(securityValveTestReport));
    }

    /**
     * 修改安全阀检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:ValveTestReport:edit')")
    @Log(title = "安全阀检测报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityValveTestReport securityValveTestReport)
    {
        return toAjax(securityValveTestReportService.updateSecurityValveTestReport(securityValveTestReport));
    }

    /**
     * 删除安全阀检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:ValveTestReport:remove')")
    @Log(title = "安全阀检测报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityValveTestReportService.deleteSecurityValveTestReportByIds(ids));
    }
}
