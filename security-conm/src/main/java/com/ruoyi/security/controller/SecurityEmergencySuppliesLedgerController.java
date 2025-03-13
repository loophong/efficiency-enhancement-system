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
import com.ruoyi.security.domain.SecurityEmergencySuppliesLedger;
import com.ruoyi.security.service.ISecurityEmergencySuppliesLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 应急物资管理台帐Controller
 * 
 * @author wang
 * @date 2025-03-06
 */
@RestController
@RequestMapping("/security/emergencysuppliesledger")
public class SecurityEmergencySuppliesLedgerController extends BaseController
{
    @Autowired
    private ISecurityEmergencySuppliesLedgerService securityEmergencySuppliesLedgerService;

    /**
     * 查询应急物资管理台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        startPage();
        List<SecurityEmergencySuppliesLedger> list = securityEmergencySuppliesLedgerService.selectSecurityEmergencySuppliesLedgerList(securityEmergencySuppliesLedger);
        return getDataTable(list);
    }

    /**
     * 导出应急物资管理台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:export')")
    @Log(title = "应急物资管理台帐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        List<SecurityEmergencySuppliesLedger> list = securityEmergencySuppliesLedgerService.selectSecurityEmergencySuppliesLedgerList(securityEmergencySuppliesLedger);
        ExcelUtil<SecurityEmergencySuppliesLedger> util = new ExcelUtil<SecurityEmergencySuppliesLedger>(SecurityEmergencySuppliesLedger.class);
        util.exportExcel(response, list, "应急物资管理台帐数据");
    }

    /**
     * 获取应急物资管理台帐详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEmergencySuppliesLedgerService.selectSecurityEmergencySuppliesLedgerById(id));
    }

    /**
     * 新增应急物资管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:add')")
    @Log(title = "应急物资管理台帐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        return toAjax(securityEmergencySuppliesLedgerService.insertSecurityEmergencySuppliesLedger(securityEmergencySuppliesLedger));
    }

    /**
     * 修改应急物资管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:edit')")
    @Log(title = "应急物资管理台帐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        return toAjax(securityEmergencySuppliesLedgerService.updateSecurityEmergencySuppliesLedger(securityEmergencySuppliesLedger));
    }

    /**
     * 删除应急物资管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:remove')")
    @Log(title = "应急物资管理台帐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEmergencySuppliesLedgerService.deleteSecurityEmergencySuppliesLedgerByIds(ids));
    }
}
