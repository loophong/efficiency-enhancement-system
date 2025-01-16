package com.heli.production.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heli.production.domain.entity.WorkdayEntity;
import com.heli.production.service.IWorkdayService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/production/workday")
public class WorkdayController extends BaseController {

    @Autowired
    private IWorkdayService workdayService;

    /**
     * 查询工作日列表
     */
    @GetMapping("/list")
    public TableDataInfo list() {
        List<WorkdayEntity> list = workdayService.list(new LambdaQueryWrapper<WorkdayEntity>().eq(WorkdayEntity::getStatus, 0));
        log.info("list: {}", list);
        return getDataTable(list);
    }


    /**
     * 获取工作日详细信息
     */
    @GetMapping(value = "/{date}")
    public AjaxResult getInfo(@PathVariable("date") Date date) {
        return success(workdayService.selectWorkdayByDate(date));
    }

    /**
     * 新增/修改工作日
     */
    @Log(title = "工作日", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkdayEntity workday) {
        return toAjax(workdayService.saveOrUpdate(workday));
    }




}
