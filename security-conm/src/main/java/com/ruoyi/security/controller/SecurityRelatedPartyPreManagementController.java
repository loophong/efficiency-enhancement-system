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
import com.ruoyi.security.domain.SecurityRelatedPartyPreManagement;
import com.ruoyi.security.service.ISecurityRelatedPartyPreManagementService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 相关方前置管理Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/PartyPreManagement")
public class SecurityRelatedPartyPreManagementController extends BaseController
{
    @Autowired
    private ISecurityRelatedPartyPreManagementService securityRelatedPartyPreManagementService;

    /**
     * 查询相关方前置管理列表
     */
    @PreAuthorize("@ss.hasPermi('security:PartyPreManagement:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRelatedPartyPreManagement securityRelatedPartyPreManagement)
    {
        startPage();
        List<SecurityRelatedPartyPreManagement> list = securityRelatedPartyPreManagementService.selectSecurityRelatedPartyPreManagementList(securityRelatedPartyPreManagement);
        return getDataTable(list);
    }

    /**
     * 导出相关方前置管理列表
     */
    @PreAuthorize("@ss.hasPermi('security:PartyPreManagement:export')")
    @Log(title = "相关方前置管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRelatedPartyPreManagement securityRelatedPartyPreManagement)
    {
        List<SecurityRelatedPartyPreManagement> list = securityRelatedPartyPreManagementService.selectSecurityRelatedPartyPreManagementList(securityRelatedPartyPreManagement);
        ExcelUtil<SecurityRelatedPartyPreManagement> util = new ExcelUtil<SecurityRelatedPartyPreManagement>(SecurityRelatedPartyPreManagement.class);
        util.exportExcel(response, list, "相关方前置管理数据");
    }

    /**
     * 获取相关方前置管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:PartyPreManagement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityRelatedPartyPreManagementService.selectSecurityRelatedPartyPreManagementById(id));
    }

    /**
     * 新增相关方前置管理
     */
    @PreAuthorize("@ss.hasPermi('security:PartyPreManagement:add')")
    @Log(title = "相关方前置管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRelatedPartyPreManagement securityRelatedPartyPreManagement)
    {
        return toAjax(securityRelatedPartyPreManagementService.insertSecurityRelatedPartyPreManagement(securityRelatedPartyPreManagement));
    }

    /**
     * 修改相关方前置管理
     */
    @PreAuthorize("@ss.hasPermi('security:PartyPreManagement:edit')")
    @Log(title = "相关方前置管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRelatedPartyPreManagement securityRelatedPartyPreManagement)
    {
        return toAjax(securityRelatedPartyPreManagementService.updateSecurityRelatedPartyPreManagement(securityRelatedPartyPreManagement));
    }

    /**
     * 删除相关方前置管理
     */
    @PreAuthorize("@ss.hasPermi('security:PartyPreManagement:remove')")
    @Log(title = "相关方前置管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityRelatedPartyPreManagementService.deleteSecurityRelatedPartyPreManagementByIds(ids));
    }
}
