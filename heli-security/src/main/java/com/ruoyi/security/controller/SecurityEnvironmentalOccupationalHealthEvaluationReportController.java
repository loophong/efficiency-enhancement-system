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
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthEvaluationReport;
import com.ruoyi.security.service.ISecurityEnvironmentalOccupationalHealthEvaluationReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境职业健康安全绩效评价报告Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/HealthEvaluationReport")
public class SecurityEnvironmentalOccupationalHealthEvaluationReportController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentalOccupationalHealthEvaluationReportService securityEnvironmentalOccupationalHealthEvaluationReportService;

    /**
     * 查询环境职业健康安全绩效评价报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthEvaluationReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentalOccupationalHealthEvaluationReport securityEnvironmentalOccupationalHealthEvaluationReport)
    {
        startPage();
        List<SecurityEnvironmentalOccupationalHealthEvaluationReport> list = securityEnvironmentalOccupationalHealthEvaluationReportService.selectSecurityEnvironmentalOccupationalHealthEvaluationReportList(securityEnvironmentalOccupationalHealthEvaluationReport);
        return getDataTable(list);
    }

    /**
     * 导出环境职业健康安全绩效评价报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthEvaluationReport:export')")
    @Log(title = "环境职业健康安全绩效评价报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentalOccupationalHealthEvaluationReport securityEnvironmentalOccupationalHealthEvaluationReport)
    {
        List<SecurityEnvironmentalOccupationalHealthEvaluationReport> list = securityEnvironmentalOccupationalHealthEvaluationReportService.selectSecurityEnvironmentalOccupationalHealthEvaluationReportList(securityEnvironmentalOccupationalHealthEvaluationReport);
        ExcelUtil<SecurityEnvironmentalOccupationalHealthEvaluationReport> util = new ExcelUtil<SecurityEnvironmentalOccupationalHealthEvaluationReport>(SecurityEnvironmentalOccupationalHealthEvaluationReport.class);
        util.exportExcel(response, list, "环境职业健康安全绩效评价报告数据");
    }

    /**
     * 获取环境职业健康安全绩效评价报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:HealthEvaluationReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityEnvironmentalOccupationalHealthEvaluationReportService.selectSecurityEnvironmentalOccupationalHealthEvaluationReportById(id));
    }

    /**
     * 新增环境职业健康安全绩效评价报告
     */
    @PreAuthorize("@ss.hasPermi('security:HealthEvaluationReport:add')")
    @Log(title = "环境职业健康安全绩效评价报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentalOccupationalHealthEvaluationReport securityEnvironmentalOccupationalHealthEvaluationReport)
    {
        return toAjax(securityEnvironmentalOccupationalHealthEvaluationReportService.insertSecurityEnvironmentalOccupationalHealthEvaluationReport(securityEnvironmentalOccupationalHealthEvaluationReport));
    }

    /**
     * 修改环境职业健康安全绩效评价报告
     */
    @PreAuthorize("@ss.hasPermi('security:HealthEvaluationReport:edit')")
    @Log(title = "环境职业健康安全绩效评价报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentalOccupationalHealthEvaluationReport securityEnvironmentalOccupationalHealthEvaluationReport)
    {
        return toAjax(securityEnvironmentalOccupationalHealthEvaluationReportService.updateSecurityEnvironmentalOccupationalHealthEvaluationReport(securityEnvironmentalOccupationalHealthEvaluationReport));
    }

    /**
     * 删除环境职业健康安全绩效评价报告
     */
    @PreAuthorize("@ss.hasPermi('security:HealthEvaluationReport:remove')")
    @Log(title = "环境职业健康安全绩效评价报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityEnvironmentalOccupationalHealthEvaluationReportService.deleteSecurityEnvironmentalOccupationalHealthEvaluationReportByIds(ids));
    }
}
