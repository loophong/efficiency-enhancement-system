package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityRelateNowcheck;

/**
 * 相关方现场检查Service接口
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
public interface ISecurityRelateNowcheckService 
{
    /**
     * 查询相关方现场检查
     * 
     * @param id 相关方现场检查主键
     * @return 相关方现场检查
     */
    public SecurityRelateNowcheck selectSecurityRelateNowcheckById(String id);

    /**
     * 查询相关方现场检查列表
     * 
     * @param securityRelateNowcheck 相关方现场检查
     * @return 相关方现场检查集合
     */
    public List<SecurityRelateNowcheck> selectSecurityRelateNowcheckList(SecurityRelateNowcheck securityRelateNowcheck);

    /**
     * 新增相关方现场检查
     * 
     * @param securityRelateNowcheck 相关方现场检查
     * @return 结果
     */
    public int insertSecurityRelateNowcheck(SecurityRelateNowcheck securityRelateNowcheck);

    /**
     * 修改相关方现场检查
     * 
     * @param securityRelateNowcheck 相关方现场检查
     * @return 结果
     */
    public int updateSecurityRelateNowcheck(SecurityRelateNowcheck securityRelateNowcheck);

    /**
     * 批量删除相关方现场检查
     * 
     * @param ids 需要删除的相关方现场检查主键集合
     * @return 结果
     */
    public int deleteSecurityRelateNowcheckByIds(String[] ids);

    /**
     * 删除相关方现场检查信息
     * 
     * @param id 相关方现场检查主键
     * @return 结果
     */
    public int deleteSecurityRelateNowcheckById(String id);
}
