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
import com.ruoyi.security.domain.SecurityRiskOpportunityAssessment;
import com.ruoyi.security.service.ISecurityRiskOpportunityAssessmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 风险和机遇评估及控制措施Controller
 * 
 * @author wang
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/security/assessment")
public class SecurityRiskOpportunityAssessmentController extends BaseController
{
    @Autowired
    private ISecurityRiskOpportunityAssessmentService securityRiskOpportunityAssessmentService;

    /**
     * 查询风险和机遇评估及控制措施列表
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        startPage();
        List<SecurityRiskOpportunityAssessment> list = securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentList(securityRiskOpportunityAssessment);
        return getDataTable(list);
    }

    /**
     * 导出风险和机遇评估及控制措施列表
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:export')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        List<SecurityRiskOpportunityAssessment> list = securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentList(securityRiskOpportunityAssessment);
        ExcelUtil<SecurityRiskOpportunityAssessment> util = new ExcelUtil<SecurityRiskOpportunityAssessment>(SecurityRiskOpportunityAssessment.class);
        util.exportExcel(response, list, "风险和机遇评估及控制措施数据");
    }

    /**
     * 获取风险和机遇评估及控制措施详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentById(id));
    }

    /**
     * 新增风险和机遇评估及控制措施
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:add')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return toAjax(securityRiskOpportunityAssessmentService.insertSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment));
    }

    /**
     * 修改风险和机遇评估及控制措施
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:edit')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return toAjax(securityRiskOpportunityAssessmentService.updateSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment));
    }

    /**
     * 删除风险和机遇评估及控制措施
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:remove')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityRiskOpportunityAssessmentService.deleteSecurityRiskOpportunityAssessmentByIds(ids));
    }
}
