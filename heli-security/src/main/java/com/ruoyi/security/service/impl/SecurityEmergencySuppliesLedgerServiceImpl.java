package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEmergencySuppliesLedgerMapper;
import com.ruoyi.security.domain.SecurityEmergencySuppliesLedger;
import com.ruoyi.security.service.ISecurityEmergencySuppliesLedgerService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 应急物资管理台帐Service业务层处理
 * 
 * @author wang
 * @date 2025-03-06
 */
@Service
public class SecurityEmergencySuppliesLedgerServiceImpl implements ISecurityEmergencySuppliesLedgerService
{
    private static final Logger log = LoggerFactory.getLogger(SecurityEmergencySuppliesLedgerServiceImpl.class);

    @Autowired
    private SecurityEmergencySuppliesLedgerMapper securityEmergencySuppliesLedgerMapper;

    /**
     * 查询应急物资管理台帐
     * 
     * @param id 应急物资管理台帐主键
     * @return 应急物资管理台帐
     */
    @Override
    public SecurityEmergencySuppliesLedger selectSecurityEmergencySuppliesLedgerById(Long id)
    {
        return securityEmergencySuppliesLedgerMapper.selectSecurityEmergencySuppliesLedgerById(id);
    }

    /**
     * 查询应急物资管理台帐列表
     * 
     * @param securityEmergencySuppliesLedger 应急物资管理台帐
     * @return 应急物资管理台帐
     */
    @Override
    public List<SecurityEmergencySuppliesLedger> selectSecurityEmergencySuppliesLedgerList(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        return securityEmergencySuppliesLedgerMapper.selectSecurityEmergencySuppliesLedgerList(securityEmergencySuppliesLedger);
    }

    /**
     * 新增应急物资管理台帐
     * 
     * @param securityEmergencySuppliesLedger 应急物资管理台帐
     * @return 结果
     */
    @Override
    public int insertSecurityEmergencySuppliesLedger(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        return securityEmergencySuppliesLedgerMapper.insertSecurityEmergencySuppliesLedger(securityEmergencySuppliesLedger);
    }

    /**
     * 修改应急物资管理台帐
     * 
     * @param securityEmergencySuppliesLedger 应急物资管理台帐
     * @return 结果
     */
    @Override
    public int updateSecurityEmergencySuppliesLedger(SecurityEmergencySuppliesLedger securityEmergencySuppliesLedger)
    {
        return securityEmergencySuppliesLedgerMapper.updateSecurityEmergencySuppliesLedger(securityEmergencySuppliesLedger);
    }

    /**
     * 批量删除应急物资管理台帐
     * 
     * @param ids 需要删除的应急物资管理台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencySuppliesLedgerByIds(Long[] ids)
    {
        return securityEmergencySuppliesLedgerMapper.deleteSecurityEmergencySuppliesLedgerByIds(ids);
    }

    /**
     * 删除应急物资管理台帐信息
     * 
     * @param id 应急物资管理台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEmergencySuppliesLedgerById(Long id)
    {
        return securityEmergencySuppliesLedgerMapper.deleteSecurityEmergencySuppliesLedgerById(id);
    }

    /**
     * 导入应急物资管理台帐数据
     *
     * @param suppliesLedgerList 应急物资管理台帐数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importSecurityEmergencySuppliesLedger(List<SecurityEmergencySuppliesLedger> suppliesLedgerList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(suppliesLedgerList) || suppliesLedgerList.size() == 0) {
            throw new ServiceException("导入应急物资管理台帐数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (SecurityEmergencySuppliesLedger suppliesLedger : suppliesLedgerList) {
            try {
                // 清空id字段，确保使用数据库自动生成的id
                suppliesLedger.setId(null);

                // 验证必填字段
                if (StringUtils.isEmpty(suppliesLedger.getSupplyName())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、物资名称不能为空");
                    continue;
                }

                // 检查是否存在相同的记录（基于物资名称、应急物资类型）
                SecurityEmergencySuppliesLedger existingLedger = findByUniqueFields(suppliesLedger.getSupplyName(), suppliesLedger.getEmergencySupplyType());

                if (existingLedger != null && isUpdateSupport) {
                    // 更新现有记录
                    suppliesLedger.setId(existingLedger.getId());
                    this.updateSecurityEmergencySuppliesLedger(suppliesLedger);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、物资名称 " + suppliesLedger.getSupplyName() + " 更新成功");
                } else if (existingLedger == null) {
                    // 插入新记录
                    this.insertSecurityEmergencySuppliesLedger(suppliesLedger);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、物资名称 " + suppliesLedger.getSupplyName() + " 导入成功");
                } else {
                    // 存在但不更新，跳过
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、物资名称 " + suppliesLedger.getSupplyName() + " 已存在，跳过导入");
                }
            } catch (Exception e) {
                failureNum++;
                String supplyName = suppliesLedger.getSupplyName();
                String identifier = StringUtils.isNotEmpty(supplyName) ? supplyName : "第" + (successNum + failureNum) + "行";
                String msg = "<br/>" + failureNum + "、" + identifier + " 导入失败：";
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
     * 根据唯一字段查找记录
     */
    private SecurityEmergencySuppliesLedger findByUniqueFields(String supplyName, String emergencySupplyType) {
        SecurityEmergencySuppliesLedger query = new SecurityEmergencySuppliesLedger();
        query.setSupplyName(supplyName);
        if (StringUtils.isNotEmpty(emergencySupplyType)) {
            query.setEmergencySupplyType(emergencySupplyType);
        }
        List<SecurityEmergencySuppliesLedger> list = securityEmergencySuppliesLedgerMapper.selectSecurityEmergencySuppliesLedgerList(query);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 根据关联ID查询应急物资管理台帐列表
     *
     * @param relatedId 关联ID
     * @return 应急物资管理台帐集合
     */
    @Override
    public List<SecurityEmergencySuppliesLedger> selectSecurityEmergencySuppliesLedgerByRelatedId(Long relatedId) {
        return securityEmergencySuppliesLedgerMapper.selectSecurityEmergencySuppliesLedgerByRelatedId(relatedId);
    }

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        return securityEmergencySuppliesLedgerMapper.updateLatestImportedRelatedId(relatedId);
    }
}
