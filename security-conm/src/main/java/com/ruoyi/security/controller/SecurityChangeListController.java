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
import com.ruoyi.security.domain.SecurityChangeList;
import com.ruoyi.security.service.ISecurityChangeListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 变更管理Controller
 * 
 * @author wnag
 * @date 2025-07-21
 */
@RestController
@RequestMapping("/security/changelist")
public class SecurityChangeListController extends BaseController
{
    @Autowired
    private ISecurityChangeListService securityChangeListService;

    /**
     * 查询变更管理列表
     */
    @PreAuthorize("@ss.hasPermi('security:changelist:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityChangeList securityChangeList)
    {
        startPage();
        List<SecurityChangeList> list = securityChangeListService.selectSecurityChangeListList(securityChangeList);
        return getDataTable(list);
    }

    /**
     * 导出变更管理列表
     */
    @PreAuthorize("@ss.hasPermi('security:changelist:export')")
    @Log(title = "变更管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityChangeList securityChangeList)
    {
        List<SecurityChangeList> list = securityChangeListService.selectSecurityChangeListList(securityChangeList);
        ExcelUtil<SecurityChangeList> util = new ExcelUtil<SecurityChangeList>(SecurityChangeList.class);
        util.exportExcel(response, list, "变更管理数据");
    }

    /**
     * 获取变更管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:changelist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityChangeListService.selectSecurityChangeListById(id));
    }

    /**
     * 新增变更管理
     */
    @PreAuthorize("@ss.hasPermi('security:changelist:add')")
    @Log(title = "变更管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityChangeList securityChangeList)
    {
        return toAjax(securityChangeListService.insertSecurityChangeList(securityChangeList));
    }

    /**
     * 修改变更管理
     */
    @PreAuthorize("@ss.hasPermi('security:changelist:edit')")
    @Log(title = "变更管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityChangeList securityChangeList)
    {
        return toAjax(securityChangeListService.updateSecurityChangeList(securityChangeList));
    }

    /**
     * 删除变更管理
     */
    @PreAuthorize("@ss.hasPermi('security:changelist:remove')")
    @Log(title = "变更管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityChangeListService.deleteSecurityChangeListByIds(ids));
    }
}
