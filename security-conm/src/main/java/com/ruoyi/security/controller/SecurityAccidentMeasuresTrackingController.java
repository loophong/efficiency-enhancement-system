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
import com.ruoyi.security.domain.SecurityAccidentMeasuresTracking;
import com.ruoyi.security.service.ISecurityAccidentMeasuresTrackingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 事故对策及跟踪记录Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/AccidentMeasuresTracking")
public class SecurityAccidentMeasuresTrackingController extends BaseController
{
    @Autowired
    private ISecurityAccidentMeasuresTrackingService securityAccidentMeasuresTrackingService;

    /**
     * 查询事故对策及跟踪记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentMeasuresTracking:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityAccidentMeasuresTracking securityAccidentMeasuresTracking)
    {
        startPage();
        List<SecurityAccidentMeasuresTracking> list = securityAccidentMeasuresTrackingService.selectSecurityAccidentMeasuresTrackingList(securityAccidentMeasuresTracking);
        return getDataTable(list);
    }

    /**
     * 导出事故对策及跟踪记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentMeasuresTracking:export')")
    @Log(title = "事故对策及跟踪记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityAccidentMeasuresTracking securityAccidentMeasuresTracking)
    {
        List<SecurityAccidentMeasuresTracking> list = securityAccidentMeasuresTrackingService.selectSecurityAccidentMeasuresTrackingList(securityAccidentMeasuresTracking);
        ExcelUtil<SecurityAccidentMeasuresTracking> util = new ExcelUtil<SecurityAccidentMeasuresTracking>(SecurityAccidentMeasuresTracking.class);
        util.exportExcel(response, list, "事故对策及跟踪记录数据");
    }

    /**
     * 获取事故对策及跟踪记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentMeasuresTracking:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityAccidentMeasuresTrackingService.selectSecurityAccidentMeasuresTrackingById(id));
    }

    /**
     * 新增事故对策及跟踪记录
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentMeasuresTracking:add')")
    @Log(title = "事故对策及跟踪记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityAccidentMeasuresTracking securityAccidentMeasuresTracking)
    {
        return toAjax(securityAccidentMeasuresTrackingService.insertSecurityAccidentMeasuresTracking(securityAccidentMeasuresTracking));
    }

    /**
     * 修改事故对策及跟踪记录
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentMeasuresTracking:edit')")
    @Log(title = "事故对策及跟踪记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityAccidentMeasuresTracking securityAccidentMeasuresTracking)
    {
        return toAjax(securityAccidentMeasuresTrackingService.updateSecurityAccidentMeasuresTracking(securityAccidentMeasuresTracking));
    }

    /**
     * 删除事故对策及跟踪记录
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentMeasuresTracking:remove')")
    @Log(title = "事故对策及跟踪记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityAccidentMeasuresTrackingService.deleteSecurityAccidentMeasuresTrackingByIds(ids));
    }
}
