package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityUploadfileListMapper;
import com.ruoyi.security.domain.SecurityUploadfileList;
import com.ruoyi.security.service.ISecurityUploadfileListService;

/**
 * 制度明细Service业务层处理
 * 
 * @author wang
 * @date 2025-03-26
 */
@Service
public class SecurityUploadfileListServiceImpl implements ISecurityUploadfileListService 
{
    @Autowired
    private SecurityUploadfileListMapper securityUploadfileListMapper;

    /**
     * 查询制度明细
     * 
     * @param id 制度明细主键
     * @return 制度明细
     */
    @Override
    public SecurityUploadfileList selectSecurityUploadfileListById(Long id)
    {
        return securityUploadfileListMapper.selectSecurityUploadfileListById(id);
    }

    /**
     * 查询制度明细列表
     * 
     * @param securityUploadfileList 制度明细
     * @return 制度明细
     */
    @Override
    public List<SecurityUploadfileList> selectSecurityUploadfileListList(SecurityUploadfileList securityUploadfileList)
    {
        return securityUploadfileListMapper.selectSecurityUploadfileListList(securityUploadfileList);
    }

    /**
     * 新增制度明细
     * 
     * @param securityUploadfileList 制度明细
     * @return 结果
     */
    @Override
    public int insertSecurityUploadfileList(SecurityUploadfileList securityUploadfileList)
    {
        return securityUploadfileListMapper.insertSecurityUploadfileList(securityUploadfileList);
    }

    /**
     * 修改制度明细
     * 
     * @param securityUploadfileList 制度明细
     * @return 结果
     */
    @Override
    public int updateSecurityUploadfileList(SecurityUploadfileList securityUploadfileList)
    {
        return securityUploadfileListMapper.updateSecurityUploadfileList(securityUploadfileList);
    }

    /**
     * 批量删除制度明细
     * 
     * @param ids 需要删除的制度明细主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUploadfileListByIds(Long[] ids)
    {
        return securityUploadfileListMapper.deleteSecurityUploadfileListByIds(ids);
    }

    /**
     * 删除制度明细信息
     * 
     * @param id 制度明细主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUploadfileListById(Long id)
    {
        return securityUploadfileListMapper.deleteSecurityUploadfileListById(id);
    }
}
