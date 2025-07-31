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
import com.ruoyi.security.domain.SecuritySpecialEquipmentPersonCertificate;
import com.ruoyi.security.service.ISecuritySpecialEquipmentPersonCertificateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 特种设备人员证件Controller
 * 
 * @author ruoyi
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/PersonCertificate")
public class SecuritySpecialEquipmentPersonCertificateController extends BaseController
{
    @Autowired
    private ISecuritySpecialEquipmentPersonCertificateService securitySpecialEquipmentPersonCertificateService;

    /**
     * 查询特种设备人员证件列表
     */
    @PreAuthorize("@ss.hasPermi('security:PersonCertificate:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecuritySpecialEquipmentPersonCertificate securitySpecialEquipmentPersonCertificate)
    {
        startPage();
        List<SecuritySpecialEquipmentPersonCertificate> list = securitySpecialEquipmentPersonCertificateService.selectSecuritySpecialEquipmentPersonCertificateList(securitySpecialEquipmentPersonCertificate);
        return getDataTable(list);
    }

    /**
     * 导出特种设备人员证件列表
     */
    @PreAuthorize("@ss.hasPermi('security:PersonCertificate:export')")
    @Log(title = "特种设备人员证件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecuritySpecialEquipmentPersonCertificate securitySpecialEquipmentPersonCertificate)
    {
        List<SecuritySpecialEquipmentPersonCertificate> list = securitySpecialEquipmentPersonCertificateService.selectSecuritySpecialEquipmentPersonCertificateList(securitySpecialEquipmentPersonCertificate);
        ExcelUtil<SecuritySpecialEquipmentPersonCertificate> util = new ExcelUtil<SecuritySpecialEquipmentPersonCertificate>(SecuritySpecialEquipmentPersonCertificate.class);
        util.exportExcel(response, list, "特种设备人员证件数据");
    }

    /**
     * 获取特种设备人员证件详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:PersonCertificate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securitySpecialEquipmentPersonCertificateService.selectSecuritySpecialEquipmentPersonCertificateById(id));
    }

    /**
     * 新增特种设备人员证件
     */
    @PreAuthorize("@ss.hasPermi('security:PersonCertificate:add')")
    @Log(title = "特种设备人员证件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecuritySpecialEquipmentPersonCertificate securitySpecialEquipmentPersonCertificate)
    {
        return toAjax(securitySpecialEquipmentPersonCertificateService.insertSecuritySpecialEquipmentPersonCertificate(securitySpecialEquipmentPersonCertificate));
    }

    /**
     * 修改特种设备人员证件
     */
    @PreAuthorize("@ss.hasPermi('security:PersonCertificate:edit')")
    @Log(title = "特种设备人员证件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecuritySpecialEquipmentPersonCertificate securitySpecialEquipmentPersonCertificate)
    {
        return toAjax(securitySpecialEquipmentPersonCertificateService.updateSecuritySpecialEquipmentPersonCertificate(securitySpecialEquipmentPersonCertificate));
    }

    /**
     * 删除特种设备人员证件
     */
    @PreAuthorize("@ss.hasPermi('security:PersonCertificate:remove')")
    @Log(title = "特种设备人员证件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securitySpecialEquipmentPersonCertificateService.deleteSecuritySpecialEquipmentPersonCertificateByIds(ids));
    }
}
