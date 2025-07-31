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
import com.ruoyi.security.domain.SecurityPeopleList;
import com.ruoyi.security.service.ISecurityPeopleListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 班组人员清单Controller
 * 
 * @author wang
 * @date 2025-07-21
 */
@RestController
@RequestMapping("/security/PeopleList")
public class SecurityPeopleListController extends BaseController
{
    @Autowired
    private ISecurityPeopleListService securityPeopleListService;

    /**
     * 查询班组人员清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:PeopleList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityPeopleList securityPeopleList)
    {
        startPage();
        List<SecurityPeopleList> list = securityPeopleListService.selectSecurityPeopleListList(securityPeopleList);
        return getDataTable(list);
    }

    /**
     * 导出班组人员清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:PeopleList:export')")
    @Log(title = "班组人员清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityPeopleList securityPeopleList)
    {
        List<SecurityPeopleList> list = securityPeopleListService.selectSecurityPeopleListList(securityPeopleList);
        ExcelUtil<SecurityPeopleList> util = new ExcelUtil<SecurityPeopleList>(SecurityPeopleList.class);
        util.exportExcel(response, list, "班组人员清单数据");
    }

    /**
     * 获取班组人员清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:PeopleList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityPeopleListService.selectSecurityPeopleListById(id));
    }

    /**
     * 新增班组人员清单
     */
    @PreAuthorize("@ss.hasPermi('security:PeopleList:add')")
    @Log(title = "班组人员清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityPeopleList securityPeopleList)
    {
        return toAjax(securityPeopleListService.insertSecurityPeopleList(securityPeopleList));
    }

    /**
     * 修改班组人员清单
     */
    @PreAuthorize("@ss.hasPermi('security:PeopleList:edit')")
    @Log(title = "班组人员清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityPeopleList securityPeopleList)
    {
        return toAjax(securityPeopleListService.updateSecurityPeopleList(securityPeopleList));
    }

    /**
     * 删除班组人员清单
     */
    @PreAuthorize("@ss.hasPermi('security:PeopleList:remove')")
    @Log(title = "班组人员清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityPeopleListService.deleteSecurityPeopleListByIds(ids));
    }

    /**
     * 导入班组人员清单数据
     */
    @Log(title = "班组人员清单", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('security:PeopleList:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SecurityPeopleList> util = new ExcelUtil<SecurityPeopleList>(SecurityPeopleList.class);
        List<SecurityPeopleList> peopleListList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = securityPeopleListService.importPeopleList(peopleListList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载班组人员清单导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecurityPeopleList> util = new ExcelUtil<SecurityPeopleList>(SecurityPeopleList.class);
        util.importTemplateExcel(response, "班组人员清单数据");
    }

    /**
     * 根据关联ID查询班组人员清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:PeopleList:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public TableDataInfo listByRelatedId(@PathVariable String relatedId)
    {
        SecurityPeopleList securityPeopleList = new SecurityPeopleList();
        securityPeopleList.setRelatedId(relatedId);
        List<SecurityPeopleList> list = securityPeopleListService.selectSecurityPeopleListList(securityPeopleList);
        return getDataTable(list);
    }
}
