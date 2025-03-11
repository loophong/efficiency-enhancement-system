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
import com.ruoyi.security.domain.SecurityInternalAuditProgram;
import com.ruoyi.security.service.ISecurityInternalAuditProgramService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 内审方案Controller
 * 
 * @author ruoyi
 * @date 2025-03-10
 */
@RestController
@RequestMapping("/security/InternaPlan")
public class SecurityInternalAuditProgramController extends BaseController
{
    @Autowired
    private ISecurityInternalAuditProgramService securityInternalAuditProgramService;

    /**
     * 查询内审方案列表
     */
    @PreAuthorize("@ss.hasPermi('security:InternaPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityInternalAuditProgram securityInternalAuditProgram)
    {
        startPage();
        List<SecurityInternalAuditProgram> list = securityInternalAuditProgramService.selectSecurityInternalAuditProgramList(securityInternalAuditProgram);
        return getDataTable(list);
    }

    /**
     * 导出内审方案列表
     */
    @PreAuthorize("@ss.hasPermi('security:InternaPlan:export')")
    @Log(title = "内审方案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityInternalAuditProgram securityInternalAuditProgram)
    {
        List<SecurityInternalAuditProgram> list = securityInternalAuditProgramService.selectSecurityInternalAuditProgramList(securityInternalAuditProgram);
        ExcelUtil<SecurityInternalAuditProgram> util = new ExcelUtil<SecurityInternalAuditProgram>(SecurityInternalAuditProgram.class);
        util.exportExcel(response, list, "内审方案数据");
    }

    /**
     * 获取内审方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:InternaPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityInternalAuditProgramService.selectSecurityInternalAuditProgramById(id));
    }

    /**
     * 新增内审方案
     */
    @PreAuthorize("@ss.hasPermi('security:InternaPlan:add')")
    @Log(title = "内审方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityInternalAuditProgram securityInternalAuditProgram)
    {
        return toAjax(securityInternalAuditProgramService.insertSecurityInternalAuditProgram(securityInternalAuditProgram));
    }

    /**
     * 修改内审方案
     */
    @PreAuthorize("@ss.hasPermi('security:InternaPlan:edit')")
    @Log(title = "内审方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityInternalAuditProgram securityInternalAuditProgram)
    {
        return toAjax(securityInternalAuditProgramService.updateSecurityInternalAuditProgram(securityInternalAuditProgram));
    }

    /**
     * 删除内审方案
     */
    @PreAuthorize("@ss.hasPermi('security:InternaPlan:remove')")
    @Log(title = "内审方案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityInternalAuditProgramService.deleteSecurityInternalAuditProgramByIds(ids));
    }
}
