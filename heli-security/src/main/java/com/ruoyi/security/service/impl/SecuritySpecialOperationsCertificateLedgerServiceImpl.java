package com.ruoyi.security.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySpecialOperationsCertificateLedgerMapper;
import com.ruoyi.security.domain.SecuritySpecialOperationsCertificateLedger;
import com.ruoyi.security.service.ISecuritySpecialOperationsCertificateLedgerService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 特种设备台账Service业务层处理
 * 
 * @author wang
 * @date 2025-03-06
 */
@Service
public class SecuritySpecialOperationsCertificateLedgerServiceImpl implements ISecuritySpecialOperationsCertificateLedgerService
{
    private static final Logger log = LoggerFactory.getLogger(SecuritySpecialOperationsCertificateLedgerServiceImpl.class);

    @Autowired
    private SecuritySpecialOperationsCertificateLedgerMapper securitySpecialOperationsCertificateLedgerMapper;

    /**
     * 查询特种设备台账
     * 
     * @param id 特种设备台账主键
     * @return 特种设备台账
     */
    @Override
    public SecuritySpecialOperationsCertificateLedger selectSecuritySpecialOperationsCertificateLedgerById(Long id)
    {
        return securitySpecialOperationsCertificateLedgerMapper.selectSecuritySpecialOperationsCertificateLedgerById(id);
    }

    /**
     * 查询特种设备台账列表
     * 
     * @param securitySpecialOperationsCertificateLedger 特种设备台账
     * @return 特种设备台账
     */
    @Override
    public List<SecuritySpecialOperationsCertificateLedger> selectSecuritySpecialOperationsCertificateLedgerList(SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        return securitySpecialOperationsCertificateLedgerMapper.selectSecuritySpecialOperationsCertificateLedgerList(securitySpecialOperationsCertificateLedger);
    }

    /**
     * 新增特种设备台账
     * 
     * @param securitySpecialOperationsCertificateLedger 特种设备台账
     * @return 结果
     */
    @Override
    public int insertSecuritySpecialOperationsCertificateLedger(SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        return securitySpecialOperationsCertificateLedgerMapper.insertSecuritySpecialOperationsCertificateLedger(securitySpecialOperationsCertificateLedger);
    }

    /**
     * 修改特种设备台账
     * 
     * @param securitySpecialOperationsCertificateLedger 特种设备台账
     * @return 结果
     */
    @Override
    public int updateSecuritySpecialOperationsCertificateLedger(SecuritySpecialOperationsCertificateLedger securitySpecialOperationsCertificateLedger)
    {
        return securitySpecialOperationsCertificateLedgerMapper.updateSecuritySpecialOperationsCertificateLedger(securitySpecialOperationsCertificateLedger);
    }

    /**
     * 批量删除特种设备台账
     *
     * @param ids 需要删除的特种设备台账主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialOperationsCertificateLedgerByIds(Long[] ids)
    {
        return securitySpecialOperationsCertificateLedgerMapper.deleteSecuritySpecialOperationsCertificateLedgerByIds(ids);
    }

    /**
     * 删除特种设备台账信息
     *
     * @param id 特种设备台账主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialOperationsCertificateLedgerById(Long id)
    {
        return securitySpecialOperationsCertificateLedgerMapper.deleteSecuritySpecialOperationsCertificateLedgerById(id);
    }

    /**
     * 导入特种设备台账数据
     *
     * @param specialOperationsCertificateLedgerList 特种设备台账数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importSpecialOperationsCertificateLedger(List<SecuritySpecialOperationsCertificateLedger> specialOperationsCertificateLedgerList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(specialOperationsCertificateLedgerList) || specialOperationsCertificateLedgerList.size() == 0) {
            throw new ServiceException("导入特种设备台账数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (SecuritySpecialOperationsCertificateLedger specialOperationsCertificateLedger : specialOperationsCertificateLedgerList) {
            try {
                // 清空id字段，确保使用数据库自动生成的id
                specialOperationsCertificateLedger.setId(null);

                // 验证和处理日期字段
                validateAndProcessDates(specialOperationsCertificateLedger);

                // 处理必填字段，确保数据库约束不会失败
                processRequiredFields(specialOperationsCertificateLedger);

                // 导入逻辑：数据库已移除唯一约束，支持完全重复数据导入
                String equipmentName = specialOperationsCertificateLedger.getEquipmentName();
                String internalNumber = specialOperationsCertificateLedger.getInternalNumber();

                if (isUpdateSupport && StringUtils.isNotEmpty(internalNumber)) {
                    // 启用更新支持：检查是否存在相同内部编号的记录进行更新
                    SecuritySpecialOperationsCertificateLedger existingRecord =
                        securitySpecialOperationsCertificateLedgerMapper.selectSecuritySpecialOperationsCertificateLedgerByInternalNumber(internalNumber);

                    if (existingRecord != null) {
                        // 更新现有记录
                        specialOperationsCertificateLedger.setId(existingRecord.getId());
                        this.updateSecuritySpecialOperationsCertificateLedger(specialOperationsCertificateLedger);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、内部编号 " + internalNumber + " 的设备更新成功");
                        log.info("更新设备记录（内部编号: {}）: {}", internalNumber, equipmentName);
                    } else {
                        // 插入新记录
                        this.insertSecuritySpecialOperationsCertificateLedger(specialOperationsCertificateLedger);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、设备 " + equipmentName + " 导入成功");
                        log.info("插入新设备记录: {}", equipmentName);
                    }
                } else {
                    // 不启用更新支持：直接插入新记录，允许完全重复数据
                    this.insertSecuritySpecialOperationsCertificateLedger(specialOperationsCertificateLedger);
                    successNum++;
                    String displayName = StringUtils.isNotEmpty(equipmentName) ? equipmentName : "第" + (successNum + failureNum) + "行";
                    successMsg.append("<br/>" + successNum + "、设备 " + displayName + " 导入成功");
                    log.info("插入设备记录（允许重复）: {}", displayName);
                }
            } catch (Exception e) {
                failureNum++;
                String equipmentName = specialOperationsCertificateLedger.getEquipmentName();
                String msg = "<br/>" + failureNum + "、设备 " + (StringUtils.isNotEmpty(equipmentName) ? equipmentName : "第" + (successNum + failureNum) + "行") + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }

        return successMsg.toString();
    }

    /**
     * 根据关联ID查询特种设备台账列表
     *
     * @param relatedId 关联ID
     * @return 特种设备台账集合
     */
    @Override
    public List<SecuritySpecialOperationsCertificateLedger> selectSecuritySpecialOperationsCertificateLedgerByRelatedId(Long relatedId) {
        return securitySpecialOperationsCertificateLedgerMapper.selectSecuritySpecialOperationsCertificateLedgerByRelatedId(relatedId);
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
            log.warn("更新最近导入特种设备台账关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入特种设备台账关联ID: {}", relatedId);

        try {
            // 直接使用批量更新SQL
            int updatedCount = securitySpecialOperationsCertificateLedgerMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新特种设备台账关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新特种设备台账关联ID失败: {}", e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 处理必填字段，确保数据库约束不会失败
     *
     * @param specialOperationsCertificateLedger 特种设备台账对象
     */
    private void processRequiredFields(SecuritySpecialOperationsCertificateLedger specialOperationsCertificateLedger) {
        // 处理特征设备类别字段，如果为null则设置为空字符串
        if (specialOperationsCertificateLedger.getSpecialEquipememtCategory() == null) {
            specialOperationsCertificateLedger.setSpecialEquipememtCategory("");
            log.debug("设置特征设备类别为空字符串");
        }
    }

    /**
     * 验证和处理日期字段
     * 确保日期字段的有效性，防止无效日期导致数据库错误
     *
     * @param specialOperationsCertificateLedger 特种设备台账对象
     */
    private void validateAndProcessDates(SecuritySpecialOperationsCertificateLedger specialOperationsCertificateLedger) {
        // 处理发证日期
        if (specialOperationsCertificateLedger.getIssuanceDate() != null) {
            Date validDate = validateDate(specialOperationsCertificateLedger.getIssuanceDate(), "发证日期");
            specialOperationsCertificateLedger.setIssuanceDate(validDate);
        }

        // 处理检验日期
        if (specialOperationsCertificateLedger.getInspectionDate() != null) {
            Date validDate = validateDate(specialOperationsCertificateLedger.getInspectionDate(), "检验日期");
            specialOperationsCertificateLedger.setInspectionDate(validDate);
        }

        // 处理下次检验日期
        if (specialOperationsCertificateLedger.getNextInspectionDate() != null) {
            Date validDate = validateDate(specialOperationsCertificateLedger.getNextInspectionDate(), "下次检验日期");
            specialOperationsCertificateLedger.setNextInspectionDate(validDate);
        }
    }

    /**
     * 验证单个日期字段
     *
     * @param date 待验证的日期
     * @param fieldName 字段名称
     * @return 验证后的日期
     */
    private Date validateDate(Date date, String fieldName) {
        if (date == null) {
            return null;
        }

        try {
            // 简单的日期验证：检查年份是否在合理范围内
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(java.util.Calendar.YEAR);

            if (year < 1900 || year > 2100) {
                log.warn("{}字段日期年份超出合理范围: {}, 将设置为null", fieldName, year);
                return null;
            }

            log.debug("{}字段日期验证通过: {}", fieldName, date);
            return date;
        } catch (Exception e) {
            log.warn("{}字段日期验证失败: {}, 将设置为null", fieldName, e.getMessage());
            return null;
        }
    }
}
