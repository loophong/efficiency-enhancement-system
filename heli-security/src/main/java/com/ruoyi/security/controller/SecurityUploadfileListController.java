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
import com.ruoyi.security.domain.SecurityUploadfileList;
import com.ruoyi.security.service.ISecurityUploadfileListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 制度明细Controller
 * 
 * @author wang
 * @date 2025-03-26
 */
@RestController
@RequestMapping("/security/fileList")
public class SecurityUploadfileListController extends BaseController
{
    @Autowired
    private ISecurityUploadfileListService securityUploadfileListService;

    /**
     * 查询制度明细列表
     */
    @PreAuthorize("@ss.hasPermi('security:fileList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityUploadfileList securityUploadfileList)
    {
        startPage();
        List<SecurityUploadfileList> list = securityUploadfileListService.selectSecurityUploadfileListList(securityUploadfileList);
        return getDataTable(list);
    }

    /**
     * 导出制度明细列表
     */
    @PreAuthorize("@ss.hasPermi('security:fileList:export')")
    @Log(title = "制度明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityUploadfileList securityUploadfileList)
    {
        List<SecurityUploadfileList> list = securityUploadfileListService.selectSecurityUploadfileListList(securityUploadfileList);
        ExcelUtil<SecurityUploadfileList> util = new ExcelUtil<SecurityUploadfileList>(SecurityUploadfileList.class);
        util.exportExcel(response, list, "制度明细数据");
    }

    /**
     * 获取制度明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:fileList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityUploadfileListService.selectSecurityUploadfileListById(id));
    }

    /**
     * 新增制度明细
     */
    @PreAuthorize("@ss.hasPermi('security:fileList:add')")
    @Log(title = "制度明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityUploadfileList securityUploadfileList)
    {
        return toAjax(securityUploadfileListService.insertSecurityUploadfileList(securityUploadfileList));
    }

    /**
     * 修改制度明细
     */
    @PreAuthorize("@ss.hasPermi('security:fileList:edit')")
    @Log(title = "制度明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityUploadfileList securityUploadfileList)
    {
        return toAjax(securityUploadfileListService.updateSecurityUploadfileList(securityUploadfileList));
    }

    /**
     * 删除制度明细
     */
    @PreAuthorize("@ss.hasPermi('security:fileList:remove')")
    @Log(title = "制度明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityUploadfileListService.deleteSecurityUploadfileListByIds(ids));
    }
}
