package com.heli.device.maintenanceTable.controller;

import java.io.InputStream;
import java.util.List;

import com.heli.device.maintenanceTable.mapper.DeviceIndicatorCountMapper;
import com.heli.device.maintenanceTable.mapper.DeviceMaintenanceTableMapper;
import com.ruoyi.common.exception.ServiceException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备指标分析Controller
 * 
 * @author YYY
 * @date 2025-01-18
 */
@RestController
@Slf4j
@RequestMapping("/fault/indicator")
public class DeviceIndicatorCountController extends BaseController
{
    @Autowired
    private IDeviceIndicatorCountService deviceIndicatorCountService;

    @Autowired
    private DeviceIndicatorCountMapper deviceIndicatorCountMapper;

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
     * 查询 indicator_name 的去重值
     */
//    @PreAuthorize("@ss.hasPermi('fault:indicator:list')")
    @GetMapping("/uniqueName")
    public TableDataInfo uniqueNameList()
    {
        List<String> list = deviceIndicatorCountMapper.selectDistinctIndicatorName();
        return getDataTable(list);
    }

    /**
     * 查询 indicator_time 的去重值
     */
//    @PreAuthorize("@ss.hasPermi('fault:indicator:list')")
    @GetMapping("/uniqueTime")
    public TableDataInfo uniqueTimeList()
    {
        List<String> list = deviceIndicatorCountMapper.selectDistinctIndicatorTime();
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

    @Log(title = "指标表上传", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('fault:indicator:import')")
    @PostMapping("/import")
    @Transactional
    public AjaxResult indicatorList(String year, MultipartFile excelFile) {
        log.info("excelFile"+"="+excelFile);
        log.info("日期="+year);
//        if (financialInterestsTableService.checkInterestsDataIsExisted(yearAndMonth)) {
//            return AjaxResult.error("当月利润表已上传");
//        }
        int status = 0;

        try (InputStream inputStream = excelFile.getInputStream()) {
            //清空数据库
            log.info("开始读取 " + excelFile.getName() + " 文件");
            deviceIndicatorCountService.readIndicatorToDB(excelFile.getOriginalFilename(), inputStream,year);
//            financialTempTableService.tempTableToInterestsTable(yearAndMonth);
            status = 1;
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        } finally {
            log.info("清空数据库");
//            financialTempTableService.clearTempTable();
        }
        return toAjax(status);
    }
}
