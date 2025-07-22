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
import com.ruoyi.security.domain.SecurityAnnualPlan;
import com.ruoyi.security.service.ISecurityAnnualPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境和职业健康安全方针Controller
 * 
 * @author wang
 * @date 2025-07-22
 */
@RestController
@RequestMapping("/security/annualplan")
public class SecurityAnnualPlanController extends BaseController
{
    @Autowired
    private ISecurityAnnualPlanService securityAnnualPlanService;

    /**
     * 查询环境和职业健康安全方针列表
     */
    @PreAuthorize("@ss.hasPermi('security:annualplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityAnnualPlan securityAnnualPlan)
    {
        startPage();
        List<SecurityAnnualPlan> list = securityAnnualPlanService.selectSecurityAnnualPlanList(securityAnnualPlan);
        return getDataTable(list);
    }

    /**
     * 导出环境和职业健康安全方针列表
     */
    @PreAuthorize("@ss.hasPermi('security:annualplan:export')")
    @Log(title = "环境和职业健康安全方针", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityAnnualPlan securityAnnualPlan)
    {
        List<SecurityAnnualPlan> list = securityAnnualPlanService.selectSecurityAnnualPlanList(securityAnnualPlan);
        ExcelUtil<SecurityAnnualPlan> util = new ExcelUtil<SecurityAnnualPlan>(SecurityAnnualPlan.class);
        util.exportExcel(response, list, "环境和职业健康安全方针数据");
    }

    /**
     * 获取环境和职业健康安全方针详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:annualplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityAnnualPlanService.selectSecurityAnnualPlanById(id));
    }

    /**
     * 新增环境和职业健康安全方针
     */
    @PreAuthorize("@ss.hasPermi('security:annualplan:add')")
    @Log(title = "环境和职业健康安全方针", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityAnnualPlan securityAnnualPlan)
    {
        return toAjax(securityAnnualPlanService.insertSecurityAnnualPlan(securityAnnualPlan));
    }

    /**
     * 修改环境和职业健康安全方针
     */
    @PreAuthorize("@ss.hasPermi('security:annualplan:edit')")
    @Log(title = "环境和职业健康安全方针", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityAnnualPlan securityAnnualPlan)
    {
        return toAjax(securityAnnualPlanService.updateSecurityAnnualPlan(securityAnnualPlan));
    }

    /**
     * 删除环境和职业健康安全方针
     */
    @PreAuthorize("@ss.hasPermi('security:annualplan:remove')")
    @Log(title = "环境和职业健康安全方针", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityAnnualPlanService.deleteSecurityAnnualPlanByIds(ids));
    }
}
