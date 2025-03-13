package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityAccidentCauseAnalysisMapper;
import com.ruoyi.security.domain.SecurityAccidentCauseAnalysis;
import com.ruoyi.security.service.ISecurityAccidentCauseAnalysisService;

/**
 * 事故原因分析记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityAccidentCauseAnalysisServiceImpl implements ISecurityAccidentCauseAnalysisService 
{
    @Autowired
    private SecurityAccidentCauseAnalysisMapper securityAccidentCauseAnalysisMapper;

    /**
     * 查询事故原因分析记录
     * 
     * @param id 事故原因分析记录主键
     * @return 事故原因分析记录
     */
    @Override
    public SecurityAccidentCauseAnalysis selectSecurityAccidentCauseAnalysisById(Long id)
    {
        return securityAccidentCauseAnalysisMapper.selectSecurityAccidentCauseAnalysisById(id);
    }

    /**
     * 查询事故原因分析记录列表
     * 
     * @param securityAccidentCauseAnalysis 事故原因分析记录
     * @return 事故原因分析记录
     */
    @Override
    public List<SecurityAccidentCauseAnalysis> selectSecurityAccidentCauseAnalysisList(SecurityAccidentCauseAnalysis securityAccidentCauseAnalysis)
    {
        return securityAccidentCauseAnalysisMapper.selectSecurityAccidentCauseAnalysisList(securityAccidentCauseAnalysis);
    }

    /**
     * 新增事故原因分析记录
     * 
     * @param securityAccidentCauseAnalysis 事故原因分析记录
     * @return 结果
     */
    @Override
    public int insertSecurityAccidentCauseAnalysis(SecurityAccidentCauseAnalysis securityAccidentCauseAnalysis)
    {
        return securityAccidentCauseAnalysisMapper.insertSecurityAccidentCauseAnalysis(securityAccidentCauseAnalysis);
    }

    /**
     * 修改事故原因分析记录
     * 
     * @param securityAccidentCauseAnalysis 事故原因分析记录
     * @return 结果
     */
    @Override
    public int updateSecurityAccidentCauseAnalysis(SecurityAccidentCauseAnalysis securityAccidentCauseAnalysis)
    {
        return securityAccidentCauseAnalysisMapper.updateSecurityAccidentCauseAnalysis(securityAccidentCauseAnalysis);
    }

    /**
     * 批量删除事故原因分析记录
     * 
     * @param ids 需要删除的事故原因分析记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAccidentCauseAnalysisByIds(Long[] ids)
    {
        return securityAccidentCauseAnalysisMapper.deleteSecurityAccidentCauseAnalysisByIds(ids);
    }

    /**
     * 删除事故原因分析记录信息
     * 
     * @param id 事故原因分析记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityAccidentCauseAnalysisById(Long id)
    {
        return securityAccidentCauseAnalysisMapper.deleteSecurityAccidentCauseAnalysisById(id);
    }
}
