package com.heli.device.maintenanceTable.controller;

import java.io.InputStream;
import java.util.List;

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
import com.heli.device.maintenanceTable.domain.DeviceMajorPlan;
import com.heli.device.maintenanceTable.service.IDeviceMajorPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 专业计划保养Controller
 * 
 * @author YYY
 * @date 2025-02-18
 */
@RestController
@RequestMapping("/fault/majorPlan")
@Slf4j
public class DeviceMajorPlanController extends BaseController
{
    @Autowired
    private IDeviceMajorPlanService deviceMajorPlanService;

    /**
     * 查询专业计划保养列表
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceMajorPlan deviceMajorPlan)
    {
        startPage();
        List<DeviceMajorPlan> list = deviceMajorPlanService.selectDeviceMajorPlanList(deviceMajorPlan);
        return getDataTable(list);
    }

    /**
     * 导出专业计划保养列表
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:plan:export')")
    @Log(title = "专业计划保养", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceMajorPlan deviceMajorPlan)
    {
        List<DeviceMajorPlan> list = deviceMajorPlanService.selectDeviceMajorPlanList(deviceMajorPlan);
        ExcelUtil<DeviceMajorPlan> util = new ExcelUtil<DeviceMajorPlan>(DeviceMajorPlan.class);
        util.exportExcel(response, list, "专业计划保养数据");
    }

    /**
     * 获取专业计划保养详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:plan:query')")
    @GetMapping(value = "/{majorId}")
    public AjaxResult getInfo(@PathVariable("majorId") String majorId)
    {
        return success(deviceMajorPlanService.selectDeviceMajorPlanByMajorId(majorId));
    }

    /**
     * 新增专业计划保养
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:plan:add')")
    @Log(title = "专业计划保养", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceMajorPlan deviceMajorPlan)
    {
        return toAjax(deviceMajorPlanService.insertDeviceMajorPlan(deviceMajorPlan));
    }

    /**
     * 修改专业计划保养
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:plan:edit')")
    @Log(title = "专业计划保养", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceMajorPlan deviceMajorPlan)
    {
        return toAjax(deviceMajorPlanService.updateDeviceMajorPlan(deviceMajorPlan));
    }

    /**
     * 删除专业计划保养
     */
    @PreAuthorize("@ss.hasPermi('maintenanceTable:plan:remove')")
    @Log(title = "专业计划保养", businessType = BusinessType.DELETE)
	@DeleteMapping("/{majorIds}")
    public AjaxResult remove(@PathVariable String[] majorIds)
    {
        return toAjax(deviceMajorPlanService.deleteDeviceMajorPlanByMajorIds(majorIds));
    }

    @Log(title = "年度专业计划表上传", businessType = BusinessType.INSERT)
//    @PreAuthorize("@ss.hasPermi('financial:interests:import')")
    @PostMapping("/import")
    @Transactional
    public AjaxResult majorPlanList(String yearAndMonth, MultipartFile excelFile) {
        log.info("excelFile"+"="+excelFile);
        log.info("date"+"="+yearAndMonth);
//        if (financialInterestsTableService.checkInterestsDataIsExisted(yearAndMonth)) {
//            return AjaxResult.error("当月利润表已上传");
//        }
        int status = 0;

        try (InputStream inputStream = excelFile.getInputStream()) {
            //清空数据库
            log.info("开始读取 " + excelFile.getName() + " 文件");
            deviceMajorPlanService.readPlanMajorToDB(excelFile.getOriginalFilename(), inputStream);
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
