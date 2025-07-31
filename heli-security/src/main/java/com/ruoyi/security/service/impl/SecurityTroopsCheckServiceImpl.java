package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityTroopsCheckMapper;
import com.ruoyi.security.domain.SecurityTroopsCheck;
import com.ruoyi.security.service.ISecurityTroopsCheckService;

/**
 * 领导干部带队检查记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@Service
public class SecurityTroopsCheckServiceImpl implements ISecurityTroopsCheckService 
{
    @Autowired
    private SecurityTroopsCheckMapper securityTroopsCheckMapper;

    /**
     * 查询领导干部带队检查记录
     * 
     * @param id 领导干部带队检查记录主键
     * @return 领导干部带队检查记录
     */
    @Override
    public SecurityTroopsCheck selectSecurityTroopsCheckById(String id)
    {
        return securityTroopsCheckMapper.selectSecurityTroopsCheckById(id);
    }

    /**
     * 查询领导干部带队检查记录列表
     * 
     * @param securityTroopsCheck 领导干部带队检查记录
     * @return 领导干部带队检查记录
     */
    @Override
    public List<SecurityTroopsCheck> selectSecurityTroopsCheckList(SecurityTroopsCheck securityTroopsCheck)
    {
        return securityTroopsCheckMapper.selectSecurityTroopsCheckList(securityTroopsCheck);
    }

    /**
     * 新增领导干部带队检查记录
     * 
     * @param securityTroopsCheck 领导干部带队检查记录
     * @return 结果
     */
    @Override
    public int insertSecurityTroopsCheck(SecurityTroopsCheck securityTroopsCheck)
    {
        return securityTroopsCheckMapper.insertSecurityTroopsCheck(securityTroopsCheck);
    }

    /**
     * 修改领导干部带队检查记录
     * 
     * @param securityTroopsCheck 领导干部带队检查记录
     * @return 结果
     */
    @Override
    public int updateSecurityTroopsCheck(SecurityTroopsCheck securityTroopsCheck)
    {
        return securityTroopsCheckMapper.updateSecurityTroopsCheck(securityTroopsCheck);
    }

    /**
     * 批量删除领导干部带队检查记录
     * 
     * @param ids 需要删除的领导干部带队检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityTroopsCheckByIds(String[] ids)
    {
        return securityTroopsCheckMapper.deleteSecurityTroopsCheckByIds(ids);
    }

    /**
     * 删除领导干部带队检查记录信息
     * 
     * @param id 领导干部带队检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityTroopsCheckById(String id)
    {
        return securityTroopsCheckMapper.deleteSecurityTroopsCheckById(id);
    }
}
