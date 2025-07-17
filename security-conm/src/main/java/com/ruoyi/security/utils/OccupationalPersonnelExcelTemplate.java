package com.ruoyi.security.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 危险职业岗位人员清单及管理台帐Excel模板生成器
 * 
 * @author ruoyi
 */
public class OccupationalPersonnelExcelTemplate {

    private static final Logger logger = LoggerFactory.getLogger(OccupationalPersonnelExcelTemplate.class);

    /**
     * 生成Excel模板
     */
    public static void generateTemplate(HttpServletResponse response) throws IOException {
        logger.info("开始生成Excel模板");
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("危险职业岗位人员清单及管理台帐");

        // 创建样式
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle subHeaderStyle = createSubHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);

        // 设置列宽
        sheet.setColumnWidth(0, 4000);  // 班组
        sheet.setColumnWidth(1, 3000);  // 姓名
        sheet.setColumnWidth(2, 2500);  // 性别
        sheet.setColumnWidth(3, 3500);  // 工种
        sheet.setColumnWidth(4, 8000);  // 接害因素
        sheet.setColumnWidth(5, 3000);  // 上岗前
        sheet.setColumnWidth(6, 3000);  // 在岗中
        sheet.setColumnWidth(7, 3000);  // 离岗时
        sheet.setColumnWidth(8, 4000);  // 备注

        // 创建表头
        createHeaders(sheet, headerStyle, subHeaderStyle);

        // 创建示例数据行
        createSampleData(sheet, dataStyle);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        String fileName = "危险职业岗位人员清单及管理台帐导入模板";
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        // 只使用编码后的文件名，避免中文字符问题
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + encodedFileName + ".xlsx");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        logger.info("设置响应头完成，编码后文件名: {}", encodedFileName);

        // 输出到响应流
        logger.info("开始写入Excel数据到响应流");
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
            logger.info("Excel模板生成成功");
        } catch (IOException e) {
            logger.error("写入Excel数据失败", e);
            throw e;
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                logger.error("关闭工作簿失败", e);
            }
        }
    }

    /**
     * 创建表头
     */
    private static void createHeaders(Sheet sheet, CellStyle headerStyle, CellStyle subHeaderStyle) {
        // 第一行：主标题
        Row titleRow = sheet.createRow(0);
        titleRow.setHeight((short) 600);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("危险职业岗位人员清单及管理台帐");
        titleCell.setCellStyle(headerStyle);
        // 合并标题行
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

        // 第二行：列标题第一层
        Row headerRow1 = sheet.createRow(1);
        headerRow1.setHeight((short) 500);
        
        // 班组
        Cell cell0 = headerRow1.createCell(0);
        cell0.setCellValue("班组");
        cell0.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));

        // 姓名
        Cell cell1 = headerRow1.createCell(1);
        cell1.setCellValue("姓名");
        cell1.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));

        // 性别
        Cell cell2 = headerRow1.createCell(2);
        cell2.setCellValue("性别");
        cell2.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));

        // 工种
        Cell cell3 = headerRow1.createCell(3);
        cell3.setCellValue("工种");
        cell3.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));

        // 接害因素
        Cell cell4 = headerRow1.createCell(4);
        cell4.setCellValue("接害因素（毒害、噪声、高温、粉尘、特殊工种）");
        cell4.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 4, 4));

        // 主要岗位在岗人员职业健康检查情况
        Cell cell5 = headerRow1.createCell(5);
        cell5.setCellValue("主要岗位在岗人员职业健康检查情况");
        cell5.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 7));

        // 备注
        Cell cell8 = headerRow1.createCell(8);
        cell8.setCellValue("备注");
        cell8.setCellStyle(subHeaderStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 8, 8));

        // 第三行：列标题第二层（健康检查子项）
        Row headerRow2 = sheet.createRow(2);
        headerRow2.setHeight((short) 400);

        Cell subCell5 = headerRow2.createCell(5);
        subCell5.setCellValue("上岗前");
        subCell5.setCellStyle(subHeaderStyle);

        Cell subCell6 = headerRow2.createCell(6);
        subCell6.setCellValue("在岗中");
        subCell6.setCellStyle(subHeaderStyle);

        Cell subCell7 = headerRow2.createCell(7);
        subCell7.setCellValue("离岗时");
        subCell7.setCellStyle(subHeaderStyle);
    }

    /**
     * 创建示例数据
     */
    private static void createSampleData(Sheet sheet, CellStyle dataStyle) {
        // 示例数据
        String[][] sampleData = {
            {"各工业车间（车间）有限公司", "王某", "男", "切割工", "其他粉尘、噪声、高温辐射、氯氧化物", "√", "", "", ""},
            {"", "李某", "男", "切割工", "其他粉尘、噪声、高温辐射、氯氧化物", "√", "", "", ""},
            {"", "张某", "男", "切割工", "其他粉尘、噪声、高温辐射、氯氧化物", "√", "", "", ""},
            {"", "赵某", "男", "中工", "其他粉尘、噪声", "√", "", "", ""},
            {"各科", "陈某", "男", "切割工", "其他粉尘、噪声、高温辐射、氯氧化物", "√", "", "", ""},
            {"", "杨某", "男", "中工", "其他粉尘、噪声", "√", "", "", ""}
        };

        for (int i = 0; i < sampleData.length; i++) {
            Row dataRow = sheet.createRow(i + 3);
            dataRow.setHeight((short) 350);
            
            for (int j = 0; j < sampleData[i].length; j++) {
                Cell cell = dataRow.createCell(j);
                cell.setCellValue(sampleData[i][j]);
                cell.setCellStyle(dataStyle);
            }
        }

        // 添加说明行
        Row noteRow = sheet.createRow(sampleData.length + 4);
        Cell noteCell = noteRow.createCell(0);
        noteCell.setCellValue("说明：1. 班组列可以合并单元格，空行会自动继承上一行的班组；2. 健康检查列请输入√表示已完成，空白表示未完成");
        noteCell.setCellStyle(createNoteStyle(sheet.getWorkbook()));
        sheet.addMergedRegion(new CellRangeAddress(sampleData.length + 4, sampleData.length + 4, 0, 8));
    }

    /**
     * 创建主标题样式
     */
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        font.setFontName("宋体");
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    /**
     * 创建子标题样式
     */
    private static CellStyle createSubHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setFontName("宋体");
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setWrapText(true);
        return style;
    }

    /**
     * 创建数据样式
     */
    private static CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("宋体");
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        return style;
    }

    /**
     * 创建说明样式
     */
    private static CellStyle createNoteStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("宋体");
        font.setColor(IndexedColors.DARK_RED.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        return style;
    }
}
