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
import com.ruoyi.security.domain.SecurityAccidentProcessRecords;
import com.ruoyi.security.service.ISecurityAccidentProcessRecordsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 事故过程记录Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/AccidentProcessRecords")
public class SecurityAccidentProcessRecordsController extends BaseController
{
    @Autowired
    private ISecurityAccidentProcessRecordsService securityAccidentProcessRecordsService;

    /**
     * 查询事故过程记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentProcessRecords:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityAccidentProcessRecords securityAccidentProcessRecords)
    {
        startPage();
        List<SecurityAccidentProcessRecords> list = securityAccidentProcessRecordsService.selectSecurityAccidentProcessRecordsList(securityAccidentProcessRecords);
        return getDataTable(list);
    }

    /**
     * 导出事故过程记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentProcessRecords:export')")
    @Log(title = "事故过程记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityAccidentProcessRecords securityAccidentProcessRecords)
    {
        List<SecurityAccidentProcessRecords> list = securityAccidentProcessRecordsService.selectSecurityAccidentProcessRecordsList(securityAccidentProcessRecords);
        ExcelUtil<SecurityAccidentProcessRecords> util = new ExcelUtil<SecurityAccidentProcessRecords>(SecurityAccidentProcessRecords.class);
        util.exportExcel(response, list, "事故过程记录数据");
    }

    /**
     * 获取事故过程记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentProcessRecords:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityAccidentProcessRecordsService.selectSecurityAccidentProcessRecordsById(id));
    }

    /**
     * 新增事故过程记录
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentProcessRecords:add')")
    @Log(title = "事故过程记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityAccidentProcessRecords securityAccidentProcessRecords)
    {
        return toAjax(securityAccidentProcessRecordsService.insertSecurityAccidentProcessRecords(securityAccidentProcessRecords));
    }

    /**
     * 修改事故过程记录
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentProcessRecords:edit')")
    @Log(title = "事故过程记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityAccidentProcessRecords securityAccidentProcessRecords)
    {
        return toAjax(securityAccidentProcessRecordsService.updateSecurityAccidentProcessRecords(securityAccidentProcessRecords));
    }

    /**
     * 删除事故过程记录
     */
    @PreAuthorize("@ss.hasPermi('security:AccidentProcessRecords:remove')")
    @Log(title = "事故过程记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityAccidentProcessRecordsService.deleteSecurityAccidentProcessRecordsByIds(ids));
    }
}
