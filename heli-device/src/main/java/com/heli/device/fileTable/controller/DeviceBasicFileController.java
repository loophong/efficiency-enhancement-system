package com.heli.device.fileTable.controller;

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
import com.heli.device.fileTable.domain.DeviceBasicFile;
import com.heli.device.fileTable.service.IDeviceBasicFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备基础档案Controller
 * 
 * @author YYY
 * @date 2025-01-19
 */
@RestController
@RequestMapping("/file/basic")
public class DeviceBasicFileController extends BaseController
{
    @Autowired
    private IDeviceBasicFileService deviceBasicFileService;

    /**
     * 查询设备基础档案列表
     */
    @PreAuthorize("@ss.hasPermi('file:basic:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceBasicFile deviceBasicFile)
    {
        startPage();
        List<DeviceBasicFile> list = deviceBasicFileService.selectDeviceBasicFileList(deviceBasicFile);
        return getDataTable(list);
    }

    /**
     * 导出设备基础档案列表
     */
    @PreAuthorize("@ss.hasPermi('file:basic:export')")
    @Log(title = "设备基础档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceBasicFile deviceBasicFile)
    {
        List<DeviceBasicFile> list = deviceBasicFileService.selectDeviceBasicFileList(deviceBasicFile);
        ExcelUtil<DeviceBasicFile> util = new ExcelUtil<DeviceBasicFile>(DeviceBasicFile.class);
        util.exportExcel(response, list, "设备基础档案数据");
    }

    /**
     * 获取设备基础档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('file:basic:query')")
    @GetMapping(value = "/{basicFileId}")
    public AjaxResult getInfo(@PathVariable("basicFileId") String basicFileId)
    {
        return success(deviceBasicFileService.selectDeviceBasicFileByBasicFileId(basicFileId));
    }

    /**
     * 新增设备基础档案
     */
    @PreAuthorize("@ss.hasPermi('file:basic:add')")
    @Log(title = "设备基础档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceBasicFile deviceBasicFile)
    {
        return toAjax(deviceBasicFileService.insertDeviceBasicFile(deviceBasicFile));
    }

    /**
     * 修改设备基础档案
     */
    @PreAuthorize("@ss.hasPermi('file:basic:edit')")
    @Log(title = "设备基础档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceBasicFile deviceBasicFile)
    {
        return toAjax(deviceBasicFileService.updateDeviceBasicFile(deviceBasicFile));
    }

    /**
     * 删除设备基础档案
     */
    @PreAuthorize("@ss.hasPermi('file:basic:remove')")
    @Log(title = "设备基础档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{basicFileIds}")
    public AjaxResult remove(@PathVariable String[] basicFileIds)
    {
        return toAjax(deviceBasicFileService.deleteDeviceBasicFileByBasicFileIds(basicFileIds));
    }
}
