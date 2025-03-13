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
import com.ruoyi.security.domain.SecurityOccupationalHazardPositionPersonnelList;
import com.ruoyi.security.service.ISecurityOccupationalHazardPositionPersonnelListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危险职业岗位人员清单及管理台帐Controller
 * 
 * @author wang
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/security/OccupationaPersonnelList")
public class SecurityOccupationalHazardPositionPersonnelListController extends BaseController
{
    @Autowired
    private ISecurityOccupationalHazardPositionPersonnelListService securityOccupationalHazardPositionPersonnelListService;

    /**
     * 查询危险职业岗位人员清单及管理台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        startPage();
        List<SecurityOccupationalHazardPositionPersonnelList> list = securityOccupationalHazardPositionPersonnelListService.selectSecurityOccupationalHazardPositionPersonnelListList(securityOccupationalHazardPositionPersonnelList);
        return getDataTable(list);
    }

    /**
     * 导出危险职业岗位人员清单及管理台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:export')")
    @Log(title = "危险职业岗位人员清单及管理台帐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        List<SecurityOccupationalHazardPositionPersonnelList> list = securityOccupationalHazardPositionPersonnelListService.selectSecurityOccupationalHazardPositionPersonnelListList(securityOccupationalHazardPositionPersonnelList);
        ExcelUtil<SecurityOccupationalHazardPositionPersonnelList> util = new ExcelUtil<SecurityOccupationalHazardPositionPersonnelList>(SecurityOccupationalHazardPositionPersonnelList.class);
        util.exportExcel(response, list, "危险职业岗位人员清单及管理台帐数据");
    }

    /**
     * 获取危险职业岗位人员清单及管理台帐详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityOccupationalHazardPositionPersonnelListService.selectSecurityOccupationalHazardPositionPersonnelListById(id));
    }

    /**
     * 新增危险职业岗位人员清单及管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:add')")
    @Log(title = "危险职业岗位人员清单及管理台帐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        return toAjax(securityOccupationalHazardPositionPersonnelListService.insertSecurityOccupationalHazardPositionPersonnelList(securityOccupationalHazardPositionPersonnelList));
    }

    /**
     * 修改危险职业岗位人员清单及管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:edit')")
    @Log(title = "危险职业岗位人员清单及管理台帐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        return toAjax(securityOccupationalHazardPositionPersonnelListService.updateSecurityOccupationalHazardPositionPersonnelList(securityOccupationalHazardPositionPersonnelList));
    }

    /**
     * 删除危险职业岗位人员清单及管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:remove')")
    @Log(title = "危险职业岗位人员清单及管理台帐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityOccupationalHazardPositionPersonnelListService.deleteSecurityOccupationalHazardPositionPersonnelListByIds(ids));
    }
}
