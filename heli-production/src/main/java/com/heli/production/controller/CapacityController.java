package com.heli.production.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heli.production.domain.entity.CapacityEntity;
import com.heli.production.domain.entity.DailyUsedCapacityEntity;
import com.heli.production.domain.vo.CapacityVO;
import com.heli.production.service.ICapacityService;
import com.heli.production.service.IDailyUsedCapacityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Date;
import java.util.List;

/**
 * 产能Controller
 *
 * @author hong
 * @date 2025-01-09
 */
@Slf4j
@RestController
@RequestMapping("/production/capacity")
public class CapacityController extends BaseController {
    @Autowired
    private ICapacityService capacityService;
    @Autowired
    private IDailyUsedCapacityService dailyUsedCapacityService;

    /**
     * @description: 排产时，获取产能列表  和 已使用的产能
     * @author: hong
     * @date: 2025/1/18 14:51
     * @version: 1.0
     */
    @GetMapping("/all")
    public AjaxResult getAllCapacity(@RequestParam(required = false) Date date) {
        log.info("date: {}", date);
        CapacityVO capacityVO = new CapacityVO();

        // 查询产能列表
        List<CapacityEntity> list = capacityService.list(new LambdaQueryWrapper<CapacityEntity>());
        capacityVO.setCapacityEntities(list);
        // 查询使用的产能列表
        if(date != null){
            List<DailyUsedCapacityEntity> dailyUsedCapacityList = dailyUsedCapacityService.list(new LambdaQueryWrapper<DailyUsedCapacityEntity>()
                    .eq(DailyUsedCapacityEntity::getProductionDate, date));
            capacityVO.setDailyUsedCapacityEntities(dailyUsedCapacityList);
        }
        return AjaxResult.success(capacityVO);
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
        return toAjax(capacityService.saveOrUpdate(capacityEntity));
    }

    /**
     * 修改产能
     */
    @PreAuthorize("@ss.hasPermi('production:capacity:edit')")
    @Log(title = "产能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CapacityEntity capacityEntity) {
        return toAjax(capacityService.saveOrUpdate(capacityEntity));
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
