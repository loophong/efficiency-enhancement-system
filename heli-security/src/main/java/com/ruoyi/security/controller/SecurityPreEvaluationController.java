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
import com.ruoyi.security.domain.SecurityPreEvaluation;
import com.ruoyi.security.service.ISecurityPreEvaluationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全预评价Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/PreEvaluation")
public class SecurityPreEvaluationController extends BaseController
{
    @Autowired
    private ISecurityPreEvaluationService securityPreEvaluationService;

    /**
     * 查询安全预评价列表
     */
    @PreAuthorize("@ss.hasPermi('security:PreEvaluation:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityPreEvaluation securityPreEvaluation)
    {
        startPage();
        List<SecurityPreEvaluation> list = securityPreEvaluationService.selectSecurityPreEvaluationList(securityPreEvaluation);
        return getDataTable(list);
    }

    /**
     * 导出安全预评价列表
     */
    @PreAuthorize("@ss.hasPermi('security:PreEvaluation:export')")
    @Log(title = "安全预评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityPreEvaluation securityPreEvaluation)
    {
        List<SecurityPreEvaluation> list = securityPreEvaluationService.selectSecurityPreEvaluationList(securityPreEvaluation);
        ExcelUtil<SecurityPreEvaluation> util = new ExcelUtil<SecurityPreEvaluation>(SecurityPreEvaluation.class);
        util.exportExcel(response, list, "安全预评价数据");
    }

    /**
     * 获取安全预评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:PreEvaluation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityPreEvaluationService.selectSecurityPreEvaluationById(id));
    }

    /**
     * 新增安全预评价
     */
    @PreAuthorize("@ss.hasPermi('security:PreEvaluation:add')")
    @Log(title = "安全预评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityPreEvaluation securityPreEvaluation)
    {
        return toAjax(securityPreEvaluationService.insertSecurityPreEvaluation(securityPreEvaluation));
    }

    /**
     * 修改安全预评价
     */
    @PreAuthorize("@ss.hasPermi('security:PreEvaluation:edit')")
    @Log(title = "安全预评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityPreEvaluation securityPreEvaluation)
    {
        return toAjax(securityPreEvaluationService.updateSecurityPreEvaluation(securityPreEvaluation));
    }

    /**
     * 删除安全预评价
     */
    @PreAuthorize("@ss.hasPermi('security:PreEvaluation:remove')")
    @Log(title = "安全预评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityPreEvaluationService.deleteSecurityPreEvaluationByIds(ids));
    }
}
