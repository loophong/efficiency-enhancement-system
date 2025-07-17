package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardousChemicalInventory;

/**
 * 危险化学品台账Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-26
 */
public interface SecurityHazardousChemicalInventoryMapper
{
    /**
     * 查询危险化学品台账
     *
     * @param id 危险化学品台账主键
     * @return 危险化学品台账
     */
    public SecurityHazardousChemicalInventory selectSecurityHazardousChemicalInventoryById(String id);

    /**
     * 查询危险化学品台账列表
     *
     * @param securityHazardousChemicalInventory 危险化学品台账
     * @return 危险化学品台账集合
     */
    public List<SecurityHazardousChemicalInventory> selectSecurityHazardousChemicalInventoryList(SecurityHazardousChemicalInventory securityHazardousChemicalInventory);

    /**
     * 新增危险化学品台账
     *
     * @param securityHazardousChemicalInventory 危险化学品台账
     * @return 结果
     */
    public int insertSecurityHazardousChemicalInventory(SecurityHazardousChemicalInventory securityHazardousChemicalInventory);

    /**
     * 修改危险化学品台账
     *
     * @param securityHazardousChemicalInventory 危险化学品台账
     * @return 结果
     */
    public int updateSecurityHazardousChemicalInventory(SecurityHazardousChemicalInventory securityHazardousChemicalInventory);

    /**
     * 删除危险化学品台账
     *
     * @param id 危险化学品台账主键
     * @return 结果
     */
    public int deleteSecurityHazardousChemicalInventoryById(String id);

    /**
     * 批量删除危险化学品台账
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityHazardousChemicalInventoryByIds(String[] ids);

    /**
     * 查询最近导入的记录（未关联ID的记录）
     *
     * @return 危险化学品台账集合
     */
    public List<SecurityHazardousChemicalInventory> selectLatestImportedRecords();

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
