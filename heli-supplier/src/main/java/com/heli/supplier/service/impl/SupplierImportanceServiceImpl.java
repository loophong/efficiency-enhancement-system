package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.listener.ImportanceListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierImportanceMapper;
import com.heli.supplier.domain.SupplierImportance;
import com.heli.supplier.service.ISupplierImportanceService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 重要度Service业务层处理
 * 
 * @author wll
 * @date 2025-03-07
 */
@Service
@Slf4j
public class SupplierImportanceServiceImpl  extends ServiceImpl<SupplierImportanceMapper, SupplierImportance> implements ISupplierImportanceService
{
    @Autowired
    private SupplierImportanceMapper supplierImportanceMapper;

    /**
     * 查询重要度
     * 
     * @param id 重要度主键
     * @return 重要度
     */
    @Override
    public SupplierImportance selectSupplierImportanceById(String id)
    {
        return supplierImportanceMapper.selectSupplierImportanceById(id);
    }

    /**
     * 查询重要度列表
     * 
     * @param supplierImportance 重要度
     * @return 重要度
     */
    @Override
    public List<SupplierImportance> selectSupplierImportanceList(SupplierImportance supplierImportance)
    {
        return supplierImportanceMapper.selectSupplierImportanceList(supplierImportance);
    }

    /**
     * 新增重要度
     * 
     * @param supplierImportance 重要度
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSupplierImportance(SupplierImportance supplierImportance)
    {
        int result = supplierImportanceMapper.insertSupplierImportance(supplierImportance);
        // 插入后重新计算所有记录的分数
        recalculateAllScores();
        return result;
//        return supplierImportanceMapper.insertSupplierImportance(supplierImportance);
    }

    /**
     * 修改重要度
     * 
     * @param supplierImportance 重要度
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSupplierImportance(SupplierImportance supplierImportance)
    {
        int result = supplierImportanceMapper.updateSupplierImportance(supplierImportance);
        // 插入后重新计算所有记录的分数
        recalculateAllScores();
        return result;
//        return supplierImportanceMapper.updateSupplierImportance(supplierImportance);
    }

    /**
     * 批量删除重要度
     * 
     * @param ids 需要删除的重要度主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSupplierImportanceByIds(String[] ids)
    {
        int result = supplierImportanceMapper.deleteSupplierImportanceByIds(ids);
        // 删除后重新计算所有记录的分数
        recalculateAllScores();
        return result;
//        return supplierImportanceMapper.deleteSupplierImportanceByIds(ids);
    }

    /**
     * 删除重要度信息
     * 
     * @param id 重要度主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSupplierImportanceById(String id)
    {
        int result = supplierImportanceMapper.deleteSupplierImportanceById(id);
        // 删除后重新计算所有记录的分数
        recalculateAllScores();
        return result;
//        return supplierImportanceMapper.deleteSupplierImportanceById(id);
    }


    @Override
    @Transactional
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
//            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierImportance.class,
                                new ImportanceListener(supplierImportanceMapper,uploadMonth,this))
                        .sheet("重要度")
                        .doRead();
                log.info("读取文件成功: {}", fileName);

                // Excel导入完成后，重新计算所有记录的分数
                recalculateAllScores();

            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());

        }
    }


    /**
     * 重新计算所有供应商的分数
     */
    @Transactional
    public void recalculateAllScores() {
        log.info("开始重新计算所有供应商的分数");
        try {
            // 1. 获取所有供应商数据，按金额降序排序
            QueryWrapper<SupplierImportance> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("amount");
            List<SupplierImportance> allSuppliers = supplierImportanceMapper.selectList(queryWrapper);

            if (allSuppliers.isEmpty()) {
                log.info("没有供应商数据，无需计算分数");
                return;
            }

            int totalSuppliers = allSuppliers.size();
            log.info("共找到 {} 个供应商数据", totalSuppliers);

            // 2. 计算每个供应商的分数
            for (int i = 0; i < totalSuppliers; i++) {
                SupplierImportance supplier = allSuppliers.get(i);
                // 计算当前供应商的排名百分比
                double percentileRank = (double) (i + 1) / totalSuppliers;
                // 计算分数
                long score = calculateScore(percentileRank);

                // 更新分数
                supplier.setScore(score);
                supplierImportanceMapper.updateById(supplier);

                log.debug("供应商 [{}] 排名百分比: {}, 计算得分: {}",
                        supplier.getSupplierName(), percentileRank, score);
            }

            log.info("所有供应商分数计算完成");
        } catch (Exception e) {
            log.error("计算分数时发生错误: {}", e.getMessage());
            throw new RuntimeException("计算分数失败: " + e.getMessage());
        }
    }

    /**
     * 根据排名百分比计算分数
     * 分值计算规则：基础分100分，前10%排名得满分，排名每10%为一档，每低一档扣10分
     * 即排名11%-20%得90分，以此类推。模块得分为基础分的2%
     *
     * @param percentileRank 排名百分比 (0-1之间，越小表示排名越靠前)
     * @return 计算得分
     */
    private long calculateScore(double percentileRank) {
        // 基础分
        int baseScore;

        // 根据排名百分比确定基础分
        if (percentileRank <= 0.1) {
            baseScore = 100; // 前10%得满分
        } else if (percentileRank <= 0.2) {
            baseScore = 90;  // 11%-20%得90分
        } else if (percentileRank <= 0.3) {
            baseScore = 80;  // 21%-30%得80分
        } else if (percentileRank <= 0.4) {
            baseScore = 70;  // 31%-40%得70分
        } else if (percentileRank <= 0.5) {
            baseScore = 60;  // 41%-50%得60分
        } else if (percentileRank <= 0.6) {
            baseScore = 50;  // 51%-60%得50分
        } else if (percentileRank <= 0.7) {
            baseScore = 40;  // 61%-70%得40分
        } else if (percentileRank <= 0.8) {
            baseScore = 30;  // 71%-80%得30分
        } else if (percentileRank <= 0.9) {
            baseScore = 20;  // 81%-90%得20分
        } else {
            baseScore = 10;  // 91%-100%得10分
        }

        // 模块得分为基础分的2%
        return Math.round(baseScore);
    }
}

