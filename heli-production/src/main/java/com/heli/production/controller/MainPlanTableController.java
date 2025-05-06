package com.heli.production.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.heli.production.domain.entity.MainPlanTableEntity;
import com.heli.production.domain.entity.OrderSchedulingEntity;
import com.heli.production.service.IOrderSchedulingService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.poi.ExcelUtil;
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
import com.heli.production.service.IMainPlanTableService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 主计划表Controller
 *
 * @author hong
 * @date 2025-01-09
 */
@Slf4j
@RestController
@RequestMapping("/production/mainPlanTable")
public class MainPlanTableController extends BaseController {
    @Autowired
    private IMainPlanTableService mainPlanTableService;
    @Autowired
    private IOrderSchedulingService orderSchedulingService;

    @Log(title = "[主计划表]上传", businessType = BusinessType.INSERT)
//    @PreAuthorize("@ss.hasPermi('production:vehicle:import')")
    @PostMapping("/import")
    @Transactional
    public AjaxResult importTable(Date date, MultipartFile excelFile) {
        log.info("传入的date为 " + date);
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            mainPlanTableService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile, date);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
        List<OrderSchedulingEntity> orderSchedulingEntities = orderSchedulingService.updateOrderData(date);
        List<String> list = new ArrayList<>();
        for (OrderSchedulingEntity entity : orderSchedulingEntities) {
            //先去重
            if (list.contains(entity.getVehicleModel())) {
                continue;
            }
            if(entity.getVehicleModel() == null){
                list.add("订单："+entity.getOrderNumber()+"车型为空");
            }else {
                list.add(entity.getVehicleModel());
            }
            log.info("车型不存在的" + String.valueOf(entity));

        }
        return AjaxResult.success(list);
    }

    /**
     * 查询主计划表列表
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:list')")
    @GetMapping("/list")
    public TableDataInfo list(MainPlanTableEntity mainPlanTable) {
        startPage();
        List<MainPlanTableEntity> list = mainPlanTableService.selectMainPlanTableList(mainPlanTable);
        return getDataTable(list);
    }

    /**
     * 导出主计划表列表
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:export')")
    @Log(title = "主计划表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MainPlanTableEntity mainPlanTable) {
        List<MainPlanTableEntity> list = mainPlanTableService.selectMainPlanTableList(mainPlanTable);
        ExcelUtil<MainPlanTableEntity> util = new ExcelUtil<MainPlanTableEntity>(MainPlanTableEntity.class);
        util.exportExcel(response, list, "主计划表数据");
    }

    /**
     * 获取主计划表详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(mainPlanTableService.selectMainPlanTableById(id));
    }

    /**
     * 新增主计划表
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:add')")
    @Log(title = "主计划表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MainPlanTableEntity mainPlanTable) {
        return toAjax(mainPlanTableService.insertMainPlanTable(mainPlanTable));
    }

    /**
     * 修改主计划表
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:edit')")
    @Log(title = "主计划表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MainPlanTableEntity mainPlanTable) {
        return toAjax(mainPlanTableService.updateMainPlanTable(mainPlanTable));
    }

    /**
     * 删除主计划表
     */
    @PreAuthorize("@ss.hasPermi('production:mainPlanTable:remove')")
    @Log(title = "主计划表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(mainPlanTableService.deleteMainPlanTableByIds(ids));
    }
}
