package com.heli.production.controller;

import com.heli.production.domain.entity.VehicleTypeEntity;
import com.heli.production.service.IVehicleTypeService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/production/vehicle")
public class VehicleTypeController extends BaseController {
    @Autowired
    private IVehicleTypeService vehicleTypeService;

    /**
     * @description:
     * @param: null
     * @return:
     * @author: hong
     * @date: 2025/1/8 14:59
     */
    @Log(title = "[车型表]上传", businessType = BusinessType.INSERT)
//    @PreAuthorize("@ss.hasPermi('production:vehicle:import')")
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {

            vehicleTypeService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile);

        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }


    /**
     * 查询车型列表
     */
    @PreAuthorize("@ss.hasPermi('production:vehicle:list')")
    @GetMapping("/list")
    public TableDataInfo list(VehicleTypeEntity vehicleType) {
        startPage();
        List<VehicleTypeEntity> list = vehicleTypeService.selectVehicleTypeList(vehicleType);
        return getDataTable(list);
    }

    /**
     * 导出车型列表
     */
    @PreAuthorize("@ss.hasPermi('production:vehicle:export')")
    @Log(title = "车型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehicleTypeEntity vehicleType) {
        List<VehicleTypeEntity> list = vehicleTypeService.selectVehicleTypeList(vehicleType);
        ExcelUtil<VehicleTypeEntity> util = new ExcelUtil<VehicleTypeEntity>(VehicleTypeEntity.class);
        util.exportExcel(response, list, "车型数据");
    }

    /**
     * 获取车型详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:vehicle:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(vehicleTypeService.selectVehicleTypeById(id));
    }

    /**
     * 新增车型
     */
    @PreAuthorize("@ss.hasPermi('production:vehicle:add')")
    @Log(title = "车型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VehicleTypeEntity vehicleType) {
        log.info("vehicleType:{}",vehicleType);
        return toAjax(vehicleTypeService.insertVehicleType(vehicleType));
    }

    /**
     * 修改车型
     */
    @PreAuthorize("@ss.hasPermi('production:vehicle:edit')")
    @Log(title = "车型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VehicleTypeEntity vehicleType) {
        return toAjax(vehicleTypeService.updateVehicleType(vehicleType));
    }

    /**
     * 删除车型
     */
    @PreAuthorize("@ss.hasPermi('production:vehicle:remove')")
    @Log(title = "车型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(vehicleTypeService.deleteVehicleTypeByIds(ids));
    }
}
