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
import com.ruoyi.security.domain.SecurityDangerWangList;
import com.ruoyi.security.service.ISecurityDangerWangListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 风险网格化清单Controller
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@RestController
@RequestMapping("/security/DangerWangList")
public class SecurityDangerWangListController extends BaseController
{
    @Autowired
    private ISecurityDangerWangListService securityDangerWangListService;

    /**
     * 查询风险网格化清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:DangerWangList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityDangerWangList securityDangerWangList)
    {
        startPage();
        List<SecurityDangerWangList> list = securityDangerWangListService.selectSecurityDangerWangListList(securityDangerWangList);
        return getDataTable(list);
    }

    /**
     * 导出风险网格化清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:DangerWangList:export')")
    @Log(title = "风险网格化清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityDangerWangList securityDangerWangList)
    {
        List<SecurityDangerWangList> list = securityDangerWangListService.selectSecurityDangerWangListList(securityDangerWangList);
        ExcelUtil<SecurityDangerWangList> util = new ExcelUtil<SecurityDangerWangList>(SecurityDangerWangList.class);
        util.exportExcel(response, list, "风险网格化清单数据");
    }

    /**
     * 获取风险网格化清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:DangerWangList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityDangerWangListService.selectSecurityDangerWangListById(id));
    }

    /**
     * 新增风险网格化清单
     */
    @PreAuthorize("@ss.hasPermi('security:DangerWangList:add')")
    @Log(title = "风险网格化清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityDangerWangList securityDangerWangList)
    {
        return toAjax(securityDangerWangListService.insertSecurityDangerWangList(securityDangerWangList));
    }

    /**
     * 修改风险网格化清单
     */
    @PreAuthorize("@ss.hasPermi('security:DangerWangList:edit')")
    @Log(title = "风险网格化清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityDangerWangList securityDangerWangList)
    {
        return toAjax(securityDangerWangListService.updateSecurityDangerWangList(securityDangerWangList));
    }

    /**
     * 删除风险网格化清单
     */
    @PreAuthorize("@ss.hasPermi('security:DangerWangList:remove')")
    @Log(title = "风险网格化清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityDangerWangListService.deleteSecurityDangerWangListByIds(ids));
    }
}
