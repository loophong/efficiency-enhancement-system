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
import com.ruoyi.security.domain.SecurityHazardPointLedger;
import com.ruoyi.security.service.ISecurityHazardPointLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 有点害台账Controller
 * 
 * @author wang
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/security/hazardpointledger")
public class SecurityHazardPointLedgerController extends BaseController
{
    private static final Logger logger = LoggerFactory.getLogger(SecurityHazardPointLedgerController.class);

    @Autowired
    private ISecurityHazardPointLedgerService securityHazardPointLedgerService;

    /**
     * 查询有点害台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityHazardPointLedger securityHazardPointLedger)
    {
        startPage();
        List<SecurityHazardPointLedger> list = securityHazardPointLedgerService.selectSecurityHazardPointLedgerList(securityHazardPointLedger);
        return getDataTable(list);
    }

    /**
     * 导出有点害台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:export')")
    @Log(title = "有点害台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityHazardPointLedger securityHazardPointLedger)
    {
        List<SecurityHazardPointLedger> list = securityHazardPointLedgerService.selectSecurityHazardPointLedgerList(securityHazardPointLedger);
        ExcelUtil<SecurityHazardPointLedger> util = new ExcelUtil<SecurityHazardPointLedger>(SecurityHazardPointLedger.class);
        util.exportExcel(response, list, "有点害台账数据");
    }

    /**
     * 获取有点害台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityHazardPointLedgerService.selectSecurityHazardPointLedgerById(id));
    }

    /**
     * 新增有点害台账
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:add')")
    @Log(title = "有点害台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityHazardPointLedger securityHazardPointLedger)
    {
        return toAjax(securityHazardPointLedgerService.insertSecurityHazardPointLedger(securityHazardPointLedger));
    }

    /**
     * 修改有点害台账
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:edit')")
    @Log(title = "有点害台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityHazardPointLedger securityHazardPointLedger)
    {
        return toAjax(securityHazardPointLedgerService.updateSecurityHazardPointLedger(securityHazardPointLedger));
    }

    /**
     * 删除有点害台账
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:remove')")
    @Log(title = "有点害台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityHazardPointLedgerService.deleteSecurityHazardPointLedgerByIds(ids));
    }

    /**
     * 导入有点害台账数据
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:import')")
    @Log(title = "有点害台账", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) throws Exception {
        logger.info("开始导入有点害台账数据，文件名: {}, 文件大小: {} bytes",
                file.getOriginalFilename(), file.getSize());

        List<SecurityHazardPointLedger> hazardPointLedgerList;
        try {
            ExcelUtil<SecurityHazardPointLedger> util = new ExcelUtil<SecurityHazardPointLedger>(SecurityHazardPointLedger.class);
            hazardPointLedgerList = util.importExcel(file.getInputStream());
            logger.info("Excel解析完成，解析到 {} 条数据", hazardPointLedgerList != null ? hazardPointLedgerList.size() : 0);

            if (hazardPointLedgerList != null && !hazardPointLedgerList.isEmpty()) {
                SecurityHazardPointLedger firstRecord = hazardPointLedgerList.get(0);
                logger.info("第一条数据示例 - 评价单元: {}, 岗位: {}, 检测地点: {}",
                        firstRecord.getEvaluationUnit(), firstRecord.getPosition(),
                        firstRecord.getInspectionLocation());
            } else {
                logger.warn("Excel解析结果为空，可能的原因：1.表头不匹配 2.没有数据行 3.文件格式错误");
            }
        } catch (Exception e) {
            logger.error("Excel解析失败", e);
            throw new RuntimeException("Excel文件解析失败: " + e.getMessage());
        }

        String operName = getUsername();
        String message = securityHazardPointLedgerService.importHazardPointLedgerList(hazardPointLedgerList, false, operName);
        return success(message);
    }

    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:list')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        try {
            logger.info("开始生成有点害台账导入模板");
            ExcelUtil<SecurityHazardPointLedger> util = new ExcelUtil<SecurityHazardPointLedger>(SecurityHazardPointLedger.class);
            util.importTemplateExcel(response, "有点害台账导入模板");
            logger.info("模板生成成功");
        } catch (Exception e) {
            logger.error("模板生成失败", e);
            throw new RuntimeException("模板下载失败: " + e.getMessage());
        }
    }

    /**
     * 根据关联ID查询有点害台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardpointledger:list')")
    @GetMapping("/listByRelatedId/{relatedId}")
    public TableDataInfo listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        logger.info("根据关联ID查询有点害台账列表，关联ID: {}", relatedId);
        startPage();
        List<SecurityHazardPointLedger> list = securityHazardPointLedgerService.selectByRelatedId(relatedId);
        logger.info("查询到 {} 条关联数据", list.size());
        return getDataTable(list);
    }
}
