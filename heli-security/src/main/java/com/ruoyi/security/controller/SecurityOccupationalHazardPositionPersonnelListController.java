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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecurityOccupationalHazardPositionPersonnelList;
import com.ruoyi.security.service.ISecurityOccupationalHazardPositionPersonnelListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.security.utils.OccupationalPersonnelExcelTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

/**
 * 危险职业岗位人员清单及管理台帐Controller
 * 
 * @author wang
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/security/OccupationaPersonnelList")
public class SecurityOccupationalHazardPositionPersonnelListController extends BaseController
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityOccupationalHazardPositionPersonnelListController.class);

    @Autowired
    private ISecurityOccupationalHazardPositionPersonnelListService securityOccupationalHazardPositionPersonnelListService;

    /**
     * 查询危险职业岗位人员清单及管理台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        startPage();
        List<SecurityOccupationalHazardPositionPersonnelList> list = securityOccupationalHazardPositionPersonnelListService.selectSecurityOccupationalHazardPositionPersonnelListList(securityOccupationalHazardPositionPersonnelList);
        return getDataTable(list);
    }

    /**
     * 导出危险职业岗位人员清单及管理台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:export')")
    @Log(title = "危险职业岗位人员清单及管理台帐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        List<SecurityOccupationalHazardPositionPersonnelList> list = securityOccupationalHazardPositionPersonnelListService.selectSecurityOccupationalHazardPositionPersonnelListList(securityOccupationalHazardPositionPersonnelList);
        ExcelUtil<SecurityOccupationalHazardPositionPersonnelList> util = new ExcelUtil<SecurityOccupationalHazardPositionPersonnelList>(SecurityOccupationalHazardPositionPersonnelList.class);
        util.exportExcel(response, list, "危险职业岗位人员清单及管理台帐数据");
    }

    /**
     * 获取危险职业岗位人员清单及管理台帐详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityOccupationalHazardPositionPersonnelListService.selectSecurityOccupationalHazardPositionPersonnelListById(id));
    }

    /**
     * 新增危险职业岗位人员清单及管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:add')")
    @Log(title = "危险职业岗位人员清单及管理台帐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        return toAjax(securityOccupationalHazardPositionPersonnelListService.insertSecurityOccupationalHazardPositionPersonnelList(securityOccupationalHazardPositionPersonnelList));
    }

    /**
     * 修改危险职业岗位人员清单及管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:edit')")
    @Log(title = "危险职业岗位人员清单及管理台帐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        return toAjax(securityOccupationalHazardPositionPersonnelListService.updateSecurityOccupationalHazardPositionPersonnelList(securityOccupationalHazardPositionPersonnelList));
    }

    /**
     * 删除危险职业岗位人员清单及管理台帐
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:remove')")
    @Log(title = "危险职业岗位人员清单及管理台帐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityOccupationalHazardPositionPersonnelListService.deleteSecurityOccupationalHazardPositionPersonnelListByIds(ids));
    }

    /**
     * 导入危险职业岗位人员清单及管理台帐数据
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:import')")
    @Log(title = "危险职业岗位人员清单及管理台帐", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) throws Exception {
        logger.info("开始导入危险职业岗位人员清单及管理台帐数据，文件名: {}, 文件大小: {} bytes",
                file.getOriginalFilename(), file.getSize());

        List<SecurityOccupationalHazardPositionPersonnelList> personnelList;
        try {
            ExcelUtil<SecurityOccupationalHazardPositionPersonnelList> util = new ExcelUtil<SecurityOccupationalHazardPositionPersonnelList>(SecurityOccupationalHazardPositionPersonnelList.class);
            personnelList = util.importExcel(file.getInputStream());
            logger.info("Excel解析完成，解析到 {} 条数据", personnelList != null ? personnelList.size() : 0);

            if (personnelList != null && !personnelList.isEmpty()) {
                SecurityOccupationalHazardPositionPersonnelList firstRecord = personnelList.get(0);
                logger.info("第一条数据示例 - 班组: {}, 姓名: {}, 性别: {}, 工种: {}",
                        firstRecord.getTeamName(), firstRecord.getName(),
                        firstRecord.getGender(), firstRecord.getJobType());
            } else {
                logger.warn("Excel解析结果为空，可能的原因：1.表头不匹配 2.没有数据行 3.文件格式错误");
            }
        } catch (Exception e) {
            logger.error("Excel解析失败", e);
            throw new RuntimeException("Excel文件解析失败: " + e.getMessage());
        }

        String operName = getUsername();
        String message = securityOccupationalHazardPositionPersonnelListService.importPersonnelList(personnelList, false, operName);
        return success(message);
    }

    /**
     * 下载导入模板（复杂版本，备用）
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:list')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        try {
            logger.info("开始生成危险职业岗位人员清单及管理台帐导入模板（复杂版）");
            // 使用自定义模板生成器
            OccupationalPersonnelExcelTemplate.generateTemplate(response);
            logger.info("复杂模板生成成功");
        } catch (Exception e) {
            logger.error("复杂模板下载失败，建议使用简单模板", e);
            throw new RuntimeException("模板下载失败: " + e.getMessage());
        }
    }

    /**
     * 下载导入模板（使用系统默认模板）
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:list')")
    @PostMapping("/importTemplateSimple")
    public void importTemplateSimple(HttpServletResponse response)
    {
        try {
            logger.info("开始生成危险职业岗位人员清单及管理台帐导入模板（简单版）");
            ExcelUtil<SecurityOccupationalHazardPositionPersonnelList> util = new ExcelUtil<SecurityOccupationalHazardPositionPersonnelList>(SecurityOccupationalHazardPositionPersonnelList.class);
            util.importTemplateExcel(response, "危险职业岗位人员清单及管理台帐导入模板");
            logger.info("简单模板生成成功");
        } catch (Exception e) {
            logger.error("简单模板生成失败", e);
            throw new RuntimeException("模板下载失败: " + e.getMessage());
        }
    }

    /**
     * 根据关联ID查询危险职业岗位人员清单及管理台帐列表
     */
    @PreAuthorize("@ss.hasPermi('security:OccupationaPersonnelList:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public TableDataInfo listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        logger.info("根据关联ID查询危险职业岗位人员清单及管理台帐列表，关联ID: {}", relatedId);
        startPage();
        List<SecurityOccupationalHazardPositionPersonnelList> list = securityOccupationalHazardPositionPersonnelListService.selectByRelatedId(relatedId);
        logger.info("查询到 {} 条关联数据", list.size());
        return getDataTable(list);
    }
}
