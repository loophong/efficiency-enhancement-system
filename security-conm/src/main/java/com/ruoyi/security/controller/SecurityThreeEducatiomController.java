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
import com.ruoyi.security.domain.SecurityThreeEducatiom;
import com.ruoyi.security.service.ISecurityThreeEducatiomService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新职工三级安全教育台账Controller
 * 
 * @author wang
 * @date 2025-03-28
 */
@RestController
@RequestMapping("/security/ThreeEducatiom")
public class SecurityThreeEducatiomController extends BaseController
{
    @Autowired
    private ISecurityThreeEducatiomService securityThreeEducatiomService;

    /**
     * 查询新职工三级安全教育台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:ThreeEducatiom:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityThreeEducatiom securityThreeEducatiom)
    {
        startPage();
        List<SecurityThreeEducatiom> list = securityThreeEducatiomService.selectSecurityThreeEducatiomList(securityThreeEducatiom);
        return getDataTable(list);
    }

    /**
     * 导出新职工三级安全教育台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:ThreeEducatiom:export')")
    @Log(title = "新职工三级安全教育台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityThreeEducatiom securityThreeEducatiom)
    {
        List<SecurityThreeEducatiom> list = securityThreeEducatiomService.selectSecurityThreeEducatiomList(securityThreeEducatiom);
        ExcelUtil<SecurityThreeEducatiom> util = new ExcelUtil<SecurityThreeEducatiom>(SecurityThreeEducatiom.class);
        util.exportExcel(response, list, "新职工三级安全教育台账数据");
    }

    /**
     * 获取新职工三级安全教育台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:ThreeEducatiom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityThreeEducatiomService.selectSecurityThreeEducatiomById(id));
    }

    /**
     * 新增新职工三级安全教育台账
     */
    @PreAuthorize("@ss.hasPermi('security:ThreeEducatiom:add')")
    @Log(title = "新职工三级安全教育台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityThreeEducatiom securityThreeEducatiom)
    {
        return toAjax(securityThreeEducatiomService.insertSecurityThreeEducatiom(securityThreeEducatiom));
    }

    /**
     * 修改新职工三级安全教育台账
     */
    @PreAuthorize("@ss.hasPermi('security:ThreeEducatiom:edit')")
    @Log(title = "新职工三级安全教育台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityThreeEducatiom securityThreeEducatiom)
    {
        return toAjax(securityThreeEducatiomService.updateSecurityThreeEducatiom(securityThreeEducatiom));
    }

    /**
     * 删除新职工三级安全教育台账
     */
    @PreAuthorize("@ss.hasPermi('security:ThreeEducatiom:remove')")
    @Log(title = "新职工三级安全教育台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityThreeEducatiomService.deleteSecurityThreeEducatiomByIds(ids));
    }
}
