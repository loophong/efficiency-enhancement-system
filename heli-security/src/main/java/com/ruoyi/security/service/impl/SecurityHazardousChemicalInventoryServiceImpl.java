package com.ruoyi.security.service.impl;

import java.util.List;
import com.alibaba.excel.EasyExcel;
import com.ruoyi.common.utils.DateUtils;
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
                        new HazardousChemicalInventoryListener(securityHazardousChemicalInventoryMapper)).headRowNumber(2).sheet().doRead();

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

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        if (relatedId == null) {
            log.warn("更新最近导入危险化学品台账关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入危险化学品台账关联ID: {}", relatedId);

        try {
            // 方法一：直接使用批量更新SQL
            int updatedCount = securityHazardousChemicalInventoryMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新危险化学品台账关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新危险化学品台账关联ID失败: {}", e.getMessage(), e);

            // 方法二：如果批量更新失败，尝试逐条更新
            try {
                List<SecurityHazardousChemicalInventory> recentRecords =
                        securityHazardousChemicalInventoryMapper.selectLatestImportedRecords();

                if (recentRecords == null || recentRecords.isEmpty()) {
                    log.info("没有找到需要更新关联ID的危险化学品台账记录");
                    return 0;
                }

                int updatedCount = 0;
                for (SecurityHazardousChemicalInventory record : recentRecords) {
                    record.setRelatedId(relatedId);
                    record.setUpdateTime(DateUtils.getNowDate());
                    updatedCount += securityHazardousChemicalInventoryMapper.updateSecurityHazardousChemicalInventory(record);
                }

                log.info("逐条更新危险化学品台账关联ID成功，更新记录数: {}", updatedCount);
                return updatedCount;
            } catch (Exception ex) {
                log.error("逐条更新危险化学品台账关联ID也失败: {}", ex.getMessage(), ex);
                return 0;
            }
        }
    }
}
