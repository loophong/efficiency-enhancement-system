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
import com.ruoyi.security.domain.SecurityOccupationalHealthProtectiveEquipmentLedger;
import com.ruoyi.security.service.ISecurityOccupationalHealthProtectiveEquipmentLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 职业健康防护用品台帐Controller
 * 
 * @author wang
 * @date 2025-03-06
 */
@RestController
@RequestMapping("/security/healthprotectiveequipment")
public class SecurityOccupationalHealthProtectiveEquipmentLedgerController extends BaseController
{
    @Autowired
    private ISecurityOccupationalHealthProtectiveEquipmentLedgerService securityOccupationalHealthProtectiveEquipmentLedgerService;

    /**
     * 查询职业健康防护用品台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:healthprotectiveequipment:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger)
    {
        startPage();
        List<SecurityOccupationalHealthProtectiveEquipmentLedger> list = securityOccupationalHealthProtectiveEquipmentLedgerService.selectSecurityOccupationalHealthProtectiveEquipmentLedgerList(securityOccupationalHealthProtectiveEquipmentLedger);
        return getDataTable(list);
    }

    /**
     * 导出职业健康防护用品台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:healthprotectiveequipment:export')")
    @Log(title = "职业健康防护用品台帐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger)
    {
        List<SecurityOccupationalHealthProtectiveEquipmentLedger> list = securityOccupationalHealthProtectiveEquipmentLedgerService.selectSecurityOccupationalHealthProtectiveEquipmentLedgerList(securityOccupationalHealthProtectiveEquipmentLedger);
        ExcelUtil<SecurityOccupationalHealthProtectiveEquipmentLedger> util = new ExcelUtil<SecurityOccupationalHealthProtectiveEquipmentLedger>(SecurityOccupationalHealthProtectiveEquipmentLedger.class);
        util.exportExcel(response, list, "职业健康防护用品台帐数据");
    }

    /**
     * 获取职业健康防护用品台帐详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:healthprotectiveequipment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityOccupationalHealthProtectiveEquipmentLedgerService.selectSecurityOccupationalHealthProtectiveEquipmentLedgerById(id));
    }

    /**
     * 新增职业健康防护用品台帐
     */
    @PreAuthorize("@ss.hasPermi('security:healthprotectiveequipment:add')")
    @Log(title = "职业健康防护用品台帐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger)
    {
        return toAjax(securityOccupationalHealthProtectiveEquipmentLedgerService.insertSecurityOccupationalHealthProtectiveEquipmentLedger(securityOccupationalHealthProtectiveEquipmentLedger));
    }

    /**
     * 修改职业健康防护用品台帐
     */
    @PreAuthorize("@ss.hasPermi('security:healthprotectiveequipment:edit')")
    @Log(title = "职业健康防护用品台帐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger)
    {
        return toAjax(securityOccupationalHealthProtectiveEquipmentLedgerService.updateSecurityOccupationalHealthProtectiveEquipmentLedger(securityOccupationalHealthProtectiveEquipmentLedger));
    }

    /**
     * 删除职业健康防护用品台帐
     */
    @PreAuthorize("@ss.hasPermi('security:healthprotectiveequipment:remove')")
    @Log(title = "职业健康防护用品台帐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityOccupationalHealthProtectiveEquipmentLedgerService.deleteSecurityOccupationalHealthProtectiveEquipmentLedgerByIds(ids));
    }
}
