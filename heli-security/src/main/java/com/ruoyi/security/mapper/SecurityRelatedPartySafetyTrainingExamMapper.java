package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityRelatedPartySafetyTrainingExam;

/**
 * 相关方安全教育培训及考试Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecurityRelatedPartySafetyTrainingExamMapper 
{
    /**
     * 查询相关方安全教育培训及考试
     * 
     * @param id 相关方安全教育培训及考试主键
     * @return 相关方安全教育培训及考试
     */
    public SecurityRelatedPartySafetyTrainingExam selectSecurityRelatedPartySafetyTrainingExamById(String id);

    /**
     * 查询相关方安全教育培训及考试列表
     * 
     * @param securityRelatedPartySafetyTrainingExam 相关方安全教育培训及考试
     * @return 相关方安全教育培训及考试集合
     */
    public List<SecurityRelatedPartySafetyTrainingExam> selectSecurityRelatedPartySafetyTrainingExamList(SecurityRelatedPartySafetyTrainingExam securityRelatedPartySafetyTrainingExam);

    /**
     * 新增相关方安全教育培训及考试
     * 
     * @param securityRelatedPartySafetyTrainingExam 相关方安全教育培训及考试
     * @return 结果
     */
    public int insertSecurityRelatedPartySafetyTrainingExam(SecurityRelatedPartySafetyTrainingExam securityRelatedPartySafetyTrainingExam);

    /**
     * 修改相关方安全教育培训及考试
     * 
     * @param securityRelatedPartySafetyTrainingExam 相关方安全教育培训及考试
     * @return 结果
     */
    public int updateSecurityRelatedPartySafetyTrainingExam(SecurityRelatedPartySafetyTrainingExam securityRelatedPartySafetyTrainingExam);

    /**
     * 删除相关方安全教育培训及考试
     * 
     * @param id 相关方安全教育培训及考试主键
     * @return 结果
     */
    public int deleteSecurityRelatedPartySafetyTrainingExamById(String id);

    /**
     * 批量删除相关方安全教育培训及考试
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityRelatedPartySafetyTrainingExamByIds(String[] ids);
}
