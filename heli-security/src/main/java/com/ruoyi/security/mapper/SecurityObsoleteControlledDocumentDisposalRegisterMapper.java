package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityObsoleteControlledDocumentDisposalRegister;

/**
 * 作废受控文件收回销毁登记Mapper接口
 *
 * @author wang
 * @date 2025-03-02
 */
public interface SecurityObsoleteControlledDocumentDisposalRegisterMapper
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
     * 删除作废受控文件收回销毁登记
     *
     * @param id 作废受控文件收回销毁登记主键
     * @return 结果
     */
    public int deleteSecurityObsoleteControlledDocumentDisposalRegisterById(Long id);

    /**
     * 批量删除作废受控文件收回销毁登记
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityObsoleteControlledDocumentDisposalRegisterByIds(Long[] ids);

    /**
     * 查询最近导入的记录（未关联ID的记录）
     *
     * @return 作废受控文件收回销毁登记集合
     */
    public List<SecurityObsoleteControlledDocumentDisposalRegister> selectLatestImportedRecords();

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);

    void batchInsert(List<SecurityObsoleteControlledDocumentDisposalRegister> List);
}
