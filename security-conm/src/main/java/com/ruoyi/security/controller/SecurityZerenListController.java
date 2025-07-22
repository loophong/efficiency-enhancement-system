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
import com.ruoyi.security.domain.SecurityZerenList;
import com.ruoyi.security.service.ISecurityZerenListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全责任清单Controller
 * 
 * @author wang
 * @date 2025-07-18
 */
@RestController
@RequestMapping("/security/zerenlist")
public class SecurityZerenListController extends BaseController
{
    @Autowired
    private ISecurityZerenListService securityZerenListService;

    /**
     * 查询安全责任清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:zerenlist:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityZerenList securityZerenList)
    {
        startPage();
        List<SecurityZerenList> list = securityZerenListService.selectSecurityZerenListList(securityZerenList);
        return getDataTable(list);
    }

    /**
     * 导出安全责任清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:zerenlist:export')")
    @Log(title = "安全责任清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityZerenList securityZerenList)
    {
        List<SecurityZerenList> list = securityZerenListService.selectSecurityZerenListList(securityZerenList);
        ExcelUtil<SecurityZerenList> util = new ExcelUtil<SecurityZerenList>(SecurityZerenList.class);
        util.exportExcel(response, list, "安全责任清单数据");
    }

    /**
     * 获取安全责任清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:zerenlist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityZerenListService.selectSecurityZerenListById(id));
    }

    /**
     * 新增安全责任清单
     */
    @PreAuthorize("@ss.hasPermi('security:zerenlist:add')")
    @Log(title = "安全责任清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityZerenList securityZerenList)
    {
        return toAjax(securityZerenListService.insertSecurityZerenList(securityZerenList));
    }

    /**
     * 修改安全责任清单
     */
    @PreAuthorize("@ss.hasPermi('security:zerenlist:edit')")
    @Log(title = "安全责任清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityZerenList securityZerenList)
    {
        return toAjax(securityZerenListService.updateSecurityZerenList(securityZerenList));
    }

    /**
     * 删除安全责任清单
     */
    @PreAuthorize("@ss.hasPermi('security:zerenlist:remove')")
    @Log(title = "安全责任清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityZerenListService.deleteSecurityZerenListByIds(ids));
    }
}
