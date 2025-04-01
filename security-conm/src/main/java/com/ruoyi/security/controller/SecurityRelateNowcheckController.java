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
import com.ruoyi.security.domain.SecurityRelateNowcheck;
import com.ruoyi.security.service.ISecurityRelateNowcheckService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 相关方现场检查Controller
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@RestController
@RequestMapping("/security/RelateNowcheck")
public class SecurityRelateNowcheckController extends BaseController
{
    @Autowired
    private ISecurityRelateNowcheckService securityRelateNowcheckService;

    /**
     * 查询相关方现场检查列表
     */
    @PreAuthorize("@ss.hasPermi('security:RelateNowcheck:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRelateNowcheck securityRelateNowcheck)
    {
        startPage();
        List<SecurityRelateNowcheck> list = securityRelateNowcheckService.selectSecurityRelateNowcheckList(securityRelateNowcheck);
        return getDataTable(list);
    }

    /**
     * 导出相关方现场检查列表
     */
    @PreAuthorize("@ss.hasPermi('security:RelateNowcheck:export')")
    @Log(title = "相关方现场检查", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRelateNowcheck securityRelateNowcheck)
    {
        List<SecurityRelateNowcheck> list = securityRelateNowcheckService.selectSecurityRelateNowcheckList(securityRelateNowcheck);
        ExcelUtil<SecurityRelateNowcheck> util = new ExcelUtil<SecurityRelateNowcheck>(SecurityRelateNowcheck.class);
        util.exportExcel(response, list, "相关方现场检查数据");
    }

    /**
     * 获取相关方现场检查详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:RelateNowcheck:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityRelateNowcheckService.selectSecurityRelateNowcheckById(id));
    }

    /**
     * 新增相关方现场检查
     */
    @PreAuthorize("@ss.hasPermi('security:RelateNowcheck:add')")
    @Log(title = "相关方现场检查", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRelateNowcheck securityRelateNowcheck)
    {
        return toAjax(securityRelateNowcheckService.insertSecurityRelateNowcheck(securityRelateNowcheck));
    }

    /**
     * 修改相关方现场检查
     */
    @PreAuthorize("@ss.hasPermi('security:RelateNowcheck:edit')")
    @Log(title = "相关方现场检查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRelateNowcheck securityRelateNowcheck)
    {
        return toAjax(securityRelateNowcheckService.updateSecurityRelateNowcheck(securityRelateNowcheck));
    }

    /**
     * 删除相关方现场检查
     */
    @PreAuthorize("@ss.hasPermi('security:RelateNowcheck:remove')")
    @Log(title = "相关方现场检查", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityRelateNowcheckService.deleteSecurityRelateNowcheckByIds(ids));
    }
}
