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
import com.heli.device.maintenanceTable.domain.DeviceGroupPlan;
import com.heli.device.maintenanceTable.service.IDeviceGroupPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 班组计划保养Controller
 * 
 * @author YYY
 * @date 2025-01-19
 */
@RestController
@RequestMapping("/device/group")
public class DeviceGroupPlanController extends BaseController
{
    @Autowired
    private IDeviceGroupPlanService deviceGroupPlanService;

    /**
     * 查询班组计划保养列表
     */
    @PreAuthorize("@ss.hasPermi('device:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceGroupPlan deviceGroupPlan)
    {
        startPage();
        List<DeviceGroupPlan> list = deviceGroupPlanService.selectDeviceGroupPlanList(deviceGroupPlan);
        return getDataTable(list);
    }

    /**
     * 导出班组计划保养列表
     */
    @PreAuthorize("@ss.hasPermi('device:group:export')")
    @Log(title = "班组计划保养", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceGroupPlan deviceGroupPlan)
    {
        List<DeviceGroupPlan> list = deviceGroupPlanService.selectDeviceGroupPlanList(deviceGroupPlan);
        ExcelUtil<DeviceGroupPlan> util = new ExcelUtil<DeviceGroupPlan>(DeviceGroupPlan.class);
        util.exportExcel(response, list, "班组计划保养数据");
    }

    /**
     * 获取班组计划保养详细信息
     */
    @PreAuthorize("@ss.hasPermi('device:group:query')")
    @GetMapping(value = "/{groupId}")
    public AjaxResult getInfo(@PathVariable("groupId") String groupId)
    {
        return success(deviceGroupPlanService.selectDeviceGroupPlanByGroupId(groupId));
    }

    /**
     * 新增班组计划保养
     */
    @PreAuthorize("@ss.hasPermi('device:group:add')")
    @Log(title = "班组计划保养", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceGroupPlan deviceGroupPlan)
    {
        return toAjax(deviceGroupPlanService.insertDeviceGroupPlan(deviceGroupPlan));
    }

    /**
     * 修改班组计划保养
     */
    @PreAuthorize("@ss.hasPermi('device:group:edit')")
    @Log(title = "班组计划保养", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceGroupPlan deviceGroupPlan)
    {
        return toAjax(deviceGroupPlanService.updateDeviceGroupPlan(deviceGroupPlan));
    }

    /**
     * 删除班组计划保养
     */
    @PreAuthorize("@ss.hasPermi('device:group:remove')")
    @Log(title = "班组计划保养", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupIds}")
    public AjaxResult remove(@PathVariable String[] groupIds)
    {
        return toAjax(deviceGroupPlanService.deleteDeviceGroupPlanByGroupIds(groupIds));
    }
}
