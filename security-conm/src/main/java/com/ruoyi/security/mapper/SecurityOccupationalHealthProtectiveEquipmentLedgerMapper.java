package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityOccupationalHealthProtectiveEquipmentLedger;

/**
 * 职业健康防护用品台帐Mapper接口
 * 
 * @author wang
 * @date 2025-03-06
 */
public interface SecurityOccupationalHealthProtectiveEquipmentLedgerMapper 
{
    /**
     * 查询职业健康防护用品台帐
     * 
     * @param id 职业健康防护用品台帐主键
     * @return 职业健康防护用品台帐
     */
    public SecurityOccupationalHealthProtectiveEquipmentLedger selectSecurityOccupationalHealthProtectiveEquipmentLedgerById(Long id);

    /**
     * 查询职业健康防护用品台帐列表
     * 
     * @param securityOccupationalHealthProtectiveEquipmentLedger 职业健康防护用品台帐
     * @return 职业健康防护用品台帐集合
     */
    public List<SecurityOccupationalHealthProtectiveEquipmentLedger> selectSecurityOccupationalHealthProtectiveEquipmentLedgerList(SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger);

    /**
     * 新增职业健康防护用品台帐
     * 
     * @param securityOccupationalHealthProtectiveEquipmentLedger 职业健康防护用品台帐
     * @return 结果
     */
    public int insertSecurityOccupationalHealthProtectiveEquipmentLedger(SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger);

    /**
     * 修改职业健康防护用品台帐
     * 
     * @param securityOccupationalHealthProtectiveEquipmentLedger 职业健康防护用品台帐
     * @return 结果
     */
    public int updateSecurityOccupationalHealthProtectiveEquipmentLedger(SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger);

    /**
     * 删除职业健康防护用品台帐
     * 
     * @param id 职业健康防护用品台帐主键
     * @return 结果
     */
    public int deleteSecurityOccupationalHealthProtectiveEquipmentLedgerById(Long id);

    /**
     * 批量删除职业健康防护用品台帐
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityOccupationalHealthProtectiveEquipmentLedgerByIds(Long[] ids);

    /**
     * 根据关联ID查询职业健康防护用品台帐列表
     *
     * @param relatedId 关联ID
     * @return 职业健康防护用品台帐集合
     */
    public List<SecurityOccupationalHealthProtectiveEquipmentLedger> selectSecurityOccupationalHealthProtectiveEquipmentLedgerByRelatedId(Long relatedId);

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
