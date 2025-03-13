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
import com.ruoyi.security.domain.SecurityRelatedPartySafetyTrainingExam;
import com.ruoyi.security.service.ISecurityRelatedPartySafetyTrainingExamService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 相关方安全教育培训及考试Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/SafetyTrainingExam")
public class SecurityRelatedPartySafetyTrainingExamController extends BaseController
{
    @Autowired
    private ISecurityRelatedPartySafetyTrainingExamService securityRelatedPartySafetyTrainingExamService;

    /**
     * 查询相关方安全教育培训及考试列表
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyTrainingExam:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRelatedPartySafetyTrainingExam securityRelatedPartySafetyTrainingExam)
    {
        startPage();
        List<SecurityRelatedPartySafetyTrainingExam> list = securityRelatedPartySafetyTrainingExamService.selectSecurityRelatedPartySafetyTrainingExamList(securityRelatedPartySafetyTrainingExam);
        return getDataTable(list);
    }

    /**
     * 导出相关方安全教育培训及考试列表
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyTrainingExam:export')")
    @Log(title = "相关方安全教育培训及考试", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRelatedPartySafetyTrainingExam securityRelatedPartySafetyTrainingExam)
    {
        List<SecurityRelatedPartySafetyTrainingExam> list = securityRelatedPartySafetyTrainingExamService.selectSecurityRelatedPartySafetyTrainingExamList(securityRelatedPartySafetyTrainingExam);
        ExcelUtil<SecurityRelatedPartySafetyTrainingExam> util = new ExcelUtil<SecurityRelatedPartySafetyTrainingExam>(SecurityRelatedPartySafetyTrainingExam.class);
        util.exportExcel(response, list, "相关方安全教育培训及考试数据");
    }

    /**
     * 获取相关方安全教育培训及考试详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyTrainingExam:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityRelatedPartySafetyTrainingExamService.selectSecurityRelatedPartySafetyTrainingExamById(id));
    }

    /**
     * 新增相关方安全教育培训及考试
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyTrainingExam:add')")
    @Log(title = "相关方安全教育培训及考试", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRelatedPartySafetyTrainingExam securityRelatedPartySafetyTrainingExam)
    {
        return toAjax(securityRelatedPartySafetyTrainingExamService.insertSecurityRelatedPartySafetyTrainingExam(securityRelatedPartySafetyTrainingExam));
    }

    /**
     * 修改相关方安全教育培训及考试
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyTrainingExam:edit')")
    @Log(title = "相关方安全教育培训及考试", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRelatedPartySafetyTrainingExam securityRelatedPartySafetyTrainingExam)
    {
        return toAjax(securityRelatedPartySafetyTrainingExamService.updateSecurityRelatedPartySafetyTrainingExam(securityRelatedPartySafetyTrainingExam));
    }

    /**
     * 删除相关方安全教育培训及考试
     */
    @PreAuthorize("@ss.hasPermi('security:SafetyTrainingExam:remove')")
    @Log(title = "相关方安全教育培训及考试", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityRelatedPartySafetyTrainingExamService.deleteSecurityRelatedPartySafetyTrainingExamByIds(ids));
    }
}
