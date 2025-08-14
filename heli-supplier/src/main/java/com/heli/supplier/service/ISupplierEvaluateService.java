package com.heli.supplier.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierEvaluate;

/**
 * 评分表Service接口
 * 
 * @author wll
 * @date 2025-03-13
 */
public interface ISupplierEvaluateService extends IService<SupplierEvaluate>
{
    /**
     * 查询评分表
     * 
     * @param id 评分表主键
     * @return 评分表
     */
    public SupplierEvaluate selectSupplierEvaluateById(String id);

    /**
     * 查询评分表列表
     * 
     * @param supplierEvaluate 评分表
     * @return 评分表集合
     */
    public List<SupplierEvaluate> selectSupplierEvaluateList(SupplierEvaluate supplierEvaluate);

    /**
     * 新增评分表
     * 
     * @param supplierEvaluate 评分表
     * @return 结果
     */
    public int insertSupplierEvaluate(SupplierEvaluate supplierEvaluate);

    /**
     * 修改评分表
     * 
     * @param supplierEvaluate 评分表
     * @return 结果
     */
    public int updateSupplierEvaluate(SupplierEvaluate supplierEvaluate);

    /**
     * 批量删除评分表
     * 
     * @param ids 需要删除的评分表主键集合
     * @return 结果
     */
    public int deleteSupplierEvaluateByIds(String[] ids);

    /**
     * 删除评分表信息
     * 
     * @param id 评分表主键
     * @return 结果
     */
    public int deleteSupplierEvaluateById(String id);

    int calculateScore(Date happenTime, Date endTime);

//
//    /**
//     * 按时间段删除评分表数据
//     *
//     * @param happenTime 开始时间
//     * @param endTime    结束时间
//     * @return 删除的记录数
//     */
//    boolean deleteSupplierEvaluateByTimeRange(Date happenTime, Date endTime);

}
