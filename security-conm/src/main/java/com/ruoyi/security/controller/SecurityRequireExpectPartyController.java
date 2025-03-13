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
import com.ruoyi.security.domain.SecurityRequireExpectParty;
import com.ruoyi.security.service.ISecurityRequireExpectPartyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 相关方期望Controller
 *
 * @author wang
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/security/RequireExpectParty")
public class SecurityRequireExpectPartyController extends BaseController
{
    @Autowired
    private ISecurityRequireExpectPartyService securityRequireExpectPartyService;

    /**
     * 查询相关方期望列表
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRequireExpectParty securityRequireExpectParty)
    {
        startPage();
        List<SecurityRequireExpectParty> list = securityRequireExpectPartyService.selectSecurityRequireExpectPartyList(securityRequireExpectParty);
        return getDataTable(list);
    }

    /**
     * 导出相关方期望列表
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:export')")
    @Log(title = "相关方期望", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRequireExpectParty securityRequireExpectParty)
    {
        List<SecurityRequireExpectParty> list = securityRequireExpectPartyService.selectSecurityRequireExpectPartyList(securityRequireExpectParty);
        ExcelUtil<SecurityRequireExpectParty> util = new ExcelUtil<SecurityRequireExpectParty>(SecurityRequireExpectParty.class);
        util.exportExcel(response, list, "相关方期望数据");
    }

    /**
     * 获取相关方期望详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityRequireExpectPartyService.selectSecurityRequireExpectPartyById(id));
    }

    /**
     * 新增相关方期望
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:add')")
    @Log(title = "相关方期望", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRequireExpectParty securityRequireExpectParty)
    {
        return toAjax(securityRequireExpectPartyService.insertSecurityRequireExpectParty(securityRequireExpectParty));
    }

    /**
     * 修改相关方期望
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:edit')")
    @Log(title = "相关方期望", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRequireExpectParty securityRequireExpectParty)
    {
        return toAjax(securityRequireExpectPartyService.updateSecurityRequireExpectParty(securityRequireExpectParty));
    }

    /**
     * 删除相关方期望
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:remove')")
    @Log(title = "相关方期望", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityRequireExpectPartyService.deleteSecurityRequireExpectPartyByIds(ids));
    }
}
