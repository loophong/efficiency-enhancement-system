package com.heli.supplier.controller;

import java.util.List;
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
import com.heli.supplier.domain.SupplierHappen;
import com.heli.supplier.service.ISupplierHappenService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 质量通知单Controller
 * 
 * @author wll
 * @date 2025-03-07
 */
@RestController
@RequestMapping("/supplier/happen")
@Slf4j
public class SupplierHappenController extends BaseController
{
    @Autowired
    private ISupplierHappenService supplierHappenService;

    /**
     * 查询质量通知单列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:happen:list')")
    @GetMapping("/list")
    public TableDataInfo list(SupplierHappen supplierHappen)
    {
        startPage();
        List<SupplierHappen> list = supplierHappenService.selectSupplierHappenList(supplierHappen);
        return getDataTable(list);
    }

    /**
     * 导出质量通知单列表
     */
    @PreAuthorize("@ss.hasPermi('supplier:happen:export')")
    @Log(title = "质量通知单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierHappen supplierHappen)
    {
        List<SupplierHappen> list = supplierHappenService.selectSupplierHappenList(supplierHappen);
        // 动态设置回函状态
        for (SupplierHappen vo : list) {
            if (vo.getReplyTime() == null) {
                vo.setReplyStatus("未回函");
            } else if (vo.getDeadline() != null && vo.getReplyTime().after(vo.getDeadline())) {
                vo.setReplyStatus("回函不及时");
            } else {
                vo.setReplyStatus("已回函");
            }
        }
        ExcelUtil<SupplierHappen> util = new ExcelUtil<SupplierHappen>(SupplierHappen.class);
        util.exportExcel(response, list, "质量通知单数据");
    }

    /**
     * 获取质量通知单详细信息
     */
    @PreAuthorize("@ss.hasPermi('supplier:happen:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(supplierHappenService.selectSupplierHappenById(id));
    }

    /**
     * 新增质量通知单
     */
    @PreAuthorize("@ss.hasPermi('supplier:happen:add')")
    @Log(title = "质量通知单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierHappen supplierHappen)
    {
        return toAjax(supplierHappenService.insertSupplierHappen(supplierHappen));
    }

    /**
     * 修改质量通知单
     */
    @PreAuthorize("@ss.hasPermi('supplier:happen:edit')")
    @Log(title = "质量通知单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierHappen supplierHappen)
    {
        return toAjax(supplierHappenService.updateSupplierHappen(supplierHappen));
    }

    /**
     * 删除质量通知单
     */
    @PreAuthorize("@ss.hasPermi('supplier:happen:remove')")
    @Log(title = "质量通知单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(supplierHappenService.deleteSupplierHappenByIds(ids));
    }
}
