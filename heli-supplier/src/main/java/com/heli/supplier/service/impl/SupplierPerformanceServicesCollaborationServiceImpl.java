package com.heli.supplier.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierGuarantee;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.listener.GuaranteeListener;
import com.heli.supplier.listener.PerformanceServicesCollaborationListener;
import com.heli.supplier.mapper.SupplierOnetimeSimpleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierPerformanceServicesCollaborationMapper;
import com.heli.supplier.domain.SupplierPerformanceServicesCollaboration;
import com.heli.supplier.service.ISupplierPerformanceServicesCollaborationService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 服务与协作Service业务层处理
 * 
 * @author wll
 * @date 2025-03-05
 */
@Service
@Slf4j
public class SupplierPerformanceServicesCollaborationServiceImpl extends ServiceImpl<SupplierPerformanceServicesCollaborationMapper, SupplierPerformanceServicesCollaboration> implements ISupplierPerformanceServicesCollaborationService
{
    @Autowired
    private SupplierPerformanceServicesCollaborationMapper supplierPerformanceServicesCollaborationMapper;

    /**
     * 查询服务与协作
     * 
     * @param id 服务与协作主键
     * @return 服务与协作
     */
    @Override
    public SupplierPerformanceServicesCollaboration selectSupplierPerformanceServicesCollaborationById(String id)
    {
        return supplierPerformanceServicesCollaborationMapper.selectSupplierPerformanceServicesCollaborationById(id);
    }

    /**
     * 查询服务与协作列表
     * 
     * @param supplierPerformanceServicesCollaboration 服务与协作
     * @return 服务与协作
     */
    @Override
    public List<SupplierPerformanceServicesCollaboration> selectSupplierPerformanceServicesCollaborationList(SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration)
    {
        return supplierPerformanceServicesCollaborationMapper.selectSupplierPerformanceServicesCollaborationList(supplierPerformanceServicesCollaboration);
    }

    /**
     * 新增服务与协作
     * 
     * @param supplierPerformanceServicesCollaboration 服务与协作
     * @return 结果
     */
    @Override
    public int insertSupplierPerformanceServicesCollaboration(SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration)
    {
        return supplierPerformanceServicesCollaborationMapper.insertSupplierPerformanceServicesCollaboration(supplierPerformanceServicesCollaboration);
    }

    /**
     * 修改服务与协作
     * 
     * @param supplierPerformanceServicesCollaboration 服务与协作
     * @return 结果
     */
    @Override
    public int updateSupplierPerformanceServicesCollaboration(SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration)
    {
        return supplierPerformanceServicesCollaborationMapper.updateSupplierPerformanceServicesCollaboration(supplierPerformanceServicesCollaboration);
    }

    /**
     * 批量删除服务与协作
     * 
     * @param ids 需要删除的服务与协作主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPerformanceServicesCollaborationByIds(String[] ids)
    {
        return supplierPerformanceServicesCollaborationMapper.deleteSupplierPerformanceServicesCollaborationByIds(ids);
    }

    /**
     * 删除服务与协作信息
     * 
     * @param id 服务与协作主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPerformanceServicesCollaborationById(String id)
    {
        return supplierPerformanceServicesCollaborationMapper.deleteSupplierPerformanceServicesCollaborationById(id);
    }

    /**
     * 有些字段需要从第二行开始读，其他的从第一行读
     * author: wll
     * @return
     */
//    private List<List<String>> buildCustomHead() {
//        List<List<String>> headList = new ArrayList<>();
//
//        // 假设 SupplierPerformanceServicesCollaboration 类有字段 field1, field2, field3
//        // field1 和 field2 从第 1 行开始读取，field3 从第 2 行开始读取
//
//        // 第 1 行表头
//        List<String> head1 = new ArrayList<>();
//        head1.add("供应商代码");
//        head1.add("供应商名称");
//        head1.add("");
//        head1.add("");
//        head1.add("");
//        head1.add("得分");
//        headList.add(head1);
//
//        // 第 2 行表头
//        List<String> head2 = new ArrayList<>();
//        head2.add("");
//        head2.add("");
//        head2.add("供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作");
//        head2.add("供应商如受到相关方处罚");
//        head2.add("供应商如对采购员需求反馈不及时");
//        head2.add("");
//        head2.add("");
//        headList.add(head2);
//
//        return headList;
//    }
    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierPerformanceServicesCollaboration.class,
                                new PerformanceServicesCollaborationListener(supplierPerformanceServicesCollaborationMapper,uploadMonth))
                        .sheet("服务与协作")//.head(buildCustomHead())
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
