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
import com.ruoyi.security.domain.SecurityEmergencyDrillPlan;
import com.ruoyi.security.service.ISecurityEmergencyDrillPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 应急演练计划Controller
 * 
 * @author wang
 * @date 2025-03-06
 */
@RestController
@RequestMapping("/security/emergencydrillplan")
public class SecurityEmergencyDrillPlanController extends BaseController
{
    @Autowired
    private ISecurityEmergencyDrillPlanService securityEmergencyDrillPlanService;

    /**
     * 查询应急演练计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        startPage();
        List<SecurityEmergencyDrillPlan> list = securityEmergencyDrillPlanService.selectSecurityEmergencyDrillPlanList(securityEmergencyDrillPlan);
        return getDataTable(list);
    }

    /**
     * 导出应急演练计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:export')")
    @Log(title = "应急演练计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        List<SecurityEmergencyDrillPlan> list = securityEmergencyDrillPlanService.selectSecurityEmergencyDrillPlanList(securityEmergencyDrillPlan);
        ExcelUtil<SecurityEmergencyDrillPlan> util = new ExcelUtil<SecurityEmergencyDrillPlan>(SecurityEmergencyDrillPlan.class);
        util.exportExcel(response, list, "应急演练计划数据");
    }

    /**
     * 获取应急演练计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEmergencyDrillPlanService.selectSecurityEmergencyDrillPlanById(id));
    }

    /**
     * 新增应急演练计划
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:add')")
    @Log(title = "应急演练计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        return toAjax(securityEmergencyDrillPlanService.insertSecurityEmergencyDrillPlan(securityEmergencyDrillPlan));
    }

    /**
     * 修改应急演练计划
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:edit')")
    @Log(title = "应急演练计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        return toAjax(securityEmergencyDrillPlanService.updateSecurityEmergencyDrillPlan(securityEmergencyDrillPlan));
    }

    /**
     * 删除应急演练计划
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:remove')")
    @Log(title = "应急演练计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEmergencyDrillPlanService.deleteSecurityEmergencyDrillPlanByIds(ids));
    }
}
