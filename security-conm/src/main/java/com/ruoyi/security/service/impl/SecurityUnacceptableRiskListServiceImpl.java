package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityUnacceptableRiskListMapper;
import com.ruoyi.security.domain.SecurityUnacceptableRiskList;
import com.ruoyi.security.service.ISecurityUnacceptableRiskListService;

/**
 * 不可接受风险清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-01
 */
@Service
public class SecurityUnacceptableRiskListServiceImpl implements ISecurityUnacceptableRiskListService 
{
    @Autowired
    private SecurityUnacceptableRiskListMapper securityUnacceptableRiskListMapper;

    /**
     * 查询不可接受风险清单
     * 
     * @param id 不可接受风险清单主键
     * @return 不可接受风险清单
     */
    @Override
    public SecurityUnacceptableRiskList selectSecurityUnacceptableRiskListById(Long id)
    {
        return securityUnacceptableRiskListMapper.selectSecurityUnacceptableRiskListById(id);
    }

    /**
     * 查询不可接受风险清单列表
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 不可接受风险清单
     */
    @Override
    public List<SecurityUnacceptableRiskList> selectSecurityUnacceptableRiskListList(SecurityUnacceptableRiskList securityUnacceptableRiskList)
    {
        return securityUnacceptableRiskListMapper.selectSecurityUnacceptableRiskListList(securityUnacceptableRiskList);
    }

    /**
     * 新增不可接受风险清单
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 结果
     */
    @Override
    public int insertSecurityUnacceptableRiskList(SecurityUnacceptableRiskList securityUnacceptableRiskList)
    {
        return securityUnacceptableRiskListMapper.insertSecurityUnacceptableRiskList(securityUnacceptableRiskList);
    }

    /**
     * 修改不可接受风险清单
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 结果
     */
    @Override
    public int updateSecurityUnacceptableRiskList(SecurityUnacceptableRiskList securityUnacceptableRiskList)
    {
        return securityUnacceptableRiskListMapper.updateSecurityUnacceptableRiskList(securityUnacceptableRiskList);
    }

    /**
     * 批量删除不可接受风险清单
     * 
     * @param ids 需要删除的不可接受风险清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUnacceptableRiskListByIds(Long[] ids)
    {
        return securityUnacceptableRiskListMapper.deleteSecurityUnacceptableRiskListByIds(ids);
    }

    /**
     * 删除不可接受风险清单信息
     * 
     * @param id 不可接受风险清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUnacceptableRiskListById(Long id)
    {
        return securityUnacceptableRiskListMapper.deleteSecurityUnacceptableRiskListById(id);
    }
}
