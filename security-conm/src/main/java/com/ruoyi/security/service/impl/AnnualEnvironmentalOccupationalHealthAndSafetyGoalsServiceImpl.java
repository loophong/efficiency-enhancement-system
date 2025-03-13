package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
