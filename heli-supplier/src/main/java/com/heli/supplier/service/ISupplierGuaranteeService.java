package com.heli.supplier.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierGuarantee;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import org.springframework.web.multipart.MultipartFile;

/**
 * 供货保障Service接口
 *
 * @author wll
 * @date 2025-03-19
 */
public interface ISupplierGuaranteeService extends IService<SupplierGuarantee>
{
    /**
     * 查询供货保障
     *
     * @param id 供货保障主键
     * @return 供货保障
     */
    public SupplierGuarantee selectSupplierGuaranteeById(Long id);

    /**
     * 查询供货保障列表
     *
     * @param supplierGuarantee 供货保障
     * @return 供货保障集合
     */
    public List<SupplierGuarantee> selectSupplierGuaranteeList(SupplierGuarantee supplierGuarantee);

    /**
     * 新增供货保障
     *
     * @param supplierGuarantee 供货保障
     * @return 结果
     */
    public int insertSupplierGuarantee(SupplierGuarantee supplierGuarantee);

    /**
     * 修改供货保障
     *
     * @param supplierGuarantee 供货保障
     * @return 结果
     */
    public int updateSupplierGuarantee(SupplierGuarantee supplierGuarantee);

    /**
     * 批量删除供货保障
     *
     * @param ids 需要删除的供货保障主键集合
     * @return 结果
     */
    public int deleteSupplierGuaranteeByIds(Long[] ids);

    /**
     * 删除供货保障信息
     *
     * @param id 供货保障主键
     * @return 结果
     */
    public int deleteSupplierGuaranteeById(Long id);

    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile, Date uploadMonth);
}
