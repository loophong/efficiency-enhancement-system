package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityOccupationalHealthProtectiveEquipmentLedgerMapper;
import com.ruoyi.security.domain.SecurityOccupationalHealthProtectiveEquipmentLedger;
import com.ruoyi.security.service.ISecurityOccupationalHealthProtectiveEquipmentLedgerService;

/**
 * 职业健康防护用品台帐Service业务层处理
 * 
 * @author wang
 * @date 2025-03-06
 */
@Service
public class SecurityOccupationalHealthProtectiveEquipmentLedgerServiceImpl implements ISecurityOccupationalHealthProtectiveEquipmentLedgerService 
{
    @Autowired
    private SecurityOccupationalHealthProtectiveEquipmentLedgerMapper securityOccupationalHealthProtectiveEquipmentLedgerMapper;

    /**
     * 查询职业健康防护用品台帐
     * 
     * @param id 职业健康防护用品台帐主键
     * @return 职业健康防护用品台帐
     */
    @Override
    public SecurityOccupationalHealthProtectiveEquipmentLedger selectSecurityOccupationalHealthProtectiveEquipmentLedgerById(Long id)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.selectSecurityOccupationalHealthProtectiveEquipmentLedgerById(id);
    }

    /**
     * 查询职业健康防护用品台帐列表
     * 
     * @param securityOccupationalHealthProtectiveEquipmentLedger 职业健康防护用品台帐
     * @return 职业健康防护用品台帐
     */
    @Override
    public List<SecurityOccupationalHealthProtectiveEquipmentLedger> selectSecurityOccupationalHealthProtectiveEquipmentLedgerList(SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.selectSecurityOccupationalHealthProtectiveEquipmentLedgerList(securityOccupationalHealthProtectiveEquipmentLedger);
    }

    /**
     * 新增职业健康防护用品台帐
     * 
     * @param securityOccupationalHealthProtectiveEquipmentLedger 职业健康防护用品台帐
     * @return 结果
     */
    @Override
    public int insertSecurityOccupationalHealthProtectiveEquipmentLedger(SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.insertSecurityOccupationalHealthProtectiveEquipmentLedger(securityOccupationalHealthProtectiveEquipmentLedger);
    }

    /**
     * 修改职业健康防护用品台帐
     * 
     * @param securityOccupationalHealthProtectiveEquipmentLedger 职业健康防护用品台帐
     * @return 结果
     */
    @Override
    public int updateSecurityOccupationalHealthProtectiveEquipmentLedger(SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.updateSecurityOccupationalHealthProtectiveEquipmentLedger(securityOccupationalHealthProtectiveEquipmentLedger);
    }

    /**
     * 批量删除职业健康防护用品台帐
     * 
     * @param ids 需要删除的职业健康防护用品台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHealthProtectiveEquipmentLedgerByIds(Long[] ids)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.deleteSecurityOccupationalHealthProtectiveEquipmentLedgerByIds(ids);
    }

    /**
     * 删除职业健康防护用品台帐信息
     * 
     * @param id 职业健康防护用品台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHealthProtectiveEquipmentLedgerById(Long id)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.deleteSecurityOccupationalHealthProtectiveEquipmentLedgerById(id);
    }
}
