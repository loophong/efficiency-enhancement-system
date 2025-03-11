package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityOccupationalHazardPositionPersonnelList;

/**
 * 危险职业岗位人员清单及管理台帐Service接口
 * 
 * @author wang
 * @date 2025-03-05
 */
public interface ISecurityOccupationalHazardPositionPersonnelListService 
{
    /**
     * 查询危险职业岗位人员清单及管理台帐
     * 
     * @param id 危险职业岗位人员清单及管理台帐主键
     * @return 危险职业岗位人员清单及管理台帐
     */
    public SecurityOccupationalHazardPositionPersonnelList selectSecurityOccupationalHazardPositionPersonnelListById(Long id);

    /**
     * 查询危险职业岗位人员清单及管理台帐列表
     * 
     * @param securityOccupationalHazardPositionPersonnelList 危险职业岗位人员清单及管理台帐
     * @return 危险职业岗位人员清单及管理台帐集合
     */
    public List<SecurityOccupationalHazardPositionPersonnelList> selectSecurityOccupationalHazardPositionPersonnelListList(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList);

    /**
     * 新增危险职业岗位人员清单及管理台帐
     * 
     * @param securityOccupationalHazardPositionPersonnelList 危险职业岗位人员清单及管理台帐
     * @return 结果
     */
    public int insertSecurityOccupationalHazardPositionPersonnelList(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList);

    /**
     * 修改危险职业岗位人员清单及管理台帐
     * 
     * @param securityOccupationalHazardPositionPersonnelList 危险职业岗位人员清单及管理台帐
     * @return 结果
     */
    public int updateSecurityOccupationalHazardPositionPersonnelList(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList);

    /**
     * 批量删除危险职业岗位人员清单及管理台帐
     * 
     * @param ids 需要删除的危险职业岗位人员清单及管理台帐主键集合
     * @return 结果
     */
    public int deleteSecurityOccupationalHazardPositionPersonnelListByIds(Long[] ids);

    /**
     * 删除危险职业岗位人员清单及管理台帐信息
     * 
     * @param id 危险职业岗位人员清单及管理台帐主键
     * @return 结果
     */
    public int deleteSecurityOccupationalHazardPositionPersonnelListById(Long id);
}
