package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierSelfTestReportsMapper;
import com.heli.supplier.domain.SupplierSelfTestReports;
import com.heli.supplier.service.ISupplierSelfTestReportsService;

/**
 * 自检报告Service业务层处理
 * 
 * @author wll
 * @date 2025-02-26
 */
@Service
public class SupplierSelfTestReportsServiceImpl extends ServiceImpl<SupplierSelfTestReportsMapper, SupplierSelfTestReports> implements ISupplierSelfTestReportsService
{
    @Autowired
    private SupplierSelfTestReportsMapper supplierSelfTestReportsMapper;

    /**
     * 查询自检报告
     * 
     * @param id 自检报告主键
     * @return 自检报告
     */
    @Override
    public SupplierSelfTestReports selectSupplierSelfTestReportsById(Long id)
    {
        return supplierSelfTestReportsMapper.selectSupplierSelfTestReportsById(id);
    }

    /**
     * 查询自检报告列表
     * 
     * @param supplierSelfTestReports 自检报告
     * @return 自检报告
     */
    @Override
    public List<SupplierSelfTestReports> selectSupplierSelfTestReportsList(SupplierSelfTestReports supplierSelfTestReports)
    {
        return supplierSelfTestReportsMapper.selectSupplierSelfTestReportsList(supplierSelfTestReports);
    }

    /**
     * 新增自检报告
     * 
     * @param supplierSelfTestReports 自检报告
     * @return 结果
     */
    @Override
    public int insertSupplierSelfTestReports(SupplierSelfTestReports supplierSelfTestReports)
    {
        // 计算分数
        double score = calculateScore(supplierSelfTestReports.getSpecificContent());

        // 设置计算后的分数
        supplierSelfTestReports.setScore(score);
        return supplierSelfTestReportsMapper.insertSupplierSelfTestReports(supplierSelfTestReports);
    }

    /**
     * 修改自检报告
     * 
     * @param supplierSelfTestReports 自检报告
     * @return 结果
     */
    @Override
    public int updateSupplierSelfTestReports(SupplierSelfTestReports supplierSelfTestReports)
    {
        // 计算分数
        double score = calculateScore(supplierSelfTestReports.getSpecificContent());

        // 设置计算后的分数
        supplierSelfTestReports.setScore(score);
        return supplierSelfTestReportsMapper.updateSupplierSelfTestReports(supplierSelfTestReports);
    }

    /**
     * 批量删除自检报告
     * 
     * @param ids 需要删除的自检报告主键
     * @return 结果
     */
    @Override
    public int deleteSupplierSelfTestReportsByIds(Long[] ids)
    {
        return supplierSelfTestReportsMapper.deleteSupplierSelfTestReportsByIds(ids);
    }

    /**
     * 删除自检报告信息
     * 
     * @param id 自检报告主键
     * @return 结果
     */
    @Override
    public int deleteSupplierSelfTestReportsById(Long id)
    {
        return supplierSelfTestReportsMapper.deleteSupplierSelfTestReportsById(id);
    }

    /**
     * 计算分数的方法
     */
    private double calculateScore(String specificContent) {
        // 默认得分 2 分
        double score = 2.0;

        // 判断是否有数据造假情况
        if (specificContent != null || !specificContent.isEmpty()) {
           // if (specificContent.contains("尺寸造假") || specificContent.contains("性能造假")) {
                score = 0.0; // 如果有数据造假，分数为 0
           // }
        }

        return score;
    }

}
