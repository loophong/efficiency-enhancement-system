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
import com.ruoyi.security.domain.SecutityLegalRegulationsIdentificationList;
import com.ruoyi.security.service.ISecutityLegalRegulationsIdentificationListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全/环境法律法规识别清单Controller
 * 
 * @author wang
 * @date 2025-03-02
 */
@RestController
@RequestMapping("/security/legallist")
public class SecutityLegalRegulationsIdentificationListController extends BaseController
{
    @Autowired
    private ISecutityLegalRegulationsIdentificationListService secutityLegalRegulationsIdentificationListService;

    /**
     * 查询安全/环境法律法规识别清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:legallist:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecutityLegalRegulationsIdentificationList secutityLegalRegulationsIdentificationList)
    {
        startPage();
        List<SecutityLegalRegulationsIdentificationList> list = secutityLegalRegulationsIdentificationListService.selectSecutityLegalRegulationsIdentificationListList(secutityLegalRegulationsIdentificationList);
        return getDataTable(list);
    }

    /**
     * 导出安全/环境法律法规识别清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:legallist:export')")
    @Log(title = "安全/环境法律法规识别清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecutityLegalRegulationsIdentificationList secutityLegalRegulationsIdentificationList)
    {
        List<SecutityLegalRegulationsIdentificationList> list = secutityLegalRegulationsIdentificationListService.selectSecutityLegalRegulationsIdentificationListList(secutityLegalRegulationsIdentificationList);
        ExcelUtil<SecutityLegalRegulationsIdentificationList> util = new ExcelUtil<SecutityLegalRegulationsIdentificationList>(SecutityLegalRegulationsIdentificationList.class);
        util.exportExcel(response, list, "安全/环境法律法规识别清单数据");
    }

    /**
     * 获取安全/环境法律法规识别清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:legallist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(secutityLegalRegulationsIdentificationListService.selectSecutityLegalRegulationsIdentificationListById(id));
    }

    /**
     * 新增安全/环境法律法规识别清单
     */
    @PreAuthorize("@ss.hasPermi('security:legallist:add')")
    @Log(title = "安全/环境法律法规识别清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecutityLegalRegulationsIdentificationList secutityLegalRegulationsIdentificationList)
    {
        return toAjax(secutityLegalRegulationsIdentificationListService.insertSecutityLegalRegulationsIdentificationList(secutityLegalRegulationsIdentificationList));
    }

    /**
     * 修改安全/环境法律法规识别清单
     */
    @PreAuthorize("@ss.hasPermi('security:legallist:edit')")
    @Log(title = "安全/环境法律法规识别清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecutityLegalRegulationsIdentificationList secutityLegalRegulationsIdentificationList)
    {
        return toAjax(secutityLegalRegulationsIdentificationListService.updateSecutityLegalRegulationsIdentificationList(secutityLegalRegulationsIdentificationList));
    }

    /**
     * 删除安全/环境法律法规识别清单
     */
    @PreAuthorize("@ss.hasPermi('security:legallist:remove')")
    @Log(title = "安全/环境法律法规识别清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(secutityLegalRegulationsIdentificationListService.deleteSecutityLegalRegulationsIdentificationListByIds(ids));
    }
}
