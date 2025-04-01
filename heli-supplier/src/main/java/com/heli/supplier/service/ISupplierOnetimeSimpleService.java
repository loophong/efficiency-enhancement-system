package com.heli.supplier.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierHappen;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import org.springframework.web.multipart.MultipartFile;

/**
 * 一次交检合格率-简化版Service接口
 * 
 * @author weill
 * @date 2025-02-21
 */
public interface ISupplierOnetimeSimpleService extends IService<SupplierOnetimeSimple>
{
    /**
     * 查询一次交检合格率-简化版
     * 
     * @param id 一次交检合格率-简化版主键
     * @return 一次交检合格率-简化版
     */
    public SupplierOnetimeSimple selectSupplierOnetimeSimpleById(Long id);

    /**
     * 查询一次交检合格率-简化版列表
     * 
     * @param supplierOnetimeSimple 一次交检合格率-简化版
     * @return 一次交检合格率-简化版集合
     */
    public List<SupplierOnetimeSimple> selectSupplierOnetimeSimpleList(SupplierOnetimeSimple supplierOnetimeSimple);

    /**
     * 新增一次交检合格率-简化版
     * 
     * @param supplierOnetimeSimple 一次交检合格率-简化版
     * @return 结果
     */
    public int insertSupplierOnetimeSimple(SupplierOnetimeSimple supplierOnetimeSimple);

    /**
     * 修改一次交检合格率-简化版
     * 
     * @param supplierOnetimeSimple 一次交检合格率-简化版
     * @return 结果
     */
    public int updateSupplierOnetimeSimple(SupplierOnetimeSimple supplierOnetimeSimple);

    /**
     * 批量删除一次交检合格率-简化版
     * 
     * @param ids 需要删除的一次交检合格率-简化版主键集合
     * @return 结果
     */
    public int deleteSupplierOnetimeSimpleByIds(Long[] ids);

    /**
     * 删除一次交检合格率-简化版信息
     * 
     * @param id 一次交检合格率-简化版主键
     * @return 结果
     */
    public int deleteSupplierOnetimeSimpleById(Long id);

    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile, Date uploadMonth);


}
