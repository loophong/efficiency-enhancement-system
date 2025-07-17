package com.ruoyi.security.service.impl;

import java.util.List;
import com.alibaba.excel.EasyExcel;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.security.domain.dto.SecurityHazardousMaterialsHandlingLedgerImportDto;
import com.ruoyi.security.listener.HazardousMaterialsHandlingLedgerListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityHazardousMaterialsHandlingLedgerMapper;
import com.ruoyi.security.domain.SecurityHazardousMaterialsHandlingLedger;
import com.ruoyi.security.service.ISecurityHazardousMaterialsHandlingLedgerService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 危化品处理台账Service业务层处理
 *
 * @author wang
 * @date 2025-03-03
 */
@Slf4j
@Service
public class SecurityHazardousMaterialsHandlingLedgerServiceImpl implements ISecurityHazardousMaterialsHandlingLedgerService 
{
    @Autowired
    private SecurityHazardousMaterialsHandlingLedgerMapper securityHazardousMaterialsHandlingLedgerMapper;

    /**
     * 查询危化品处理台账
     * 
     * @param id 危化品处理台账主键
     * @return 危化品处理台账
     */
    @Override
    public SecurityHazardousMaterialsHandlingLedger selectSecurityHazardousMaterialsHandlingLedgerById(Long id)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.selectSecurityHazardousMaterialsHandlingLedgerById(id);
    }

    /**
     * 查询危化品处理台账列表
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 危化品处理台账
     */
    @Override
    public List<SecurityHazardousMaterialsHandlingLedger> selectSecurityHazardousMaterialsHandlingLedgerList(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.selectSecurityHazardousMaterialsHandlingLedgerList(securityHazardousMaterialsHandlingLedger);
    }

    /**
     * 新增危化品处理台账
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 结果
     */
    @Override
    public int insertSecurityHazardousMaterialsHandlingLedger(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.insertSecurityHazardousMaterialsHandlingLedger(securityHazardousMaterialsHandlingLedger);
    }

    /**
     * 修改危化品处理台账
     * 
     * @param securityHazardousMaterialsHandlingLedger 危化品处理台账
     * @return 结果
     */
    @Override
    public int updateSecurityHazardousMaterialsHandlingLedger(SecurityHazardousMaterialsHandlingLedger securityHazardousMaterialsHandlingLedger)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.updateSecurityHazardousMaterialsHandlingLedger(securityHazardousMaterialsHandlingLedger);
    }

    /**
     * 批量删除危化品处理台账
     * 
     * @param ids 需要删除的危化品处理台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardousMaterialsHandlingLedgerByIds(Long[] ids)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.deleteSecurityHazardousMaterialsHandlingLedgerByIds(ids);
    }

    /**
     * 删除危化品处理台账信息
     * 
     * @param id 危化品处理台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardousMaterialsHandlingLedgerById(Long id)
    {
        return securityHazardousMaterialsHandlingLedgerMapper.deleteSecurityHazardousMaterialsHandlingLedgerById(id);
    }

    /**
     * 导入Excel数据到数据库
     *
     * @param originalFilename 原始文件名
     * @param excelFile Excel文件
     */
    @Override
    public void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile) {
        String fileName = originalFilename;
        log.info("开始读取文件: {}", fileName);

        try {
            EasyExcel.read(excelFile.getInputStream(), SecurityHazardousMaterialsHandlingLedgerImportDto.class,
                    new HazardousMaterialsHandlingLedgerListener(securityHazardousMaterialsHandlingLedgerMapper))
                    .sheet().doRead();
            log.info("读取文件成功: {}", fileName);
        } catch (Exception e) {
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
            log.warn("更新最近导入危化品处理台账关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入危化品处理台账关联ID: {}", relatedId);

        try {
            // 直接使用批量更新SQL
            int updatedCount = securityHazardousMaterialsHandlingLedgerMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新危化品处理台账关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新危化品处理台账关联ID失败: {}", e.getMessage(), e);
            return 0;
        }
    }
}
