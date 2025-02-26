package com.ruoyi.security.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecurityEnvironmentalOrganizationDescription;
import com.ruoyi.security.service.ISecurityEnvironmentalOrganizationDescriptionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 环境识别Controller
 * 
 * @author 王
 * @date 2025-01-14
 */
@RestController
@RequestMapping("/security/environmentidicaation")
public class SecurityEnvironmentalOrganizationDescriptionController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentalOrganizationDescriptionService securityEnvironmentalOrganizationDescriptionService;

    /**
     * 查询环境识别列表
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        startPage();
        List<SecurityEnvironmentalOrganizationDescription> list = securityEnvironmentalOrganizationDescriptionService.selectSecurityEnvironmentalOrganizationDescriptionList(securityEnvironmentalOrganizationDescription);
        return getDataTable(list);
    }

    /**
     * 导出环境识别列表
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:export')")
    @Log(title = "环境识别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        List<SecurityEnvironmentalOrganizationDescription> list = securityEnvironmentalOrganizationDescriptionService.selectSecurityEnvironmentalOrganizationDescriptionList(securityEnvironmentalOrganizationDescription);
        ExcelUtil<SecurityEnvironmentalOrganizationDescription> util = new ExcelUtil<SecurityEnvironmentalOrganizationDescription>(SecurityEnvironmentalOrganizationDescription.class);
        util.exportExcel(response, list, "环境识别数据");
    }

    /**
     * 导入环境识别数据
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:import')")
    @Log(title = "环境识别", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) throws Exception {
        ExcelUtil<SecurityEnvironmentalOrganizationDescription> util = new ExcelUtil<>(SecurityEnvironmentalOrganizationDescription.class);
        List<SecurityEnvironmentalOrganizationDescription> list = util.importExcel(file.getInputStream());
        int successCount = 0;
        for (SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription : list) {
            if (securityEnvironmentalOrganizationDescriptionService.insertSecurityEnvironmentalOrganizationDescription(securityEnvironmentalOrganizationDescription) > 0) {
                successCount++;
            }
        }
        return toAjax(successCount > 0 && successCount == list.size());
    }
    /**
     * 获取环境识别详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEnvironmentalOrganizationDescriptionService.selectSecurityEnvironmentalOrganizationDescriptionById(id));
    }

    /**
     * 新增环境识别
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:add')")
    @Log(title = "环境识别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        return toAjax(securityEnvironmentalOrganizationDescriptionService.insertSecurityEnvironmentalOrganizationDescription(securityEnvironmentalOrganizationDescription));
    }

    /**
     * 修改环境识别
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:edit')")
    @Log(title = "环境识别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        return toAjax(securityEnvironmentalOrganizationDescriptionService.updateSecurityEnvironmentalOrganizationDescription(securityEnvironmentalOrganizationDescription));
    }

    /**
     * 删除环境识别
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:remove')")
    @Log(title = "环境识别", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEnvironmentalOrganizationDescriptionService.deleteSecurityEnvironmentalOrganizationDescriptionByIds(ids));
    }
}
