package com.heli.device.maintenanceTable.controller;

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
import com.heli.device.maintenanceTable.domain.DeviceIndicatorCount;
import com.heli.device.maintenanceTable.service.IDeviceIndicatorCountService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备指标分析Controller
 * 
 * @author YYY
 * @date 2025-01-18
 */
@RestController
@RequestMapping("/fault/indicator")
public class DeviceIndicatorCountController extends BaseController
{
    @Autowired
    private IDeviceIndicatorCountService deviceIndicatorCountService;

    /**
     * 查询设备指标分析列表
     */
    @PreAuthorize("@ss.hasPermi('fault:indicator:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceIndicatorCount deviceIndicatorCount)
    {
        startPage();
        List<DeviceIndicatorCount> list = deviceIndicatorCountService.selectDeviceIndicatorCountList(deviceIndicatorCount);
        return getDataTable(list);
    }

    /**
     * 导出设备指标分析列表
     */
    @PreAuthorize("@ss.hasPermi('fault:indicator:export')")
    @Log(title = "设备指标分析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceIndicatorCount deviceIndicatorCount)
    {
        List<DeviceIndicatorCount> list = deviceIndicatorCountService.selectDeviceIndicatorCountList(deviceIndicatorCount);
        ExcelUtil<DeviceIndicatorCount> util = new ExcelUtil<DeviceIndicatorCount>(DeviceIndicatorCount.class);
        util.exportExcel(response, list, "设备指标分析数据");
    }

    /**
     * 获取设备指标分析详细信息
     */
    @PreAuthorize("@ss.hasPermi('fault:indicator:query')")
    @GetMapping(value = "/{indicatorId}")
    public AjaxResult getInfo(@PathVariable("indicatorId") Long indicatorId)
    {
        return success(deviceIndicatorCountService.selectDeviceIndicatorCountByIndicatorId(indicatorId));
    }

    /**
     * 新增设备指标分析
     */
    @PreAuthorize("@ss.hasPermi('fault:indicator:add')")
    @Log(title = "设备指标分析", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceIndicatorCount deviceIndicatorCount)
    {
        return toAjax(deviceIndicatorCountService.insertDeviceIndicatorCount(deviceIndicatorCount));
    }

    /**
     * 修改设备指标分析
     */
    @PreAuthorize("@ss.hasPermi('fault:indicator:edit')")
    @Log(title = "设备指标分析", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceIndicatorCount deviceIndicatorCount)
    {
        return toAjax(deviceIndicatorCountService.updateDeviceIndicatorCount(deviceIndicatorCount));
    }

    /**
     * 删除设备指标分析
     */
    @PreAuthorize("@ss.hasPermi('fault:indicator:remove')")
    @Log(title = "设备指标分析", businessType = BusinessType.DELETE)
	@DeleteMapping("/{indicatorIds}")
    public AjaxResult remove(@PathVariable Long[] indicatorIds)
    {
        return toAjax(deviceIndicatorCountService.deleteDeviceIndicatorCountByIndicatorIds(indicatorIds));
    }
}
