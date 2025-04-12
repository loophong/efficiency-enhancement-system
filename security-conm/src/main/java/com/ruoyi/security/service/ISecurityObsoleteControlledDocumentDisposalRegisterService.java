package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityObsoleteControlledDocumentDisposalRegister;
import org.springframework.web.multipart.MultipartFile;

/**
 * 作废受控文件收回销毁登记Service接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface ISecurityObsoleteControlledDocumentDisposalRegisterService 
{
    /**
     * 查询作废受控文件收回销毁登记
     * 
     * @param id 作废受控文件收回销毁登记主键
     * @return 作废受控文件收回销毁登记
     */
    public SecurityObsoleteControlledDocumentDisposalRegister selectSecurityObsoleteControlledDocumentDisposalRegisterById(Long id);

    /**
     * 查询作废受控文件收回销毁登记列表
     * 
     * @param securityObsoleteControlledDocumentDisposalRegister 作废受控文件收回销毁登记
     * @return 作废受控文件收回销毁登记集合
     */
    public List<SecurityObsoleteControlledDocumentDisposalRegister> selectSecurityObsoleteControlledDocumentDisposalRegisterList(SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister);

    /**
     * 新增作废受控文件收回销毁登记
     * 
     * @param securityObsoleteControlledDocumentDisposalRegister 作废受控文件收回销毁登记
     * @return 结果
     */
    public int insertSecurityObsoleteControlledDocumentDisposalRegister(SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister);

    /**
     * 修改作废受控文件收回销毁登记
     * 
     * @param securityObsoleteControlledDocumentDisposalRegister 作废受控文件收回销毁登记
     * @return 结果
     */
    public int updateSecurityObsoleteControlledDocumentDisposalRegister(SecurityObsoleteControlledDocumentDisposalRegister securityObsoleteControlledDocumentDisposalRegister);

    /**
     * 批量删除作废受控文件收回销毁登记
     * 
     * @param ids 需要删除的作废受控文件收回销毁登记主键集合
     * @return 结果
     */
    public int deleteSecurityObsoleteControlledDocumentDisposalRegisterByIds(Long[] ids);

    /**
     * 删除作废受控文件收回销毁登记信息
     * 
     * @param id 作废受控文件收回销毁登记主键
     * @return 结果
     */
    public int deleteSecurityObsoleteControlledDocumentDisposalRegisterById(Long id);

    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile);
}
