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
import com.ruoyi.security.domain.SecuritySpecialEquipmentInspectionCertificate;
import com.ruoyi.security.service.ISecuritySpecialEquipmentInspectionCertificateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 特种设备监督检验证书Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/InspectionCertificate")
public class SecuritySpecialEquipmentInspectionCertificateController extends BaseController
{
    @Autowired
    private ISecuritySpecialEquipmentInspectionCertificateService securitySpecialEquipmentInspectionCertificateService;

    /**
     * 查询特种设备监督检验证书列表
     */
    @PreAuthorize("@ss.hasPermi('security:InspectionCertificate:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecuritySpecialEquipmentInspectionCertificate securitySpecialEquipmentInspectionCertificate)
    {
        startPage();
        List<SecuritySpecialEquipmentInspectionCertificate> list = securitySpecialEquipmentInspectionCertificateService.selectSecuritySpecialEquipmentInspectionCertificateList(securitySpecialEquipmentInspectionCertificate);
        return getDataTable(list);
    }

    /**
     * 导出特种设备监督检验证书列表
     */
    @PreAuthorize("@ss.hasPermi('security:InspectionCertificate:export')")
    @Log(title = "特种设备监督检验证书", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecuritySpecialEquipmentInspectionCertificate securitySpecialEquipmentInspectionCertificate)
    {
        List<SecuritySpecialEquipmentInspectionCertificate> list = securitySpecialEquipmentInspectionCertificateService.selectSecuritySpecialEquipmentInspectionCertificateList(securitySpecialEquipmentInspectionCertificate);
        ExcelUtil<SecuritySpecialEquipmentInspectionCertificate> util = new ExcelUtil<SecuritySpecialEquipmentInspectionCertificate>(SecuritySpecialEquipmentInspectionCertificate.class);
        util.exportExcel(response, list, "特种设备监督检验证书数据");
    }

    /**
     * 获取特种设备监督检验证书详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:InspectionCertificate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securitySpecialEquipmentInspectionCertificateService.selectSecuritySpecialEquipmentInspectionCertificateById(id));
    }

    /**
     * 新增特种设备监督检验证书
     */
    @PreAuthorize("@ss.hasPermi('security:InspectionCertificate:add')")
    @Log(title = "特种设备监督检验证书", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecuritySpecialEquipmentInspectionCertificate securitySpecialEquipmentInspectionCertificate)
    {
        return toAjax(securitySpecialEquipmentInspectionCertificateService.insertSecuritySpecialEquipmentInspectionCertificate(securitySpecialEquipmentInspectionCertificate));
    }

    /**
     * 修改特种设备监督检验证书
     */
    @PreAuthorize("@ss.hasPermi('security:InspectionCertificate:edit')")
    @Log(title = "特种设备监督检验证书", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecuritySpecialEquipmentInspectionCertificate securitySpecialEquipmentInspectionCertificate)
    {
        return toAjax(securitySpecialEquipmentInspectionCertificateService.updateSecuritySpecialEquipmentInspectionCertificate(securitySpecialEquipmentInspectionCertificate));
    }

    /**
     * 删除特种设备监督检验证书
     */
    @PreAuthorize("@ss.hasPermi('security:InspectionCertificate:remove')")
    @Log(title = "特种设备监督检验证书", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securitySpecialEquipmentInspectionCertificateService.deleteSecuritySpecialEquipmentInspectionCertificateByIds(ids));
    }
}
