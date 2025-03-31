package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityImportantListMapper;
import com.ruoyi.security.domain.SecurityImportantList;
import com.ruoyi.security.service.ISecurityImportantListService;

/**
 * 重点部位检查记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@Service
public class SecurityImportantListServiceImpl implements ISecurityImportantListService 
{
    @Autowired
    private SecurityImportantListMapper securityImportantListMapper;

    /**
     * 查询重点部位检查记录
     * 
     * @param id 重点部位检查记录主键
     * @return 重点部位检查记录
     */
    @Override
    public SecurityImportantList selectSecurityImportantListById(String id)
    {
        return securityImportantListMapper.selectSecurityImportantListById(id);
    }

    /**
     * 查询重点部位检查记录列表
     * 
     * @param securityImportantList 重点部位检查记录
     * @return 重点部位检查记录
     */
    @Override
    public List<SecurityImportantList> selectSecurityImportantListList(SecurityImportantList securityImportantList)
    {
        return securityImportantListMapper.selectSecurityImportantListList(securityImportantList);
    }

    /**
     * 新增重点部位检查记录
     * 
     * @param securityImportantList 重点部位检查记录
     * @return 结果
     */
    @Override
    public int insertSecurityImportantList(SecurityImportantList securityImportantList)
    {
        return securityImportantListMapper.insertSecurityImportantList(securityImportantList);
    }

    /**
     * 修改重点部位检查记录
     * 
     * @param securityImportantList 重点部位检查记录
     * @return 结果
     */
    @Override
    public int updateSecurityImportantList(SecurityImportantList securityImportantList)
    {
        return securityImportantListMapper.updateSecurityImportantList(securityImportantList);
    }

    /**
     * 批量删除重点部位检查记录
     * 
     * @param ids 需要删除的重点部位检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityImportantListByIds(String[] ids)
    {
        return securityImportantListMapper.deleteSecurityImportantListByIds(ids);
    }

    /**
     * 删除重点部位检查记录信息
     * 
     * @param id 重点部位检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityImportantListById(String id)
    {
        return securityImportantListMapper.deleteSecurityImportantListById(id);
    }
}
