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
import com.ruoyi.security.domain.SecurityWeekendList;
import com.ruoyi.security.service.ISecurityWeekendListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 节假日检查记录Controller
 * 
 * @author wang
 * @date 2025-03-28
 */
@RestController
@RequestMapping("/security/WeekendList")
public class SecurityWeekendListController extends BaseController
{
    @Autowired
    private ISecurityWeekendListService securityWeekendListService;

    /**
     * 查询节假日检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:WeekendList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityWeekendList securityWeekendList)
    {
        startPage();
        List<SecurityWeekendList> list = securityWeekendListService.selectSecurityWeekendListList(securityWeekendList);
        return getDataTable(list);
    }

    /**
     * 导出节假日检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:WeekendList:export')")
    @Log(title = "节假日检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityWeekendList securityWeekendList)
    {
        List<SecurityWeekendList> list = securityWeekendListService.selectSecurityWeekendListList(securityWeekendList);
        ExcelUtil<SecurityWeekendList> util = new ExcelUtil<SecurityWeekendList>(SecurityWeekendList.class);
        util.exportExcel(response, list, "节假日检查记录数据");
    }

    /**
     * 获取节假日检查记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:WeekendList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityWeekendListService.selectSecurityWeekendListById(id));
    }

    /**
     * 新增节假日检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:WeekendList:add')")
    @Log(title = "节假日检查记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityWeekendList securityWeekendList)
    {
        return toAjax(securityWeekendListService.insertSecurityWeekendList(securityWeekendList));
    }

    /**
     * 修改节假日检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:WeekendList:edit')")
    @Log(title = "节假日检查记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityWeekendList securityWeekendList)
    {
        return toAjax(securityWeekendListService.updateSecurityWeekendList(securityWeekendList));
    }

    /**
     * 删除节假日检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:WeekendList:remove')")
    @Log(title = "节假日检查记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityWeekendListService.deleteSecurityWeekendListByIds(ids));
    }
}
