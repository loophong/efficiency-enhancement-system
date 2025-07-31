package com.ruoyi.security.controller;

import java.util.List;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(SecurityCompanyImportantEnvironmentalFactorsController.class);
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
        // 对数据按照环境因素排序，便于合并单元格
        list.sort((a, b) -> {
            if (a.getEnvironmentalFactor() == null || b.getEnvironmentalFactor() == null) {
                return 0;
            }
            return a.getEnvironmentalFactor().compareTo(b.getEnvironmentalFactor());
        });
        ExcelUtil<SecurityCompanyImportantEnvironmentalFactors> util = new ExcelUtil<SecurityCompanyImportantEnvironmentalFactors>(SecurityCompanyImportantEnvironmentalFactors.class);
        util.exportExcel(response, list, "公司级重要环境因素清单数据");
    }
    
    /**
     * 导入公司级重要环境因素清单数据
     */
    @PreAuthorize("@ss.hasPermi('security:importantfactors:import')")
    @Log(title = "公司级重要环境因素清单", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        String message = securityCompanyImportantEnvironmentalFactorsService.importData(file, updateSupport);
        return success(message);
    }
    
    /**
     * 下载公司级重要环境因素清单导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:importantfactors:export')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        try {
            // 设置响应头信息
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = "公司级重要环境因素清单导入模板.xlsx";
            // 使用URLEncoder处理文件名，避免中文乱码
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            // 禁止缓存
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            
            ExcelUtil<SecurityCompanyImportantEnvironmentalFactors> util = new ExcelUtil<SecurityCompanyImportantEnvironmentalFactors>(SecurityCompanyImportantEnvironmentalFactors.class);
            util.importTemplateExcel(response, "公司级重要环境因素清单数据");
        } catch (Exception e) {
            log.error("下载模板失败", e);
        }
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
