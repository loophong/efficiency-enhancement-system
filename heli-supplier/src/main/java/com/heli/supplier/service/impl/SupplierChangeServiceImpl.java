package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierGuarantee;
import com.heli.supplier.mapper.SupplierGuaranteeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierChangeMapper;
import com.heli.supplier.domain.SupplierChange;
import com.heli.supplier.service.ISupplierChangeService;

/**
 * 擅自变更产品材质参数尺寸Service业务层处理
 * 
 * @author wll
 * @date 2025-03-07
 */
@Service
public class SupplierChangeServiceImpl extends ServiceImpl<SupplierChangeMapper, SupplierChange> implements ISupplierChangeService
{
    @Autowired
    private SupplierChangeMapper supplierChangeMapper;

    /**
     * 查询擅自变更产品材质参数尺寸
     * 
     * @param id 擅自变更产品材质参数尺寸主键
     * @return 擅自变更产品材质参数尺寸
     */
    @Override
    public SupplierChange selectSupplierChangeById(String id)
    {
        return supplierChangeMapper.selectSupplierChangeById(id);
    }

    /**
     * 查询擅自变更产品材质参数尺寸列表
     * 
     * @param supplierChange 擅自变更产品材质参数尺寸
     * @return 擅自变更产品材质参数尺寸
     */
    @Override
    public List<SupplierChange> selectSupplierChangeList(SupplierChange supplierChange)
    {
        return supplierChangeMapper.selectSupplierChangeList(supplierChange);
    }

    /**
     * 新增擅自变更产品材质参数尺寸
     * 
     * @param supplierChange 擅自变更产品材质参数尺寸
     * @return 结果
     */
    @Override
    public int insertSupplierChange(SupplierChange supplierChange) {
        QueryWrapper<SupplierChange> queryWrapper = new QueryWrapper<SupplierChange>()
                .eq("supplier_code", supplierChange.getSupplierCode());
        SupplierChange existingChange = supplierChangeMapper.selectOne(queryWrapper);
        if (existingChange != null) {
            if (!existingChange.getFormLeibie().equals(supplierChange.getFormLeibie())) {
                throw new IllegalArgumentException("与已存入的供应商类别不一致");
            }
        }
        return supplierChangeMapper.insertSupplierChange(supplierChange);
    }
//    @Override
//    public int insertSupplierChange(SupplierChange supplierChange) {
//        QueryWrapper<SupplierChange> queryWrapper = new QueryWrapper<SupplierChange>()
//                .eq("supplier_code", supplierChange.getSupplierCode());
//
//        SupplierChange existingChange = supplierChangeMapper.selectOne(queryWrapper);
//        if (existingChange != null) {
//            if (!existingChange.getFormLeibie().equals(supplierChange.getFormLeibie())) {
//                throw new IllegalArgumentException("供应商类别不一致");
//            }
//            long occurrenceCount = existingChange.getHappenNumber() + 1;
//            existingChange.setScore(calculateScore(supplierChange.getFormLeibie(), occurrenceCount));
//            existingChange.setHappenNumber(occurrenceCount);
//            return supplierChangeMapper.updateById(existingChange);
//        } else {
//            long occurrenceCount = 1;
//            supplierChange.setScore(calculateScore(supplierChange.getFormLeibie(), occurrenceCount));
//            supplierChange.setHappenNumber(occurrenceCount);
//            return supplierChangeMapper.insertSupplierChange(supplierChange);
//        }
//    }

    /**
     * 修改擅自变更产品材质参数尺寸
     * 
     * @param supplierChange 擅自变更产品材质参数尺寸
     * @return 结果
     */
    @Override
    public int updateSupplierChange(SupplierChange supplierChange)
    {
        // 查询现有的 SupplierChange 记录
        SupplierChange existingChange = supplierChangeMapper.selectById(supplierChange.getId());
        if (existingChange == null) {
            throw new IllegalArgumentException("指定的 SupplierChange 记录不存在");
        }
        // 检查并防止修改 supplierCode, formLeibie, supplierName
        if (!existingChange.getSupplierCode().equals(supplierChange.getSupplierCode())) {
            throw new IllegalArgumentException("禁止修改供应商代码");
        }
        if (!existingChange.getFormLeibie().equals(supplierChange.getFormLeibie())) {
            throw new IllegalArgumentException("禁止修改供应商类别");
        }
        if (!existingChange.getSupplierName().equals(supplierChange.getSupplierName())) {
            throw new IllegalArgumentException("禁止修改供应商名称");
        }
        return supplierChangeMapper.updateSupplierChange(supplierChange);
    }

    /**
     * 批量删除擅自变更产品材质参数尺寸
     * 
     * @param ids 需要删除的擅自变更产品材质参数尺寸主键
     * @return 结果
     */
    @Override
    public int deleteSupplierChangeByIds(String[] ids)
    {

        return supplierChangeMapper.deleteSupplierChangeByIds(ids);
    }

    /**
     * 删除擅自变更产品材质参数尺寸信息
     * 
     * @param id 擅自变更产品材质参数尺寸主键
     * @return 结果
     */
    @Override
    public int deleteSupplierChangeById(String id)
    {
        return supplierChangeMapper.deleteSupplierChangeById(id);
    }


    /**
     * 计算模块得分
     * @param formLeibie
     * @param occurrenceCount
     * @return
     */
//    private Double calculateScore(Long formLeibie, long occurrenceCount) {
//        final double BASE_SCORE = 100;
//        final double MODULE_PERCENTAGE = 0.03;
//        double deduction = 0;
//
//        if (formLeibie == 20) { // 股份共有供应商
//            deduction = occurrenceCount * 20;
//        } else if (formLeibie == 40) { // 自主供应商
//            deduction = occurrenceCount * 40;
//        }
//
//        double finalScore = Math.max(BASE_SCORE - deduction, 0); // 确保分数不会变成负数
//        return finalScore * MODULE_PERCENTAGE; // 取模块得分
//    }


//    public int countOccurrencesBySupplierCode(String supplierCode) {
//        return Math.toIntExact(this.count(new QueryWrapper<SupplierChange>()
//                .eq("supplier_code", supplierCode)));
//    }

}
