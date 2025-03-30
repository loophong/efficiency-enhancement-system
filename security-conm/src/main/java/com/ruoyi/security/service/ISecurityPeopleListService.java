package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityPeopleList;

/**
 * 班组人员清单Service接口
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
public interface ISecurityPeopleListService 
{
    /**
     * 查询班组人员清单
     * 
     * @param id 班组人员清单主键
     * @return 班组人员清单
     */
    public SecurityPeopleList selectSecurityPeopleListById(String id);

    /**
     * 查询班组人员清单列表
     * 
     * @param securityPeopleList 班组人员清单
     * @return 班组人员清单集合
     */
    public List<SecurityPeopleList> selectSecurityPeopleListList(SecurityPeopleList securityPeopleList);

    /**
     * 新增班组人员清单
     * 
     * @param securityPeopleList 班组人员清单
     * @return 结果
     */
    public int insertSecurityPeopleList(SecurityPeopleList securityPeopleList);

    /**
     * 修改班组人员清单
     * 
     * @param securityPeopleList 班组人员清单
     * @return 结果
     */
    public int updateSecurityPeopleList(SecurityPeopleList securityPeopleList);

    /**
     * 批量删除班组人员清单
     * 
     * @param ids 需要删除的班组人员清单主键集合
     * @return 结果
     */
    public int deleteSecurityPeopleListByIds(String[] ids);

    /**
     * 删除班组人员清单信息
     * 
     * @param id 班组人员清单主键
     * @return 结果
     */
    public int deleteSecurityPeopleListById(String id);
}
