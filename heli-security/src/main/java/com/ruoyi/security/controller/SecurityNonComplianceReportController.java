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
import com.ruoyi.security.domain.SecurityNonComplianceReport;
import com.ruoyi.security.service.ISecurityNonComplianceReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 不符合报告Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/NonComplianceReport")
public class SecurityNonComplianceReportController extends BaseController
{
    @Autowired
    private ISecurityNonComplianceReportService securityNonComplianceReportService;

    /**
     * 查询不符合报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:NonComplianceReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityNonComplianceReport securityNonComplianceReport)
    {
        startPage();
        List<SecurityNonComplianceReport> list = securityNonComplianceReportService.selectSecurityNonComplianceReportList(securityNonComplianceReport);
        return getDataTable(list);
    }

    /**
     * 导出不符合报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:NonComplianceReport:export')")
    @Log(title = "不符合报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityNonComplianceReport securityNonComplianceReport)
    {
        List<SecurityNonComplianceReport> list = securityNonComplianceReportService.selectSecurityNonComplianceReportList(securityNonComplianceReport);
        ExcelUtil<SecurityNonComplianceReport> util = new ExcelUtil<SecurityNonComplianceReport>(SecurityNonComplianceReport.class);
        util.exportExcel(response, list, "不符合报告数据");
    }

    /**
     * 获取不符合报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:NonComplianceReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityNonComplianceReportService.selectSecurityNonComplianceReportById(id));
    }

    /**
     * 新增不符合报告
     */
    @PreAuthorize("@ss.hasPermi('security:NonComplianceReport:add')")
    @Log(title = "不符合报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityNonComplianceReport securityNonComplianceReport)
    {
        return toAjax(securityNonComplianceReportService.insertSecurityNonComplianceReport(securityNonComplianceReport));
    }

    /**
     * 修改不符合报告
     */
    @PreAuthorize("@ss.hasPermi('security:NonComplianceReport:edit')")
    @Log(title = "不符合报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityNonComplianceReport securityNonComplianceReport)
    {
        return toAjax(securityNonComplianceReportService.updateSecurityNonComplianceReport(securityNonComplianceReport));
    }

    /**
     * 删除不符合报告
     */
    @PreAuthorize("@ss.hasPermi('security:NonComplianceReport:remove')")
    @Log(title = "不符合报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityNonComplianceReportService.deleteSecurityNonComplianceReportByIds(ids));
    }
}
