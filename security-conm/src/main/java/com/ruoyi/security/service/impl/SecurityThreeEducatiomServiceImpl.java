package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityThreeEducatiomMapper;
import com.ruoyi.security.domain.SecurityThreeEducatiom;
import com.ruoyi.security.service.ISecurityThreeEducatiomService;

/**
 * 新职工三级安全教育台账Service业务层处理
 * 
 * @author wang
 * @date 2025-03-28
 */
@Service
public class SecurityThreeEducatiomServiceImpl implements ISecurityThreeEducatiomService 
{
    @Autowired
    private SecurityThreeEducatiomMapper securityThreeEducatiomMapper;

    /**
     * 查询新职工三级安全教育台账
     * 
     * @param id 新职工三级安全教育台账主键
     * @return 新职工三级安全教育台账
     */
    @Override
    public SecurityThreeEducatiom selectSecurityThreeEducatiomById(String id)
    {
        return securityThreeEducatiomMapper.selectSecurityThreeEducatiomById(id);
    }

    /**
     * 查询新职工三级安全教育台账列表
     * 
     * @param securityThreeEducatiom 新职工三级安全教育台账
     * @return 新职工三级安全教育台账
     */
    @Override
    public List<SecurityThreeEducatiom> selectSecurityThreeEducatiomList(SecurityThreeEducatiom securityThreeEducatiom)
    {
        return securityThreeEducatiomMapper.selectSecurityThreeEducatiomList(securityThreeEducatiom);
    }

    /**
     * 新增新职工三级安全教育台账
     * 
     * @param securityThreeEducatiom 新职工三级安全教育台账
     * @return 结果
     */
    @Override
    public int insertSecurityThreeEducatiom(SecurityThreeEducatiom securityThreeEducatiom)
    {
        return securityThreeEducatiomMapper.insertSecurityThreeEducatiom(securityThreeEducatiom);
    }

    /**
     * 修改新职工三级安全教育台账
     * 
     * @param securityThreeEducatiom 新职工三级安全教育台账
     * @return 结果
     */
    @Override
    public int updateSecurityThreeEducatiom(SecurityThreeEducatiom securityThreeEducatiom)
    {
        return securityThreeEducatiomMapper.updateSecurityThreeEducatiom(securityThreeEducatiom);
    }

    /**
     * 批量删除新职工三级安全教育台账
     * 
     * @param ids 需要删除的新职工三级安全教育台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityThreeEducatiomByIds(String[] ids)
    {
        return securityThreeEducatiomMapper.deleteSecurityThreeEducatiomByIds(ids);
    }

    /**
     * 删除新职工三级安全教育台账信息
     * 
     * @param id 新职工三级安全教育台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityThreeEducatiomById(String id)
    {
        return securityThreeEducatiomMapper.deleteSecurityThreeEducatiomById(id);
    }
}
