package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRegulationListMapper;
import com.ruoyi.security.domain.SecurityRegulationList;
import com.ruoyi.security.service.ISecurityRegulationListService;

/**
 * 安全制度清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityRegulationListServiceImpl implements ISecurityRegulationListService 
{
    @Autowired
    private SecurityRegulationListMapper securityRegulationListMapper;

    /**
     * 查询安全制度清单
     * 
     * @param id 安全制度清单主键
     * @return 安全制度清单
     */
    @Override
    public SecurityRegulationList selectSecurityRegulationListById(Long id)
    {
        return securityRegulationListMapper.selectSecurityRegulationListById(id);
    }

    /**
     * 查询安全制度清单列表
     * 
     * @param securityRegulationList 安全制度清单
     * @return 安全制度清单
     */
    @Override
    public List<SecurityRegulationList> selectSecurityRegulationListList(SecurityRegulationList securityRegulationList)
    {
        return securityRegulationListMapper.selectSecurityRegulationListList(securityRegulationList);
    }

    /**
     * 新增安全制度清单
     * 
     * @param securityRegulationList 安全制度清单
     * @return 结果
     */
    @Override
    public int insertSecurityRegulationList(SecurityRegulationList securityRegulationList)
    {
        return securityRegulationListMapper.insertSecurityRegulationList(securityRegulationList);
    }

    /**
     * 修改安全制度清单
     * 
     * @param securityRegulationList 安全制度清单
     * @return 结果
     */
    @Override
    public int updateSecurityRegulationList(SecurityRegulationList securityRegulationList)
    {
        return securityRegulationListMapper.updateSecurityRegulationList(securityRegulationList);
    }

    /**
     * 批量删除安全制度清单
     * 
     * @param ids 需要删除的安全制度清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRegulationListByIds(Long[] ids)
    {
        return securityRegulationListMapper.deleteSecurityRegulationListByIds(ids);
    }

    /**
     * 删除安全制度清单信息
     * 
     * @param id 安全制度清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRegulationListById(Long id)
    {
        return securityRegulationListMapper.deleteSecurityRegulationListById(id);
    }
}
