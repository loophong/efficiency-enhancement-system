package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityHazardousChemicalInventoryMapper;
import com.ruoyi.security.domain.SecurityHazardousChemicalInventory;
import com.ruoyi.security.service.ISecurityHazardousChemicalInventoryService;

/**
 * 危险化学品台账Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Service
public class SecurityHazardousChemicalInventoryServiceImpl implements ISecurityHazardousChemicalInventoryService
{
    @Autowired
    private SecurityHazardousChemicalInventoryMapper securityHazardousChemicalInventoryMapper;

    /**
     * 查询危险化学品台账
     *
     * @param id 危险化学品台账主键
     * @return 危险化学品台账
     */
    @Override
    public SecurityHazardousChemicalInventory selectSecurityHazardousChemicalInventoryById(String id)
    {
        return securityHazardousChemicalInventoryMapper.selectSecurityHazardousChemicalInventoryById(id);
    }

    /**
     * 查询危险化学品台账列表
     *
     * @param securityHazardousChemicalInventory 危险化学品台账
     * @return 危险化学品台账
     */
    @Override
    public List<SecurityHazardousChemicalInventory> selectSecurityHazardousChemicalInventoryList(SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        return securityHazardousChemicalInventoryMapper.selectSecurityHazardousChemicalInventoryList(securityHazardousChemicalInventory);
    }

    /**
     * 新增危险化学品台账
     *
     * @param securityHazardousChemicalInventory 危险化学品台账
     * @return 结果
     */
    @Override
    public int insertSecurityHazardousChemicalInventory(SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        return securityHazardousChemicalInventoryMapper.insertSecurityHazardousChemicalInventory(securityHazardousChemicalInventory);
    }

    /**
     * 修改危险化学品台账
     *
     * @param securityHazardousChemicalInventory 危险化学品台账
     * @return 结果
     */
    @Override
    public int updateSecurityHazardousChemicalInventory(SecurityHazardousChemicalInventory securityHazardousChemicalInventory)
    {
        return securityHazardousChemicalInventoryMapper.updateSecurityHazardousChemicalInventory(securityHazardousChemicalInventory);
    }

    /**
     * 批量删除危险化学品台账
     *
     * @param ids 需要删除的危险化学品台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardousChemicalInventoryByIds(String[] ids)
    {
        return securityHazardousChemicalInventoryMapper.deleteSecurityHazardousChemicalInventoryByIds(ids);
    }

    /**
     * 删除危险化学品台账信息
     *
     * @param id 危险化学品台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardousChemicalInventoryById(String id)
    {
        return securityHazardousChemicalInventoryMapper.deleteSecurityHazardousChemicalInventoryById(id);
    }
}
