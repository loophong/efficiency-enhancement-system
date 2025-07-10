package com.ruoyi.security.controller;

import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecurityCompanyKeyWorks;
import com.ruoyi.security.service.ISecurityCompanyKeyWorksService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;

/**
 * 公司重点工作Controller
 * 
 * @author wang
 * @date 2025-02-27
 */
@RestController
@RequestMapping("/security/works")
public class SecurityCompanyKeyWorksController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(SecurityCompanyKeyWorksController.class);
    
    @Autowired
    private ISecurityCompanyKeyWorksService securityCompanyKeyWorksService;

    /**
     * 查询公司重点工作列表
     */
    @PreAuthorize("@ss.hasPermi('security:works:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        startPage();
        List<SecurityCompanyKeyWorks> list = securityCompanyKeyWorksService.selectSecurityCompanyKeyWorksList(securityCompanyKeyWorks);
        return getDataTable(list);
    }

    /**
     * 导出公司重点工作列表
     */
    @PreAuthorize("@ss.hasPermi('security:works:export')")
    @Log(title = "公司重点工作", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        List<SecurityCompanyKeyWorks> list = securityCompanyKeyWorksService.selectSecurityCompanyKeyWorksList(securityCompanyKeyWorks);
        ExcelUtil<SecurityCompanyKeyWorks> util = new ExcelUtil<SecurityCompanyKeyWorks>(SecurityCompanyKeyWorks.class);
        util.exportExcel(response, list, "公司重点工作数据");
    }

    /**
     * 获取公司重点工作详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:works:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityCompanyKeyWorksService.selectSecurityCompanyKeyWorksById(id));
    }

    /**
     * 新增公司重点工作
     */
    @PreAuthorize("@ss.hasPermi('security:works:add')")
    @Log(title = "公司重点工作", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        return toAjax(securityCompanyKeyWorksService.insertSecurityCompanyKeyWorks(securityCompanyKeyWorks));
    }

    /**
     * 修改公司重点工作
     */
    @PreAuthorize("@ss.hasPermi('security:works:edit')")
    @Log(title = "公司重点工作", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityCompanyKeyWorks securityCompanyKeyWorks)
    {
        return toAjax(securityCompanyKeyWorksService.updateSecurityCompanyKeyWorks(securityCompanyKeyWorks));
    }

    /**
     * 删除公司重点工作
     */
    @PreAuthorize("@ss.hasPermi('security:works:remove')")
    @Log(title = "公司重点工作", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityCompanyKeyWorksService.deleteSecurityCompanyKeyWorksByIds(ids));
    }
    
    /**
     * 导入公司重点工作数据
     */
    @PreAuthorize("@ss.hasPermi('security:works:import')")
    @Log(title = "公司重点工作", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("excelFile") MultipartFile file) {
        log.info("开始导入Excel文件，文件名: {}, 文件大小: {}", file.getOriginalFilename(), file.getSize());
        try {
            if (file.isEmpty()) {
                log.error("导入失败：文件为空");
                return AjaxResult.error("导入失败：文件为空");
            }
            
            ExcelUtil<SecurityCompanyKeyWorks> util = new ExcelUtil<>(SecurityCompanyKeyWorks.class);
            List<SecurityCompanyKeyWorks> list = util.importExcel(file.getInputStream());
            log.info("Excel解析完成，总行数: {}", list.size());
            
            // 过滤有效行
            List<SecurityCompanyKeyWorks> validList = list.stream()
                .filter(item -> item != null && (
                    (item.getUnitName() != null && !item.getUnitName().trim().isEmpty())
                    || (item.getKeyWorkName() != null && !item.getKeyWorkName().trim().isEmpty())
                ))
                .collect(Collectors.toList());
            
            log.info("有效数据行数: {}", validList.size());
            
            // 处理数据并保存
            int successCount = 0;
            for (SecurityCompanyKeyWorks item : validList) {
                try {
                    securityCompanyKeyWorksService.insertSecurityCompanyKeyWorks(item);
                    successCount++;
                } catch (Exception e) {
                    log.error("保存数据行失败: {}", e.getMessage());
                }
            }
            
            log.info("导入完成，成功导入 {} 条数据", successCount);
            return AjaxResult.success("导入成功，共" + successCount + "条数据");
        } catch (Exception e) {
            log.error("导入失败: {}", e.getMessage(), e);
            return AjaxResult.error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:works:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        try {
            // 设置响应头信息
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = "公司重点工作导入模板.xlsx";
            // 使用URLEncoder处理文件名，避免中文乱码
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            // 禁止缓存
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            
            // 创建示例数据
            List<SecurityCompanyKeyWorks> list = new java.util.ArrayList<>();
            
            // 示例1
            SecurityCompanyKeyWorks example1 = new SecurityCompanyKeyWorks();
            example1.setUnitName("XX部门");
            example1.setKeyWorkName("安全生产管理");
            example1.setCurrentStatusAndCauses("现状描述及原因分析");
            example1.setTarget("提高安全生产管理水平");
            example1.setWeight1("30%");
            example1.setWeight2("70%");
            example1.setQ1FocusImplementation("一季度重点实施内容");
            example1.setQ1CompletionResults("一季度完成情况");
            example1.setQ1Score("95");
            example1.setQ2FocusImplementation("二季度重点实施内容");
            example1.setQ2CompletionResults("二季度完成情况");
            example1.setQ2Score("90");
            example1.setQ3FocusImplementation("三季度重点实施内容");
            example1.setQ3CompletionResults("三季度完成情况");
            example1.setQ3Score("92");
            example1.setQ4FocusImplementation("四季度重点实施内容");
            example1.setQ4CompletionResults("四季度完成情况");
            example1.setQ4Score("93");
            example1.setTotalScore("92.5");
            list.add(example1);
            
            // 示例2
            SecurityCompanyKeyWorks example2 = new SecurityCompanyKeyWorks();
            example2.setUnitName("YY部门");
            example2.setKeyWorkName("质量管理体系建设");
            example2.setCurrentStatusAndCauses("质量管理体系有待完善");
            example2.setTarget("完善质量管理体系");
            example2.setWeight1("40%");
            example2.setWeight2("60%");
            example2.setQ1FocusImplementation("一季度质量体系建设计划");
            example2.setQ1CompletionResults("一季度完成情况");
            example2.setQ1Score("88");
            example2.setQ2FocusImplementation("二季度质量体系建设计划");
            example2.setQ2CompletionResults("二季度完成情况");
            example2.setQ2Score("92");
            example2.setQ3FocusImplementation("三季度质量体系建设计划");
            example2.setQ3CompletionResults("三季度完成情况");
            example2.setQ3Score("94");
            example2.setQ4FocusImplementation("四季度质量体系建设计划");
            example2.setQ4CompletionResults("四季度完成情况");
            example2.setQ4Score("96");
            example2.setTotalScore("92.5");
            list.add(example2);
            
            ExcelUtil<SecurityCompanyKeyWorks> util = new ExcelUtil<>(SecurityCompanyKeyWorks.class);
            util.exportExcel(response, list, "公司重点工作导入模板");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("下载模板失败: " + e.getMessage());
            try {
                // 设置错误响应
                response.reset();
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().println("{\"code\":500,\"msg\":\"下载模板失败：" + e.getMessage() + "\"}");
            } catch (Exception ex) {
                log.error("响应错误信息失败", ex);
            }
        }
    }
}
