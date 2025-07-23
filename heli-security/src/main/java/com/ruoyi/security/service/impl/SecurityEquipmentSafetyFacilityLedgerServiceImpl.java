package com.ruoyi.security.service.impl;

import java.io.InputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.security.mapper.SecurityEquipmentSafetyFacilityLedgerMapper;
import com.ruoyi.security.domain.SecurityEquipmentSafetyFacilityLedger;
import com.ruoyi.security.service.ISecurityEquipmentSafetyFacilityLedgerService;
import com.ruoyi.security.utils.SafetyFacilityExcelImportUtil;

/**
 * 安全防护设备设施Service业务层处理
 *
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityEquipmentSafetyFacilityLedgerServiceImpl implements ISecurityEquipmentSafetyFacilityLedgerService
{
    private static final Logger log = LoggerFactory.getLogger(SecurityEquipmentSafetyFacilityLedgerServiceImpl.class);

    @Autowired
    private SecurityEquipmentSafetyFacilityLedgerMapper securityEquipmentSafetyFacilityLedgerMapper;

    /**
     * 查询安全防护设备设施
     * 
     * @param id 安全防护设备设施主键
     * @return 安全防护设备设施
     */
    @Override
    public SecurityEquipmentSafetyFacilityLedger selectSecurityEquipmentSafetyFacilityLedgerById(Long id)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.selectSecurityEquipmentSafetyFacilityLedgerById(id);
    }

    /**
     * 查询安全防护设备设施列表
     * 
     * @param securityEquipmentSafetyFacilityLedger 安全防护设备设施
     * @return 安全防护设备设施
     */
    @Override
    public List<SecurityEquipmentSafetyFacilityLedger> selectSecurityEquipmentSafetyFacilityLedgerList(SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.selectSecurityEquipmentSafetyFacilityLedgerList(securityEquipmentSafetyFacilityLedger);
    }

    /**
     * 新增安全防护设备设施
     * 
     * @param securityEquipmentSafetyFacilityLedger 安全防护设备设施
     * @return 结果
     */
    @Override
    public int insertSecurityEquipmentSafetyFacilityLedger(SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.insertSecurityEquipmentSafetyFacilityLedger(securityEquipmentSafetyFacilityLedger);
    }

    /**
     * 修改安全防护设备设施
     * 
     * @param securityEquipmentSafetyFacilityLedger 安全防护设备设施
     * @return 结果
     */
    @Override
    public int updateSecurityEquipmentSafetyFacilityLedger(SecurityEquipmentSafetyFacilityLedger securityEquipmentSafetyFacilityLedger)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.updateSecurityEquipmentSafetyFacilityLedger(securityEquipmentSafetyFacilityLedger);
    }

    /**
     * 批量删除安全防护设备设施
     * 
     * @param ids 需要删除的安全防护设备设施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEquipmentSafetyFacilityLedgerByIds(Long[] ids)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.deleteSecurityEquipmentSafetyFacilityLedgerByIds(ids);
    }

    /**
     * 删除安全防护设备设施信息
     *
     * @param id 安全防护设备设施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEquipmentSafetyFacilityLedgerById(Long id)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.deleteSecurityEquipmentSafetyFacilityLedgerById(id);
    }

    /**
     * 导入安全防护设备设施数据
     *
     * @param inputStream 文件输入流
     * @param updateSupport 是否更新已存在数据
     * @return 导入结果信息
     */
    @Override
    public String importData(InputStream inputStream, boolean updateSupport) throws Exception
    {
        // 使用自定义的Excel导入工具，支持合并单元格处理
        List<SecurityEquipmentSafetyFacilityLedger> dataList = SafetyFacilityExcelImportUtil.importFromExcel(inputStream);
        String operName = "系统";
        if (StringUtils.isNull(dataList) || dataList.size() == 0)
        {
            throw new Exception("导入数据不能为空！");
        }

        log.info("开始处理导入的{}条安全防护设备设施数据", dataList.size());
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SecurityEquipmentSafetyFacilityLedger data : dataList)
        {
            try
            {
                // 验证必填字段
                if (StringUtils.isEmpty(data.getFacilityCategory())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、设施类别不能为空");
                    continue;
                }

                if (StringUtils.isEmpty(data.getSafetyFacilityName())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、安全设施名称不能为空");
                    continue;
                }

                // 直接新增数据，允许重复
                data.setId(null); // 确保ID为空，让数据库自动生成
                data.setCreateBy(operName);
                this.insertSecurityEquipmentSafetyFacilityLedger(data);
                successNum++;
                successMsg.append("<br/>" + successNum + "、设施名称 " + data.getSafetyFacilityName() + " 导入成功");
                log.info("新增安全防护设备设施: {} - {}", data.getFacilityCategory(), data.getSafetyFacilityName());
            }
            catch (Exception e)
            {
                failureNum++;
                String facilityName = data.getSafetyFacilityName() != null ? data.getSafetyFacilityName() : "未知设施";
                String msg = "<br/>" + failureNum + "、设施名称 " + facilityName + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error("导入安全防护设备设施失败: {}", msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new Exception(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
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
            log.warn("更新最近导入安全防护设备设施关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入安全防护设备设施关联ID: {}", relatedId);

        try {
            // 直接使用批量更新SQL
            int updatedCount = securityEquipmentSafetyFacilityLedgerMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新安全防护设备设施关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新安全防护设备设施关联ID失败: {}", e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 根据关联ID查询安全防护设备设施列表
     *
     * @param relatedId 关联ID
     * @return 安全防护设备设施集合
     */
    @Override
    public List<SecurityEquipmentSafetyFacilityLedger> selectSecurityEquipmentSafetyFacilityLedgerByRelatedId(Long relatedId)
    {
        return securityEquipmentSafetyFacilityLedgerMapper.selectSecurityEquipmentSafetyFacilityLedgerByRelatedId(relatedId);
    }
}
