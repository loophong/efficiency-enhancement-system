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
import com.ruoyi.security.domain.AnnualEnvironmentalOccupationalHealthAndSafetyGoals;
import com.ruoyi.security.service.IAnnualEnvironmentalOccupationalHealthAndSafetyGoalsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 年度环境职业健康安全目标、指标及工作分解Controller
 * 
 * @author wang
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/security/HealthAndSafetyGoals")
public class AnnualEnvironmentalOccupationalHealthAndSafetyGoalsController extends BaseController
{
    @Autowired
    private IAnnualEnvironmentalOccupationalHealthAndSafetyGoalsService annualEnvironmentalOccupationalHealthAndSafetyGoalsService;

    /**
     * 查询年度环境职业健康安全目标、指标及工作分解列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthAndSafetyGoals:list')")
    @GetMapping("/list")
    public TableDataInfo list(AnnualEnvironmentalOccupationalHealthAndSafetyGoals annualEnvironmentalOccupationalHealthAndSafetyGoals)
    {
        startPage();
        List<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> list = annualEnvironmentalOccupationalHealthAndSafetyGoalsService.selectAnnualEnvironmentalOccupationalHealthAndSafetyGoalsList(annualEnvironmentalOccupationalHealthAndSafetyGoals);
        return getDataTable(list);
    }

    /**
     * 导出年度环境职业健康安全目标、指标及工作分解列表
     */
    @PreAuthorize("@ss.hasPermi('security:HealthAndSafetyGoals:export')")
    @Log(title = "年度环境职业健康安全目标、指标及工作分解", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AnnualEnvironmentalOccupationalHealthAndSafetyGoals annualEnvironmentalOccupationalHealthAndSafetyGoals)
    {
        List<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> list = annualEnvironmentalOccupationalHealthAndSafetyGoalsService.selectAnnualEnvironmentalOccupationalHealthAndSafetyGoalsList(annualEnvironmentalOccupationalHealthAndSafetyGoals);
        ExcelUtil<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> util = new ExcelUtil<AnnualEnvironmentalOccupationalHealthAndSafetyGoals>(AnnualEnvironmentalOccupationalHealthAndSafetyGoals.class);
        util.exportExcel(response, list, "年度环境职业健康安全目标、指标及工作分解数据");
    }

    /**
     * 获取年度环境职业健康安全目标、指标及工作分解详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:HealthAndSafetyGoals:query')")
    @GetMapping(value = "/{serialNumber}")
    public AjaxResult getInfo(@PathVariable("serialNumber") Long serialNumber)
    {
        return success(annualEnvironmentalOccupationalHealthAndSafetyGoalsService.selectAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumber(serialNumber));
    }

    /**
     * 新增年度环境职业健康安全目标、指标及工作分解
     */
    @PreAuthorize("@ss.hasPermi('security:HealthAndSafetyGoals:add')")
    @Log(title = "年度环境职业健康安全目标、指标及工作分解", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AnnualEnvironmentalOccupationalHealthAndSafetyGoals annualEnvironmentalOccupationalHealthAndSafetyGoals)
    {
        return toAjax(annualEnvironmentalOccupationalHealthAndSafetyGoalsService.insertAnnualEnvironmentalOccupationalHealthAndSafetyGoals(annualEnvironmentalOccupationalHealthAndSafetyGoals));
    }

    /**
     * 修改年度环境职业健康安全目标、指标及工作分解
     */
    @PreAuthorize("@ss.hasPermi('security:HealthAndSafetyGoals:edit')")
    @Log(title = "年度环境职业健康安全目标、指标及工作分解", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AnnualEnvironmentalOccupationalHealthAndSafetyGoals annualEnvironmentalOccupationalHealthAndSafetyGoals)
    {
        return toAjax(annualEnvironmentalOccupationalHealthAndSafetyGoalsService.updateAnnualEnvironmentalOccupationalHealthAndSafetyGoals(annualEnvironmentalOccupationalHealthAndSafetyGoals));
    }

    /**
     * 删除年度环境职业健康安全目标、指标及工作分解
     */
    @PreAuthorize("@ss.hasPermi('security:HealthAndSafetyGoals:remove')")
    @Log(title = "年度环境职业健康安全目标、指标及工作分解", businessType = BusinessType.DELETE)
	@DeleteMapping("/{serialNumbers}")
    public AjaxResult remove(@PathVariable Long[] serialNumbers)
    {
        return toAjax(annualEnvironmentalOccupationalHealthAndSafetyGoalsService.deleteAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumbers(serialNumbers));
    }
}
