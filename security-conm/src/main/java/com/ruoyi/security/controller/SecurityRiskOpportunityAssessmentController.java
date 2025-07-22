package com.ruoyi.security.controller;

import java.util.List;

import com.ruoyi.security.domain.SecurityEnvironmentalOrganizationDescription;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.security.domain.SecurityRiskOpportunityAssessment;
import com.ruoyi.security.service.ISecurityRiskOpportunityAssessmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

// POI 相关导入
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
    public void export(HttpServletResponse response, SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        List<SecurityRiskOpportunityAssessment> list = securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentList(securityRiskOpportunityAssessment);

        // 调试日志：检查数据中的风险等级和风险系数
        for (SecurityRiskOpportunityAssessment item : list) {
            logger.info("导出数据 - ID: {}, 风险等级: [{}], 风险系数: [{}]",
                item.getId(), item.getRiskLevel(), item.getRisk());
        }

        ExcelUtil<SecurityRiskOpportunityAssessment> util = new ExcelUtil<SecurityRiskOpportunityAssessment>(SecurityRiskOpportunityAssessment.class);
        util.exportExcel(response, list, "风险和机遇评估及控制措施数据");
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
    
    /**
     * 导入风险和机遇评估及控制措施
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:import')")
    @Log(title = "风险和机遇评估及控制措施", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(@RequestParam("excelFile") MultipartFile file) {
        try {
            String message = securityRiskOpportunityAssessmentService.importAssessment(file);
            return success(message);
        } catch (Exception e) {
            logger.error("导入Excel数据失败: {}", e.getMessage());
            return error("导入Excel数据失败: " + e.getMessage());
        }
    }
    
    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:assessment:import')")
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        try {
            // 创建工作簿
            Workbook workbook = new SXSSFWorkbook();
            
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
            
            // 创建标题行样式
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle contentStyle = createCenterStyle(workbook);
            
            // 创建标题行
            Row titleRow = sheet.createRow(0);
            titleRow.setHeight((short) (30 * 20));
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("风险和机遇评估及控制措施");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
            
            // 创建空行
            Row emptyRow1 = sheet.createRow(1);
            emptyRow1.setHeight((short) (25 * 20));
            
            // 创建评价行
            Row evaluationRow = sheet.createRow(2);
            evaluationRow.setHeight((short) (25 * 20));
            
            Cell evalCell1 = evaluationRow.createCell(2);
            evalCell1.setCellValue("评价");
            evalCell1.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 5));
            
            Cell evalCell2 = evaluationRow.createCell(6);
            evalCell2.setCellValue("风险等级");
            evalCell2.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, 8));
            
            Cell evalCell3 = evaluationRow.createCell(10);
            evalCell3.setCellValue("实施");
            evalCell3.setCellStyle(headerStyle);
            
            // 创建表头
            Row headerRow = sheet.createRow(3);
            headerRow.setHeight((short) (25 * 20));
            
            String[] headers = {"活动/过程", "风险和机遇", "造成后果", "严重程度", "发生频次", "风险系数", "高", "一般", "低", "应对措施", "时间", "部门"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // 创建示例数据行
            String[][] exampleData = {
                {"法律法规", "对产品标准、法律法规的更新了解不及时、准确", "产品不符合最新产品标准、法律法规的规定要求", "3", "3", "9", "", "√", "", "随时关注最新产品标准/法律法规更新信息、适时对技术文件、检验规程等文件及时进行更新，并组织培训教育", "2023.7.1", "安环设备科/质量科/技术科"},
                {"设备管理", "设备精度不够、", "加工精度不达标", "2", "3", "6", "", "√", "", "更新设备", "2023.7.1", "安环设备科"},
                {"材料管理", "材质不对", "产品不合格", "1", "1", "1", "", "", "√", "加强采购控制，索要材质单", "2023.7.1", "技术科/供应科"},
                {"加工制造", "图纸设计不合理", "加工尺寸不合格", "3", "2", "6", "", "√", "", "图纸符合校对", "2023.7.1", "技术科"},
                {"检验试验设备", "未检定/失效", "产品不合格", "3", "2", "6", "", "√", "", "定期检定，发现失效后及时处理", "2023.7.1", "安环设备科"}
            };
            
            for (int i = 0; i < exampleData.length; i++) {
                Row dataRow = sheet.createRow(i + 4);
                dataRow.setHeight((short) (25 * 20));
                
                for (int j = 0; j < exampleData[i].length; j++) {
                    Cell cell = dataRow.createCell(j);
                    cell.setCellValue(exampleData[i][j]);
                    cell.setCellStyle(contentStyle);
                }
            }
            
            // 创建时间行
            Row timeRow = sheet.createRow(exampleData.length + 4);
            timeRow.setHeight((short) (25 * 20));
            Cell timeCell = timeRow.createCell(0);
            timeCell.setCellValue("时间：" + new java.text.SimpleDateFormat("yyyy.MM.dd").format(new java.util.Date()));
            timeCell.setCellStyle(contentStyle);
            
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=风险和机遇评估导入模板.xlsx");
            
            // 写入响应
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            logger.error("下载模板失败: {}", e.getMessage());
        }
    }
    
    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        style.setFont(font);
        
        return style;
    }
    
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
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        style.setFont(font);
        
        return style;
    }
    
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
     * 根据关联ID查询环境识别列表
     */
    @GetMapping("/listByRelatedId/{relatedId}")
    public AjaxResult listByRelatedId(@PathVariable("relatedId") Long relatedId)
    {
        if (relatedId == null) {
            return AjaxResult.error("关联ID不能为空");
        }

        SecurityRiskOpportunityAssessment query = new SecurityRiskOpportunityAssessment();
        query.setRelatedId(relatedId);

        List<SecurityRiskOpportunityAssessment> list =
                securityRiskOpportunityAssessmentService.selectSecurityRiskOpportunityAssessmentList(query);

        return success(list);
    }
}
