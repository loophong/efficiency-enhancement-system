package com.ruoyi.security.service.impl;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityHazardPointLedgerMapper;
import com.ruoyi.security.domain.SecurityHazardPointLedger;
import com.ruoyi.security.service.ISecurityHazardPointLedgerService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.exception.ServiceException;

@Slf4j
/**
 * 有点害台账Service业务层处理
 * 
 * @author wang
 * @date 2025-03-05
 */
@Service
public class SecurityHazardPointLedgerServiceImpl implements ISecurityHazardPointLedgerService 
{
    @Autowired
    private SecurityHazardPointLedgerMapper securityHazardPointLedgerMapper;

    /**
     * 查询有点害台账
     * 
     * @param id 有点害台账主键
     * @return 有点害台账
     */
    @Override
    public SecurityHazardPointLedger selectSecurityHazardPointLedgerById(Long id)
    {
        return securityHazardPointLedgerMapper.selectSecurityHazardPointLedgerById(id);
    }

    /**
     * 查询有点害台账列表
     * 
     * @param securityHazardPointLedger 有点害台账
     * @return 有点害台账
     */
    @Override
    public List<SecurityHazardPointLedger> selectSecurityHazardPointLedgerList(SecurityHazardPointLedger securityHazardPointLedger)
    {
        return securityHazardPointLedgerMapper.selectSecurityHazardPointLedgerList(securityHazardPointLedger);
    }

    /**
     * 新增有点害台账
     * 
     * @param securityHazardPointLedger 有点害台账
     * @return 结果
     */
    @Override
    public int insertSecurityHazardPointLedger(SecurityHazardPointLedger securityHazardPointLedger)
    {
        return securityHazardPointLedgerMapper.insertSecurityHazardPointLedger(securityHazardPointLedger);
    }

    /**
     * 修改有点害台账
     * 
     * @param securityHazardPointLedger 有点害台账
     * @return 结果
     */
    @Override
    public int updateSecurityHazardPointLedger(SecurityHazardPointLedger securityHazardPointLedger)
    {
        return securityHazardPointLedgerMapper.updateSecurityHazardPointLedger(securityHazardPointLedger);
    }

    /**
     * 批量删除有点害台账
     * 
     * @param ids 需要删除的有点害台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardPointLedgerByIds(Long[] ids)
    {
        return securityHazardPointLedgerMapper.deleteSecurityHazardPointLedgerByIds(ids);
    }

    /**
     * 删除有点害台账信息
     *
     * @param id 有点害台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardPointLedgerById(Long id)
    {
        return securityHazardPointLedgerMapper.deleteSecurityHazardPointLedgerById(id);
    }

    /**
     * 根据关联ID查询有点害台账列表
     *
     * @param relatedId 关联ID
     * @return 有点害台账集合
     */
    @Override
    public List<SecurityHazardPointLedger> selectByRelatedId(Long relatedId)
    {
        return securityHazardPointLedgerMapper.selectByRelatedId(relatedId);
    }

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId)
    {
        return securityHazardPointLedgerMapper.updateLatestImportedRelatedId(relatedId);
    }

    /**
     * 导入有点害台账数据
     *
     * @param hazardPointLedgerList 有点害台账数据列表
     * @param isUpdateSupport 是否更新支持
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importHazardPointLedgerList(List<SecurityHazardPointLedger> hazardPointLedgerList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(hazardPointLedgerList) || hazardPointLedgerList.size() == 0)
        {
            throw new ServiceException("导入有点害台账数据不能为空！请检查Excel文件格式是否正确，确保表头与模板一致，并且至少包含一行数据。");
        }



        // 处理合并单元格数据
        log.info("开始处理合并单元格数据，原始数据条数: {}", hazardPointLedgerList.size());
        processMergedCellData(hazardPointLedgerList);
        log.info("合并单元格数据处理完成");

        // 输出处理后的前几条数据用于验证
        for (int i = 0; i < Math.min(5, hazardPointLedgerList.size()); i++) {
            SecurityHazardPointLedger item = hazardPointLedgerList.get(i);
            log.info("第{}行数据: 评价单元={}, 岗位={}, 检测地点={}",
                i + 1, item.getEvaluationUnit(), item.getPosition(), item.getInspectionLocation());
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (SecurityHazardPointLedger hazardPointLedger : hazardPointLedgerList)
        {
            try
            {
                // 跳过评价单元为空的行（可能是合并单元格的空行）
                if (StringUtils.isEmpty(hazardPointLedger.getEvaluationUnit()))
                {
                    continue;
                }

                // 直接插入数据，不检查重复
                this.insertSecurityHazardPointLedger(hazardPointLedger);
                successNum++;
                successMsg.append("<br/>" + successNum + "、评价单元 " + hazardPointLedger.getEvaluationUnit() + "（" + hazardPointLedger.getPosition() + "）导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、评价单元 " + (hazardPointLedger.getEvaluationUnit() != null ? hazardPointLedger.getEvaluationUnit() : "未知") + " 导入失败：";
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
     * 处理合并单元格数据
     * 对于合并单元格，空值会继承上一行的值
     * 处理层级关系：评价单元 > 岗位 > 检测地点
     */
    private void processMergedCellData(List<SecurityHazardPointLedger> hazardPointLedgerList) {
        if (hazardPointLedgerList == null || hazardPointLedgerList.isEmpty()) {
            return;
        }

        String currentEvaluationUnit = "";
        String currentPosition = "";
        String currentInspectionLocation = "";

        log.info("开始处理合并单元格数据，总行数: {}", hazardPointLedgerList.size());

        for (int i = 0; i < hazardPointLedgerList.size(); i++) {
            SecurityHazardPointLedger hazardPointLedger = hazardPointLedgerList.get(i);

            // 记录处理前的状态
            String beforeEvaluationUnit = hazardPointLedger.getEvaluationUnit();
            String beforePosition = hazardPointLedger.getPosition();
            String beforeInspectionLocation = hazardPointLedger.getInspectionLocation();

            // 处理评价单元（序号）合并单元格
            if (isEmptyOrMergedCell(hazardPointLedger.getEvaluationUnit())) {
                // 检测到合并单元格或空值，从上一行获取数据
                if (StringUtils.isNotEmpty(currentEvaluationUnit)) {
                    hazardPointLedger.setEvaluationUnit(currentEvaluationUnit);
                    log.debug("第{}行评价单元为空，从上一行继承: {}", i + 1, currentEvaluationUnit);
                }
            } else {
                // 当前行有新的评价单元值
                currentEvaluationUnit = hazardPointLedger.getEvaluationUnit().trim();
                // 当评价单元改变时，重置下级字段
                currentPosition = "";
                currentInspectionLocation = "";
                log.debug("第{}行评价单元更新: {}", i + 1, currentEvaluationUnit);
            }

            // 处理岗位合并单元格
            if (isEmptyOrMergedCell(hazardPointLedger.getPosition())) {
                // 检测到合并单元格或空值，从上一行获取数据
                if (StringUtils.isNotEmpty(currentPosition)) {
                    hazardPointLedger.setPosition(currentPosition);
                    log.debug("第{}行岗位为空，从上一行继承: {}", i + 1, currentPosition);
                }
            } else {
                // 当前行有新的岗位值
                currentPosition = hazardPointLedger.getPosition().trim();
                // 当岗位改变时，重置检测地点
                currentInspectionLocation = "";
                log.debug("第{}行岗位更新: {}", i + 1, currentPosition);
            }

            // 处理检测地点合并单元格
            if (isEmptyOrMergedCell(hazardPointLedger.getInspectionLocation())) {
                // 检测到合并单元格或空值，从上一行获取数据
                if (StringUtils.isNotEmpty(currentInspectionLocation)) {
                    hazardPointLedger.setInspectionLocation(currentInspectionLocation);
                    log.debug("第{}行检测地点为空，从上一行继承: {}", i + 1, currentInspectionLocation);
                }
            } else {
                // 当前行有新的检测地点值
                currentInspectionLocation = hazardPointLedger.getInspectionLocation().trim();
                log.debug("第{}行检测地点更新: {}", i + 1, currentInspectionLocation);
            }

            // 记录处理结果
            if (!StringUtils.equals(beforeEvaluationUnit, hazardPointLedger.getEvaluationUnit()) ||
                !StringUtils.equals(beforePosition, hazardPointLedger.getPosition()) ||
                !StringUtils.equals(beforeInspectionLocation, hazardPointLedger.getInspectionLocation())) {
                log.debug("第{}行数据处理: 评价单元[{}->{}], 岗位[{}->{}], 检测地点[{}->{}]",
                    i + 1,
                    beforeEvaluationUnit, hazardPointLedger.getEvaluationUnit(),
                    beforePosition, hazardPointLedger.getPosition(),
                    beforeInspectionLocation, hazardPointLedger.getInspectionLocation());
            }
        }

        log.info("合并单元格数据处理完成");
    }

    /**
     * 检测是否为空值或合并单元格
     * 包括：null、空字符串、纯空格、Excel合并单元格的空值
     */
    private boolean isEmptyOrMergedCell(String value) {
        if (value == null) {
            return true;
        }

        String trimmedValue = value.trim();

        // 检查是否为空字符串
        if (trimmedValue.isEmpty()) {
            return true;
        }

        // 检查是否为Excel导入时的特殊空值标识
        // 有些Excel导入工具会将合并单元格的空值表示为特定字符
        if ("null".equalsIgnoreCase(trimmedValue) ||
            "NULL".equals(trimmedValue) ||
            "-".equals(trimmedValue) ||
            "N/A".equalsIgnoreCase(trimmedValue) ||
            "".equals(trimmedValue)) {
            return true;
        }

        return false;
    }
}
