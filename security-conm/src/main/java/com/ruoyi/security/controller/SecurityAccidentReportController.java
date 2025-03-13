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
import com.ruoyi.security.domain.SecurityAccidentReport;
import com.ruoyi.security.service.ISecurityAccidentReportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 事故处理报告Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/AccidentReport")
public class SecurityAccidentReportController extends BaseController
{
    @Autowired
    private ISecurityAccidentReportService securityAccidentReportService;

    /**
     * 查询事故处理报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityAccidentReport securityAccidentReport)
    {
        startPage();
        List<SecurityAccidentReport> list = securityAccidentReportService.selectSecurityAccidentReportList(securityAccidentReport);
        return getDataTable(list);
    }

    /**
     * 导出事故处理报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentReport:export')")
    @Log(title = "事故处理报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityAccidentReport securityAccidentReport)
    {
        List<SecurityAccidentReport> list = securityAccidentReportService.selectSecurityAccidentReportList(securityAccidentReport);
        ExcelUtil<SecurityAccidentReport> util = new ExcelUtil<SecurityAccidentReport>(SecurityAccidentReport.class);
        util.exportExcel(response, list, "事故处理报告数据");
    }

    /**
     * 获取事故处理报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityAccidentReportService.selectSecurityAccidentReportById(id));
    }

    /**
     * 新增事故处理报告
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentReport:add')")
    @Log(title = "事故处理报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityAccidentReport securityAccidentReport)
    {
        return toAjax(securityAccidentReportService.insertSecurityAccidentReport(securityAccidentReport));
    }

    /**
     * 修改事故处理报告
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentReport:edit')")
    @Log(title = "事故处理报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityAccidentReport securityAccidentReport)
    {
        return toAjax(securityAccidentReportService.updateSecurityAccidentReport(securityAccidentReport));
    }

    /**
     * 删除事故处理报告
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentReport:remove')")
    @Log(title = "事故处理报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityAccidentReportService.deleteSecurityAccidentReportByIds(ids));
    }
}
