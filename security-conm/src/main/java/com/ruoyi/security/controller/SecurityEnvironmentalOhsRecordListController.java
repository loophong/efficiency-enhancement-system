package com.ruoyi.security.controller;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
import com.ruoyi.security.domain.SecurityEnvironmentalOhsRecordList;
import com.ruoyi.security.service.ISecurityEnvironmentalOhsRecordListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 环境职业健康安全记录清单Controller
 * 
 * @author wang
 * @date 2025-03-07
 */
@Slf4j
@RestController
@RequestMapping("/security/EnvironmentalOhsRecordList")
public class SecurityEnvironmentalOhsRecordListController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentalOhsRecordListService securityEnvironmentalOhsRecordListService;
    @Log(title = "[危险化学物品上传]上传", businessType = BusinessType.IMPORT)

    @PostMapping("/import")
    public void importTable( MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            securityEnvironmentalOhsRecordListService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }
    /**
     * 查询环境职业健康安全记录清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalOhsRecordList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList)
    {
        startPage();
        List<SecurityEnvironmentalOhsRecordList> list = securityEnvironmentalOhsRecordListService.selectSecurityEnvironmentalOhsRecordListList(securityEnvironmentalOhsRecordList);
        return getDataTable(list);
    }

    /**
     * 导出环境职业健康安全记录清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalOhsRecordList:export')")
    @Log(title = "环境职业健康安全记录清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList)
    {
        List<SecurityEnvironmentalOhsRecordList> list = securityEnvironmentalOhsRecordListService.selectSecurityEnvironmentalOhsRecordListList(securityEnvironmentalOhsRecordList);
        ExcelUtil<SecurityEnvironmentalOhsRecordList> util = new ExcelUtil<SecurityEnvironmentalOhsRecordList>(SecurityEnvironmentalOhsRecordList.class);
        util.exportExcel(response, list, "环境职业健康安全记录清单数据");
    }

    /**
     * 获取环境职业健康安全记录清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalOhsRecordList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEnvironmentalOhsRecordListService.selectSecurityEnvironmentalOhsRecordListById(id));
    }

    /**
     * 新增环境职业健康安全记录清单
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalOhsRecordList:add')")
    @Log(title = "环境职业健康安全记录清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList)
    {
        return toAjax(securityEnvironmentalOhsRecordListService.insertSecurityEnvironmentalOhsRecordList(securityEnvironmentalOhsRecordList));
    }

    /**
     * 修改环境职业健康安全记录清单
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalOhsRecordList:edit')")
    @Log(title = "环境职业健康安全记录清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList)
    {
        return toAjax(securityEnvironmentalOhsRecordListService.updateSecurityEnvironmentalOhsRecordList(securityEnvironmentalOhsRecordList));
    }

    /**
     * 删除环境职业健康安全记录清单
     */
    @PreAuthorize("@ss.hasPermi('security:EnvironmentalOhsRecordList:remove')")
    @Log(title = "环境职业健康安全记录清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEnvironmentalOhsRecordListService.deleteSecurityEnvironmentalOhsRecordListByIds(ids));
    }
}
