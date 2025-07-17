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
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.security.domain.SecurityHazardousMaterialsHandlingLedger;
import com.ruoyi.security.service.ISecurityHazardousMaterialsHandlingLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 危化品处理台账Controller
 *
 * @author wang
 * @date 2025-03-03
 */
@Slf4j
@RestController
@RequestMapping("/security/hazardousledger")
public class SecurityHazardousMaterialsHandlingLedgerController extends BaseController
{
    @Autowired
    private ISecurityHazardousMaterialsHandlingLedgerService securityHazardousMaterialsHandlingLedgerService;

    /**
     * 查询危化品处理台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousledger:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger)
    {
        System.out.println("接收到的查询参数: " + securityHazardousMaterialsHandlingLedger);
        System.out.println("isCompliant值: [" + securityHazardousMaterialsHandlingLedger.getIsCompliant() + "]");
        System.out.println("isCompliant是否为空: " + (securityHazardousMaterialsHandlingLedger.getIsCompliant() == null));
        System.out.println("isCompliant是否为空字符串: " + "".equals(securityHazardousMaterialsHandlingLedger.getIsCompliant()));

        // 临时解决方案：如果前端没有传递isCompliant参数，则设置为null
        if ("否".equals(securityHazardousMaterialsHandlingLedger.getIsCompliant())) {
            System.out.println("检测到默认值'否'，设置为null以查询所有数据");
            securityHazardousMaterialsHandlingLedger.setIsCompliant(null);
        }

        startPage();
        List<SecurityHazardousMaterialsHandlingLedger> list = securityHazardousMaterialsHandlingLedgerService.selectSecurityHazardousMaterialsHandlingLedgerList(securityHazardousMaterialsHandlingLedger);
        System.out.println("查询到的数据条数: " + (list != null ? list.size() : 0));
        if (list != null && !list.isEmpty()) {
            System.out.println("第一条数据: " + list.get(0));
        }
        return getDataTable(list);
    }

    /**
     * 导出危化品处理台账列表
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousledger:export')")
    @Log(title = "危化品处理台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger)
    {
        List<SecurityHazardousMaterialsHandlingLedger> list = securityHazardousMaterialsHandlingLedgerService.selectSecurityHazardousMaterialsHandlingLedgerList(securityHazardousMaterialsHandlingLedger);
        ExcelUtil<SecurityHazardousMaterialsHandlingLedger> util = new ExcelUtil<SecurityHazardousMaterialsHandlingLedger>(SecurityHazardousMaterialsHandlingLedger.class);
        util.exportExcel(response, list, "危化品处理台账数据");
    }

    /**
     * 获取危化品处理台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousledger:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityHazardousMaterialsHandlingLedgerService.selectSecurityHazardousMaterialsHandlingLedgerById(id));
    }

    /**
     * 新增危化品处理台账
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousledger:add')")
    @Log(title = "危化品处理台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger)
    {
        return toAjax(securityHazardousMaterialsHandlingLedgerService.insertSecurityHazardousMaterialsHandlingLedger(securityHazardousMaterialsHandlingLedger));
    }

    /**
     * 修改危化品处理台账
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousledger:edit')")
    @Log(title = "危化品处理台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger)
    {
        return toAjax(securityHazardousMaterialsHandlingLedgerService.updateSecurityHazardousMaterialsHandlingLedger(securityHazardousMaterialsHandlingLedger));
    }

    /**
     * 删除危化品处理台账
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousledger:remove')")
    @Log(title = "危化品处理台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityHazardousMaterialsHandlingLedgerService.deleteSecurityHazardousMaterialsHandlingLedgerByIds(ids));
    }

    /**
     * 导入危化品处理台账
     */
    @Log(title = "[危化品处理台账]上传", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public void importTable(MultipartFile file) {
        log.info("传入的参数为 " + file.getName() + " 文件");
        try {
            securityHazardousMaterialsHandlingLedgerService.readSalaryExcelToDB(file.getOriginalFilename(), file);
        } catch (Exception e) {
            log.error("读取 " + file.getName() + " 文件失败, 原因: {}", e.getMessage());
            throw new ServiceException("读取 " + file.getName() + " 文件失败");
        }
    }

    /**
     * 下载危化品处理台账导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:hazardousledger:import')")
    @Log(title = "危化品处理台账模板", businessType = BusinessType.EXPORT)
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response)
    {
        // 创建示例数据
        List<SecurityHazardousMaterialsHandlingLedger> sampleData = createSampleData();

        ExcelUtil<SecurityHazardousMaterialsHandlingLedger> util = new ExcelUtil<SecurityHazardousMaterialsHandlingLedger>(SecurityHazardousMaterialsHandlingLedger.class);
        util.exportExcel(response, sampleData, "危化品处理台账导入模板");
    }

    /**
     * 创建示例数据
     */
    private List<SecurityHazardousMaterialsHandlingLedger> createSampleData() {
        List<SecurityHazardousMaterialsHandlingLedger> sampleList = new ArrayList<>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // 示例1：硫酸处理
            SecurityHazardousMaterialsHandlingLedger sample1 = new SecurityHazardousMaterialsHandlingLedger();
            sample1.setHandlingTime(sdf.parse("2024-01-15"));
            sample1.setChemicalName("硫酸");
            sample1.setUserInCharge("张三");
            sample1.setHandlingQuantity(new BigDecimal("10.5"));
            sample1.setHandlingMethod("中和处理");
            sample1.setIsCompliant("是");
            sample1.setHandler("李四");
            sampleList.add(sample1);

            // 示例2：盐酸处理
            SecurityHazardousMaterialsHandlingLedger sample2 = new SecurityHazardousMaterialsHandlingLedger();
            sample2.setHandlingTime(sdf.parse("2024-01-20"));
            sample2.setChemicalName("盐酸");
            sample2.setUserInCharge("王五");
            sample2.setHandlingQuantity(new BigDecimal("5.2"));
            sample2.setHandlingMethod("稀释处理");
            sample2.setIsCompliant("是");
            sample2.setHandler("赵六");
            sampleList.add(sample2);

            // 示例3：丙酮处理
            SecurityHazardousMaterialsHandlingLedger sample3 = new SecurityHazardousMaterialsHandlingLedger();
            sample3.setHandlingTime(sdf.parse("2024-01-25"));
            sample3.setChemicalName("丙酮");
            sample3.setUserInCharge("孙七");
            sample3.setHandlingQuantity(new BigDecimal("2.8"));
            sample3.setHandlingMethod("回收处理");
            sample3.setIsCompliant("否");
            sample3.setHandler("周八");
            sampleList.add(sample3);

        } catch (Exception e) {
            log.error("创建示例数据失败: {}", e.getMessage());
        }

        return sampleList;
    }
}
