package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityAccidentCauseAnalysis;

/**
 * 事故原因分析记录Service接口
 * 
 * @author wang
 * @date 2025-07-21
 */
public interface ISecurityAccidentCauseAnalysisService 
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
     * 批量删除事故原因分析记录
     * 
     * @param ids 需要删除的事故原因分析记录主键集合
     * @return 结果
     */
    public int deleteSecurityAccidentCauseAnalysisByIds(Long[] ids);

    /**
     * 删除事故原因分析记录信息
     * 
     * @param id 事故原因分析记录主键
     * @return 结果
     */
    public int deleteSecurityAccidentCauseAnalysisById(Long id);
}
