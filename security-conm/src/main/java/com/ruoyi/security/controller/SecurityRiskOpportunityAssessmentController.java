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
import com.ruoyi.security.domain.SecurityRiskOpportunityAssessment;
import com.ruoyi.security.service.ISecurityRiskOpportunityAssessmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

// POI 相關導入
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 风险和机遇评估及控制措施Controller
 * 
 * @author wang
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/security/assessment")
public class SecurityRiskOpportunityAssessmentController extends BaseController
{
    @Autowired
    private ISecurityRiskOpportunityAssessmentService securityRiskOpportunityAssessmentService;

    /**
     * 查询风险和机遇评估及控制措施列表
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        startPage();
        List<SecurityRiskOpportunityAssessment> list = securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentList(securityRiskOpportunityAssessment);
        return getDataTable(list);
    }

    /**
     * 导出风险和机遇评估及控制措施列表
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:export')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment) throws Exception
    {
        List<SecurityRiskOpportunityAssessment> list = securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentList(securityRiskOpportunityAssessment);
        
        // 创建工作簿
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        // 创建工作表
        Sheet sheet = workbook.createSheet("风险和机遇评估");
        
        // 设置列宽
        sheet.setColumnWidth(0, 12 * 256); // A - 活动/过程
        sheet.setColumnWidth(1, 25 * 256); // B - 风险和机遇
        sheet.setColumnWidth(2, 25 * 256); // C - 造成后果
        sheet.setColumnWidth(3, 10 * 256); // D - 严重程度
        sheet.setColumnWidth(4, 10 * 256); // E - 发生频次
        sheet.setColumnWidth(5, 10 * 256); // F - 风险系数
        sheet.setColumnWidth(6, 6 * 256);  // G - 高
        sheet.setColumnWidth(7, 6 * 256);  // H - 一般
        sheet.setColumnWidth(8, 6 * 256);  // I - 低
        sheet.setColumnWidth(9, 30 * 256); // J - 应对措施
        sheet.setColumnWidth(10, 12 * 256); // K - 实施时间
        sheet.setColumnWidth(11, 18 * 256); // L - 部门
        
        // 创建表头样式
        CellStyle headerStyle = createHeaderStyle(workbook);
        // 创建数据样式
        CellStyle dataStyle = createDataStyle(workbook);
        // 创建居中样式
        CellStyle centerStyle = createCenterStyle(workbook);
        
        // 创建表头行 - 空行
        Row headerRow1 = sheet.createRow(0);
        Row headerRow2 = sheet.createRow(1);
        
        // 创建表头行3 - 评价、风险等级、实施
        Row headerRow3 = sheet.createRow(2);
        createHeaderCell(headerRow3, 2, "评价", headerStyle);
        createHeaderCell(headerRow3, 6, "风险等级", headerStyle);
        createHeaderCell(headerRow3, 10, "实施", headerStyle);
        
        // 合并单元格 - 评价、风险等级、实施
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 5)); // C3:F3
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, 8)); // G3:I3
        
        // 创建表头行4 - 具体列名
        Row headerRow4 = sheet.createRow(3);
        String[] headers = {"活动/过程", "风险和机遇", "造成后果", "严重程度", "发生频次", "风险系数", "高", "一般", "低", "应对措施", "时间", "部门"};
        for (int i = 0; i < headers.length; i++) {
            createHeaderCell(headerRow4, i, headers[i], headerStyle);
        }
        
        // 填充数据
        int rowNum = 4;
        for (SecurityRiskOpportunityAssessment item : list) {
            Row row = sheet.createRow(rowNum++);
            
            // 活动/过程
            createCell(row, 0, item.getActivity(), dataStyle);
            // 风险和机遇
            createCell(row, 1, item.getRiskOpportunity(), dataStyle);
            // 造成后果
            createCell(row, 2, item.getConsequences(), dataStyle);
            // 严重程度
            createCell(row, 3, item.getSeverity(), centerStyle);
            // 发生频次
            createCell(row, 4, item.getFrequency(), centerStyle);
            // 风险系数
            createCell(row, 5, item.getRisk(), centerStyle);
            
            // 风险等级 - 高、一般、低
            if ("高".equals(item.getRiskLevel())) {
                createCell(row, 6, "√", centerStyle);
                createCell(row, 7, "", centerStyle);
                createCell(row, 8, "", centerStyle);
            } else if ("一般".equals(item.getRiskLevel())) {
                createCell(row, 6, "", centerStyle);
                createCell(row, 7, "√", centerStyle);
                createCell(row, 8, "", centerStyle);
            } else if ("低".equals(item.getRiskLevel())) {
                createCell(row, 6, "", centerStyle);
                createCell(row, 7, "", centerStyle);
                createCell(row, 8, "√", centerStyle);
            } else if ("-".equals(item.getRiskLevel())) {
                // 特殊情况 - 使用 - 符号
                createCell(row, 6, "-", centerStyle);
                createCell(row, 7, "-", centerStyle);
                createCell(row, 8, "-", centerStyle);
            } else {
                createCell(row, 6, "", centerStyle);
                createCell(row, 7, "", centerStyle);
                createCell(row, 8, "", centerStyle);
            }
            
            // 应对措施
            createCell(row, 9, item.getResponseMeasures(), dataStyle);
            
            // 实施时间
            String dateStr = "";
            if (item.getImplementationTime() != null) {
                dateStr = new java.text.SimpleDateFormat("yyyy.M.d").format(item.getImplementationTime());
            }
            createCell(row, 10, dateStr, dataStyle);
            
            // 部门
            createCell(row, 11, item.getDepartment(), dataStyle);
        }
        
        // 添加时间行
        Row timeRow = sheet.createRow(rowNum);
        createCell(timeRow, 0, "时间：" + new java.text.SimpleDateFormat("yyyy.M.d").format(new java.util.Date()), dataStyle);
        
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "风险和机遇评估_" + System.currentTimeMillis() + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
        
        // 写入响应流
        try (java.io.OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } finally {
            workbook.dispose();
        }
    }

    /**
     * 创建表头单元格
     */
    private void createHeaderCell(Row row, int column, String text, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(text);
        cell.setCellStyle(style);
    }

    /**
     * 创建数据单元格
     */
    private void createCell(Row row, int column, String text, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(text == null ? "" : text);
        cell.setCellStyle(style);
    }

    /**
     * 创建表头样式
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
     * 创建数据样式
     */
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
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
     * 创建居中样式
     */
    private CellStyle createCenterStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        
        return style;
    }

    /**
     * 获取风险和机遇评估及控制措施详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentById(id));
    }

    /**
     * 新增风险和机遇评估及控制措施
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:add')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return toAjax(securityRiskOpportunityAssessmentService.insertSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment));
    }

    /**
     * 修改风险和机遇评估及控制措施
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:edit')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return toAjax(securityRiskOpportunityAssessmentService.updateSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment));
    }

    /**
     * 删除风险和机遇评估及控制措施
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:remove')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityRiskOpportunityAssessmentService.deleteSecurityRiskOpportunityAssessmentByIds(ids));
    }
}
