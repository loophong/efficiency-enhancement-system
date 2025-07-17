package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecutityLegalRegulationsIdentificationList1;
import org.springframework.web.multipart.MultipartFile;

/**
 * 安全法律法规识别清单Service接口
 * 
 * @author wang
 * @date 2025-04-11
 */
public interface ISecutityLegalRegulationsIdentificationList1Service 
{
    /**
     * 查询安全法律法规识别清单
     * 
     * @param id 安全法律法规识别清单主键
     * @return 安全法律法规识别清单
     */
    public SecutityLegalRegulationsIdentificationList1 selectSecutityLegalRegulationsIdentificationList1ById(Long id);

    /**
     * 查询安全法律法规识别清单列表
     * 
     * @param secutityLegalRegulationsIdentificationList1 安全法律法规识别清单
     * @return 安全法律法规识别清单集合
     */
    public List<SecutityLegalRegulationsIdentificationList1> selectSecutityLegalRegulationsIdentificationList1List(SecutityLegalRegulationsIdentificationList1 secutityLegalRegulationsIdentificationList1);

    /**
     * 新增安全法律法规识别清单
     * 
     * @param secutityLegalRegulationsIdentificationList1 安全法律法规识别清单
     * @return 结果
     */
    public int insertSecutityLegalRegulationsIdentificationList1(SecutityLegalRegulationsIdentificationList1 secutityLegalRegulationsIdentificationList1);

    /**
     * 修改安全法律法规识别清单
     * 
     * @param secutityLegalRegulationsIdentificationList1 安全法律法规识别清单
     * @return 结果
     */
    public int updateSecutityLegalRegulationsIdentificationList1(SecutityLegalRegulationsIdentificationList1 secutityLegalRegulationsIdentificationList1);

    /**
     * 批量删除安全法律法规识别清单
     * 
     * @param ids 需要删除的安全法律法规识别清单主键集合
     * @return 结果
     */
    public int deleteSecutityLegalRegulationsIdentificationList1ByIds(Long[] ids);

    /**
     * 删除安全法律法规识别清单信息
     * 
     * @param id 安全法律法规识别清单主键
     * @return 结果
     */
    public int deleteSecutityLegalRegulationsIdentificationList1ById(Long id);
    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile);
    
    /**
     * 更新最近导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
