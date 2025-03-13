package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityPreEvaluation;

/**
 * 安全预评价Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecurityPreEvaluationMapper 
{
    /**
     * 查询安全预评价
     * 
     * @param id 安全预评价主键
     * @return 安全预评价
     */
    public SecurityPreEvaluation selectSecurityPreEvaluationById(String id);

    /**
     * 查询安全预评价列表
     * 
     * @param securityPreEvaluation 安全预评价
     * @return 安全预评价集合
     */
    public List<SecurityPreEvaluation> selectSecurityPreEvaluationList(SecurityPreEvaluation securityPreEvaluation);

    /**
     * 新增安全预评价
     * 
     * @param securityPreEvaluation 安全预评价
     * @return 结果
     */
    public int insertSecurityPreEvaluation(SecurityPreEvaluation securityPreEvaluation);

    /**
     * 修改安全预评价
     * 
     * @param securityPreEvaluation 安全预评价
     * @return 结果
     */
    public int updateSecurityPreEvaluation(SecurityPreEvaluation securityPreEvaluation);

    /**
     * 删除安全预评价
     * 
     * @param id 安全预评价主键
     * @return 结果
     */
    public int deleteSecurityPreEvaluationById(String id);

    /**
     * 批量删除安全预评价
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityPreEvaluationByIds(String[] ids);
}
