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
import com.ruoyi.security.domain.SecurityCompanyKeyWorks;
import com.ruoyi.security.service.ISecurityCompanyKeyWorksService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公司重点工作Controller
 * 
 * @author wang
 * @date 2025-02-27
 */
@RestController
@RequestMapping("/security/works")
public class SecurityCompanyKeyWorksController extends BaseController
{
    @Autowired
    private ISecurityCompanyKeyWorksService securityCompanyKeyWorksService;

    /**
     * 查询公司重点工作列表
     */
    @PreAuthorize("@ss.hasPermi('security:works:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        startPage();
        List<SecurityCompanyKeyWorks> list = securityCompanyKeyWorksService.selectSecurityCompanyKeyWorksList(securityCompanyKeyWorks);
        return getDataTable(list);
    }

    /**
     * 导出公司重点工作列表
     */
    @PreAuthorize("@ss.hasPermi('security:works:export')")
    @Log(title = "公司重点工作", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        List<SecurityCompanyKeyWorks> list = securityCompanyKeyWorksService.selectSecurityCompanyKeyWorksList(securityCompanyKeyWorks);
        ExcelUtil<SecurityCompanyKeyWorks> util = new ExcelUtil<SecurityCompanyKeyWorks>(SecurityCompanyKeyWorks.class);
        util.exportExcel(response, list, "公司重点工作数据");
    }

    /**
     * 获取公司重点工作详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:works:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityCompanyKeyWorksService.selectSecurityCompanyKeyWorksById(id));
    }

    /**
     * 新增公司重点工作
     */
    @PreAuthorize("@ss.hasPermi('security:works:add')")
    @Log(title = "公司重点工作", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        return toAjax(securityCompanyKeyWorksService.insertSecurityCompanyKeyWorks(securityCompanyKeyWorks));
    }

    /**
     * 修改公司重点工作
     */
    @PreAuthorize("@ss.hasPermi('security:works:edit')")
    @Log(title = "公司重点工作", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        return toAjax(securityCompanyKeyWorksService.updateSecurityCompanyKeyWorks(securityCompanyKeyWorks));
    }

    /**
     * 删除公司重点工作
     */
    @PreAuthorize("@ss.hasPermi('security:works:remove')")
    @Log(title = "公司重点工作", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityCompanyKeyWorksService.deleteSecurityCompanyKeyWorksByIds(ids));
    }
}
