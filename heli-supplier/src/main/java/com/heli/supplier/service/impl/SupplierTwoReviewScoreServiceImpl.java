package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierPriceTrust;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.listener.PriceTrustListener;
import com.heli.supplier.listener.TwoReviewScoreListener;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierTwoReviewScoreMapper;
import com.heli.supplier.domain.SupplierTwoReviewScore;
import com.heli.supplier.service.ISupplierTwoReviewScoreService;
import org.springframework.web.multipart.MultipartFile;

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
//        Long score = calculateScore(supplierTwoReviewScore);
//        supplierTwoReviewScore.setScore(score); // 设置计算后的得分

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
//        Long score = calculateScore(supplierTwoReviewScore);
//        supplierTwoReviewScore.setScore(score); // 设置计算后的得分

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


/*
      * 导入
 */
    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
//            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierTwoReviewScore.class,
                                new TwoReviewScoreListener(supplierTwoReviewScoreMapper,uploadMonth))
                        .sheet("二方审核得分")
                        .doRead();
                log.info("读取文件成功: {}", fileName);
            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());

        }
    }



    /**
     * 计算得分，根据不符合项的数量决定得分
     *
     * @param supplierTwoReviewScore 二方审核得分
     * @return 计算后的得分
     */
//    private Long calculateScore(SupplierTwoReviewScore supplierTwoReviewScore) {
//        // 计算不符合项的数量
//        int notTrueCount = 0;
//
////
//        // 根据不符合项数量来计算得分
//        if (notTrueCount > 3) {
//            return 0L; // 不符合项超过3条，得分为0
//        } else {
//            return 1L; // 不符合项不超过3条，得分为1
//        }
//    }

}
