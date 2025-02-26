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
import com.ruoyi.security.domain.SecurityRiskOpportunityReviewRecords;
import com.ruoyi.security.service.ISecurityRiskOpportunityReviewRecordsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 记录表Controller
 * 
 * @author wang
 * @date 2025-02-23
 */
@RestController
@RequestMapping("/security/record")
public class SecurityRiskOpportunityReviewRecordsController extends BaseController
{
    @Autowired
    private ISecurityRiskOpportunityReviewRecordsService securityRiskOpportunityReviewRecordsService;

    /**
     * 查询记录表列表
     */
    @PreAuthorize("@ss.hasPermi('security:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRiskOpportunityReviewRecords securityRiskOpportunityReviewRecords)
    {
        startPage();
        List<SecurityRiskOpportunityReviewRecords> list = securityRiskOpportunityReviewRecordsService.selectSecurityRiskOpportunityReviewRecordsList(securityRiskOpportunityReviewRecords);
        return getDataTable(list);
    }

    /**
     * 导出记录表列表
     */
    @PreAuthorize("@ss.hasPermi('security:record:export')")
    @Log(title = "记录表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRiskOpportunityReviewRecords securityRiskOpportunityReviewRecords)
    {
        List<SecurityRiskOpportunityReviewRecords> list = securityRiskOpportunityReviewRecordsService.selectSecurityRiskOpportunityReviewRecordsList(securityRiskOpportunityReviewRecords);
        ExcelUtil<SecurityRiskOpportunityReviewRecords> util = new ExcelUtil<SecurityRiskOpportunityReviewRecords>(SecurityRiskOpportunityReviewRecords.class);
        util.exportExcel(response, list, "记录表数据");
    }

    /**
     * 获取记录表详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityRiskOpportunityReviewRecordsService.selectSecurityRiskOpportunityReviewRecordsById(id));
    }

    /**
     * 新增记录表
     */
    @PreAuthorize("@ss.hasPermi('security:record:add')")
    @Log(title = "记录表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRiskOpportunityReviewRecords securityRiskOpportunityReviewRecords)
    {
        return toAjax(securityRiskOpportunityReviewRecordsService.insertSecurityRiskOpportunityReviewRecords(securityRiskOpportunityReviewRecords));
    }

    /**
     * 修改记录表
     */
    @PreAuthorize("@ss.hasPermi('security:record:edit')")
    @Log(title = "记录表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRiskOpportunityReviewRecords securityRiskOpportunityReviewRecords)
    {
        return toAjax(securityRiskOpportunityReviewRecordsService.updateSecurityRiskOpportunityReviewRecords(securityRiskOpportunityReviewRecords));
    }

    /**
     * 删除记录表
     */
    @PreAuthorize("@ss.hasPermi('security:record:remove')")
    @Log(title = "记录表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityRiskOpportunityReviewRecordsService.deleteSecurityRiskOpportunityReviewRecordsByIds(ids));
    }
}
