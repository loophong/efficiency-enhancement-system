package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.listener.PriceCompeteListener;
import com.heli.supplier.listener.RiskListener;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierPriceCompeteMapper;
import com.heli.supplier.domain.SupplierPriceCompete;
import com.heli.supplier.service.ISupplierPriceCompeteService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 价格竞争力Service业务层处理
 * 
 * @author wll
 * @date 2025-03-05
 */
@Service
@Slf4j
public class SupplierPriceCompeteServiceImpl extends ServiceImpl<SupplierPriceCompeteMapper, SupplierPriceCompete> implements ISupplierPriceCompeteService
{
    @Autowired
    private SupplierPriceCompeteMapper supplierPriceCompeteMapper;

    /**
     * 查询价格竞争力
     * 
     * @param id 价格竞争力主键
     * @return 价格竞争力
     */
    @Override
    public SupplierPriceCompete selectSupplierPriceCompeteById(String id)
    {
        return supplierPriceCompeteMapper.selectSupplierPriceCompeteById(id);
    }

    /**
     * 查询价格竞争力列表
     * 
     * @param supplierPriceCompete 价格竞争力
     * @return 价格竞争力
     */
    @Override
    public List<SupplierPriceCompete> selectSupplierPriceCompeteList(SupplierPriceCompete supplierPriceCompete)
    {
        return supplierPriceCompeteMapper.selectSupplierPriceCompeteList(supplierPriceCompete);
    }

    /**
     * 新增价格竞争力
     * 
     * @param supplierPriceCompete 价格竞争力
     * @return 结果
     */
    @Override
    public int insertSupplierPriceCompete(SupplierPriceCompete supplierPriceCompete)
    {
        setSupplierScore(supplierPriceCompete);
        return supplierPriceCompeteMapper.insertSupplierPriceCompete(supplierPriceCompete);
    }

    /**
     * 修改价格竞争力
     * 
     * @param supplierPriceCompete 价格竞争力
     * @return 结果
     */
    @Override
    public int updateSupplierPriceCompete(SupplierPriceCompete supplierPriceCompete)
    {
        setSupplierScore(supplierPriceCompete);
        return supplierPriceCompeteMapper.updateSupplierPriceCompete(supplierPriceCompete);
    }

    /**
     * 批量删除价格竞争力
     * 
     * @param ids 需要删除的价格竞争力主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPriceCompeteByIds(String[] ids)
    {
        return supplierPriceCompeteMapper.deleteSupplierPriceCompeteByIds(ids);
    }

    /**
     * 删除价格竞争力信息
     * 
     * @param id 价格竞争力主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPriceCompeteById(String id)
    {
        return supplierPriceCompeteMapper.deleteSupplierPriceCompeteById(id);
    }


    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
//            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierPriceCompete.class,
                                new PriceCompeteListener(supplierPriceCompeteMapper,uploadMonth))
                        .sheet("价格竞争力")
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
     * 计算价格竞争力分数
     * 各供应商默认基础分60分，如存在专项返利政策+20分；需自主提货-20分；
     * 常规物料存在价格优势+20分；单一物料无价格优势-5分；常规物料无价格优势-20分。
     *
     * @param priceType 价格类型
     * @return 计算后的分数
     */
    private Long calculateScore(String priceType) {
        // 默认基础分60分
        long baseScore = 60;

        if (priceType == null || priceType.trim().isEmpty()) {
            return baseScore;
        }

        // 根据价格类型调整分数
        switch (priceType) {
            case "专项返利政策":
            case "1":
                return baseScore + 20; // +20分

            case "需自主提货":
            case "2":
                return baseScore - 20; // -20分

            case "常规物料有价格优势":
            case "3":
                return baseScore + 20; // +20分

            case "单一物料无价格优势":
            case "4":
                return baseScore - 5;  // -5分

            case "常规物料无价格优势":
            case "5":
                return baseScore - 20; // -20分

            default:
                log.warn("未知的价格类型: {}, 使用默认分数", priceType);
                return baseScore;
        }
    }

    /**
     * 设置供应商分数
     * 包括计算分数和模型得分
     *
     * @param supplierPriceCompete 供应商价格竞争力对象
     */
    private void setSupplierScore(SupplierPriceCompete supplierPriceCompete) {
        if (supplierPriceCompete != null) {
            // 计算分数
            Long score = calculateScore(supplierPriceCompete.getPriceType());
            supplierPriceCompete.setScore(score);

            log.info("供应商 [{}] 的价格类型为 [{}], 计算得分: {}, 模型得分: {}",
                    supplierPriceCompete.getSupplierName(),
                    supplierPriceCompete.getPriceType(),
                    supplierPriceCompete.getScore(),
                    supplierPriceCompete.getModelScore());
        }
    }


}
