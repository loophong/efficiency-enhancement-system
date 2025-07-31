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
import com.ruoyi.security.domain.SecurityRelationList;
import com.ruoyi.security.service.ISecurityRelationListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 相关方检查记录Controller
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@RestController
@RequestMapping("/security/RelationList1")
public class SecurityRelationListController extends BaseController
{
    @Autowired
    private ISecurityRelationListService securityRelationListService;

    /**
     * 查询相关方检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:RelationList1:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRelationList securityRelationList)
    {
        startPage();
        List<SecurityRelationList> list = securityRelationListService.selectSecurityRelationListList(securityRelationList);
        return getDataTable(list);
    }

    /**
     * 导出相关方检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:RelationList1:export')")
    @Log(title = "相关方检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRelationList securityRelationList)
    {
        List<SecurityRelationList> list = securityRelationListService.selectSecurityRelationListList(securityRelationList);
        ExcelUtil<SecurityRelationList> util = new ExcelUtil<SecurityRelationList>(SecurityRelationList.class);
        util.exportExcel(response, list, "相关方检查记录数据");
    }

    /**
     * 获取相关方检查记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:RelationList1:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityRelationListService.selectSecurityRelationListById(id));
    }

    /**
     * 新增相关方检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:RelationList1:add')")
    @Log(title = "相关方检查记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRelationList securityRelationList)
    {
        return toAjax(securityRelationListService.insertSecurityRelationList(securityRelationList));
    }

    /**
     * 修改相关方检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:RelationList1:edit')")
    @Log(title = "相关方检查记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRelationList securityRelationList)
    {
        return toAjax(securityRelationListService.updateSecurityRelationList(securityRelationList));
    }

    /**
     * 删除相关方检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:RelationList1:remove')")
    @Log(title = "相关方检查记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityRelationListService.deleteSecurityRelationListByIds(ids));
    }
}
