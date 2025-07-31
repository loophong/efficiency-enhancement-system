package com.ruoyi.security.service.impl;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.security.listener.HazardousMaterialsSafetyInspectioListener;
import com.ruoyi.security.listener.SecurityEnvironmentalOhsRecordListListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityHazardousMaterialsSafetyInspectionMapper;
import com.ruoyi.security.domain.SecurityHazardousMaterialsSafetyInspection;
import com.ruoyi.security.service.ISecurityHazardousMaterialsSafetyInspectionService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 危化品检查记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-03
 */
@Slf4j
@Service
public class SecurityHazardousMaterialsSafetyInspectionServiceImpl implements ISecurityHazardousMaterialsSafetyInspectionService 
{
    @Autowired
    private SecurityHazardousMaterialsSafetyInspectionMapper securityHazardousMaterialsSafetyInspectionMapper;

    /**
     * 查询危化品检查记录
     * 
     * @param id 危化品检查记录主键
     * @return 危化品检查记录
     */
    @Override
    public SecurityHazardousMaterialsSafetyInspection selectSecurityHazardousMaterialsSafetyInspectionById(Long id)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.selectSecurityHazardousMaterialsSafetyInspectionById(id);
    }

    /**
     * 查询危化品检查记录列表
     * 
     * @param securityHazardousMaterialsSafetyInspection 危化品检查记录
     * @return 危化品检查记录
     */
    @Override
    public List<SecurityHazardousMaterialsSafetyInspection> selectSecurityHazardousMaterialsSafetyInspectionList(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.selectSecurityHazardousMaterialsSafetyInspectionList(securityHazardousMaterialsSafetyInspection);
    }

    /**
     * 新增危化品检查记录
     * 
     * @param securityHazardousMaterialsSafetyInspection 危化品检查记录
     * @return 结果
     */
    @Override
    public int insertSecurityHazardousMaterialsSafetyInspection(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.insertSecurityHazardousMaterialsSafetyInspection(securityHazardousMaterialsSafetyInspection);
    }

    /**
     * 修改危化品检查记录
     * 
     * @param securityHazardousMaterialsSafetyInspection 危化品检查记录
     * @return 结果
     */
    @Override
    public int updateSecurityHazardousMaterialsSafetyInspection(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.updateSecurityHazardousMaterialsSafetyInspection(securityHazardousMaterialsSafetyInspection);
    }

    /**
     * 批量删除危化品检查记录
     * 
     * @param ids 需要删除的危化品检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardousMaterialsSafetyInspectionByIds(Long[] ids)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.deleteSecurityHazardousMaterialsSafetyInspectionByIds(ids);
    }

    /**
     * 删除危化品检查记录信息
     * 
     * @param id 危化品检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardousMaterialsSafetyInspectionById(Long id)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.deleteSecurityHazardousMaterialsSafetyInspectionById(id);
    }

    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(), SecurityHazardousMaterialsSafetyInspection.class,
                        new HazardousMaterialsSafetyInspectioListener(securityHazardousMaterialsSafetyInspectionMapper)).headRowNumber(5).sheet().doRead();
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
            log.warn("更新最近导入危化品检查记录关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入危化品检查记录关联ID: {}", relatedId);

        try {
            // 直接使用批量更新SQL
            int updatedCount = securityHazardousMaterialsSafetyInspectionMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新危化品检查记录关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新危化品检查记录关联ID失败: {}", e.getMessage(), e);
            return 0;
        }
    }
}

