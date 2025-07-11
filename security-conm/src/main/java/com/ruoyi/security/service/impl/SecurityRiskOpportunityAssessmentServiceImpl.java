package com.ruoyi.security.service.impl;

import java.util.List;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.security.domain.SecurityRequireExpectParty;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.security.mapper.SecurityRiskOpportunityAssessmentMapper;
import com.ruoyi.security.domain.SecurityRiskOpportunityAssessment;
import com.ruoyi.security.service.ISecurityRiskOpportunityAssessmentService;

/**
 * 风险和机遇评估及控制措施Service业务层处理
 * 
 * @author wang
 * @date 2025-03-11
 */
@Service
public class SecurityRiskOpportunityAssessmentServiceImpl implements ISecurityRiskOpportunityAssessmentService 
{
    private static final Logger log = LoggerFactory.getLogger(SecurityRiskOpportunityAssessmentServiceImpl.class);
    
    @Autowired
    private SecurityRiskOpportunityAssessmentMapper securityRiskOpportunityAssessmentMapper;

    /**
     * 查询风险和机遇评估及控制措施
     * 
     * @param id 风险和机遇评估及控制措施主键
     * @return 风险和机遇评估及控制措施
     */
    @Override
    public SecurityRiskOpportunityAssessment selectSecurityRiskOpportunityAssessmentById(Long id)
    {
        return securityRiskOpportunityAssessmentMapper.selectSecurityRiskOpportunityAssessmentById(id);
    }

    /**
     * 查询风险和机遇评估及控制措施列表
     * 
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 风险和机遇评估及控制措施
     */
    @Override
    public List<SecurityRiskOpportunityAssessment> selectSecurityRiskOpportunityAssessmentList(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return securityRiskOpportunityAssessmentMapper.selectSecurityRiskOpportunityAssessmentList(securityRiskOpportunityAssessment);
    }

    /**
     * 新增风险和机遇评估及控制措施
     * 
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 结果
     */
    @Override
    public int insertSecurityRiskOpportunityAssessment(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return securityRiskOpportunityAssessmentMapper.insertSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment);
    }

    /**
     * 修改风险和机遇评估及控制措施
     * 
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 结果
     */
    @Override
    public int updateSecurityRiskOpportunityAssessment(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return securityRiskOpportunityAssessmentMapper.updateSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment);
    }

    /**
     * 批量删除风险和机遇评估及控制措施
     * 
     * @param ids 需要删除的风险和机遇评估及控制措施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRiskOpportunityAssessmentByIds(Long[] ids)
    {
        return securityRiskOpportunityAssessmentMapper.deleteSecurityRiskOpportunityAssessmentByIds(ids);
    }

    /**
     * 删除风险和机遇评估及控制措施信息
     * 
     * @param id 风险和机遇评估及控制措施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRiskOpportunityAssessmentById(Long id)
    {
        return securityRiskOpportunityAssessmentMapper.deleteSecurityRiskOpportunityAssessmentById(id);
    }
    
    /**
     * 导入风险和机遇评估及控制措施数据
     * 
     * @param file 导入文件
     * @return 结果
     */
    @Override
    public String importAssessment(MultipartFile file)
    {
        try {
            // 使用POI直接解析Excel文件
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            if (workbook == null) {
                throw new ServiceException("导入Excel数据失败，文件格式不正确");
            }
            
            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new ServiceException("导入Excel数据失败，工作表不存在");
            }
            
            // 从第5行开始解析数据（跳过表头）
            int startRow = 4;
            int lastRowNum = sheet.getLastRowNum();
            int successCount = 0;
            
            log.info("开始导入风险和机遇评估数据，共{}行", (lastRowNum - startRow + 1));
            List<SecurityRiskOpportunityAssessment> assessmentList = new ArrayList<>();
            
            // 遍历每一行数据
            for (int i = startRow; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                
                // 跳过时间标记行
                String cellValue = getCellValue(row, 0);
                if (cellValue != null && cellValue.contains("时间：")) {
                    continue;
                }
                
                // 创建实体对象
                SecurityRiskOpportunityAssessment assessment = new SecurityRiskOpportunityAssessment();
                
                // 解析每一列数据
                assessment.setActivity(getCellValue(row, 0)); // A - 活动/过程
                assessment.setRiskOpportunity(getCellValue(row, 1)); // B - 风险和机遇
                assessment.setConsequences(getCellValue(row, 2)); // C - 造成后果
                assessment.setSeverity(getCellValue(row, 3)); // D - 严重程度
                assessment.setFrequency(getCellValue(row, 4)); // E - 发生频次
                assessment.setRisk(getCellValue(row, 5)); // F - 风险系数
                
                // 风险等级判断
                String riskLevel = "一般"; // 默认为一般
                if ("√".equals(getCellValue(row, 6))) {
                    riskLevel = "高";
                } else if ("√".equals(getCellValue(row, 7))) {
                    riskLevel = "一般";
                } else if ("√".equals(getCellValue(row, 8))) {
                    riskLevel = "低";
                } else if ("-".equals(getCellValue(row, 6)) && "-".equals(getCellValue(row, 7)) && "-".equals(getCellValue(row, 8))) {
                    riskLevel = "-";
                }
                assessment.setRiskLevel(riskLevel);
                
                assessment.setResponseMeasures(getCellValue(row, 9)); // J - 应对措施
                
                // 处理日期格式
                String dateStr = getCellValue(row, 10);
                if (StringUtils.isNotEmpty(dateStr)) {
                    try {
                        // 将2023.7.1格式转换为2023-07-01
                        String[] dateParts = dateStr.split("\\.");
                        if (dateParts.length == 3) {
                            String year = dateParts[0];
                            String month = dateParts[1].length() == 1 ? "0" + dateParts[1] : dateParts[1];
                            String day = dateParts[2].length() == 1 ? "0" + dateParts[2] : dateParts[2];
                            dateStr = year + "-" + month + "-" + day;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            assessment.setImplementationTime(sdf.parse(dateStr));
                        }
                    } catch (Exception e) {
                        log.error("日期格式转换失败: {}", e.getMessage());
                    }
                }
                
                assessment.setDepartment(getCellValue(row, 11)); // L - 部门
                
                // 检查必填字段
                if (StringUtils.isEmpty(assessment.getActivity()) || StringUtils.isEmpty(assessment.getRiskOpportunity())) {
                    continue;
                }
                
                // 添加到列表
                assessmentList.add(assessment);
            }
            
            // 保存数据
            if (assessmentList.isEmpty()) {
                throw new ServiceException("导入Excel数据失败，未找到有效数据");
            }
            
            for (SecurityRiskOpportunityAssessment assessment : assessmentList) {
                try {
                    insertSecurityRiskOpportunityAssessment(assessment);
                    successCount++;
                } catch (Exception e) {
                    log.error("保存数据失败: {}", e.getMessage());
                }
            }
            
            log.info("导入完成，成功导入 {} 条数据", successCount);
            return "导入成功，共" + successCount + "条数据";
            
        } catch (IOException e) {
            log.error("导入Excel数据失败: {}", e.getMessage());
            throw new ServiceException("导入Excel数据失败: " + e.getMessage());
        }
    }

    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        // 查询最近导入的数据
        SecurityRiskOpportunityAssessment query = new SecurityRiskOpportunityAssessment();
        // 按创建时间倒序排序，获取最近的记录
        List<SecurityRiskOpportunityAssessment> recentRecords =
                securityRiskOpportunityAssessmentMapper.selectLatestImportedRecords();

        if (recentRecords == null || recentRecords.isEmpty()) {
            return 0;
        }

        int updatedCount = 0;

        // 更新这些记录的relatedId
        for (SecurityRiskOpportunityAssessment record : recentRecords) {
            record.setRelatedId(relatedId);
            record.setUpdateTime(DateUtils.getNowDate());
            updatedCount += securityRiskOpportunityAssessmentMapper.updateSecurityRiskOpportunityAssessment(record);
        }

        return updatedCount;
    }

    /**
     * 获取单元格的值
     */
    private String getCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return null;
        }
        
        String cellValue = "";
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                cellValue = String.valueOf((int) cell.getNumericCellValue());
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                try {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    cellValue = cell.getStringCellValue();
                }
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }
}
