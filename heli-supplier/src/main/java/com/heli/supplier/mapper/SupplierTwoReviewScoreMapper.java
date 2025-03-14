package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierTwoReviewScore;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 二方审核得分Mapper接口
 * 
 * @author wll
 * @date 2025-03-06
 */
public interface SupplierTwoReviewScoreMapper extends BaseMapper<SupplierTwoReviewScore>
{
    /**
     * 查询二方审核得分
     * 
     * @param id 二方审核得分主键
     * @return 二方审核得分
     */
    public SupplierTwoReviewScore selectSupplierTwoReviewScoreById(String id);

    /**
     * 查询二方审核得分列表
     * 
     * @param supplierTwoReviewScore 二方审核得分
     * @return 二方审核得分集合
     */
    public List<SupplierTwoReviewScore> selectSupplierTwoReviewScoreList(SupplierTwoReviewScore supplierTwoReviewScore);

    /**
     * 新增二方审核得分
     * 
     * @param supplierTwoReviewScore 二方审核得分
     * @return 结果
     */
    public int insertSupplierTwoReviewScore(SupplierTwoReviewScore supplierTwoReviewScore);

    /**
     * 修改二方审核得分
     * 
     * @param supplierTwoReviewScore 二方审核得分
     * @return 结果
     */
    public int updateSupplierTwoReviewScore(SupplierTwoReviewScore supplierTwoReviewScore);

    /**
     * 删除二方审核得分
     * 
     * @param id 二方审核得分主键
     * @return 结果
     */
    public int deleteSupplierTwoReviewScoreById(String id);

    /**
     * 批量删除二方审核得分
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierTwoReviewScoreByIds(String[] ids);
}
