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
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthManagementPlan;
import com.ruoyi.security.service.ISecurityEnvironmentalOccupationalHealthManagementPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境职业健康安全管理评审计划Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/HealthManagementPlan")
public class SecurityEnvironmentalOccupationalHealthManagementPlanController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentalOccupationalHealthManagementPlanService securityEnvironmentalOccupationalHealthManagementPlanService;

    /**
     * 查询环境职业健康安全管理评审计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentalOccupationalHealthManagementPlan securityEnvironmentalOccupationalHealthManagementPlan)
    {
        startPage();
        List<SecurityEnvironmentalOccupationalHealthManagementPlan> list = securityEnvironmentalOccupationalHealthManagementPlanService.selectSecurityEnvironmentalOccupationalHealthManagementPlanList(securityEnvironmentalOccupationalHealthManagementPlan);
        return getDataTable(list);
    }

    /**
     * 导出环境职业健康安全管理评审计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementPlan:export')")
    @Log(title = "环境职业健康安全管理评审计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentalOccupationalHealthManagementPlan securityEnvironmentalOccupationalHealthManagementPlan)
    {
        List<SecurityEnvironmentalOccupationalHealthManagementPlan> list = securityEnvironmentalOccupationalHealthManagementPlanService.selectSecurityEnvironmentalOccupationalHealthManagementPlanList(securityEnvironmentalOccupationalHealthManagementPlan);
        ExcelUtil<SecurityEnvironmentalOccupationalHealthManagementPlan> util = new ExcelUtil<SecurityEnvironmentalOccupationalHealthManagementPlan>(SecurityEnvironmentalOccupationalHealthManagementPlan.class);
        util.exportExcel(response, list, "环境职业健康安全管理评审计划数据");
    }

    /**
     * 获取环境职业健康安全管理评审计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityEnvironmentalOccupationalHealthManagementPlanService.selectSecurityEnvironmentalOccupationalHealthManagementPlanById(id));
    }

    /**
     * 新增环境职业健康安全管理评审计划
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementPlan:add')")
    @Log(title = "环境职业健康安全管理评审计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentalOccupationalHealthManagementPlan securityEnvironmentalOccupationalHealthManagementPlan)
    {
        return toAjax(securityEnvironmentalOccupationalHealthManagementPlanService.insertSecurityEnvironmentalOccupationalHealthManagementPlan(securityEnvironmentalOccupationalHealthManagementPlan));
    }

    /**
     * 修改环境职业健康安全管理评审计划
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementPlan:edit')")
    @Log(title = "环境职业健康安全管理评审计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentalOccupationalHealthManagementPlan securityEnvironmentalOccupationalHealthManagementPlan)
    {
        return toAjax(securityEnvironmentalOccupationalHealthManagementPlanService.updateSecurityEnvironmentalOccupationalHealthManagementPlan(securityEnvironmentalOccupationalHealthManagementPlan));
    }

    /**
     * 删除环境职业健康安全管理评审计划
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementPlan:remove')")
    @Log(title = "环境职业健康安全管理评审计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityEnvironmentalOccupationalHealthManagementPlanService.deleteSecurityEnvironmentalOccupationalHealthManagementPlanByIds(ids));
    }
}
