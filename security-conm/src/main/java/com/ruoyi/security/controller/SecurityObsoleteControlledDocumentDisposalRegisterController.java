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
import com.ruoyi.security.domain.SecurityObsoleteControlledDocumentDisposalRegister;
import com.ruoyi.security.service.ISecurityObsoleteControlledDocumentDisposalRegisterService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 作废受控文件收回销毁登记Controller
 * 
 * @author wang
 * @date 2025-03-02
 */
@RestController
@RequestMapping("/security/obsoleteregister")
public class SecurityObsoleteControlledDocumentDisposalRegisterController extends BaseController
{
    @Autowired
    private ISecurityObsoleteControlledDocumentDisposalRegisterService securityObsoleteControlledDocumentDisposalRegisterService;

    /**
     * 查询作废受控文件收回销毁登记列表
     */
    @PreAuthorize("@ss.hasPermi('security:obsoleteregister:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister)
    {
        startPage();
        List<SecurityObsoleteControlledDocumentDisposalRegister> list = securityObsoleteControlledDocumentDisposalRegisterService.selectSecurityObsoleteControlledDocumentDisposalRegisterList(securityObsoleteControlledDocumentDisposalRegister);
        return getDataTable(list);
    }

    /**
     * 导出作废受控文件收回销毁登记列表
     */
    @PreAuthorize("@ss.hasPermi('security:obsoleteregister:export')")
    @Log(title = "作废受控文件收回销毁登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister)
    {
        List<SecurityObsoleteControlledDocumentDisposalRegister> list = securityObsoleteControlledDocumentDisposalRegisterService.selectSecurityObsoleteControlledDocumentDisposalRegisterList(securityObsoleteControlledDocumentDisposalRegister);
        ExcelUtil<SecurityObsoleteControlledDocumentDisposalRegister> util = new ExcelUtil<SecurityObsoleteControlledDocumentDisposalRegister>(SecurityObsoleteControlledDocumentDisposalRegister.class);
        util.exportExcel(response, list, "作废受控文件收回销毁登记数据");
    }

    /**
     * 获取作废受控文件收回销毁登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:obsoleteregister:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityObsoleteControlledDocumentDisposalRegisterService.selectSecurityObsoleteControlledDocumentDisposalRegisterById(id));
    }

    /**
     * 新增作废受控文件收回销毁登记
     */
    @PreAuthorize("@ss.hasPermi('security:obsoleteregister:add')")
    @Log(title = "作废受控文件收回销毁登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister)
    {
        return toAjax(securityObsoleteControlledDocumentDisposalRegisterService.insertSecurityObsoleteControlledDocumentDisposalRegister(securityObsoleteControlledDocumentDisposalRegister));
    }

    /**
     * 修改作废受控文件收回销毁登记
     */
    @PreAuthorize("@ss.hasPermi('security:obsoleteregister:edit')")
    @Log(title = "作废受控文件收回销毁登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister)
    {
        return toAjax(securityObsoleteControlledDocumentDisposalRegisterService.updateSecurityObsoleteControlledDocumentDisposalRegister(securityObsoleteControlledDocumentDisposalRegister));
    }

    /**
     * 删除作废受控文件收回销毁登记
     */
    @PreAuthorize("@ss.hasPermi('security:obsoleteregister:remove')")
    @Log(title = "作废受控文件收回销毁登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityObsoleteControlledDocumentDisposalRegisterService.deleteSecurityObsoleteControlledDocumentDisposalRegisterByIds(ids));
    }
}
