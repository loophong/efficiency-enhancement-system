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
import com.ruoyi.security.domain.SecurityComplianceEvaluationReport;
import com.ruoyi.security.service.ISecurityComplianceEvaluationReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 合规性评价报告Controller
 * 
 * @author wang
 * @date 2025-07-22
 */
@RestController
@RequestMapping("/security/compliancereport")
public class SecurityComplianceEvaluationReportController extends BaseController
{
    @Autowired
    private ISecurityComplianceEvaluationReportService securityComplianceEvaluationReportService;

    /**
     * 查询合规性评价报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:compliancereport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityComplianceEvaluationReport securityComplianceEvaluationReport)
    {
        startPage();
        List<SecurityComplianceEvaluationReport> list = securityComplianceEvaluationReportService.selectSecurityComplianceEvaluationReportList(securityComplianceEvaluationReport);
        return getDataTable(list);
    }

    /**
     * 导出合规性评价报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:compliancereport:export')")
    @Log(title = "合规性评价报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityComplianceEvaluationReport securityComplianceEvaluationReport)
    {
        List<SecurityComplianceEvaluationReport> list = securityComplianceEvaluationReportService.selectSecurityComplianceEvaluationReportList(securityComplianceEvaluationReport);
        ExcelUtil<SecurityComplianceEvaluationReport> util = new ExcelUtil<SecurityComplianceEvaluationReport>(SecurityComplianceEvaluationReport.class);
        util.exportExcel(response, list, "合规性评价报告数据");
    }

    /**
     * 获取合规性评价报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:compliancereport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityComplianceEvaluationReportService.selectSecurityComplianceEvaluationReportById(id));
    }

    /**
     * 新增合规性评价报告
     */
    @PreAuthorize("@ss.hasPermi('security:compliancereport:add')")
    @Log(title = "合规性评价报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityComplianceEvaluationReport securityComplianceEvaluationReport)
    {
        return toAjax(securityComplianceEvaluationReportService.insertSecurityComplianceEvaluationReport(securityComplianceEvaluationReport));
    }

    /**
     * 修改合规性评价报告
     */
    @PreAuthorize("@ss.hasPermi('security:compliancereport:edit')")
    @Log(title = "合规性评价报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityComplianceEvaluationReport securityComplianceEvaluationReport)
    {
        return toAjax(securityComplianceEvaluationReportService.updateSecurityComplianceEvaluationReport(securityComplianceEvaluationReport));
    }

    /**
     * 删除合规性评价报告
     */
    @PreAuthorize("@ss.hasPermi('security:compliancereport:remove')")
    @Log(title = "合规性评价报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityComplianceEvaluationReportService.deleteSecurityComplianceEvaluationReportByIds(ids));
    }
}
