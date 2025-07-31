package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityShenpiBiaoMapper;
import com.ruoyi.security.domain.SecurityShenpiBiao;
import com.ruoyi.security.service.ISecurityShenpiBiaoService;

/**
 * 不符合、纠正措施和预防措施程序Service业务层处理
 * 
 * @author wang
 * @date 2025-07-29
 */
@Service
public class SecurityShenpiBiaoServiceImpl implements ISecurityShenpiBiaoService 
{
    @Autowired
    private SecurityShenpiBiaoMapper securityShenpiBiaoMapper;

    /**
     * 查询不符合、纠正措施和预防措施程序
     * 
     * @param id 不符合、纠正措施和预防措施程序主键
     * @return 不符合、纠正措施和预防措施程序
     */
    @Override
    public SecurityShenpiBiao selectSecurityShenpiBiaoById(String id)
    {
        return securityShenpiBiaoMapper.selectSecurityShenpiBiaoById(id);
    }

    /**
     * 查询不符合、纠正措施和预防措施程序列表
     * 
     * @param securityShenpiBiao 不符合、纠正措施和预防措施程序
     * @return 不符合、纠正措施和预防措施程序
     */
    @Override
    public List<SecurityShenpiBiao> selectSecurityShenpiBiaoList(SecurityShenpiBiao securityShenpiBiao)
    {
        return securityShenpiBiaoMapper.selectSecurityShenpiBiaoList(securityShenpiBiao);
    }

    /**
     * 新增不符合、纠正措施和预防措施程序
     * 
     * @param securityShenpiBiao 不符合、纠正措施和预防措施程序
     * @return 结果
     */
    @Override
    public int insertSecurityShenpiBiao(SecurityShenpiBiao securityShenpiBiao)
    {
        return securityShenpiBiaoMapper.insertSecurityShenpiBiao(securityShenpiBiao);
    }

    /**
     * 修改不符合、纠正措施和预防措施程序
     * 
     * @param securityShenpiBiao 不符合、纠正措施和预防措施程序
     * @return 结果
     */
    @Override
    public int updateSecurityShenpiBiao(SecurityShenpiBiao securityShenpiBiao)
    {
        return securityShenpiBiaoMapper.updateSecurityShenpiBiao(securityShenpiBiao);
    }

    /**
     * 批量删除不符合、纠正措施和预防措施程序
     * 
     * @param ids 需要删除的不符合、纠正措施和预防措施程序主键
     * @return 结果
     */
    @Override
    public int deleteSecurityShenpiBiaoByIds(String[] ids)
    {
        return securityShenpiBiaoMapper.deleteSecurityShenpiBiaoByIds(ids);
    }

    /**
     * 删除不符合、纠正措施和预防措施程序信息
     * 
     * @param id 不符合、纠正措施和预防措施程序主键
     * @return 结果
     */
    @Override
    public int deleteSecurityShenpiBiaoById(String id)
    {
        return securityShenpiBiaoMapper.deleteSecurityShenpiBiaoById(id);
    }
}
