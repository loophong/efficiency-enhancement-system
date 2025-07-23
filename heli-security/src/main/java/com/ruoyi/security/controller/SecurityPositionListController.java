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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecurityPositionList;
import com.ruoyi.security.service.ISecurityPositionListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 岗位清单Controller
 * 
 * @author ruoyi
 * @date 2025-07-18
 */
@RestController
@RequestMapping("/security/Position")
public class SecurityPositionListController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(SecurityPositionListController.class);

    @Autowired
    private ISecurityPositionListService securityPositionListService;

    /**
     * 查询岗位清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:Position:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityPositionList securityPositionList)
    {
        startPage();
        List<SecurityPositionList> list = securityPositionListService.selectSecurityPositionListList(securityPositionList);
        return getDataTable(list);
    }

    /**
     * 导出岗位清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:Position:export')")
    @Log(title = "岗位清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityPositionList securityPositionList)
    {
        List<SecurityPositionList> list = securityPositionListService.selectSecurityPositionListList(securityPositionList);
        ExcelUtil<SecurityPositionList> util = new ExcelUtil<SecurityPositionList>(SecurityPositionList.class);
        util.exportExcel(response, list, "岗位清单数据");
    }

    /**
     * 获取岗位清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:Position:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityPositionListService.selectSecurityPositionListById(id));
    }

    /**
     * 新增岗位清单
     */
    @PreAuthorize("@ss.hasPermi('security:Position:add')")
    @Log(title = "岗位清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityPositionList securityPositionList)
    {
        return toAjax(securityPositionListService.insertSecurityPositionList(securityPositionList));
    }

    /**
     * 修改岗位清单
     */
    @PreAuthorize("@ss.hasPermi('security:Position:edit')")
    @Log(title = "岗位清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityPositionList securityPositionList)
    {
        return toAjax(securityPositionListService.updateSecurityPositionList(securityPositionList));
    }

    /**
     * 删除岗位清单
     */
    @PreAuthorize("@ss.hasPermi('security:Position:remove')")
    @Log(title = "岗位清单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityPositionListService.deleteSecurityPositionListByIds(ids));
    }

    /**
     * 获取岗位清单导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:Position:import')")
    @RequestMapping(value = "/importTemplate", method = {RequestMethod.GET, RequestMethod.POST})
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SecurityPositionList> util = new ExcelUtil<SecurityPositionList>(SecurityPositionList.class);
        util.importTemplateExcel(response, "岗位清单数据");
    }

    /**
     * 导入岗位清单数据
     */
    @Log(title = "岗位清单", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('security:Position:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        try {
            ExcelUtil<SecurityPositionList> util = new ExcelUtil<SecurityPositionList>(SecurityPositionList.class);
            List<SecurityPositionList> positionList = util.importExcel(file.getInputStream());

            if (positionList.isEmpty()) {
                return error("导入数据为空，请检查Excel文件格式");
            }

            String operName = SecurityUtils.getUsername();
            String message = securityPositionListService.importSecurityPositionList(positionList, updateSupport, operName);
            return success(message);

        } catch (Exception e) {
            log.error("导入岗位清单数据失败", e);
            return error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 根据关联ID查询岗位清单列表
     */
    @PreAuthorize("@ss.hasPermi('security:Position:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public TableDataInfo listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        List<SecurityPositionList> list = securityPositionListService.selectSecurityPositionListByRelatedId(relatedId);
        return getDataTable(list);
    }
}
