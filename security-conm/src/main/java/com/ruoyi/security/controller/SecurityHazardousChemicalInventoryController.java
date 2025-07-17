package com.ruoyi.security.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
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
import com.ruoyi.security.domain.SecurityHazardousChemicalInventory;
import com.ruoyi.security.service.ISecurityHazardousChemicalInventoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 危险化学品台账Controller
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Slf4j
@RestController

@RequestMapping("/security/inventory")
public class SecurityHazardousChemicalInventoryController extends BaseController
{
    @Autowired
    private ISecurityHazardousChemicalInventoryService securityHazardousChemicalInventoryService;
    /**
     * 上传危险化学品台账列表
     */
    @Log(title = "[危险化学物品上传]上传", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('production:historyOrder:import')")
    @PostMapping("/import")
    public void importTable( MultipartFile file) {
        log.info("传入的参数为 " + file.getName() + " 文件");
        try {
            securityHazardousChemicalInventoryService.readSalaryExcelToDB(file.getOriginalFilename(), file);
        } catch (Exception e) {
            log.error("读取 " + file.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + file.getName() + " 文件失败");
        }
    }

    /**
     * 查询危险化学品台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        startPage();
        List<SecurityHazardousChemicalInventory> list = securityHazardousChemicalInventoryService.selectSecurityHazardousChemicalInventoryList(securityHazardousChemicalInventory);
        return getDataTable(list);
    }

    /**
     * 导出危险化学品台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:export')")
    @Log(title = "危险化学品台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        List<SecurityHazardousChemicalInventory> list = securityHazardousChemicalInventoryService.selectSecurityHazardousChemicalInventoryList(securityHazardousChemicalInventory);
        ExcelUtil<SecurityHazardousChemicalInventory> util = new ExcelUtil<SecurityHazardousChemicalInventory>(SecurityHazardousChemicalInventory.class);
        util.exportExcel(response, list, "危险化学品台账数据");
    }

    /**
     * 下载危险化学品台账导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:import')")
    @Log(title = "危险化学品台账模板", businessType = BusinessType.EXPORT)
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response)
    {
        // 创建示例数据
        List<SecurityHazardousChemicalInventory> sampleData = createSampleData();

        ExcelUtil<SecurityHazardousChemicalInventory> util = new ExcelUtil<SecurityHazardousChemicalInventory>(SecurityHazardousChemicalInventory.class);
        util.exportExcel(response, sampleData, "危险化学品台账导入模板");
    }

    /**
     * 创建示例数据
     */
    private List<SecurityHazardousChemicalInventory> createSampleData() {
        List<SecurityHazardousChemicalInventory> sampleList = new ArrayList<>();

        // 示例1：硫酸
        SecurityHazardousChemicalInventory sample1 = new SecurityHazardousChemicalInventory();
        sample1.setSubstanceName("硫酸");
        sample1.setHazardousMaterialsNumber("81007");
        sample1.setCasNumber("7664-93-9");
        sample1.setFireDangerClass("乙类");
        sample1.setAppearance("无色透明液体");
        sample1.setMaxDesignStock("500L");
        sample1.setAnnualUsage("2000L");
        sample1.setDailyStock("50L");
        sample1.setStorageLocation("化学品仓库A区");
        sample1.setManagementResponsiblePerson("张三");
        sample1.setContactNumber("13800138001");
        sample1.setRemarks("强腐蚀性，需专人管理");
        sampleList.add(sample1);

        // 示例2：盐酸
        SecurityHazardousChemicalInventory sample2 = new SecurityHazardousChemicalInventory();
        sample2.setSubstanceName("盐酸");
        sample2.setHazardousMaterialsNumber("81013");
        sample2.setCasNumber("7647-01-0");
        sample2.setFireDangerClass("乙类");
        sample2.setAppearance("无色或微黄色液体");
        sample2.setMaxDesignStock("300L");
        sample2.setAnnualUsage("1200L");
        sample2.setDailyStock("30L");
        sample2.setStorageLocation("化学品仓库B区");
        sample2.setManagementResponsiblePerson("李四");
        sample2.setContactNumber("13800138002");
        sample2.setRemarks("强酸性，避免与碱类接触");
        sampleList.add(sample2);

        // 示例3：丙酮
        SecurityHazardousChemicalInventory sample3 = new SecurityHazardousChemicalInventory();
        sample3.setSubstanceName("丙酮");
        sample3.setHazardousMaterialsNumber("32067");
        sample3.setCasNumber("67-64-1");
        sample3.setFireDangerClass("甲类");
        sample3.setAppearance("无色透明液体");
        sample3.setMaxDesignStock("200L");
        sample3.setAnnualUsage("800L");
        sample3.setDailyStock("20L");
        sample3.setStorageLocation("易燃品仓库");
        sample3.setManagementResponsiblePerson("王五");
        sample3.setContactNumber("13800138003");
        sample3.setRemarks("易燃易挥发，远离火源");
        sampleList.add(sample3);

        return sampleList;
    }

    /**
     * 获取危险化学品台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(securityHazardousChemicalInventoryService.selectSecurityHazardousChemicalInventoryById(id));
    }

    /**
     * 新增危险化学品台账
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:add')")
    @Log(title = "危险化学品台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        return toAjax(securityHazardousChemicalInventoryService.insertSecurityHazardousChemicalInventory(securityHazardousChemicalInventory));
    }

    /**
     * 修改危险化学品台账
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:edit')")
    @Log(title = "危险化学品台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        return toAjax(securityHazardousChemicalInventoryService.updateSecurityHazardousChemicalInventory(securityHazardousChemicalInventory));
    }

    /**
     * 删除危险化学品台账
     */
    @PreAuthorize("@ss.hasPermi('security:inventory:remove')")
    @Log(title = "危险化学品台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(securityHazardousChemicalInventoryService.deleteSecurityHazardousChemicalInventoryByIds(ids));
    }
}
