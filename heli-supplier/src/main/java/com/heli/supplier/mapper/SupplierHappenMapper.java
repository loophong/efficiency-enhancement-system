package com.heli.supplier.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.supplier.domain.SupplierHappen;
import com.heli.supplier.domain.SupplierImportance;

/**
 * 质量通知单Mapper接口
 * 
 * @author wll
 * @date 2025-03-07
 */
public interface SupplierHappenMapper extends BaseMapper<SupplierHappen>
{
    /**
     * 查询质量通知单
     * 
     * @param id 质量通知单主键
     * @return 质量通知单
     */
    public SupplierHappen selectSupplierHappenById(Long id);

    /**
     * 查询质量通知单列表
     * 
     * @param supplierHappen 质量通知单
     * @return 质量通知单集合
     */
    public List<SupplierHappen> selectSupplierHappenList(SupplierHappen supplierHappen);

    /**
     * 新增质量通知单
     * 
     * @param supplierHappen 质量通知单
     * @return 结果
     */
    public int insertSupplierHappen(SupplierHappen supplierHappen);

    /**
     * 修改质量通知单
     * 
     * @param supplierHappen 质量通知单
     * @return 结果
     */
    public int updateSupplierHappen(SupplierHappen supplierHappen);

    /**
     * 删除质量通知单
     * 
     * @param id 质量通知单主键
     * @return 结果
     */
    public int deleteSupplierHappenById(Long id);

    /**
     * 批量删除质量通知单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierHappenByIds(Long[] ids);
}
