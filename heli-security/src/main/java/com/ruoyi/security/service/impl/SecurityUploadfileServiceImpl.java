package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityUploadfileMapper;
import com.ruoyi.security.domain.SecurityUploadfile;
import com.ruoyi.security.service.ISecurityUploadfileService;

/**
 * 文件预览Service业务层处理
 * 
 * @author wang
 * @date 2025-03-26
 */
@Service
public class SecurityUploadfileServiceImpl implements ISecurityUploadfileService 
{
    @Autowired
    private SecurityUploadfileMapper securityUploadfileMapper;

    /**
     * 查询文件预览
     * 
     * @param id 文件预览主键
     * @return 文件预览
     */
    @Override
    public SecurityUploadfile selectSecurityUploadfileById(Long id)
    {
        return securityUploadfileMapper.selectSecurityUploadfileById(id);
    }

    /**
     * 查询文件预览列表
     * 
     * @param securityUploadfile 文件预览
     * @return 文件预览
     */
    @Override
    public List<SecurityUploadfile> selectSecurityUploadfileList(SecurityUploadfile securityUploadfile)
    {
        return securityUploadfileMapper.selectSecurityUploadfileList(securityUploadfile);
    }

    /**
     * 新增文件预览
     * 
     * @param securityUploadfile 文件预览
     * @return 结果
     */
    @Override
    public int insertSecurityUploadfile(SecurityUploadfile securityUploadfile)
    {
        return securityUploadfileMapper.insertSecurityUploadfile(securityUploadfile);
    }

    /**
     * 修改文件预览
     * 
     * @param securityUploadfile 文件预览
     * @return 结果
     */
    @Override
    public int updateSecurityUploadfile(SecurityUploadfile securityUploadfile)
    {
        return securityUploadfileMapper.updateSecurityUploadfile(securityUploadfile);
    }

    /**
     * 批量删除文件预览
     * 
     * @param ids 需要删除的文件预览主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUploadfileByIds(Long[] ids)
    {
        return securityUploadfileMapper.deleteSecurityUploadfileByIds(ids);
    }

    /**
     * 删除文件预览信息
     * 
     * @param id 文件预览主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUploadfileById(Long id)
    {
        return securityUploadfileMapper.deleteSecurityUploadfileById(id);
    }
}
