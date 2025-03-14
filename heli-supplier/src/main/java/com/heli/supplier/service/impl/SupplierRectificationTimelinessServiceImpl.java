package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierRectificationTimelinessMapper;
import com.heli.supplier.domain.SupplierRectificationTimeliness;
import com.heli.supplier.service.ISupplierRectificationTimelinessService;

/**
 * 产品技术问题整改及时性Service业务层处理
 * 
 * @author wll
 * @date 2025-03-05
 */
@Service
@Slf4j
public class SupplierRectificationTimelinessServiceImpl extends ServiceImpl<SupplierRectificationTimelinessMapper, SupplierRectificationTimeliness> implements ISupplierRectificationTimelinessService
{
    @Autowired
    private SupplierRectificationTimelinessMapper supplierRectificationTimelinessMapper;

    /**
     * 查询产品技术问题整改及时性
     * 
     * @param id 产品技术问题整改及时性主键
     * @return 产品技术问题整改及时性
     */
    @Override
    public SupplierRectificationTimeliness selectSupplierRectificationTimelinessById(String id)
    {
        return supplierRectificationTimelinessMapper.selectSupplierRectificationTimelinessById(id);
    }

    /**
     * 查询产品技术问题整改及时性列表
     * 
     * @param supplierRectificationTimeliness 产品技术问题整改及时性
     * @return 产品技术问题整改及时性
     */
    @Override
    public List<SupplierRectificationTimeliness> selectSupplierRectificationTimelinessList(SupplierRectificationTimeliness supplierRectificationTimeliness)
    {
        return supplierRectificationTimelinessMapper.selectSupplierRectificationTimelinessList(supplierRectificationTimeliness);
    }

    /**
     * 新增产品技术问题整改及时性
     * 
     * @param supplierRectificationTimeliness 产品技术问题整改及时性
     * @return 结果
     */
    @Override
    public int insertSupplierRectificationTimeliness(SupplierRectificationTimeliness supplierRectificationTimeliness)
    {
        supplierRectificationTimeliness.setScore(calculateScore(supplierRectificationTimeliness.getCooperationDegree()));
        return supplierRectificationTimelinessMapper.updateSupplierRectificationTimeliness(supplierRectificationTimeliness);
// return supplierRectificationTimelinessMapper.insertSupplierRectificationTimeliness(supplierRectificationTimeliness);
    }

    /**
     * 修改产品技术问题整改及时性
     * 
     * @param supplierRectificationTimeliness 产品技术问题整改及时性
     * @return 结果
     */
    @Override
    public int updateSupplierRectificationTimeliness(SupplierRectificationTimeliness supplierRectificationTimeliness)
    {
        supplierRectificationTimeliness.setScore(calculateScore(supplierRectificationTimeliness.getCooperationDegree()));
        return supplierRectificationTimelinessMapper.updateSupplierRectificationTimeliness(supplierRectificationTimeliness);
//        return supplierRectificationTimelinessMapper.updateSupplierRectificationTimeliness(supplierRectificationTimeliness);
    }

    /**
     * 批量删除产品技术问题整改及时性
     * 
     * @param ids 需要删除的产品技术问题整改及时性主键
     * @return 结果
     */
    @Override
    public int deleteSupplierRectificationTimelinessByIds(String[] ids)
    {
        return supplierRectificationTimelinessMapper.deleteSupplierRectificationTimelinessByIds(ids);
    }

    /**
     * 删除产品技术问题整改及时性信息
     * 
     * @param id 产品技术问题整改及时性主键
     * @return 结果
     */
    @Override
    public int deleteSupplierRectificationTimelinessById(String id)
    {
        return supplierRectificationTimelinessMapper.deleteSupplierRectificationTimelinessById(id);
    }

    /**
     * 根据配合程度计算得分
     * @param cooperationDegree 配合程度
     * @return 计算后的得分
     */
    private Long calculateScore(Long cooperationDegree) {
        long cooperationScore;

        if (cooperationDegree == null) {
            return 7L; // 如果为空，则返回默认值 70
        }

        switch (cooperationDegree.intValue()) {
            case 100: // 配合度高，满足设计时效
                cooperationScore = 100;
                break;
            case 40: // 配合意愿较低，影响生产进度
                cooperationScore = 40;
                break;
            case 0: // 配合差，影响生产
                cooperationScore = 0;
                break;
            default:
                cooperationScore = 70; // 其他情况，保持默认值
        }

        // 计算最终得分：模块得分 = 评定得分 10%
        return Math.round(cooperationScore*0.1);
    }
}
