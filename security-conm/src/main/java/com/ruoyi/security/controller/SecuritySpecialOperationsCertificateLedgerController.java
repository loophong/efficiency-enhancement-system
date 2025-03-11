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
import com.ruoyi.security.domain.SecuritySpecialOperationsCertificateLedger;
import com.ruoyi.security.service.ISecuritySpecialOperationsCertificateLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 特种设备台账Controller
 * 
 * @author wang
 * @date 2025-03-06
 */
@RestController
@RequestMapping("/security/specialoperationscertificate")
public class SecuritySpecialOperationsCertificateLedgerController extends BaseController
{
    @Autowired
    private ISecuritySpecialOperationsCertificateLedgerService securitySpecialOperationsCertificateLedgerService;

    /**
     * 查询特种设备台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationscertificate:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        startPage();
        List<SecuritySpecialOperationsCertificateLedger> list = securitySpecialOperationsCertificateLedgerService.selectSecuritySpecialOperationsCertificateLedgerList(securitySpecialOperationsCertificateLedger);
        return getDataTable(list);
    }

    /**
     * 导出特种设备台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationscertificate:export')")
    @Log(title = "特种设备台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        List<SecuritySpecialOperationsCertificateLedger> list = securitySpecialOperationsCertificateLedgerService.selectSecuritySpecialOperationsCertificateLedgerList(securitySpecialOperationsCertificateLedger);
        ExcelUtil<SecuritySpecialOperationsCertificateLedger> util = new ExcelUtil<SecuritySpecialOperationsCertificateLedger>(SecuritySpecialOperationsCertificateLedger.class);
        util.exportExcel(response, list, "特种设备台账数据");
    }

    /**
     * 获取特种设备台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationscertificate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securitySpecialOperationsCertificateLedgerService.selectSecuritySpecialOperationsCertificateLedgerById(id));
    }

    /**
     * 新增特种设备台账
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationscertificate:add')")
    @Log(title = "特种设备台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        return toAjax(securitySpecialOperationsCertificateLedgerService.insertSecuritySpecialOperationsCertificateLedger(securitySpecialOperationsCertificateLedger));
    }

    /**
     * 修改特种设备台账
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationscertificate:edit')")
    @Log(title = "特种设备台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        return toAjax(securitySpecialOperationsCertificateLedgerService.updateSecuritySpecialOperationsCertificateLedger(securitySpecialOperationsCertificateLedger));
    }

    /**
     * 删除特种设备台账
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationscertificate:remove')")
    @Log(title = "特种设备台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securitySpecialOperationsCertificateLedgerService.deleteSecuritySpecialOperationsCertificateLedgerByIds(ids));
    }
}
