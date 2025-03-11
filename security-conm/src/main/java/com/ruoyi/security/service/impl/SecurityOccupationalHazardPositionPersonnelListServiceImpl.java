package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityOccupationalHazardPositionPersonnelListMapper;
import com.ruoyi.security.domain.SecurityOccupationalHazardPositionPersonnelList;
import com.ruoyi.security.service.ISecurityOccupationalHazardPositionPersonnelListService;

/**
 * 危险职业岗位人员清单及管理台帐Service业务层处理
 * 
 * @author wang
 * @date 2025-03-05
 */
@Service
public class SecurityOccupationalHazardPositionPersonnelListServiceImpl implements ISecurityOccupationalHazardPositionPersonnelListService 
{
    @Autowired
    private SecurityOccupationalHazardPositionPersonnelListMapper securityOccupationalHazardPositionPersonnelListMapper;

    /**
     * 查询危险职业岗位人员清单及管理台帐
     * 
     * @param id 危险职业岗位人员清单及管理台帐主键
     * @return 危险职业岗位人员清单及管理台帐
     */
    @Override
    public SecurityOccupationalHazardPositionPersonnelList selectSecurityOccupationalHazardPositionPersonnelListById(Long id)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.selectSecurityOccupationalHazardPositionPersonnelListById(id);
    }

    /**
     * 查询危险职业岗位人员清单及管理台帐列表
     * 
     * @param securityOccupationalHazardPositionPersonnelList 危险职业岗位人员清单及管理台帐
     * @return 危险职业岗位人员清单及管理台帐
     */
    @Override
    public List<SecurityOccupationalHazardPositionPersonnelList> selectSecurityOccupationalHazardPositionPersonnelListList(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.selectSecurityOccupationalHazardPositionPersonnelListList(securityOccupationalHazardPositionPersonnelList);
    }

    /**
     * 新增危险职业岗位人员清单及管理台帐
     * 
     * @param securityOccupationalHazardPositionPersonnelList 危险职业岗位人员清单及管理台帐
     * @return 结果
     */
    @Override
    public int insertSecurityOccupationalHazardPositionPersonnelList(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.insertSecurityOccupationalHazardPositionPersonnelList(securityOccupationalHazardPositionPersonnelList);
    }

    /**
     * 修改危险职业岗位人员清单及管理台帐
     * 
     * @param securityOccupationalHazardPositionPersonnelList 危险职业岗位人员清单及管理台帐
     * @return 结果
     */
    @Override
    public int updateSecurityOccupationalHazardPositionPersonnelList(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.updateSecurityOccupationalHazardPositionPersonnelList(securityOccupationalHazardPositionPersonnelList);
    }

    /**
     * 批量删除危险职业岗位人员清单及管理台帐
     * 
     * @param ids 需要删除的危险职业岗位人员清单及管理台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHazardPositionPersonnelListByIds(Long[] ids)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.deleteSecurityOccupationalHazardPositionPersonnelListByIds(ids);
    }

    /**
     * 删除危险职业岗位人员清单及管理台帐信息
     * 
     * @param id 危险职业岗位人员清单及管理台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHazardPositionPersonnelListById(Long id)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.deleteSecurityOccupationalHazardPositionPersonnelListById(id);
    }
}
