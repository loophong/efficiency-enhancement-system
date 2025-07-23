package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityZerenList;

/**
 * 安全责任清单Service接口
 * 
 * @author wang
 * @date 2025-07-18
 */
public interface ISecurityZerenListService 
{
    /**
     * 查询安全责任清单
     * 
     * @param id 安全责任清单主键
     * @return 安全责任清单
     */
    public SecurityZerenList selectSecurityZerenListById(Long id);

    /**
     * 查询安全责任清单列表
     * 
     * @param securityZerenList 安全责任清单
     * @return 安全责任清单集合
     */
    public List<SecurityZerenList> selectSecurityZerenListList(SecurityZerenList securityZerenList);

    /**
     * 新增安全责任清单
     * 
     * @param securityZerenList 安全责任清单
     * @return 结果
     */
    public int insertSecurityZerenList(SecurityZerenList securityZerenList);

    /**
     * 修改安全责任清单
     * 
     * @param securityZerenList 安全责任清单
     * @return 结果
     */
    public int updateSecurityZerenList(SecurityZerenList securityZerenList);

    /**
     * 批量删除安全责任清单
     * 
     * @param ids 需要删除的安全责任清单主键集合
     * @return 结果
     */
    public int deleteSecurityZerenListByIds(Long[] ids);

    /**
     * 删除安全责任清单信息
     * 
     * @param id 安全责任清单主键
     * @return 结果
     */
    public int deleteSecurityZerenListById(Long id);
}
