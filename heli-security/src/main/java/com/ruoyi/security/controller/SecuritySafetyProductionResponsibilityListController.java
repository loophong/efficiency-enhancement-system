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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.annotation.Anonymous;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecuritySafetyProductionResponsibilityList;
import com.ruoyi.security.service.ISecuritySafetyProductionResponsibilityListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全生产责任制清单Controller
 * 
 * @author wang
 * @date 2025-07-18
 */
@RestController
@RequestMapping("/security/ProductionResponsibilityList")
public class SecuritySafetyProductionResponsibilityListController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(SecuritySafetyProductionResponsibilityListController.class);

    @Autowired
    private ISecuritySafetyProductionResponsibilityListService securitySafetyProductionResponsibilityListService;

    /**
     * 查询安全生产责任制清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        startPage();
        List<SecuritySafetyProductionResponsibilityList> list = securitySafetyProductionResponsibilityListService.selectSecuritySafetyProductionResponsibilityListList(securitySafetyProductionResponsibilityList);
        return getDataTable(list);
    }

    /**
     * 导出安全生产责任制清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:export')")
    @Log(title = "安全生产责任制清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        List<SecuritySafetyProductionResponsibilityList> list = securitySafetyProductionResponsibilityListService.selectSecuritySafetyProductionResponsibilityListList(securitySafetyProductionResponsibilityList);
        ExcelUtil<SecuritySafetyProductionResponsibilityList> util = new ExcelUtil<SecuritySafetyProductionResponsibilityList>(SecuritySafetyProductionResponsibilityList.class);
        util.exportExcel(response, list, "安全生产责任制清单数据");
    }

    /**
     * 获取安全生产责任制清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securitySafetyProductionResponsibilityListService.selectSecuritySafetyProductionResponsibilityListById(id));
    }

    /**
     * 新增安全生产责任制清单
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:add')")
    @Log(title = "安全生产责任制清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        return toAjax(securitySafetyProductionResponsibilityListService.insertSecuritySafetyProductionResponsibilityList(securitySafetyProductionResponsibilityList));
    }

    /**
     * 修改安全生产责任制清单
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:edit')")
    @Log(title = "安全生产责任制清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        return toAjax(securitySafetyProductionResponsibilityListService.updateSecuritySafetyProductionResponsibilityList(securitySafetyProductionResponsibilityList));
    }

    /**
     * 删除安全生产责任制清单
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:remove')")
    @Log(title = "安全生产责任制清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securitySafetyProductionResponsibilityListService.deleteSecuritySafetyProductionResponsibilityListByIds(ids));
    }

    /**
     * 获取安全生产责任制清单导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:import')")
    @RequestMapping(value = "/importTemplate", method = {RequestMethod.GET, RequestMethod.POST})
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecuritySafetyProductionResponsibilityList> util = new ExcelUtil<SecuritySafetyProductionResponsibilityList>(SecuritySafetyProductionResponsibilityList.class);
        util.importTemplateExcel(response, "安全生产责任制清单数据");
    }

    /**
     * 导入安全生产责任制清单数据
     */
    @Log(title = "安全生产责任制清单", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        try {
            ExcelUtil<SecuritySafetyProductionResponsibilityList> util = new ExcelUtil<SecuritySafetyProductionResponsibilityList>(SecuritySafetyProductionResponsibilityList.class);
            List<SecuritySafetyProductionResponsibilityList> responsibilityList = util.importExcel(file.getInputStream());

            if (responsibilityList.isEmpty()) {
                return error("导入数据为空，请检查Excel文件格式");
            }

            String operName = SecurityUtils.getUsername();
            String message = securitySafetyProductionResponsibilityListService.importSecuritySafetyProductionResponsibilityList(responsibilityList, updateSupport, operName);
            return success(message);

        } catch (Exception e) {
            log.error("导入安全生产责任制清单数据失败", e);
            return error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 根据关联ID查询安全生产责任制清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:ProductionResponsibilityList:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public TableDataInfo listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        List<SecuritySafetyProductionResponsibilityList> list = securitySafetyProductionResponsibilityListService.selectSecuritySafetyProductionResponsibilityListByRelatedId(relatedId);
        return getDataTable(list);
    }
}
