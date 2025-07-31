package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardousMaterialsHandlingLedger;

/**
 * 危化品处理台账Mapper接口
 * 
 * @author wang
 * @date 2025-03-03
 */
public interface SecurityHazardousMaterialsHandlingLedgerMapper 
{
    /**
     * 查询危化品处理台账
     * 
     * @param id 危化品处理台账主键
     * @return 危化品处理台账
     */
    public SecurityHazardousMaterialsHandlingLedger selectSecurityHazardousMaterialsHandlingLedgerById(Long id);

    /**
     * 查询危化品处理台账列表
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 危化品处理台账集合
     */
    public List<SecurityHazardousMaterialsHandlingLedger> selectSecurityHazardousMaterialsHandlingLedgerList(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger);

    /**
     * 新增危化品处理台账
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 结果
     */
    public int insertSecurityHazardousMaterialsHandlingLedger(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger);

    /**
     * 修改危化品处理台账
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 结果
     */
    public int updateSecurityHazardousMaterialsHandlingLedger(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger);

    /**
     * 删除危化品处理台账
     * 
     * @param id 危化品处理台账主键
     * @return 结果
     */
    public int deleteSecurityHazardousMaterialsHandlingLedgerById(Long id);

    /**
     * 批量删除危化品处理台账
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityHazardousMaterialsHandlingLedgerByIds(Long[] ids);

    /**
     * 查询最近导入的记录（未关联ID的记录）
     *
     * @return 危化品处理台账集合
     */
    public List<SecurityHazardousMaterialsHandlingLedger> selectLatestImportedRecords();

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
