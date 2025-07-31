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
import com.ruoyi.security.domain.SecurityShenpiBiao;
import com.ruoyi.security.service.ISecurityShenpiBiaoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 不符合、纠正措施和预防措施程序Controller
 * 
 * @author wang
 * @date 2025-07-29
 */
@RestController
@RequestMapping("/security/shenpibiao")
public class SecurityShenpiBiaoController extends BaseController
{
    @Autowired
    private ISecurityShenpiBiaoService securityShenpiBiaoService;

    /**
     * 查询不符合、纠正措施和预防措施程序列表
     */
    @PreAuthorize("@ss.hasPermi('security:shenpibiao:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityShenpiBiao securityShenpiBiao)
    {
        startPage();
        List<SecurityShenpiBiao> list = securityShenpiBiaoService.selectSecurityShenpiBiaoList(securityShenpiBiao);
        return getDataTable(list);
    }

    /**
     * 导出不符合、纠正措施和预防措施程序列表
     */
    @PreAuthorize("@ss.hasPermi('security:shenpibiao:export')")
    @Log(title = "不符合、纠正措施和预防措施程序", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityShenpiBiao securityShenpiBiao)
    {
        List<SecurityShenpiBiao> list = securityShenpiBiaoService.selectSecurityShenpiBiaoList(securityShenpiBiao);
        ExcelUtil<SecurityShenpiBiao> util = new ExcelUtil<SecurityShenpiBiao>(SecurityShenpiBiao.class);
        util.exportExcel(response, list, "不符合、纠正措施和预防措施程序数据");
    }

    /**
     * 获取不符合、纠正措施和预防措施程序详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:shenpibiao:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityShenpiBiaoService.selectSecurityShenpiBiaoById(id));
    }

    /**
     * 新增不符合、纠正措施和预防措施程序
     */
    @PreAuthorize("@ss.hasPermi('security:shenpibiao:add')")
    @Log(title = "不符合、纠正措施和预防措施程序", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityShenpiBiao securityShenpiBiao)
    {
        return toAjax(securityShenpiBiaoService.insertSecurityShenpiBiao(securityShenpiBiao));
    }

    /**
     * 修改不符合、纠正措施和预防措施程序
     */
    @PreAuthorize("@ss.hasPermi('security:shenpibiao:edit')")
    @Log(title = "不符合、纠正措施和预防措施程序", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityShenpiBiao securityShenpiBiao)
    {
        return toAjax(securityShenpiBiaoService.updateSecurityShenpiBiao(securityShenpiBiao));
    }

    /**
     * 删除不符合、纠正措施和预防措施程序
     */
    @PreAuthorize("@ss.hasPermi('security:shenpibiao:remove')")
    @Log(title = "不符合、纠正措施和预防措施程序", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityShenpiBiaoService.deleteSecurityShenpiBiaoByIds(ids));
    }
}
