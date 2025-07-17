package com.ruoyi.security.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.security.service.ISecurityHazardousChemicalInventoryService;
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
import com.ruoyi.security.domain.SecurityHazardousMaterialsSafetyInspection;
import com.ruoyi.security.service.ISecurityHazardousMaterialsSafetyInspectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 危化品检查记录Controller
 * 
 * @author wang
 * @date 2025-03-03
 */
@Slf4j
@RestController
@RequestMapping("/security/hazardousinspection")
public class SecurityHazardousMaterialsSafetyInspectionController extends BaseController
{
    @Autowired
    private ISecurityHazardousMaterialsSafetyInspectionService securityHazardousMaterialsSafetyInspectionService;

    /**
     * 查询危化品检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        startPage();
        List<SecurityHazardousMaterialsSafetyInspection> list = securityHazardousMaterialsSafetyInspectionService.selectSecurityHazardousMaterialsSafetyInspectionList(securityHazardousMaterialsSafetyInspection);
        return getDataTable(list);
    }

    /**
     * 导出危化品检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:export')")
    @Log(title = "危化品检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        List<SecurityHazardousMaterialsSafetyInspection> list = securityHazardousMaterialsSafetyInspectionService.selectSecurityHazardousMaterialsSafetyInspectionList(securityHazardousMaterialsSafetyInspection);
        ExcelUtil<SecurityHazardousMaterialsSafetyInspection> util = new ExcelUtil<SecurityHazardousMaterialsSafetyInspection>(SecurityHazardousMaterialsSafetyInspection.class);
        util.exportExcel(response, list, "危化品检查记录数据");
    }

    /**
     * 获取危化品检查记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityHazardousMaterialsSafetyInspectionService.selectSecurityHazardousMaterialsSafetyInspectionById(id));
    }

    /**
     * 新增危化品检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:add')")
    @Log(title = "危化品检查记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        return toAjax(securityHazardousMaterialsSafetyInspectionService.insertSecurityHazardousMaterialsSafetyInspection(securityHazardousMaterialsSafetyInspection));
    }

    /**
     * 修改危化品检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:edit')")
    @Log(title = "危化品检查记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        return toAjax(securityHazardousMaterialsSafetyInspectionService.updateSecurityHazardousMaterialsSafetyInspection(securityHazardousMaterialsSafetyInspection));
    }

    /**
     * 删除危化品检查记录
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:remove')")
    @Log(title = "危化品检查记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityHazardousMaterialsSafetyInspectionService.deleteSecurityHazardousMaterialsSafetyInspectionByIds(ids));
    }

    /**
     * 导入危化品检查记录
     */
    @Log(title = "[危险检查记录i]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public void importTable( MultipartFile file) {
        log.info("传入的参数为 " + file.getName() + " 文件");
        try {
            securityHazardousMaterialsSafetyInspectionService.readSalaryExcelToDB(file.getOriginalFilename(), file);
        } catch (Exception e) {
            log.error("读取 " + file.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + file.getName() + " 文件失败");
        }
    }

    /**
     * 下载危化品检查记录导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousinspection:import')")
    @Log(title = "危化品检查记录模板", businessType = BusinessType.EXPORT)
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response)
    {
        // 创建示例数据
        List<SecurityHazardousMaterialsSafetyInspection> sampleData = createSampleData();

        ExcelUtil<SecurityHazardousMaterialsSafetyInspection> util = new ExcelUtil<SecurityHazardousMaterialsSafetyInspection>(SecurityHazardousMaterialsSafetyInspection.class);
        util.exportExcel(response, sampleData, "危化品检查记录导入模板");
    }

    /**
     * 创建示例数据
     */
    private List<SecurityHazardousMaterialsSafetyInspection> createSampleData() {
        List<SecurityHazardousMaterialsSafetyInspection> sampleList = new ArrayList<>();

        // 示例1：储存区域检查
        SecurityHazardousMaterialsSafetyInspection sample1 = new SecurityHazardousMaterialsSafetyInspection();
        sample1.setInspectionItem("储存区域通风系统");
        sample1.setInspectionStandard("通风良好，无异味");
        sample1.setInspectionRecord("通风系统正常运行，无异常");
        sample1.setInspecter("张三");
        sample1.setInspectionTime(new Date());
        sampleList.add(sample1);

        // 示例2：安全标识检查
        SecurityHazardousMaterialsSafetyInspection sample2 = new SecurityHazardousMaterialsSafetyInspection();
        sample2.setInspectionItem("安全警示标识");
        sample2.setInspectionStandard("标识清晰完整");
        sample2.setInspectionRecord("部分标识模糊，需要更换");
        sample2.setInspecter("李四");
        sample2.setInspectionTime(new Date());
        sampleList.add(sample2);

        // 示例3：防护设备检查
        SecurityHazardousMaterialsSafetyInspection sample3 = new SecurityHazardousMaterialsSafetyInspection();
        sample3.setInspectionItem("个人防护设备");
        sample3.setInspectionStandard("防护服、手套、护目镜齐全");
        sample3.setInspectionRecord("防护设备齐全，状态良好");
        sample3.setInspecter("王五");
        sample3.setInspectionTime(new Date());
        sampleList.add(sample3);

        return sampleList;
    }

}


