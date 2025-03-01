package com.heli.production.controller;

import java.util.List;

import com.heli.production.domain.entity.ScanRecordsEntity;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.poi.ExcelUtil;
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
import com.heli.production.service.IScanRecordsService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 整机扫码记录Controller
 *
 * @author ruoyi
 * @date 2025-02-27
 */
@Slf4j
@RestController
@RequestMapping("/production/scanRecords")
public class ScanRecordsController extends BaseController {
    @Autowired
    private IScanRecordsService scanRecordsService;


    @Log(title = "[整机扫码记录表]上传", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('production:scanRecords:import')")
    @PostMapping("/import")
    public void importTable(MultipartFile excelFile) {
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            scanRecordsService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile);
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }

    /**
     * 查询整机扫码记录列表
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScanRecordsEntity scanRecords) {
        startPage();
        List<ScanRecordsEntity> list = scanRecordsService.selectScanRecordsList(scanRecords);
        return getDataTable(list);
    }

    /**
     * 导出整机扫码记录列表
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:export')")
    @Log(title = "整机扫码记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScanRecordsEntity scanRecords) {
        List<ScanRecordsEntity> list = scanRecordsService.selectScanRecordsList(scanRecords);
        ExcelUtil<ScanRecordsEntity> util = new ExcelUtil<ScanRecordsEntity>(ScanRecordsEntity.class);
        util.exportExcel(response, list, "整机扫码记录数据");
    }

    /**
     * 获取整机扫码记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(scanRecordsService.selectScanRecordsById(id));
    }

    /**
     * 新增整机扫码记录
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:add')")
    @Log(title = "整机扫码记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScanRecordsEntity scanRecords) {
        return toAjax(scanRecordsService.insertScanRecords(scanRecords));
    }

    /**
     * 修改整机扫码记录
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:edit')")
    @Log(title = "整机扫码记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScanRecordsEntity scanRecords) {
        return toAjax(scanRecordsService.updateScanRecords(scanRecords));
    }

    /**
     * 删除整机扫码记录
     */
    @PreAuthorize("@ss.hasPermi('production:scanRecords:remove')")
    @Log(title = "整机扫码记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(scanRecordsService.deleteScanRecordsByIds(ids));
    }
}
