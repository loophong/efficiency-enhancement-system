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
import com.ruoyi.security.domain.SecuritySeasonList;
import com.ruoyi.security.service.ISecuritySeasonListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 季节性检查记录Controller
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@RestController
@RequestMapping("/security/SeasonList")
public class SecuritySeasonListController extends BaseController
{
    @Autowired
    private ISecuritySeasonListService securitySeasonListService;

    /**
     * 查询季节性检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:SeasonList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecuritySeasonList securitySeasonList)
    {
        startPage();
        List<SecuritySeasonList> list = securitySeasonListService.selectSecuritySeasonListList(securitySeasonList);
        return getDataTable(list);
    }

    /**
     * 导出季节性检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:SeasonList:export')")
    @Log(title = "季节性检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecuritySeasonList securitySeasonList)
    {
        List<SecuritySeasonList> list = securitySeasonListService.selectSecuritySeasonListList(securitySeasonList);
        ExcelUtil<SecuritySeasonList> util = new ExcelUtil<SecuritySeasonList>(SecuritySeasonList.class);
        util.exportExcel(response, list, "季节性检查记录数据");
    }

    /**
     * 获取季节性检查记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:SeasonList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securitySeasonListService.selectSecuritySeasonListById(id));
    }

    /**
     * 新增季节性检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:SeasonList:add')")
    @Log(title = "季节性检查记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecuritySeasonList securitySeasonList)
    {
        return toAjax(securitySeasonListService.insertSecuritySeasonList(securitySeasonList));
    }

    /**
     * 修改季节性检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:SeasonList:edit')")
    @Log(title = "季节性检查记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecuritySeasonList securitySeasonList)
    {
        return toAjax(securitySeasonListService.updateSecuritySeasonList(securitySeasonList));
    }

    /**
     * 删除季节性检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:SeasonList:remove')")
    @Log(title = "季节性检查记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securitySeasonListService.deleteSecuritySeasonListByIds(ids));
    }
}
