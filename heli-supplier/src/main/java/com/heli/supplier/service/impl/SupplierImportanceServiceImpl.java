package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.listener.ImportanceListener;
import com.heli.supplier.listener.RiskListener;
import com.heli.supplier.mapper.SupplierRiskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierImportanceMapper;
import com.heli.supplier.domain.SupplierImportance;
import com.heli.supplier.service.ISupplierImportanceService;
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
    public int insertSupplierImportance(SupplierImportance supplierImportance)
    {
        return supplierImportanceMapper.insertSupplierImportance(supplierImportance);
    }

    /**
     * 修改重要度
     * 
     * @param supplierImportance 重要度
     * @return 结果
     */
    @Override
    public int updateSupplierImportance(SupplierImportance supplierImportance)
    {
        return supplierImportanceMapper.updateSupplierImportance(supplierImportance);
    }

    /**
     * 批量删除重要度
     * 
     * @param ids 需要删除的重要度主键
     * @return 结果
     */
    @Override
    public int deleteSupplierImportanceByIds(String[] ids)
    {
        return supplierImportanceMapper.deleteSupplierImportanceByIds(ids);
    }

    /**
     * 删除重要度信息
     * 
     * @param id 重要度主键
     * @return 结果
     */
    @Override
    public int deleteSupplierImportanceById(String id)
    {
        return supplierImportanceMapper.deleteSupplierImportanceById(id);
    }


    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierImportance.class,
                                new ImportanceListener(supplierImportanceMapper,uploadMonth))
                        .sheet("重要度")
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

}
