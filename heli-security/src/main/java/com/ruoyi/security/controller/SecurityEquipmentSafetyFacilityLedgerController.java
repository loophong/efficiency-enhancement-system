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
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecurityEquipmentSafetyFacilityLedger;
import com.ruoyi.security.service.ISecurityEquipmentSafetyFacilityLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全防护设备设施Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/SafetyFacilityLedger")
public class SecurityEquipmentSafetyFacilityLedgerController extends BaseController
{
    @Autowired
    private ISecurityEquipmentSafetyFacilityLedgerService securityEquipmentSafetyFacilityLedgerService;

    /**
     * 查询安全防护设备设施列表
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyFacilityLedger:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger)
    {
        startPage();
        List<SecurityEquipmentSafetyFacilityLedger> list = securityEquipmentSafetyFacilityLedgerService.selectSecurityEquipmentSafetyFacilityLedgerList(securityEquipmentSafetyFacilityLedger);
        return getDataTable(list);
    }

    /**
     * 导出安全防护设备设施列表
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyFacilityLedger:export')")
    @Log(title = "安全防护设备设施", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger)
    {
        List<SecurityEquipmentSafetyFacilityLedger> list = securityEquipmentSafetyFacilityLedgerService.selectSecurityEquipmentSafetyFacilityLedgerList(securityEquipmentSafetyFacilityLedger);
        ExcelUtil<SecurityEquipmentSafetyFacilityLedger> util = new ExcelUtil<SecurityEquipmentSafetyFacilityLedger>(SecurityEquipmentSafetyFacilityLedger.class);
        util.exportExcel(response, list, "安全防护设备设施数据");
    }

    /**
     * 获取安全防护设备设施详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyFacilityLedger:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEquipmentSafetyFacilityLedgerService.selectSecurityEquipmentSafetyFacilityLedgerById(id));
    }

    /**
     * 新增安全防护设备设施
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyFacilityLedger:add')")
    @Log(title = "安全防护设备设施", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger)
    {
        return toAjax(securityEquipmentSafetyFacilityLedgerService.insertSecurityEquipmentSafetyFacilityLedger(securityEquipmentSafetyFacilityLedger));
    }

    /**
     * 修改安全防护设备设施
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyFacilityLedger:edit')")
    @Log(title = "安全防护设备设施", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger)
    {
        return toAjax(securityEquipmentSafetyFacilityLedgerService.updateSecurityEquipmentSafetyFacilityLedger(securityEquipmentSafetyFacilityLedger));
    }

    /**
     * 删除安全防护设备设施
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyFacilityLedger:remove')")
    @Log(title = "安全防护设备设施", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEquipmentSafetyFacilityLedgerService.deleteSecurityEquipmentSafetyFacilityLedgerByIds(ids));
    }

    /**
     * 导入安全防护设备设施数据
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyFacilityLedger:import')")
    @Log(title = "安全防护设备设施", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        String message = securityEquipmentSafetyFacilityLedgerService.importData(file.getInputStream(), updateSupport);
        return success(message);
    }

    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyFacilityLedger:export')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecurityEquipmentSafetyFacilityLedger> util = new ExcelUtil<SecurityEquipmentSafetyFacilityLedger>(SecurityEquipmentSafetyFacilityLedger.class);
        util.importTemplateExcel(response, "安全防护设备设施数据");
    }

    /**
     * 根据关联ID查询安全防护设备设施列表
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyFacilityLedger:list')")
    @GetMapping("/listByRelatedId")
    public TableDataInfo listByRelatedId(Long relatedId, String sourceModule)
    {
        startPage();
        List<SecurityEquipmentSafetyFacilityLedger> list = securityEquipmentSafetyFacilityLedgerService.selectSecurityEquipmentSafetyFacilityLedgerByRelatedId(relatedId);
        return getDataTable(list);
    }
}
