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
import com.ruoyi.security.domain.SecurityAccidentCauseAnalysis;
import com.ruoyi.security.service.ISecurityAccidentCauseAnalysisService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 事故原因分析记录Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/AccidentCauseAnalysis")
public class SecurityAccidentCauseAnalysisController extends BaseController
{
    @Autowired
    private ISecurityAccidentCauseAnalysisService securityAccidentCauseAnalysisService;

    /**
     * 查询事故原因分析记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentCauseAnalysis:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityAccidentCauseAnalysis securityAccidentCauseAnalysis)
    {
        startPage();
        List<SecurityAccidentCauseAnalysis> list = securityAccidentCauseAnalysisService.selectSecurityAccidentCauseAnalysisList(securityAccidentCauseAnalysis);
        return getDataTable(list);
    }

    /**
     * 导出事故原因分析记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentCauseAnalysis:export')")
    @Log(title = "事故原因分析记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityAccidentCauseAnalysis securityAccidentCauseAnalysis)
    {
        List<SecurityAccidentCauseAnalysis> list = securityAccidentCauseAnalysisService.selectSecurityAccidentCauseAnalysisList(securityAccidentCauseAnalysis);
        ExcelUtil<SecurityAccidentCauseAnalysis> util = new ExcelUtil<SecurityAccidentCauseAnalysis>(SecurityAccidentCauseAnalysis.class);
        util.exportExcel(response, list, "事故原因分析记录数据");
    }

    /**
     * 获取事故原因分析记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentCauseAnalysis:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityAccidentCauseAnalysisService.selectSecurityAccidentCauseAnalysisById(id));
    }

    /**
     * 新增事故原因分析记录
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentCauseAnalysis:add')")
    @Log(title = "事故原因分析记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityAccidentCauseAnalysis securityAccidentCauseAnalysis)
    {
        return toAjax(securityAccidentCauseAnalysisService.insertSecurityAccidentCauseAnalysis(securityAccidentCauseAnalysis));
    }

    /**
     * 修改事故原因分析记录
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentCauseAnalysis:edit')")
    @Log(title = "事故原因分析记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityAccidentCauseAnalysis securityAccidentCauseAnalysis)
    {
        return toAjax(securityAccidentCauseAnalysisService.updateSecurityAccidentCauseAnalysis(securityAccidentCauseAnalysis));
    }

    /**
     * 删除事故原因分析记录
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentCauseAnalysis:remove')")
    @Log(title = "事故原因分析记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityAccidentCauseAnalysisService.deleteSecurityAccidentCauseAnalysisByIds(ids));
    }
}
