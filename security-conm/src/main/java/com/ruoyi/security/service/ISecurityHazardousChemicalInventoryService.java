package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardousChemicalInventory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 危险化学品台账Service接口
 *
 * @author ruoyi
 * @date 2025-02-26
 */
public interface ISecurityHazardousChemicalInventoryService
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
     * 批量删除危险化学品台账
     *
     * @param ids 需要删除的危险化学品台账主键集合
     * @return 结果
     */
    public int deleteSecurityHazardousChemicalInventoryByIds(String[] ids);

    /**
     * 删除危险化学品台账信息
     *
     * @param id 危险化学品台账主键
     * @return 结果
     */
    public int deleteSecurityHazardousChemicalInventoryById(String id);

    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile);
}
