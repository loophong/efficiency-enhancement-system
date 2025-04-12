package com.ruoyi.security.controller;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.security.service.impl.SecurityEnvironmentalMonitoringReportServiceImpl;
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
import com.ruoyi.security.domain.SecurityControlledDocumentDistributionDirectory;
import com.ruoyi.security.service.ISecurityControlledDocumentDistributionDirectoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 受控文件发放目录Controller
 * 
 * @author wang
 * @date 2025-03-02
 */
@RestController
@Slf4j
@RequestMapping("/security/controlleddirectory")
public class SecurityControlledDocumentDistributionDirectoryController extends BaseController
{
    @Autowired
    private ISecurityControlledDocumentDistributionDirectoryService securityControlledDocumentDistributionDirectoryService;
    @Autowired
    private SecurityEnvironmentalMonitoringReportServiceImpl securityEnvironmentalMonitoringReportServiceImpl;

    /**
     * 查询受控文件发放目录列表
     */
    @PreAuthorize("@ss.hasPermi('security:controlleddirectory:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory)
    {
        startPage();
        List<SecurityControlledDocumentDistributionDirectory> list = securityControlledDocumentDistributionDirectoryService.selectSecurityControlledDocumentDistributionDirectoryList(securityControlledDocumentDistributionDirectory);
        return getDataTable(list);
    }

    /**
     * 导出受控文件发放目录列表
     */
    @PreAuthorize("@ss.hasPermi('security:controlleddirectory:export')")
    @Log(title = "受控文件发放目录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory)
    {
        List<SecurityControlledDocumentDistributionDirectory> list = securityControlledDocumentDistributionDirectoryService.selectSecurityControlledDocumentDistributionDirectoryList(securityControlledDocumentDistributionDirectory);
        ExcelUtil<SecurityControlledDocumentDistributionDirectory> util = new ExcelUtil<SecurityControlledDocumentDistributionDirectory>(SecurityControlledDocumentDistributionDirectory.class);
        util.exportExcel(response, list, "受控文件发放目录数据");
    }

    /**
     * 获取受控文件发放目录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:controlleddirectory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityControlledDocumentDistributionDirectoryService.selectSecurityControlledDocumentDistributionDirectoryById(id));
    }

    /**
     * 新增受控文件发放目录
     */
    @PreAuthorize("@ss.hasPermi('security:controlleddirectory:add')")
    @Log(title = "受控文件发放目录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory)
    {
        return toAjax(securityControlledDocumentDistributionDirectoryService.insertSecurityControlledDocumentDistributionDirectory(securityControlledDocumentDistributionDirectory));
    }

    /**
     * 修改受控文件发放目录
     */
    @PreAuthorize("@ss.hasPermi('security:controlleddirectory:edit')")
    @Log(title = "受控文件发放目录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory)
    {
        return toAjax(securityControlledDocumentDistributionDirectoryService.updateSecurityControlledDocumentDistributionDirectory(securityControlledDocumentDistributionDirectory));
    }

    /**
     * 删除受控文件发放目录
     */
    @PreAuthorize("@ss.hasPermi('security:controlleddirectory:remove')")
    @Log(title = "受控文件发放目录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityControlledDocumentDistributionDirectoryService.deleteSecurityControlledDocumentDistributionDirectoryByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('production:historyOrder:import')")
    @PostMapping("/import")
    public void importTable( MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            securityControlledDocumentDistributionDirectoryService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }
}
