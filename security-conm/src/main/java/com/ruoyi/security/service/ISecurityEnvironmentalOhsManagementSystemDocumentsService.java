package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOhsManagementSystemDocuments;
import org.springframework.web.multipart.MultipartFile;

/**
 * 环境职业健康安全管理体系文件清单Service接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface ISecurityEnvironmentalOhsManagementSystemDocumentsService 
{
    /**
     * 查询环境职业健康安全管理体系文件清单
     * 
     * @param id 环境职业健康安全管理体系文件清单主键
     * @return 环境职业健康安全管理体系文件清单
     */
    public SecurityEnvironmentalOhsManagementSystemDocuments selectSecurityEnvironmentalOhsManagementSystemDocumentsById(Long id);

    /**
     * 查询环境职业健康安全管理体系文件清单列表
     * 
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 环境职业健康安全管理体系文件清单集合
     */
    public List<SecurityEnvironmentalOhsManagementSystemDocuments> selectSecurityEnvironmentalOhsManagementSystemDocumentsList(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments);

    /**
     * 新增环境职业健康安全管理体系文件清单
     * 
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 结果
     */
    public int insertSecurityEnvironmentalOhsManagementSystemDocuments(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments);

    /**
     * 修改环境职业健康安全管理体系文件清单
     * 
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 结果
     */
    public int updateSecurityEnvironmentalOhsManagementSystemDocuments(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments);

    /**
     * 批量删除环境职业健康安全管理体系文件清单
     * 
     * @param ids 需要删除的环境职业健康安全管理体系文件清单主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOhsManagementSystemDocumentsByIds(Long[] ids);

    /**
     * 删除环境职业健康安全管理体系文件清单信息
     *
     * @param id 环境职业健康安全管理体系文件清单主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOhsManagementSystemDocumentsById(Long id);

    /**
     * 更新最近导入数据的关联ID
     *
     * @param fileManagementId 文件管理ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long fileManagementId);


    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile);
}
