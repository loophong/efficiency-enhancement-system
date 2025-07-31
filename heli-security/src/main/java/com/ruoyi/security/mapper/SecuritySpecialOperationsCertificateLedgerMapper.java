package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecuritySpecialOperationsCertificateLedger;

/**
 * 特种设备台账Mapper接口
 * 
 * @author wang
 * @date 2025-03-06
 */
public interface SecuritySpecialOperationsCertificateLedgerMapper 
{
    /**
     * 查询特种设备台账
     * 
     * @param id 特种设备台账主键
     * @return 特种设备台账
     */
    public SecuritySpecialOperationsCertificateLedger selectSecuritySpecialOperationsCertificateLedgerById(Long id);

    /**
     * 查询特种设备台账列表
     * 
     * @param securitySpecialOperationsCertificateLedger 特种设备台账
     * @return 特种设备台账集合
     */
    public List<SecuritySpecialOperationsCertificateLedger> selectSecuritySpecialOperationsCertificateLedgerList(SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger);

    /**
     * 新增特种设备台账
     * 
     * @param securitySpecialOperationsCertificateLedger 特种设备台账
     * @return 结果
     */
    public int insertSecuritySpecialOperationsCertificateLedger(SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger);

    /**
     * 修改特种设备台账
     * 
     * @param securitySpecialOperationsCertificateLedger 特种设备台账
     * @return 结果
     */
    public int updateSecuritySpecialOperationsCertificateLedger(SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger);

    /**
     * 删除特种设备台账
     * 
     * @param id 特种设备台账主键
     * @return 结果
     */
    public int deleteSecuritySpecialOperationsCertificateLedgerById(Long id);

    /**
     * 批量删除特种设备台账
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecuritySpecialOperationsCertificateLedgerByIds(Long[] ids);

    /**
     * 根据设备名称查询特种设备台账
     *
     * @param equipmentName 设备名称
     * @return 特种设备台账
     */
    public SecuritySpecialOperationsCertificateLedger selectSecuritySpecialOperationsCertificateLedgerByName(String equipmentName);

    /**
     * 根据内部编号查询特种设备台账
     *
     * @param internalNumber 内部编号
     * @return 特种设备台账
     */
    public SecuritySpecialOperationsCertificateLedger selectSecuritySpecialOperationsCertificateLedgerByInternalNumber(String internalNumber);

    /**
     * 根据关联ID查询特种设备台账列表
     *
     * @param relatedId 关联ID
     * @return 特种设备台账集合
     */
    public List<SecuritySpecialOperationsCertificateLedger> selectSecuritySpecialOperationsCertificateLedgerByRelatedId(Long relatedId);

    /**
     * 批量更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
