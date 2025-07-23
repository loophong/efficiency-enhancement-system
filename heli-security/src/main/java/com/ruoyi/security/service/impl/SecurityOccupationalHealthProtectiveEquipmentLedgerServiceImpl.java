package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityOccupationalHealthProtectiveEquipmentLedgerMapper;
import com.ruoyi.security.domain.SecurityOccupationalHealthProtectiveEquipmentLedger;
import com.ruoyi.security.service.ISecurityOccupationalHealthProtectiveEquipmentLedgerService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 职业健康防护用品台帐Service业务层处理
 * 
 * @author wang
 * @date 2025-03-06
 */
@Service
public class SecurityOccupationalHealthProtectiveEquipmentLedgerServiceImpl implements ISecurityOccupationalHealthProtectiveEquipmentLedgerService
{
    private static final Logger log = LoggerFactory.getLogger(SecurityOccupationalHealthProtectiveEquipmentLedgerServiceImpl.class);

    @Autowired
    private SecurityOccupationalHealthProtectiveEquipmentLedgerMapper securityOccupationalHealthProtectiveEquipmentLedgerMapper;

    /**
     * 查询职业健康防护用品台帐
     * 
     * @param id 职业健康防护用品台帐主键
     * @return 职业健康防护用品台帐
     */
    @Override
    public SecurityOccupationalHealthProtectiveEquipmentLedger selectSecurityOccupationalHealthProtectiveEquipmentLedgerById(Long id)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.selectSecurityOccupationalHealthProtectiveEquipmentLedgerById(id);
    }

    /**
     * 查询职业健康防护用品台帐列表
     * 
     * @param securityOccupationalHealthProtectiveEquipmentLedger 职业健康防护用品台帐
     * @return 职业健康防护用品台帐
     */
    @Override
    public List<SecurityOccupationalHealthProtectiveEquipmentLedger> selectSecurityOccupationalHealthProtectiveEquipmentLedgerList(SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.selectSecurityOccupationalHealthProtectiveEquipmentLedgerList(securityOccupationalHealthProtectiveEquipmentLedger);
    }

    /**
     * 新增职业健康防护用品台帐
     * 
     * @param securityOccupationalHealthProtectiveEquipmentLedger 职业健康防护用品台帐
     * @return 结果
     */
    @Override
    public int insertSecurityOccupationalHealthProtectiveEquipmentLedger(SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.insertSecurityOccupationalHealthProtectiveEquipmentLedger(securityOccupationalHealthProtectiveEquipmentLedger);
    }

    /**
     * 修改职业健康防护用品台帐
     * 
     * @param securityOccupationalHealthProtectiveEquipmentLedger 职业健康防护用品台帐
     * @return 结果
     */
    @Override
    public int updateSecurityOccupationalHealthProtectiveEquipmentLedger(SecurityOccupationalHealthProtectiveEquipmentLedger securityOccupationalHealthProtectiveEquipmentLedger)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.updateSecurityOccupationalHealthProtectiveEquipmentLedger(securityOccupationalHealthProtectiveEquipmentLedger);
    }

    /**
     * 批量删除职业健康防护用品台帐
     * 
     * @param ids 需要删除的职业健康防护用品台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHealthProtectiveEquipmentLedgerByIds(Long[] ids)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.deleteSecurityOccupationalHealthProtectiveEquipmentLedgerByIds(ids);
    }

    /**
     * 删除职业健康防护用品台帐信息
     * 
     * @param id 职业健康防护用品台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHealthProtectiveEquipmentLedgerById(Long id)
    {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.deleteSecurityOccupationalHealthProtectiveEquipmentLedgerById(id);
    }

    /**
     * 导入职业健康防护用品台帐数据
     *
     * @param ledgerList 职业健康防护用品台帐数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importSecurityOccupationalHealthProtectiveEquipmentLedger(List<SecurityOccupationalHealthProtectiveEquipmentLedger> ledgerList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(ledgerList) || ledgerList.size() == 0) {
            throw new ServiceException("导入职业健康防护用品台帐数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (SecurityOccupationalHealthProtectiveEquipmentLedger ledger : ledgerList) {
            try {
                // 清空id字段，确保使用数据库自动生成的id
                ledger.setId(null);

                // 验证必填字段
                if (StringUtils.isEmpty(ledger.getPanjinHeliSapMaterialNumber())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、SAP物料号不能为空");
                    continue;
                }

                if (StringUtils.isEmpty(ledger.getMaterialName())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、物料名称不能为空");
                    continue;
                }

                // 检查是否存在相同的SAP物料号
                SecurityOccupationalHealthProtectiveEquipmentLedger existingLedger = findBySapMaterialNumber(ledger.getPanjinHeliSapMaterialNumber());

                if (existingLedger != null && isUpdateSupport) {
                    // 更新现有记录
                    ledger.setId(existingLedger.getId());
                    this.updateSecurityOccupationalHealthProtectiveEquipmentLedger(ledger);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、SAP物料号 " + ledger.getPanjinHeliSapMaterialNumber() + " 更新成功");
                } else if (existingLedger == null) {
                    // 插入新记录
                    this.insertSecurityOccupationalHealthProtectiveEquipmentLedger(ledger);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、SAP物料号 " + ledger.getPanjinHeliSapMaterialNumber() + " 导入成功");
                } else {
                    // 存在但不更新，跳过
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、SAP物料号 " + ledger.getPanjinHeliSapMaterialNumber() + " 已存在，跳过导入");
                }
            } catch (Exception e) {
                failureNum++;
                String sapNumber = ledger.getPanjinHeliSapMaterialNumber();
                String msg = "<br/>" + failureNum + "、SAP物料号 " + (StringUtils.isNotEmpty(sapNumber) ? sapNumber : "第" + (successNum + failureNum) + "行") + " 导入失败：";
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
     * 根据SAP物料号查找记录
     */
    private SecurityOccupationalHealthProtectiveEquipmentLedger findBySapMaterialNumber(String sapMaterialNumber) {
        SecurityOccupationalHealthProtectiveEquipmentLedger query = new SecurityOccupationalHealthProtectiveEquipmentLedger();
        query.setPanjinHeliSapMaterialNumber(sapMaterialNumber);
        List<SecurityOccupationalHealthProtectiveEquipmentLedger> list = securityOccupationalHealthProtectiveEquipmentLedgerMapper.selectSecurityOccupationalHealthProtectiveEquipmentLedgerList(query);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 根据关联ID查询职业健康防护用品台帐列表
     *
     * @param relatedId 关联ID
     * @return 职业健康防护用品台帐集合
     */
    @Override
    public List<SecurityOccupationalHealthProtectiveEquipmentLedger> selectSecurityOccupationalHealthProtectiveEquipmentLedgerByRelatedId(Long relatedId) {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.selectSecurityOccupationalHealthProtectiveEquipmentLedgerByRelatedId(relatedId);
    }

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        return securityOccupationalHealthProtectiveEquipmentLedgerMapper.updateLatestImportedRelatedId(relatedId);
    }
}
