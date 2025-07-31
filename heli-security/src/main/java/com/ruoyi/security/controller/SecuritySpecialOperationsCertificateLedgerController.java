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
import com.ruoyi.security.domain.SecuritySpecialOperationsCertificateLedger;
import com.ruoyi.security.service.ISecuritySpecialOperationsCertificateLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger log = LoggerFactory.getLogger(SecuritySpecialOperationsCertificateLedgerController.class);

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

    /**
     * 获取特种设备台账导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecuritySpecialOperationsCertificateLedger> util = new ExcelUtil<SecuritySpecialOperationsCertificateLedger>(SecuritySpecialOperationsCertificateLedger.class);
        util.importTemplateExcel(response, "特种设备台账数据");
    }

    /**
     * 导入特种设备台账数据
     */
    @Log(title = "特种设备台账", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('security:specialoperationscertificate:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SecuritySpecialOperationsCertificateLedger> util = new ExcelUtil<SecuritySpecialOperationsCertificateLedger>(SecuritySpecialOperationsCertificateLedger.class);
        List<SecuritySpecialOperationsCertificateLedger> specialOperationsCertificateLedgerList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = securitySpecialOperationsCertificateLedgerService.importSpecialOperationsCertificateLedger(specialOperationsCertificateLedgerList, updateSupport, operName);
        return success(message);
    }

    /**
     * 根据关联ID查询特种设备台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationscertificate:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public TableDataInfo listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        List<SecuritySpecialOperationsCertificateLedger> list = securitySpecialOperationsCertificateLedgerService.selectSecuritySpecialOperationsCertificateLedgerByRelatedId(relatedId);
        return getDataTable(list);
    }

    /**
     * 根据关联ID查询特种设备台账列表（支持分页和条件查询）
     * 对应URL: /specialdevice?relatedId=427
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationscertificate:list')")
    @GetMapping("/specialdevice")
    public TableDataInfo getSpecialDeviceByRelatedId(
            SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        startPage();
        List<SecuritySpecialOperationsCertificateLedger> list = securitySpecialOperationsCertificateLedgerService.selectSecuritySpecialOperationsCertificateLedgerList(securitySpecialOperationsCertificateLedger);
        return getDataTable(list);
    }
}
