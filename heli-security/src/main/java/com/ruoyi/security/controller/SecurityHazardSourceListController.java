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
import com.ruoyi.security.domain.SecurityHazardSourceList;
import com.ruoyi.security.service.ISecurityHazardSourceListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.security.utils.HazardSourceExcelUtil;

/**
 * 危险源清单Controller
 * 
 * @author wang
 * @date 2025-03-26
 */
@RestController
@RequestMapping("/security/risklist")
public class SecurityHazardSourceListController extends BaseController
{
    @Autowired
    private ISecurityHazardSourceListService securityHazardSourceListService;

    /**
     * 查询危险源清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:risklist:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityHazardSourceList securityHazardSourceList)
    {
        startPage();
        List<SecurityHazardSourceList> list = securityHazardSourceListService.selectSecurityHazardSourceListList(securityHazardSourceList);
        return getDataTable(list);
    }

    /**
     * 导出危险源清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:risklist:export')")
    @Log(title = "危险源清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityHazardSourceList securityHazardSourceList)
    {
        List<SecurityHazardSourceList> list = securityHazardSourceListService.selectSecurityHazardSourceListList(securityHazardSourceList);
        // 使用自定义的Excel工具类，支持合并单元格
        HazardSourceExcelUtil util = new HazardSourceExcelUtil();
        util.exportExcel(response, list, "危险源清单数据");
    }

    /**
     * 获取危险源清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:risklist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityHazardSourceListService.selectSecurityHazardSourceListById(id));
    }

    /**
     * 新增危险源清单
     */
    @PreAuthorize("@ss.hasPermi('security:risklist:add')")
    @Log(title = "危险源清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityHazardSourceList securityHazardSourceList)
    {
        return toAjax(securityHazardSourceListService.insertSecurityHazardSourceList(securityHazardSourceList));
    }

    /**
     * 修改危险源清单
     */
    @PreAuthorize("@ss.hasPermi('security:risklist:edit')")
    @Log(title = "危险源清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityHazardSourceList securityHazardSourceList)
    {
        return toAjax(securityHazardSourceListService.updateSecurityHazardSourceList(securityHazardSourceList));
    }

    /**
     * 删除危险源清单
     */
    @PreAuthorize("@ss.hasPermi('security:risklist:remove')")
    @Log(title = "危险源清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityHazardSourceListService.deleteSecurityHazardSourceListByIds(ids));
    }
    
    /**
     * 导入危险源清单数据
     */
    @PreAuthorize("@ss.hasPermi('security:risklist:import')")
    @Log(title = "危险源清单", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        String message = securityHazardSourceListService.importData(file, updateSupport);
        return success(message);
    }
    
    /**
     * 下载危险源清单导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:risklist:import')")
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        // 使用自定义的Excel工具类，支持合并单元格
        HazardSourceExcelUtil util = new HazardSourceExcelUtil();
        util.importTemplateExcel(response, "危险源清单数据");
    }
}
