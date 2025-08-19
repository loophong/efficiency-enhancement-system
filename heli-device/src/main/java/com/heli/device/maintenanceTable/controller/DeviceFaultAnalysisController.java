package com.heli.device.maintenanceTable.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.heli.device.maintenanceTable.mapper.DeviceFaultAnalysisMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
@Slf4j
public class DeviceFaultAnalysisController extends BaseController
{
    @Autowired
    private IDeviceFaultAnalysisService deviceFaultAnalysisService;
    @Autowired
    private DeviceFaultAnalysisMapper deviceFaultAnalysisMapper;

    @PreAuthorize("@ss.hasPermi('fault:analysis:list')")
    @GetMapping("/listByNameAndDate")
    public TableDataInfo listByNameAndDate(@RequestParam(required = false) String analysisName,
                                           @RequestParam(required = false) String analysisRecordTime)
    {
        log.info("analysisRecordTimeStr---->"+analysisRecordTime);
        DeviceFaultAnalysis query = new DeviceFaultAnalysis();
        if (analysisRecordTime != null && !analysisRecordTime.isEmpty()) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
                Date tmp = formatter.parse(analysisRecordTime);
                query.setAnalysisRecordTime(tmp);
            } catch (Exception e) {
                log.error("Failed to parse date: {}", analysisRecordTime, e);
            }
        }
        query.setAnalysisName(analysisName);
        log.info("测试cesium"+query);

        List<DeviceFaultAnalysis> result = deviceFaultAnalysisMapper.selectByNameAndDate(query);
        return getDataTable(result);
    }


    /**   selectByNameAndDate
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
