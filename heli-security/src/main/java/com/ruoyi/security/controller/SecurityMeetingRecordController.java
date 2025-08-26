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
import com.ruoyi.security.domain.SecurityMeetingRecord;
import com.ruoyi.security.service.ISecurityMeetingRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全会议记录Controller
 * 
 * @author wang
 * @date 2025-08-26
 */
@RestController
@RequestMapping("/security/meeting")
public class SecurityMeetingRecordController extends BaseController
{
    @Autowired
    private ISecurityMeetingRecordService securityMeetingRecordService;

    /**
     * 查询安全会议记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:meeting:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityMeetingRecord securityMeetingRecord)
    {
        startPage();
        List<SecurityMeetingRecord> list = securityMeetingRecordService.selectSecurityMeetingRecordList(securityMeetingRecord);
        return getDataTable(list);
    }

    /**
     * 导出安全会议记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:meeting:export')")
    @Log(title = "安全会议记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityMeetingRecord securityMeetingRecord)
    {
        List<SecurityMeetingRecord> list = securityMeetingRecordService.selectSecurityMeetingRecordList(securityMeetingRecord);
        ExcelUtil<SecurityMeetingRecord> util = new ExcelUtil<SecurityMeetingRecord>(SecurityMeetingRecord.class);
        util.exportExcel(response, list, "安全会议记录数据");
    }

    /**
     * 获取安全会议记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:meeting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityMeetingRecordService.selectSecurityMeetingRecordById(id));
    }

    /**
     * 新增安全会议记录
     */
    @PreAuthorize("@ss.hasPermi('security:meeting:add')")
    @Log(title = "安全会议记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityMeetingRecord securityMeetingRecord)
    {
        return toAjax(securityMeetingRecordService.insertSecurityMeetingRecord(securityMeetingRecord));
    }

    /**
     * 修改安全会议记录
     */
    @PreAuthorize("@ss.hasPermi('security:meeting:edit')")
    @Log(title = "安全会议记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityMeetingRecord securityMeetingRecord)
    {
        return toAjax(securityMeetingRecordService.updateSecurityMeetingRecord(securityMeetingRecord));
    }

    /**
     * 删除安全会议记录
     */
    @PreAuthorize("@ss.hasPermi('security:meeting:remove')")
    @Log(title = "安全会议记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityMeetingRecordService.deleteSecurityMeetingRecordByIds(ids));
    }
}
