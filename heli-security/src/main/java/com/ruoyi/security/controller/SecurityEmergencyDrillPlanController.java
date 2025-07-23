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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.SecurityUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.ruoyi.security.listener.SecurityEmergencyDrillPlanListener;
import com.ruoyi.security.utils.EmergencyDrillPlanExcelUtil;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecurityEmergencyDrillPlan;
import com.ruoyi.security.service.ISecurityEmergencyDrillPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 应急演练计划Controller
 * 
 * @author wang
 * @date 2025-03-06
 */
@RestController
@RequestMapping("/security/emergencydrillplan")
public class SecurityEmergencyDrillPlanController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(SecurityEmergencyDrillPlanController.class);

    @Autowired
    private ISecurityEmergencyDrillPlanService securityEmergencyDrillPlanService;

    /**
     * 查询应急演练计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        startPage();
        List<SecurityEmergencyDrillPlan> list = securityEmergencyDrillPlanService.selectSecurityEmergencyDrillPlanList(securityEmergencyDrillPlan);
        return getDataTable(list);
    }

    /**
     * 导出应急演练计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:export')")
    @Log(title = "应急演练计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        List<SecurityEmergencyDrillPlan> list = securityEmergencyDrillPlanService.selectSecurityEmergencyDrillPlanList(securityEmergencyDrillPlan);
        ExcelUtil<SecurityEmergencyDrillPlan> util = new ExcelUtil<SecurityEmergencyDrillPlan>(SecurityEmergencyDrillPlan.class);
        util.exportExcel(response, list, "应急演练计划数据");
    }

    /**
     * 获取应急演练计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEmergencyDrillPlanService.selectSecurityEmergencyDrillPlanById(id));
    }

    /**
     * 新增应急演练计划
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:add')")
    @Log(title = "应急演练计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        return toAjax(securityEmergencyDrillPlanService.insertSecurityEmergencyDrillPlan(securityEmergencyDrillPlan));
    }

    /**
     * 修改应急演练计划
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:edit')")
    @Log(title = "应急演练计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEmergencyDrillPlan securityEmergencyDrillPlan)
    {
        return toAjax(securityEmergencyDrillPlanService.updateSecurityEmergencyDrillPlan(securityEmergencyDrillPlan));
    }

    /**
     * 删除应急演练计划
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:remove')")
    @Log(title = "应急演练计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEmergencyDrillPlanService.deleteSecurityEmergencyDrillPlanByIds(ids));
    }

    /**
     * 获取应急演练计划导入模板
     */
    @RequestMapping(value = "/importTemplate", method = {RequestMethod.GET, RequestMethod.POST})
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecurityEmergencyDrillPlan> util = new ExcelUtil<SecurityEmergencyDrillPlan>(SecurityEmergencyDrillPlan.class);
        util.importTemplateExcel(response, "应急演练计划数据");
    }

    /**
     * 导入应急演练计划数据
     */
    @Log(title = "应急演练计划", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        try {
            // 使用标准的ExcelUtil处理
            ExcelUtil<SecurityEmergencyDrillPlan> util = new ExcelUtil<SecurityEmergencyDrillPlan>(SecurityEmergencyDrillPlan.class);
            List<SecurityEmergencyDrillPlan> drillPlanList = util.importExcel(file.getInputStream());

            if (drillPlanList.isEmpty()) {
                return error("导入数据为空，请检查Excel文件格式");
            }

            String operName = SecurityUtils.getUsername();
            String message = securityEmergencyDrillPlanService.importSecurityEmergencyDrillPlan(drillPlanList, updateSupport, operName);
            return success(message);

        } catch (IOException e) {
            log.error("导入应急演练计划数据失败", e);
            return error("导入失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("导入应急演练计划数据失败", e);
            return error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 根据关联ID查询应急演练计划列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencydrillplan:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public TableDataInfo listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        List<SecurityEmergencyDrillPlan> list = securityEmergencyDrillPlanService.selectSecurityEmergencyDrillPlanByRelatedId(relatedId);
        return getDataTable(list);
    }
}
