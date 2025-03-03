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
import com.ruoyi.security.domain.SecurityUnacceptableRiskList;
import com.ruoyi.security.service.ISecurityUnacceptableRiskListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 不可接受风险清单Controller
 * 
 * @author wang
 * @date 2025-03-01
 */
@RestController
@RequestMapping("/security/unacceptablerisklist")
public class SecurityUnacceptableRiskListController extends BaseController
{
    @Autowired
    private ISecurityUnacceptableRiskListService securityUnacceptableRiskListService;

    /**
     * 查询不可接受风险清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:unacceptablerisklist:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityUnacceptableRiskList securityUnacceptableRiskList)
    {
        startPage();
        List<SecurityUnacceptableRiskList> list = securityUnacceptableRiskListService.selectSecurityUnacceptableRiskListList(securityUnacceptableRiskList);
        return getDataTable(list);
    }

    /**
     * 导出不可接受风险清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:unacceptablerisklist:export')")
    @Log(title = "不可接受风险清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityUnacceptableRiskList securityUnacceptableRiskList)
    {
        List<SecurityUnacceptableRiskList> list = securityUnacceptableRiskListService.selectSecurityUnacceptableRiskListList(securityUnacceptableRiskList);
        ExcelUtil<SecurityUnacceptableRiskList> util = new ExcelUtil<SecurityUnacceptableRiskList>(SecurityUnacceptableRiskList.class);
        util.exportExcel(response, list, "不可接受风险清单数据");
    }

    /**
     * 获取不可接受风险清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:unacceptablerisklist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityUnacceptableRiskListService.selectSecurityUnacceptableRiskListById(id));
    }

    /**
     * 新增不可接受风险清单
     */
    @PreAuthorize("@ss.hasPermi('security:unacceptablerisklist:add')")
    @Log(title = "不可接受风险清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityUnacceptableRiskList securityUnacceptableRiskList)
    {
        return toAjax(securityUnacceptableRiskListService.insertSecurityUnacceptableRiskList(securityUnacceptableRiskList));
    }

    /**
     * 修改不可接受风险清单
     */
    @PreAuthorize("@ss.hasPermi('security:unacceptablerisklist:edit')")
    @Log(title = "不可接受风险清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityUnacceptableRiskList securityUnacceptableRiskList)
    {
        return toAjax(securityUnacceptableRiskListService.updateSecurityUnacceptableRiskList(securityUnacceptableRiskList));
    }

    /**
     * 删除不可接受风险清单
     */
    @PreAuthorize("@ss.hasPermi('security:unacceptablerisklist:remove')")
    @Log(title = "不可接受风险清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityUnacceptableRiskListService.deleteSecurityUnacceptableRiskListByIds(ids));
    }
}
