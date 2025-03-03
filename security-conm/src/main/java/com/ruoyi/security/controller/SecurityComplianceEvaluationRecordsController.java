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
import com.ruoyi.security.domain.SecurityComplianceEvaluationRecords;
import com.ruoyi.security.service.ISecurityComplianceEvaluationRecordsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 合规性评价记录Controller
 * 
 * @author wang
 * @date 2025-03-02
 */
@RestController
@RequestMapping("/security/compliance")
public class SecurityComplianceEvaluationRecordsController extends BaseController
{
    @Autowired
    private ISecurityComplianceEvaluationRecordsService securityComplianceEvaluationRecordsService;

    /**
     * 查询合规性评价记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        startPage();
        List<SecurityComplianceEvaluationRecords> list = securityComplianceEvaluationRecordsService.selectSecurityComplianceEvaluationRecordsList(securityComplianceEvaluationRecords);
        return getDataTable(list);
    }

    /**
     * 导出合规性评价记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:export')")
    @Log(title = "合规性评价记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        List<SecurityComplianceEvaluationRecords> list = securityComplianceEvaluationRecordsService.selectSecurityComplianceEvaluationRecordsList(securityComplianceEvaluationRecords);
        ExcelUtil<SecurityComplianceEvaluationRecords> util = new ExcelUtil<SecurityComplianceEvaluationRecords>(SecurityComplianceEvaluationRecords.class);
        util.exportExcel(response, list, "合规性评价记录数据");
    }

    /**
     * 获取合规性评价记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityComplianceEvaluationRecordsService.selectSecurityComplianceEvaluationRecordsById(id));
    }

    /**
     * 新增合规性评价记录
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:add')")
    @Log(title = "合规性评价记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        return toAjax(securityComplianceEvaluationRecordsService.insertSecurityComplianceEvaluationRecords(securityComplianceEvaluationRecords));
    }

    /**
     * 修改合规性评价记录
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:edit')")
    @Log(title = "合规性评价记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        return toAjax(securityComplianceEvaluationRecordsService.updateSecurityComplianceEvaluationRecords(securityComplianceEvaluationRecords));
    }

    /**
     * 删除合规性评价记录
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:remove')")
    @Log(title = "合规性评价记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityComplianceEvaluationRecordsService.deleteSecurityComplianceEvaluationRecordsByIds(ids));
    }
}
