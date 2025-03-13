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
import com.ruoyi.security.domain.SecurityEmergencyPlan;
import com.ruoyi.security.service.ISecurityEmergencyPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 应急预案Controller
 * 
 * @author wang
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/security/emergencyplan")
public class SecurityEmergencyPlanController extends BaseController
{
    @Autowired
    private ISecurityEmergencyPlanService securityEmergencyPlanService;

    /**
     * 查询应急预案列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencyplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEmergencyPlan securityEmergencyPlan)
    {
        startPage();
        List<SecurityEmergencyPlan> list = securityEmergencyPlanService.selectSecurityEmergencyPlanList(securityEmergencyPlan);
        return getDataTable(list);
    }

    /**
     * 导出应急预案列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencyplan:export')")
    @Log(title = "应急预案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEmergencyPlan securityEmergencyPlan)
    {
        List<SecurityEmergencyPlan> list = securityEmergencyPlanService.selectSecurityEmergencyPlanList(securityEmergencyPlan);
        ExcelUtil<SecurityEmergencyPlan> util = new ExcelUtil<SecurityEmergencyPlan>(SecurityEmergencyPlan.class);
        util.exportExcel(response, list, "应急预案数据");
    }

    /**
     * 获取应急预案详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:emergencyplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityEmergencyPlanService.selectSecurityEmergencyPlanById(id));
    }

    /**
     * 新增应急预案
     */
    @PreAuthorize("@ss.hasPermi('security:emergencyplan:add')")
    @Log(title = "应急预案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEmergencyPlan securityEmergencyPlan)
    {
        return toAjax(securityEmergencyPlanService.insertSecurityEmergencyPlan(securityEmergencyPlan));
    }

    /**
     * 修改应急预案
     */
    @PreAuthorize("@ss.hasPermi('security:emergencyplan:edit')")
    @Log(title = "应急预案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEmergencyPlan securityEmergencyPlan)
    {
        return toAjax(securityEmergencyPlanService.updateSecurityEmergencyPlan(securityEmergencyPlan));
    }

    /**
     * 删除应急预案
     */
    @PreAuthorize("@ss.hasPermi('security:emergencyplan:remove')")
    @Log(title = "应急预案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityEmergencyPlanService.deleteSecurityEmergencyPlanByIds(ids));
    }
}
