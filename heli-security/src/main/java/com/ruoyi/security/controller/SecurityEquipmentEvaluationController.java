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
import com.ruoyi.security.domain.SecurityEquipmentEvaluation;
import com.ruoyi.security.service.ISecurityEquipmentEvaluationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全设备设施现状评价Controller
 * 
 * @author wang
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/security/EquipmentEvaluation")
public class SecurityEquipmentEvaluationController extends BaseController
{
    @Autowired
    private ISecurityEquipmentEvaluationService securityEquipmentEvaluationService;

    /**
     * 查询安全设备设施现状评价列表
     */
    @PreAuthorize("@ss.hasPermi('security:EquipmentEvaluation:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEquipmentEvaluation securityEquipmentEvaluation)
    {
        startPage();
        List<SecurityEquipmentEvaluation> list = securityEquipmentEvaluationService.selectSecurityEquipmentEvaluationList(securityEquipmentEvaluation);
        return getDataTable(list);
    }

    /**
     * 导出安全设备设施现状评价列表
     */
    @PreAuthorize("@ss.hasPermi('security:EquipmentEvaluation:export')")
    @Log(title = "安全设备设施现状评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEquipmentEvaluation securityEquipmentEvaluation)
    {
        List<SecurityEquipmentEvaluation> list = securityEquipmentEvaluationService.selectSecurityEquipmentEvaluationList(securityEquipmentEvaluation);
        ExcelUtil<SecurityEquipmentEvaluation> util = new ExcelUtil<SecurityEquipmentEvaluation>(SecurityEquipmentEvaluation.class);
        util.exportExcel(response, list, "安全设备设施现状评价数据");
    }

    /**
     * 获取安全设备设施现状评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:EquipmentEvaluation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityEquipmentEvaluationService.selectSecurityEquipmentEvaluationById(id));
    }

    /**
     * 新增安全设备设施现状评价
     */
    @PreAuthorize("@ss.hasPermi('security:EquipmentEvaluation:add')")
    @Log(title = "安全设备设施现状评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEquipmentEvaluation securityEquipmentEvaluation)
    {
        return toAjax(securityEquipmentEvaluationService.insertSecurityEquipmentEvaluation(securityEquipmentEvaluation));
    }

    /**
     * 修改安全设备设施现状评价
     */
    @PreAuthorize("@ss.hasPermi('security:EquipmentEvaluation:edit')")
    @Log(title = "安全设备设施现状评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEquipmentEvaluation securityEquipmentEvaluation)
    {
        return toAjax(securityEquipmentEvaluationService.updateSecurityEquipmentEvaluation(securityEquipmentEvaluation));
    }

    /**
     * 删除安全设备设施现状评价
     */
    @PreAuthorize("@ss.hasPermi('security:EquipmentEvaluation:remove')")
    @Log(title = "安全设备设施现状评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityEquipmentEvaluationService.deleteSecurityEquipmentEvaluationByIds(ids));
    }
}
