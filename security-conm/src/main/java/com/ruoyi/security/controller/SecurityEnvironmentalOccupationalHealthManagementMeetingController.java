package com.ruoyi.security.controller;

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
import com.ruoyi.security.domain.SecurityEnvironmentalOccupationalHealthManagementMeeting;
import com.ruoyi.security.service.ISecurityEnvironmentalOccupationalHealthManagementMeetingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境职业健康安全管理评审会议记录Controller
 * 
 * @author wang
 * @date 2025-03-12
 */
@RestController
@RequestMapping("/security/HealthManagementMeeting")
public class SecurityEnvironmentalOccupationalHealthManagementMeetingController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentalOccupationalHealthManagementMeetingService securityEnvironmentalOccupationalHealthManagementMeetingService;

    /**
     * 查询环境职业健康安全管理评审会议记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementMeeting:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentalOccupationalHealthManagementMeeting securityEnvironmentalOccupationalHealthManagementMeeting)
    {
        startPage();
        List<SecurityEnvironmentalOccupationalHealthManagementMeeting> list = securityEnvironmentalOccupationalHealthManagementMeetingService.selectSecurityEnvironmentalOccupationalHealthManagementMeetingList(securityEnvironmentalOccupationalHealthManagementMeeting);
        return getDataTable(list);
    }

    /**
     * 导出环境职业健康安全管理评审会议记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementMeeting:export')")
    @Log(title = "环境职业健康安全管理评审会议记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentalOccupationalHealthManagementMeeting securityEnvironmentalOccupationalHealthManagementMeeting)
    {
        List<SecurityEnvironmentalOccupationalHealthManagementMeeting> list = securityEnvironmentalOccupationalHealthManagementMeetingService.selectSecurityEnvironmentalOccupationalHealthManagementMeetingList(securityEnvironmentalOccupationalHealthManagementMeeting);
        ExcelUtil<SecurityEnvironmentalOccupationalHealthManagementMeeting> util = new ExcelUtil<SecurityEnvironmentalOccupationalHealthManagementMeeting>(SecurityEnvironmentalOccupationalHealthManagementMeeting.class);
        util.exportExcel(response, list, "环境职业健康安全管理评审会议记录数据");
    }

    /**
     * 获取环境职业健康安全管理评审会议记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementMeeting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityEnvironmentalOccupationalHealthManagementMeetingService.selectSecurityEnvironmentalOccupationalHealthManagementMeetingById(id));
    }

    /**
     * 新增环境职业健康安全管理评审会议记录
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementMeeting:add')")
    @Log(title = "环境职业健康安全管理评审会议记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentalOccupationalHealthManagementMeeting securityEnvironmentalOccupationalHealthManagementMeeting)
    {
        return toAjax(securityEnvironmentalOccupationalHealthManagementMeetingService.insertSecurityEnvironmentalOccupationalHealthManagementMeeting(securityEnvironmentalOccupationalHealthManagementMeeting));
    }

    /**
     * 修改环境职业健康安全管理评审会议记录
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementMeeting:edit')")
    @Log(title = "环境职业健康安全管理评审会议记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentalOccupationalHealthManagementMeeting securityEnvironmentalOccupationalHealthManagementMeeting)
    {
        return toAjax(securityEnvironmentalOccupationalHealthManagementMeetingService.updateSecurityEnvironmentalOccupationalHealthManagementMeeting(securityEnvironmentalOccupationalHealthManagementMeeting));
    }

    /**
     * 删除环境职业健康安全管理评审会议记录
     */
    @PreAuthorize("@ss.hasPermi('security:HealthManagementMeeting:remove')")
    @Log(title = "环境职业健康安全管理评审会议记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityEnvironmentalOccupationalHealthManagementMeetingService.deleteSecurityEnvironmentalOccupationalHealthManagementMeetingByIds(ids));
    }
}
