package com.ruoyi.security.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.security.mapper.AnnualEnvironmentalOccupationalHealthAndSafetyGoalsMapper;
import com.ruoyi.security.domain.AnnualEnvironmentalOccupationalHealthAndSafetyGoals;
import com.ruoyi.security.service.IAnnualEnvironmentalOccupationalHealthAndSafetyGoalsService;

/**
 * 年度环境职业健康安全目标、指标及工作分解Service业务层处理
 * 
 * @author wang
 * @date 2025-03-11
 */
@Service
public class AnnualEnvironmentalOccupationalHealthAndSafetyGoalsServiceImpl implements IAnnualEnvironmentalOccupationalHealthAndSafetyGoalsService 
{
    private static final Logger log = LoggerFactory.getLogger(AnnualEnvironmentalOccupationalHealthAndSafetyGoalsServiceImpl.class);
    
    @Autowired
    private AnnualEnvironmentalOccupationalHealthAndSafetyGoalsMapper annualEnvironmentalOccupationalHealthAndSafetyGoalsMapper;

    /**
     * 查询年度环境职业健康安全目标、指标及工作分解
     * 
     * @param serialNumber 年度环境职业健康安全目标、指标及工作分解主键
     * @return 年度环境职业健康安全目标、指标及工作分解
     */
    @Override
    public AnnualEnvironmentalOccupationalHealthAndSafetyGoals selectAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumber(Long serialNumber)
    {
        return annualEnvironmentalOccupationalHealthAndSafetyGoalsMapper.selectAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumber(serialNumber);
    }

    /**
     * 查询年度环境职业健康安全目标、指标及工作分解列表
     * 
     * @param annualEnvironmentalOccupationalHealthAndSafetyGoals 年度环境职业健康安全目标、指标及工作分解
     * @return 年度环境职业健康安全目标、指标及工作分解
     */
    @Override
    public List<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> selectAnnualEnvironmentalOccupationalHealthAndSafetyGoalsList(AnnualEnvironmentalOccupationalHealthAndSafetyGoals annualEnvironmentalOccupationalHealthAndSafetyGoals)
    {
        return annualEnvironmentalOccupationalHealthAndSafetyGoalsMapper.selectAnnualEnvironmentalOccupationalHealthAndSafetyGoalsList(annualEnvironmentalOccupationalHealthAndSafetyGoals);
    }

    /**
     * 新增年度环境职业健康安全目标、指标及工作分解
     * 
     * @param annualEnvironmentalOccupationalHealthAndSafetyGoals 年度环境职业健康安全目标、指标及工作分解
     * @return 结果
     */
    @Override
    public int insertAnnualEnvironmentalOccupationalHealthAndSafetyGoals(AnnualEnvironmentalOccupationalHealthAndSafetyGoals annualEnvironmentalOccupationalHealthAndSafetyGoals)
    {
        return annualEnvironmentalOccupationalHealthAndSafetyGoalsMapper.insertAnnualEnvironmentalOccupationalHealthAndSafetyGoals(annualEnvironmentalOccupationalHealthAndSafetyGoals);
    }

    /**
     * 修改年度环境职业健康安全目标、指标及工作分解
     * 
     * @param annualEnvironmentalOccupationalHealthAndSafetyGoals 年度环境职业健康安全目标、指标及工作分解
     * @return 结果
     */
    @Override
    public int updateAnnualEnvironmentalOccupationalHealthAndSafetyGoals(AnnualEnvironmentalOccupationalHealthAndSafetyGoals annualEnvironmentalOccupationalHealthAndSafetyGoals)
    {
        return annualEnvironmentalOccupationalHealthAndSafetyGoalsMapper.updateAnnualEnvironmentalOccupationalHealthAndSafetyGoals(annualEnvironmentalOccupationalHealthAndSafetyGoals);
    }

    /**
     * 批量删除年度环境职业健康安全目标、指标及工作分解
     * 
     * @param serialNumbers 需要删除的年度环境职业健康安全目标、指标及工作分解主键
     * @return 结果
     */
    @Override
    public int deleteAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumbers(Long[] serialNumbers)
    {
        return annualEnvironmentalOccupationalHealthAndSafetyGoalsMapper.deleteAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumbers(serialNumbers);
    }

    /**
     * 删除年度环境职业健康安全目标、指标及工作分解信息
     * 
     * @param serialNumber 年度环境职业健康安全目标、指标及工作分解主键
     * @return 结果
     */
    @Override
    public int deleteAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumber(Long serialNumber)
    {
        return annualEnvironmentalOccupationalHealthAndSafetyGoalsMapper.deleteAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumber(serialNumber);
    }
    
    /**
     * 导入年度环境职业健康安全目标数据
     * 
     * @param file 导入文件
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public AjaxResult importData(MultipartFile file) throws Exception {
        if (file == null) {
            return AjaxResult.error("导入文件不能为空");
        }
        
        log.info("开始导入文件：{}", file.getOriginalFilename());
        ExcelUtil<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> util = new ExcelUtil<>(AnnualEnvironmentalOccupationalHealthAndSafetyGoals.class);
        List<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> goalsList = util.importExcel(file.getInputStream());
        
        log.info("从Excel读取到数据条数：{}", goalsList != null ? goalsList.size() : 0);
        if (StringUtils.isNull(goalsList) || goalsList.isEmpty()) {
            return AjaxResult.error("导入数据为空");
        }
        
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        
        // 记录上一个有效的部门名称和考核结果，用于处理合并单元格
        String lastDepartmentName = null;
        String lastEvaluationResult = null;
        
        for (int i = 0; i < goalsList.size(); i++) {
            AnnualEnvironmentalOccupationalHealthAndSafetyGoals goals = goalsList.get(i);
            try {
                log.info("处理第{}条数据，部门名称：{}, 目标项：{}, 考核结果：{}", i + 1, goals.getDepartmentName(), goals.getGoalItem(), goals.getEvaluationResult());
                
                // 处理部门名称合并单元格
                if (StringUtils.isEmpty(goals.getDepartmentName())) {
                    if (lastDepartmentName != null) {
                        goals.setDepartmentName(lastDepartmentName);
                        log.info("第{}条数据部门名称为空，使用上一个有效部门名称：{}", i + 1, lastDepartmentName);
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>第 " + (i + 1) + " 条数据部门名称为空且无法确定");
                        log.warn("第{}条数据部门名称为空且无法确定", i + 1);
                        continue;
                    }
                } else {
                    // 更新最后一个有效的部门名称
                    lastDepartmentName = goals.getDepartmentName();
                    // 当部门名称变更时，重置考核结果
                    lastEvaluationResult = goals.getEvaluationResult();
                }
                
                // 处理考核结果合并单元格
                if (StringUtils.isEmpty(goals.getEvaluationResult())) {
                    if (lastEvaluationResult != null) {
                        goals.setEvaluationResult(lastEvaluationResult);
                        log.info("第{}条数据考核结果为空，使用上一个有效考核结果：{}", i + 1, lastEvaluationResult);
                    }
                } else {
                    // 更新最后一个有效的考核结果
                    lastEvaluationResult = goals.getEvaluationResult();
                }
                
                // 验证必填字段
                if (StringUtils.isEmpty(goals.getGoalItem())) {
                    failureNum++;
                    failureMsg.append("<br/>第 " + (i + 1) + " 条数据目标项为空");
                    log.warn("第{}条数据目标项为空", i + 1);
                    continue;
                }
                
                // 插入数据
                this.insertAnnualEnvironmentalOccupationalHealthAndSafetyGoals(goals);
                successNum++;
                log.info("成功插入第{}条数据", i + 1);
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>第 " + (i + 1) + " 条数据导入失败：" + e.getMessage();
                failureMsg.append(msg);
                log.error("第{}条数据导入失败", i + 1, e);
            }
        }
        
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            log.error("导入失败，共{}条数据格式不正确", failureNum);
            return AjaxResult.error(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
            log.info("导入成功，共{}条数据", successNum);
            return AjaxResult.success(successMsg.toString());
        }
    }
    
    /**
     * 导入模板
     * 
     * @return 结果
     */
    @Override
    public AjaxResult importTemplate() {
        try {
            log.info("開始生成年度環境職業健康安全目標導入模板");
            ExcelUtil<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> util = new ExcelUtil<>(AnnualEnvironmentalOccupationalHealthAndSafetyGoals.class);
            log.info("ExcelUtil實例化成功，準備調用importTemplateExcel方法");
            AjaxResult result = util.importTemplateExcel("年度环境职业健康安全目标导入模板");
            log.info("模板生成結果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("生成導入模板失敗，異常詳情: ", e);
            return AjaxResult.error("生成導入模板失敗：" + e.getMessage());
        }
    }
    
    /**
     * 导出导入模板到响应
     * 
     * @param response 响应对象
     * @param sheetName 工作表名称
     */
    @Override
    public void importTemplateExcel(jakarta.servlet.http.HttpServletResponse response, String sheetName) {
        try {
            log.info("开始生成年度环境职业健康安全目标导入模板并直接输出到响应");
            ExcelUtil<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> util = new ExcelUtil<>(AnnualEnvironmentalOccupationalHealthAndSafetyGoals.class);
            log.info("ExcelUtil实例化成功，准备调用importTemplateExcel方法输出到响应");
            util.importTemplateExcel(response, sheetName);
            log.info("模板已成功输出到响应");
        } catch (Exception e) {
            log.error("生成导入模板并输出到响应失败，异常详情: ", e);
            throw new RuntimeException("生成导入模板失败：" + e.getMessage(), e);
        }
    }
    
    /**
     * 更新最近导入的数据关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的行数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        if (relatedId == null) {
            log.error("更新关联ID失败：关联ID为空");
            return 0;
        }
        
        try {
            int rows = annualEnvironmentalOccupationalHealthAndSafetyGoalsMapper.updateLatestImportedRelatedId(relatedId);
            log.info("成功更新年度环境职业健康安全目标关联ID：{}，更新行数：{}", relatedId, rows);
            return rows;
        } catch (Exception e) {
            log.error("更新年度环境职业健康安全目标关联ID失败", e);
            return 0;
        }
    }
}
