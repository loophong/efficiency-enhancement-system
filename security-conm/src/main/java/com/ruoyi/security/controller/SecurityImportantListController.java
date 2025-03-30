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
import com.ruoyi.security.domain.SecurityImportantList;
import com.ruoyi.security.service.ISecurityImportantListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 重点部位检查记录Controller
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@RestController
@RequestMapping("/security/ImportantList")
public class SecurityImportantListController extends BaseController
{
    @Autowired
    private ISecurityImportantListService securityImportantListService;

    /**
     * 查询重点部位检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:ImportantList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityImportantList securityImportantList)
    {
        startPage();
        List<SecurityImportantList> list = securityImportantListService.selectSecurityImportantListList(securityImportantList);
        return getDataTable(list);
    }

    /**
     * 导出重点部位检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:ImportantList:export')")
    @Log(title = "重点部位检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityImportantList securityImportantList)
    {
        List<SecurityImportantList> list = securityImportantListService.selectSecurityImportantListList(securityImportantList);
        ExcelUtil<SecurityImportantList> util = new ExcelUtil<SecurityImportantList>(SecurityImportantList.class);
        util.exportExcel(response, list, "重点部位检查记录数据");
    }

    /**
     * 获取重点部位检查记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:ImportantList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityImportantListService.selectSecurityImportantListById(id));
    }

    /**
     * 新增重点部位检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:ImportantList:add')")
    @Log(title = "重点部位检查记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityImportantList securityImportantList)
    {
        return toAjax(securityImportantListService.insertSecurityImportantList(securityImportantList));
    }

    /**
     * 修改重点部位检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:ImportantList:edit')")
    @Log(title = "重点部位检查记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityImportantList securityImportantList)
    {
        return toAjax(securityImportantListService.updateSecurityImportantList(securityImportantList));
    }

    /**
     * 删除重点部位检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:ImportantList:remove')")
    @Log(title = "重点部位检查记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityImportantListService.deleteSecurityImportantListByIds(ids));
    }
}
