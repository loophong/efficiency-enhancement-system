package com.heli.production.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heli.production.domain.entity.CycleEntity;
import com.ruoyi.common.core.domain.R;
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
import com.heli.production.service.ICycleService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 产能Controller
 *
 * @author hong
 * @date 2025-01-17
 */
@Slf4j
@RestController
@RequestMapping("/production/cycle")
public class CycleController extends BaseController {
    @Autowired
    private ICycleService productionCycleService;

    @Log(title = "[生产周期]上传", businessType = BusinessType.INSERT)
    @PostMapping("/import")
    @Transactional
    public AjaxResult importTable(MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            return success(productionCycleService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile));
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }


    /**
     * 查询产能列表
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:list')")
    @GetMapping("/list")
    public TableDataInfo list(CycleEntity productionCycle) {
        startPage();
        List<CycleEntity> list = productionCycleService.selectProductionCycleList(productionCycle);
        return getDataTable(list);
    }

    /**
     * 导出产能列表
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:export')")
    @Log(title = "产能", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CycleEntity productionCycle) {
        List<CycleEntity> list = productionCycleService.selectProductionCycleList(productionCycle);
        ExcelUtil<CycleEntity> util = new ExcelUtil<CycleEntity>(CycleEntity.class);
        util.exportExcel(response, list, "产能数据");
    }

    /**
     * 获取产能详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(productionCycleService.selectProductionCycleById(id));
    }

    /**
     * 新增产能
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:add')")
    @Log(title = "产能", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CycleEntity productionCycle) {
        return toAjax(productionCycleService.insertProductionCycle(productionCycle));
    }

    /**
     * 修改产能
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:edit')")
    @Log(title = "产能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CycleEntity productionCycle) {
        return toAjax(productionCycleService.updateProductionCycle(productionCycle));
    }

    /**
     * 删除产能
     */
    @PreAuthorize("@ss.hasPermi('production:cycle:remove')")
    @Log(title = "产能", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(productionCycleService.deleteProductionCycleByIds(ids));
    }



    /**
     * @description: 获取车型列表
     * @author: hong
     * @date: 2025/2/20 17:01
     * @version: 1.0
     */
    @GetMapping("/vehicleModel")
    public AjaxResult getVehicleModel() {
        LambdaQueryWrapper<CycleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(CycleEntity::getVehicleModel);
        List<CycleEntity> list = productionCycleService.list(queryWrapper);
        return AjaxResult.success(list);
    }
}
