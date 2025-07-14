package com.ruoyi.security.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.security.domain.AnnualEnvironmentalOccupationalHealthAndSafetyGoals;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 年度环境职业健康安全目标、指标及工作分解Service接口
 * 
 * @author wang
 * @date 2025-03-11
 */
public interface IAnnualEnvironmentalOccupationalHealthAndSafetyGoalsService 
{
    /**
     * 查询年度环境职业健康安全目标、指标及工作分解
     * 
     * @param serialNumber 年度环境职业健康安全目标、指标及工作分解主键
     * @return 年度环境职业健康安全目标、指标及工作分解
     */
    public AnnualEnvironmentalOccupationalHealthAndSafetyGoals selectAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumber(Long serialNumber);

    /**
     * 查询年度环境职业健康安全目标、指标及工作分解列表
     * 
     * @param annualEnvironmentalOccupationalHealthAndSafetyGoals 年度环境职业健康安全目标、指标及工作分解
     * @return 年度环境职业健康安全目标、指标及工作分解集合
     */
    public List<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> selectAnnualEnvironmentalOccupationalHealthAndSafetyGoalsList(AnnualEnvironmentalOccupationalHealthAndSafetyGoals annualEnvironmentalOccupationalHealthAndSafetyGoals);

    /**
     * 新增年度环境职业健康安全目标、指标及工作分解
     * 
     * @param annualEnvironmentalOccupationalHealthAndSafetyGoals 年度环境职业健康安全目标、指标及工作分解
     * @return 结果
     */
    public int insertAnnualEnvironmentalOccupationalHealthAndSafetyGoals(AnnualEnvironmentalOccupationalHealthAndSafetyGoals annualEnvironmentalOccupationalHealthAndSafetyGoals);

    /**
     * 修改年度环境职业健康安全目标、指标及工作分解
     * 
     * @param annualEnvironmentalOccupationalHealthAndSafetyGoals 年度环境职业健康安全目标、指标及工作分解
     * @return 结果
     */
    public int updateAnnualEnvironmentalOccupationalHealthAndSafetyGoals(AnnualEnvironmentalOccupationalHealthAndSafetyGoals annualEnvironmentalOccupationalHealthAndSafetyGoals);

    /**
     * 批量删除年度环境职业健康安全目标、指标及工作分解
     * 
     * @param serialNumbers 需要删除的年度环境职业健康安全目标、指标及工作分解主键集合
     * @return 结果
     */
    public int deleteAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumbers(Long[] serialNumbers);

    /**
     * 删除年度环境职业健康安全目标、指标及工作分解信息
     * 
     * @param serialNumber 年度环境职业健康安全目标、指标及工作分解主键
     * @return 结果
     */
    public int deleteAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumber(Long serialNumber);
    
    /**
     * 导入年度环境职业健康安全目标数据
     * 
     * @param file 导入文件
     * @return 结果
     * @throws Exception 异常
     */
    public AjaxResult importData(MultipartFile file) throws Exception;
    
    /**
     * 导入模板
     * 
     * @return 结果
     */
    public AjaxResult importTemplate();
    
    /**
     * 导出导入模板到响应
     * 
     * @param response 响应对象
     * @param sheetName 工作表名称
     */
    public void importTemplateExcel(jakarta.servlet.http.HttpServletResponse response, String sheetName);
    
    /**
     * 更新最近导入的数据关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的行数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
