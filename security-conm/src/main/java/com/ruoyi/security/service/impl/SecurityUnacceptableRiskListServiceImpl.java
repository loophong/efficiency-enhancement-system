package com.ruoyi.security.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityUnacceptableRiskListMapper;
import com.ruoyi.security.domain.SecurityUnacceptableRiskList;
import com.ruoyi.security.service.ISecurityUnacceptableRiskListService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 不可接受风险清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-26
 */
@Service
public class SecurityUnacceptableRiskListServiceImpl implements ISecurityUnacceptableRiskListService 
{
    private static final Logger log = LoggerFactory.getLogger(SecurityUnacceptableRiskListServiceImpl.class);
    
    @Autowired
    private SecurityUnacceptableRiskListMapper securityUnacceptableRiskListMapper;

    /**
     * 查询不可接受风险清单
     * 
     * @param id 不可接受风险清单主键
     * @return 不可接受风险清单
     */
    @Override
    public SecurityUnacceptableRiskList selectSecurityUnacceptableRiskListById(Long id)
    {
        return securityUnacceptableRiskListMapper.selectSecurityUnacceptableRiskListById(id);
    }

    /**
     * 查询不可接受风险清单列表
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 不可接受风险清单
     */
    @Override
    public List<SecurityUnacceptableRiskList> selectSecurityUnacceptableRiskListList(SecurityUnacceptableRiskList securityUnacceptableRiskList)
    {
        return securityUnacceptableRiskListMapper.selectSecurityUnacceptableRiskListList(securityUnacceptableRiskList);
    }

    /**
     * 新增不可接受风险清单
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 结果
     */
    @Override
    public int insertSecurityUnacceptableRiskList(SecurityUnacceptableRiskList securityUnacceptableRiskList)
    {
        return securityUnacceptableRiskListMapper.insertSecurityUnacceptableRiskList(securityUnacceptableRiskList);
    }

    /**
     * 修改不可接受风险清单
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 结果
     */
    @Override
    public int updateSecurityUnacceptableRiskList(SecurityUnacceptableRiskList securityUnacceptableRiskList)
    {
        return securityUnacceptableRiskListMapper.updateSecurityUnacceptableRiskList(securityUnacceptableRiskList);
    }

    /**
     * 批量删除不可接受风险清单
     * 
     * @param ids 需要删除的不可接受风险清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUnacceptableRiskListByIds(Long[] ids)
    {
        return securityUnacceptableRiskListMapper.deleteSecurityUnacceptableRiskListByIds(ids);
    }

    /**
     * 删除不可接受风险清单信息
     * 
     * @param id 不可接受风险清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUnacceptableRiskListById(Long id)
    {
        return securityUnacceptableRiskListMapper.deleteSecurityUnacceptableRiskListById(id);
    }
    
    /**
     * 导入不可接受风险清单数据
     * 
     * @param file 导入文件
     * @param updateSupport 是否更新已存在数据
     * @return 结果
     */
    @Override
    public String importData(MultipartFile file, boolean updateSupport)
    {
        try {
            // 使用ExcelUtil导入数据
            ExcelUtil<SecurityUnacceptableRiskList> util = new ExcelUtil<>(SecurityUnacceptableRiskList.class);
            List<SecurityUnacceptableRiskList> riskList = util.importExcel(file.getInputStream());
            
            if (StringUtils.isNull(riskList) || riskList.size() == 0) {
                throw new ServiceException("导入数据不能为空！");
            }
            
            List<SecurityUnacceptableRiskList> validList = new ArrayList<>();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            
            // 记录上一行的值，用于处理合并单元格
            String lastHazardSource = null;
            String lastSignificantHazard = null;
            String lastAccidentHealthHazards = null;
            String lastProcessEquipmentPersonnel = null;
            String lastControlMethods = null;
            String lastDepartmentProcess = null;
            
            int rowIndex = 1; // 从第1行开始计数
            for (SecurityUnacceptableRiskList risk : riskList) {
                rowIndex++;
                try {
                    // 检查对象是否为null
                    if (risk == null) {
                        failureNum++;
                        failureMsg.append("<br/>第" + rowIndex + "行：数据解析失败，跳过该行");
                        log.warn("第{}行数据为null，跳过处理", rowIndex);
                        continue;
                    }
                    
                    // 处理危险源点合并单元格
                    if (StringUtils.isEmpty(risk.getHazardSource()) && lastHazardSource != null) {
                        log.info("第{}行危险源点为空，使用上一行值：{}", rowIndex, lastHazardSource);
                        risk.setHazardSource(lastHazardSource);
                    } else if (StringUtils.isNotEmpty(risk.getHazardSource())) {
                        lastHazardSource = risk.getHazardSource();
                    }
                    
                    // 处理重要危险源合并单元格
                    if (StringUtils.isEmpty(risk.getSignificantHazard()) && lastSignificantHazard != null) {
                        log.info("第{}行重要危险源为空，使用上一行值：{}", rowIndex, lastSignificantHazard);
                        risk.setSignificantHazard(lastSignificantHazard);
                    } else if (StringUtils.isNotEmpty(risk.getSignificantHazard())) {
                        lastSignificantHazard = risk.getSignificantHazard();
                    }
                    
                    // 处理导致事故健康危害合并单元格
                    if (StringUtils.isEmpty(risk.getAccidentHealthHazards()) && lastAccidentHealthHazards != null) {
                        log.info("第{}行导致事故健康危害为空，使用上一行值：{}", rowIndex, lastAccidentHealthHazards);
                        risk.setAccidentHealthHazards(lastAccidentHealthHazards);
                    } else if (StringUtils.isNotEmpty(risk.getAccidentHealthHazards())) {
                        lastAccidentHealthHazards = risk.getAccidentHealthHazards();
                    }
                    
                    // 处理工序/设备/人员合并单元格
                    if (StringUtils.isEmpty(risk.getProcessEquipmentPersonnel()) && lastProcessEquipmentPersonnel != null) {
                        log.info("第{}行工序/设备/人员为空，使用上一行值：{}", rowIndex, lastProcessEquipmentPersonnel);
                        risk.setProcessEquipmentPersonnel(lastProcessEquipmentPersonnel);
                    } else if (StringUtils.isNotEmpty(risk.getProcessEquipmentPersonnel())) {
                        lastProcessEquipmentPersonnel = risk.getProcessEquipmentPersonnel();
                    }
                    
                    // 处理控制方法合并单元格
                    if (StringUtils.isEmpty(risk.getControlMethods()) && lastControlMethods != null) {
                        log.info("第{}行控制方法为空，使用上一行值：{}", rowIndex, lastControlMethods);
                        risk.setControlMethods(lastControlMethods);
                    } else if (StringUtils.isNotEmpty(risk.getControlMethods())) {
                        lastControlMethods = risk.getControlMethods();
                    }
                    
                    // 处理部门/工序合并单元格
                    if (StringUtils.isEmpty(risk.getDepartmentProcess()) && lastDepartmentProcess != null) {
                        log.info("第{}行部门/工序为空，使用上一行值：{}", rowIndex, lastDepartmentProcess);
                        risk.setDepartmentProcess(lastDepartmentProcess);
                    } else if (StringUtils.isNotEmpty(risk.getDepartmentProcess())) {
                        lastDepartmentProcess = risk.getDepartmentProcess();
                    }
                    
                    // 数据验证：危险源点、重要危险源和导致事故健康危害是必填项
                    if (StringUtils.isNotEmpty(risk.getHazardSource()) && 
                        StringUtils.isNotEmpty(risk.getSignificantHazard()) &&
                        StringUtils.isNotEmpty(risk.getAccidentHealthHazards())) {
                        
                        validList.add(risk);
                        successNum++;
                        log.info("处理第{}行成功：危险源点={}, 重要危险源={}, 导致事故健康危害={}", 
                                rowIndex,
                                risk.getHazardSource(), 
                                risk.getSignificantHazard(),
                                risk.getAccidentHealthHazards());
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>第" + rowIndex + "行：必填字段为空");
                        log.warn("处理第{}行失败，必填字段为空：{}", rowIndex, risk);
                    }
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>第" + rowIndex + "行导入失败：" + e.getMessage();
                    failureMsg.append(msg);
                    log.error("第{}行导入失败: {}", rowIndex, e.getMessage(), e);
                }
            }
            
            if (validList.size() > 0) {
                try {
                    // 批量插入有效數據
                    securityUnacceptableRiskListMapper.batchInsertRiskList(validList);
                    successMsg.append("成功导入 " + successNum + " 条数据");
                } catch (Exception e) {
                    log.error("批量插入数据失败", e);
                    return "导入失败：" + e.getMessage();
                }
            } else {
                return "没有有效数据可导入";
            }
            
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                return successMsg.toString() + failureMsg.toString();
            } else {
                return successMsg.toString();
            }
        } catch (Exception e) {
            log.error("导入Excel数据失败", e);
            return "导入Excel数据失败：" + e.getMessage();
        }
    }
    
    /**
     * 更新最近导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId)
    {
        return securityUnacceptableRiskListMapper.updateLatestImportedRelatedId(relatedId);
    }
}
