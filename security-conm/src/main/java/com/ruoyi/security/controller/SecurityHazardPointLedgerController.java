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
import com.ruoyi.security.domain.SecurityHazardPointLedger;
import com.ruoyi.security.service.ISecurityHazardPointLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 有点害台账Controller
 * 
 * @author wang
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/security/hazardpointledger")
public class SecurityHazardPointLedgerController extends BaseController
{
    @Autowired
    private ISecurityHazardPointLedgerService securityHazardPointLedgerService;

    /**
     * 查询有点害台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityHazardPointLedger securityHazardPointLedger)
    {
        startPage();
        List<SecurityHazardPointLedger> list = securityHazardPointLedgerService.selectSecurityHazardPointLedgerList(securityHazardPointLedger);
        return getDataTable(list);
    }

    /**
     * 导出有点害台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:export')")
    @Log(title = "有点害台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityHazardPointLedger securityHazardPointLedger)
    {
        List<SecurityHazardPointLedger> list = securityHazardPointLedgerService.selectSecurityHazardPointLedgerList(securityHazardPointLedger);
        ExcelUtil<SecurityHazardPointLedger> util = new ExcelUtil<SecurityHazardPointLedger>(SecurityHazardPointLedger.class);
        util.exportExcel(response, list, "有点害台账数据");
    }

    /**
     * 获取有点害台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityHazardPointLedgerService.selectSecurityHazardPointLedgerById(id));
    }

    /**
     * 新增有点害台账
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:add')")
    @Log(title = "有点害台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityHazardPointLedger securityHazardPointLedger)
    {
        return toAjax(securityHazardPointLedgerService.insertSecurityHazardPointLedger(securityHazardPointLedger));
    }

    /**
     * 修改有点害台账
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:edit')")
    @Log(title = "有点害台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityHazardPointLedger securityHazardPointLedger)
    {
        return toAjax(securityHazardPointLedgerService.updateSecurityHazardPointLedger(securityHazardPointLedger));
    }

    /**
     * 删除有点害台账
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:remove')")
    @Log(title = "有点害台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityHazardPointLedgerService.deleteSecurityHazardPointLedgerByIds(ids));
    }
}
