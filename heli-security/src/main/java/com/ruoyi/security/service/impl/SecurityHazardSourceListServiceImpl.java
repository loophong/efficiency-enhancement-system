package com.ruoyi.security.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityHazardSourceListMapper;
import com.ruoyi.security.domain.SecurityHazardSourceList;
import com.ruoyi.security.service.ISecurityHazardSourceListService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 危险源清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-26
 */
@Service
public class SecurityHazardSourceListServiceImpl implements ISecurityHazardSourceListService 
{
    private static final Logger log = LoggerFactory.getLogger(SecurityHazardSourceListServiceImpl.class);
    
    @Autowired
    private SecurityHazardSourceListMapper securityHazardSourceListMapper;

    /**
     * 查询危险源清单
     * 
     * @param id 危险源清单主键
     * @return 危险源清单
     */
    @Override
    public SecurityHazardSourceList selectSecurityHazardSourceListById(Long id)
    {
        return securityHazardSourceListMapper.selectSecurityHazardSourceListById(id);
    }

    /**
     * 查询危险源清单列表
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 危险源清单
     */
    @Override
    public List<SecurityHazardSourceList> selectSecurityHazardSourceListList(SecurityHazardSourceList securityHazardSourceList)
    {
        return securityHazardSourceListMapper.selectSecurityHazardSourceListList(securityHazardSourceList);
    }

    /**
     * 新增危险源清单
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 结果
     */
    @Override
    public int insertSecurityHazardSourceList(SecurityHazardSourceList securityHazardSourceList)
    {
        return securityHazardSourceListMapper.insertSecurityHazardSourceList(securityHazardSourceList);
    }

    /**
     * 修改危险源清单
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 结果
     */
    @Override
    public int updateSecurityHazardSourceList(SecurityHazardSourceList securityHazardSourceList)
    {
        return securityHazardSourceListMapper.updateSecurityHazardSourceList(securityHazardSourceList);
    }

    /**
     * 批量删除危险源清单
     * 
     * @param ids 需要删除的危险源清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardSourceListByIds(Long[] ids)
    {
        return securityHazardSourceListMapper.deleteSecurityHazardSourceListByIds(ids);
    }

    /**
     * 删除危险源清单信息
     * 
     * @param id 危险源清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardSourceListById(Long id)
    {
        return securityHazardSourceListMapper.deleteSecurityHazardSourceListById(id);
    }
    
    /**
     * 导入危险源清单数据
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
            ExcelUtil<SecurityHazardSourceList> util = new ExcelUtil<>(SecurityHazardSourceList.class);
            List<SecurityHazardSourceList> riskList = util.importExcel(file.getInputStream());
            
            if (StringUtils.isNull(riskList) || riskList.size() == 0) {
                throw new ServiceException("导入数据不能为空！");
            }
            
            List<SecurityHazardSourceList> validList = new ArrayList<>();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            
            // 记录上一行的值，用于处理合并单元格
            String lastActivity = null;
            String lastHazardSource = null;
            String lastControlMeasures = null;
            
            // 增强处理合并单元格的逻辑
            // 记录连续相同值的开始行
            int activityStartRow = 0;
            int controlMeasuresStartRow = 0;
            
            int rowIndex = 1; // 从第1行开始计数
            for (SecurityHazardSourceList risk : riskList) {
                rowIndex++;
                try {
                    // 检查对象是否为null
                    if (risk == null) {
                        failureNum++;
                        failureMsg.append("<br/>第" + rowIndex + "行：数据解析失败，跳过该行");
                        log.warn("第{}行数据为null，跳过处理", rowIndex);
                        continue;
                    }
                    
                    // 处理作业活动合并单元格
                    // 如果当前行的作业活动为空，使用上一行的值
                    if (StringUtils.isEmpty(risk.getActivity()) && lastActivity != null) {
                        log.info("第{}行作业活动为空，使用上一行值：{}", rowIndex, lastActivity);
                        risk.setActivity(lastActivity);
                    } else if (StringUtils.isNotEmpty(risk.getActivity())) {
                        // 如果当前行有值，更新lastActivity
                        lastActivity = risk.getActivity();
                        // 更新开始行
                        activityStartRow = rowIndex - 1;
                        log.debug("更新作业活动开始行为: {}, 值: {}", activityStartRow, lastActivity);
                    }
                    
                    // 处理危险源合并单元格
                    if (StringUtils.isEmpty(risk.getHazardSource()) && lastHazardSource != null) {
                        log.info("第{}行危险源为空，使用上一行值：{}", rowIndex, lastHazardSource);
                        risk.setHazardSource(lastHazardSource);
                    } else if (StringUtils.isNotEmpty(risk.getHazardSource())) {
                        lastHazardSource = risk.getHazardSource();
                    }
                    
                    // 处理控制措施合并单元格
                    if (StringUtils.isEmpty(risk.getControlMeasures()) && lastControlMeasures != null) {
                        log.info("第{}行控制措施为空，使用上一行值：{}", rowIndex, lastControlMeasures);
                        risk.setControlMeasures(lastControlMeasures);
                    } else if (StringUtils.isNotEmpty(risk.getControlMeasures())) {
                        lastControlMeasures = risk.getControlMeasures();
                        // 更新开始行
                        controlMeasuresStartRow = rowIndex - 1;
                        log.debug("更新控制措施开始行为: {}, 值: {}", controlMeasuresStartRow, lastControlMeasures);
                    }
                    
                    // 数据验证：作业活动和危险源是必填项
                    if (StringUtils.isNotEmpty(risk.getActivity()) && 
                        StringUtils.isNotEmpty(risk.getHazardSource())) {
                        
                        validList.add(risk);
                        successNum++;
                        log.info("处理第{}行成功：作业活动={}, 危险源={}, 管控措施={}", 
                                rowIndex,
                                risk.getActivity(), 
                                risk.getHazardSource(),
                                risk.getControlMeasures());
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
                    // 批量插入有效数据
                    securityHazardSourceListMapper.batchInsertRiskList(validList);
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
        return securityHazardSourceListMapper.updateLatestImportedRelatedId(relatedId);
    }
}
