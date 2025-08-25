package com.ruoyi.security.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.security.domain.SecurityRequireExpectParty;
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
import com.ruoyi.security.domain.SecurityEnvironmentalOrganizationDescription;
import com.ruoyi.security.service.ISecurityEnvironmentalOrganizationDescriptionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

// 導入POI相關類
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import java.io.OutputStream;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.security.utils.FileImportUtil;
import com.ruoyi.security.utils.ThreadLocalContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 环境识别Controller
 *
 * @author wang
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/security/environmentidicaation")
public class SecurityEnvironmentalOrganizationDescriptionController extends BaseController
{
    @Autowired
    private ISecurityEnvironmentalOrganizationDescriptionService securityEnvironmentalOrganizationDescriptionService;

    /**
     * 查询环境识别列表
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        startPage();
        List<SecurityEnvironmentalOrganizationDescription> list = securityEnvironmentalOrganizationDescriptionService.selectSecurityEnvironmentalOrganizationDescriptionList(securityEnvironmentalOrganizationDescription);
        return getDataTable(list);
    }


    /**
     * 导出环境识别列表（自定義合併功能）
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:export')")
    @Log(title = "环境识别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        List<SecurityEnvironmentalOrganizationDescription> list = securityEnvironmentalOrganizationDescriptionService.selectSecurityEnvironmentalOrganizationDescriptionList(securityEnvironmentalOrganizationDescription);
        
        // 按環境分組並排序，確保相同環境的數據相鄰
        list.sort((a, b) -> {
            if (a.getEnvironment() == null && b.getEnvironment() == null) {
                return 0;
            }
            if (a.getEnvironment() == null) {
                return 1;
            }
            if (b.getEnvironment() == null) {
                return -1;
            }
            return a.getEnvironment().compareTo(b.getEnvironment());
        });
        
        try {
            // 創建自定義導出
            exportWithMerge(response, list);
        } catch (Exception e) {
            throw new ServiceException("导出失败：" + e.getMessage());
        }
    }
    
    /**
     * 自定義導出方法，實現相同環境值的合併
     */
    private void exportWithMerge(HttpServletResponse response, List<SecurityEnvironmentalOrganizationDescription> list) throws Exception {
        // 創建工作簿
        Workbook workbook = new SXSSFWorkbook(500);
        Sheet sheet = workbook.createSheet("环境识别数据");
        
        // 創建樣式
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        
        // 創建標題行
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(20);
        
        // 設置列標題
        String[] headers = {"环境", "环境要素", "环境要素描述"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 20 * 256); // 設置列寬
        }
        
        // 填充數據並處理合併
        int rowNum = 1;
        String currentEnvironment = null;
        int mergeStartRow = 1;
        
        for (SecurityEnvironmentalOrganizationDescription item : list) {
            Row dataRow = sheet.createRow(rowNum);
            dataRow.setHeightInPoints(18);
            
            // 環境列
            Cell envCell = dataRow.createCell(0);
            envCell.setCellValue(item.getEnvironment() != null ? item.getEnvironment() : "");
            envCell.setCellStyle(dataStyle);
            
            // 環境要素列
            Cell featuresCell = dataRow.createCell(1);
            featuresCell.setCellValue(item.getFeatures() != null ? item.getFeatures() : "");
            featuresCell.setCellStyle(dataStyle);
            
            // 環境要素描述列
            Cell descCell = dataRow.createCell(2);
            descCell.setCellValue(item.getDescription() != null ? item.getDescription() : "");
            descCell.setCellStyle(dataStyle);
            
            // 處理環境列的合併
            if (currentEnvironment == null) {
                currentEnvironment = item.getEnvironment();
                mergeStartRow = rowNum;
            } else if (!currentEnvironment.equals(item.getEnvironment())) {
                // 環境發生變化，合併之前的行
                if (rowNum - 1 > mergeStartRow) {
                    sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, rowNum - 1, 0, 0));
                }
                currentEnvironment = item.getEnvironment();
                mergeStartRow = rowNum;
            }
            
            rowNum++;
        }
        
        // 處理最後一組環境的合併
        if (rowNum - 1 > mergeStartRow) {
            sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, rowNum - 1, 0, 0));
        }
        
        // 設置響應頭
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = "环境识别数据_" + System.currentTimeMillis() + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
        
        // 寫入響應流
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
    }
    
    /**
     * 創建表頭樣式
     */
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        
        return style;
    }
    
    /**
     * 創建數據樣式
     */
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        
        return style;
    }

    /**
     * 获取环境识别详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityEnvironmentalOrganizationDescriptionService.selectSecurityEnvironmentalOrganizationDescriptionById(id));
    }
    
    /**
     * 根据关联ID查询环境识别列表
     */
    @GetMapping("/listByRelatedId/{relatedId}")
    public AjaxResult listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        if (relatedId == null) {
            return AjaxResult.error("关联ID不能为空");
        }
        
        SecurityEnvironmentalOrganizationDescription query = new SecurityEnvironmentalOrganizationDescription();
        query.setRelatedId(relatedId);
        
        List<SecurityEnvironmentalOrganizationDescription> list = 
            securityEnvironmentalOrganizationDescriptionService.selectSecurityEnvironmentalOrganizationDescriptionList(query);
        
        return success(list);
    }
    
    /**
     * 新增环境识别
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:add')")
    @Log(title = "环境识别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        return toAjax(securityEnvironmentalOrganizationDescriptionService.insertSecurityEnvironmentalOrganizationDescription(securityEnvironmentalOrganizationDescription));
    }

    /**
     * 修改环境识别
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:edit')")
    @Log(title = "环境识别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        return toAjax(securityEnvironmentalOrganizationDescriptionService.updateSecurityEnvironmentalOrganizationDescription(securityEnvironmentalOrganizationDescription));
    }

    /**
     * 删除环境识别
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:remove')")
    @Log(title = "环境识别", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityEnvironmentalOrganizationDescriptionService.deleteSecurityEnvironmentalOrganizationDescriptionByIds(ids));
    }

    /**
     * 导入环境识别数据
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:import')")
    @Log(title = "环境识别", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) {
        try {
            // 使用原有逻辑导入数据
            ExcelUtil<SecurityEnvironmentalOrganizationDescription> util = new ExcelUtil<>(SecurityEnvironmentalOrganizationDescription.class);
            List<SecurityEnvironmentalOrganizationDescription> list = util.importExcel(file.getInputStream());
            // 过滤有效行
            List<SecurityEnvironmentalOrganizationDescription> validList = list.stream()
                .filter(item -> item != null && (
                    (item.getEnvironment() != null && !item.getEnvironment().trim().isEmpty())
                    || (item.getFeatures() != null && !item.getFeatures().trim().isEmpty())
                    || (item.getDescription() != null && !item.getDescription().trim().isEmpty())
                ))
                .collect(Collectors.toList());
                
            if (validList.isEmpty()) {
                return AjaxResult.error("导入失败：无有效数据");
            }
            //处理内部环境的单元格合并
            // 按顺序处理数据，保持Excel中的顺序
            String currentPartyInvolved = null;


            for (SecurityEnvironmentalOrganizationDescription item : validList) {
                // 处理相关方类型的合并单元格
                if (item.getEnvironment() == null || item.getEnvironment().trim().isEmpty()) {
                    if (currentPartyInvolved != null) {
                        item.setEnvironment(currentPartyInvolved);
                    } else {
                        // 如果没有前一个值，跳过这一行
                        continue;
                    }
                } else {
                    currentPartyInvolved = item.getEnvironment();
                }
            }

            // 插入数据
            int successCount = 0;
            for (SecurityEnvironmentalOrganizationDescription item : validList) {
                successCount += securityEnvironmentalOrganizationDescriptionService.insertSecurityEnvironmentalOrganizationDescription(item);
            }
            
            return AjaxResult.success("导入成功，共 " + successCount + " 条数据");
        } catch (Exception e) {
            throw new ServiceException("导入失败：" + e.getMessage());
        }
    }
    
    /**
     * 原有的导入逻辑，当没有关联 ID 时使用
     */
//    private AjaxResult importDataWithoutRelatedId(MultipartFile file) {
//        try {
//            ExcelUtil<SecurityEnvironmentalOrganizationDescription> util = new ExcelUtil<>(SecurityEnvironmentalOrganizationDescription.class);
//            List<SecurityEnvironmentalOrganizationDescription> list = util.importExcel(file.getInputStream());
//
//            // 自动补齐环境字段
//            String lastEnv = null;
//            for (SecurityEnvironmentalOrganizationDescription item : list) {
//                if (item == null) continue;
//
//                // 处理环境字段
//                if (item.getEnvironment() != null && !item.getEnvironment().trim().isEmpty()) {
//                    lastEnv = item.getEnvironment();
//                } else {
//                    item.setEnvironment(lastEnv);
//                }
//
//                // 设置创建时间
//                if (item.getCreateTime() == null) {
//                    item.setCreateTime(DateUtils.getNowDate());
//                }
//
//                // 初始化父ID为0(顶级节点)
//                if (item.getParentId() == null) {
//                    item.setParentId(0L);
//                }
//            }
//
//            // 过滤有效行
//            List<SecurityEnvironmentalOrganizationDescription> validList = list.stream()
//                .filter(item -> item != null && (
//                    (item.getEnvironment() != null && !item.getEnvironment().trim().isEmpty())
//                    || (item.getFeatures() != null && !item.getFeatures().trim().isEmpty())
//                    || (item.getDescription() != null && !item.getDescription().trim().isEmpty())
//                ))
//                .collect(Collectors.toList());
//
//            if (validList.isEmpty()) {
//                return AjaxResult.error("导入失败：无有效数据");
//            }
//
//            // 插入数据
//            int successCount = 0;
//            for (SecurityEnvironmentalOrganizationDescription item : validList) {
//                successCount += securityEnvironmentalOrganizationDescriptionService.insertSecurityEnvironmentalOrganizationDescription(item);
//            }
//
//            return AjaxResult.success("导入成功，共 " + successCount + " 条数据");
//        } catch (Exception e) {
//            throw new ServiceException("导入失败：" + e.getMessage());
//        }
//    }

    /**
     * 下载导入模板（只包含三个字段，并合并单元格）
     */
    @PreAuthorize("@ss.hasPermi('security:environmentidicaation:import')")
    @GetMapping("/import/template")
    public void importTemplate(HttpServletResponse response) {
        try {
            List<SecurityEnvironmentalOrganizationDescription> list = new ArrayList<>();
            
            // 添加模板说明
            SecurityEnvironmentalOrganizationDescription headerNote = new SecurityEnvironmentalOrganizationDescription();
            headerNote.setEnvironment("导入说明");
            headerNote.setFeatures("填写环境识别数据");
            headerNote.setDescription("填写时环境可以留空，系统会自动继承上一行的环境值");
            list.add(headerNote);
            
            // 添加示例数据
            SecurityEnvironmentalOrganizationDescription example1 = new SecurityEnvironmentalOrganizationDescription();
            example1.setEnvironment("内部环境");
            example1.setFeatures("组织结构");
            example1.setDescription("公司采用职能制组织结构");
            list.add(example1);
            
            SecurityEnvironmentalOrganizationDescription example2 = new SecurityEnvironmentalOrganizationDescription();
            example2.setEnvironment("内部环境");
            example2.setFeatures("领导作风");
            example2.setDescription("管理层重视产品质量和环境保护");
            list.add(example2);
            
            SecurityEnvironmentalOrganizationDescription example3 = new SecurityEnvironmentalOrganizationDescription();
            example3.setEnvironment("外部环境");
            example3.setFeatures("法律法规");
            example3.setDescription("严格遵守环保法规和安全生产标准");
            list.add(example3);
            
            // 导出三列模板
            ExcelUtil<SecurityEnvironmentalOrganizationDescription> util = new ExcelUtil<>(SecurityEnvironmentalOrganizationDescription.class);
            util.showColumn("environment", "features", "description");
            util.exportExcel(response, list, "环境识别模板");
        } catch (Exception e) {
            throw new ServiceException("模板下载失败：" + e.getMessage());
        }
    }
}
