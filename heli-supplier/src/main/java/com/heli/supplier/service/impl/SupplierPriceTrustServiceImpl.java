package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.listener.PriceTrustListener;
import com.heli.supplier.listener.RiskListener;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierPriceTrustMapper;
import com.heli.supplier.domain.SupplierPriceTrust;
import com.heli.supplier.service.ISupplierPriceTrustService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 价格诚信Service业务层处理
 * 
 * @author wll
 * @date 2025-02-28
 */
@Service
@Slf4j
public class SupplierPriceTrustServiceImpl  extends ServiceImpl<SupplierPriceTrustMapper, SupplierPriceTrust> implements ISupplierPriceTrustService
{
    @Autowired
    private SupplierPriceTrustMapper supplierPriceTrustMapper;

    /**
     * 查询价格诚信
     * 
     * @param id 价格诚信主键
     * @return 价格诚信
     */
    @Override
    public SupplierPriceTrust selectSupplierPriceTrustById(String id)
    {
        return supplierPriceTrustMapper.selectSupplierPriceTrustById(id);
    }

    /**
     * 查询价格诚信列表
     * 
     * @param supplierPriceTrust 价格诚信
     * @return 价格诚信
     */
    @Override
    public List<SupplierPriceTrust> selectSupplierPriceTrustList(SupplierPriceTrust supplierPriceTrust)
    {
        return supplierPriceTrustMapper.selectSupplierPriceTrustList(supplierPriceTrust);
    }

    /**
     * 新增价格诚信
     * 
     * @param supplierPriceTrust 价格诚信
     * @return 结果
     */
    @Override
    public int insertSupplierPriceTrust(SupplierPriceTrust supplierPriceTrust)
    {
//        // 根据供应商代码查询是否已存在该供应商
//        SupplierPriceTrust existingRecord = supplierPriceTrustMapper.selectBySupplierCode(supplierPriceTrust.getSupplierCode());
//
//        if (existingRecord != null) {
//            // 如果记录已存在，更新次数并计算新的分数
//            Long happenNumber = existingRecord.getHappenNumber() + 1;  // 发生次数+1
//            existingRecord.setHappenNumber(happenNumber);
//
//            // 计算新的分数
//            calculateAndSetScore(existingRecord);
//        } else {
//            // 如果记录不存在，则新增记录
//            supplierPriceTrust.setHappenNumber(1L);  // 设置首次发生次数为1
//            calculateAndSetScore(supplierPriceTrust); // 计算得分
//        }
        calculateAndSetScore(supplierPriceTrust);
        return supplierPriceTrustMapper.insertSupplierPriceTrust(supplierPriceTrust);
    }

    /**
     * 修改价格诚信
     * 
     * @param supplierPriceTrust 价格诚信
     * @return 结果
     */
    @Override
    public int updateSupplierPriceTrust(SupplierPriceTrust supplierPriceTrust)
    {
//        // 获取当前发生次数
//        Long happenNumber = supplierPriceTrust.getHappenNumber();
//
//        // 计算并设置得分
//        calculateAndSetScore(supplierPriceTrust);
//
//        // 更新数据
        calculateAndSetScore(supplierPriceTrust);
        return supplierPriceTrustMapper.updateSupplierPriceTrust(supplierPriceTrust);
    }

    /**
     * 批量删除价格诚信
     * 
     * @param ids 需要删除的价格诚信主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPriceTrustByIds(String[] ids)
    {
        return supplierPriceTrustMapper.deleteSupplierPriceTrustByIds(ids);
    }

    /**
     * 删除价格诚信信息
     * 
     * @param id 价格诚信主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPriceTrustById(String id)
    {
        return supplierPriceTrustMapper.deleteSupplierPriceTrustById(id);
    }
//    private void calculateAndSetScore(SupplierPriceTrust supplierPriceTrust) {
//        // 基础分为 100
//        double baseScore = 100.0;
//
//        // 每发生一次扣减 20 分
//        long happenNumber = supplierPriceTrust.getHappenNumber();
//        double score = baseScore - (happenNumber * 20);
//
//        // 模块得分为基础分的 5%
//        double moduleScore = baseScore * 0.05;
//
//        // 总分 = 模块得分
//        score = moduleScore;
//
//        // 设置得分
//        supplierPriceTrust.setScore(score);
//    }
    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierPriceTrust.class,
                                new PriceTrustListener(supplierPriceTrustMapper,uploadMonth))
                        .sheet("价格诚信")
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
     * 计算并设置价格诚信得分
     */
    private void calculateAndSetScore(SupplierPriceTrust supplierPriceTrust) {
        // 确保 happenNumber 不能为空
//        Long happenNumber = supplierPriceTrust.getHappenNumber();
//        if (supplierPriceTrust.getHappenNumber() == null) {
//            happenNumber = 0L;
//        }

        long happenNumber = (supplierPriceTrust.getHappenNumber() != null) ? supplierPriceTrust.getHappenNumber() : 0;
        // 计算基础得分
        double baseScore = 100.0;
        double finalScore = (baseScore - happenNumber * 20);

        // 确保得分不会低于 0
        supplierPriceTrust.setScore(Math.max(finalScore, 0));
    }



}
