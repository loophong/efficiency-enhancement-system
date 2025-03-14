package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SupplierTwoReviewScoreServiceImpl extends ServiceImpl<SupplierTwoReviewScoreMapper, SupplierTwoReviewScore> implements ISupplierTwoReviewScoreService
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
        // 调用 calculateScore 方法计算分数
        Long score = calculateScore(supplierTwoReviewScore);
        supplierTwoReviewScore.setScore(score); // 设置计算后的得分

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
        // 调用 calculateScore 方法计算分数
        Long score = calculateScore(supplierTwoReviewScore);
        supplierTwoReviewScore.setScore(score); // 设置计算后的得分

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

    /**
     * 计算得分，根据不符合项的数量决定得分
     *
     * @param supplierTwoReviewScore 二方审核得分
     * @return 计算后的得分
     */
    private Long calculateScore(SupplierTwoReviewScore supplierTwoReviewScore) {
        // 计算不符合项的数量
        int notTrueCount = 0;

        if (supplierTwoReviewScore.getNotTrue1() != null && !supplierTwoReviewScore.getNotTrue1().isEmpty()) {
            notTrueCount++;
        }
        if (supplierTwoReviewScore.getNotTrue2() != null && !supplierTwoReviewScore.getNotTrue2().isEmpty()) {
            notTrueCount++;
        }
        if (supplierTwoReviewScore.getNotTrue3() != null && !supplierTwoReviewScore.getNotTrue3().isEmpty()) {
            notTrueCount++;
        }
        if (supplierTwoReviewScore.getNotTrue4() != null && !supplierTwoReviewScore.getNotTrue4().isEmpty()) {
            notTrueCount++;
        }
        if (supplierTwoReviewScore.getNotTrue5() != null && !supplierTwoReviewScore.getNotTrue5().isEmpty()) {
            notTrueCount++;
        }

        // 根据不符合项数量来计算得分
        if (notTrueCount > 3) {
            return 0L; // 不符合项超过3条，得分为0
        } else {
            return 1L; // 不符合项不超过3条，得分为1
        }
    }

}
