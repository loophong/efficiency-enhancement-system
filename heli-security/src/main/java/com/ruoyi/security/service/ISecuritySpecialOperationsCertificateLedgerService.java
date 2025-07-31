package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecuritySpecialOperationsCertificateLedger;

/**
 * 特种设备台账Service接口
 * 
 * @author wang
 * @date 2025-03-06
 */
public interface ISecuritySpecialOperationsCertificateLedgerService 
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
     * 批量删除特种设备台账
     * 
     * @param ids 需要删除的特种设备台账主键集合
     * @return 结果
     */
    public int deleteSecuritySpecialOperationsCertificateLedgerByIds(Long[] ids);

    /**
     * 删除特种设备台账信息
     *
     * @param id 特种设备台账主键
     * @return 结果
     */
    public int deleteSecuritySpecialOperationsCertificateLedgerById(Long id);

    /**
     * 导入特种设备台账数据
     *
     * @param specialOperationsCertificateLedgerList 特种设备台账数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importSpecialOperationsCertificateLedger(List<SecuritySpecialOperationsCertificateLedger> specialOperationsCertificateLedgerList, Boolean isUpdateSupport, String operName);

    /**
     * 根据关联ID查询特种设备台账列表
     *
     * @param relatedId 关联ID
     * @return 特种设备台账集合
     */
    public List<SecuritySpecialOperationsCertificateLedger> selectSecuritySpecialOperationsCertificateLedgerByRelatedId(Long relatedId);

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
