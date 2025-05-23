package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecutityLegalRegulationsIdentificationList;
import org.springframework.web.multipart.MultipartFile;

/**
 * 安全/环境法律法规识别清单Service接口
 * 
 * @author wang
 * @date 2025-03-02
 */
public interface ISecutityLegalRegulationsIdentificationListService 
{
    /**
     * 查询安全/环境法律法规识别清单
     * 
     * @param id 安全/环境法律法规识别清单主键
     * @return 安全/环境法律法规识别清单
     */
    public SecutityLegalRegulationsIdentificationList selectSecutityLegalRegulationsIdentificationListById(Long id);

    /**
     * 查询安全/环境法律法规识别清单列表
     * 
     * @param secutityLegalRegulationsIdentificationList 安全/环境法律法规识别清单
     * @return 安全/环境法律法规识别清单集合
     */
    public List<SecutityLegalRegulationsIdentificationList> selectSecutityLegalRegulationsIdentificationListList(SecutityLegalRegulationsIdentificationList secutityLegalRegulationsIdentificationList);

    /**
     * 新增安全/环境法律法规识别清单
     * 
     * @param secutityLegalRegulationsIdentificationList 安全/环境法律法规识别清单
     * @return 结果
     */
    public int insertSecutityLegalRegulationsIdentificationList(SecutityLegalRegulationsIdentificationList secutityLegalRegulationsIdentificationList);

    /**
     * 修改安全/环境法律法规识别清单
     * 
     * @param secutityLegalRegulationsIdentificationList 安全/环境法律法规识别清单
     * @return 结果
     */
    public int updateSecutityLegalRegulationsIdentificationList(SecutityLegalRegulationsIdentificationList secutityLegalRegulationsIdentificationList);

    /**
     * 批量删除安全/环境法律法规识别清单
     * 
     * @param ids 需要删除的安全/环境法律法规识别清单主键集合
     * @return 结果
     */
    public int deleteSecutityLegalRegulationsIdentificationListByIds(Long[] ids);

    /**
     * 删除安全/环境法律法规识别清单信息
     * 
     * @param id 安全/环境法律法规识别清单主键
     * @return 结果
     */
    public int deleteSecutityLegalRegulationsIdentificationListById(Long id);
    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile);
}
