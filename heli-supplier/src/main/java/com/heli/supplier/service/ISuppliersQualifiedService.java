package com.heli.supplier.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SuppliersQualified;
import org.springframework.web.multipart.MultipartFile;

/**
 * 供应商Service接口
 *
 * @author ruoyi
 * @date 2025-01-17
 */
public interface ISuppliersQualifiedService extends IService<SuppliersQualified> {
    /**
     * 查询供应商
     *
     * @param id 供应商主键
     * @return 供应商
     */
    public SuppliersQualified selectSuppliersQualifiedById(Long id);

    /**
     * 查询供应商列表
     *
     * @param suppliersQualified 供应商
     * @return 供应商集合
     */
    public List<SuppliersQualified> selectSuppliersQualifiedList(SuppliersQualified suppliersQualified);

    /**
     * 新增供应商
     *
     * @param suppliersQualified 供应商
     * @return 结果
     */
    public int insertSuppliersQualified(SuppliersQualified suppliersQualified);

    /**
     * 修改供应商
     *
     * @param suppliersQualified 供应商
     * @return 结果
     */
    public int updateSuppliersQualified(SuppliersQualified suppliersQualified);

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的供应商主键集合
     * @return 结果
     */
    public int deleteSuppliersQualifiedByIds(Long[] ids);

    /**
     * 删除供应商信息
     *
     * @param id 供应商主键
     * @return 结果
     */
    public int deleteSuppliersQualifiedById(Long id);

    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile);
}
