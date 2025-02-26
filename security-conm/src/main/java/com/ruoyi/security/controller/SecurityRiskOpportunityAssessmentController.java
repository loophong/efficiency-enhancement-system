package com.ruoyi.security.controller;

import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.security.service.ISecurityRequireExpectPartyService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecurityRiskOpportunityAssessment;
import com.ruoyi.security.service.ISecurityRiskOpportunityAssessmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 风险和机遇评估及控制措施Controller
 *
 * @author wang
 * @date 2025-02-23
 */
@RestController
@RequestMapping("/security/assessment")
public class SecurityRiskOpportunityAssessmentController extends BaseController
{
    @Autowired
    private ISecurityRiskOpportunityAssessmentService securityRiskOpportunityAssessmentService;

    /**
     * 查询风险和机遇评估及控制措施列表
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        startPage();
        List<SecurityRiskOpportunityAssessment> list = securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentList(securityRiskOpportunityAssessment);
        return getDataTable(list);
    }

    /**
     * 导出风险和机遇评估及控制措施列表
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:export')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        List<SecurityRiskOpportunityAssessment> list = securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentList(securityRiskOpportunityAssessment);
        ExcelUtil<SecurityRiskOpportunityAssessment> util = new ExcelUtil<SecurityRiskOpportunityAssessment>(SecurityRiskOpportunityAssessment.class);
        util.exportExcel(response, list, "风险和机遇评估及控制措施数据");
    }

    /**
     * 获取风险和机遇评估及控制措施详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentById(id));
    }

    /**
     * 新增风险和机遇评估及控制措施
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:add')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment) {
        securityRiskOpportunityAssessmentService.insertSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment);
        /**
         * 发送审批
         *
         */

//        approvalTable;
//        送审人，送审时间；审批人；审批时间；审批状态；
//        审批的表格； securityRiskOpportunityAssessment.id

        return success();
    }

    /**
     * 修改风险和机遇评估及控制措施
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:edit')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return toAjax(securityRiskOpportunityAssessmentService.updateSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment));
    }

    /**
     * 删除风险和机遇评估及控制措施
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:remove')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityRiskOpportunityAssessmentService.deleteSecurityRiskOpportunityAssessmentByIds(ids));
    }
    /**
     * 导入风险和机遇评估及控制措施
     */
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PostMapping  ("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SecurityRiskOpportunityAssessment> util = new ExcelUtil<SecurityRiskOpportunityAssessment>(SecurityRiskOpportunityAssessment.class);
        List<SecurityRiskOpportunityAssessment> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = getLoginUser();
        String operName = loginUser.getUsername();
        String message = securityRiskOpportunityAssessmentService .importsalary(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public AjaxResult importTemplate(@RequestParam(value = "filePath", required = false) String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return AjaxResult.error("文件路径参数 'filePath' 未提供");
        }
        try {
            logger.info("开始导入模板");

            // 创建 ExcelUtil 实例
            ExcelUtil<SecurityRiskOpportunityAssessment> util = new ExcelUtil<>(SecurityRiskOpportunityAssessment.class);

            // 调用 importTemplateExcel 方法
            AjaxResult result = util.importTemplateExcel(filePath);

            logger.info("模板导入成功");
            return result;
        } catch (Exception e) {
            logger.error("模板导入失败", e);
            return AjaxResult.error("模板导入失败: " + e.getMessage());
        }
    }

}
