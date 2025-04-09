package com.heli.production.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heli.production.domain.entity.DailyPlanEntity;
import com.heli.production.domain.entity.SysNotificationsEntity;
import com.heli.production.domain.vo.CoSignInfoVO;
import com.heli.production.service.IDailyPlanService;
import com.heli.production.service.ISysNotificationsService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
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
import com.heli.production.domain.entity.PlanCoSignEntity;
import com.heli.production.service.IPlanCoSignService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 计划会签Controller
 *
 * @author ruoyi
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/production/planCoSign")
public class PlanCoSignController extends BaseController {
    @Autowired
    private IPlanCoSignService planCoSignService;
    @Autowired
    private IDailyPlanService dailyPlanService;
    @Autowired
    private ISysNotificationsService sysNotificationsService;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询计划会签列表
     */
    @PreAuthorize("@ss.hasPermi('production:planCoSign:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlanCoSignEntity planCoSign) {
        startPage();
        List<PlanCoSignEntity> list = planCoSignService.selectPlanCoSignList(planCoSign);
        return getDataTable(list);
    }

    /**
     * 导出计划会签列表
     */
    @PreAuthorize("@ss.hasPermi('production:planCoSign:export')")
    @Log(title = "计划会签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlanCoSignEntity planCoSign) {
        List<PlanCoSignEntity> list = planCoSignService.selectPlanCoSignList(planCoSign);
        ExcelUtil<PlanCoSignEntity> util = new ExcelUtil<PlanCoSignEntity>(PlanCoSignEntity.class);
        util.exportExcel(response, list, "计划会签数据");
    }

    /**
     * 获取计划会签详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:planCoSign:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(planCoSignService.selectPlanCoSignById(id));
    }

    /**
     * 获取计划会签详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:planCoSign:query')")
    @GetMapping(value = "/info/{id}")
    public AjaxResult getDetailInfo(@PathVariable("id") String id) {
        CoSignInfoVO vo = new CoSignInfoVO();
        PlanCoSignEntity planCoSign = planCoSignService.selectPlanCoSignById(id);
        vo.setPlanCoSign(planCoSign);
        List<DailyPlanEntity> list = dailyPlanService.list(new LambdaQueryWrapper<DailyPlanEntity>().eq(DailyPlanEntity::getOnlineDate, planCoSign.getOnlineDate()));
        vo.setDailyPlanList(list);
        return success(vo);
    }

    /**
     * 新增计划会签
     */
    @PreAuthorize("@ss.hasPermi('production:planCoSign:add')")
    @Log(title = "计划会签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlanCoSignEntity planCoSign) {
        // 检查当日是否已经会签
        long count = planCoSignService.count(new LambdaQueryWrapper<PlanCoSignEntity>().eq(PlanCoSignEntity::getOnlineDate, planCoSign.getOnlineDate()));
        if(count > 0){
            return AjaxResult.error("当日已经会签，请勿重复操作");
        }
        // 检查当日是否有排产计划
        long size = dailyPlanService.count(new LambdaQueryWrapper<DailyPlanEntity>().eq(DailyPlanEntity::getOnlineDate, planCoSign.getOnlineDate()));
        if(size == 0){
            return AjaxResult.error("当日没有排产计划，请先创建排产计划");
        }

        SysUser sysUser = sysUserService.selectUserById(getUserId());
        planCoSign.setReviewerName(sysUser.getNickName());
        planCoSignService.insertPlanCoSign(planCoSign);

        //创建消息实体
        SysNotificationsEntity sysNotificationsEntity = new SysNotificationsEntity();
        sysNotificationsEntity.setType("production_plan_co_sign");
        sysNotificationsEntity.setReceiverId(planCoSign.getReviewerId());
        sysNotificationsEntity.setSenderId(getUserId());
        sysNotificationsEntity.setTitle("计划会签");
        sysNotificationsEntity.setContent("您有一条新的计划会签待审核，请及时处理");
        sysNotificationsService.createNotifications(sysNotificationsEntity);
        return success();
    }

    /**
     * 修改计划会签
     */
    @PreAuthorize("@ss.hasPermi('production:planCoSign:edit')")
    @Log(title = "计划会签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlanCoSignEntity planCoSign) {
        return toAjax(planCoSignService.updatePlanCoSign(planCoSign));
    }

    /**
     * 删除计划会签
     */
    @PreAuthorize("@ss.hasPermi('production:planCoSign:remove')")
    @Log(title = "计划会签", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(planCoSignService.deletePlanCoSignByIds(ids));
    }
}
