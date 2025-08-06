package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.listener.RiskListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierRiskMapper;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.service.ISupplierRiskService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 经营风险Service业务层处理
 * 
 * @author wll
 * @date 2025-02-27
 */
@Service
@Slf4j
public class SupplierRiskServiceImpl  extends ServiceImpl<SupplierRiskMapper, SupplierRisk> implements ISupplierRiskService
{
    @Autowired
    private SupplierRiskMapper supplierRiskMapper;

    /**
     * 查询经营风险
     * 
     * @param id 经营风险主键
     * @return 经营风险
     */
    @Override
    public SupplierRisk selectSupplierRiskById(String id)
    {
        return supplierRiskMapper.selectSupplierRiskById(id);
    }

    /**
     * 查询经营风险列表
     * 
     * @param supplierRisk 经营风险
     * @return 经营风险
     */
    @Override
    public List<SupplierRisk> selectSupplierRiskList(SupplierRisk supplierRisk)
    {
        return supplierRiskMapper.selectSupplierRiskList(supplierRisk);
    }

    /**
     * 新增经营风险
     * 
     * @param supplierRisk 经营风险
     * @return 结果
     */
    @Override
    public int insertSupplierRisk(SupplierRisk supplierRisk)
    {
        calculateRiskScore(supplierRisk);
        return supplierRiskMapper.insertSupplierRisk(supplierRisk);
    }

    /**
     * 修改经营风险
     * 
     * @param supplierRisk 经营风险
     * @return 结果
     */
    @Override
    public int updateSupplierRisk(SupplierRisk supplierRisk)
    {
        calculateRiskScore(supplierRisk);
        return supplierRiskMapper.updateSupplierRisk(supplierRisk);
    }

    /**
     * 批量删除经营风险
     * 
     * @param ids 需要删除的经营风险主键
     * @return 结果
     */
    @Override
    public int deleteSupplierRiskByIds(String[] ids)
    {
        return supplierRiskMapper.deleteSupplierRiskByIds(ids);
    }

    /**
     * 删除经营风险信息
     * 
     * @param id 经营风险主键
     * @return 结果
     */
    @Override
    public int deleteSupplierRiskById(String id)
    {
        return supplierRiskMapper.deleteSupplierRiskById(id);
    }


    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierRisk.class,
                                new RiskListener(supplierRiskMapper,uploadMonth))
                        .sheet("经营风险")
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
     * 计算经营风险得分
     *
     * @param supplierRisk 供应商风险对象，包含 riskNumber 字段
     * @return 计算后的得分
     */
    private void calculateRiskScore(SupplierRisk supplierRisk) {
        // 基础分
        long baseScore = 100;
        // 获取风险数量，如果为 null 则默认为 0
        long riskNumber = (supplierRisk.getRiskNumber() != null) ? supplierRisk.getRiskNumber() : 0;
        // 计算总扣分
        double deduction = riskNumber * 10;
        // 最终得分，不能小于 0
        double finalScore = Math.max(baseScore - deduction, 0);
        supplierRisk.setScore(Math.max(finalScore, 0));

    }


}
