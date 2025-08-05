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
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecurityEducationAnnualTrainingPlan;
import com.ruoyi.security.service.ISecurityEducationAnnualTrainingPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 年度培训计划Controller
 * 
 * @author wang
 * @date 2025-03-02
 */
@RestController
@RequestMapping("/security/trainingplan")
public class SecurityEducationAnnualTrainingPlanController extends BaseController
{
    @Autowired
    private ISecurityEducationAnnualTrainingPlanService securityEducationAnnualTrainingPlanService;

    /**
     * 查询年度培训计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:trainingplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEducationAnnualTrainingPlan securityEducationAnnualTrainingPlan)
    {
        startPage();
        List<SecurityEducationAnnualTrainingPlan> list = securityEducationAnnualTrainingPlanService.selectSecurityEducationAnnualTrainingPlanList(securityEducationAnnualTrainingPlan);
        return getDataTable(list);
    }

    /**
     * 导出年度培训计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:trainingplan:export')")
    @Log(title = "年度培训计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEducationAnnualTrainingPlan securityEducationAnnualTrainingPlan)
    {
        List<SecurityEducationAnnualTrainingPlan> list = securityEducationAnnualTrainingPlanService.selectSecurityEducationAnnualTrainingPlanList(securityEducationAnnualTrainingPlan);
        ExcelUtil<SecurityEducationAnnualTrainingPlan> util = new ExcelUtil<SecurityEducationAnnualTrainingPlan>(SecurityEducationAnnualTrainingPlan.class);
        util.exportExcel(response, list, "年度培训计划数据");
    }
    
//    /**
//     * 导入年度培训计划数据
//     */
//    @PreAuthorize("@ss.hasPermi('security:trainingplan:import')")
//    @Log(title = "年度培训计划", businessType = BusinessType.IMPORT)
//    @PostMapping("/importData")
//    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
//    {
//        String message = securityEducationAnnualTrainingPlanService.importData(file.getInputStream(), updateSupport);
//        return success(message);
//    }
    
    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:trainingplan:export')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecurityEducationAnnualTrainingPlan> util = new ExcelUtil<SecurityEducationAnnualTrainingPlan>(SecurityEducationAnnualTrainingPlan.class);
        util.importTemplateExcel(response, "年度培训计划数据");
    }

    /**
     * 导入年度培训计划数据
     */
    @PreAuthorize("@ss.hasPermi('security:trainingplan:import')")
    @Log(title = "年度培训计划", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        String message = securityEducationAnnualTrainingPlanService.importData(file.getInputStream(), updateSupport);
        return success(message);
    }

    /**
     * 获取年度培训计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:trainingplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEducationAnnualTrainingPlanService.selectSecurityEducationAnnualTrainingPlanById(id));
    }

    /**
     * 新增年度培训计划
     */
    @PreAuthorize("@ss.hasPermi('security:trainingplan:add')")
    @Log(title = "年度培训计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEducationAnnualTrainingPlan securityEducationAnnualTrainingPlan)
    {
        return toAjax(securityEducationAnnualTrainingPlanService.insertSecurityEducationAnnualTrainingPlan(securityEducationAnnualTrainingPlan));
    }

    /**
     * 修改年度培训计划
     */
    @PreAuthorize("@ss.hasPermi('security:trainingplan:edit')")
    @Log(title = "年度培训计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEducationAnnualTrainingPlan securityEducationAnnualTrainingPlan)
    {
        return toAjax(securityEducationAnnualTrainingPlanService.updateSecurityEducationAnnualTrainingPlan(securityEducationAnnualTrainingPlan));
    }

    /**
     * 删除年度培训计划
     */
    @PreAuthorize("@ss.hasPermi('security:trainingplan:remove')")
    @Log(title = "年度培训计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEducationAnnualTrainingPlanService.deleteSecurityEducationAnnualTrainingPlanByIds(ids));
    }
}
