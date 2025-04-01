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
import com.ruoyi.security.domain.SecurityUploadfileDevice;
import com.ruoyi.security.service.ISecurityUploadfileDeviceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 岗位安全操作规程Controller
 * 
 * @author wang
 * @date 2025-03-26
 */
@RestController
@RequestMapping("/security/UploadfileDevice")
public class SecurityUploadfileDeviceController extends BaseController
{
    @Autowired
    private ISecurityUploadfileDeviceService securityUploadfileDeviceService;

    /**
     * 查询岗位安全操作规程列表
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileDevice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityUploadfileDevice securityUploadfileDevice)
    {
        startPage();
        List<SecurityUploadfileDevice> list = securityUploadfileDeviceService.selectSecurityUploadfileDeviceList(securityUploadfileDevice);
        return getDataTable(list);
    }

    /**
     * 导出岗位安全操作规程列表
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileDevice:export')")
    @Log(title = "岗位安全操作规程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityUploadfileDevice securityUploadfileDevice)
    {
        List<SecurityUploadfileDevice> list = securityUploadfileDeviceService.selectSecurityUploadfileDeviceList(securityUploadfileDevice);
        ExcelUtil<SecurityUploadfileDevice> util = new ExcelUtil<SecurityUploadfileDevice>(SecurityUploadfileDevice.class);
        util.exportExcel(response, list, "岗位安全操作规程数据");
    }

    /**
     * 获取岗位安全操作规程详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileDevice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityUploadfileDeviceService.selectSecurityUploadfileDeviceById(id));
    }

    /**
     * 新增岗位安全操作规程
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileDevice:add')")
    @Log(title = "岗位安全操作规程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityUploadfileDevice securityUploadfileDevice)
    {
        return toAjax(securityUploadfileDeviceService.insertSecurityUploadfileDevice(securityUploadfileDevice));
    }

    /**
     * 修改岗位安全操作规程
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileDevice:edit')")
    @Log(title = "岗位安全操作规程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityUploadfileDevice securityUploadfileDevice)
    {
        return toAjax(securityUploadfileDeviceService.updateSecurityUploadfileDevice(securityUploadfileDevice));
    }

    /**
     * 删除岗位安全操作规程
     */
    @PreAuthorize("@ss.hasPermi('security:UploadfileDevice:remove')")
    @Log(title = "岗位安全操作规程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityUploadfileDeviceService.deleteSecurityUploadfileDeviceByIds(ids));
    }
}
