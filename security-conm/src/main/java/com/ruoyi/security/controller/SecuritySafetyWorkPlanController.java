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
import com.ruoyi.security.domain.SecuritySafetyWorkPlan;
import com.ruoyi.security.service.ISecuritySafetyWorkPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 中长短期安全工作计划Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/SafetyWorkPlan")
public class SecuritySafetyWorkPlanController extends BaseController
{
    @Autowired
    private ISecuritySafetyWorkPlanService securitySafetyWorkPlanService;

    /**
     * 查询中长短期安全工作计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyWorkPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecuritySafetyWorkPlan securitySafetyWorkPlan)
    {
        startPage();
        List<SecuritySafetyWorkPlan> list = securitySafetyWorkPlanService.selectSecuritySafetyWorkPlanList(securitySafetyWorkPlan);
        return getDataTable(list);
    }

    /**
     * 导出中长短期安全工作计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyWorkPlan:export')")
    @Log(title = "中长短期安全工作计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecuritySafetyWorkPlan securitySafetyWorkPlan)
    {
        List<SecuritySafetyWorkPlan> list = securitySafetyWorkPlanService.selectSecuritySafetyWorkPlanList(securitySafetyWorkPlan);
        ExcelUtil<SecuritySafetyWorkPlan> util = new ExcelUtil<SecuritySafetyWorkPlan>(SecuritySafetyWorkPlan.class);
        util.exportExcel(response, list, "中长短期安全工作计划数据");
    }

    /**
     * 获取中长短期安全工作计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyWorkPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securitySafetyWorkPlanService.selectSecuritySafetyWorkPlanById(id));
    }

    /**
     * 新增中长短期安全工作计划
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyWorkPlan:add')")
    @Log(title = "中长短期安全工作计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecuritySafetyWorkPlan securitySafetyWorkPlan)
    {
        return toAjax(securitySafetyWorkPlanService.insertSecuritySafetyWorkPlan(securitySafetyWorkPlan));
    }

    /**
     * 修改中长短期安全工作计划
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyWorkPlan:edit')")
    @Log(title = "中长短期安全工作计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecuritySafetyWorkPlan securitySafetyWorkPlan)
    {
        return toAjax(securitySafetyWorkPlanService.updateSecuritySafetyWorkPlan(securitySafetyWorkPlan));
    }

    /**
     * 删除中长短期安全工作计划
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyWorkPlan:remove')")
    @Log(title = "中长短期安全工作计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securitySafetyWorkPlanService.deleteSecuritySafetyWorkPlanByIds(ids));
    }
}
