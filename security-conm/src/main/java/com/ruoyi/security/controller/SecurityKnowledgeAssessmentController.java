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
import com.ruoyi.security.domain.SecurityKnowledgeAssessment;
import com.ruoyi.security.service.ISecurityKnowledgeAssessmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全知识考核Controller
 * 
 * @author wang
 * @date 2025-03-02
 */
@RestController
@RequestMapping("/security/knowledgeassessment")
public class SecurityKnowledgeAssessmentController extends BaseController
{
    @Autowired
    private ISecurityKnowledgeAssessmentService securityKnowledgeAssessmentService;

    /**
     * 查询安全知识考核列表
     */
    @PreAuthorize("@ss.hasPermi('security:knowledgeassessment:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityKnowledgeAssessment securityKnowledgeAssessment)
    {
        startPage();
        List<SecurityKnowledgeAssessment> list = securityKnowledgeAssessmentService.selectSecurityKnowledgeAssessmentList(securityKnowledgeAssessment);
        return getDataTable(list);
    }

    /**
     * 导出安全知识考核列表
     */
    @PreAuthorize("@ss.hasPermi('security:knowledgeassessment:export')")
    @Log(title = "安全知识考核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityKnowledgeAssessment securityKnowledgeAssessment)
    {
        List<SecurityKnowledgeAssessment> list = securityKnowledgeAssessmentService.selectSecurityKnowledgeAssessmentList(securityKnowledgeAssessment);
        ExcelUtil<SecurityKnowledgeAssessment> util = new ExcelUtil<SecurityKnowledgeAssessment>(SecurityKnowledgeAssessment.class);
        util.exportExcel(response, list, "安全知识考核数据");
    }

    /**
     * 获取安全知识考核详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:knowledgeassessment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityKnowledgeAssessmentService.selectSecurityKnowledgeAssessmentById(id));
    }

    /**
     * 新增安全知识考核
     */
    @PreAuthorize("@ss.hasPermi('security:knowledgeassessment:add')")
    @Log(title = "安全知识考核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityKnowledgeAssessment securityKnowledgeAssessment)
    {
        return toAjax(securityKnowledgeAssessmentService.insertSecurityKnowledgeAssessment(securityKnowledgeAssessment));
    }

    /**
     * 修改安全知识考核
     */
    @PreAuthorize("@ss.hasPermi('security:knowledgeassessment:edit')")
    @Log(title = "安全知识考核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityKnowledgeAssessment securityKnowledgeAssessment)
    {
        return toAjax(securityKnowledgeAssessmentService.updateSecurityKnowledgeAssessment(securityKnowledgeAssessment));
    }

    /**
     * 删除安全知识考核
     */
    @PreAuthorize("@ss.hasPermi('security:knowledgeassessment:remove')")
    @Log(title = "安全知识考核", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityKnowledgeAssessmentService.deleteSecurityKnowledgeAssessmentByIds(ids));
    }
}
