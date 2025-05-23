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
import com.ruoyi.security.domain.SecutityLegalRegulationsIdentificationList1;
import com.ruoyi.security.service.ISecutityLegalRegulationsIdentificationList1Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 安全法律法规识别清单Controller
 * 
 * @author wang
 * @date 2025-04-11
 */
@Slf4j
@RestController
@RequestMapping("/security/RegulationsIdentificationList1")
public class SecutityLegalRegulationsIdentificationList1Controller extends BaseController
{
    @Autowired
    private ISecutityLegalRegulationsIdentificationList1Service secutityLegalRegulationsIdentificationList1Service;

    /**
     * 查询安全法律法规识别清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:RegulationsIdentificationList1:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecutityLegalRegulationsIdentificationList1 secutityLegalRegulationsIdentificationList1)
    {
        startPage();
        List<SecutityLegalRegulationsIdentificationList1> list = secutityLegalRegulationsIdentificationList1Service.selectSecutityLegalRegulationsIdentificationList1List(secutityLegalRegulationsIdentificationList1);
        return getDataTable(list);
    }

    /**
     * 导出安全法律法规识别清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:RegulationsIdentificationList1:export')")
    @Log(title = "安全法律法规识别清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecutityLegalRegulationsIdentificationList1 secutityLegalRegulationsIdentificationList1)
    {
        List<SecutityLegalRegulationsIdentificationList1> list = secutityLegalRegulationsIdentificationList1Service.selectSecutityLegalRegulationsIdentificationList1List(secutityLegalRegulationsIdentificationList1);
        ExcelUtil<SecutityLegalRegulationsIdentificationList1> util = new ExcelUtil<SecutityLegalRegulationsIdentificationList1>(SecutityLegalRegulationsIdentificationList1.class);
        util.exportExcel(response, list, "安全法律法规识别清单数据");
    }

    /**
     * 获取安全法律法规识别清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:RegulationsIdentificationList1:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(secutityLegalRegulationsIdentificationList1Service.selectSecutityLegalRegulationsIdentificationList1ById(id));
    }

    /**
     * 新增安全法律法规识别清单
     */
    @PreAuthorize("@ss.hasPermi('security:RegulationsIdentificationList1:add')")
    @Log(title = "安全法律法规识别清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecutityLegalRegulationsIdentificationList1 secutityLegalRegulationsIdentificationList1)
    {
        return toAjax(secutityLegalRegulationsIdentificationList1Service.insertSecutityLegalRegulationsIdentificationList1(secutityLegalRegulationsIdentificationList1));
    }

    /**
     * 修改安全法律法规识别清单
     */
    @PreAuthorize("@ss.hasPermi('security:RegulationsIdentificationList1:edit')")
    @Log(title = "安全法律法规识别清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecutityLegalRegulationsIdentificationList1 secutityLegalRegulationsIdentificationList1)
    {
        return toAjax(secutityLegalRegulationsIdentificationList1Service.updateSecutityLegalRegulationsIdentificationList1(secutityLegalRegulationsIdentificationList1));
    }

    /**
     * 删除安全法律法规识别清单
     */
    @PreAuthorize("@ss.hasPermi('security:RegulationsIdentificationList1:remove')")
    @Log(title = "安全法律法规识别清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(secutityLegalRegulationsIdentificationList1Service.deleteSecutityLegalRegulationsIdentificationList1ByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('production:historyOrder:import')")
    @PostMapping("/import")
    public void importTable( MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            secutityLegalRegulationsIdentificationList1Service.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }
}
