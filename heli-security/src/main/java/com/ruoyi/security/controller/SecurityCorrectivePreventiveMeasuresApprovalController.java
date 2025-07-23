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
import com.ruoyi.security.domain.SecurityCorrectivePreventiveMeasuresApproval;
import com.ruoyi.security.service.ISecurityCorrectivePreventiveMeasuresApprovalService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 纠正与预防措施审批实施Controller
 * 
 * @author wang
 * @date 2025-03-07
 */
@RestController
@RequestMapping("/security/CorrectiveMeasuresApproval")
public class SecurityCorrectivePreventiveMeasuresApprovalController extends BaseController
{
    @Autowired
    private ISecurityCorrectivePreventiveMeasuresApprovalService securityCorrectivePreventiveMeasuresApprovalService;

    /**
     * 查询纠正与预防措施审批实施列表
     */
    @PreAuthorize("@ss.hasPermi('security:CorrectiveMeasuresApproval:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityCorrectivePreventiveMeasuresApproval securityCorrectivePreventiveMeasuresApproval)
    {
        startPage();
        List<SecurityCorrectivePreventiveMeasuresApproval> list = securityCorrectivePreventiveMeasuresApprovalService.selectSecurityCorrectivePreventiveMeasuresApprovalList(securityCorrectivePreventiveMeasuresApproval);
        return getDataTable(list);
    }

    /**
     * 导出纠正与预防措施审批实施列表
     */
    @PreAuthorize("@ss.hasPermi('security:CorrectiveMeasuresApproval:export')")
    @Log(title = "纠正与预防措施审批实施", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityCorrectivePreventiveMeasuresApproval securityCorrectivePreventiveMeasuresApproval)
    {
        List<SecurityCorrectivePreventiveMeasuresApproval> list = securityCorrectivePreventiveMeasuresApprovalService.selectSecurityCorrectivePreventiveMeasuresApprovalList(securityCorrectivePreventiveMeasuresApproval);
        ExcelUtil<SecurityCorrectivePreventiveMeasuresApproval> util = new ExcelUtil<SecurityCorrectivePreventiveMeasuresApproval>(SecurityCorrectivePreventiveMeasuresApproval.class);
        util.exportExcel(response, list, "纠正与预防措施审批实施数据");
    }

    /**
     * 获取纠正与预防措施审批实施详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:CorrectiveMeasuresApproval:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityCorrectivePreventiveMeasuresApprovalService.selectSecurityCorrectivePreventiveMeasuresApprovalById(id));
    }

    /**
     * 新增纠正与预防措施审批实施
     */
    @PreAuthorize("@ss.hasPermi('security:CorrectiveMeasuresApproval:add')")
    @Log(title = "纠正与预防措施审批实施", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityCorrectivePreventiveMeasuresApproval securityCorrectivePreventiveMeasuresApproval)
    {
        return toAjax(securityCorrectivePreventiveMeasuresApprovalService.insertSecurityCorrectivePreventiveMeasuresApproval(securityCorrectivePreventiveMeasuresApproval));
    }

    /**
     * 修改纠正与预防措施审批实施
     */
    @PreAuthorize("@ss.hasPermi('security:CorrectiveMeasuresApproval:edit')")
    @Log(title = "纠正与预防措施审批实施", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityCorrectivePreventiveMeasuresApproval securityCorrectivePreventiveMeasuresApproval)
    {
        return toAjax(securityCorrectivePreventiveMeasuresApprovalService.updateSecurityCorrectivePreventiveMeasuresApproval(securityCorrectivePreventiveMeasuresApproval));
    }

    /**
     * 删除纠正与预防措施审批实施
     */
    @PreAuthorize("@ss.hasPermi('security:CorrectiveMeasuresApproval:remove')")
    @Log(title = "纠正与预防措施审批实施", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityCorrectivePreventiveMeasuresApprovalService.deleteSecurityCorrectivePreventiveMeasuresApprovalByIds(ids));
    }
}
