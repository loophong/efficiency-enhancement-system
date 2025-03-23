package com.heli.supplier.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.domain.SupplierPerformanceServicesCollaboration;
import org.springframework.web.multipart.MultipartFile;

/**
 * 服务与协作Service接口
 * 
 * @author wll
 * @date 2025-03-05
 */
public interface ISupplierPerformanceServicesCollaborationService extends IService<SupplierPerformanceServicesCollaboration>
{
    /**
     * 查询服务与协作
     * 
     * @param id 服务与协作主键
     * @return 服务与协作
     */
    public SupplierPerformanceServicesCollaboration selectSupplierPerformanceServicesCollaborationById(String id);

    /**
     * 查询服务与协作列表
     * 
     * @param supplierPerformanceServicesCollaboration 服务与协作
     * @return 服务与协作集合
     */
    public List<SupplierPerformanceServicesCollaboration> selectSupplierPerformanceServicesCollaborationList(SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration);

    /**
     * 新增服务与协作
     * 
     * @param supplierPerformanceServicesCollaboration 服务与协作
     * @return 结果
     */
    public int insertSupplierPerformanceServicesCollaboration(SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration);

    /**
     * 修改服务与协作
     * 
     * @param supplierPerformanceServicesCollaboration 服务与协作
     * @return 结果
     */
    public int updateSupplierPerformanceServicesCollaboration(SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration);

    /**
     * 批量删除服务与协作
     * 
     * @param ids 需要删除的服务与协作主键集合
     * @return 结果
     */
    public int deleteSupplierPerformanceServicesCollaborationByIds(String[] ids);

    /**
     * 删除服务与协作信息
     * 
     * @param id 服务与协作主键
     * @return 结果
     */
    public int deleteSupplierPerformanceServicesCollaborationById(String id);

    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile, Date uploadMonth);
}
