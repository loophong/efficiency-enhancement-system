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
import com.ruoyi.security.domain.SecuritySpecialCheck;
import com.ruoyi.security.service.ISecuritySpecialCheckService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 专项检查记录Controller
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@RestController
@RequestMapping("/security/SpecialCheck")
public class SecuritySpecialCheckController extends BaseController
{
    @Autowired
    private ISecuritySpecialCheckService securitySpecialCheckService;

    /**
     * 查询专项检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:SpecialCheck:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecuritySpecialCheck securitySpecialCheck)
    {
        startPage();
        List<SecuritySpecialCheck> list = securitySpecialCheckService.selectSecuritySpecialCheckList(securitySpecialCheck);
        return getDataTable(list);
    }

    /**
     * 导出专项检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:SpecialCheck:export')")
    @Log(title = "专项检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecuritySpecialCheck securitySpecialCheck)
    {
        List<SecuritySpecialCheck> list = securitySpecialCheckService.selectSecuritySpecialCheckList(securitySpecialCheck);
        ExcelUtil<SecuritySpecialCheck> util = new ExcelUtil<SecuritySpecialCheck>(SecuritySpecialCheck.class);
        util.exportExcel(response, list, "专项检查记录数据");
    }

    /**
     * 获取专项检查记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:SpecialCheck:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securitySpecialCheckService.selectSecuritySpecialCheckById(id));
    }

    /**
     * 新增专项检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:SpecialCheck:add')")
    @Log(title = "专项检查记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecuritySpecialCheck securitySpecialCheck)
    {
        return toAjax(securitySpecialCheckService.insertSecuritySpecialCheck(securitySpecialCheck));
    }

    /**
     * 修改专项检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:SpecialCheck:edit')")
    @Log(title = "专项检查记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecuritySpecialCheck securitySpecialCheck)
    {
        return toAjax(securitySpecialCheckService.updateSecuritySpecialCheck(securitySpecialCheck));
    }

    /**
     * 删除专项检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:SpecialCheck:remove')")
    @Log(title = "专项检查记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securitySpecialCheckService.deleteSecuritySpecialCheckByIds(ids));
    }
}
