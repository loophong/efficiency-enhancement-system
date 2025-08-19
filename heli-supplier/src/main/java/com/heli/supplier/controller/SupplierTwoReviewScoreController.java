package com.heli.supplier.controller;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.heli.supplier.domain.SupplierTwoReviewScore;
import com.heli.supplier.service.ISupplierTwoReviewScoreService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 二方审核得分Controller
 * 
 * @author wll
 * @date 2025-03-06
 */
@Slf4j
@RestController
@RequestMapping("/supplier/score")
public class SupplierTwoReviewScoreController extends BaseController
{
    @Autowired
    private ISupplierTwoReviewScoreService supplierTwoReviewScoreService;
    @PreAuthorize("@ss.hasPermi('supplier:score:import')")
    @Log(title = "[二方审核得分]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    @Transactional
    public void importTable(MultipartFile excelFile, Date uploadMonth) {
        log.info("传入的参数为 " +excelFile);
        log.info("传入的参数为 " +uploadMonth);
        try {
            supplierTwoReviewScoreService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile,uploadMonth);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }
    /**
     * 查询二方审核得分列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:score:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierTwoReviewScore supplierTwoReviewScore)
    {
        startPage();
        List<SupplierTwoReviewScore> list = supplierTwoReviewScoreService.selectSupplierTwoReviewScoreList(supplierTwoReviewScore);
        return getDataTable(list);
    }

    /**
     * 导出二方审核得分列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:score:export')")
    @Log(title = "二方审核得分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierTwoReviewScore supplierTwoReviewScore)
    {
        List<SupplierTwoReviewScore> list = supplierTwoReviewScoreService.selectSupplierTwoReviewScoreList(supplierTwoReviewScore);
        ExcelUtil<SupplierTwoReviewScore> util = new ExcelUtil<SupplierTwoReviewScore>(SupplierTwoReviewScore.class);
        util.exportExcel(response, list, "二方审核得分");
    }

    /**
     * 获取二方审核得分详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:score:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(supplierTwoReviewScoreService.selectSupplierTwoReviewScoreById(id));
    }

    /**
     * 新增二方审核得分
     */
    @PreAuthorize("@ss.hasPermi('supplier:score:add')")
    @Log(title = "二方审核得分", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierTwoReviewScore supplierTwoReviewScore)
    {
        return toAjax(supplierTwoReviewScoreService.insertSupplierTwoReviewScore(supplierTwoReviewScore));
    }

    /**
     * 修改二方审核得分
     */
    @PreAuthorize("@ss.hasPermi('supplier:score:edit')")
    @Log(title = "二方审核得分", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierTwoReviewScore supplierTwoReviewScore)
    {
        return toAjax(supplierTwoReviewScoreService.updateSupplierTwoReviewScore(supplierTwoReviewScore));
    }

    /**
     * 删除二方审核得分
     */
    @PreAuthorize("@ss.hasPermi('supplier:score:remove')")
    @Log(title = "二方审核得分", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(supplierTwoReviewScoreService.deleteSupplierTwoReviewScoreByIds(ids));
    }
}
