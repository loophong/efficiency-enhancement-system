package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.AnnualEnvironmentalOccupationalHealthAndSafetyGoals;

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
}
