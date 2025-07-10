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
}
