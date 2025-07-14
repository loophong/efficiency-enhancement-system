package com.heli.device.fileTable.controller;

import java.util.List;

import com.heli.device.fileTable.domain.DeviceTemplate;
import com.heli.device.fileTable.service.IDeviceTemplateService;
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


import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备导入模板文件Controller
 * 
 * @author YYY
 * @date 2025-07-11
 */
@RestController
@RequestMapping("/file/template")
public class DeviceTemplateController extends BaseController
{
    @Autowired
    private IDeviceTemplateService deviceTemplateService;

    /**
     * 查询设备导入模板文件列表
     */
//    @PreAuthorize("@ss.hasPermi('maintenanceTable:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceTemplate deviceTemplate)
    {
        startPage();
        List<DeviceTemplate> list = deviceTemplateService.selectDeviceTemplateList(deviceTemplate);
        return getDataTable(list);
    }

//    /**
//     * 导出设备导入模板文件列表
//     */
//    @PreAuthorize("@ss.hasPermi('maintenanceTable:template:export')")
//    @Log(title = "设备导入模板文件", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, DeviceTemplate deviceTemplate)
//    {
//        List<DeviceTemplate> list = deviceTemplateService.selectDeviceTemplateList(deviceTemplate);
//        ExcelUtil<DeviceTemplate> util = new ExcelUtil<DeviceTemplate>(DeviceTemplate.class);
//        util.exportExcel(response, list, "设备导入模板文件数据");
//    }

    /**
     * 获取设备导入模板文件详细信息
     */
//    @PreAuthorize("@ss.hasPermi('maintenanceTable:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId)
    {
        return success(deviceTemplateService.selectDeviceTemplateByTemplateId(templateId));
    }

    /**
     * 新增设备导入模板文件
     */
//    @PreAuthorize("@ss.hasPermi('maintenanceTable:template:add')")
    @Log(title = "设备导入模板文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceTemplate deviceTemplate)
    {
        return toAjax(deviceTemplateService.insertDeviceTemplate(deviceTemplate));
    }

    /**
     * 修改设备导入模板文件
     */
//    @PreAuthorize("@ss.hasPermi('maintenanceTable:template:edit')")
    @Log(title = "设备导入模板文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceTemplate deviceTemplate)
    {
        return toAjax(deviceTemplateService.updateDeviceTemplate(deviceTemplate));
    }

    /**
     * 删除设备导入模板文件
     */
//    @PreAuthorize("@ss.hasPermi('maintenanceTable:template:remove')")
    @Log(title = "设备导入模板文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {
        return toAjax(deviceTemplateService.deleteDeviceTemplateByTemplateIds(templateIds));
    }
}
