package com.ruoyi.security.service.impl;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.security.domain.SecurityHazardousChemicalInventory;
import com.ruoyi.security.listener.HazardousChemicalInventoryListener;
import com.ruoyi.security.listener.SecurityEnvironmentalOhsManagementSystemDocumentsListener;
import com.ruoyi.security.listener.SecurityEnvironmentalOhsRecordListListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOhsManagementSystemDocumentsMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOhsManagementSystemDocuments;
import com.ruoyi.security.service.ISecurityEnvironmentalOhsManagementSystemDocumentsService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 环境职业健康安全管理体系文件清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Slf4j
@Service
public class SecurityEnvironmentalOhsManagementSystemDocumentsServiceImpl implements ISecurityEnvironmentalOhsManagementSystemDocumentsService {
    @Autowired
    private SecurityEnvironmentalOhsManagementSystemDocumentsMapper securityEnvironmentalOhsManagementSystemDocumentsMapper;

    /**
     * 查询环境职业健康安全管理体系文件清单
     *
     * @param id 环境职业健康安全管理体系文件清单主键
     * @return 环境职业健康安全管理体系文件清单
     */
    @Override
    public SecurityEnvironmentalOhsManagementSystemDocuments selectSecurityEnvironmentalOhsManagementSystemDocumentsById(Long id) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.selectSecurityEnvironmentalOhsManagementSystemDocumentsById(id);
    }

    /**
     * 查询环境职业健康安全管理体系文件清单列表
     *
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 环境职业健康安全管理体系文件清单
     */
    @Override
    public List<SecurityEnvironmentalOhsManagementSystemDocuments> selectSecurityEnvironmentalOhsManagementSystemDocumentsList(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.selectSecurityEnvironmentalOhsManagementSystemDocumentsList(securityEnvironmentalOhsManagementSystemDocuments);
    }

    /**
     * 新增环境职业健康安全管理体系文件清单
     *
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOhsManagementSystemDocuments(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.insertSecurityEnvironmentalOhsManagementSystemDocuments(securityEnvironmentalOhsManagementSystemDocuments);
    }

    /**
     * 修改环境职业健康安全管理体系文件清单
     *
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOhsManagementSystemDocuments(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.updateSecurityEnvironmentalOhsManagementSystemDocuments(securityEnvironmentalOhsManagementSystemDocuments);
    }

    /**
     * 批量删除环境职业健康安全管理体系文件清单
     *
     * @param ids 需要删除的环境职业健康安全管理体系文件清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOhsManagementSystemDocumentsByIds(Long[] ids) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.deleteSecurityEnvironmentalOhsManagementSystemDocumentsByIds(ids);
    }

    /**
     * 删除环境职业健康安全管理体系文件清单信息
     *
     * @param id 环境职业健康安全管理体系文件清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOhsManagementSystemDocumentsById(Long id) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.deleteSecurityEnvironmentalOhsManagementSystemDocumentsById(id);
    }

    /**
     * 更新最近导入数据的关联ID
     *
     * @param fileManagementId 文件管理ID
     * @return 更新的记录数
     */
    @Override
    public int updateLatestImportedRelatedId(Long fileManagementId) {
        if (fileManagementId == null) {
            log.warn("更新最近导入环境职业健康安全管理体系文件清单关联ID失败：fileManagementId为空");
            return 0;
        }

        log.info("更新最近导入环境职业健康安全管理体系文件清单关联ID: {}", fileManagementId);

        try {
            // 方法一：直接使用批量更新SQL
            int updatedCount = securityEnvironmentalOhsManagementSystemDocumentsMapper.updateLatestImportedRelatedId(fileManagementId);

            if (updatedCount > 0) {
                log.info("成功更新环境职业健康安全管理体系文件清单关联ID，共更新{}条记录", updatedCount);
            } else {
                log.info("没有找到需要更新关联ID的环境职业健康安全管理体系文件清单记录");

                // 方法二：如果方法一未更新任何记录，则尝试获取最近导入的记录并逐个更新
                List<SecurityEnvironmentalOhsManagementSystemDocuments> recentRecords =
                    securityEnvironmentalOhsManagementSystemDocumentsMapper.selectLatestImportedRecords();

                if (recentRecords != null && !recentRecords.isEmpty()) {
                    for (SecurityEnvironmentalOhsManagementSystemDocuments record : recentRecords) {
                        record.setRelatedId(fileManagementId);
                        updatedCount += securityEnvironmentalOhsManagementSystemDocumentsMapper.updateSecurityEnvironmentalOhsManagementSystemDocuments(record);
                    }
                    log.info("使用方法二成功更新环境职业健康安全管理体系文件清单关联ID，共更新{}条记录", updatedCount);
                }
            }

            return updatedCount;
        } catch (Exception e) {
            log.error("更新环境职业健康安全管理体系文件清单关联ID失败", e);
            return 0;
        }
    }

    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);

            try {
                EasyExcel.read(excelFile.getInputStream(), SecurityEnvironmentalOhsManagementSystemDocuments.class,
                        new SecurityEnvironmentalOhsManagementSystemDocumentsListener(securityEnvironmentalOhsManagementSystemDocumentsMapper)).headRowNumber(3).sheet().doRead();

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


