package com.ruoyi.security.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecutityEnvironmentalFactorsImpactMapper;
import com.ruoyi.security.domain.SecutityEnvironmentalFactorsImpact;
import com.ruoyi.security.service.ISecutityEnvironmentalFactorsImpactService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 环境因素清单Service业务层处理
 * 
 * @author wang
 * @date 2025-07-14
 */
@Service
public class SecutityEnvironmentalFactorsImpactServiceImpl implements ISecutityEnvironmentalFactorsImpactService 
{
    private static final Logger log = LoggerFactory.getLogger(SecutityEnvironmentalFactorsImpactServiceImpl.class);
    
    @Autowired
    private SecutityEnvironmentalFactorsImpactMapper secutityEnvironmentalFactorsImpactMapper;

    /**
     * 查询环境因素清单
     * 
     * @param id 环境因素清单主键
     * @return 环境因素清单
     */
    @Override
    public SecutityEnvironmentalFactorsImpact selectSecutityEnvironmentalFactorsImpactById(Long id)
    {
        return secutityEnvironmentalFactorsImpactMapper.selectSecutityEnvironmentalFactorsImpactById(id);
    }

    /**
     * 查询环境因素清单列表
     * 
     * @param secutityEnvironmentalFactorsImpact 环境因素清单
     * @return 环境因素清单
     */
    @Override
    public List<SecutityEnvironmentalFactorsImpact> selectSecutityEnvironmentalFactorsImpactList(SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact)
    {
        return secutityEnvironmentalFactorsImpactMapper.selectSecutityEnvironmentalFactorsImpactList(secutityEnvironmentalFactorsImpact);
    }

    /**
     * 新增环境因素清单
     * 
     * @param secutityEnvironmentalFactorsImpact 环境因素清单
     * @return 结果
     */
    @Override
    public int insertSecutityEnvironmentalFactorsImpact(SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact)
    {
        return secutityEnvironmentalFactorsImpactMapper.insertSecutityEnvironmentalFactorsImpact(secutityEnvironmentalFactorsImpact);
    }

    /**
     * 修改环境因素清单
     * 
     * @param secutityEnvironmentalFactorsImpact 环境因素清单
     * @return 结果
     */
    @Override
    public int updateSecutityEnvironmentalFactorsImpact(SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact)
    {
        return secutityEnvironmentalFactorsImpactMapper.updateSecutityEnvironmentalFactorsImpact(secutityEnvironmentalFactorsImpact);
    }

    /**
     * 批量删除环境因素清单
     * 
     * @param ids 需要删除的环境因素清单主键
     * @return 结果
     */
    @Override
    public int deleteSecutityEnvironmentalFactorsImpactByIds(Long[] ids)
    {
        return secutityEnvironmentalFactorsImpactMapper.deleteSecutityEnvironmentalFactorsImpactByIds(ids);
    }

    /**
     * 删除环境因素清单信息
     * 
     * @param id 环境因素清单主键
     * @return 结果
     */
    @Override
    public int deleteSecutityEnvironmentalFactorsImpactById(Long id)
    {
        return secutityEnvironmentalFactorsImpactMapper.deleteSecutityEnvironmentalFactorsImpactById(id);
    }
    
    /**
     * 导入环境因素清单数据
     * 
     * @param file 导入文件
     * @param updateSupport 是否更新已存在数据
     * @return 结果
     */
    @Override
    public String importData(MultipartFile file, boolean updateSupport)
    {
        try {
            ExcelUtil<SecutityEnvironmentalFactorsImpact> util = new ExcelUtil<>(SecutityEnvironmentalFactorsImpact.class);
            List<SecutityEnvironmentalFactorsImpact> impactList = util.importExcel(file.getInputStream());
            
            if (StringUtils.isNull(impactList) || impactList.size() == 0) {
                throw new ServiceException("导入数据不能为空！");
            }
            
            List<SecutityEnvironmentalFactorsImpact> validList = new ArrayList<>();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            
            String lastEnvironmentalFactor = null;
            String lastImpactOfEnvironmentalFactor = null;
            String lastControlMeasures = null;
            
            int rowIndex = 1; // 从第1行开始计数
            for (SecutityEnvironmentalFactorsImpact impact : impactList) {
                rowIndex++;
                try {
                    // 检查对象是否为null
                    if (impact == null) {
                        failureNum++;
                        failureMsg.append("<br/>第" + rowIndex + "行：数据解析失败，跳过该行");
                        log.warn("第{}行数据为null，跳过处理", rowIndex);
                        continue;
                    }
                    
                    // 处理合并单元格 - 如果环境因素为空，使用上一行的值
                    if (StringUtils.isEmpty(impact.getEnvironmentalFactor()) && lastEnvironmentalFactor != null) {
                        impact.setEnvironmentalFactor(lastEnvironmentalFactor);
                    } else if (StringUtils.isNotEmpty(impact.getEnvironmentalFactor())) {
                        lastEnvironmentalFactor = impact.getEnvironmentalFactor();
                    }
                    
                    // 处理合并单元格 - 如果环境影响为空，使用上一行的值
                    if (StringUtils.isEmpty(impact.getImpactOfEnvironmentalFactor()) && lastImpactOfEnvironmentalFactor != null) {
                        impact.setImpactOfEnvironmentalFactor(lastImpactOfEnvironmentalFactor);
                    } else if (StringUtils.isNotEmpty(impact.getImpactOfEnvironmentalFactor())) {
                        lastImpactOfEnvironmentalFactor = impact.getImpactOfEnvironmentalFactor();
                    }
                    
                    // 处理合并单元格 - 如果管控措施为空，使用上一行的值
                    if (StringUtils.isEmpty(impact.getControlMeasures()) && lastControlMeasures != null) {
                        impact.setControlMeasures(lastControlMeasures);
                    } else if (StringUtils.isNotEmpty(impact.getControlMeasures())) {
                        lastControlMeasures = impact.getControlMeasures();
                    }
                    
                    // 如果环境因素和环境影响都不为空，则认为是有效数据
                    if (StringUtils.isNotEmpty(impact.getEnvironmentalFactor()) && 
                        StringUtils.isNotEmpty(impact.getImpactOfEnvironmentalFactor())) {
                        
                        // 设置默认状态
                        if (StringUtils.isEmpty(impact.getStatu())) {
                            impact.setStatu("0");
                        }
                        
                        validList.add(impact);
                        successNum++;
                        log.info("处理第{}行成功：环境因素={}, 环境影响={}, 管控措施={}", 
                                rowIndex,
                                impact.getEnvironmentalFactor(), 
                                impact.getImpactOfEnvironmentalFactor(),
                                impact.getControlMeasures());
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>第" + rowIndex + "行：必填字段为空");
                        log.warn("处理第{}行失败，必填字段为空：{}", rowIndex, impact);
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
                    secutityEnvironmentalFactorsImpactMapper.batchInsertImpacts(validList);
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
        return secutityEnvironmentalFactorsImpactMapper.updateLatestImportedRelatedId(relatedId);
    }
}
