package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityObsoleteControlledDocumentDisposalRegisterMapper;
import com.ruoyi.security.domain.SecurityObsoleteControlledDocumentDisposalRegister;
import com.ruoyi.security.service.ISecurityObsoleteControlledDocumentDisposalRegisterService;

/**
 * 作废受控文件收回销毁登记Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Service
public class SecurityObsoleteControlledDocumentDisposalRegisterServiceImpl implements ISecurityObsoleteControlledDocumentDisposalRegisterService 
{
    @Autowired
    private SecurityObsoleteControlledDocumentDisposalRegisterMapper securityObsoleteControlledDocumentDisposalRegisterMapper;

    /**
     * 查询作废受控文件收回销毁登记
     * 
     * @param id 作废受控文件收回销毁登记主键
     * @return 作废受控文件收回销毁登记
     */
    @Override
    public SecurityObsoleteControlledDocumentDisposalRegister selectSecurityObsoleteControlledDocumentDisposalRegisterById(Long id)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.selectSecurityObsoleteControlledDocumentDisposalRegisterById(id);
    }

    /**
     * 查询作废受控文件收回销毁登记列表
     * 
     * @param securityObsoleteControlledDocumentDisposalRegister 作废受控文件收回销毁登记
     * @return 作废受控文件收回销毁登记
     */
    @Override
    public List<SecurityObsoleteControlledDocumentDisposalRegister> selectSecurityObsoleteControlledDocumentDisposalRegisterList(SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.selectSecurityObsoleteControlledDocumentDisposalRegisterList(securityObsoleteControlledDocumentDisposalRegister);
    }

    /**
     * 新增作废受控文件收回销毁登记
     * 
     * @param securityObsoleteControlledDocumentDisposalRegister 作废受控文件收回销毁登记
     * @return 结果
     */
    @Override
    public int insertSecurityObsoleteControlledDocumentDisposalRegister(SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.insertSecurityObsoleteControlledDocumentDisposalRegister(securityObsoleteControlledDocumentDisposalRegister);
    }

    /**
     * 修改作废受控文件收回销毁登记
     * 
     * @param securityObsoleteControlledDocumentDisposalRegister 作废受控文件收回销毁登记
     * @return 结果
     */
    @Override
    public int updateSecurityObsoleteControlledDocumentDisposalRegister(SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.updateSecurityObsoleteControlledDocumentDisposalRegister(securityObsoleteControlledDocumentDisposalRegister);
    }

    /**
     * 批量删除作废受控文件收回销毁登记
     * 
     * @param ids 需要删除的作废受控文件收回销毁登记主键
     * @return 结果
     */
    @Override
    public int deleteSecurityObsoleteControlledDocumentDisposalRegisterByIds(Long[] ids)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.deleteSecurityObsoleteControlledDocumentDisposalRegisterByIds(ids);
    }

    /**
     * 删除作废受控文件收回销毁登记信息
     * 
     * @param id 作废受控文件收回销毁登记主键
     * @return 结果
     */
    @Override
    public int deleteSecurityObsoleteControlledDocumentDisposalRegisterById(Long id)
    {
        return securityObsoleteControlledDocumentDisposalRegisterMapper.deleteSecurityObsoleteControlledDocumentDisposalRegisterById(id);
    }
}
