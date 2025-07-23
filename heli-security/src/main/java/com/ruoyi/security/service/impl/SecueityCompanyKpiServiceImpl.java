package com.ruoyi.security.service.impl;

import java.util.List;

import com.ruoyi.security.domain.SecurityCompanyKeyWorks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecueityCompanyKpiMapper;
import com.ruoyi.security.domain.SecueityCompanyKpi;
import com.ruoyi.security.service.ISecueityCompanyKpiService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;

/**
 * 公司KPI服务实现
 * 
 * @author wang
 * @date 2025-02-27
 */
@Service
public class SecueityCompanyKpiServiceImpl implements ISecueityCompanyKpiService 
{
    private static final Logger log = LoggerFactory.getLogger(SecueityCompanyKpiServiceImpl.class);
    
    // 匹配目标值格式的正则表达式
    private static final Pattern TARGET_VALUE_PATTERN = Pattern.compile("^([<>=]+)?(\\d+(\\.\\d+)?)$");

    @Autowired
    private SecueityCompanyKpiMapper secueityCompanyKpiMapper;

    /**
     * 查询公司KPI
     * 
     * @param id 公司KPI主键
     * @return 公司KPI
     */
    @Override
    public SecueityCompanyKpi selectSecueityCompanyKpiById(Long id)
    {
        return secueityCompanyKpiMapper.selectSecueityCompanyKpiById(id);
    }

    /**
     * 查询公司KPI列表
     * 
     * @param secueityCompanyKpi 公司KPI
     * @return 公司KPI
     */
    @Override
    public List<SecueityCompanyKpi> selectSecueityCompanyKpiList(SecueityCompanyKpi secueityCompanyKpi)
    {
        return secueityCompanyKpiMapper.selectSecueityCompanyKpiList(secueityCompanyKpi);
    }

    /**
     * 根据关联ID查询公司KPI列表
     * 
     * @param relatedId 关联ID
     * @return 公司KPI集合
     */
    @Override
    public List<SecueityCompanyKpi> selectSecueityCompanyKpiByRelatedId(Long relatedId)
    {
        return secueityCompanyKpiMapper.selectSecueityCompanyKpiByRelatedId(relatedId);
    }

    /**
     * 新增公司KPI
     * 
     * @param secueityCompanyKpi 公司KPI
     * @return 结果
     */
    @Override
    public int insertSecueityCompanyKpi(SecueityCompanyKpi secueityCompanyKpi)
    {
        return secueityCompanyKpiMapper.insertSecueityCompanyKpi(secueityCompanyKpi);
    }

    /**
     * 修改公司KPI
     * 
     * @param secueityCompanyKpi 公司KPI
     * @return 结果
     */
    @Override
    public int updateSecueityCompanyKpi(SecueityCompanyKpi secueityCompanyKpi)
    {
        return secueityCompanyKpiMapper.updateSecueityCompanyKpi(secueityCompanyKpi);
    }

    /**
     * 批量删除公司KPI
     * 
     * @param ids 需要删除的公司KPI主键
     * @return 结果
     */
    @Override
    public int deleteSecueityCompanyKpiByIds(Long[] ids)
    {
        return secueityCompanyKpiMapper.deleteSecueityCompanyKpiByIds(ids);
    }

    /**
     * 删除公司KPI信息
     * 
     * @param id 公司KPI主键
     * @return 结果
     */
    @Override
    public int deleteSecueityCompanyKpiById(Long id)
    {
        return secueityCompanyKpiMapper.deleteSecueityCompanyKpiById(id);
    }

    /**
     * 根据关联ID删除公司KPI
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int deleteSecueityCompanyKpiByRelatedId(Long relatedId)
    {
        return secueityCompanyKpiMapper.deleteSecueityCompanyKpiByRelatedId(relatedId);
    }

    /**
     * 解析目标值格式
     * 
     * @param value 原始值
     * @return 解析结果，包含操作符和数值
     */
    private Map<String, String> parseTargetValue(String value) {
        Map<String, String> result = new HashMap<>();
        result.put("operator", "");
        result.put("value", "0");
        
        if (StringUtils.isEmpty(value)) {
            return result;
        }
        
        Matcher matcher = TARGET_VALUE_PATTERN.matcher(value.trim());
        if (matcher.find()) {
            String operator = matcher.group(1);
            String numValue = matcher.group(2);
            
            result.put("operator", operator != null ? operator : "");
            result.put("value", numValue != null ? numValue : "0");
        }
        
        return result;
    }

    /**
     * 导入KPI数据
     * 
     * @param kpiList KPI数据列表
     * @param operName 操作人
     * @return 结果
     */
    @Override
    public String importKpi(List<SecueityCompanyKpi> kpiList, String operName)
    {
        if (StringUtils.isNull(kpiList) || kpiList.size() == 0)
        {
            throw new ServiceException("导入KPI数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        
        for (SecueityCompanyKpi kpi : kpiList)
        {
            try
            {
                // 设置默认值
                if (StringUtils.isEmpty(kpi.getUnitName()))
                {
                    kpi.setUnitName("");  // 设置为空字符串
                }
                
                if (StringUtils.isEmpty(kpi.getCategory()))
                {
                    kpi.setCategory("默认分类");
                }
                
                if (StringUtils.isEmpty(kpi.getIndicatorName()))
                {
                    kpi.setIndicatorName("默认指标");
                }
                
                if (StringUtils.isEmpty(kpi.getUnitOfMeasurement()))
                {
                    kpi.setUnitOfMeasurement("个");
                }
                
                if (StringUtils.isEmpty(kpi.getYear()))
                {
                    kpi.setYear(DateUtils.getDate().substring(0, 4));
                }
                
                if (StringUtils.isEmpty(kpi.getEvaluationFrequency()))
                {
                    kpi.setEvaluationFrequency("季度");
                }
                
                if (kpi.getWeight() == null)
                {
                    kpi.setWeight(new BigDecimal("1"));
                }
                
                // 处理特殊格式的目标值
                StringBuilder formatInfo = new StringBuilder();
                
                // 处理年度目标值
                if (kpi.getAnnualTargetValue() == null || kpi.getAnnualTargetValue().compareTo(BigDecimal.ZERO) == 0)
                {
                    kpi.setAnnualTargetValue(new BigDecimal("95"));
                    formatInfo.append("ANNUAL_TARGET:>=;");
                }
                
                // 处理一季度目标值
                if (kpi.getQ1TargetValue() == null || kpi.getQ1TargetValue().compareTo(BigDecimal.ZERO) == 0)
                {
                    kpi.setQ1TargetValue(new BigDecimal("95"));
                    formatInfo.append("Q1_TARGET:>=;");
                }
                
                // 处理二季度目标值
                if (kpi.getQ2TargetValue() == null || kpi.getQ2TargetValue().compareTo(BigDecimal.ZERO) == 0)
                {
                    kpi.setQ2TargetValue(new BigDecimal("95"));
                    formatInfo.append("Q2_TARGET:>=;");
                }
                
                // 处理三季度目标值
                if (kpi.getQ3TargetValue() == null || kpi.getQ3TargetValue().compareTo(BigDecimal.ZERO) == 0)
                {
                    kpi.setQ3TargetValue(new BigDecimal("95"));
                    formatInfo.append("Q3_TARGET:>=;");
                }
                
                // 处理四季度目标值
                if (kpi.getQ4TargetValue() == null || kpi.getQ4TargetValue().compareTo(BigDecimal.ZERO) == 0)
                {
                    kpi.setQ4TargetValue(new BigDecimal("95"));
                    formatInfo.append("Q4_TARGET:>=;");
                }
                
                // 添加格式信息到隐藏字段或元数据中，而不是备注
                // 这里我们仍然使用备注字段存储格式信息，但是前端会过滤掉这些信息
                if (formatInfo.length() > 0)
                {
                    String beizhu = kpi.getBeizhu();
                    if (StringUtils.isEmpty(beizhu))
                    {
                        // 如果没有备注，只添加格式信息
                        kpi.setBeizhu("FORMAT_INFO:" + formatInfo.toString());
                    }
                    else
                    {
                        // 如果已有备注，将格式信息添加到备注后面
                        // 检查备注是否已经包含格式信息
                        if (beizhu.contains("FORMAT_INFO:"))
                        {
                            // 替换已有的格式信息
                            kpi.setBeizhu(beizhu.replaceAll("FORMAT_INFO:[^；]+", "FORMAT_INFO:" + formatInfo.toString()));
                        }
                        else
                        {
                            // 添加新的格式信息
                            kpi.setBeizhu(beizhu + "；FORMAT_INFO:" + formatInfo.toString());
                        }
                    }
                }
                
                // 插入数据
                this.insertSecueityCompanyKpi(kpi);
                successNum++;
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>第 " + (successNum + failureNum) + " 条数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据导入失败，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
        }
        return successMsg.toString();
    }

    /**
     * 更新最近导入的KPI数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId)
    {
        log.info("更新最近导入的KPI数据关联ID: {}", relatedId);
        
        // 查询最近导入的数据
        List<SecueityCompanyKpi> recentRecords = secueityCompanyKpiMapper.selectLatestImportedRecords();
        
        if (recentRecords == null || recentRecords.isEmpty()) {
            log.info("没有找到需要更新关联ID的KPI记录");
            return 0;
        }
        
        int updatedCount = 0;
        
        // 更新这些记录的relatedId
        for (SecueityCompanyKpi record : recentRecords) {
            record.setRelatedId(relatedId);
            updatedCount += secueityCompanyKpiMapper.updateSecueityCompanyKpi(record);
        }
        
        log.info("成功更新KPI数据关联ID，共更新{}条记录", updatedCount);
        return updatedCount;
    }
}
