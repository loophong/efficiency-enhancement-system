package com.ruoyi.security.service.impl;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.security.domain.SecurityHazardousChemicalInventory;
import com.ruoyi.security.listener.HazardousChemicalInventoryListener;
import com.ruoyi.security.listener.SecurityObsoleteControlledDocumentDisposalRegisterListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityObsoleteControlledDocumentDisposalRegisterMapper;
import com.ruoyi.security.domain.SecurityObsoleteControlledDocumentDisposalRegister;
import com.ruoyi.security.service.ISecurityObsoleteControlledDocumentDisposalRegisterService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 作废受控文件收回销毁登记Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Slf4j
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
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);

            try {
                EasyExcel.read(excelFile.getInputStream(), SecurityObsoleteControlledDocumentDisposalRegister.class,
                        new SecurityObsoleteControlledDocumentDisposalRegisterListener(securityObsoleteControlledDocumentDisposalRegisterMapper)).headRowNumber(3).sheet().doRead();

                log.info("读取文件成功: {}", fileName);

            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }

//            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
//            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }
}
