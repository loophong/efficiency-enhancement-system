package com.ruoyi.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Iterator;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOrganizationDescriptionMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOrganizationDescription;
import com.ruoyi.security.service.ISecurityEnvironmentalOrganizationDescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 环境识别Service业务层处理
 * 
 */
@Service
public class SecurityEnvironmentalOrganizationDescriptionServiceImpl implements ISecurityEnvironmentalOrganizationDescriptionService 
{
    @Autowired
    private SecurityEnvironmentalOrganizationDescriptionMapper securityEnvironmentalOrganizationDescriptionMapper;

    /**
     * 查询环境识别
     * 
     * @param id 环境识别主键
     * @return 环境识别
     */
    @Override
    public SecurityEnvironmentalOrganizationDescription selectSecurityEnvironmentalOrganizationDescriptionById(Long id)
    {
        return securityEnvironmentalOrganizationDescriptionMapper.selectSecurityEnvironmentalOrganizationDescriptionById(id);
    }

    /**
     * 查询环境识别列表
     * 
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 环境识别
     */
    @Override
    public List<SecurityEnvironmentalOrganizationDescription> selectSecurityEnvironmentalOrganizationDescriptionList(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        return securityEnvironmentalOrganizationDescriptionMapper.selectSecurityEnvironmentalOrganizationDescriptionList(securityEnvironmentalOrganizationDescription);
    }

    /**
     * 构建树形结构列表
     *
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 树形结构列表
     */
    @Override
    public List<SecurityEnvironmentalOrganizationDescription> buildTreeList(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        // 查询所有数据
        List<SecurityEnvironmentalOrganizationDescription> allList = selectSecurityEnvironmentalOrganizationDescriptionList(securityEnvironmentalOrganizationDescription);
        
        if (allList.isEmpty()) {
            return allList;
        }
        
        return buildTree(allList);
    }

    /**
     * 构建树形结构
     *
     * @param list 原始列表
     * @return 树形结构列表
     */
    private List<SecurityEnvironmentalOrganizationDescription> buildTree(List<SecurityEnvironmentalOrganizationDescription> list)
    {
        List<SecurityEnvironmentalOrganizationDescription> returnList = new ArrayList<>();
        List<Long> idList = list.stream().map(SecurityEnvironmentalOrganizationDescription::getId).collect(Collectors.toList());
        
        for (SecurityEnvironmentalOrganizationDescription item : list)
        {
            // 如果是顶级节点或父节点不存在，则加入返回列表
            if (item.getParentId() == null || item.getParentId() == 0 || !idList.contains(item.getParentId()))
            {
                recursionFn(list, item);
                returnList.add(item);
            }
        }
        
        if (returnList.isEmpty())
        {
            returnList = list;
        }
        return returnList;
    }

    /**
     * 递归函数，为每个节点构建子节点列表
     */
    private void recursionFn(List<SecurityEnvironmentalOrganizationDescription> list, SecurityEnvironmentalOrganizationDescription t)
    {
        // 得到子节点列表
        List<SecurityEnvironmentalOrganizationDescription> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SecurityEnvironmentalOrganizationDescription child : childList)
        {
            if (hasChild(list, child))
            {
                recursionFn(list, child);
            }
        }
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SecurityEnvironmentalOrganizationDescription> list, SecurityEnvironmentalOrganizationDescription t)
    {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 得到子节点列表
     */
    private List<SecurityEnvironmentalOrganizationDescription> getChildList(List<SecurityEnvironmentalOrganizationDescription> list, SecurityEnvironmentalOrganizationDescription t)
    {
        List<SecurityEnvironmentalOrganizationDescription> childList = new ArrayList<>();
        for (SecurityEnvironmentalOrganizationDescription n : list)
        {
            if (n.getParentId() != null && n.getParentId().longValue() == t.getId().longValue())
            {
                childList.add(n);
            }
        }
        return childList;
    }

    /**
     * 新增环境识别
     * 
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOrganizationDescription(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        securityEnvironmentalOrganizationDescription.setCreateTime(DateUtils.getNowDate());
        return securityEnvironmentalOrganizationDescriptionMapper.insertSecurityEnvironmentalOrganizationDescription(securityEnvironmentalOrganizationDescription);
    }

    /**
     * 修改环境识别
     * 
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOrganizationDescription(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        securityEnvironmentalOrganizationDescription.setUpdateTime(DateUtils.getNowDate());
        return securityEnvironmentalOrganizationDescriptionMapper.updateSecurityEnvironmentalOrganizationDescription(securityEnvironmentalOrganizationDescription);
    }

    /**
     * 批量删除环境识别
     * 
     * @param ids 需要删除的环境识别主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOrganizationDescriptionByIds(Long[] ids)
    {
        return securityEnvironmentalOrganizationDescriptionMapper.deleteSecurityEnvironmentalOrganizationDescriptionByIds(ids);
    }

    /**
     * 删除环境识别信息
     * 
     * @param id 环境识别主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOrganizationDescriptionById(Long id)
    {
        return securityEnvironmentalOrganizationDescriptionMapper.deleteSecurityEnvironmentalOrganizationDescriptionById(id);
    }
    
    /**
     * 更新最近导入的环境识别数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        // 查询最近导入的数据
        SecurityEnvironmentalOrganizationDescription query = new SecurityEnvironmentalOrganizationDescription();
        // 按创建时间倒序排序，获取最近的记录
        List<SecurityEnvironmentalOrganizationDescription> recentRecords = 
            securityEnvironmentalOrganizationDescriptionMapper.selectLatestImportedRecords();
        
        if (recentRecords == null || recentRecords.isEmpty()) {
            return 0;
        }
        
        int updatedCount = 0;
        
        // 更新这些记录的relatedId
        for (SecurityEnvironmentalOrganizationDescription record : recentRecords) {
            record.setRelatedId(relatedId);
            record.setUpdateTime(DateUtils.getNowDate());
            updatedCount += securityEnvironmentalOrganizationDescriptionMapper.updateSecurityEnvironmentalOrganizationDescription(record);
        }
        
        return updatedCount;
    }

    /**
     * 批量新增环境识别
     * 
     * @param list 环境识别列表
     * @return 结果
     */
    @Override
    public int batchInsertSecurityEnvironmentalOrganizationDescription(List<SecurityEnvironmentalOrganizationDescription> list) {
        Logger log = LoggerFactory.getLogger(SecurityEnvironmentalOrganizationDescriptionServiceImpl.class);
        
        if (list == null || list.isEmpty()) {
            return 0;
        }
        
        int successCount = 0;
        for (SecurityEnvironmentalOrganizationDescription item : list) {
            try {
                // 设置创建时间
                if (item.getCreateTime() == null) {
                    item.setCreateTime(DateUtils.getNowDate());
                }
                
                // 确保父ID不为空
                if (item.getParentId() == null) {
                    item.setParentId(0L);
                }
                
                // 插入记录
                successCount += securityEnvironmentalOrganizationDescriptionMapper.insertSecurityEnvironmentalOrganizationDescription(item);
                
            } catch (Exception e) {
                log.error("批量插入环境识别数据失败: {}", e.getMessage(), e);
            }
        }
        
        log.info("批量插入环境识别数据成功，共 {} 条记录", successCount);
        return successCount;
    }
}
