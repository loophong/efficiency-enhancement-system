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
import com.ruoyi.security.domain.SecurityRelatedPartyLedger;
import com.ruoyi.security.service.ISecurityRelatedPartyLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 相关方管理台账Controller
 * 
 * @author wang
 * @date 2025-04-09
 */
@RestController
@RequestMapping("/security/relatedpartyledger")
public class SecurityRelatedPartyLedgerController extends BaseController
{
    @Autowired
    private ISecurityRelatedPartyLedgerService securityRelatedPartyLedgerService;

    /**
     * 查询相关方管理台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyledger:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRelatedPartyLedger securityRelatedPartyLedger)
    {
        startPage();
        List<SecurityRelatedPartyLedger> list = securityRelatedPartyLedgerService.selectSecurityRelatedPartyLedgerList(securityRelatedPartyLedger);
        return getDataTable(list);
    }

    /**
     * 导出相关方管理台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyledger:export')")
    @Log(title = "相关方管理台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRelatedPartyLedger securityRelatedPartyLedger)
    {
        List<SecurityRelatedPartyLedger> list = securityRelatedPartyLedgerService.selectSecurityRelatedPartyLedgerList(securityRelatedPartyLedger);
        ExcelUtil<SecurityRelatedPartyLedger> util = new ExcelUtil<SecurityRelatedPartyLedger>(SecurityRelatedPartyLedger.class);
        util.exportExcel(response, list, "相关方管理台账数据");
    }

    /**
     * 获取相关方管理台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyledger:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityRelatedPartyLedgerService.selectSecurityRelatedPartyLedgerById(id));
    }

    /**
     * 新增相关方管理台账
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyledger:add')")
    @Log(title = "相关方管理台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRelatedPartyLedger securityRelatedPartyLedger)
    {
        return toAjax(securityRelatedPartyLedgerService.insertSecurityRelatedPartyLedger(securityRelatedPartyLedger));
    }

    /**
     * 修改相关方管理台账
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyledger:edit')")
    @Log(title = "相关方管理台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRelatedPartyLedger securityRelatedPartyLedger)
    {
        return toAjax(securityRelatedPartyLedgerService.updateSecurityRelatedPartyLedger(securityRelatedPartyLedger));
    }

    /**
     * 删除相关方管理台账
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyledger:remove')")
    @Log(title = "相关方管理台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityRelatedPartyLedgerService.deleteSecurityRelatedPartyLedgerByIds(ids));
    }

    /**
     * 获取相关方管理台账导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecurityRelatedPartyLedger> util = new ExcelUtil<SecurityRelatedPartyLedger>(SecurityRelatedPartyLedger.class);
        util.importTemplateExcel(response, "相关方管理台账数据");
    }

    /**
     * 导入相关方管理台账数据
     */
    @Log(title = "相关方管理台账", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('security:relatedpartyledger:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SecurityRelatedPartyLedger> util = new ExcelUtil<SecurityRelatedPartyLedger>(SecurityRelatedPartyLedger.class);
        List<SecurityRelatedPartyLedger> relatedPartyLedgerList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = securityRelatedPartyLedgerService.importRelatedPartyLedger(relatedPartyLedgerList, updateSupport, operName);
        return success(message);
    }

    /**
     * 根据关联ID查询相关方管理台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:relatedpartyledger:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public TableDataInfo listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        List<SecurityRelatedPartyLedger> list = securityRelatedPartyLedgerService.selectSecurityRelatedPartyLedgerByRelatedId(relatedId);
        return getDataTable(list);
    }
}
