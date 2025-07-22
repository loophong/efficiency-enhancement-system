package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEquipmentSafetyFacilityLedger;

/**
 * 安全防护设备设施Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecurityEquipmentSafetyFacilityLedgerMapper 
{
    /**
     * 查询安全防护设备设施
     * 
     * @param id 安全防护设备设施主键
     * @return 安全防护设备设施
     */
    public SecurityEquipmentSafetyFacilityLedger selectSecurityEquipmentSafetyFacilityLedgerById(Long id);

    /**
     * 查询安全防护设备设施列表
     * 
     * @param securityEquipmentSafetyFacilityLedger 安全防护设备设施
     * @return 安全防护设备设施集合
     */
    public List<SecurityEquipmentSafetyFacilityLedger> selectSecurityEquipmentSafetyFacilityLedgerList(SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger);

    /**
     * 新增安全防护设备设施
     * 
     * @param securityEquipmentSafetyFacilityLedger 安全防护设备设施
     * @return 结果
     */
    public int insertSecurityEquipmentSafetyFacilityLedger(SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger);

    /**
     * 修改安全防护设备设施
     * 
     * @param securityEquipmentSafetyFacilityLedger 安全防护设备设施
     * @return 结果
     */
    public int updateSecurityEquipmentSafetyFacilityLedger(SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger);

    /**
     * 删除安全防护设备设施
     * 
     * @param id 安全防护设备设施主键
     * @return 结果
     */
    public int deleteSecurityEquipmentSafetyFacilityLedgerById(Long id);

    /**
     * 批量删除安全防护设备设施
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEquipmentSafetyFacilityLedgerByIds(Long[] ids);

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);

    /**
     * 根据关联ID查询安全防护设备设施列表
     *
     * @param relatedId 关联ID
     * @return 安全防护设备设施集合
     */
    public List<SecurityEquipmentSafetyFacilityLedger> selectSecurityEquipmentSafetyFacilityLedgerByRelatedId(Long relatedId);
}
