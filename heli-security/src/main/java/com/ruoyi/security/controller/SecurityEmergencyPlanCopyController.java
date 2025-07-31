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
import com.ruoyi.security.domain.SecurityEmergencyPlanCopy;
import com.ruoyi.security.service.ISecurityEmergencyPlanCopyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 风险和机遇评应对措施评审记录2Controller
 * 
 * @author wang
 * @date 2025-07-30
 */
@RestController
@RequestMapping("/security/copy222")
public class SecurityEmergencyPlanCopyController extends BaseController
{
    @Autowired
    private ISecurityEmergencyPlanCopyService securityEmergencyPlanCopyService;

    /**
     * 查询风险和机遇评应对措施评审记录2列表
     */
    @PreAuthorize("@ss.hasPermi('security:copy222:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEmergencyPlanCopy securityEmergencyPlanCopy)
    {
        startPage();
        List<SecurityEmergencyPlanCopy> list = securityEmergencyPlanCopyService.selectSecurityEmergencyPlanCopyList(securityEmergencyPlanCopy);
        return getDataTable(list);
    }

    /**
     * 导出风险和机遇评应对措施评审记录2列表
     */
    @PreAuthorize("@ss.hasPermi('security:copy222:export')")
    @Log(title = "风险和机遇评应对措施评审记录2", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEmergencyPlanCopy securityEmergencyPlanCopy)
    {
        List<SecurityEmergencyPlanCopy> list = securityEmergencyPlanCopyService.selectSecurityEmergencyPlanCopyList(securityEmergencyPlanCopy);
        ExcelUtil<SecurityEmergencyPlanCopy> util = new ExcelUtil<SecurityEmergencyPlanCopy>(SecurityEmergencyPlanCopy.class);
        util.exportExcel(response, list, "风险和机遇评应对措施评审记录2数据");
    }

    /**
     * 获取风险和机遇评应对措施评审记录2详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:copy222:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityEmergencyPlanCopyService.selectSecurityEmergencyPlanCopyById(id));
    }

    /**
     * 新增风险和机遇评应对措施评审记录2
     */
    @PreAuthorize("@ss.hasPermi('security:copy222:add')")
    @Log(title = "风险和机遇评应对措施评审记录2", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEmergencyPlanCopy securityEmergencyPlanCopy)
    {
        return toAjax(securityEmergencyPlanCopyService.insertSecurityEmergencyPlanCopy(securityEmergencyPlanCopy));
    }

    /**
     * 修改风险和机遇评应对措施评审记录2
     */
    @PreAuthorize("@ss.hasPermi('security:copy222:edit')")
    @Log(title = "风险和机遇评应对措施评审记录2", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEmergencyPlanCopy securityEmergencyPlanCopy)
    {
        return toAjax(securityEmergencyPlanCopyService.updateSecurityEmergencyPlanCopy(securityEmergencyPlanCopy));
    }

    /**
     * 删除风险和机遇评应对措施评审记录2
     */
    @PreAuthorize("@ss.hasPermi('security:copy222:remove')")
    @Log(title = "风险和机遇评应对措施评审记录2", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityEmergencyPlanCopyService.deleteSecurityEmergencyPlanCopyByIds(ids));
    }
}
