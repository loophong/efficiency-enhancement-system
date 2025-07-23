package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityAcceptanceEvaluation;

/**
 * 安全验收评价Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecurityAcceptanceEvaluationMapper 
{
    /**
     * 查询安全验收评价
     * 
     * @param id 安全验收评价主键
     * @return 安全验收评价
     */
    public SecurityAcceptanceEvaluation selectSecurityAcceptanceEvaluationById(String id);

    /**
     * 查询安全验收评价列表
     * 
     * @param securityAcceptanceEvaluation 安全验收评价
     * @return 安全验收评价集合
     */
    public List<SecurityAcceptanceEvaluation> selectSecurityAcceptanceEvaluationList(SecurityAcceptanceEvaluation securityAcceptanceEvaluation);

    /**
     * 新增安全验收评价
     * 
     * @param securityAcceptanceEvaluation 安全验收评价
     * @return 结果
     */
    public int insertSecurityAcceptanceEvaluation(SecurityAcceptanceEvaluation securityAcceptanceEvaluation);

    /**
     * 修改安全验收评价
     * 
     * @param securityAcceptanceEvaluation 安全验收评价
     * @return 结果
     */
    public int updateSecurityAcceptanceEvaluation(SecurityAcceptanceEvaluation securityAcceptanceEvaluation);

    /**
     * 删除安全验收评价
     * 
     * @param id 安全验收评价主键
     * @return 结果
     */
    public int deleteSecurityAcceptanceEvaluationById(String id);

    /**
     * 批量删除安全验收评价
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityAcceptanceEvaluationByIds(String[] ids);
}
