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
import com.ruoyi.security.domain.SecurityManagementReviewInstructionForm;
import com.ruoyi.security.service.ISecurityManagementReviewInstructionFormService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 管理评审纠正和改进指令Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/ReviewInstructionForm")
public class SecurityManagementReviewInstructionFormController extends BaseController
{
    @Autowired
    private ISecurityManagementReviewInstructionFormService securityManagementReviewInstructionFormService;

    /**
     * 查询管理评审纠正和改进指令列表
     */
    @PreAuthorize("@ss.hasPermi('security:ReviewInstructionForm:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityManagementReviewInstructionForm securityManagementReviewInstructionForm)
    {
        startPage();
        List<SecurityManagementReviewInstructionForm> list = securityManagementReviewInstructionFormService.selectSecurityManagementReviewInstructionFormList(securityManagementReviewInstructionForm);
        return getDataTable(list);
    }

    /**
     * 导出管理评审纠正和改进指令列表
     */
    @PreAuthorize("@ss.hasPermi('security:ReviewInstructionForm:export')")
    @Log(title = "管理评审纠正和改进指令", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityManagementReviewInstructionForm securityManagementReviewInstructionForm)
    {
        List<SecurityManagementReviewInstructionForm> list = securityManagementReviewInstructionFormService.selectSecurityManagementReviewInstructionFormList(securityManagementReviewInstructionForm);
        ExcelUtil<SecurityManagementReviewInstructionForm> util = new ExcelUtil<SecurityManagementReviewInstructionForm>(SecurityManagementReviewInstructionForm.class);
        util.exportExcel(response, list, "管理评审纠正和改进指令数据");
    }

    /**
     * 获取管理评审纠正和改进指令详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:ReviewInstructionForm:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityManagementReviewInstructionFormService.selectSecurityManagementReviewInstructionFormById(id));
    }

    /**
     * 新增管理评审纠正和改进指令
     */
    @PreAuthorize("@ss.hasPermi('security:ReviewInstructionForm:add')")
    @Log(title = "管理评审纠正和改进指令", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityManagementReviewInstructionForm securityManagementReviewInstructionForm)
    {
        return toAjax(securityManagementReviewInstructionFormService.insertSecurityManagementReviewInstructionForm(securityManagementReviewInstructionForm));
    }

    /**
     * 修改管理评审纠正和改进指令
     */
    @PreAuthorize("@ss.hasPermi('security:ReviewInstructionForm:edit')")
    @Log(title = "管理评审纠正和改进指令", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityManagementReviewInstructionForm securityManagementReviewInstructionForm)
    {
        return toAjax(securityManagementReviewInstructionFormService.updateSecurityManagementReviewInstructionForm(securityManagementReviewInstructionForm));
    }

    /**
     * 删除管理评审纠正和改进指令
     */
    @PreAuthorize("@ss.hasPermi('security:ReviewInstructionForm:remove')")
    @Log(title = "管理评审纠正和改进指令", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityManagementReviewInstructionFormService.deleteSecurityManagementReviewInstructionFormByIds(ids));
    }
}
