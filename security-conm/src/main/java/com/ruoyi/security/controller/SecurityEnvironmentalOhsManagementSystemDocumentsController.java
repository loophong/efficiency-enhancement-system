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
import com.ruoyi.security.domain.SecurityEnvironmentalOhsManagementSystemDocuments;
import com.ruoyi.security.service.ISecurityEnvironmentalOhsManagementSystemDocumentsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 环境职业健康安全管理体系文件清单Controller
 * 
 * @author wang
 * @date 2025-03-02
 */
@Slf4j
@RestController
@RequestMapping("/security/OhsDocuments")
public class SecurityEnvironmentalOhsManagementSystemDocumentsController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentalOhsManagementSystemDocumentsService securityEnvironmentalOhsManagementSystemDocumentsService;

    /**
     * 查询环境职业健康安全管理体系文件清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:OhsDocuments:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments)
    {
        startPage();
        List<SecurityEnvironmentalOhsManagementSystemDocuments> list = securityEnvironmentalOhsManagementSystemDocumentsService.selectSecurityEnvironmentalOhsManagementSystemDocumentsList(securityEnvironmentalOhsManagementSystemDocuments);
        return getDataTable(list);
    }

    /**
     * 导出环境职业健康安全管理体系文件清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:OhsDocuments:export')")
    @Log(title = "环境职业健康安全管理体系文件清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments)
    {
        List<SecurityEnvironmentalOhsManagementSystemDocuments> list = securityEnvironmentalOhsManagementSystemDocumentsService.selectSecurityEnvironmentalOhsManagementSystemDocumentsList(securityEnvironmentalOhsManagementSystemDocuments);
        ExcelUtil<SecurityEnvironmentalOhsManagementSystemDocuments> util = new ExcelUtil<SecurityEnvironmentalOhsManagementSystemDocuments>(SecurityEnvironmentalOhsManagementSystemDocuments.class);
        util.exportExcel(response, list, "环境职业健康安全管理体系文件清单数据");
    }

    /**
     * 获取环境职业健康安全管理体系文件清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:OhsDocuments:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEnvironmentalOhsManagementSystemDocumentsService.selectSecurityEnvironmentalOhsManagementSystemDocumentsById(id));
    }

    /**
     * 新增环境职业健康安全管理体系文件清单
     */
    @PreAuthorize("@ss.hasPermi('security:OhsDocuments:add')")
    @Log(title = "环境职业健康安全管理体系文件清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments)
    {
        return toAjax(securityEnvironmentalOhsManagementSystemDocumentsService.insertSecurityEnvironmentalOhsManagementSystemDocuments(securityEnvironmentalOhsManagementSystemDocuments));
    }

    /**
     * 修改环境职业健康安全管理体系文件清单
     */
    @PreAuthorize("@ss.hasPermi('security:OhsDocuments:edit')")
    @Log(title = "环境职业健康安全管理体系文件清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments)
    {
        return toAjax(securityEnvironmentalOhsManagementSystemDocumentsService.updateSecurityEnvironmentalOhsManagementSystemDocuments(securityEnvironmentalOhsManagementSystemDocuments));
    }

    /**
     * 删除环境职业健康安全管理体系文件清单
     */
    @PreAuthorize("@ss.hasPermi('security:OhsDocuments:remove')")
    @Log(title = "环境职业健康安全管理体系文件清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEnvironmentalOhsManagementSystemDocumentsService.deleteSecurityEnvironmentalOhsManagementSystemDocumentsByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('production:historyOrder:import')")
    @PostMapping("/import")
    public void importTable( MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            securityEnvironmentalOhsManagementSystemDocumentsService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }
}
