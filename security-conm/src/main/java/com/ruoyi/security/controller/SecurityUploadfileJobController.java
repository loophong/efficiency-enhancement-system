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
import com.ruoyi.security.domain.SecurityUploadfileJob;
import com.ruoyi.security.service.ISecurityUploadfileJobService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备设施安全操作规程Controller
 * 
 * @author wang
 * @date 2025-03-26
 */
@RestController
@RequestMapping("/security/UploadfileJob")
public class SecurityUploadfileJobController extends BaseController
{
    @Autowired
    private ISecurityUploadfileJobService securityUploadfileJobService;

    /**
     * 查询设备设施安全操作规程列表
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileJob:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityUploadfileJob securityUploadfileJob)
    {
        startPage();
        List<SecurityUploadfileJob> list = securityUploadfileJobService.selectSecurityUploadfileJobList(securityUploadfileJob);
        return getDataTable(list);
    }

    /**
     * 导出设备设施安全操作规程列表
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileJob:export')")
    @Log(title = "设备设施安全操作规程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityUploadfileJob securityUploadfileJob)
    {
        List<SecurityUploadfileJob> list = securityUploadfileJobService.selectSecurityUploadfileJobList(securityUploadfileJob);
        ExcelUtil<SecurityUploadfileJob> util = new ExcelUtil<SecurityUploadfileJob>(SecurityUploadfileJob.class);
        util.exportExcel(response, list, "设备设施安全操作规程数据");
    }

    /**
     * 获取设备设施安全操作规程详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileJob:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityUploadfileJobService.selectSecurityUploadfileJobById(id));
    }

    /**
     * 新增设备设施安全操作规程
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileJob:add')")
    @Log(title = "设备设施安全操作规程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityUploadfileJob securityUploadfileJob)
    {
        return toAjax(securityUploadfileJobService.insertSecurityUploadfileJob(securityUploadfileJob));
    }

    /**
     * 修改设备设施安全操作规程
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileJob:edit')")
    @Log(title = "设备设施安全操作规程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityUploadfileJob securityUploadfileJob)
    {
        return toAjax(securityUploadfileJobService.updateSecurityUploadfileJob(securityUploadfileJob));
    }

    /**
     * 删除设备设施安全操作规程
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileJob:remove')")
    @Log(title = "设备设施安全操作规程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityUploadfileJobService.deleteSecurityUploadfileJobByIds(ids));
    }
}
