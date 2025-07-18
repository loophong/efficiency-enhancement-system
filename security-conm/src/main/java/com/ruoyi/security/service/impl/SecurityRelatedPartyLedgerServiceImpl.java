package com.ruoyi.security.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRelatedPartyLedgerMapper;
import com.ruoyi.security.domain.SecurityRelatedPartyLedger;
import com.ruoyi.security.service.ISecurityRelatedPartyLedgerService;
import com.ruoyi.security.utils.ExcelDateUtils;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 相关方管理台账Service业务层处理
 * 
 * @author wang
 * @date 2025-04-09
 */
@Service
public class SecurityRelatedPartyLedgerServiceImpl implements ISecurityRelatedPartyLedgerService
{
    private static final Logger log = LoggerFactory.getLogger(SecurityRelatedPartyLedgerServiceImpl.class);

    @Autowired
    private SecurityRelatedPartyLedgerMapper securityRelatedPartyLedgerMapper;

    /**
     * 查询相关方管理台账
     * 
     * @param id 相关方管理台账主键
     * @return 相关方管理台账
     */
    @Override
    public SecurityRelatedPartyLedger selectSecurityRelatedPartyLedgerById(Long id)
    {
        return securityRelatedPartyLedgerMapper.selectSecurityRelatedPartyLedgerById(id);
    }

    /**
     * 查询相关方管理台账列表
     * 
     * @param securityRelatedPartyLedger 相关方管理台账
     * @return 相关方管理台账
     */
    @Override
    public List<SecurityRelatedPartyLedger> selectSecurityRelatedPartyLedgerList(SecurityRelatedPartyLedger securityRelatedPartyLedger)
    {
        return securityRelatedPartyLedgerMapper.selectSecurityRelatedPartyLedgerList(securityRelatedPartyLedger);
    }

    /**
     * 新增相关方管理台账
     * 
     * @param securityRelatedPartyLedger 相关方管理台账
     * @return 结果
     */
    @Override
    public int insertSecurityRelatedPartyLedger(SecurityRelatedPartyLedger securityRelatedPartyLedger)
    {
        return securityRelatedPartyLedgerMapper.insertSecurityRelatedPartyLedger(securityRelatedPartyLedger);
    }

    /**
     * 修改相关方管理台账
     * 
     * @param securityRelatedPartyLedger 相关方管理台账
     * @return 结果
     */
    @Override
    public int updateSecurityRelatedPartyLedger(SecurityRelatedPartyLedger securityRelatedPartyLedger)
    {
        return securityRelatedPartyLedgerMapper.updateSecurityRelatedPartyLedger(securityRelatedPartyLedger);
    }

    /**
     * 批量删除相关方管理台账
     * 
     * @param ids 需要删除的相关方管理台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelatedPartyLedgerByIds(Long[] ids)
    {
        return securityRelatedPartyLedgerMapper.deleteSecurityRelatedPartyLedgerByIds(ids);
    }

    /**
     * 删除相关方管理台账信息
     *
     * @param id 相关方管理台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelatedPartyLedgerById(Long id)
    {
        return securityRelatedPartyLedgerMapper.deleteSecurityRelatedPartyLedgerById(id);
    }

    /**
     * 导入相关方管理台账数据
     *
     * @param relatedPartyLedgerList 相关方管理台账数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importRelatedPartyLedger(List<SecurityRelatedPartyLedger> relatedPartyLedgerList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(relatedPartyLedgerList) || relatedPartyLedgerList.size() == 0)
        {
            throw new ServiceException("导入相关方管理台账数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SecurityRelatedPartyLedger relatedPartyLedger : relatedPartyLedgerList)
        {
            try
            {
                // 跳过空记录
                if (relatedPartyLedger == null) {
                    continue;
                }

                // 获取相关方名称，如果为空则使用默认值
                String relatedPartyName = StringUtils.isNotEmpty(relatedPartyLedger.getRelatedPartyName()) ?
                    relatedPartyLedger.getRelatedPartyName() : "未知相关方_" + System.currentTimeMillis();

                // 如果相关方名称为空，设置默认值
                if (StringUtils.isEmpty(relatedPartyLedger.getRelatedPartyName())) {
                    relatedPartyLedger.setRelatedPartyName(relatedPartyName);
                }

                // 清空id字段，确保使用数据库自动生成的id
                relatedPartyLedger.setId(null);

                // 验证和处理日期字段
                validateAndProcessDates(relatedPartyLedger);

                // 处理是否签订安全管理协议字段
                processAgreementSignedField(relatedPartyLedger);

                // 处理所有布尔值字段
                processBooleanFields(relatedPartyLedger);

                // 直接导入，不进行重复检查
                // 如果启用更新支持，则尝试根据相关方名称查找并更新；否则直接插入新记录
                if (isUpdateSupport && StringUtils.isNotEmpty(relatedPartyName) && !relatedPartyName.startsWith("未知相关方_")) {
                    // 只有在启用更新支持且相关方名称有效时才检查重复
                    SecurityRelatedPartyLedger u = securityRelatedPartyLedgerMapper.selectSecurityRelatedPartyLedgerByName(relatedPartyName);
                    if (StringUtils.isNotNull(u)) {
                        // 找到重复记录，执行更新
                        relatedPartyLedger.setUpdateBy(operName);
                        relatedPartyLedger.setId(u.getId());
                        this.updateSecurityRelatedPartyLedger(relatedPartyLedger);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、相关方 " + relatedPartyName + " 更新成功");
                    } else {
                        // 没有找到重复记录，插入新记录
                        relatedPartyLedger.setCreateBy(operName);
                        this.insertSecurityRelatedPartyLedger(relatedPartyLedger);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、相关方 " + relatedPartyName + " 导入成功");
                    }
                } else {
                    // 不启用更新支持或相关方名称无效，直接插入新记录（允许重复）
                    relatedPartyLedger.setCreateBy(operName);
                    this.insertSecurityRelatedPartyLedger(relatedPartyLedger);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、相关方 " + relatedPartyName + " 导入成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String relatedPartyName = (relatedPartyLedger != null && StringUtils.isNotEmpty(relatedPartyLedger.getRelatedPartyName()))
                    ? relatedPartyLedger.getRelatedPartyName() : "未知记录";
                String msg = "<br/>" + failureNum + "、相关方 " + relatedPartyName + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 根据关联ID查询相关方管理台账列表
     *
     * @param relatedId 关联ID
     * @return 相关方管理台账集合
     */
    @Override
    public List<SecurityRelatedPartyLedger> selectSecurityRelatedPartyLedgerByRelatedId(Long relatedId)
    {
        return securityRelatedPartyLedgerMapper.selectSecurityRelatedPartyLedgerByRelatedId(relatedId);
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
            log.warn("更新最近导入相关方管理台账关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入相关方管理台账关联ID: {}", relatedId);

        try {
            // 直接使用批量更新SQL
            int updatedCount = securityRelatedPartyLedgerMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新相关方管理台账关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新相关方管理台账关联ID失败: {}", e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 处理是否签订安全管理协议字段
     * 确保该字段的值正确转换为数字格式
     *
     * @param relatedPartyLedger 相关方管理台账对象
     */
    private void processAgreementSignedField(SecurityRelatedPartyLedger relatedPartyLedger) {
        String value = relatedPartyLedger.getIsSafetyManagementAgreementSigned();
        log.info("是否签订安全管理协议字段原始值: [{}]", value);

        // 如果字段为null或空字符串，设置为默认值"0"
        if (value == null || value.trim().isEmpty()) {
            relatedPartyLedger.setIsSafetyManagementAgreementSigned("0");
            log.warn("是否签订安全管理协议字段值为空，设置为默认值0");
            return;
        }

        // 去除前后空格
        value = value.trim();
        log.info("去除空格后的字段值: [{}]", value);

        // 手动处理转换逻辑，因为@Excel的readConverterExp可能没有正常工作
        String convertedValue;
        switch (value) {
            case "是":
            case "√":
            case "1":
                convertedValue = "1";
                break;
            case "否":
            case "0":
            case "":
                convertedValue = "0";
                break;
            default:
                log.warn("未知的是否签订安全管理协议字段值: [{}]，设置为默认值0", value);
                convertedValue = "0";
                break;
        }

        relatedPartyLedger.setIsSafetyManagementAgreementSigned(convertedValue);
        log.info("是否签订安全管理协议字段转换: [{}] -> [{}]", value, convertedValue);
    }

    /**
     * 处理所有布尔值字段
     * 将"√"转换为"1"，空值转换为"0"
     *
     * @param relatedPartyLedger 相关方管理台账对象
     */
    private void processBooleanFields(SecurityRelatedPartyLedger relatedPartyLedger) {
        // 处理外来参观
        relatedPartyLedger.setWaiLai(convertBooleanField(relatedPartyLedger.getWaiLai(), "外来参观"));

        // 处理实习生
        relatedPartyLedger.setShiXi(convertBooleanField(relatedPartyLedger.getShiXi(), "实习生"));

        // 处理劳务派遣、外包等
        relatedPartyLedger.setLaoWu(convertBooleanField(relatedPartyLedger.getLaoWu(), "劳务派遣、外包等"));

        // 处理施工作业类
        relatedPartyLedger.setShiGong(convertBooleanField(relatedPartyLedger.getShiGong(), "施工作业类"));

        // 处理清洁检测服务
        relatedPartyLedger.setQinjieJianceFuwu(convertBooleanField(relatedPartyLedger.getQinjieJianceFuwu(), "清运、检测、服务等"));

        // 处理物流配送
        relatedPartyLedger.setWuLiu(convertBooleanField(relatedPartyLedger.getWuLiu(), "物流配送"));

        // 处理驻厂相关方
        relatedPartyLedger.setZhuCangXiangguanfang(convertBooleanField(relatedPartyLedger.getZhuCangXiangguanfang(), "驻厂相关方"));

        // 处理其他
        relatedPartyLedger.setOther(convertBooleanField(relatedPartyLedger.getOther(), "其他"));
    }

    /**
     * 转换布尔值字段
     *
     * @param value 原始值
     * @param fieldName 字段名称（用于日志）
     * @return 转换后的值："1"或"0"
     */
    private String convertBooleanField(String value, String fieldName) {
        log.debug("{}字段原始值: [{}]", fieldName, value);

        if (value == null || value.trim().isEmpty()) {
            log.debug("{}字段为空，设置为0", fieldName);
            return "0";
        }

        value = value.trim();
        String convertedValue;

        switch (value) {
            case "√":
            case "是":
            case "1":
                convertedValue = "1";
                break;
            case "否":
            case "0":
            case "":
                convertedValue = "0";
                break;
            default:
                log.warn("{}字段未知值: [{}]，设置为默认值0", fieldName, value);
                convertedValue = "0";
                break;
        }

        log.debug("{}字段转换: [{}] -> [{}]", fieldName, value, convertedValue);
        return convertedValue;
    }

    /**
     * 验证和处理日期字段
     * 确保日期字段的有效性，防止无效日期导致数据库错误
     *
     * @param relatedPartyLedger 相关方管理台账对象
     */
    private void validateAndProcessDates(SecurityRelatedPartyLedger relatedPartyLedger) {
        // 处理签订时间
        if (relatedPartyLedger.getAgreementSigningDate() != null) {
            Date validDate = validateDate(relatedPartyLedger.getAgreementSigningDate(), "签订时间");
            relatedPartyLedger.setAgreementSigningDate(validDate);
        }

        // 处理进厂时间
        if (relatedPartyLedger.getFactoryEntryStartDate() != null) {
            Date validDate = validateDate(relatedPartyLedger.getFactoryEntryStartDate(), "进厂时间");
            relatedPartyLedger.setFactoryEntryStartDate(validDate);
        }
    }

    /**
     * 验证单个日期字段
     *
     * @param date 待验证的日期
     * @param fieldName 字段名称（用于日志）
     * @return 验证后的日期，无效时返回null
     */
    private Date validateDate(Date date, String fieldName) {
        if (date == null) {
            return null;
        }

        try {
            // 检查年份是否在合理范围内
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(java.util.Calendar.YEAR);

            if (year < 1900 || year > 2100) {
                log.warn("{}字段年份超出合理范围: {}, 将设置为null", fieldName, year);
                return null;
            }

            return date;
        } catch (Exception e) {
            log.error("验证{}字段时发生错误: {}, 将设置为null", fieldName, e.getMessage());
            return null;
        }
    }
}
