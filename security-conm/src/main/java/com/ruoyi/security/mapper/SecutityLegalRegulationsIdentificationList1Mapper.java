package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecutityLegalRegulationsIdentificationList1;

/**
 * 安全法律法规识别清单Mapper接口
 * 
 * @author wang
 * @date 2025-04-11
 */
public interface SecutityLegalRegulationsIdentificationList1Mapper 
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
     * 删除安全法律法规识别清单
     * 
     * @param id 安全法律法规识别清单主键
     * @return 结果
     */
    public int deleteSecutityLegalRegulationsIdentificationList1ById(Long id);

    /**
     * 批量删除安全法律法规识别清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecutityLegalRegulationsIdentificationList1ByIds(Long[] ids);
}
