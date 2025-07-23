package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.AnnualEnvironmentalOccupationalHealthAndSafetyGoals;

/**
 * 年度环境职业健康安全目标、指标及工作分解Mapper接口
 * 
 * @author wang
 * @date 2025-03-11
 */
public interface AnnualEnvironmentalOccupationalHealthAndSafetyGoalsMapper 
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
     * 删除年度环境职业健康安全目标、指标及工作分解
     * 
     * @param serialNumber 年度环境职业健康安全目标、指标及工作分解主键
     * @return 结果
     */
    public int deleteAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumber(Long serialNumber);

    /**
     * 批量删除年度环境职业健康安全目标、指标及工作分解
     * 
     * @param serialNumbers 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAnnualEnvironmentalOccupationalHealthAndSafetyGoalsBySerialNumbers(Long[] serialNumbers);
    
    /**
     * 批量插入年度环境职业健康安全目标、指标及工作分解
     * 
     * @param list 年度环境职业健康安全目标、指标及工作分解列表
     * @return 结果
     */
    public int batchInsertAnnualEnvironmentalOccupationalHealthAndSafetyGoals(List<AnnualEnvironmentalOccupationalHealthAndSafetyGoals> list);
    
    /**
     * 更新最近导入的数据关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的行数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
