package com.ruoyi.security.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityCompanyImportantEnvironmentalFactorsMapper;
import com.ruoyi.security.domain.SecurityCompanyImportantEnvironmentalFactors;
import com.ruoyi.security.service.ISecurityCompanyImportantEnvironmentalFactorsService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 公司级重要环境因素清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-26
 */
@Service
public class SecurityCompanyImportantEnvironmentalFactorsServiceImpl implements ISecurityCompanyImportantEnvironmentalFactorsService 
{
    private static final Logger log = LoggerFactory.getLogger(SecurityCompanyImportantEnvironmentalFactorsServiceImpl.class);
    
    @Autowired
    private SecurityCompanyImportantEnvironmentalFactorsMapper securityCompanyImportantEnvironmentalFactorsMapper;

    /**
     * 查询公司级重要环境因素清单
     * 
     * @param id 公司级重要环境因素清单主键
     * @return 公司级重要环境因素清单
     */
    @Override
    public SecurityCompanyImportantEnvironmentalFactors selectSecurityCompanyImportantEnvironmentalFactorsById(Long id)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.selectSecurityCompanyImportantEnvironmentalFactorsById(id);
    }

    /**
     * 查询公司级重要环境因素清单列表
     * 
     * @param securityCompanyImportantEnvironmentalFactors 公司级重要环境因素清单
     * @return 公司级重要环境因素清单
     */
    @Override
    public List<SecurityCompanyImportantEnvironmentalFactors> selectSecurityCompanyImportantEnvironmentalFactorsList(SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.selectSecurityCompanyImportantEnvironmentalFactorsList(securityCompanyImportantEnvironmentalFactors);
    }

    /**
     * 新增公司级重要环境因素清单
     * 
     * @param securityCompanyImportantEnvironmentalFactors 公司级重要环境因素清单
     * @return 结果
     */
    @Override
    public int insertSecurityCompanyImportantEnvironmentalFactors(SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.insertSecurityCompanyImportantEnvironmentalFactors(securityCompanyImportantEnvironmentalFactors);
    }

    /**
     * 修改公司级重要环境因素清单
     * 
     * @param securityCompanyImportantEnvironmentalFactors 公司级重要环境因素清单
     * @return 结果
     */
    @Override
    public int updateSecurityCompanyImportantEnvironmentalFactors(SecurityCompanyImportantEnvironmentalFactors securityCompanyImportantEnvironmentalFactors)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.updateSecurityCompanyImportantEnvironmentalFactors(securityCompanyImportantEnvironmentalFactors);
    }

    /**
     * 批量删除公司级重要环境因素清单
     * 
     * @param ids 需要删除的公司级重要环境因素清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityCompanyImportantEnvironmentalFactorsByIds(Long[] ids)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.deleteSecurityCompanyImportantEnvironmentalFactorsByIds(ids);
    }

    /**
     * 删除公司级重要环境因素清单信息
     * 
     * @param id 公司级重要环境因素清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityCompanyImportantEnvironmentalFactorsById(Long id)
    {
        return securityCompanyImportantEnvironmentalFactorsMapper.deleteSecurityCompanyImportantEnvironmentalFactorsById(id);
    }
    
    /**
     * 导入公司级重要环境因素清单数据
     * 
     * @param file 导入文件
     * @param updateSupport 是否更新已存在数据
     * @return 结果
     */
    @Override
    public String importData(MultipartFile file, boolean updateSupport)
    {
        try {
            ExcelUtil<SecurityCompanyImportantEnvironmentalFactors> util = new ExcelUtil<>(SecurityCompanyImportantEnvironmentalFactors.class);
            List<SecurityCompanyImportantEnvironmentalFactors> factorsList = util.importExcel(file.getInputStream());
            
            if (StringUtils.isNull(factorsList) || factorsList.size() == 0) {
                throw new ServiceException("导入数据不能为空！");
            }
            
            List<SecurityCompanyImportantEnvironmentalFactors> validList = new ArrayList<>();
            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();
            
            String lastEnvironmentalFactor = null;
            String lastEnvironmentalImpact = null;
            String lastControlMeasures = null;
            
            for (SecurityCompanyImportantEnvironmentalFactors factors : factorsList) {
                try {
                    // 处理合并单元格 - 如果环境因素为空，使用上一行的值
                    if (StringUtils.isEmpty(factors.getEnvironmentalFactor()) && lastEnvironmentalFactor != null) {
                        factors.setEnvironmentalFactor(lastEnvironmentalFactor);
                    } else if (StringUtils.isNotEmpty(factors.getEnvironmentalFactor())) {
                        lastEnvironmentalFactor = factors.getEnvironmentalFactor();
                    }
                    
                    // 处理合并单元格 - 如果环境影响为空，使用上一行的值
                    if (StringUtils.isEmpty(factors.getEnvironmentalImpact()) && lastEnvironmentalImpact != null) {
                        factors.setEnvironmentalImpact(lastEnvironmentalImpact);
                    } else if (StringUtils.isNotEmpty(factors.getEnvironmentalImpact())) {
                        lastEnvironmentalImpact = factors.getEnvironmentalImpact();
                    }
                    
                    // 处理合并单元格 - 如果管控措施为空，使用上一行的值
                    if (StringUtils.isEmpty(factors.getControlMeasures()) && lastControlMeasures != null) {
                        factors.setControlMeasures(lastControlMeasures);
                    } else if (StringUtils.isNotEmpty(factors.getControlMeasures())) {
                        lastControlMeasures = factors.getControlMeasures();
                    }
                    
                    // 如果环境因素和环境影响都不为空，则认为是有效数据
                    if (StringUtils.isNotEmpty(factors.getEnvironmentalFactor()) && 
                        StringUtils.isNotEmpty(factors.getEnvironmentalImpact())) {
                        
                        // 设置默认状态
                        if (StringUtils.isEmpty(factors.getStatu())) {
                            factors.setStatu("0");
                        }
                        
                        validList.add(factors);
                        successNum++;
                        log.info("处理行成功：环境因素={}, 环境影响={}, 管控措施={}", 
                                factors.getEnvironmentalFactor(), 
                                factors.getEnvironmentalImpact(),
                                factors.getControlMeasures());
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>第" + (successNum + failureNum) + "行：必填字段为空");
                        log.warn("处理行失败，必填字段为空：{}", factors);
                    }
                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>第" + (successNum + failureNum) + "行导入失败：" + e.getMessage();
                    failureMsg.append(msg);
                    log.error(msg, e);
                }
            }
            
            if (validList.size() > 0) {
                try {
                    // 批量插入有效数据
                    securityCompanyImportantEnvironmentalFactorsMapper.batchInsertFactors(validList);
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
        return securityCompanyImportantEnvironmentalFactorsMapper.updateLatestImportedRelatedId(relatedId);
    }
}
