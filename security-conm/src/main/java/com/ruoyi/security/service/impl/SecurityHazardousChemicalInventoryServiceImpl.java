package com.ruoyi.security.service.impl;

import java.util.List;
import com.alibaba.excel.EasyExcel;
import com.ruoyi.security.listener.HazardousChemicalInventoryListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityHazardousChemicalInventoryMapper;
import com.ruoyi.security.domain.SecurityHazardousChemicalInventory;
import com.ruoyi.security.service.ISecurityHazardousChemicalInventoryService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 危险化学品台账Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Slf4j
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


    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);

            try {


                EasyExcel.read(excelFile.getInputStream(), SecurityHazardousChemicalInventory.class,
                        new HazardousChemicalInventoryListener(securityHazardousChemicalInventoryMapper)).headRowNumber(4).sheet().doRead();

                log.info("读取文件成功: {}", fileName);

            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }

//            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
//            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }
}
