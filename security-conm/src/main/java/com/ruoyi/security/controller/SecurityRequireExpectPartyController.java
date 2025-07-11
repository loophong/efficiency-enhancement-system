package com.ruoyi.security.controller;

import java.util.List;
import java.util.stream.Collectors;
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
import com.ruoyi.security.domain.SecurityRequireExpectParty;
import com.ruoyi.security.service.ISecurityRequireExpectPartyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;

/**
 * 相关方期望Controller
 *
 * @author wang
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/security/RequireExpectParty")
public class SecurityRequireExpectPartyController extends BaseController
{
    @Autowired
    private ISecurityRequireExpectPartyService securityRequireExpectPartyService;

    /**
     * 查询相关方期望列表
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRequireExpectParty securityRequireExpectParty)
    {
        startPage();
        List<SecurityRequireExpectParty> list = securityRequireExpectPartyService.selectSecurityRequireExpectPartyList(securityRequireExpectParty);
        return getDataTable(list);
    }

    /**
     * 导出相关方期望列表
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:export')")
    @Log(title = "相关方期望", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRequireExpectParty securityRequireExpectParty)
    {
        List<SecurityRequireExpectParty> list = securityRequireExpectPartyService.selectSecurityRequireExpectPartyList(securityRequireExpectParty);
        ExcelUtil<SecurityRequireExpectParty> util = new ExcelUtil<SecurityRequireExpectParty>(SecurityRequireExpectParty.class);
        // 只导出必要的列，不包含审批时间、填报时间、审批状态、审批部门
        util.showColumn("id", "partyInvolved", "demand", "monitoringIndicators", "monitoringFrequency", "monitoringDepartment");
        util.exportExcel(response, list, "相关方期望数据");
    }

    /**
     * 获取相关方期望详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityRequireExpectPartyService.selectSecurityRequireExpectPartyById(id));
    }

    /**
     * 新增相关方期望
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:add')")
    @Log(title = "相关方期望", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRequireExpectParty securityRequireExpectParty)
    {
        return toAjax(securityRequireExpectPartyService.insertSecurityRequireExpectParty(securityRequireExpectParty));
    }

    /**
     * 修改相关方期望
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:edit')")
    @Log(title = "相关方期望", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRequireExpectParty securityRequireExpectParty)
    {
        return toAjax(securityRequireExpectPartyService.updateSecurityRequireExpectParty(securityRequireExpectParty));
    }

    /**
     * 删除相关方期望
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:remove')")
    @Log(title = "相关方期望", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityRequireExpectPartyService.deleteSecurityRequireExpectPartyByIds(ids));
    }

    @GetMapping("/listByRelatedId/{relatedId}")
    public AjaxResult listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        if (relatedId == null) {
            return AjaxResult.error("关联ID不能为空");
        }

        SecurityRequireExpectParty query = new SecurityRequireExpectParty();
        query.setRelatedId(relatedId);
        List<SecurityRequireExpectParty> list =
                securityRequireExpectPartyService.selectSecurityRequireExpectPartyList(query);

        return success(list);
    }
    
    /**
     * 导入相关方期望数据
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:import')")
    @Log(title = "相关方期望", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) {
        try {
            ExcelUtil<SecurityRequireExpectParty> util = new ExcelUtil<>(SecurityRequireExpectParty.class);
            List<SecurityRequireExpectParty> list = util.importExcel(file.getInputStream());
            
            // 过滤有效行
            List<SecurityRequireExpectParty> validList = list.stream()
                .filter(item -> item != null && (
                    (item.getPartyInvolved() != null && !item.getPartyInvolved().trim().isEmpty())
                    || (item.getDemand() != null && !item.getDemand().trim().isEmpty())
                    || (item.getMonitoringIndicators() != null && !item.getMonitoringIndicators().trim().isEmpty())
                ))
                .collect(Collectors.toList());
            
            // 按顺序处理数据，保持Excel中的顺序
            String currentPartyInvolved = null;
            String currentDemand = null;
            
            for (SecurityRequireExpectParty item : validList) {
                // 处理相关方类型的合并单元格
                if (item.getPartyInvolved() == null || item.getPartyInvolved().trim().isEmpty()) {
                    if (currentPartyInvolved != null) {
                        item.setPartyInvolved(currentPartyInvolved);
                    } else {
                        // 如果没有前一个值，跳过这一行
                        continue;
                    }
                } else {
                    currentPartyInvolved = item.getPartyInvolved();
                    currentDemand = null; // 重置需求和期望，因为相关方类型变了
                }
                
                // 处理需求和期望的合并单元格
                if (item.getDemand() == null || item.getDemand().trim().isEmpty()) {
                    if (currentDemand != null) {
                        item.setDemand(currentDemand);
                    } else {
                        // 如果没有前一个值，使用默认值
                        item.setDemand("未指定");
                    }
                } else {
                    currentDemand = item.getDemand();
                }
                
                // 插入数据
                securityRequireExpectPartyService.insertSecurityRequireExpectParty(item);
            }
            
            return AjaxResult.success("导入成功，共" + validList.size() + "条数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("导入失败：" + e.getMessage());
        }
    }

    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:RequireExpectParty:import')")
    @GetMapping("/import/template")
    public void importTemplate(HttpServletResponse response) {
        try {
            // 创建示例数据，展示合并单元格效果
            List<SecurityRequireExpectParty> list = new java.util.ArrayList<>();
            
            // 示例1: 客户相关方类型
            SecurityRequireExpectParty example1 = new SecurityRequireExpectParty();
            example1.setId(1L);
            example1.setPartyInvolved("客户");
            example1.setDemand("质量符合顾客要求");
            example1.setMonitoringIndicators("客户满意度");
            example1.setMonitoringFrequency("年考核");
            example1.setMonitoringDepartment("质量科");
            list.add(example1);
            
            // 示例2: 同样的客户类型，不同需求
            SecurityRequireExpectParty example2 = new SecurityRequireExpectParty();
            example2.setId(2L);
            example2.setPartyInvolved("客户");
            example2.setDemand("及时交付");
            example2.setMonitoringIndicators("顾客反馈质量问题回复率");
            example2.setMonitoringFrequency("月考核");
            example2.setMonitoringDepartment("质量科");
            list.add(example2);
            
            // 示例3: 供方相关方类型
            SecurityRequireExpectParty example3 = new SecurityRequireExpectParty();
            example3.setId(3L);
            example3.setPartyInvolved("供方");
            example3.setDemand("长期合作、双赢");
            example3.setMonitoringIndicators("供应商年审考评表");
            example3.setMonitoringFrequency("年考核");
            example3.setMonitoringDepartment("质量科");
            list.add(example3);
            
            ExcelUtil<SecurityRequireExpectParty> util = new ExcelUtil<>(SecurityRequireExpectParty.class);
            // 只显示需要的列：序号、相关方类型、需求和期望、监测指标、监测频率、监测部门
            util.showColumn("id", "partyInvolved", "demand", "monitoringIndicators", "monitoringFrequency", "monitoringDepartment");
            util.exportExcel(response, list, "相关方期望模板");
        } catch (Exception e) {
            throw new ServiceException("模板下载失败：" + e.getMessage());
        }
    }
}
