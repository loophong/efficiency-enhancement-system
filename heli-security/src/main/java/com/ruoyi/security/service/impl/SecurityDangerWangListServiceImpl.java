package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityDangerWangListMapper;
import com.ruoyi.security.domain.SecurityDangerWangList;
import com.ruoyi.security.service.ISecurityDangerWangListService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Validator;

/**
 * 风险网格化清单Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-30
 */
@Service
public class SecurityDangerWangListServiceImpl implements ISecurityDangerWangListService 
{
    private static final Logger log = LoggerFactory.getLogger(SecurityDangerWangListServiceImpl.class);

    @Autowired
    private SecurityDangerWangListMapper securityDangerWangListMapper;
    
    @Autowired
    protected Validator validator;

    /**
     * 查询风险网格化清单
     * 
     * @param id 风险网格化清单主键
     * @return 风险网格化清单
     */
    @Override
    public SecurityDangerWangList selectSecurityDangerWangListById(String id)
    {
        return securityDangerWangListMapper.selectSecurityDangerWangListById(id);
    }

    /**
     * 查询风险网格化清单列表
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 风险网格化清单
     */
    @Override
    public List<SecurityDangerWangList> selectSecurityDangerWangListList(SecurityDangerWangList securityDangerWangList)
    {
        return securityDangerWangListMapper.selectSecurityDangerWangListList(securityDangerWangList);
    }

    /**
     * 新增风险网格化清单
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 结果
     */
    @Override
    public int insertSecurityDangerWangList(SecurityDangerWangList securityDangerWangList)
    {
        return securityDangerWangListMapper.insertSecurityDangerWangList(securityDangerWangList);
    }

    /**
     * 修改风险网格化清单
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 结果
     */
    @Override
    public int updateSecurityDangerWangList(SecurityDangerWangList securityDangerWangList)
    {
        return securityDangerWangListMapper.updateSecurityDangerWangList(securityDangerWangList);
    }

    /**
     * 批量删除风险网格化清单
     * 
     * @param ids 需要删除的风险网格化清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityDangerWangListByIds(String[] ids)
    {
        return securityDangerWangListMapper.deleteSecurityDangerWangListByIds(ids);
    }

    /**
     * 删除风险网格化清单信息
     * 
     * @param id 风险网格化清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityDangerWangListById(String id)
    {
        return securityDangerWangListMapper.deleteSecurityDangerWangListById(id);
    }
    
    /**
     * 导入风险网格化清单数据
     * 
     * @param dangerWangList 风险网格化清单数据列表
     * @param updateSupport 是否支持更新
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importDangerWangList(List<SecurityDangerWangList> dangerWangList, boolean updateSupport, String operName)
    {
        if (StringUtils.isNull(dangerWangList) || dangerWangList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        
        // 记录最后一条导入数据的relatedId
        String lastRelatedId = null;
        
        // 记录上一行的值，用于处理合并单元格
        String lastChangsuoBanzu = null;      // 所在班组
        String lastGongxuShebei = null;       // 岗位/工序/设备
        String lastJituBuwei = null;          // 危险物质、作业、能量
        String lastWangLuoFuZeRen = null;     // 网格负责人及联系电话
        String lastFuZeRen = null;            // 本岗位负责人
        String lastZhuyaoGongzuo = null;      // 备注
        
        int rowIndex = 1; // 从第1行开始计数
        for (SecurityDangerWangList dangerWang : dangerWangList)
        {
            rowIndex++;
            try
            {
                // 移除为每条记录生成唯一ID的代码
                // dangerWang.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                
                // 处理所在班组合并单元格
                if (StringUtils.isEmpty(dangerWang.getChangsuoBanzu()) && lastChangsuoBanzu != null) {
                    log.info("第{}行所在班组为空，使用上一行值：{}", rowIndex, lastChangsuoBanzu);
                    dangerWang.setChangsuoBanzu(lastChangsuoBanzu);
                } else if (StringUtils.isNotEmpty(dangerWang.getChangsuoBanzu())) {
                    lastChangsuoBanzu = dangerWang.getChangsuoBanzu();
                }
                
                // 处理岗位/工序/设备合并单元格
                if (StringUtils.isEmpty(dangerWang.getGongxuShebei()) && lastGongxuShebei != null) {
                    log.info("第{}行岗位/工序/设备为空，使用上一行值：{}", rowIndex, lastGongxuShebei);
                    dangerWang.setGongxuShebei(lastGongxuShebei);
                } else if (StringUtils.isNotEmpty(dangerWang.getGongxuShebei())) {
                    lastGongxuShebei = dangerWang.getGongxuShebei();
                }
                
                // 处理危险物质、作业、能量合并单元格
                if (StringUtils.isEmpty(dangerWang.getJituBuwei()) && lastJituBuwei != null) {
                    log.info("第{}行危险物质、作业、能量为空，使用上一行值：{}", rowIndex, lastJituBuwei);
                    dangerWang.setJituBuwei(lastJituBuwei);
                } else if (StringUtils.isNotEmpty(dangerWang.getJituBuwei())) {
                    lastJituBuwei = dangerWang.getJituBuwei();
                }
                
                // 处理网格负责人及联系电话合并单元格
                if (StringUtils.isEmpty(dangerWang.getWangLuoFuZeRen()) && lastWangLuoFuZeRen != null) {
                    log.info("第{}行网格负责人及联系电话为空，使用上一行值：{}", rowIndex, lastWangLuoFuZeRen);
                    dangerWang.setWangLuoFuZeRen(lastWangLuoFuZeRen);
                } else if (StringUtils.isNotEmpty(dangerWang.getWangLuoFuZeRen())) {
                    lastWangLuoFuZeRen = dangerWang.getWangLuoFuZeRen();
                }
                
                // 处理本岗位负责人合并单元格
                if (StringUtils.isEmpty(dangerWang.getFuZeRen()) && lastFuZeRen != null) {
                    log.info("第{}行本岗位负责人为空，使用上一行值：{}", rowIndex, lastFuZeRen);
                    dangerWang.setFuZeRen(lastFuZeRen);
                } else if (StringUtils.isNotEmpty(dangerWang.getFuZeRen())) {
                    lastFuZeRen = dangerWang.getFuZeRen();
                }
                
                // 处理备注合并单元格
                if (StringUtils.isEmpty(dangerWang.getZhuyaoGongzuo()) && lastZhuyaoGongzuo != null) {
                    log.info("第{}行备注为空，使用上一行值：{}", rowIndex, lastZhuyaoGongzuo);
                    dangerWang.setZhuyaoGongzuo(lastZhuyaoGongzuo);
                } else if (StringUtils.isNotEmpty(dangerWang.getZhuyaoGongzuo())) {
                    lastZhuyaoGongzuo = dangerWang.getZhuyaoGongzuo();
                }
                
                // 修改：不再检查数据是否已存在，直接插入新记录
                BeanValidators.validateWithException(validator, dangerWang);
                securityDangerWangListMapper.insertSecurityDangerWangList(dangerWang);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、所在班组 ").append(dangerWang.getChangsuoBanzu())
                        .append("，岗位/工序/设备 ").append(dangerWang.getGongxuShebei()).append(" 导入成功");
                
                // 记录relatedId
                if (dangerWang.getRelatedId() != null) {
                    lastRelatedId = dangerWang.getRelatedId();
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、所在班组 " + dangerWang.getChangsuoBanzu() + 
                        "，岗位/工序/设备 " + dangerWang.getGongxuShebei() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        
        // 记录最后导入的relatedId，用于后续处理
        if (lastRelatedId != null) {
            log.info("Last imported relatedId: {}", lastRelatedId);
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
     * 更新最近导入的风险网格化清单关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId)
    {
        if (relatedId == null)
        {
            log.warn("关联ID为空，无法更新风险网格化清单关联ID");
            return 0;
        }
        
        try
        {
            // 查询最近导入的没有关联ID的记录（按ID降序排列，取前100条）
            SecurityDangerWangList query = new SecurityDangerWangList();
            query.setRelatedId(null); // 只查询关联ID为空的记录
            List<SecurityDangerWangList> list = securityDangerWangListMapper.selectLatestImportedWithoutRelatedId(100);
            
            if (list == null || list.isEmpty())
            {
                log.info("没有找到需要更新关联ID的风险网格化清单记录");
                return 0;
            }
            
            int updatedCount = 0;
            for (SecurityDangerWangList item : list)
            {
                // 设置关联ID
                item.setRelatedId(String.valueOf(relatedId));
                int rows = securityDangerWangListMapper.updateSecurityDangerWangList(item);
                updatedCount += rows;
            }
            
            log.info("成功更新 {} 条风险网格化清单记录的关联ID为: {}", updatedCount, relatedId);
            return updatedCount;
        }
        catch (Exception e)
        {
            log.error("更新风险网格化清单关联ID时发生错误", e);
            return 0;
        }
    }
    
    /**
     * 根据关联ID查询风险网格化清单数据
     * 
     * @param relatedId 关联ID
     * @return 风险网格化清单集合
     */
    @Override
    public List<SecurityDangerWangList> selectSecurityDangerWangListByRelatedId(String relatedId)
    {
        if (StringUtils.isEmpty(relatedId))
        {
            log.warn("关联ID为空，无法查询风险网格化清单数据");
            return null;
        }
        
        try
        {
            return securityDangerWangListMapper.selectSecurityDangerWangListByRelatedId(relatedId);
        }
        catch (Exception e)
        {
            log.error("根据关联ID查询风险网格化清单数据时发生错误", e);
            return null;
        }
    }
}
