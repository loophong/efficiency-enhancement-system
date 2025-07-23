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
import com.ruoyi.security.domain.SecurityOccupationalPhysicalExaminationReportpersons;
import com.ruoyi.security.service.ISecurityOccupationalPhysicalExaminationReportpersonsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 职业危害接触人员体检报告Controller
 * 
 * @author wang
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/security/reportpersons")
public class SecurityOccupationalPhysicalExaminationReportpersonsController extends BaseController
{
    @Autowired
    private ISecurityOccupationalPhysicalExaminationReportpersonsService securityOccupationalPhysicalExaminationReportpersonsService;

    /**
     * 查询职业危害接触人员体检报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:reportpersons:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityOccupationalPhysicalExaminationReportpersons securityOccupationalPhysicalExaminationReportpersons)
    {
        startPage();
        List<SecurityOccupationalPhysicalExaminationReportpersons> list = securityOccupationalPhysicalExaminationReportpersonsService.selectSecurityOccupationalPhysicalExaminationReportpersonsList(securityOccupationalPhysicalExaminationReportpersons);
        return getDataTable(list);
    }

    /**
     * 导出职业危害接触人员体检报告列表
     */
    @PreAuthorize("@ss.hasPermi('security:reportpersons:export')")
    @Log(title = "职业危害接触人员体检报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityOccupationalPhysicalExaminationReportpersons securityOccupationalPhysicalExaminationReportpersons)
    {
        List<SecurityOccupationalPhysicalExaminationReportpersons> list = securityOccupationalPhysicalExaminationReportpersonsService.selectSecurityOccupationalPhysicalExaminationReportpersonsList(securityOccupationalPhysicalExaminationReportpersons);
        ExcelUtil<SecurityOccupationalPhysicalExaminationReportpersons> util = new ExcelUtil<SecurityOccupationalPhysicalExaminationReportpersons>(SecurityOccupationalPhysicalExaminationReportpersons.class);
        util.exportExcel(response, list, "职业危害接触人员体检报告数据");
    }

    /**
     * 获取职业危害接触人员体检报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:reportpersons:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityOccupationalPhysicalExaminationReportpersonsService.selectSecurityOccupationalPhysicalExaminationReportpersonsById(id));
    }

    /**
     * 新增职业危害接触人员体检报告
     */
    @PreAuthorize("@ss.hasPermi('security:reportpersons:add')")
    @Log(title = "职业危害接触人员体检报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityOccupationalPhysicalExaminationReportpersons securityOccupationalPhysicalExaminationReportpersons)
    {
        return toAjax(securityOccupationalPhysicalExaminationReportpersonsService.insertSecurityOccupationalPhysicalExaminationReportpersons(securityOccupationalPhysicalExaminationReportpersons));
    }

    /**
     * 修改职业危害接触人员体检报告
     */
    @PreAuthorize("@ss.hasPermi('security:reportpersons:edit')")
    @Log(title = "职业危害接触人员体检报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityOccupationalPhysicalExaminationReportpersons securityOccupationalPhysicalExaminationReportpersons)
    {
        return toAjax(securityOccupationalPhysicalExaminationReportpersonsService.updateSecurityOccupationalPhysicalExaminationReportpersons(securityOccupationalPhysicalExaminationReportpersons));
    }

    /**
     * 删除职业危害接触人员体检报告
     */
    @PreAuthorize("@ss.hasPermi('security:reportpersons:remove')")
    @Log(title = "职业危害接触人员体检报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityOccupationalPhysicalExaminationReportpersonsService.deleteSecurityOccupationalPhysicalExaminationReportpersonsByIds(ids));
    }
}
