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
import com.ruoyi.security.domain.SecurityInternalAuditReport1;
import com.ruoyi.security.service.ISecurityInternalAuditReport1Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 内审报告Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/InternalAuditReport")
public class SecurityInternalAuditReport1Controller extends BaseController
{
    @Autowired
    private ISecurityInternalAuditReport1Service securityInternalAuditReport1Service;

    /**
     * 查询内审报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:InternalAuditReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityInternalAuditReport1 securityInternalAuditReport1)
    {
        startPage();
        List<SecurityInternalAuditReport1> list = securityInternalAuditReport1Service.selectSecurityInternalAuditReport1List(securityInternalAuditReport1);
        return getDataTable(list);
    }

    /**
     * 导出内审报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:InternalAuditReport:export')")
    @Log(title = "内审报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityInternalAuditReport1 securityInternalAuditReport1)
    {
        List<SecurityInternalAuditReport1> list = securityInternalAuditReport1Service.selectSecurityInternalAuditReport1List(securityInternalAuditReport1);
        ExcelUtil<SecurityInternalAuditReport1> util = new ExcelUtil<SecurityInternalAuditReport1>(SecurityInternalAuditReport1.class);
        util.exportExcel(response, list, "内审报告数据");
    }

    /**
     * 获取内审报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:InternalAuditReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityInternalAuditReport1Service.selectSecurityInternalAuditReport1ById(id));
    }

    /**
     * 新增内审报告
     */
    @PreAuthorize("@ss.hasPermi('security:InternalAuditReport:add')")
    @Log(title = "内审报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityInternalAuditReport1 securityInternalAuditReport1)
    {
        return toAjax(securityInternalAuditReport1Service.insertSecurityInternalAuditReport1(securityInternalAuditReport1));
    }

    /**
     * 修改内审报告
     */
    @PreAuthorize("@ss.hasPermi('security:InternalAuditReport:edit')")
    @Log(title = "内审报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityInternalAuditReport1 securityInternalAuditReport1)
    {
        return toAjax(securityInternalAuditReport1Service.updateSecurityInternalAuditReport1(securityInternalAuditReport1));
    }

    /**
     * 删除内审报告
     */
    @PreAuthorize("@ss.hasPermi('security:InternalAuditReport:remove')")
    @Log(title = "内审报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityInternalAuditReport1Service.deleteSecurityInternalAuditReport1ByIds(ids));
    }
}
