package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityAcceptanceEvaluation;

/**
 * 安全验收评价Service接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface ISecurityAcceptanceEvaluationService 
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
     * 批量删除安全验收评价
     * 
     * @param ids 需要删除的安全验收评价主键集合
     * @return 结果
     */
    public int deleteSecurityAcceptanceEvaluationByIds(String[] ids);

    /**
     * 删除安全验收评价信息
     * 
     * @param id 安全验收评价主键
     * @return 结果
     */
    public int deleteSecurityAcceptanceEvaluationById(String id);
}
