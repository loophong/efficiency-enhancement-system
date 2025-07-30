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
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecuritySafetyInvestmentPlan;
import com.ruoyi.security.service.ISecuritySafetyInvestmentPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;


/**
 * 安全投入计划Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/InvestmentPlan")
public class SecuritySafetyInvestmentPlanController extends BaseController
{
    @Autowired
    private ISecuritySafetyInvestmentPlanService securitySafetyInvestmentPlanService;

    /**
     * 查询安全投入计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:InvestmentPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan)
    {
        startPage();
        List<SecuritySafetyInvestmentPlan> list = securitySafetyInvestmentPlanService.selectSecuritySafetyInvestmentPlanList(securitySafetyInvestmentPlan);
        return getDataTable(list);
    }

    /**
     * 导出安全投入计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:InvestmentPlan:export')")
    @Log(title = "安全投入计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan)
    {
        List<SecuritySafetyInvestmentPlan> list = securitySafetyInvestmentPlanService.selectSecuritySafetyInvestmentPlanList(securitySafetyInvestmentPlan);
        ExcelUtil<SecuritySafetyInvestmentPlan> util = new ExcelUtil<SecuritySafetyInvestmentPlan>(SecuritySafetyInvestmentPlan.class);
        util.exportExcel(response, list, "安全投入计划数据");
    }

    /**
     * 获取安全投入计划导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:InvestmentPlan:import')")
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecuritySafetyInvestmentPlan> util = new ExcelUtil<SecuritySafetyInvestmentPlan>(SecuritySafetyInvestmentPlan.class);
        util.importTemplateExcel(response, "安全投入计划数据");
    }

    /**
     * 导入安全投入计划数据
     */
    @PreAuthorize("@ss.hasPermi('security:InvestmentPlan:import')")
    @Log(title = "安全投入计划", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SecuritySafetyInvestmentPlan> util = new ExcelUtil<SecuritySafetyInvestmentPlan>(SecuritySafetyInvestmentPlan.class);
        List<SecuritySafetyInvestmentPlan> investmentPlanList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = securitySafetyInvestmentPlanService.importInvestmentPlan(investmentPlanList, updateSupport, operName);
        return success(message);
    }

    /**
     * 获取安全投入计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:InvestmentPlan:query')")
    @GetMapping(value = "/{serialNumber}")
    public AjaxResult getInfo(@PathVariable("serialNumber") Long serialNumber)
    {
        return success(securitySafetyInvestmentPlanService.selectSecuritySafetyInvestmentPlanBySerialNumber(serialNumber));
    }

    /**
     * 新增安全投入计划
     */
    @PreAuthorize("@ss.hasPermi('security:InvestmentPlan:add')")
    @Log(title = "安全投入计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan)
    {
        return toAjax(securitySafetyInvestmentPlanService.insertSecuritySafetyInvestmentPlan(securitySafetyInvestmentPlan));
    }

    /**
     * 修改安全投入计划
     */
    @PreAuthorize("@ss.hasPermi('security:InvestmentPlan:edit')")
    @Log(title = "安全投入计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan)
    {
        return toAjax(securitySafetyInvestmentPlanService.updateSecuritySafetyInvestmentPlan(securitySafetyInvestmentPlan));
    }

    /**
     * 删除安全投入计划
     */
    @PreAuthorize("@ss.hasPermi('security:InvestmentPlan:remove')")
    @Log(title = "安全投入计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{serialNumbers}")
    public AjaxResult remove(@PathVariable Long[] serialNumbers)
    {
        return toAjax(securitySafetyInvestmentPlanService.deleteSecuritySafetyInvestmentPlanBySerialNumbers(serialNumbers));
    }
}
