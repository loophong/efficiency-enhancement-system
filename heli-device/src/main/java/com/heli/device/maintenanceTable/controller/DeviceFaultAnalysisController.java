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
import com.heli.device.maintenanceTable.domain.DeviceFaultAnalysis;
import com.heli.device.maintenanceTable.service.IDeviceFaultAnalysisService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备故障分析Controller
 * 
 * @author YYY
 * @date 2025-01-17
 */
@RestController
@RequestMapping("/fault/analysis")
public class DeviceFaultAnalysisController extends BaseController
{
    @Autowired
    private IDeviceFaultAnalysisService deviceFaultAnalysisService;

    /**
     * 查询设备故障分析列表
     */
    @PreAuthorize("@ss.hasPermi('fault:analysis:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceFaultAnalysis deviceFaultAnalysis)
    {
        startPage();
        List<DeviceFaultAnalysis> list = deviceFaultAnalysisService.selectDeviceFaultAnalysisList(deviceFaultAnalysis);
        return getDataTable(list);
    }

    /**
     * 导出设备故障分析列表
     */
    @PreAuthorize("@ss.hasPermi('fault:analysis:export')")
    @Log(title = "设备故障分析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceFaultAnalysis deviceFaultAnalysis)
    {
        List<DeviceFaultAnalysis> list = deviceFaultAnalysisService.selectDeviceFaultAnalysisList(deviceFaultAnalysis);
        ExcelUtil<DeviceFaultAnalysis> util = new ExcelUtil<DeviceFaultAnalysis>(DeviceFaultAnalysis.class);
        util.exportExcel(response, list, "设备故障分析数据");
    }

    /**
     * 获取设备故障分析详细信息
     */
    @PreAuthorize("@ss.hasPermi('fault:analysis:query')")
    @GetMapping(value = "/{analysisId}")
    public AjaxResult getInfo(@PathVariable("analysisId") String analysisId)
    {
        return success(deviceFaultAnalysisService.selectDeviceFaultAnalysisByAnalysisId(analysisId));
    }

    /**
     * 新增设备故障分析
     */
    @PreAuthorize("@ss.hasPermi('fault:analysis:add')")
    @Log(title = "设备故障分析", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceFaultAnalysis deviceFaultAnalysis)
    {
        return toAjax(deviceFaultAnalysisService.insertDeviceFaultAnalysis(deviceFaultAnalysis));
    }

    /**
     * 修改设备故障分析
     */
    @PreAuthorize("@ss.hasPermi('fault:analysis:edit')")
    @Log(title = "设备故障分析", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceFaultAnalysis deviceFaultAnalysis)
    {
        return toAjax(deviceFaultAnalysisService.updateDeviceFaultAnalysis(deviceFaultAnalysis));
    }

    /**
     * 删除设备故障分析
     */
    @PreAuthorize("@ss.hasPermi('fault:analysis:remove')")
    @Log(title = "设备故障分析", businessType = BusinessType.DELETE)
	@DeleteMapping("/{analysisIds}")
    public AjaxResult remove(@PathVariable String[] analysisIds)
    {
        return toAjax(deviceFaultAnalysisService.deleteDeviceFaultAnalysisByAnalysisIds(analysisIds));
    }
}
