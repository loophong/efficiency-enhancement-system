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
import com.ruoyi.security.domain.SecurityOccupationalHazardOperationPointTestReport;
import com.ruoyi.security.service.ISecurityOccupationalHazardOperationPointTestReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 职业危害作业点检测报告Controller
 * 
 * @author wang
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/security/OperationPointTestReport")
public class SecurityOccupationalHazardOperationPointTestReportController extends BaseController
{
    @Autowired
    private ISecurityOccupationalHazardOperationPointTestReportService securityOccupationalHazardOperationPointTestReportService;

    /**
     * 查询职业危害作业点检测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:OperationPointTestReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityOccupationalHazardOperationPointTestReport securityOccupationalHazardOperationPointTestReport)
    {
        startPage();
        List<SecurityOccupationalHazardOperationPointTestReport> list = securityOccupationalHazardOperationPointTestReportService.selectSecurityOccupationalHazardOperationPointTestReportList(securityOccupationalHazardOperationPointTestReport);
        return getDataTable(list);
    }

    /**
     * 导出职业危害作业点检测报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:OperationPointTestReport:export')")
    @Log(title = "职业危害作业点检测报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityOccupationalHazardOperationPointTestReport securityOccupationalHazardOperationPointTestReport)
    {
        List<SecurityOccupationalHazardOperationPointTestReport> list = securityOccupationalHazardOperationPointTestReportService.selectSecurityOccupationalHazardOperationPointTestReportList(securityOccupationalHazardOperationPointTestReport);
        ExcelUtil<SecurityOccupationalHazardOperationPointTestReport> util = new ExcelUtil<SecurityOccupationalHazardOperationPointTestReport>(SecurityOccupationalHazardOperationPointTestReport.class);
        util.exportExcel(response, list, "职业危害作业点检测报告数据");
    }

    /**
     * 获取职业危害作业点检测报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:OperationPointTestReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityOccupationalHazardOperationPointTestReportService.selectSecurityOccupationalHazardOperationPointTestReportById(id));
    }

    /**
     * 新增职业危害作业点检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:OperationPointTestReport:add')")
    @Log(title = "职业危害作业点检测报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityOccupationalHazardOperationPointTestReport securityOccupationalHazardOperationPointTestReport)
    {
        return toAjax(securityOccupationalHazardOperationPointTestReportService.insertSecurityOccupationalHazardOperationPointTestReport(securityOccupationalHazardOperationPointTestReport));
    }

    /**
     * 修改职业危害作业点检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:OperationPointTestReport:edit')")
    @Log(title = "职业危害作业点检测报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityOccupationalHazardOperationPointTestReport securityOccupationalHazardOperationPointTestReport)
    {
        return toAjax(securityOccupationalHazardOperationPointTestReportService.updateSecurityOccupationalHazardOperationPointTestReport(securityOccupationalHazardOperationPointTestReport));
    }

    /**
     * 删除职业危害作业点检测报告
     */
    @PreAuthorize("@ss.hasPermi('security:OperationPointTestReport:remove')")
    @Log(title = "职业危害作业点检测报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityOccupationalHazardOperationPointTestReportService.deleteSecurityOccupationalHazardOperationPointTestReportByIds(ids));
    }
}
