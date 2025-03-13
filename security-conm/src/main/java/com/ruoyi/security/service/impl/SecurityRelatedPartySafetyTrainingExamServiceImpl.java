package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRelatedPartySafetyTrainingExamMapper;
import com.ruoyi.security.domain.SecurityRelatedPartySafetyTrainingExam;
import com.ruoyi.security.service.ISecurityRelatedPartySafetyTrainingExamService;

/**
 * 相关方安全教育培训及考试Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityRelatedPartySafetyTrainingExamServiceImpl implements ISecurityRelatedPartySafetyTrainingExamService 
{
    @Autowired
    private SecurityRelatedPartySafetyTrainingExamMapper securityRelatedPartySafetyTrainingExamMapper;

    /**
     * 查询相关方安全教育培训及考试
     * 
     * @param id 相关方安全教育培训及考试主键
     * @return 相关方安全教育培训及考试
     */
    @Override
    public SecurityRelatedPartySafetyTrainingExam selectSecurityRelatedPartySafetyTrainingExamById(String id)
    {
        return securityRelatedPartySafetyTrainingExamMapper.selectSecurityRelatedPartySafetyTrainingExamById(id);
    }

    /**
     * 查询相关方安全教育培训及考试列表
     * 
     * @param securityRelatedPartySafetyTrainingExam 相关方安全教育培训及考试
     * @return 相关方安全教育培训及考试
     */
    @Override
    public List<SecurityRelatedPartySafetyTrainingExam> selectSecurityRelatedPartySafetyTrainingExamList(SecurityRelatedPartySafetyTrainingExam securityRelatedPartySafetyTrainingExam)
    {
        return securityRelatedPartySafetyTrainingExamMapper.selectSecurityRelatedPartySafetyTrainingExamList(securityRelatedPartySafetyTrainingExam);
    }

    /**
     * 新增相关方安全教育培训及考试
     * 
     * @param securityRelatedPartySafetyTrainingExam 相关方安全教育培训及考试
     * @return 结果
     */
    @Override
    public int insertSecurityRelatedPartySafetyTrainingExam(SecurityRelatedPartySafetyTrainingExam securityRelatedPartySafetyTrainingExam)
    {
        return securityRelatedPartySafetyTrainingExamMapper.insertSecurityRelatedPartySafetyTrainingExam(securityRelatedPartySafetyTrainingExam);
    }

    /**
     * 修改相关方安全教育培训及考试
     * 
     * @param securityRelatedPartySafetyTrainingExam 相关方安全教育培训及考试
     * @return 结果
     */
    @Override
    public int updateSecurityRelatedPartySafetyTrainingExam(SecurityRelatedPartySafetyTrainingExam securityRelatedPartySafetyTrainingExam)
    {
        return securityRelatedPartySafetyTrainingExamMapper.updateSecurityRelatedPartySafetyTrainingExam(securityRelatedPartySafetyTrainingExam);
    }

    /**
     * 批量删除相关方安全教育培训及考试
     * 
     * @param ids 需要删除的相关方安全教育培训及考试主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelatedPartySafetyTrainingExamByIds(String[] ids)
    {
        return securityRelatedPartySafetyTrainingExamMapper.deleteSecurityRelatedPartySafetyTrainingExamByIds(ids);
    }

    /**
     * 删除相关方安全教育培训及考试信息
     * 
     * @param id 相关方安全教育培训及考试主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelatedPartySafetyTrainingExamById(String id)
    {
        return securityRelatedPartySafetyTrainingExamMapper.deleteSecurityRelatedPartySafetyTrainingExamById(id);
    }
}
