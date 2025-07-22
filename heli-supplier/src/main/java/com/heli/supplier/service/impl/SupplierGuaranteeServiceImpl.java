package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.controller.SupplierGuaranteeController;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.listener.GuaranteeListener;
import com.heli.supplier.listener.RiskListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierGuaranteeMapper;
import com.heli.supplier.domain.SupplierGuarantee;
import com.heli.supplier.service.ISupplierGuaranteeService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 供货保障Service业务层处理
 *
 * @author wll
 * @date 2025-03-19
 */
@Service
@Slf4j
public class SupplierGuaranteeServiceImpl extends ServiceImpl<SupplierGuaranteeMapper, SupplierGuarantee> implements ISupplierGuaranteeService
{
    @Autowired
    private SupplierGuaranteeMapper supplierGuaranteeMapper;

    /**
     * 查询供货保障
     *
     * @param id 供货保障主键
     * @return 供货保障
     */
    @Override
    public SupplierGuarantee selectSupplierGuaranteeById(Long id)
    {
        return supplierGuaranteeMapper.selectSupplierGuaranteeById(id);
    }

    /**
     * 查询供货保障列表
     *
     * @param supplierGuarantee 供货保障
     * @return 供货保障
     */
    @Override
    public List<SupplierGuarantee> selectSupplierGuaranteeList(SupplierGuarantee supplierGuarantee)
    {
        return supplierGuaranteeMapper.selectSupplierGuaranteeList(supplierGuarantee);
    }

    /**
     * 新增供货保障
     *
     * @param supplierGuarantee 供货保障
     * @return 结果
     */
    @Override
    public int insertSupplierGuarantee(SupplierGuarantee supplierGuarantee)
    {
        double score = calculateScore(supplierGuarantee.getTimelyDeliveryRate());
        supplierGuarantee.setTimelyRateScore(score);
        return supplierGuaranteeMapper.insertSupplierGuarantee(supplierGuarantee);
    }

    /**
     * 修改供货保障
     *
     * @param supplierGuarantee 供货保障
     * @return 结果
     */
    @Override
    public int updateSupplierGuarantee(SupplierGuarantee supplierGuarantee)
    {
        double score = calculateScore(supplierGuarantee.getTimelyDeliveryRate());
        supplierGuarantee.setTimelyRateScore(score);
        return supplierGuaranteeMapper.updateSupplierGuarantee(supplierGuarantee);
    }

    /**
     * 批量删除供货保障
     *
     * @param ids 需要删除的供货保障主键
     * @return 结果
     */
    @Override
    public int deleteSupplierGuaranteeByIds(Long[] ids)
    {
        return supplierGuaranteeMapper.deleteSupplierGuaranteeByIds(ids);
    }

    /**
     * 删除供货保障信息
     *
     * @param id 供货保障主键
     * @return 结果
     */
    @Override
    public int deleteSupplierGuaranteeById(Long id)
    {
        return supplierGuaranteeMapper.deleteSupplierGuaranteeById(id);
    }

    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
//            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierGuarantee.class,
                                new GuaranteeListener(supplierGuaranteeMapper,uploadMonth))
                        .sheet("供货保障")
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


    private double calculateScore(String guarantee) {
        if (guarantee == null || !guarantee.endsWith("%")) {
            throw new IllegalArgumentException("供货及时率格式错误，必须是百分比字符串，如 '85.5%'");
        }
        // 去掉百分号并转换为数值
        double rate = Double.parseDouble(guarantee.replace("%", "").trim());
        return rate;
    }

}
