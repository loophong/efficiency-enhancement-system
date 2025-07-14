package com.ruoyi.security.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.security.domain.SecurityFileManagement;
import com.ruoyi.security.domain.FileStatistics;
import com.ruoyi.security.service.ISecurityFileManagementService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.http.MediaType;
import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.utils.DateUtils;

/**
 * 文件管理Controller
 * 
 * @author wang
 * @date 2025-07-09
 */
@RestController
@RequestMapping("/filemanagement/filemanagement")
public class SecurityFileManagementController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(SecurityFileManagementController.class);
    
    @Autowired
    private ISecurityFileManagementService securityFileManagementService;

    /**
     * 查询文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('filemanagement:filemanagement:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityFileManagement securityFileManagement)
    {
        startPage();
        List<SecurityFileManagement> list = securityFileManagementService.selectSecurityFileManagementList(securityFileManagement);
        return getDataTable(list);
    }

    /**
     * 导出文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('filemanagement:filemanagement:export')")
    @Log(title = "文件管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityFileManagement securityFileManagement)
    {
        List<SecurityFileManagement> list = securityFileManagementService.selectSecurityFileManagementList(securityFileManagement);
        ExcelUtil<SecurityFileManagement> util = new ExcelUtil<SecurityFileManagement>(SecurityFileManagement.class);
        util.exportExcel(response, list, "文件管理数据");
    }

    /**
     * 获取文件管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('filemanagement:filemanagement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityFileManagementService.selectSecurityFileManagementById(id));
    }

    /**
     * 新增文件管理
     */
    @PreAuthorize("@ss.hasPermi('filemanagement:filemanagement:add')")
    @Log(title = "文件管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityFileManagement securityFileManagement)
    {
        return toAjax(securityFileManagementService.insertSecurityFileManagement(securityFileManagement));
    }

    /**
     * 修改文件管理
     */
    @PreAuthorize("@ss.hasPermi('filemanagement:filemanagement:edit')")
    @Log(title = "文件管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityFileManagement securityFileManagement)
    {
        return toAjax(securityFileManagementService.updateSecurityFileManagement(securityFileManagement));
    }

    /**
     * 删除文件管理
     */
    @PreAuthorize("@ss.hasPermi('filemanagement:filemanagement:remove')")
    @Log(title = "文件管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityFileManagementService.deleteSecurityFileManagementByIds(ids));
    }
    
    /**
     * 下载文件
     */
    @PreAuthorize("@ss.hasPermi('filemanagement:filemanagement:download')")
    @Log(title = "下载文件", businessType = BusinessType.OTHER)
    @PostMapping ("/download/{id}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException
    {
        try
        {
            SecurityFileManagement fileInfo = securityFileManagementService.selectSecurityFileManagementById(id);
            if (fileInfo == null || StringUtils.isEmpty(fileInfo.getFilePath()))
            {
                throw new RuntimeException("文件不存在或文件路径为空");
            }

            // 获取文件路径，文件路径可能是相对路径，也可能是绝对路径
            String filePath = fileInfo.getFilePath();
            
            // 如果是相对路径，需要转为绝对路径
            if (!filePath.startsWith("/") && !filePath.contains(":"))
            {
                // 获取项目上传文件根路径
                String baseDir = RuoYiConfig.getProfile();
                filePath = baseDir + File.separator + filePath;
            }
            
            // 文件存在性检查
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new RuntimeException("文件不存在");
            }
            
            // 设置响应头
            String fileName = fileInfo.getFileName();
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, fileName);
            
            // 写入响应流
            FileUtils.writeBytes(filePath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println("{\"code\":500,\"msg\":\"" + e.getMessage() + "\"}");
        }
    }
    
    /**
     * 获取文件统计信息
     */
    @GetMapping("/statistics")
    public AjaxResult getFileStatistics()
    {
        FileStatistics statistics = securityFileManagementService.getFileStatistics();
        return success(statistics);
    }
    
    /**
     * 获取文件监控数据（最近上传文件和模块统计）
     */
    @GetMapping("/monitor")
    public AjaxResult getMonitorData(@RequestParam(defaultValue = "10") int limit)
    {
        // 获取最近上传的文件
        List<SecurityFileManagement> recentFiles = securityFileManagementService.getRecentFiles(limit);
        
        // 获取模块统计
        Map<String, Integer> moduleStats = securityFileManagementService.getModuleStatistics();
        
        // 组装返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("recentFiles", recentFiles);
        result.put("moduleStats", moduleStats);
        
        return success(result);
    }
    
    /**
     * 记录文件上传
     */
    @PostMapping("/record")
    public AjaxResult recordFileUpload(@RequestBody SecurityFileManagement fileManagement)
    {
        fileManagement.setUploadTime(DateUtils.getNowDate());
        fileManagement.setCreateTime(DateUtils.getNowDate());
        fileManagement.setStatus("0");
        return toAjax(securityFileManagementService.insertSecurityFileManagement(fileManagement));
    }
}
