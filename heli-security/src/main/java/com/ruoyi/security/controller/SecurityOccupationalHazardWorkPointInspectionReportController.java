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
import com.ruoyi.security.domain.SecurityOccupationalHazardWorkPointInspectionReport;
import com.ruoyi.security.service.ISecurityOccupationalHazardWorkPointInspectionReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 职业危害作业点检测报告Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/WorkPointInspectionReport")
public class SecurityOccupationalHazardWorkPointInspectionReportController extends BaseController
{
    @Autowired
    private ISecurityOccupationalHazardWorkPointInspectionReportService securityOccupationalHazardWorkPointInspectionReportService;

    /**
     * 查询职业危害作业点检测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:WorkPointInspectionReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityOccupationalHazardWorkPointInspectionReport securityOccupationalHazardWorkPointInspectionReport)
    {
        startPage();
        List<SecurityOccupationalHazardWorkPointInspectionReport> list = securityOccupationalHazardWorkPointInspectionReportService.selectSecurityOccupationalHazardWorkPointInspectionReportList(securityOccupationalHazardWorkPointInspectionReport);
        return getDataTable(list);
    }

    /**
     * 导出职业危害作业点检测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:WorkPointInspectionReport:export')")
    @Log(title = "职业危害作业点检测报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityOccupationalHazardWorkPointInspectionReport securityOccupationalHazardWorkPointInspectionReport)
    {
        List<SecurityOccupationalHazardWorkPointInspectionReport> list = securityOccupationalHazardWorkPointInspectionReportService.selectSecurityOccupationalHazardWorkPointInspectionReportList(securityOccupationalHazardWorkPointInspectionReport);
        ExcelUtil<SecurityOccupationalHazardWorkPointInspectionReport> util = new ExcelUtil<SecurityOccupationalHazardWorkPointInspectionReport>(SecurityOccupationalHazardWorkPointInspectionReport.class);
        util.exportExcel(response, list, "职业危害作业点检测报告数据");
    }

    /**
     * 获取职业危害作业点检测报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:WorkPointInspectionReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityOccupationalHazardWorkPointInspectionReportService.selectSecurityOccupationalHazardWorkPointInspectionReportById(id));
    }

    /**
     * 新增职业危害作业点检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:WorkPointInspectionReport:add')")
    @Log(title = "职业危害作业点检测报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityOccupationalHazardWorkPointInspectionReport securityOccupationalHazardWorkPointInspectionReport)
    {
        return toAjax(securityOccupationalHazardWorkPointInspectionReportService.insertSecurityOccupationalHazardWorkPointInspectionReport(securityOccupationalHazardWorkPointInspectionReport));
    }

    /**
     * 修改职业危害作业点检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:WorkPointInspectionReport:edit')")
    @Log(title = "职业危害作业点检测报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityOccupationalHazardWorkPointInspectionReport securityOccupationalHazardWorkPointInspectionReport)
    {
        return toAjax(securityOccupationalHazardWorkPointInspectionReportService.updateSecurityOccupationalHazardWorkPointInspectionReport(securityOccupationalHazardWorkPointInspectionReport));
    }

    /**
     * 删除职业危害作业点检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:WorkPointInspectionReport:remove')")
    @Log(title = "职业危害作业点检测报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityOccupationalHazardWorkPointInspectionReportService.deleteSecurityOccupationalHazardWorkPointInspectionReportByIds(ids));
    }
}
