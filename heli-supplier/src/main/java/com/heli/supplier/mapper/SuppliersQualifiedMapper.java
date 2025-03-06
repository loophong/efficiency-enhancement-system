package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 供应商Mapper接口
 *
 * @author ruoyi
 * @date 2025-01-17
 */
public interface SuppliersQualifiedMapper extends BaseMapper<SuppliersQualified> {
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
     * 删除供应商
     *
     * @param id 供应商主键
     * @return 结果
     */
    public int deleteSuppliersQualifiedById(Long id);

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSuppliersQualifiedByIds(Long[] ids);
}
