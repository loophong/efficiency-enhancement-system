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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.annotation.Anonymous;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecurityEmergencySuppliesLedger;
import com.ruoyi.security.service.ISecurityEmergencySuppliesLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 应急物资管理台帐Controller
 * 
 * @author wang
 * @date 2025-03-06
 */
@RestController
@RequestMapping("/security/emergencysuppliesledger")
public class SecurityEmergencySuppliesLedgerController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(SecurityEmergencySuppliesLedgerController.class);

    @Autowired
    private ISecurityEmergencySuppliesLedgerService securityEmergencySuppliesLedgerService;

    /**
     * 查询应急物资管理台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        startPage();
        List<SecurityEmergencySuppliesLedger> list = securityEmergencySuppliesLedgerService.selectSecurityEmergencySuppliesLedgerList(securityEmergencySuppliesLedger);
        return getDataTable(list);
    }

    /**
     * 导出应急物资管理台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:export')")
    @Log(title = "应急物资管理台帐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        List<SecurityEmergencySuppliesLedger> list = securityEmergencySuppliesLedgerService.selectSecurityEmergencySuppliesLedgerList(securityEmergencySuppliesLedger);
        ExcelUtil<SecurityEmergencySuppliesLedger> util = new ExcelUtil<SecurityEmergencySuppliesLedger>(SecurityEmergencySuppliesLedger.class);
        util.exportExcel(response, list, "应急物资管理台帐数据");
    }

    /**
     * 获取应急物资管理台帐详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEmergencySuppliesLedgerService.selectSecurityEmergencySuppliesLedgerById(id));
    }

    /**
     * 新增应急物资管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:add')")
    @Log(title = "应急物资管理台帐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        return toAjax(securityEmergencySuppliesLedgerService.insertSecurityEmergencySuppliesLedger(securityEmergencySuppliesLedger));
    }

    /**
     * 修改应急物资管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:edit')")
    @Log(title = "应急物资管理台帐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        return toAjax(securityEmergencySuppliesLedgerService.updateSecurityEmergencySuppliesLedger(securityEmergencySuppliesLedger));
    }

    /**
     * 删除应急物资管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:remove')")
    @Log(title = "应急物资管理台帐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEmergencySuppliesLedgerService.deleteSecurityEmergencySuppliesLedgerByIds(ids));
    }

    /**
     * 获取应急物资管理台帐导入模板
     */
    @Anonymous
    @RequestMapping(value = "/importTemplate", method = {RequestMethod.GET, RequestMethod.POST})
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecurityEmergencySuppliesLedger> util = new ExcelUtil<SecurityEmergencySuppliesLedger>(SecurityEmergencySuppliesLedger.class);
        util.importTemplateExcel(response, "应急物资管理台帐数据");
    }

    /**
     * 导入应急物资管理台帐数据
     */
    @Log(title = "应急物资管理台帐", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        try {
            ExcelUtil<SecurityEmergencySuppliesLedger> util = new ExcelUtil<SecurityEmergencySuppliesLedger>(SecurityEmergencySuppliesLedger.class);
            List<SecurityEmergencySuppliesLedger> suppliesLedgerList = util.importExcel(file.getInputStream());

            if (suppliesLedgerList.isEmpty()) {
                return error("导入数据为空，请检查Excel文件格式");
            }

            String operName = SecurityUtils.getUsername();
            String message = securityEmergencySuppliesLedgerService.importSecurityEmergencySuppliesLedger(suppliesLedgerList, updateSupport, operName);
            return success(message);

        } catch (Exception e) {
            log.error("导入应急物资管理台帐数据失败", e);
            return error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 根据关联ID查询应急物资管理台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:emergencysuppliesledger:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public TableDataInfo listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        List<SecurityEmergencySuppliesLedger> list = securityEmergencySuppliesLedgerService.selectSecurityEmergencySuppliesLedgerByRelatedId(relatedId);
        return getDataTable(list);
    }
}
