package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityAccidentCauseAnalysis;

/**
 * 事故原因分析记录Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecurityAccidentCauseAnalysisMapper 
{
    /**
     * 查询事故原因分析记录
     * 
     * @param id 事故原因分析记录主键
     * @return 事故原因分析记录
     */
    public SecurityAccidentCauseAnalysis selectSecurityAccidentCauseAnalysisById(Long id);

    /**
     * 查询事故原因分析记录列表
     * 
     * @param securityAccidentCauseAnalysis 事故原因分析记录
     * @return 事故原因分析记录集合
     */
    public List<SecurityAccidentCauseAnalysis> selectSecurityAccidentCauseAnalysisList(SecurityAccidentCauseAnalysis securityAccidentCauseAnalysis);

    /**
     * 新增事故原因分析记录
     * 
     * @param securityAccidentCauseAnalysis 事故原因分析记录
     * @return 结果
     */
    public int insertSecurityAccidentCauseAnalysis(SecurityAccidentCauseAnalysis securityAccidentCauseAnalysis);

    /**
     * 修改事故原因分析记录
     * 
     * @param securityAccidentCauseAnalysis 事故原因分析记录
     * @return 结果
     */
    public int updateSecurityAccidentCauseAnalysis(SecurityAccidentCauseAnalysis securityAccidentCauseAnalysis);

    /**
     * 删除事故原因分析记录
     * 
     * @param id 事故原因分析记录主键
     * @return 结果
     */
    public int deleteSecurityAccidentCauseAnalysisById(Long id);

    /**
     * 批量删除事故原因分析记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityAccidentCauseAnalysisByIds(Long[] ids);
}
