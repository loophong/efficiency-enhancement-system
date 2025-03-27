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
import com.ruoyi.security.domain.SecutityEnvironmentalFactorsImpact;
import com.ruoyi.security.service.ISecutityEnvironmentalFactorsImpactService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
@Slf4j

/**
 * 环境因素清单Controller
 *
 * @author wang
 * @date 2025-03-26
 */
@RestController
@RequestMapping("/security/impact")
public class SecutityEnvironmentalFactorsImpactController extends BaseController {
    @Autowired
    private ISecutityEnvironmentalFactorsImpactService secutityEnvironmentalFactorsImpactService;

    /**
     * 查询环境因素清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:impact:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact) {
        startPage();
        List<SecutityEnvironmentalFactorsImpact> list = secutityEnvironmentalFactorsImpactService.selectSecutityEnvironmentalFactorsImpactList(secutityEnvironmentalFactorsImpact);
        return getDataTable(list);
    }

    /**
     * 导出环境因素清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:impact:export')")
    @Log(title = "环境因素清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact) {
        List<SecutityEnvironmentalFactorsImpact> list = secutityEnvironmentalFactorsImpactService.selectSecutityEnvironmentalFactorsImpactList(secutityEnvironmentalFactorsImpact);
        ExcelUtil<SecutityEnvironmentalFactorsImpact> util = new ExcelUtil<SecutityEnvironmentalFactorsImpact>(SecutityEnvironmentalFactorsImpact.class);
        util.exportExcel(response, list, "环境因素清单数据");
    }

    /**
     * 获取环境因素清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:impact:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(secutityEnvironmentalFactorsImpactService.selectSecutityEnvironmentalFactorsImpactById(id));
    }

    /**
     * 新增环境因素清单
     */
    @PreAuthorize("@ss.hasPermi('security:impact:add')")
    @Log(title = "环境因素清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact) {
        return toAjax(secutityEnvironmentalFactorsImpactService.insertSecutityEnvironmentalFactorsImpact(secutityEnvironmentalFactorsImpact));
    }

    /**
     * 修改环境因素清单
     */
    @PreAuthorize("@ss.hasPermi('security:impact:edit')")
    @Log(title = "环境因素清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact) {
        return toAjax(secutityEnvironmentalFactorsImpactService.updateSecutityEnvironmentalFactorsImpact(secutityEnvironmentalFactorsImpact));
    }

    /**
     * 删除环境因素清单
     */
    @PreAuthorize("@ss.hasPermi('security:impact:remove')")
    @Log(title = "环境因素清单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(secutityEnvironmentalFactorsImpactService.deleteSecutityEnvironmentalFactorsImpactByIds(ids));
    }
    @Log(title = "[环境因素清单]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public void importTable(MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            secutityEnvironmentalFactorsImpactService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }

    }
}
