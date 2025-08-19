package com.heli.supplier.controller;

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
import com.heli.supplier.domain.SupplierEvaluate;
import com.heli.supplier.service.ISupplierEvaluateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评分表Controller
 *
 * @author wll
 * @date 2025-03-13
 */
@RestController
@RequestMapping("/supplier/evaluate")
public class SupplierEvaluateController extends BaseController {
    @Autowired
    private ISupplierEvaluateService supplierEvaluateService;


//    /**
//     * 按时间段删除评分表数据
//     */
//    @PreAuthorize("@ss.hasPermi('supplier:evaluate:remove')")
//    @Log(title = "评分表", businessType = BusinessType.DELETE)
//    @PostMapping("/deleteByTimeRange")
//    public AjaxResult deleteByTimeRange(@RequestBody SupplierEvaluate supplierEvaluate) {
////        return toAjax(supplierEvaluateService.deleteSupplierEvaluateByTimeRange(
////                supplierEvaluate.getHappenTime(),
////                supplierEvaluate.getEndTime()
////        ));
//        boolean result = supplierEvaluateService.deleteSupplierEvaluateByTimeRange(
//                supplierEvaluate.getHappenTime(),
//                supplierEvaluate.getEndTime()
//        );
//        return toAjax(result ? 1 : 0); // 转换为AjaxResult期望的格式
//    }
//


    /**
     * 查询评分表列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:evaluate:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierEvaluate supplierEvaluate) {
        startPage();
        List<SupplierEvaluate> list = supplierEvaluateService.selectSupplierEvaluateList(supplierEvaluate);
        return getDataTable(list);
    }

    /**
     * 导出评分表列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:evaluate:export')")
    @Log(title = "评分表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierEvaluate supplierEvaluate) {
        List<SupplierEvaluate> list = supplierEvaluateService.selectSupplierEvaluateList(supplierEvaluate);
        ExcelUtil<SupplierEvaluate> util = new ExcelUtil<SupplierEvaluate>(SupplierEvaluate.class);
        util.exportExcel(response, list, "评分表数据");
    }

    /**
     * 获取评分表详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:evaluate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(supplierEvaluateService.selectSupplierEvaluateById(id));
    }

    /**
     * 新增评分表
     */
    @PreAuthorize("@ss.hasPermi('supplier:evaluate:add')")
    @Log(title = "评分表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierEvaluate supplierEvaluate) {
        return toAjax(supplierEvaluateService.insertSupplierEvaluate(supplierEvaluate));
    }

    /**
     * 计算得分接口
     */
//    @PreAuthorize("@ss.hasPermi('supplier:evaluate:add')")
//    @Log(title = "评分表", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('supplier:evaluate:calculate')")
    @Log(title = "评分表", businessType = BusinessType.OTHER)
    @PostMapping("/calculateScore")
    public AjaxResult calculateScore(@RequestBody SupplierEvaluate supplierEvaluate) {
        return toAjax(supplierEvaluateService.calculateScore(supplierEvaluate.getHappenTime(), supplierEvaluate.getEndTime()));
    }

    /**
     * 修改评分表
     */
    @PreAuthorize("@ss.hasPermi('supplier:evaluate:edit')")
    @Log(title = "评分表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierEvaluate supplierEvaluate) {
        return toAjax(supplierEvaluateService.updateSupplierEvaluate(supplierEvaluate));
    }

    /**
     * 删除评分表
     */
    @PreAuthorize("@ss.hasPermi('supplier:evaluate:remove')")
    @Log(title = "评分表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(supplierEvaluateService.deleteSupplierEvaluateByIds(ids));
    }
}
