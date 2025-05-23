package com.ruoyi.security.service.impl;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.security.domain.SecurityHazardousChemicalInventory;
import com.ruoyi.security.listener.HazardousChemicalInventoryListener;
import com.ruoyi.security.listener.SecurityEnvironmentalOhsManagementSystemDocumentsListener;
import com.ruoyi.security.listener.SecurityEnvironmentalOhsRecordListListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOhsManagementSystemDocumentsMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOhsManagementSystemDocuments;
import com.ruoyi.security.service.ISecurityEnvironmentalOhsManagementSystemDocumentsService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 环境职业健康安全管理体系文件清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Slf4j
@Service
public class SecurityEnvironmentalOhsManagementSystemDocumentsServiceImpl implements ISecurityEnvironmentalOhsManagementSystemDocumentsService {
    @Autowired
    private SecurityEnvironmentalOhsManagementSystemDocumentsMapper securityEnvironmentalOhsManagementSystemDocumentsMapper;

    /**
     * 查询环境职业健康安全管理体系文件清单
     *
     * @param id 环境职业健康安全管理体系文件清单主键
     * @return 环境职业健康安全管理体系文件清单
     */
    @Override
    public SecurityEnvironmentalOhsManagementSystemDocuments selectSecurityEnvironmentalOhsManagementSystemDocumentsById(Long id) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.selectSecurityEnvironmentalOhsManagementSystemDocumentsById(id);
    }

    /**
     * 查询环境职业健康安全管理体系文件清单列表
     *
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 环境职业健康安全管理体系文件清单
     */
    @Override
    public List<SecurityEnvironmentalOhsManagementSystemDocuments> selectSecurityEnvironmentalOhsManagementSystemDocumentsList(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.selectSecurityEnvironmentalOhsManagementSystemDocumentsList(securityEnvironmentalOhsManagementSystemDocuments);
    }

    /**
     * 新增环境职业健康安全管理体系文件清单
     *
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOhsManagementSystemDocuments(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.insertSecurityEnvironmentalOhsManagementSystemDocuments(securityEnvironmentalOhsManagementSystemDocuments);
    }

    /**
     * 修改环境职业健康安全管理体系文件清单
     *
     * @param securityEnvironmentalOhsManagementSystemDocuments 环境职业健康安全管理体系文件清单
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOhsManagementSystemDocuments(SecurityEnvironmentalOhsManagementSystemDocuments securityEnvironmentalOhsManagementSystemDocuments) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.updateSecurityEnvironmentalOhsManagementSystemDocuments(securityEnvironmentalOhsManagementSystemDocuments);
    }

    /**
     * 批量删除环境职业健康安全管理体系文件清单
     *
     * @param ids 需要删除的环境职业健康安全管理体系文件清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOhsManagementSystemDocumentsByIds(Long[] ids) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.deleteSecurityEnvironmentalOhsManagementSystemDocumentsByIds(ids);
    }

    /**
     * 删除环境职业健康安全管理体系文件清单信息
     *
     * @param id 环境职业健康安全管理体系文件清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOhsManagementSystemDocumentsById(Long id) {
        return securityEnvironmentalOhsManagementSystemDocumentsMapper.deleteSecurityEnvironmentalOhsManagementSystemDocumentsById(id);
    }

    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);

            try {
                EasyExcel.read(excelFile.getInputStream(), SecurityEnvironmentalOhsManagementSystemDocuments.class,
                        new SecurityEnvironmentalOhsManagementSystemDocumentsListener(securityEnvironmentalOhsManagementSystemDocumentsMapper)).headRowNumber(3).sheet().doRead();

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


