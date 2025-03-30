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
import com.ruoyi.security.domain.SecurityCompanyImportantEnvironmentalFactors;
import com.ruoyi.security.service.ISecurityCompanyImportantEnvironmentalFactorsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公司级重要环境因素清单Controller
 * 
 * @author wang
 * @date 2025-03-26
 */
@RestController
@RequestMapping("/security/importantfactors")
public class SecurityCompanyImportantEnvironmentalFactorsController extends BaseController
{
    @Autowired
    private ISecurityCompanyImportantEnvironmentalFactorsService securityCompanyImportantEnvironmentalFactorsService;

    /**
     * 查询公司级重要环境因素清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:importantfactors:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors)
    {
        startPage();
        List<SecurityCompanyImportantEnvironmentalFactors> list = securityCompanyImportantEnvironmentalFactorsService.selectSecurityCompanyImportantEnvironmentalFactorsList(securityCompanyImportantEnvironmentalFactors);
        return getDataTable(list);
    }

    /**
     * 导出公司级重要环境因素清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:importantfactors:export')")
    @Log(title = "公司级重要环境因素清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors)
    {
        List<SecurityCompanyImportantEnvironmentalFactors> list = securityCompanyImportantEnvironmentalFactorsService.selectSecurityCompanyImportantEnvironmentalFactorsList(securityCompanyImportantEnvironmentalFactors);
        ExcelUtil<SecurityCompanyImportantEnvironmentalFactors> util = new ExcelUtil<SecurityCompanyImportantEnvironmentalFactors>(SecurityCompanyImportantEnvironmentalFactors.class);
        util.exportExcel(response, list, "公司级重要环境因素清单数据");
    }

    /**
     * 获取公司级重要环境因素清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:importantfactors:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityCompanyImportantEnvironmentalFactorsService.selectSecurityCompanyImportantEnvironmentalFactorsById(id));
    }

    /**
     * 新增公司级重要环境因素清单
     */
    @PreAuthorize("@ss.hasPermi('security:importantfactors:add')")
    @Log(title = "公司级重要环境因素清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors)
    {
        return toAjax(securityCompanyImportantEnvironmentalFactorsService.insertSecurityCompanyImportantEnvironmentalFactors(securityCompanyImportantEnvironmentalFactors));
    }

    /**
     * 修改公司级重要环境因素清单
     */
    @PreAuthorize("@ss.hasPermi('security:importantfactors:edit')")
    @Log(title = "公司级重要环境因素清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors)
    {
        return toAjax(securityCompanyImportantEnvironmentalFactorsService.updateSecurityCompanyImportantEnvironmentalFactors(securityCompanyImportantEnvironmentalFactors));
    }

    /**
     * 删除公司级重要环境因素清单
     */
    @PreAuthorize("@ss.hasPermi('security:importantfactors:remove')")
    @Log(title = "公司级重要环境因素清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityCompanyImportantEnvironmentalFactorsService.deleteSecurityCompanyImportantEnvironmentalFactorsByIds(ids));
    }
}
