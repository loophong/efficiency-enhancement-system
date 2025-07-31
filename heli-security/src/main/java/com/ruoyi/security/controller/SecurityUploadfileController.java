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
import com.ruoyi.security.domain.SecurityUploadfile;
import com.ruoyi.security.service.ISecurityUploadfileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文件预览Controller
 * 
 * @author wang
 * @date 2025-03-26
 */
@RestController
@RequestMapping("/security/uploadfile")
public class SecurityUploadfileController extends BaseController
{
    @Autowired
    private ISecurityUploadfileService securityUploadfileService;

    /**
     * 查询文件预览列表
     */
    @PreAuthorize("@ss.hasPermi('security:uploadfile:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityUploadfile securityUploadfile)
    {
        startPage();
        List<SecurityUploadfile> list = securityUploadfileService.selectSecurityUploadfileList(securityUploadfile);
        return getDataTable(list);
    }

    /**
     * 导出文件预览列表
     */
    @PreAuthorize("@ss.hasPermi('security:uploadfile:export')")
    @Log(title = "文件预览", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityUploadfile securityUploadfile)
    {
        List<SecurityUploadfile> list = securityUploadfileService.selectSecurityUploadfileList(securityUploadfile);
        ExcelUtil<SecurityUploadfile> util = new ExcelUtil<SecurityUploadfile>(SecurityUploadfile.class);
        util.exportExcel(response, list, "文件预览数据");
    }

    /**
     * 获取文件预览详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:uploadfile:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityUploadfileService.selectSecurityUploadfileById(id));
    }

    /**
     * 新增文件预览
     */
    @PreAuthorize("@ss.hasPermi('security:uploadfile:add')")
    @Log(title = "文件预览", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityUploadfile securityUploadfile)
    {
        return toAjax(securityUploadfileService.insertSecurityUploadfile(securityUploadfile));
    }

    /**
     * 修改文件预览
     */
    @PreAuthorize("@ss.hasPermi('security:uploadfile:edit')")
    @Log(title = "文件预览", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityUploadfile securityUploadfile)
    {
        return toAjax(securityUploadfileService.updateSecurityUploadfile(securityUploadfile));
    }

    /**
     * 删除文件预览
     */
    @PreAuthorize("@ss.hasPermi('security:uploadfile:remove')")
    @Log(title = "文件预览", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityUploadfileService.deleteSecurityUploadfileByIds(ids));
    }
}
