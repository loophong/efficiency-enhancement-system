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
import com.ruoyi.security.domain.SecurityEnvironmentTableInfo;
import com.ruoyi.security.service.ISecurityEnvironmentTableInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境识别表格元数据Controller
 * 
 */
@RestController
@RequestMapping("/security/tableinfo")
public class SecurityEnvironmentTableInfoController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentTableInfoService securityEnvironmentTableInfoService;

    /**
     * 查询环境识别表格元数据列表
     */
    @PreAuthorize("@ss.hasPermi('security:tableinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentTableInfo securityEnvironmentTableInfo)
    {
        startPage();
        List<SecurityEnvironmentTableInfo> list = securityEnvironmentTableInfoService.selectSecurityEnvironmentTableInfoList(securityEnvironmentTableInfo);
        return getDataTable(list);
    }

    /**
     * 导出环境识别表格元数据列表
     */
    @PreAuthorize("@ss.hasPermi('security:tableinfo:export')")
    @Log(title = "环境识别表格元数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentTableInfo securityEnvironmentTableInfo)
    {
        List<SecurityEnvironmentTableInfo> list = securityEnvironmentTableInfoService.selectSecurityEnvironmentTableInfoList(securityEnvironmentTableInfo);
        ExcelUtil<SecurityEnvironmentTableInfo> util = new ExcelUtil<SecurityEnvironmentTableInfo>(SecurityEnvironmentTableInfo.class);
        util.exportExcel(response, list, "环境识别表格元数据数据");
    }

    /**
     * 获取环境识别表格元数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:tableinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEnvironmentTableInfoService.selectSecurityEnvironmentTableInfoById(id));
    }

    /**
     * 新增环境识别表格元数据
     */
    @PreAuthorize("@ss.hasPermi('security:tableinfo:add')")
    @Log(title = "环境识别表格元数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentTableInfo securityEnvironmentTableInfo)
    {
        return toAjax(securityEnvironmentTableInfoService.insertSecurityEnvironmentTableInfo(securityEnvironmentTableInfo));
    }

    /**
     * 修改环境识别表格元数据
     */
    @PreAuthorize("@ss.hasPermi('security:tableinfo:edit')")
    @Log(title = "环境识别表格元数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentTableInfo securityEnvironmentTableInfo)
    {
        return toAjax(securityEnvironmentTableInfoService.updateSecurityEnvironmentTableInfo(securityEnvironmentTableInfo));
    }

    /**
     * 删除环境识别表格元数据
     */
    @PreAuthorize("@ss.hasPermi('security:tableinfo:remove')")
    @Log(title = "环境识别表格元数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEnvironmentTableInfoService.deleteSecurityEnvironmentTableInfoByIds(ids));
    }
} 