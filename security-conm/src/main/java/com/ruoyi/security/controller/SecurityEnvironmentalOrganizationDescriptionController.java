package com.ruoyi.security.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.exception.ServiceException;
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
import com.ruoyi.security.domain.SecurityEnvironmentalOrganizationDescription;
import com.ruoyi.security.service.ISecurityEnvironmentalOrganizationDescriptionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 环境识别Controller
 *
 * @author wang
 * @date 2025-03-05
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

    /**
     * 导入环境识别数据
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:import')")
    @Log(title = "环境识别", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) {
        try {
            ExcelUtil<SecurityEnvironmentalOrganizationDescription> util = new ExcelUtil<>(SecurityEnvironmentalOrganizationDescription.class);
            List<SecurityEnvironmentalOrganizationDescription> list = util.importExcel(file.getInputStream());
            // 自動補齊 environment
            String lastEnv = null;
            for (SecurityEnvironmentalOrganizationDescription item : list) {
                if (item == null) continue;
                if (item.getEnvironment() != null && !item.getEnvironment().trim().isEmpty()) {
                    lastEnv = item.getEnvironment();
                } else {
                    item.setEnvironment(lastEnv);
                }
            }
            // 過濾有效行
            List<SecurityEnvironmentalOrganizationDescription> validList = list.stream()
                .filter(item -> item != null && (
                    (item.getEnvironment() != null && !item.getEnvironment().trim().isEmpty())
                    || (item.getFeatures() != null && !item.getFeatures().trim().isEmpty())
                    || (item.getDescription() != null && !item.getDescription().trim().isEmpty())
                ))
                .collect(Collectors.toList());
            for (SecurityEnvironmentalOrganizationDescription item : validList) {
                securityEnvironmentalOrganizationDescriptionService.insertSecurityEnvironmentalOrganizationDescription(item);
            }
            return AjaxResult.success("导入成功，共" + validList.size() + "条数据");
        } catch (Exception e) {
            throw new com.ruoyi.common.exception.ServiceException("导入失败：" + e.getMessage());
        }
    }

    /**
     * 下载导入模板（只包含三個字段，並合併單元格）
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:import')")
    @GetMapping("/import/template")
    public void importTemplate(HttpServletResponse response) {
        try {
            // 只導出三個字段的模板
            List<SecurityEnvironmentalOrganizationDescription> list = List.of();
            ExcelUtil<SecurityEnvironmentalOrganizationDescription> util = new ExcelUtil<>(SecurityEnvironmentalOrganizationDescription.class);
            util.showColumn("environment", "features", "description");
            util.exportExcel(response, list, "环境识别模板");
            // 合併單元格（如需合併，需手動操作POI，這裡僅導出三列表頭，合併可根據需求擴展）
        } catch (Exception e) {
            throw new ServiceException("模板下载失败：" + e.getMessage());
        }
    }
}
