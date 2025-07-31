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
import com.ruoyi.security.domain.SecurityInternalAuditImplementationPlan1;
import com.ruoyi.security.service.ISecurityInternalAuditImplementationPlan1Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 内审实施计划Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/AuditImplementationPlan")
public class SecurityInternalAuditImplementationPlan1Controller extends BaseController
{
    @Autowired
    private ISecurityInternalAuditImplementationPlan1Service securityInternalAuditImplementationPlan1Service;

    /**
     * 查询内审实施计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:AuditImplementationPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityInternalAuditImplementationPlan1 securityInternalAuditImplementationPlan1)
    {
        startPage();
        List<SecurityInternalAuditImplementationPlan1> list = securityInternalAuditImplementationPlan1Service.selectSecurityInternalAuditImplementationPlan1List(securityInternalAuditImplementationPlan1);
        return getDataTable(list);
    }

    /**
     * 导出内审实施计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:AuditImplementationPlan:export')")
    @Log(title = "内审实施计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityInternalAuditImplementationPlan1 securityInternalAuditImplementationPlan1)
    {
        List<SecurityInternalAuditImplementationPlan1> list = securityInternalAuditImplementationPlan1Service.selectSecurityInternalAuditImplementationPlan1List(securityInternalAuditImplementationPlan1);
        ExcelUtil<SecurityInternalAuditImplementationPlan1> util = new ExcelUtil<SecurityInternalAuditImplementationPlan1>(SecurityInternalAuditImplementationPlan1.class);
        util.exportExcel(response, list, "内审实施计划数据");
    }

    /**
     * 获取内审实施计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:AuditImplementationPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityInternalAuditImplementationPlan1Service.selectSecurityInternalAuditImplementationPlan1ById(id));
    }

    /**
     * 新增内审实施计划
     */
    @PreAuthorize("@ss.hasPermi('security:AuditImplementationPlan:add')")
    @Log(title = "内审实施计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityInternalAuditImplementationPlan1 securityInternalAuditImplementationPlan1)
    {
        return toAjax(securityInternalAuditImplementationPlan1Service.insertSecurityInternalAuditImplementationPlan1(securityInternalAuditImplementationPlan1));
    }

    /**
     * 修改内审实施计划
     */
    @PreAuthorize("@ss.hasPermi('security:AuditImplementationPlan:edit')")
    @Log(title = "内审实施计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityInternalAuditImplementationPlan1 securityInternalAuditImplementationPlan1)
    {
        return toAjax(securityInternalAuditImplementationPlan1Service.updateSecurityInternalAuditImplementationPlan1(securityInternalAuditImplementationPlan1));
    }

    /**
     * 删除内审实施计划
     */
    @PreAuthorize("@ss.hasPermi('security:AuditImplementationPlan:remove')")
    @Log(title = "内审实施计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityInternalAuditImplementationPlan1Service.deleteSecurityInternalAuditImplementationPlan1ByIds(ids));
    }
}
