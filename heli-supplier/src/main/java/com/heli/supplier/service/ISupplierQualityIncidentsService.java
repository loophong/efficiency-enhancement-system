package com.heli.supplier.service;

import java.util.List;
import com.heli.supplier.domain.SupplierQualityIncidents;
import com.baomidou.mybatisplus.extension.service.IService;

import org.springframework.web.multipart.MultipartFile;
/**
 * 质量事故Service接口
 *
 * @author ruoyi
 * @date 2025-02-20
 */
public interface ISupplierQualityIncidentsService extends IService<SupplierQualityIncidents>
{
    /**
     * 查询质量事故
     *
     * @param id 质量事故主键
     * @return 质量事故
     */
    public SupplierQualityIncidents selectSupplierQualityIncidentsById(String id);

    /**
     * 查询质量事故列表
     *
     * @param supplierQualityIncidents 质量事故
     * @return 质量事故集合
     */
    public List<SupplierQualityIncidents> selectSupplierQualityIncidentsList(SupplierQualityIncidents supplierQualityIncidents);

    /**
     * 新增质量事故
     *
     * @param supplierQualityIncidents 质量事故
     * @return 结果
     */
    public int insertSupplierQualityIncidents(SupplierQualityIncidents supplierQualityIncidents);

    /**
     * 修改质量事故
     *
     * @param supplierQualityIncidents 质量事故
     * @return 结果
     */
    public int updateSupplierQualityIncidents(SupplierQualityIncidents supplierQualityIncidents);

    /**
     * 批量删除质量事故
     *
     * @param ids 需要删除的质量事故主键集合
     * @return 结果
     */
    public int deleteSupplierQualityIncidentsByIds(String[] ids);

    /**
     * 删除质量事故信息
     *
     * @param id 质量事故主键
     * @return 结果
     */
    public int deleteSupplierQualityIncidentsById(String id);
}
