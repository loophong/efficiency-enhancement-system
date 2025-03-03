package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecutityLegalRegulationsIdentificationListMapper;
import com.ruoyi.security.domain.SecutityLegalRegulationsIdentificationList;
import com.ruoyi.security.service.ISecutityLegalRegulationsIdentificationListService;

/**
 * 安全/环境法律法规识别清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-02
 */
@Service
public class SecutityLegalRegulationsIdentificationListServiceImpl implements ISecutityLegalRegulationsIdentificationListService 
{
    @Autowired
    private SecutityLegalRegulationsIdentificationListMapper secutityLegalRegulationsIdentificationListMapper;

    /**
     * 查询安全/环境法律法规识别清单
     * 
     * @param id 安全/环境法律法规识别清单主键
     * @return 安全/环境法律法规识别清单
     */
    @Override
    public SecutityLegalRegulationsIdentificationList selectSecutityLegalRegulationsIdentificationListById(Long id)
    {
        return secutityLegalRegulationsIdentificationListMapper.selectSecutityLegalRegulationsIdentificationListById(id);
    }

    /**
     * 查询安全/环境法律法规识别清单列表
     * 
     * @param secutityLegalRegulationsIdentificationList 安全/环境法律法规识别清单
     * @return 安全/环境法律法规识别清单
     */
    @Override
    public List<SecutityLegalRegulationsIdentificationList> selectSecutityLegalRegulationsIdentificationListList(SecutityLegalRegulationsIdentificationList secutityLegalRegulationsIdentificationList)
    {
        return secutityLegalRegulationsIdentificationListMapper.selectSecutityLegalRegulationsIdentificationListList(secutityLegalRegulationsIdentificationList);
    }

    /**
     * 新增安全/环境法律法规识别清单
     * 
     * @param secutityLegalRegulationsIdentificationList 安全/环境法律法规识别清单
     * @return 结果
     */
    @Override
    public int insertSecutityLegalRegulationsIdentificationList(SecutityLegalRegulationsIdentificationList secutityLegalRegulationsIdentificationList)
    {
        return secutityLegalRegulationsIdentificationListMapper.insertSecutityLegalRegulationsIdentificationList(secutityLegalRegulationsIdentificationList);
    }

    /**
     * 修改安全/环境法律法规识别清单
     * 
     * @param secutityLegalRegulationsIdentificationList 安全/环境法律法规识别清单
     * @return 结果
     */
    @Override
    public int updateSecutityLegalRegulationsIdentificationList(SecutityLegalRegulationsIdentificationList secutityLegalRegulationsIdentificationList)
    {
        return secutityLegalRegulationsIdentificationListMapper.updateSecutityLegalRegulationsIdentificationList(secutityLegalRegulationsIdentificationList);
    }

    /**
     * 批量删除安全/环境法律法规识别清单
     * 
     * @param ids 需要删除的安全/环境法律法规识别清单主键
     * @return 结果
     */
    @Override
    public int deleteSecutityLegalRegulationsIdentificationListByIds(Long[] ids)
    {
        return secutityLegalRegulationsIdentificationListMapper.deleteSecutityLegalRegulationsIdentificationListByIds(ids);
    }

    /**
     * 删除安全/环境法律法规识别清单信息
     * 
     * @param id 安全/环境法律法规识别清单主键
     * @return 结果
     */
    @Override
    public int deleteSecutityLegalRegulationsIdentificationListById(Long id)
    {
        return secutityLegalRegulationsIdentificationListMapper.deleteSecutityLegalRegulationsIdentificationListById(id);
    }
}
