package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEquipmentEvaluationMapper;
import com.ruoyi.security.domain.SecurityEquipmentEvaluation;
import com.ruoyi.security.service.ISecurityEquipmentEvaluationService;

/**
 * 安全设备设施现状评价Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityEquipmentEvaluationServiceImpl implements ISecurityEquipmentEvaluationService 
{
    @Autowired
    private SecurityEquipmentEvaluationMapper securityEquipmentEvaluationMapper;

    /**
     * 查询安全设备设施现状评价
     * 
     * @param id 安全设备设施现状评价主键
     * @return 安全设备设施现状评价
     */
    @Override
    public SecurityEquipmentEvaluation selectSecurityEquipmentEvaluationById(String id)
    {
        return securityEquipmentEvaluationMapper.selectSecurityEquipmentEvaluationById(id);
    }

    /**
     * 查询安全设备设施现状评价列表
     * 
     * @param securityEquipmentEvaluation 安全设备设施现状评价
     * @return 安全设备设施现状评价
     */
    @Override
    public List<SecurityEquipmentEvaluation> selectSecurityEquipmentEvaluationList(SecurityEquipmentEvaluation securityEquipmentEvaluation)
    {
        return securityEquipmentEvaluationMapper.selectSecurityEquipmentEvaluationList(securityEquipmentEvaluation);
    }

    /**
     * 新增安全设备设施现状评价
     * 
     * @param securityEquipmentEvaluation 安全设备设施现状评价
     * @return 结果
     */
    @Override
    public int insertSecurityEquipmentEvaluation(SecurityEquipmentEvaluation securityEquipmentEvaluation)
    {
        return securityEquipmentEvaluationMapper.insertSecurityEquipmentEvaluation(securityEquipmentEvaluation);
    }

    /**
     * 修改安全设备设施现状评价
     * 
     * @param securityEquipmentEvaluation 安全设备设施现状评价
     * @return 结果
     */
    @Override
    public int updateSecurityEquipmentEvaluation(SecurityEquipmentEvaluation securityEquipmentEvaluation)
    {
        return securityEquipmentEvaluationMapper.updateSecurityEquipmentEvaluation(securityEquipmentEvaluation);
    }

    /**
     * 批量删除安全设备设施现状评价
     * 
     * @param ids 需要删除的安全设备设施现状评价主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEquipmentEvaluationByIds(String[] ids)
    {
        return securityEquipmentEvaluationMapper.deleteSecurityEquipmentEvaluationByIds(ids);
    }

    /**
     * 删除安全设备设施现状评价信息
     * 
     * @param id 安全设备设施现状评价主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEquipmentEvaluationById(String id)
    {
        return securityEquipmentEvaluationMapper.deleteSecurityEquipmentEvaluationById(id);
    }
}
