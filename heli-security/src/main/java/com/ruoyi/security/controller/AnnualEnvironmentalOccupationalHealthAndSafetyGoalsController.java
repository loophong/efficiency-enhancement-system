package com.ruoyi.security.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ruoyi.security.domain.AnnualEnvironmentalOccupationalHealthAndSafetyGoals;
import com.ruoyi.security.service.IAnnualEnvironmentalOccupationalHealthAndSafetyGoalsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import java.io.IOException;

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
    private static final Logger logger = LoggerFactory.getLogger(AnnualEnvironmentalOccupationalHealthAndSafetyGoalsController.class);
    
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
        
        // 对数据按照部门名称排序，确保相同部门的数据连续，便于合并单元格
        list.sort((a, b) -> {
            if (a.getDepartmentName() == null && b.getDepartmentName() == null) {
                return 0;
            }
            if (a.getDepartmentName() == null) {
                return -1;
            }
            if (b.getDepartmentName() == null) {
                return 1;
            }
            return a.getDepartmentName().compareTo(b.getDepartmentName());
        });
        
        ExcelUtil<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> util = new ExcelUtil<AnnualEnvironmentalOccupationalHealthAndSafetyGoals>(AnnualEnvironmentalOccupationalHealthAndSafetyGoals.class);
        util.exportExcel(response, list, "年度环境职业健康安全目标、指标及工作分解数据");
    }

    /**
     * 获取导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:HealthAndSafetyGoals:import')")
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        try {
            logger.info("开始获取年度环境职业健康安全目标导入模板");
            annualEnvironmentalOccupationalHealthAndSafetyGoalsService.importTemplateExcel(response, "年度环境职业健康安全目标导入模板");
            logger.info("模板生成完成并已发送到客户端");
        } catch (Exception e) {
            logger.error("获取导入模板异常", e);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("{\"code\":500,\"msg\":\"获取导入模板失败：" + e.getMessage() + "\"}");
        }
    }

    /**
     * 导入数据
     */
    @PreAuthorize("@ss.hasPermi('security:HealthAndSafetyGoals:import')")
    @Log(title = "年度环境职业健康安全目标", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile excelFile) throws Exception
    {
        return annualEnvironmentalOccupationalHealthAndSafetyGoalsService.importData(excelFile);
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
