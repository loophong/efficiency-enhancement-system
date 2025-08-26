package com.ruoyi.security.controller;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 作废受控文件收回销毁登记Controller
 * 
 * @author wang
 * @date 2025-03-02
 */
@Slf4j
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

    @PreAuthorize("@ss.hasPermi('security:obsoleteregister:import')")
    @PostMapping("/import")
    public AjaxResult importTable( MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            securityObsoleteControlledDocumentDisposalRegisterService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile);
            return AjaxResult.success("导入成功");
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            return AjaxResult.error("读取 " + excelFile.getName() + " 文件失败: " + e.getMessage());
        }
    }

    /**
     * 下载作废受控文件收回销毁登记导入模板
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecurityObsoleteControlledDocumentDisposalRegister> util = new ExcelUtil<SecurityObsoleteControlledDocumentDisposalRegister>(SecurityObsoleteControlledDocumentDisposalRegister.class);
        util.importTemplateExcel(response, "作废受控文件收回销毁登记数据");
    }

    /**
     * 根据关联ID查询作废受控文件收回销毁登记列表
     */
    @PreAuthorize("@ss.hasPermi('security:obsoleteregister:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public TableDataInfo listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        startPage();
        SecurityObsoleteControlledDocumentDisposalRegister query = new SecurityObsoleteControlledDocumentDisposalRegister();
        query.setRelatedId(relatedId);
        List<SecurityObsoleteControlledDocumentDisposalRegister> list = securityObsoleteControlledDocumentDisposalRegisterService.selectSecurityObsoleteControlledDocumentDisposalRegisterList(query);
        return getDataTable(list);
    }
}
