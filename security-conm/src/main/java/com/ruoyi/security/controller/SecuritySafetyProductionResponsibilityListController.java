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
import com.ruoyi.security.domain.SecuritySafetyProductionResponsibilityList;
import com.ruoyi.security.service.ISecuritySafetyProductionResponsibilityListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全生产责任制清单Controller
 * 
 * @author ruoyi
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/ProductionResponsibilityList")
public class SecuritySafetyProductionResponsibilityListController extends BaseController
{
    @Autowired
    private ISecuritySafetyProductionResponsibilityListService securitySafetyProductionResponsibilityListService;

    /**
     * 查询安全生产责任制清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        startPage();
        List<SecuritySafetyProductionResponsibilityList> list = securitySafetyProductionResponsibilityListService.selectSecuritySafetyProductionResponsibilityListList(securitySafetyProductionResponsibilityList);
        return getDataTable(list);
    }

    /**
     * 导出安全生产责任制清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:export')")
    @Log(title = "安全生产责任制清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        List<SecuritySafetyProductionResponsibilityList> list = securitySafetyProductionResponsibilityListService.selectSecuritySafetyProductionResponsibilityListList(securitySafetyProductionResponsibilityList);
        ExcelUtil<SecuritySafetyProductionResponsibilityList> util = new ExcelUtil<SecuritySafetyProductionResponsibilityList>(SecuritySafetyProductionResponsibilityList.class);
        util.exportExcel(response, list, "安全生产责任制清单数据");
    }

    /**
     * 获取安全生产责任制清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securitySafetyProductionResponsibilityListService.selectSecuritySafetyProductionResponsibilityListById(id));
    }

    /**
     * 新增安全生产责任制清单
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:add')")
    @Log(title = "安全生产责任制清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        return toAjax(securitySafetyProductionResponsibilityListService.insertSecuritySafetyProductionResponsibilityList(securitySafetyProductionResponsibilityList));
    }

    /**
     * 修改安全生产责任制清单
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:edit')")
    @Log(title = "安全生产责任制清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        return toAjax(securitySafetyProductionResponsibilityListService.updateSecuritySafetyProductionResponsibilityList(securitySafetyProductionResponsibilityList));
    }

    /**
     * 删除安全生产责任制清单
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:remove')")
    @Log(title = "安全生产责任制清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securitySafetyProductionResponsibilityListService.deleteSecuritySafetyProductionResponsibilityListByIds(ids));
    }
}
