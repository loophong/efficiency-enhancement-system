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
import com.ruoyi.security.domain.SecurityRelatedPartyEvaluationRecords;
import com.ruoyi.security.service.ISecurityRelatedPartyEvaluationRecordsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 相关方评价记录Controller
 * 
 * @author wang
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/security/relatedpartyevaluationrecords")
public class SecurityRelatedPartyEvaluationRecordsController extends BaseController
{
    @Autowired
    private ISecurityRelatedPartyEvaluationRecordsService securityRelatedPartyEvaluationRecordsService;

    /**
     * 查询相关方评价记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyevaluationrecords:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRelatedPartyEvaluationRecords securityRelatedPartyEvaluationRecords)
    {
        startPage();
        List<SecurityRelatedPartyEvaluationRecords> list = securityRelatedPartyEvaluationRecordsService.selectSecurityRelatedPartyEvaluationRecordsList(securityRelatedPartyEvaluationRecords);
        return getDataTable(list);
    }

    /**
     * 导出相关方评价记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyevaluationrecords:export')")
    @Log(title = "相关方评价记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRelatedPartyEvaluationRecords securityRelatedPartyEvaluationRecords)
    {
        List<SecurityRelatedPartyEvaluationRecords> list = securityRelatedPartyEvaluationRecordsService.selectSecurityRelatedPartyEvaluationRecordsList(securityRelatedPartyEvaluationRecords);
        ExcelUtil<SecurityRelatedPartyEvaluationRecords> util = new ExcelUtil<SecurityRelatedPartyEvaluationRecords>(SecurityRelatedPartyEvaluationRecords.class);
        util.exportExcel(response, list, "相关方评价记录数据");
    }

    /**
     * 获取相关方评价记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyevaluationrecords:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityRelatedPartyEvaluationRecordsService.selectSecurityRelatedPartyEvaluationRecordsById(id));
    }

    /**
     * 新增相关方评价记录
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyevaluationrecords:add')")
    @Log(title = "相关方评价记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRelatedPartyEvaluationRecords securityRelatedPartyEvaluationRecords)
    {
        return toAjax(securityRelatedPartyEvaluationRecordsService.insertSecurityRelatedPartyEvaluationRecords(securityRelatedPartyEvaluationRecords));
    }

    /**
     * 修改相关方评价记录
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyevaluationrecords:edit')")
    @Log(title = "相关方评价记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRelatedPartyEvaluationRecords securityRelatedPartyEvaluationRecords)
    {
        return toAjax(securityRelatedPartyEvaluationRecordsService.updateSecurityRelatedPartyEvaluationRecords(securityRelatedPartyEvaluationRecords));
    }

    /**
     * 删除相关方评价记录
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyevaluationrecords:remove')")
    @Log(title = "相关方评价记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityRelatedPartyEvaluationRecordsService.deleteSecurityRelatedPartyEvaluationRecordsByIds(ids));
    }
}
