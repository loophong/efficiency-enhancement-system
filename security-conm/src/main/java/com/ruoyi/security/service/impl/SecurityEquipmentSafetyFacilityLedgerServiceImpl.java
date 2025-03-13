package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEquipmentSafetyFacilityLedgerMapper;
import com.ruoyi.security.domain.SecurityEquipmentSafetyFacilityLedger;
import com.ruoyi.security.service.ISecurityEquipmentSafetyFacilityLedgerService;

/**
 * 安全防护设备设施Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityEquipmentSafetyFacilityLedgerServiceImpl implements ISecurityEquipmentSafetyFacilityLedgerService 
{
    @Autowired
    private SecurityEquipmentSafetyFacilityLedgerMapper securityEquipmentSafetyFacilityLedgerMapper;

    /**
     * 查询安全防护设备设施
     * 
     * @param id 安全防护设备设施主键
     * @return 安全防护设备设施
     */
    @Override
    public SecurityEquipmentSafetyFacilityLedger selectSecurityEquipmentSafetyFacilityLedgerById(Long id)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.selectSecurityEquipmentSafetyFacilityLedgerById(id);
    }

    /**
     * 查询安全防护设备设施列表
     * 
     * @param securityEquipmentSafetyFacilityLedger 安全防护设备设施
     * @return 安全防护设备设施
     */
    @Override
    public List<SecurityEquipmentSafetyFacilityLedger> selectSecurityEquipmentSafetyFacilityLedgerList(SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.selectSecurityEquipmentSafetyFacilityLedgerList(securityEquipmentSafetyFacilityLedger);
    }

    /**
     * 新增安全防护设备设施
     * 
     * @param securityEquipmentSafetyFacilityLedger 安全防护设备设施
     * @return 结果
     */
    @Override
    public int insertSecurityEquipmentSafetyFacilityLedger(SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.insertSecurityEquipmentSafetyFacilityLedger(securityEquipmentSafetyFacilityLedger);
    }

    /**
     * 修改安全防护设备设施
     * 
     * @param securityEquipmentSafetyFacilityLedger 安全防护设备设施
     * @return 结果
     */
    @Override
    public int updateSecurityEquipmentSafetyFacilityLedger(SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.updateSecurityEquipmentSafetyFacilityLedger(securityEquipmentSafetyFacilityLedger);
    }

    /**
     * 批量删除安全防护设备设施
     * 
     * @param ids 需要删除的安全防护设备设施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEquipmentSafetyFacilityLedgerByIds(Long[] ids)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.deleteSecurityEquipmentSafetyFacilityLedgerByIds(ids);
    }

    /**
     * 删除安全防护设备设施信息
     * 
     * @param id 安全防护设备设施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEquipmentSafetyFacilityLedgerById(Long id)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.deleteSecurityEquipmentSafetyFacilityLedgerById(id);
    }
}
