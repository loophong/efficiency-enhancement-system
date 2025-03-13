package com.heli.device.maintenanceTable.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.heli.device.maintenanceTable.domain.DeviceMajorPlan;
import com.heli.device.maintenanceTable.mapper.DeviceGroupPlanMapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
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
import com.heli.device.maintenanceTable.domain.DeviceGroupPlan;
import com.heli.device.maintenanceTable.service.IDeviceGroupPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 班组计划保养Controller
 * 
 * @author YYY
 * @date 2025-01-19
 */
@RestController
@RequestMapping("/fault/groupPlan")
@Slf4j
public class DeviceGroupPlanController extends BaseController
{
    @Autowired
    private IDeviceGroupPlanService deviceGroupPlanService;
    @Autowired
    private DeviceGroupPlanMapper deviceGroupPlanMapper;

    /**
     * 查询专业计划消息列表
     */
//    @PreAuthorize("@ss.hasPermi('maintenanceTable:plan:list')")
    @GetMapping("/tipList")
    public TableDataInfo tipList()
    {
        List<String> fields = new ArrayList<>();
        String[] weeks = {"one", "two", "three", "four"};

        for (String week : weeks) {
            fields.add("month_"  + week);
        }


        List<DeviceGroupPlan> list = deviceGroupPlanMapper.selectRecordsByAuditStatus(fields);
        return getDataTable(list);
    }

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



    @Log(title = "班组计划表上传", businessType = BusinessType.INSERT)
//    @PreAuthorize("@ss.hasPermi('financial:interests:import')")
    @PostMapping("/import")
    @Transactional
    public AjaxResult groupPlanList(String yearAndMonth, MultipartFile excelFile) {
        log.info("excelFile"+"="+excelFile);
        log.info("date"+"="+yearAndMonth);
//        if (financialInterestsTableService.checkInterestsDataIsExisted(yearAndMonth)) {
//            return AjaxResult.error("当月利润表已上传");
//        }
        int status = 0;

        try (InputStream inputStream = excelFile.getInputStream()) {
            //清空数据库
            log.info("开始读取 " + excelFile.getName() + " 文件");
            deviceGroupPlanService.readPlanGroupToDB(excelFile.getOriginalFilename(), inputStream);
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
