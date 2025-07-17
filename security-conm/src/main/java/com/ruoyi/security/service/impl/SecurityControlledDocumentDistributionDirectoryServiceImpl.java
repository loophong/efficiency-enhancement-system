package com.ruoyi.security.service.impl;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.security.domain.SecurityEnvironmentalOhsManagementSystemDocuments;
import com.ruoyi.security.listener.SecurityControlledDocumentDistributionDirectoryListener;
import com.ruoyi.security.listener.SecurityEnvironmentalOhsManagementSystemDocumentsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityControlledDocumentDistributionDirectoryMapper;
import com.ruoyi.security.domain.SecurityControlledDocumentDistributionDirectory;
import com.ruoyi.security.service.ISecurityControlledDocumentDistributionDirectoryService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 受控文件发放目录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Service
@Slf4j
public class SecurityControlledDocumentDistributionDirectoryServiceImpl implements ISecurityControlledDocumentDistributionDirectoryService 
{
    @Autowired
    private SecurityControlledDocumentDistributionDirectoryMapper securityControlledDocumentDistributionDirectoryMapper;

    /**
     * 查询受控文件发放目录
     * 
     * @param id 受控文件发放目录主键
     * @return 受控文件发放目录
     */
    @Override
    public SecurityControlledDocumentDistributionDirectory selectSecurityControlledDocumentDistributionDirectoryById(Long id)
    {
        return securityControlledDocumentDistributionDirectoryMapper.selectSecurityControlledDocumentDistributionDirectoryById(id);
    }

    /**
     * 查询受控文件发放目录列表
     * 
     * @param securityControlledDocumentDistributionDirectory 受控文件发放目录
     * @return 受控文件发放目录
     */
    @Override
    public List<SecurityControlledDocumentDistributionDirectory> selectSecurityControlledDocumentDistributionDirectoryList(SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory)
    {
        return securityControlledDocumentDistributionDirectoryMapper.selectSecurityControlledDocumentDistributionDirectoryList(securityControlledDocumentDistributionDirectory);
    }

    /**
     * 新增受控文件发放目录
     * 
     * @param securityControlledDocumentDistributionDirectory 受控文件发放目录
     * @return 结果
     */
    @Override
    public int insertSecurityControlledDocumentDistributionDirectory(SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory)
    {
        return securityControlledDocumentDistributionDirectoryMapper.insertSecurityControlledDocumentDistributionDirectory(securityControlledDocumentDistributionDirectory);
    }

    /**
     * 修改受控文件发放目录
     * 
     * @param securityControlledDocumentDistributionDirectory 受控文件发放目录
     * @return 结果
     */
    @Override
    public int updateSecurityControlledDocumentDistributionDirectory(SecurityControlledDocumentDistributionDirectory securityControlledDocumentDistributionDirectory)
    {
        return securityControlledDocumentDistributionDirectoryMapper.updateSecurityControlledDocumentDistributionDirectory(securityControlledDocumentDistributionDirectory);
    }

    /**
     * 批量删除受控文件发放目录
     * 
     * @param ids 需要删除的受控文件发放目录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityControlledDocumentDistributionDirectoryByIds(Long[] ids)
    {
        return securityControlledDocumentDistributionDirectoryMapper.deleteSecurityControlledDocumentDistributionDirectoryByIds(ids);
    }

    /**
     * 删除受控文件发放目录信息
     * 
     * @param id 受控文件发放目录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityControlledDocumentDistributionDirectoryById(Long id)
    {
        return securityControlledDocumentDistributionDirectoryMapper.deleteSecurityControlledDocumentDistributionDirectoryById(id);
    }

    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);

            try {
                EasyExcel.read(excelFile.getInputStream(), SecurityControlledDocumentDistributionDirectory.class,
                        new SecurityControlledDocumentDistributionDirectoryListener(securityControlledDocumentDistributionDirectoryMapper)).headRowNumber(3).sheet().doRead();

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
            log.warn("更新最近导入受控文件发放目录关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入受控文件发放目录关联ID: {}", relatedId);

        try {
            // 方法一：直接使用批量更新SQL
            int updatedCount = securityControlledDocumentDistributionDirectoryMapper.updateLatestImportedRelatedId(relatedId);

            if (updatedCount > 0) {
                log.info("成功更新受控文件发放目录关联ID，共更新{}条记录", updatedCount);
            } else {
                log.info("没有找到需要更新关联ID的受控文件发放目录记录");

                // 方法二：如果方法一未更新任何记录，则尝试获取最近导入的记录并逐个更新
                List<SecurityControlledDocumentDistributionDirectory> recentRecords =
                    securityControlledDocumentDistributionDirectoryMapper.selectLatestImportedRecords();

                if (recentRecords != null && !recentRecords.isEmpty()) {
                    for (SecurityControlledDocumentDistributionDirectory record : recentRecords) {
                        record.setRelatedId(relatedId);
                        updatedCount += securityControlledDocumentDistributionDirectoryMapper.updateSecurityControlledDocumentDistributionDirectory(record);
                    }
                    log.info("使用方法二成功更新受控文件发放目录关联ID，共更新{}条记录", updatedCount);
                }
            }

            return updatedCount;
        } catch (Exception e) {
            log.error("更新受控文件发放目录关联ID失败", e);
            return 0;
        }
    }
}
