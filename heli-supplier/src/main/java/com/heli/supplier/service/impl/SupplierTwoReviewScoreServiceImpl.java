package com.heli.supplier.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierTwoReviewScoreMapper;
import com.heli.supplier.domain.SupplierTwoReviewScore;
import com.heli.supplier.service.ISupplierTwoReviewScoreService;

/**
 * 二方审核得分Service业务层处理
 * 
 * @author wll
 * @date 2025-03-06
 */
@Service
public class SupplierTwoReviewScoreServiceImpl implements ISupplierTwoReviewScoreService 
{
    @Autowired
    private SupplierTwoReviewScoreMapper supplierTwoReviewScoreMapper;

    /**
     * 查询二方审核得分
     * 
     * @param id 二方审核得分主键
     * @return 二方审核得分
     */
    @Override
    public SupplierTwoReviewScore selectSupplierTwoReviewScoreById(String id)
    {
        return supplierTwoReviewScoreMapper.selectSupplierTwoReviewScoreById(id);
    }

    /**
     * 查询二方审核得分列表
     * 
     * @param supplierTwoReviewScore 二方审核得分
     * @return 二方审核得分
     */
    @Override
    public List<SupplierTwoReviewScore> selectSupplierTwoReviewScoreList(SupplierTwoReviewScore supplierTwoReviewScore)
    {
        return supplierTwoReviewScoreMapper.selectSupplierTwoReviewScoreList(supplierTwoReviewScore);
    }

    /**
     * 新增二方审核得分
     * 
     * @param supplierTwoReviewScore 二方审核得分
     * @return 结果
     */
    @Override
    public int insertSupplierTwoReviewScore(SupplierTwoReviewScore supplierTwoReviewScore)
    {
        return supplierTwoReviewScoreMapper.insertSupplierTwoReviewScore(supplierTwoReviewScore);
    }

    /**
     * 修改二方审核得分
     * 
     * @param supplierTwoReviewScore 二方审核得分
     * @return 结果
     */
    @Override
    public int updateSupplierTwoReviewScore(SupplierTwoReviewScore supplierTwoReviewScore)
    {
        return supplierTwoReviewScoreMapper.updateSupplierTwoReviewScore(supplierTwoReviewScore);
    }

    /**
     * 批量删除二方审核得分
     * 
     * @param ids 需要删除的二方审核得分主键
     * @return 结果
     */
    @Override
    public int deleteSupplierTwoReviewScoreByIds(String[] ids)
    {
        return supplierTwoReviewScoreMapper.deleteSupplierTwoReviewScoreByIds(ids);
    }

    /**
     * 删除二方审核得分信息
     * 
     * @param id 二方审核得分主键
     * @return 结果
     */
    @Override
    public int deleteSupplierTwoReviewScoreById(String id)
    {
        return supplierTwoReviewScoreMapper.deleteSupplierTwoReviewScoreById(id);
    }
}
