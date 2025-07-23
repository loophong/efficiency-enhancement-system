package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEquipmentEvaluation;

/**
 * 安全设备设施现状评价Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecurityEquipmentEvaluationMapper 
{
    /**
     * 查询安全设备设施现状评价
     * 
     * @param id 安全设备设施现状评价主键
     * @return 安全设备设施现状评价
     */
    public SecurityEquipmentEvaluation selectSecurityEquipmentEvaluationById(String id);

    /**
     * 查询安全设备设施现状评价列表
     * 
     * @param securityEquipmentEvaluation 安全设备设施现状评价
     * @return 安全设备设施现状评价集合
     */
    public List<SecurityEquipmentEvaluation> selectSecurityEquipmentEvaluationList(SecurityEquipmentEvaluation securityEquipmentEvaluation);

    /**
     * 新增安全设备设施现状评价
     * 
     * @param securityEquipmentEvaluation 安全设备设施现状评价
     * @return 结果
     */
    public int insertSecurityEquipmentEvaluation(SecurityEquipmentEvaluation securityEquipmentEvaluation);

    /**
     * 修改安全设备设施现状评价
     * 
     * @param securityEquipmentEvaluation 安全设备设施现状评价
     * @return 结果
     */
    public int updateSecurityEquipmentEvaluation(SecurityEquipmentEvaluation securityEquipmentEvaluation);

    /**
     * 删除安全设备设施现状评价
     * 
     * @param id 安全设备设施现状评价主键
     * @return 结果
     */
    public int deleteSecurityEquipmentEvaluationById(String id);

    /**
     * 批量删除安全设备设施现状评价
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEquipmentEvaluationByIds(String[] ids);
}
