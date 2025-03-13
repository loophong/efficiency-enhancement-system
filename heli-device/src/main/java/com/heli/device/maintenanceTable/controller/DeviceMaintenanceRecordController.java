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
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceRecord;
import com.heli.device.maintenanceTable.service.IDeviceMaintenanceRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 记录Controller
 * 
 * @author YYY
 * @date 2025-03-04
 */
@RestController
@RequestMapping("/fault/record")
public class DeviceMaintenanceRecordController extends BaseController
{
    @Autowired
    private IDeviceMaintenanceRecordService deviceMaintenanceRecordService;

    /**
     * 查询记录列表
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceMaintenanceRecord deviceMaintenanceRecord)
    {
        startPage();
        List<DeviceMaintenanceRecord> list = deviceMaintenanceRecordService.selectDeviceMaintenanceRecordList(deviceMaintenanceRecord);
        return getDataTable(list);
    }

    /**
     * 导出记录列表
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:record:export')")
    @Log(title = "记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceMaintenanceRecord deviceMaintenanceRecord)
    {
        List<DeviceMaintenanceRecord> list = deviceMaintenanceRecordService.selectDeviceMaintenanceRecordList(deviceMaintenanceRecord);
        ExcelUtil<DeviceMaintenanceRecord> util = new ExcelUtil<DeviceMaintenanceRecord>(DeviceMaintenanceRecord.class);
        util.exportExcel(response, list, "记录数据");
    }

    /**
     * 获取记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:record:query')")
    @GetMapping(value = "/{maintenanceId}")
    public AjaxResult getInfo(@PathVariable("maintenanceId") Long maintenanceId)
    {
        return success(deviceMaintenanceRecordService.selectDeviceMaintenanceRecordByMaintenanceId(maintenanceId));
    }

    /**
     * 新增记录
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:record:add')")
    @Log(title = "记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceMaintenanceRecord deviceMaintenanceRecord)
    {
        return toAjax(deviceMaintenanceRecordService.insertDeviceMaintenanceRecord(deviceMaintenanceRecord));
    }

    /**
     * 修改记录
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:record:edit')")
    @Log(title = "记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceMaintenanceRecord deviceMaintenanceRecord)
    {
        return toAjax(deviceMaintenanceRecordService.updateDeviceMaintenanceRecord(deviceMaintenanceRecord));
    }

    /**
     * 删除记录
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:record:remove')")
    @Log(title = "记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{maintenanceIds}")
    public AjaxResult remove(@PathVariable Long[] maintenanceIds)
    {
        return toAjax(deviceMaintenanceRecordService.deleteDeviceMaintenanceRecordByMaintenanceIds(maintenanceIds));
    }
}
