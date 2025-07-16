package com.ruoyi.security.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecueityCompanyKpi;
import com.ruoyi.security.service.ISecueityCompanyKpiService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公司KPIController
 * 
 * @author wang
 * @date 2025-02-27
 */
@RestController
@RequestMapping("/security/kpi")
public class SecueityCompanyKpiController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(SecueityCompanyKpiController.class);
    
    @Autowired
    private ISecueityCompanyKpiService secueityCompanyKpiService;

    /**
     * 查询公司KPI列表
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecueityCompanyKpi secueityCompanyKpi)
    {
        startPage();
        List<SecueityCompanyKpi> list = secueityCompanyKpiService.selectSecueityCompanyKpiList(secueityCompanyKpi);
        return getDataTable(list);
    }

    /**
     * 根据关联ID查询公司KPI列表
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public AjaxResult listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        List<SecueityCompanyKpi> list = secueityCompanyKpiService.selectSecueityCompanyKpiByRelatedId(relatedId);
        return success(list);
    }

    /**
     * 导出公司KPI列表
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:export')")
    @Log(title = "公司KPI", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, SecueityCompanyKpi secueityCompanyKpi)
    {
        List<SecueityCompanyKpi> list = secueityCompanyKpiService.selectSecueityCompanyKpiList(secueityCompanyKpi);
        ExcelUtil<SecueityCompanyKpi> util = new ExcelUtil<SecueityCompanyKpi>(SecueityCompanyKpi.class);
        util.exportExcel(response, list, "公司KPI考核数据");
    }

    /**
     * 获取公司KPI详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(secueityCompanyKpiService.selectSecueityCompanyKpiById(id));
    }

    /**
     * 新增公司KPI
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:add')")
    @Log(title = "公司KPI", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecueityCompanyKpi secueityCompanyKpi)
    {
        return toAjax(secueityCompanyKpiService.insertSecueityCompanyKpi(secueityCompanyKpi));
    }

    /**
     * 修改公司KPI
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:edit')")
    @Log(title = "公司KPI", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecueityCompanyKpi secueityCompanyKpi)
    {
        return toAjax(secueityCompanyKpiService.updateSecueityCompanyKpi(secueityCompanyKpi));
    }

    /**
     * 删除公司KPI
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:remove')")
    @Log(title = "公司KPI", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(secueityCompanyKpiService.deleteSecueityCompanyKpiByIds(ids));
    }

    /**
     * 根据关联ID删除公司KPI
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:remove')")
    @Log(title = "公司KPI", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteByRelatedId/{relatedId}")
    public AjaxResult removeByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        return toAjax(secueityCompanyKpiService.deleteSecueityCompanyKpiByRelatedId(relatedId));
    }
    
    /**
     * 下载公司KPI导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<SecueityCompanyKpi> util = new ExcelUtil<SecueityCompanyKpi>(SecueityCompanyKpi.class);
        util.importTemplateExcel(response, "公司KPI考核数据");
    }

    /**
     * 导入公司KPI数据
     */
    @PreAuthorize("@ss.hasPermi('security:kpi:import')")
    @Log(title = "公司KPI", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("excelFile") MultipartFile file) throws Exception
    {
        try {
            log.info("接收到文件上传请求，文件名：{}，文件大小：{}，内容类型：{}", 
                file.getOriginalFilename(), file.getSize(), file.getContentType());
            
            // 检查文件是否为空
            if (file.isEmpty()) {
                log.error("上传的文件为空");
                return error("上传的文件为空");
            }
            
            ExcelUtil<SecueityCompanyKpi> util = new ExcelUtil<SecueityCompanyKpi>(SecueityCompanyKpi.class);
            List<SecueityCompanyKpi> kpiList = util.importExcel(file.getInputStream());
            
            log.info("成功解析Excel文件，共{}条记录", kpiList.size());
            
            String operName = getUsername();
            String message = secueityCompanyKpiService.importKpi(kpiList, operName);
            return success(message);
        } catch (Exception e) {
            log.error("导入KPI数据失败", e);
            return error("导入KPI数据失败：" + e.getMessage());
        }
    }
}
