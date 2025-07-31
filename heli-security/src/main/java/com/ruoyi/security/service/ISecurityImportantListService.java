package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityImportantList;

/**
 * 重点部位检查记录Service接口
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
public interface ISecurityImportantListService 
{
    /**
     * 查询重点部位检查记录
     * 
     * @param id 重点部位检查记录主键
     * @return 重点部位检查记录
     */
    public SecurityImportantList selectSecurityImportantListById(String id);

    /**
     * 查询重点部位检查记录列表
     * 
     * @param securityImportantList 重点部位检查记录
     * @return 重点部位检查记录集合
     */
    public List<SecurityImportantList> selectSecurityImportantListList(SecurityImportantList securityImportantList);

    /**
     * 新增重点部位检查记录
     * 
     * @param securityImportantList 重点部位检查记录
     * @return 结果
     */
    public int insertSecurityImportantList(SecurityImportantList securityImportantList);

    /**
     * 修改重点部位检查记录
     * 
     * @param securityImportantList 重点部位检查记录
     * @return 结果
     */
    public int updateSecurityImportantList(SecurityImportantList securityImportantList);

    /**
     * 批量删除重点部位检查记录
     * 
     * @param ids 需要删除的重点部位检查记录主键集合
     * @return 结果
     */
    public int deleteSecurityImportantListByIds(String[] ids);

    /**
     * 删除重点部位检查记录信息
     * 
     * @param id 重点部位检查记录主键
     * @return 结果
     */
    public int deleteSecurityImportantListById(String id);
}
