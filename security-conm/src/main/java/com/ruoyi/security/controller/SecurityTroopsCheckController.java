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
import com.ruoyi.security.domain.SecurityTroopsCheck;
import com.ruoyi.security.service.ISecurityTroopsCheckService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 领导干部带队检查记录Controller
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@RestController
@RequestMapping("/security/TroopsCheck")
public class SecurityTroopsCheckController extends BaseController
{
    @Autowired
    private ISecurityTroopsCheckService securityTroopsCheckService;

    /**
     * 查询领导干部带队检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:TroopsCheck:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityTroopsCheck securityTroopsCheck)
    {
        startPage();
        List<SecurityTroopsCheck> list = securityTroopsCheckService.selectSecurityTroopsCheckList(securityTroopsCheck);
        return getDataTable(list);
    }

    /**
     * 导出领导干部带队检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:TroopsCheck:export')")
    @Log(title = "领导干部带队检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityTroopsCheck securityTroopsCheck)
    {
        List<SecurityTroopsCheck> list = securityTroopsCheckService.selectSecurityTroopsCheckList(securityTroopsCheck);
        ExcelUtil<SecurityTroopsCheck> util = new ExcelUtil<SecurityTroopsCheck>(SecurityTroopsCheck.class);
        util.exportExcel(response, list, "领导干部带队检查记录数据");
    }

    /**
     * 获取领导干部带队检查记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:TroopsCheck:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityTroopsCheckService.selectSecurityTroopsCheckById(id));
    }

    /**
     * 新增领导干部带队检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:TroopsCheck:add')")
    @Log(title = "领导干部带队检查记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityTroopsCheck securityTroopsCheck)
    {
        return toAjax(securityTroopsCheckService.insertSecurityTroopsCheck(securityTroopsCheck));
    }

    /**
     * 修改领导干部带队检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:TroopsCheck:edit')")
    @Log(title = "领导干部带队检查记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityTroopsCheck securityTroopsCheck)
    {
        return toAjax(securityTroopsCheckService.updateSecurityTroopsCheck(securityTroopsCheck));
    }

    /**
     * 删除领导干部带队检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:TroopsCheck:remove')")
    @Log(title = "领导干部带队检查记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityTroopsCheckService.deleteSecurityTroopsCheckByIds(ids));
    }
}
