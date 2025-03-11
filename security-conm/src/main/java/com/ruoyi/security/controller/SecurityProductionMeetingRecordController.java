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
import com.ruoyi.security.domain.SecurityProductionMeetingRecord;
import com.ruoyi.security.service.ISecurityProductionMeetingRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全会议记录Controller
 * 
 * @author wang
 * @date 2025-03-07
 */
@RestController
@RequestMapping("/security/MeetingRecord")
public class SecurityProductionMeetingRecordController extends BaseController
{
    @Autowired
    private ISecurityProductionMeetingRecordService securityProductionMeetingRecordService;

    /**
     * 查询安全会议记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:MeetingRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityProductionMeetingRecord securityProductionMeetingRecord)
    {
        startPage();
        List<SecurityProductionMeetingRecord> list = securityProductionMeetingRecordService.selectSecurityProductionMeetingRecordList(securityProductionMeetingRecord);
        return getDataTable(list);
    }

    /**
     * 导出安全会议记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:MeetingRecord:export')")
    @Log(title = "安全会议记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityProductionMeetingRecord securityProductionMeetingRecord)
    {
        List<SecurityProductionMeetingRecord> list = securityProductionMeetingRecordService.selectSecurityProductionMeetingRecordList(securityProductionMeetingRecord);
        ExcelUtil<SecurityProductionMeetingRecord> util = new ExcelUtil<SecurityProductionMeetingRecord>(SecurityProductionMeetingRecord.class);
        util.exportExcel(response, list, "安全会议记录数据");
    }

    /**
     * 获取安全会议记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:MeetingRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityProductionMeetingRecordService.selectSecurityProductionMeetingRecordById(id));
    }

    /**
     * 新增安全会议记录
     */
    @PreAuthorize("@ss.hasPermi('security:MeetingRecord:add')")
    @Log(title = "安全会议记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityProductionMeetingRecord securityProductionMeetingRecord)
    {
        return toAjax(securityProductionMeetingRecordService.insertSecurityProductionMeetingRecord(securityProductionMeetingRecord));
    }

    /**
     * 修改安全会议记录
     */
    @PreAuthorize("@ss.hasPermi('security:MeetingRecord:edit')")
    @Log(title = "安全会议记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityProductionMeetingRecord securityProductionMeetingRecord)
    {
        return toAjax(securityProductionMeetingRecordService.updateSecurityProductionMeetingRecord(securityProductionMeetingRecord));
    }

    /**
     * 删除安全会议记录
     */
    @PreAuthorize("@ss.hasPermi('security:MeetingRecord:remove')")
    @Log(title = "安全会议记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityProductionMeetingRecordService.deleteSecurityProductionMeetingRecordByIds(ids));
    }
}
