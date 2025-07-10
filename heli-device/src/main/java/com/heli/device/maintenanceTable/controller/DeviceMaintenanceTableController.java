package com.heli.device.maintenanceTable.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.heli.device.maintenanceTable.mapper.DeviceMaintenanceTableMapper;
import com.ruoyi.common.exception.ServiceException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;
import com.heli.device.maintenanceTable.service.IDeviceMaintenanceTableService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;


/**
 * 2.设备故障记录(导入)Controller
 *
 * @author ruoyi
 * @date 2025-01-09
 */
@Slf4j
@RestController
@RequestMapping("/fault/maintenance")
public class DeviceMaintenanceTableController extends BaseController
{
    @Autowired
    private IDeviceMaintenanceTableService deviceMaintenanceTableService;
    @Autowired
    private DeviceMaintenanceTableMapper deviceMaintenanceTableMapper;



    /**
     * 查询设备故障记录列表
     */
//    @PreAuthorize("@ss.hasPermi('fault:maintenance:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceMaintenanceTable deviceMaintenanceTable)
    {
        startPage();
        List<DeviceMaintenanceTable> list = deviceMaintenanceTableService.selectDeviceMaintenanceTableList(deviceMaintenanceTable);
        return getDataTable(list);
    }

    /**
     * 查询设备故障记录列表
     */
//    @PreAuthorize("@ss.hasPermi('fault:maintenance:list')")
    @GetMapping("/listByDate")
    public TableDataInfo listByDate(@DateTimeFormat(pattern = "yyyy-MM")Date yearAndMonth,String faultType)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String formattedDate = sdf.format(yearAndMonth);
//        startPage();
        List<DeviceMaintenanceTable> list = deviceMaintenanceTableMapper.selectListByDate(formattedDate,faultType);
        return getDataTable(list);
    }

    /**
     * 查询设备故障记录列表故障树
     */
//    @PreAuthorize("@ss.hasPermi('fault:maintenance:list')")
    @GetMapping("/treeList")
    public TableDataInfo treeList(DeviceMaintenanceTable deviceMaintenanceTable)
    {
//        startPage();
        List<Map<String, Object>> list = deviceMaintenanceTableService.handleTreeList(deviceMaintenanceTable);
        return getDataTable(list);
    }

    /**
     * 查询设备故障记录列表故障树
     */
//    @PreAuthorize("@ss.hasPermi('fault:maintenance:list')")
    @GetMapping("/numberList")
    public TableDataInfo uniqueNumberList()
    {
//        startPage();
        List<String> list = deviceMaintenanceTableMapper.selectUniqueMaintenanceTableIds();
        return getDataTable(list);
    }

    /**
     * 导出设备故障记录列表
     */
//    @PreAuthorize("@ss.hasPermi('fault:maintenance:export')")
    @Log(title = "设备故障记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceMaintenanceTable deviceMaintenanceTable)
    {
        List<DeviceMaintenanceTable> list = deviceMaintenanceTableService.selectDeviceMaintenanceTableList(deviceMaintenanceTable);
        ExcelUtil<DeviceMaintenanceTable> util = new ExcelUtil<DeviceMaintenanceTable>(DeviceMaintenanceTable.class);
        util.exportExcel(response, list, "设备故障记录数据");
    }

    /**
     * 获取设备故障记录详细信息
     */
//    @PreAuthorize("@ss.hasPermi('fault:maintenance:query')")
    @GetMapping(value = "/{maintenanceTableId}")
    public AjaxResult getInfo(@PathVariable("maintenanceTableId") Long maintenanceTableId)
    {
        return success(deviceMaintenanceTableService.selectDeviceMaintenanceTableByMaintenanceTableId(maintenanceTableId));
    }

    /**
     * 新增设备故障记录
     */
//    @PreAuthorize("@ss.hasPermi('fault:maintenance:add')")
    @Log(title = "2.设备故障记录(导入)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceMaintenanceTable deviceMaintenanceTable)
    {
        return toAjax(deviceMaintenanceTableService.insertDeviceMaintenanceTable(deviceMaintenanceTable));
    }

    /**
     * 修改设备故障记录(导入)
     */
//    @PreAuthorize("@ss.hasPermi('fault:maintenance:edit')")
    @Log(title = "2.设备故障记录(导入)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceMaintenanceTable deviceMaintenanceTable)
    {
        return toAjax(deviceMaintenanceTableService.updateDeviceMaintenanceTable(deviceMaintenanceTable));
    }

    /**
     * 删除设备故障记录(导入)
     */
//    @PreAuthorize("@ss.hasPermi('fault:maintenance:remove')")
    @Log(title = "设备故障记录(导入)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{maintenanceTableIds}")
    public AjaxResult remove(@PathVariable Long[] maintenanceTableIds)
    {
        return toAjax(deviceMaintenanceTableService.deleteDeviceMaintenanceTableByMaintenanceTableIds(maintenanceTableIds));
    }


    @Log(title = "故障记录表上传", businessType = BusinessType.INSERT)
//    @PreAuthorize("@ss.hasPermi('financial:interests:import')")
    @PostMapping("/import")
    @Transactional
    public AjaxResult faultRecordsList(String yearAndMonth, MultipartFile excelFile) {
        log.info("excelFile"+"="+excelFile);
        log.info("date"+"="+yearAndMonth);
//        if (financialInterestsTableService.checkInterestsDataIsExisted(yearAndMonth)) {
//            return AjaxResult.error("当月利润表已上传");
//        }
        int status = 0;

        try (InputStream inputStream = excelFile.getInputStream()) {
            //清空数据库
            log.info("开始读取 " + excelFile.getName() + " 文件");
            deviceMaintenanceTableService.readFaultRecordsToDB(excelFile.getOriginalFilename(), inputStream);
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
