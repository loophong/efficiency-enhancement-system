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
import com.ruoyi.security.domain.SecurityHazardousChemicalInventory;
import com.ruoyi.security.service.ISecurityHazardousChemicalInventoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危险化学品台账Controller
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@RestController
@RequestMapping("/security/inventory")
public class SecurityHazardousChemicalInventoryController extends BaseController
{
    @Autowired
    private ISecurityHazardousChemicalInventoryService securityHazardousChemicalInventoryService;

    /**
     * 查询危险化学品台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        startPage();
        List<SecurityHazardousChemicalInventory> list = securityHazardousChemicalInventoryService.selectSecurityHazardousChemicalInventoryList(securityHazardousChemicalInventory);
        return getDataTable(list);
    }

    /**
     * 导出危险化学品台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:export')")
    @Log(title = "危险化学品台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        List<SecurityHazardousChemicalInventory> list = securityHazardousChemicalInventoryService.selectSecurityHazardousChemicalInventoryList(securityHazardousChemicalInventory);
        ExcelUtil<SecurityHazardousChemicalInventory> util = new ExcelUtil<SecurityHazardousChemicalInventory>(SecurityHazardousChemicalInventory.class);
        util.exportExcel(response, list, "危险化学品台账数据");
    }

    /**
     * 获取危险化学品台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityHazardousChemicalInventoryService.selectSecurityHazardousChemicalInventoryById(id));
    }

    /**
     * 新增危险化学品台账
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:add')")
    @Log(title = "危险化学品台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        return toAjax(securityHazardousChemicalInventoryService.insertSecurityHazardousChemicalInventory(securityHazardousChemicalInventory));
    }

    /**
     * 修改危险化学品台账
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:edit')")
    @Log(title = "危险化学品台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        return toAjax(securityHazardousChemicalInventoryService.updateSecurityHazardousChemicalInventory(securityHazardousChemicalInventory));
    }

    /**
     * 删除危险化学品台账
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:remove')")
    @Log(title = "危险化学品台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityHazardousChemicalInventoryService.deleteSecurityHazardousChemicalInventoryByIds(ids));
    }
}
