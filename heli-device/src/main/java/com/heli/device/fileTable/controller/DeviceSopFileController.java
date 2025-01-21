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
import com.heli.device.fileTable.domain.DeviceSopFile;
import com.heli.device.fileTable.service.IDeviceSopFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * SOP文件管理Controller
 * 
 * @author YYY
 * @date 2025-01-19
 */
@RestController
@RequestMapping("/file/sop")
public class DeviceSopFileController extends BaseController
{
    @Autowired
    private IDeviceSopFileService deviceSopFileService;

    /**
     * 查询SOP文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('file:sop:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceSopFile deviceSopFile)
    {
        startPage();
        List<DeviceSopFile> list = deviceSopFileService.selectDeviceSopFileList(deviceSopFile);
        return getDataTable(list);
    }

    /**
     * 导出SOP文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('file:sop:export')")
    @Log(title = "SOP文件管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceSopFile deviceSopFile)
    {
        List<DeviceSopFile> list = deviceSopFileService.selectDeviceSopFileList(deviceSopFile);
        ExcelUtil<DeviceSopFile> util = new ExcelUtil<DeviceSopFile>(DeviceSopFile.class);
        util.exportExcel(response, list, "SOP文件管理数据");
    }

    /**
     * 获取SOP文件管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('file:sop:query')")
    @GetMapping(value = "/{sopFileId}")
    public AjaxResult getInfo(@PathVariable("sopFileId") String sopFileId)
    {
        return success(deviceSopFileService.selectDeviceSopFileBySopFileId(sopFileId));
    }

    /**
     * 新增SOP文件管理
     */
    @PreAuthorize("@ss.hasPermi('file:sop:add')")
    @Log(title = "SOP文件管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceSopFile deviceSopFile)
    {
        return toAjax(deviceSopFileService.insertDeviceSopFile(deviceSopFile));
    }

    /**
     * 修改SOP文件管理
     */
    @PreAuthorize("@ss.hasPermi('file:sop:edit')")
    @Log(title = "SOP文件管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceSopFile deviceSopFile)
    {
        return toAjax(deviceSopFileService.updateDeviceSopFile(deviceSopFile));
    }

    /**
     * 删除SOP文件管理
     */
    @PreAuthorize("@ss.hasPermi('file:sop:remove')")
    @Log(title = "SOP文件管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sopFileIds}")
    public AjaxResult remove(@PathVariable String[] sopFileIds)
    {
        return toAjax(deviceSopFileService.deleteDeviceSopFileBySopFileIds(sopFileIds));
    }
}
