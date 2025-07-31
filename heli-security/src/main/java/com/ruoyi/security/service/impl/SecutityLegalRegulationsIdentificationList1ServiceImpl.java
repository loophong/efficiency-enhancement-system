package com.ruoyi.security.service.impl;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.security.listener.SecutityLegalRegulationsIdentificationListListener1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecutityLegalRegulationsIdentificationList1Mapper;
import com.ruoyi.security.domain.SecutityLegalRegulationsIdentificationList1;
import com.ruoyi.security.service.ISecutityLegalRegulationsIdentificationList1Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 安全法律法规识别清单Service业务层处理
 * 
 * @author wang
 * @date 2025-04-11
 */
@Slf4j
@Service
public class SecutityLegalRegulationsIdentificationList1ServiceImpl implements ISecutityLegalRegulationsIdentificationList1Service 
{
    @Autowired
    private SecutityLegalRegulationsIdentificationList1Mapper secutityLegalRegulationsIdentificationList1Mapper;

    /**
     * 查询安全法律法规识别清单
     * 
     * @param id 安全法律法规识别清单主键
     * @return 安全法律法规识别清单
     */
    @Override
    public SecutityLegalRegulationsIdentificationList1 selectSecutityLegalRegulationsIdentificationList1ById(Long id)
    {
        return secutityLegalRegulationsIdentificationList1Mapper.selectSecutityLegalRegulationsIdentificationList1ById(id);
    }

    /**
     * 查询安全法律法规识别清单列表
     * 
     * @param secutityLegalRegulationsIdentificationList1 安全法律法规识别清单
     * @return 安全法律法规识别清单
     */
    @Override
    public List<SecutityLegalRegulationsIdentificationList1> selectSecutityLegalRegulationsIdentificationList1List(SecutityLegalRegulationsIdentificationList1 secutityLegalRegulationsIdentificationList1)
    {
        return secutityLegalRegulationsIdentificationList1Mapper.selectSecutityLegalRegulationsIdentificationList1List(secutityLegalRegulationsIdentificationList1);
    }

    /**
     * 新增安全法律法规识别清单
     * 
     * @param secutityLegalRegulationsIdentificationList1 安全法律法规识别清单
     * @return 结果
     */
    @Override
    public int insertSecutityLegalRegulationsIdentificationList1(SecutityLegalRegulationsIdentificationList1 secutityLegalRegulationsIdentificationList1)
    {
        return secutityLegalRegulationsIdentificationList1Mapper.insertSecutityLegalRegulationsIdentificationList1(secutityLegalRegulationsIdentificationList1);
    }

    /**
     * 修改安全法律法规识别清单
     * 
     * @param secutityLegalRegulationsIdentificationList1 安全法律法规识别清单
     * @return 结果
     */
    @Override
    public int updateSecutityLegalRegulationsIdentificationList1(SecutityLegalRegulationsIdentificationList1 secutityLegalRegulationsIdentificationList1)
    {
        return secutityLegalRegulationsIdentificationList1Mapper.updateSecutityLegalRegulationsIdentificationList1(secutityLegalRegulationsIdentificationList1);
    }

    /**
     * 批量删除安全法律法规识别清单
     * 
     * @param ids 需要删除的安全法律法规识别清单主键
     * @return 结果
     */
    @Override
    public int deleteSecutityLegalRegulationsIdentificationList1ByIds(Long[] ids)
    {
        return secutityLegalRegulationsIdentificationList1Mapper.deleteSecutityLegalRegulationsIdentificationList1ByIds(ids);
    }

    /**
     * 删除安全法律法规识别清单信息
     * 
     * @param id 安全法律法规识别清单主键
     * @return 结果
     */
    @Override
    public int deleteSecutityLegalRegulationsIdentificationList1ById(Long id)
    {
        return secutityLegalRegulationsIdentificationList1Mapper.deleteSecutityLegalRegulationsIdentificationList1ById(id);
    }

    public void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile) {
        try {
            log.info("开始读取文件: {}", originalFilename);
            try {
                EasyExcel.read(excelFile.getInputStream(), SecutityLegalRegulationsIdentificationList1.class,
                        new SecutityLegalRegulationsIdentificationListListener1(secutityLegalRegulationsIdentificationList1Mapper)).headRowNumber(6).sheet().doRead();
                log.info("读取文件成功: {}", originalFilename);
            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("读取 " + originalFilename + " 文件失败, 原因: {}", e.getMessage());
        }
    }
    
    /**
     * 更新最近导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId)
    {
        return secutityLegalRegulationsIdentificationList1Mapper.updateLatestImportedRelatedId(relatedId);
    }
}
