package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityPeopleListMapper;
import com.ruoyi.security.domain.SecurityPeopleList;
import com.ruoyi.security.service.ISecurityPeopleListService;

/**
 * 班组人员清单Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@Service
public class SecurityPeopleListServiceImpl implements ISecurityPeopleListService 
{
    @Autowired
    private SecurityPeopleListMapper securityPeopleListMapper;

    /**
     * 查询班组人员清单
     * 
     * @param id 班组人员清单主键
     * @return 班组人员清单
     */
    @Override
    public SecurityPeopleList selectSecurityPeopleListById(String id)
    {
        return securityPeopleListMapper.selectSecurityPeopleListById(id);
    }

    /**
     * 查询班组人员清单列表
     * 
     * @param securityPeopleList 班组人员清单
     * @return 班组人员清单
     */
    @Override
    public List<SecurityPeopleList> selectSecurityPeopleListList(SecurityPeopleList securityPeopleList)
    {
        return securityPeopleListMapper.selectSecurityPeopleListList(securityPeopleList);
    }

    /**
     * 新增班组人员清单
     * 
     * @param securityPeopleList 班组人员清单
     * @return 结果
     */
    @Override
    public int insertSecurityPeopleList(SecurityPeopleList securityPeopleList)
    {
        return securityPeopleListMapper.insertSecurityPeopleList(securityPeopleList);
    }

    /**
     * 修改班组人员清单
     * 
     * @param securityPeopleList 班组人员清单
     * @return 结果
     */
    @Override
    public int updateSecurityPeopleList(SecurityPeopleList securityPeopleList)
    {
        return securityPeopleListMapper.updateSecurityPeopleList(securityPeopleList);
    }

    /**
     * 批量删除班组人员清单
     * 
     * @param ids 需要删除的班组人员清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityPeopleListByIds(String[] ids)
    {
        return securityPeopleListMapper.deleteSecurityPeopleListByIds(ids);
    }

    /**
     * 删除班组人员清单信息
     * 
     * @param id 班组人员清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityPeopleListById(String id)
    {
        return securityPeopleListMapper.deleteSecurityPeopleListById(id);
    }
}
