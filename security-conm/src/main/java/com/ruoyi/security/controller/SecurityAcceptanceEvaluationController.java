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
import com.ruoyi.security.domain.SecurityAcceptanceEvaluation;
import com.ruoyi.security.service.ISecurityAcceptanceEvaluationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全验收评价Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/AcceptanceEvaluation")
public class SecurityAcceptanceEvaluationController extends BaseController
{
    @Autowired
    private ISecurityAcceptanceEvaluationService securityAcceptanceEvaluationService;

    /**
     * 查询安全验收评价列表
     */
    @PreAuthorize("@ss.hasPermi('security:AcceptanceEvaluation:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityAcceptanceEvaluation securityAcceptanceEvaluation)
    {
        startPage();
        List<SecurityAcceptanceEvaluation> list = securityAcceptanceEvaluationService.selectSecurityAcceptanceEvaluationList(securityAcceptanceEvaluation);
        return getDataTable(list);
    }

    /**
     * 导出安全验收评价列表
     */
    @PreAuthorize("@ss.hasPermi('security:AcceptanceEvaluation:export')")
    @Log(title = "安全验收评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityAcceptanceEvaluation securityAcceptanceEvaluation)
    {
        List<SecurityAcceptanceEvaluation> list = securityAcceptanceEvaluationService.selectSecurityAcceptanceEvaluationList(securityAcceptanceEvaluation);
        ExcelUtil<SecurityAcceptanceEvaluation> util = new ExcelUtil<SecurityAcceptanceEvaluation>(SecurityAcceptanceEvaluation.class);
        util.exportExcel(response, list, "安全验收评价数据");
    }

    /**
     * 获取安全验收评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:AcceptanceEvaluation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityAcceptanceEvaluationService.selectSecurityAcceptanceEvaluationById(id));
    }

    /**
     * 新增安全验收评价
     */
    @PreAuthorize("@ss.hasPermi('security:AcceptanceEvaluation:add')")
    @Log(title = "安全验收评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityAcceptanceEvaluation securityAcceptanceEvaluation)
    {
        return toAjax(securityAcceptanceEvaluationService.insertSecurityAcceptanceEvaluation(securityAcceptanceEvaluation));
    }

    /**
     * 修改安全验收评价
     */
    @PreAuthorize("@ss.hasPermi('security:AcceptanceEvaluation:edit')")
    @Log(title = "安全验收评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityAcceptanceEvaluation securityAcceptanceEvaluation)
    {
        return toAjax(securityAcceptanceEvaluationService.updateSecurityAcceptanceEvaluation(securityAcceptanceEvaluation));
    }

    /**
     * 删除安全验收评价
     */
    @PreAuthorize("@ss.hasPermi('security:AcceptanceEvaluation:remove')")
    @Log(title = "安全验收评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityAcceptanceEvaluationService.deleteSecurityAcceptanceEvaluationByIds(ids));
    }
}
