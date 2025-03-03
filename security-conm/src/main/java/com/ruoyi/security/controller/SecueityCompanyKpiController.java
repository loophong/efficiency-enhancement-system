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
import com.ruoyi.security.domain.SecueityCompanyKpi;
import com.ruoyi.security.service.ISecueityCompanyKpiService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公司KPIController
 * 
 * @author wang
 * @date 2025-02-27
 */
@RestController
@RequestMapping("/security/kpi")
public class SecueityCompanyKpiController extends BaseController
{
    @Autowired
    private ISecueityCompanyKpiService secueityCompanyKpiService;

    /**
     * 查询公司KPI列表
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecueityCompanyKpi secueityCompanyKpi)
    {
        startPage();
        List<SecueityCompanyKpi> list = secueityCompanyKpiService.selectSecueityCompanyKpiList(secueityCompanyKpi);
        return getDataTable(list);
    }

    /**
     * 导出公司KPI列表
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:export')")
    @Log(title = "公司KPI", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecueityCompanyKpi secueityCompanyKpi)
    {
        List<SecueityCompanyKpi> list = secueityCompanyKpiService.selectSecueityCompanyKpiList(secueityCompanyKpi);
        ExcelUtil<SecueityCompanyKpi> util = new ExcelUtil<SecueityCompanyKpi>(SecueityCompanyKpi.class);
        util.exportExcel(response, list, "公司KPI数据");
    }

    /**
     * 获取公司KPI详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(secueityCompanyKpiService.selectSecueityCompanyKpiById(id));
    }

    /**
     * 新增公司KPI
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:add')")
    @Log(title = "公司KPI", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecueityCompanyKpi secueityCompanyKpi)
    {
        return toAjax(secueityCompanyKpiService.insertSecueityCompanyKpi(secueityCompanyKpi));
    }

    /**
     * 修改公司KPI
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:edit')")
    @Log(title = "公司KPI", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecueityCompanyKpi secueityCompanyKpi)
    {
        return toAjax(secueityCompanyKpiService.updateSecueityCompanyKpi(secueityCompanyKpi));
    }

    /**
     * 删除公司KPI
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:remove')")
    @Log(title = "公司KPI", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(secueityCompanyKpiService.deleteSecueityCompanyKpiByIds(ids));
    }
}
