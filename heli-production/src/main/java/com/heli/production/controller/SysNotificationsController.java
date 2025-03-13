package com.heli.production.controller;

import java.util.List;

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
import com.heli.production.domain.entity.SysNotificationsEntity;
import com.heli.production.service.ISysNotificationsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 消息通知Controller
 *
 * @author hong
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/production/notifications")
public class SysNotificationsController extends BaseController {
    @Autowired
    private ISysNotificationsService sysNotificationsService;

    /**
     * 查询消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('production:notifications:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNotificationsEntity sysNotifications) {
        startPage();
        List<SysNotificationsEntity> list = sysNotificationsService.selectSysNotificationsList(sysNotifications);
        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('production:notifications:mine')")
    @GetMapping("/mine")
    public TableDataInfo mine(SysNotificationsEntity sysNotifications) {
        startPage();
        sysNotifications.setReceiverId(getUserId());
        List<SysNotificationsEntity> list = sysNotificationsService.selectSysNotificationsList(sysNotifications);
        return getDataTable(list);
    }

    /**
     * 导出消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('production:notifications:export')")
    @Log(title = "消息通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysNotificationsEntity sysNotifications) {
        List<SysNotificationsEntity> list = sysNotificationsService.selectSysNotificationsList(sysNotifications);
        ExcelUtil<SysNotificationsEntity> util = new ExcelUtil<SysNotificationsEntity>(SysNotificationsEntity.class);
        util.exportExcel(response, list, "消息通知数据");
    }

    /**
     * 获取消息通知详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:notifications:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(sysNotificationsService.selectSysNotificationsById(id));
    }

    /**
     * 新增消息通知
     */
    @PreAuthorize("@ss.hasPermi('production:notifications:add')")
    @Log(title = "消息通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysNotificationsEntity sysNotifications) {
        return toAjax(sysNotificationsService.insertSysNotifications(sysNotifications));
    }

    /**
     * 修改消息通知
     */
    @PreAuthorize("@ss.hasPermi('production:notifications:edit')")
    @Log(title = "消息通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysNotificationsEntity sysNotifications) {
        return toAjax(sysNotificationsService.updateSysNotifications(sysNotifications));
    }

    /**
     * 删除消息通知
     */
    @PreAuthorize("@ss.hasPermi('production:notifications:remove')")
    @Log(title = "消息通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(sysNotificationsService.deleteSysNotificationsByIds(ids));
    }
}
