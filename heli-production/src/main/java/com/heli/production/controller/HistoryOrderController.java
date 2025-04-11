package com.heli.production.controller;

import java.util.Date;
import java.util.List;

import com.heli.production.domain.entity.HistoryOrderEntity;
import com.heli.production.service.IHistoryOrderService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
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
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 历史订单Controller
 *
 * @author ruoyi
 * @date 2025-01-19
 */
@Slf4j
@RestController
@RequestMapping("/production/historyOrder")
public class HistoryOrderController extends BaseController {
    @Autowired
    private IHistoryOrderService historyOrderService;


    @Log(title = "[历史订单表]上传", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('production:historyOrder:import')")
    @PostMapping("/import")
    public AjaxResult importTable(Date date, MultipartFile excelFile) {
        log.info("传入的date为 " + DateUtils.formatOutput(date));
        log.info("传入的参数为 " + excelFile.getName() + " 文件");
        try {
            return historyOrderService.readSalaryExcelToDB(excelFile.getOriginalFilename(), excelFile, date);
//            return AjaxResult.success();
        } catch (Exception e) {
            log.error("读取 " + excelFile.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + excelFile.getName() + " 文件失败");
        }
    }

    /**
     * 查询历史订单列表
     */
    @PreAuthorize("@ss.hasPermi('production:historyOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(HistoryOrderEntity historyOrder) {
        startPage();
        List<HistoryOrderEntity> list = historyOrderService.selectHistoryOrderList(historyOrder);
        return getDataTable(list);
    }

    /**
     * 导出历史订单列表
     */
    @PreAuthorize("@ss.hasPermi('production:historyOrder:export')")
    @Log(title = "历史订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HistoryOrderEntity historyOrder) {
        List<HistoryOrderEntity> list = historyOrderService.selectHistoryOrderList(historyOrder);
        ExcelUtil<HistoryOrderEntity> util = new ExcelUtil<HistoryOrderEntity>(HistoryOrderEntity.class);
        util.exportExcel(response, list, "历史订单数据");
    }

    /**
     * 获取历史订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:historyOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(historyOrderService.selectHistoryOrderById(id));
    }

    /**
     * 新增历史订单
     */
    @PreAuthorize("@ss.hasPermi('production:historyOrder:add')")
    @Log(title = "历史订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HistoryOrderEntity historyOrder) {
        return toAjax(historyOrderService.insertHistoryOrder(historyOrder));
    }

    /**
     * 修改历史订单
     */
    @PreAuthorize("@ss.hasPermi('production:historyOrder:edit')")
    @Log(title = "历史订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HistoryOrderEntity historyOrder) {
        return toAjax(historyOrderService.updateHistoryOrder(historyOrder));
    }

    /**
     * 删除历史订单
     */
    @PreAuthorize("@ss.hasPermi('production:historyOrder:remove')")
    @Log(title = "历史订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(historyOrderService.deleteHistoryOrderByIds(ids));
    }
}
