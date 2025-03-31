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
            this.remove(new QueryWrapper<>());
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
}
