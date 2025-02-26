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
import com.heli.device.fileTable.domain.DeviceDetails;
import com.heli.device.fileTable.service.IDeviceDetailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备台账Controller
 * 
 * @author YYY
 * @date 2025-01-19
 */
@RestController
@RequestMapping("/file/details")
public class DeviceDetailsController extends BaseController
{
    @Autowired
    private IDeviceDetailsService deviceDetailsService;

    /**
     * 查询设备台账列表
     */
    @PreAuthorize("@ss.hasPermi('file:details:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceDetails deviceDetails)
    {
        startPage();
        List<DeviceDetails> list = deviceDetailsService.selectDeviceDetailsList(deviceDetails);
        return getDataTable(list);
    }

    /**
     * 导出设备台账列表
     */
    @PreAuthorize("@ss.hasPermi('file:details:export')")
    @Log(title = "设备台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceDetails deviceDetails)
    {
        List<DeviceDetails> list = deviceDetailsService.selectDeviceDetailsList(deviceDetails);
        ExcelUtil<DeviceDetails> util = new ExcelUtil<DeviceDetails>(DeviceDetails.class);
        util.exportExcel(response, list, "设备台账数据");
    }

    /**
     * 获取设备台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('file:details:query')")
    @GetMapping(value = "/{detailsId}")
    public AjaxResult getInfo(@PathVariable("detailsId") String detailsId)
    {
        return success(deviceDetailsService.selectDeviceDetailsByDetailsId(detailsId));
    }

    /**
     * 新增设备台账
     */
    @PreAuthorize("@ss.hasPermi('file:details:add')")
    @Log(title = "设备台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceDetails deviceDetails)
    {
        return toAjax(deviceDetailsService.insertDeviceDetails(deviceDetails));
    }

    /**
     * 修改设备台账
     */
    @PreAuthorize("@ss.hasPermi('file:details:edit')")
    @Log(title = "设备台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceDetails deviceDetails)
    {
        return toAjax(deviceDetailsService.updateDeviceDetails(deviceDetails));
    }

    /**
     * 删除设备台账
     */
    @PreAuthorize("@ss.hasPermi('file:details:remove')")
    @Log(title = "设备台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailsIds}")
    public AjaxResult remove(@PathVariable String[] detailsIds)
    {
        return toAjax(deviceDetailsService.deleteDeviceDetailsByDetailsIds(detailsIds));
    }
}
