package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityUploadfile;

/**
 * 文件预览Mapper接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface SecurityUploadfileMapper 
{
    /**
     * 查询文件预览
     * 
     * @param id 文件预览主键
     * @return 文件预览
     */
    public SecurityUploadfile selectSecurityUploadfileById(Long id);

    /**
     * 查询文件预览列表
     * 
     * @param securityUploadfile 文件预览
     * @return 文件预览集合
     */
    public List<SecurityUploadfile> selectSecurityUploadfileList(SecurityUploadfile securityUploadfile);

    /**
     * 新增文件预览
     * 
     * @param securityUploadfile 文件预览
     * @return 结果
     */
    public int insertSecurityUploadfile(SecurityUploadfile securityUploadfile);

    /**
     * 修改文件预览
     * 
     * @param securityUploadfile 文件预览
     * @return 结果
     */
    public int updateSecurityUploadfile(SecurityUploadfile securityUploadfile);

    /**
     * 删除文件预览
     * 
     * @param id 文件预览主键
     * @return 结果
     */
    public int deleteSecurityUploadfileById(Long id);

    /**
     * 批量删除文件预览
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityUploadfileByIds(Long[] ids);
}
