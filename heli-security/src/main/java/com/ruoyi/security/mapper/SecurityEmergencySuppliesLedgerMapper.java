package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEmergencySuppliesLedger;

/**
 * 应急物资管理台帐Mapper接口
 * 
 * @author wang
 * @date 2025-03-06
 */
public interface SecurityEmergencySuppliesLedgerMapper 
{
    /**
     * 查询应急物资管理台帐
     * 
     * @param id 应急物资管理台帐主键
     * @return 应急物资管理台帐
     */
    public SecurityEmergencySuppliesLedger selectSecurityEmergencySuppliesLedgerById(Long id);

    /**
     * 查询应急物资管理台帐列表
     * 
     * @param securityEmergencySuppliesLedger 应急物资管理台帐
     * @return 应急物资管理台帐集合
     */
    public List<SecurityEmergencySuppliesLedger> selectSecurityEmergencySuppliesLedgerList(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger);

    /**
     * 新增应急物资管理台帐
     * 
     * @param securityEmergencySuppliesLedger 应急物资管理台帐
     * @return 结果
     */
    public int insertSecurityEmergencySuppliesLedger(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger);

    /**
     * 修改应急物资管理台帐
     * 
     * @param securityEmergencySuppliesLedger 应急物资管理台帐
     * @return 结果
     */
    public int updateSecurityEmergencySuppliesLedger(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger);

    /**
     * 删除应急物资管理台帐
     * 
     * @param id 应急物资管理台帐主键
     * @return 结果
     */
    public int deleteSecurityEmergencySuppliesLedgerById(Long id);

    /**
     * 批量删除应急物资管理台帐
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEmergencySuppliesLedgerByIds(Long[] ids);

    /**
     * 根据关联ID查询应急物资管理台帐列表
     *
     * @param relatedId 关联ID
     * @return 应急物资管理台帐集合
     */
    public List<SecurityEmergencySuppliesLedger> selectSecurityEmergencySuppliesLedgerByRelatedId(Long relatedId);

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
