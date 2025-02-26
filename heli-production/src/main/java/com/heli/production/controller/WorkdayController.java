package com.heli.production.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heli.production.domain.entity.WorkdayEntity;
import com.heli.production.service.IWorkdayService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/production/workday")
public class WorkdayController extends BaseController {

    @Autowired
    private IWorkdayService workdayService;


    /**
     * @description: 获取工作日列表，包含date前50天和后50天的日期，用于前端展示工作日列表
     * @author: hong
     * @date: 2025/1/20 10:31
     * @version: 1.0
     */
    @GetMapping("/limitList")
    public TableDataInfo getLimitWorkdayList(Date date) {
        log.info("date: {}", DateUtils.formatOutput(date));
        // 获取date前50天的日期
        Date dateBefore = DateUtils.getDateBefore(date, 50);
        log.info("dateBefore: {}", DateUtils.formatOutput(dateBefore));
        // 获取date后50天的日期
        Date dateAfter = DateUtils.getDateAfter(date, 50);
        log.info("dateAfter: {}", DateUtils.formatOutput(dateAfter));

        List<WorkdayEntity> list = workdayService.list(
                new LambdaQueryWrapper<WorkdayEntity>()
                        .between(WorkdayEntity::getDate, dateBefore, dateAfter)
        );
        log.info("list: {}", list);
        return getDataTable(list);
    }

    /**
     * 查询工作日列表
     */
    @GetMapping("/list")
    public TableDataInfo list() {
        List<WorkdayEntity> list = workdayService.list();
        log.info("list: {}", list);
        return getDataTable(list);
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
