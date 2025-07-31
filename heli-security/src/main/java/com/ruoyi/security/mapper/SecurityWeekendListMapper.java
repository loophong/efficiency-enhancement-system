package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityWeekendList;

/**
 * 节假日检查记录Mapper接口
 * 
 * @author wang
 * @date 2025-03-28
 */
public interface SecurityWeekendListMapper 
{
    /**
     * 查询节假日检查记录
     * 
     * @param id 节假日检查记录主键
     * @return 节假日检查记录
     */
    public SecurityWeekendList selectSecurityWeekendListById(String id);

    /**
     * 查询节假日检查记录列表
     * 
     * @param securityWeekendList 节假日检查记录
     * @return 节假日检查记录集合
     */
    public List<SecurityWeekendList> selectSecurityWeekendListList(SecurityWeekendList securityWeekendList);

    /**
     * 新增节假日检查记录
     * 
     * @param securityWeekendList 节假日检查记录
     * @return 结果
     */
    public int insertSecurityWeekendList(SecurityWeekendList securityWeekendList);

    /**
     * 修改节假日检查记录
     * 
     * @param securityWeekendList 节假日检查记录
     * @return 结果
     */
    public int updateSecurityWeekendList(SecurityWeekendList securityWeekendList);

    /**
     * 删除节假日检查记录
     * 
     * @param id 节假日检查记录主键
     * @return 结果
     */
    public int deleteSecurityWeekendListById(String id);

    /**
     * 批量删除节假日检查记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityWeekendListByIds(String[] ids);
}
