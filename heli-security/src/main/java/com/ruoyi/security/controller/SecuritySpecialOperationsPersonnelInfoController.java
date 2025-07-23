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
import com.ruoyi.security.domain.SecuritySpecialOperationsPersonnelInfo;
import com.ruoyi.security.service.ISecuritySpecialOperationsPersonnelInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 特种作业人员信息Controller
 * 
 * @author wang
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/security/specialoperationspersonnelInfo")
public class SecuritySpecialOperationsPersonnelInfoController extends BaseController
{
    @Autowired
    private ISecuritySpecialOperationsPersonnelInfoService securitySpecialOperationsPersonnelInfoService;

    /**
     * 查询特种作业人员信息列表
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationspersonnelInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo)
    {
        startPage();
        List<SecuritySpecialOperationsPersonnelInfo> list = securitySpecialOperationsPersonnelInfoService.selectSecuritySpecialOperationsPersonnelInfoList(securitySpecialOperationsPersonnelInfo);
        return getDataTable(list);
    }

    /**
     * 导出特种作业人员信息列表
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationspersonnelInfo:export')")
    @Log(title = "特种作业人员信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo)
    {
        List<SecuritySpecialOperationsPersonnelInfo> list = securitySpecialOperationsPersonnelInfoService.selectSecuritySpecialOperationsPersonnelInfoList(securitySpecialOperationsPersonnelInfo);
        ExcelUtil<SecuritySpecialOperationsPersonnelInfo> util = new ExcelUtil<SecuritySpecialOperationsPersonnelInfo>(SecuritySpecialOperationsPersonnelInfo.class);
        util.exportExcel(response, list, "特种作业人员信息数据");
    }

    /**
     * 获取特种作业人员信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationspersonnelInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securitySpecialOperationsPersonnelInfoService.selectSecuritySpecialOperationsPersonnelInfoById(id));
    }

    /**
     * 新增特种作业人员信息
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationspersonnelInfo:add')")
    @Log(title = "特种作业人员信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo)
    {
        return toAjax(securitySpecialOperationsPersonnelInfoService.insertSecuritySpecialOperationsPersonnelInfo(securitySpecialOperationsPersonnelInfo));
    }

    /**
     * 修改特种作业人员信息
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationspersonnelInfo:edit')")
    @Log(title = "特种作业人员信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo)
    {
        return toAjax(securitySpecialOperationsPersonnelInfoService.updateSecuritySpecialOperationsPersonnelInfo(securitySpecialOperationsPersonnelInfo));
    }

    /**
     * 删除特种作业人员信息
     */
    @PreAuthorize("@ss.hasPermi('security:specialoperationspersonnelInfo:remove')")
    @Log(title = "特种作业人员信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securitySpecialOperationsPersonnelInfoService.deleteSecuritySpecialOperationsPersonnelInfoByIds(ids));
    }

    /**
     * 获取特种作业人员信息导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecuritySpecialOperationsPersonnelInfo> util = new ExcelUtil<SecuritySpecialOperationsPersonnelInfo>(SecuritySpecialOperationsPersonnelInfo.class);
        util.importTemplateExcel(response, "特种作业人员信息数据");
    }

    /**
     * 导入特种作业人员信息数据
     */
    @Log(title = "特种作业人员信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('security:specialoperationspersonnelInfo:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SecuritySpecialOperationsPersonnelInfo> util = new ExcelUtil<SecuritySpecialOperationsPersonnelInfo>(SecuritySpecialOperationsPersonnelInfo.class);
        List<SecuritySpecialOperationsPersonnelInfo> personnelInfoList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = securitySpecialOperationsPersonnelInfoService.importSpecialOperationsPersonnelInfo(personnelInfoList, updateSupport, operName);
        return success(message);
    }
}
