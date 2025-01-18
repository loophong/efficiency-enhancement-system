package com.heli.production.controller;

import com.heli.production.domain.entity.CapacityEntity;
import com.heli.production.service.ICapacityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
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
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.List;

/**
 * 产能Controller
 *
 * @author hong
 * @date 2025-01-09
 */
@RestController
@RequestMapping("/production/capacity")
public class CapacityController extends BaseController {
    @Autowired
    private ICapacityService capacityService;

    /**
     * @description: 排产时，获取全部产能列表
     * @author: hong
     * @date: 2025/1/18 14:51
     * @version: 1.0
     */
    @GetMapping("/all")
    public AjaxResult getAllCapacity() {
        List<CapacityEntity> list = capacityService.list();
        return AjaxResult.success(list);
    }

    /**
     * 查询产能列表
     */
    @PreAuthorize("@ss.hasPermi('production:capacity:list')")
    @GetMapping("/list")
    public TableDataInfo list(CapacityEntity capacityEntity) {
        //test
        startPage();
        List<CapacityEntity> list = capacityService.selectCapacityList(capacityEntity);
        return getDataTable(list);
    }

    /**
     * 导出产能列表
     */
    @PreAuthorize("@ss.hasPermi('production:capacity:export')")
    @Log(title = "产能", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CapacityEntity capacityEntity) {
        List<CapacityEntity> list = capacityService.selectCapacityList(capacityEntity);
        ExcelUtil<CapacityEntity> util = new ExcelUtil<CapacityEntity>(CapacityEntity.class);
        util.exportExcel(response, list, "产能数据");
    }

    /**
     * 获取产能详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:capacity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(capacityService.selectCapacityById(id));
    }

    /**
     * 新增产能
     */
    @PreAuthorize("@ss.hasPermi('production:capacity:add')")
    @Log(title = "产能", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CapacityEntity capacityEntity) {
        return toAjax(capacityService.insertCapacity(capacityEntity));
    }

    /**
     * 修改产能
     */
    @PreAuthorize("@ss.hasPermi('production:capacity:edit')")
    @Log(title = "产能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CapacityEntity capacityEntity) {
        return toAjax(capacityService.updateCapacity(capacityEntity));
    }

    /**
     * 删除产能
     */
    @PreAuthorize("@ss.hasPermi('production:capacity:remove')")
    @Log(title = "产能", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(capacityService.deleteCapacityByIds(ids));
    }
}
