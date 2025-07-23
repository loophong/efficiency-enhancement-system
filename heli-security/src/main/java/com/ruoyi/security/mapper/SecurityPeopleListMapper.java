package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityPeopleList;

/**
 * 班组人员清单Mapper接口
 * 
 * @author wang
 * @date 2025-07-21
 */
public interface SecurityPeopleListMapper 
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
     * 删除班组人员清单
     * 
     * @param id 班组人员清单主键
     * @return 结果
     */
    public int deleteSecurityPeopleListById(String id);

    /**
     * 批量删除班组人员清单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityPeopleListByIds(String[] ids);

    /**
     * 根据姓名和班组查询班组人员清单
     *
     * @param xingMing 姓名
     * @param banZu 班组
     * @return 班组人员清单
     */
    public SecurityPeopleList selectSecurityPeopleListByName(String xingMing, String banZu);

    /**
     * 批量更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
