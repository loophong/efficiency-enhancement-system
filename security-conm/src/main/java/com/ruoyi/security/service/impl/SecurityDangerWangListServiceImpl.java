package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityDangerWangListMapper;
import com.ruoyi.security.domain.SecurityDangerWangList;
import com.ruoyi.security.service.ISecurityDangerWangListService;

/**
 * 风险网格化清单Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@Service
public class SecurityDangerWangListServiceImpl implements ISecurityDangerWangListService 
{
    @Autowired
    private SecurityDangerWangListMapper securityDangerWangListMapper;

    /**
     * 查询风险网格化清单
     * 
     * @param id 风险网格化清单主键
     * @return 风险网格化清单
     */
    @Override
    public SecurityDangerWangList selectSecurityDangerWangListById(String id)
    {
        return securityDangerWangListMapper.selectSecurityDangerWangListById(id);
    }

    /**
     * 查询风险网格化清单列表
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 风险网格化清单
     */
    @Override
    public List<SecurityDangerWangList> selectSecurityDangerWangListList(SecurityDangerWangList securityDangerWangList)
    {
        return securityDangerWangListMapper.selectSecurityDangerWangListList(securityDangerWangList);
    }

    /**
     * 新增风险网格化清单
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 结果
     */
    @Override
    public int insertSecurityDangerWangList(SecurityDangerWangList securityDangerWangList)
    {
        return securityDangerWangListMapper.insertSecurityDangerWangList(securityDangerWangList);
    }

    /**
     * 修改风险网格化清单
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 结果
     */
    @Override
    public int updateSecurityDangerWangList(SecurityDangerWangList securityDangerWangList)
    {
        return securityDangerWangListMapper.updateSecurityDangerWangList(securityDangerWangList);
    }

    /**
     * 批量删除风险网格化清单
     * 
     * @param ids 需要删除的风险网格化清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityDangerWangListByIds(String[] ids)
    {
        return securityDangerWangListMapper.deleteSecurityDangerWangListByIds(ids);
    }

    /**
     * 删除风险网格化清单信息
     * 
     * @param id 风险网格化清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityDangerWangListById(String id)
    {
        return securityDangerWangListMapper.deleteSecurityDangerWangListById(id);
    }
}
