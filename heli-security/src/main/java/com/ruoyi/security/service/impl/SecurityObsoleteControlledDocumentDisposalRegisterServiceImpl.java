package com.ruoyi.security.service.impl;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.security.listener.SecurityObsoleteControlledDocumentDisposalRegisterListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityObsoleteControlledDocumentDisposalRegisterMapper;
import com.ruoyi.security.domain.SecurityObsoleteControlledDocumentDisposalRegister;
import com.ruoyi.security.service.ISecurityObsoleteControlledDocumentDisposalRegisterService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 作废受控文件收回销毁登记Service业务层处理
 *
 * @author wang
 * @date 2025-03-02
 */
@Slf4j
@Service
public class SecurityObsoleteControlledDocumentDisposalRegisterServiceImpl implements ISecurityObsoleteControlledDocumentDisposalRegisterService
{
    @Autowired
    private SecurityObsoleteControlledDocumentDisposalRegisterMapper securityObsoleteControlledDocumentDisposalRegisterMapper;

    /**
     * 查询作废受控文件收回销毁登记
     *
     * @param id 作废受控文件收回销毁登记主键
     * @return 作废受控文件收回销毁登记
     */
    @Override
    public SecurityObsoleteControlledDocumentDisposalRegister selectSecurityObsoleteControlledDocumentDisposalRegisterById(Long id)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.selectSecurityObsoleteControlledDocumentDisposalRegisterById(id);
    }

    /**
     * 查询作废受控文件收回销毁登记列表
     *
     * @param securityObsoleteControlledDocumentDisposalRegister 作废受控文件收回销毁登记
     * @return 作废受控文件收回销毁登记
     */
    @Override
    public List<SecurityObsoleteControlledDocumentDisposalRegister> selectSecurityObsoleteControlledDocumentDisposalRegisterList(SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.selectSecurityObsoleteControlledDocumentDisposalRegisterList(securityObsoleteControlledDocumentDisposalRegister);
    }

    /**
     * 新增作废受控文件收回销毁登记
     *
     * @param securityObsoleteControlledDocumentDisposalRegister 作废受控文件收回销毁登记
     * @return 结果
     */
    @Override
    public int insertSecurityObsoleteControlledDocumentDisposalRegister(SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.insertSecurityObsoleteControlledDocumentDisposalRegister(securityObsoleteControlledDocumentDisposalRegister);
    }

    /**
     * 修改作废受控文件收回销毁登记
     *
     * @param securityObsoleteControlledDocumentDisposalRegister 作废受控文件收回销毁登记
     * @return 结果
     */
    @Override
    public int updateSecurityObsoleteControlledDocumentDisposalRegister(SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.updateSecurityObsoleteControlledDocumentDisposalRegister(securityObsoleteControlledDocumentDisposalRegister);
    }

    /**
     * 批量删除作废受控文件收回销毁登记
     *
     * @param ids 需要删除的作废受控文件收回销毁登记主键
     * @return 结果
     */
    @Override
    public int deleteSecurityObsoleteControlledDocumentDisposalRegisterByIds(Long[] ids)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.deleteSecurityObsoleteControlledDocumentDisposalRegisterByIds(ids);
    }

    /**
     * 删除作废受控文件收回销毁登记信息
     *
     * @param id 作废受控文件收回销毁登记主键
     * @return 结果
     */
    @Override
    public int deleteSecurityObsoleteControlledDocumentDisposalRegisterById(Long id)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.deleteSecurityObsoleteControlledDocumentDisposalRegisterById(id);
    }
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);

            try {
                // 创建一个监听器实例
                SecurityObsoleteControlledDocumentDisposalRegisterListener listener =
                    new SecurityObsoleteControlledDocumentDisposalRegisterListener(securityObsoleteControlledDocumentDisposalRegisterMapper);

                EasyExcel.read(excelFile.getInputStream(), SecurityObsoleteControlledDocumentDisposalRegister.class,
                        listener).headRowNumber(1).sheet().doRead();

                // 检查是否有数据被读取
                if (listener.getDataList() == null || listener.getDataList().isEmpty()) {
                    log.warn("Excel文件中没有读取到有效数据: {}", fileName);
                    return;
                }

                log.info("读取文件成功: {}", fileName);

            } catch (Exception e) {
                log.error("读取文件失败: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
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
            log.warn("更新最近导入作废受控文件收回销毁登记关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入作废受控文件收回销毁登记关联ID: {}", relatedId);

        try {
            // 方法一：直接使用批量更新SQL
            int updatedCount = securityObsoleteControlledDocumentDisposalRegisterMapper.updateLatestImportedRelatedId(relatedId);

            if (updatedCount > 0) {
                log.info("成功更新作废受控文件收回销毁登记关联ID，共更新{}条记录", updatedCount);
            } else {
                log.info("没有找到需要更新关联ID的作废受控文件收回销毁登记记录");

                // 方法二：如果方法一未更新任何记录，则尝试获取最近导入的记录并逐个更新
                List<SecurityObsoleteControlledDocumentDisposalRegister> recentRecords =
                    securityObsoleteControlledDocumentDisposalRegisterMapper.selectLatestImportedRecords();

                if (recentRecords != null && !recentRecords.isEmpty()) {
                    for (SecurityObsoleteControlledDocumentDisposalRegister record : recentRecords) {
                        record.setRelatedId(relatedId);
                        updatedCount += securityObsoleteControlledDocumentDisposalRegisterMapper.updateSecurityObsoleteControlledDocumentDisposalRegister(record);
                    }
                    log.info("使用方法二成功更新作废受控文件收回销毁登记关联ID，共更新{}条记录", updatedCount);
                }
            }

            return updatedCount;
        } catch (Exception e) {
            log.error("更新作废受控文件收回销毁登记关联ID失败", e);
            return 0;
        }
    }
}
