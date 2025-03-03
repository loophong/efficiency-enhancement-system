package com.heli.production.controller;

import java.util.Arrays;
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
import com.heli.production.domain.entity.FileEntity;
import com.heli.production.service.IFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生产文件Controller
 *
 * @author hong
 * @date 2025-03-03
 */
@RestController
@RequestMapping("/production/file")
public class FileController extends BaseController {
    @Autowired
    private IFileService productionFileService;

    /**
     * 查询生产文件列表
     */
    @PreAuthorize("@ss.hasPermi('production:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(FileEntity productionFile) {
        startPage();
        List<FileEntity> list = productionFileService.selectProductionFileList(productionFile);
        return getDataTable(list);
    }

    /**
     * 获取生产文件详细信息
     */
    @PreAuthorize("@ss.hasPermi('production:file:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(productionFileService.getById(id));
    }

    /**
     * 新增生产文件
     */
    @PreAuthorize("@ss.hasPermi('production:file:add')")
    @Log(title = "生产文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FileEntity productionFile) {
        return toAjax(productionFileService.save(productionFile));
    }

    /**
     * 修改生产文件
     */
    @PreAuthorize("@ss.hasPermi('production:file:edit')")
    @Log(title = "生产文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FileEntity productionFile) {
        return toAjax(productionFileService.updateById(productionFile));
    }

    /**
     * 删除生产文件
     */
    @PreAuthorize("@ss.hasPermi('production:file:remove')")
    @Log(title = "生产文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(productionFileService.removeByIds(Arrays.asList(ids)));
    }
}
