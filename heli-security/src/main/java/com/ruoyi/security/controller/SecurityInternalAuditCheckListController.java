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
import com.ruoyi.security.domain.SecurityInternalAuditCheckList;
import com.ruoyi.security.service.ISecurityInternalAuditCheckListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 内审检查Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/AuditCheckList")
public class SecurityInternalAuditCheckListController extends BaseController
{
    @Autowired
    private ISecurityInternalAuditCheckListService securityInternalAuditCheckListService;

    /**
     * 查询内审检查列表
     */
    @PreAuthorize("@ss.hasPermi('security:AuditCheckList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityInternalAuditCheckList securityInternalAuditCheckList)
    {
        startPage();
        List<SecurityInternalAuditCheckList> list = securityInternalAuditCheckListService.selectSecurityInternalAuditCheckListList(securityInternalAuditCheckList);
        return getDataTable(list);
    }

    /**
     * 导出内审检查列表
     */
    @PreAuthorize("@ss.hasPermi('security:AuditCheckList:export')")
    @Log(title = "内审检查", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityInternalAuditCheckList securityInternalAuditCheckList)
    {
        List<SecurityInternalAuditCheckList> list = securityInternalAuditCheckListService.selectSecurityInternalAuditCheckListList(securityInternalAuditCheckList);
        ExcelUtil<SecurityInternalAuditCheckList> util = new ExcelUtil<SecurityInternalAuditCheckList>(SecurityInternalAuditCheckList.class);
        util.exportExcel(response, list, "内审检查数据");
    }

    /**
     * 获取内审检查详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:AuditCheckList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityInternalAuditCheckListService.selectSecurityInternalAuditCheckListById(id));
    }

    /**
     * 新增内审检查
     */
    @PreAuthorize("@ss.hasPermi('security:AuditCheckList:add')")
    @Log(title = "内审检查", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityInternalAuditCheckList securityInternalAuditCheckList)
    {
        return toAjax(securityInternalAuditCheckListService.insertSecurityInternalAuditCheckList(securityInternalAuditCheckList));
    }

    /**
     * 修改内审检查
     */
    @PreAuthorize("@ss.hasPermi('security:AuditCheckList:edit')")
    @Log(title = "内审检查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityInternalAuditCheckList securityInternalAuditCheckList)
    {
        return toAjax(securityInternalAuditCheckListService.updateSecurityInternalAuditCheckList(securityInternalAuditCheckList));
    }

    /**
     * 删除内审检查
     */
    @PreAuthorize("@ss.hasPermi('security:AuditCheckList:remove')")
    @Log(title = "内审检查", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityInternalAuditCheckListService.deleteSecurityInternalAuditCheckListByIds(ids));
    }
}
